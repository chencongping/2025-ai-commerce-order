# 2025 AI Agent Microservice Template - Complete Documentation
- [中文](README-zh.md)
- [English](README-en.md)
- [日本語](README-ja.md)
## Table of Contents
1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Environment Requirements](#environment-requirements)
4. [Project Structure](#project-structure)
5. [Quick Start](#quick-start)
6. [Core Features](#core-features)
7. [Configuration Instructions](#configuration-instructions)
8. [Dependency Description](#dependency-description)
9. [Extension Development Guide](#extension-development-guide)
10. [Testing Guide](#testing-guide)
11. [Deployment Instructions](#deployment-instructions)
12. [Frequently Asked Questions](#frequently-asked-questions)
13. [License](#license)


## Project Overview

`2025_ai_agent_microservice_template_3_5_4` is a microservice template project based on Spring Boot 3.5.4, designed specifically for AI agent services. This template provides a standardized project structure, automatically generated API documentation, and basic microservice architecture support, aiming to help developers quickly build stable and scalable AI agent services.

The project was initially built based on the [chencongping/2025_ai_agent_microservice_template](https://github.com/chencongping/2025_ai_agent_microservice_template) template and can be updated by merging from the Git repository.


## Technology Stack

- **Core Framework**: Spring Boot 3.5.4
- **Build Tool**: Maven 3.6+
- **Programming Language**: Java 17
- **API Documentation**: SpringDoc OpenAPI 2.3.0 (Swagger UI)
- **Microservice Support**: Spring Cloud 2025.0.0
- **Code Simplification Tool**: Lombok


## Environment Requirements

- JDK 17 or higher
- Maven 3.6 or higher
- Git version control system
- Optional: Docker (for containerized deployment)


## Project Structure

```
2025_ai_agent_microservice_template_3_5_4
├── .github/                 # GitHub configuration files
├── src/
│   ├── main/
│   │   ├── java/com/ai/agent/
│   │   │   ├── configs/     # Configuration classes directory
│   │   │   │   └── SwaggerConfig.java  # Swagger configuration
│   │   │   ├── controller/  # Controllers directory
│   │   │   │   └── HellowordController.java  # Example controller
│   │   │   └── Application.java  # Application entry class
│   │   └── resources/       # Resource files directory
│   │       └── application.yml  # Application configuration file
│   └── test/                # Test code directory
├── .gitattributes           # Git attributes configuration
├── .gitignore               # Git ignore file configuration
├── Dockerfile               # Docker build file
├── LICENSE                  # License file
├── pom.xml                  # Maven Project Object Model
├── README.md                # Project description document
└── settings.xml             # Maven configuration file
```


## Quick Start

### 1. Get Project Code

```bash
# Clone the repository
git clone <your-repository-url>

# Enter the project directory
cd 2025_ai_agent_microservice_template_3_5_4

# Merge the latest updates from the official template
git pull https://github.com/chencongping/2025_ai_agent_microservice_template.git master --allow-unrelated-histories
```

### 2. Build the Project

```bash
# Clean and build the project, skip tests to speed up the build
mvn clean package -Dmaven.test.skip=true
```

### 3. Run the Project

```bash
# Run using Java command
java -jar target/2025_ai_agent_microservice_template_3_5_4-0.0.1-SNAPSHOT.jar

# Or run using Maven
mvn spring-boot:run
```

### 4. Access API Documentation

After the project starts, access the automatically generated API documentation at:
```
http://localhost:8080/swagger-ui/index.html
```


## Core Features

### 1. Automatic API Documentation Generation

Integrated with SpringDoc OpenAPI to automatically generate and manage API documentation:
- Automatically scans controller classes and methods to generate documentation
- Supports online interface debugging
- Complies with OpenAPI 3.0 specification

### 2. Basic Web Service Support

Provides complete web service capabilities based on Spring Boot Starter Web:
- Built-in Tomcat server
- Supports RESTful style interface development
- Automatically handles JSON serialization/deserialization

### 3. Code Simplification

Reduces template code through Lombok:
- Automatically generates getter/setter methods
- Simplifies constructor creation
- Provides logging and other common functions

### 4. Microservice Expansion Capability

Through Spring Cloud dependency management, provides support for subsequent microservice expansion:
- Service registration and discovery
- Configuration center
- Service circuit breaker and degradation
- Load balancing


## Configuration Instructions

The main configuration file of the project is `src/main/resources/application.yml`, which can be modified as needed:

```yaml
# Server configuration
server:
  port: 8080  # Service port
  servlet:
    context-path: /  # Context path

# Spring configuration
spring:
  application:
    name: ai-agent-service  # Service name

# SpringDoc OpenAPI configuration
springdoc:
  api-docs:
    path: /api-docs  # API documentation JSON path
  swagger-ui:
    path: /swagger-ui.html  # Swagger UI path
    operationsSorter: method  # Interface sorting method

# Log configuration
logging:
  level:
    root: INFO  # Root log level
    com.ai.agent: DEBUG  # Project package log level
```


## Dependency Description

Project dependencies are managed through Maven. The main dependencies are as follows:

### Core Dependencies

| Dependency | Version | Description |
|------------|---------|-------------|
| `org.springframework.boot:spring-boot-starter-parent` | 3.5.4 | Spring Boot parent project, unified management of dependency versions |
| `org.springframework.boot:spring-boot-starter-web` | Managed by parent | Core dependency for web development, including Spring MVC and Tomcat |
| `org.springframework.boot:spring-boot-starter-test` | Managed by parent | Test support, including JUnit, Mockito, etc. |
| `org.projectlombok:lombok` | Managed by parent | Code simplification tool |
| `org.springdoc:springdoc-openapi-starter-webmvc-ui` | 2.3.0 | OpenAPI documentation generation tool |
| `org.springframework.cloud:spring-cloud-dependencies` | 2025.0.0 | Spring Cloud dependency management |

### Build Plugins

| Plugin | Version | Description |
|--------|---------|-------------|
| `org.springframework.boot:spring-boot-maven-plugin` | Managed by parent | Spring Boot packaging plugin |
| `org.apache.maven.plugins:maven-surefire-plugin` | 3.0.0-M3 | Maven test plugin |


## Extension Development Guide

### Adding a New Controller

1. Create a new controller class under the `com.ai.agent.controller` package
2. Mark the class with `@RestController` annotation
3. Define interfaces using `@RequestMapping` or specific HTTP method annotations (`@GetMapping`, `@PostMapping`, etc.)

Example:
```java
package com.ai.agent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Interfaces for user-related operations")
public class UserController {

    @GetMapping("/{id}")
    @Operation(summary = "Get user information", description = "Get detailed user information by user ID")
    public String getUser(@PathVariable String id) {
        return "User information for ID: " + id;
    }
}
```

### Integrating a Database

1. Add database-related dependencies to `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

2. Configure the data source in `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ai_agent_db?useSSL=false&serverTimezone=UTC
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
```

### Integrating Message Queue (Kafka)

1. Add Kafka dependency to `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-kafka</artifactId>
</dependency>
```

2. Configure Kafka in `application.yml`:
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: ai-agent-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```


## Testing Guide

### Unit Testing

1. Create a test package structure corresponding to the main code in the `src/test/java` directory
2. Write unit tests using JUnit 5
3. Use Mockito for dependency mocking

Example:
```java
package com.ai.agent.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HellowordController.class)
public class HellowordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHello() throws Exception {
        mockMvc.perform(get("/hello"))
               .andExpect(status().isOk());
    }
}
```

### Running Tests

```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=HellowordControllerTest
```


## Deployment Instructions

### 1. Build Executable JAR

```bash
mvn clean package -Dmaven.test.skip=true
```

After the build is complete, the executable JAR file will be generated in the `target` directory.

### 2. Run the JAR File

```bash
java -jar target/2025_ai_agent_microservice_template_3_5_4-0.0.1-SNAPSHOT.jar
```

### 3. Containerized Deployment (Docker)

1. Build the Docker image:
```bash
docker build -t ai-agent-service:0.0.1 .
```

2. Run the Docker container:
```bash
docker run -d -p 8080:8080 --name ai-agent-service ai-agent-service:0.0.1
```


## Frequently Asked Questions

### 1. Swagger UI Cannot Be Accessed

- Check if the `springdoc-openapi-starter-webmvc-ui` dependency is correctly added
- Confirm the access path is correct: `http://localhost:8080/swagger-ui/index.html`
- Check if any interceptors or security configurations are blocking access

### 2. Dependency Conflicts

- Use `mvn dependency:tree` command to view the dependency tree
- Troubleshoot and exclude conflicting dependencies
- Ensure Spring Boot and Spring Cloud versions are compatible

### 3. Failed to Start

- Check if the port is occupied
- Check the error information in the console output
- Check if the configuration file has errors


## License

This project is open-source under the MIT License. For details, see the LICENSE file.