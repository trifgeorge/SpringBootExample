# Basic Spring Boot Config Project

## Description

A basic Spring Boot example that can be copied and developed.

## Table of Contents

- [Installation](#installation)
- [Further Development](#further-development)
- [Configuration](#configuration)
- [Dependencies](#dependencies)
- [Testing](#testing)

## Installation

To build the project `mvn clean install`(default profile is set to `DEV`).

To build and run the project `mvn spring-boot:run`(default profile is set to `DEV`).

If you need to change or add a new profile please check [Configuration](#configuration).

## Further Development

The project can be expanded further using Spring or Spring Boot libraries, depending on the requirements. Here are some possible areas for development:

- **Web MVC**: Implementing a web layer for handling HTTP requests and responses, allowing for the development of web applications.  
- **Repository**: Integrating repository layers for data access and management, enabling interaction with databases or other data sources.  
- **Caching**: Implementing caching mechanisms to improve application performance by reducing latency and minimizing database or external service calls.  

## Configuration

To run a specific profile please add `-P` parameter to `mvn clean install` and `mvn spring-boot:run`. Currently, a `prod` profile is configured in `pom.xml`.  
EX: `mvn clean install -Pprod`  
You can modify/add new profiles in `pom.xml`.  
Every profile should have an `application.properties` defined(`application-{profile}.properties`) in the _resource_ folder.  

For logging config please modify `log4j2-spring.xml` file from _resource_ folder.  

You can add JVM parameters in _.mvn/maven.config_. The parameters will be passed to each build.

## Dependencies

You need to have `JAVA_HOME` and `MAVEN_HOME` in the `PATH` environment.  
For more info on how to create a Java env please check [JAVA_ENV](JAVA_ENV.md)

## Testing

To run the tests `mvn clean test`.  
By default it will use the properties file from _src/test/resources_.  
If you need Spring context initialized please extend `DefaultAbstractContextTestClass`.
