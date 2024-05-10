# GOA systems commons

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
|0.5.1|Migrating to Kotlin based Gradle scripts.|
|0.5.0|Adding logic.|
|0.4.0|Adding logic to make a given string valid.|
|0.3.1|Migration to GitHub complete. Dependency updates.|