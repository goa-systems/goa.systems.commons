# GOA systems commons

[![Quality Gate Status](https://sonarqube.goa.systems/api/project_badges/measure?project=goa.systems.commons&metric=alert_status&token=sqb_fde560455caecb7b8d7f7f7f90e2c1327b15214e)](https://sonarqube.goa.systems/dashboard?id=goa.systems.commons)

A library containing common code snippets to reduce duplicate implementations of often used code.

## Compatibility

JDK >= 11

## Usage
The file `build.gradle` in your project should contain
```groovy
repositories {
	maven {
		url 'https://maven.goa.systems'
	}
}
dependencies {
	api 'goa.systems:commons:{version}'
}
```
