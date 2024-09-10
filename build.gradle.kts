plugins {
	`java-library`
	`maven-publish`
    `jacoco`
}

val group = "goa.systems".toString()
val artifactname = "commons".toString()
val version = project.property("ARTIFACT_VERSION").toString()

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

	from(layout.buildDirectory.dir("publications/Commons"))
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
		
tasks.register<Tar>("distribute") {

	group = "build"
	description = "Creates tgz distribution."
	
	dependsOn(tasks["writeVariables"])
	dependsOn(tasks["exportPom"])
	dependsOn(tasks["exportApplicationLibraries"])
	
	from(layout.buildDirectory.dir("export"))
    archiveExtension.set("tar.gz")
	compression = Compression.GZIP
	
}

publishing {
    publications {
        create<MavenPublication>(artifactname) {
            groupId = group
            artifactId = artifactname
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