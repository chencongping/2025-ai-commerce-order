# 2025 AI Agent 微服务模板 - 完整文档
- [中文](README-zh.md)
- [English](README-en.md)
- [日本語](README-ja.md)
## 目录
1. [项目概述](#项目概述)
2. [技术栈说明](#技术栈说明)
3. [环境要求](#环境要求)
4. [项目结构](#项目结构)
5. [快速开始](#快速开始)
6. [核心功能](#核心功能)
7. [配置说明](#配置说明)
8. [依赖说明](#依赖说明)
9. [扩展开发指南](#扩展开发指南)
10. [测试指南](#测试指南)
11. [部署说明](#部署说明)
12. [常见问题](#常见问题)
13. [许可证](#许可证)

## 项目概述

`2025_ai_agent_microservice_template_3_5_4` 是一个基于 Spring Boot 3.5.4 的微服务模板项目，专为 AI 代理服务设计。该模板提供了标准化的项目结构、自动生成的 API 文档和基础的微服务架构支持，旨在帮助开发者快速搭建稳定、可扩展的 AI 代理服务。

项目最初基于 [chencongping/2025_ai_agent_microservice_template](https://github.com/chencongping/2025_ai_agent_microservice_template) 模板构建，并可通过 Git 合并获取最新更新。

## 技术栈说明

- **核心框架**：Spring Boot 3.5.4
- **构建工具**：Maven 3.6+
- **开发语言**：Java 17
- **API 文档**：SpringDoc OpenAPI 2.3.0 (Swagger UI)
- **微服务支持**：Spring Cloud 2025.0.0
- **代码简化工具**：Lombok

## 环境要求

- JDK 17 或更高版本
- Maven 3.6 或更高版本
- Git 版本控制系统
- 可选：Docker (用于容器化部署)

## 项目结构

```
2025_ai_agent_microservice_template_3_5_4
├── .github/                 # GitHub 配置文件
├── src/
│   ├── main/
│   │   ├── java/com/ai/agent/
│   │   │   ├── configs/     # 配置类目录
│   │   │   │   └── SwaggerConfig.java  # Swagger 配置
│   │   │   ├── controller/  # 控制器目录
│   │   │   │   └── HellowordController.java  # 示例控制器
│   │   │   └── Application.java  # 应用程序入口类
│   │   └── resources/       # 资源文件目录
│   │       └── application.yml  # 应用配置文件
│   └── test/                # 测试代码目录
├── .gitattributes           # Git 属性配置
├── .gitignore               # Git 忽略文件配置
├── Dockerfile               # Docker 构建文件
├── LICENSE                  # 许可证文件
├── pom.xml                  # Maven 项目对象模型
├── README.md                # 项目说明文档
└── settings.xml             # Maven 配置文件
```

## 快速开始

### 1. 获取项目代码

```bash
# 克隆仓库
git clone <你的仓库地址>

# 进入项目目录
cd 2025_ai_agent_microservice_template_3_5_4

# 合并官方模板最新更新
git pull https://github.com/chencongping/2025_ai_agent_microservice_template.git master --allow-unrelated-histories
```

### 2. 构建项目

```bash
# 清理并构建项目，跳过测试以加快构建速度
mvn clean package -Dmaven.test.skip=true
```

### 3. 运行项目

```bash
# 使用 Java 命令运行
java -jar target/2025_ai_agent_microservice_template_3_5_4-0.0.1-SNAPSHOT.jar

# 或者使用 Maven 运行
mvn spring-boot:run
```

### 4. 访问 API 文档

项目启动后，通过以下地址访问自动生成的 API 文档：
```
http://localhost:8080/swagger-ui/index.html
```

## 核心功能

### 1. API 文档自动生成

集成 SpringDoc OpenAPI 实现 API 文档的自动生成和管理：
- 自动扫描控制器类和方法生成文档
- 支持在线接口调试
- 符合 OpenAPI 3.0 规范

### 2. 基础 Web 服务支持

基于 Spring Boot Starter Web 提供完整的 Web 服务能力：
- 内置 Tomcat 服务器
- 支持 RESTful 风格接口开发
- 自动处理 JSON 序列化/反序列化

### 3. 代码简化

通过 Lombok 减少模板代码：
- 自动生成 getter/setter 方法
- 简化构造函数创建
- 提供日志记录等常用功能

### 4. 微服务扩展能力

通过 Spring Cloud 依赖管理，为后续微服务扩展提供支持：
- 服务注册与发现
- 配置中心
- 服务熔断与降级
- 负载均衡

## 配置说明

项目主要配置文件为 `src/main/resources/application.yml`，可根据需求进行修改：

```yaml
# 服务器配置
server:
  port: 8080  # 服务端口
  servlet:
    context-path: /  # 上下文路径

# Spring 配置
spring:
  application:
    name: ai-agent-service  # 服务名称

# SpringDoc OpenAPI 配置
springdoc:
  api-docs:
    path: /api-docs  # API 文档JSON路径
  swagger-ui:
    path: /swagger-ui.html  # Swagger UI 路径
    operationsSorter: method  # 接口排序方式

# 日志配置
logging:
  level:
    root: INFO  # 根日志级别
    com.ai.agent: DEBUG  # 项目包日志级别
```

## 依赖说明

项目依赖通过 Maven 管理，主要依赖项如下：

### 核心依赖

| 依赖 | 版本 | 描述 |
|------|------|------|
| `org.springframework.boot:spring-boot-starter-parent` | 3.5.4 | Spring Boot 父工程，统一管理依赖版本 |
| `org.springframework.boot:spring-boot-starter-web` | 由父工程管理 | Web 开发核心依赖，包含 Spring MVC 和 Tomcat |
| `org.springframework.boot:spring-boot-starter-test` | 由父工程管理 | 测试支持，包含 JUnit、Mockito 等 |
| `org.projectlombok:lombok` | 由父工程管理 | 代码简化工具 |
| `org.springdoc:springdoc-openapi-starter-webmvc-ui` | 2.3.0 | OpenAPI 文档生成工具 |
| `org.springframework.cloud:spring-cloud-dependencies` | 2025.0.0 | Spring Cloud 依赖管理 |

### 构建插件

| 插件 | 版本 | 描述 |
|------|------|------|
| `org.springframework.boot:spring-boot-maven-plugin` | 由父工程管理 | Spring Boot 打包插件 |
| `org.apache.maven.plugins:maven-surefire-plugin` | 3.0.0-M3 | Maven 测试插件 |

## 扩展开发指南

### 添加新的控制器

1. 在 `com.ai.agent.controller` 包下创建新的控制器类
2. 使用 `@RestController` 注解标记类
3. 使用 `@RequestMapping` 或特定 HTTP 方法注解（`@GetMapping`、`@PostMapping` 等）定义接口

示例：
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
@Tag(name = "用户管理", description = "用户相关操作接口")
public class UserController {

    @GetMapping("/{id}")
    @Operation(summary = "获取用户信息", description = "根据用户ID获取用户详细信息")
    public String getUser(@PathVariable String id) {
        return "User information for ID: " + id;
    }
}
```

### 集成数据库

1. 添加数据库相关依赖到 `pom.xml`：
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

2. 在 `application.yml` 中配置数据源：
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

### 集成消息队列（Kafka）

1. 添加 Kafka 依赖到 `pom.xml`：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-kafka</artifactId>
</dependency>
```

2. 在 `application.yml` 中配置 Kafka：
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

## 测试指南

### 单元测试

1. 在 `src/test/java` 目录下创建与主代码对应的测试包结构
2. 使用 JUnit 5 编写单元测试
3. 使用 Mockito 进行依赖模拟

示例：
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

### 运行测试

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=HellowordControllerTest
```

## 部署说明

### 1. 构建可执行 JAR

```bash
mvn clean package -Dmaven.test.skip=true
```

构建完成后，可执行 JAR 文件将生成在 `target` 目录下。

### 2. 运行 JAR 文件

```bash
java -jar target/2025_ai_agent_microservice_template_3_5_4-0.0.1-SNAPSHOT.jar
```

### 3. 容器化部署（Docker）

1. 构建 Docker 镜像：
```bash
docker build -t ai-agent-service:0.0.1 .
```

2. 运行 Docker 容器：
```bash
docker run -d -p 8080:8080 --name ai-agent-service ai-agent-service:0.0.1
```

## 常见问题

### 1. Swagger UI 无法访问

- 检查 `springdoc-openapi-starter-webmvc-ui` 依赖是否正确添加
- 确认访问路径是否正确：`http://localhost:8080/swagger-ui/index.html`
- 检查是否有拦截器或安全配置阻止了访问

### 2. 依赖冲突

- 使用 `mvn dependency:tree` 命令查看依赖树
- 排查并排除冲突的依赖
- 确保 Spring Boot 和 Spring Cloud 版本兼容

### 3. 启动失败

- 检查端口是否被占用
- 查看控制台输出的错误信息
- 检查配置文件是否有错误

## 许可证

本项目基于 MIT 许可证开源，详情参见 LICENSE 文件。