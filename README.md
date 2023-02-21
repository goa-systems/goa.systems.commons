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
|0.3.1|Migration to GitHub complete. Dependency updates.|