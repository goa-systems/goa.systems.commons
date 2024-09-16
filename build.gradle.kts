plugins {
    `java-library`
    `maven-publish`
    `jacoco`
}

if (hasProperty("ARTIFACT_VERSION")) { version = property("ARTIFACT_VERSION").toString() } else { version = "0.0.0".toString() }
if (hasProperty("ARTIFACT_GROUP")) { group = property("ARTIFACT_GROUP").toString() } else { group = "goa.systems".toString() }

var artifactname: String = ""
if (hasProperty("ARTIFACT_ID")) { artifactname = property("ARTIFACT_ID").toString() } else { artifactname ="commons".toString() }

val groupname = group.toString()
val localreponame = "Project"
val repodir = "repo"

val fullSetup by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.slf4j:slf4j-api:2.0.13")
    
    /* Specify all dependencies in configuration fullSetup that are conveniently used during development and that allow execution of the application but which are optional and up to the customer to define. */
    fullSetup("ch.qos.logback:logback-core:1.5.6") {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    fullSetup("ch.qos.logback:logback-classic:1.5.6") {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    
    testImplementation("ch.qos.logback:logback-classic:1.5.6")
    testImplementation("ch.qos.logback:logback-core:1.5.6")
    
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
    
    from(layout.buildDirectory.dir(repodir + "/" + groupname.replace(".", "/") + "/" + artifactname + "/" + version))
    
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