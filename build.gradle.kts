plugins {
    `java-library`
    `maven-publish`
    `jacoco`
}

group = "goa.systems".toString()
if (hasProperty("ARTIFACT_GROUP")) {
    group = property("ARTIFACT_GROUP").toString()
}

var artifactname: String = "commons"
if (hasProperty("ARTIFACT_ID")) {
    artifactname = property("ARTIFACT_ID").toString()
}

version = "0.0.0".toString()
if (hasProperty("ARTIFACT_VERSION")) {
    version = property("ARTIFACT_VERSION").toString()
}

val localreponame = "Project"
val repodir = "repo"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.16")

    // https://mvnrepository.com/artifact/ch.qos.logback/logback-core
    testImplementation("ch.qos.logback:logback-core:1.5.8")

    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    testImplementation("ch.qos.logback:logback-classic:1.5.8")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
    }
}

tasks.register<Copy>("exportFromLocalRepo"){

    group = "build"
    description = "Exports from local Maven repository"
    
    dependsOn(tasks.clean)
    dependsOn(tasks.get("publish" + artifactname.replaceFirstChar(Char::titlecase) + "PublicationTo" + localreponame + "Repository"))
    
    from(layout.buildDirectory.dir(repodir + "/" + project.getGroup().toString().replace(".", "/") + "/" + artifactname + "/" + version))
    
    include(artifactname + "-" + version + ".jar")
    include(artifactname + "-" + version + "-javadoc.jar")
    include(artifactname + "-" + version + "-sources.jar")
    include(artifactname + "-" + version + ".pom")
    
    into(layout.buildDirectory.dir("test"))
}
        
tasks.register<Tar>("distribute") {

    group = "build"
    description = "Creates tgz distribution."
    
    dependsOn(tasks["exportFromLocalRepo"])
    
    from(layout.buildDirectory.dir("test"))

    archiveFileName = artifactname + "-" + version + ".tar.gz"
    destinationDirectory = layout.buildDirectory.dir("distributions")
    compression = Compression.GZIP
}

publishing {

    repositories {
        maven {
            name = localreponame
            url = uri(layout.buildDirectory.dir(repodir))
        }
    }
    
    publications {
        create<MavenPublication>(artifactname) {
            groupId = group.toString()
            artifactId = artifactname.toString()
            version = version
            from(components["java"])
            pom {
                name = "GOA systems " + artifactname
                description = "A library."
            }
        }
    }
}