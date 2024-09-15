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

tasks.register<Copy>("exportPom") {

    group = "build"
    description = "Copy application libraries"
    dependsOn(tasks["generatePomFileForCommonsPublication"])

    from(layout.buildDirectory.dir("publications/" + artifactname))
    include("pom-default.xml")
    into(layout.buildDirectory.dir("export/conf"))
}

tasks.register<Copy>("exportApplicationLibraries") {

    group = "build"
    description = "Copy application libraries"
    dependsOn(tasks["build"])
    dependsOn(tasks["jar"])
    dependsOn(tasks["javadocJar"])
    dependsOn(tasks["sourcesJar"])
    
    from(layout.buildDirectory.dir("libs"))
    into(layout.buildDirectory.dir("export/lib"))
    include("*.jar")
    
}

tasks.register("writeVariables") {

    group = "build"
    description = "Writes variables for maven"
    
    doLast {
        with(layout.buildDirectory.file("export/vars").get().asFile) {
            parentFile.mkdirs()
            writeText("export VERSION=${version}\nexport GROUP=goa.systems\nexport ARTIFACT=commons")
        }
    }
}

tasks.register<Copy>("exportFromLocalRepo"){
    group = "build"
    description = "Exports from local Maven repository"
    
    dependsOn(tasks.get("publish" + artifactname.replaceFirstChar(Char::titlecase) + "PublicationTo" + localreponame + "Repository"))
    from(layout.buildDirectory.dir(repodir + "/" + groupname.replace(".", "/") + "/" + artifactname + "/" + version))
    into(layout.buildDirectory.dir("test"))
    
    include(artifactname + "-" + version + ".jar")
    include(artifactname + "-" + version + "-javadoc.jar")
    include(artifactname + "-" + version + "-sources.jar")
    include(artifactname + "-" + version + ".pom")
}
        
tasks.register<Tar>("distribute") {

    group = "build"
    description = "Creates tgz distribution."
    
    dependsOn(tasks["exportFromLocalRepo"])
    
    from(layout.buildDirectory.dir("test"))
    archiveBaseName = artifactname + "-" + version
    destinationDirectory = layout.buildDirectory.dir("distributions")
    archiveExtension.set("tar.gz")
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

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
    }
}