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

## Changelog

|Version|Description|
|-|-|
|0.6.0|Adding partition calculation logic and dimension calculator.|
|0.5.1|Migrating to Kotlin based Gradle scripts.|
|0.5.0|Adding logic.|
|0.4.0|Adding logic to make a given string valid.|
|0.3.1|Migration to GitHub complete. Dependency updates.|
