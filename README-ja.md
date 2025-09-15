# 2025 AI Agent マイクロサービステンプレート - 完全ドキュメント
- [中文](README-zh.md)
- [English](README-en.md)
- [日本語](README-ja.md)
## 目次
1. [プロジェクト概要](#プロジェクト概要)
2. [技術スタック](#技術スタック)
3. [環境要件](#環境要件)
4. [プロジェクト構造](#プロジェクト構造)
5. [クイックスタート](#クイックスタート)
6. [コア機能](#コア機能)
7. [設定説明](#設定説明)
8. [依存関係の説明](#依存関係の説明)
9. [拡張開発ガイド](#拡張開発ガイド)
10. [テストガイド](#テストガイド)
11. [デプロイメント手順](#デプロイメント手順)
12. [よくある質問](#よくある質問)
13. [ライセンス](#ライセンス)


## プロジェクト概要

`2025_ai_agent_microservice_template_3_5_4` は、Spring Boot 3.5.4 を基盤としたAIエージェントサービス向けのマイクロサービステンプレートです。このテンプレートは標準化されたプロジェクト構造、自動生成されるAPIドキュメント、基本的なマイクロサービスアーキテクチャのサポートを提供し、開発者が安定的で拡張可能なAIエージェントサービスを迅速に構築するのを支援します。

このプロジェクトは当初、[chencongping/2025_ai_agent_microservice_template](https://github.com/chencongping/2025_ai_agent_microservice_template) テンプレートを基に構築され、Gitリポジトリからのマージによって最新の更新を取得できます。


## 技術スタック

- **コアフレームワーク**: Spring Boot 3.5.4
- **ビルドツール**: Maven 3.6+
- **開発言語**: Java 17
- **APIドキュメント**: SpringDoc OpenAPI 2.3.0 (Swagger UI)
- **マイクロサービスサポート**: Spring Cloud 2025.0.0
- **コード簡略化ツール**: Lombok


## 環境要件

- JDK 17 以上
- Maven 3.6 以上
- Git バージョン管理システム
- オプション: Docker (コンテナ化デプロイ用)


## プロジェクト構造

```
2025_ai_agent_microservice_template_3_5_4
├── .github/                 # GitHub設定ファイル
├── src/
│   ├── main/
│   │   ├── java/com/ai/agent/
│   │   │   ├── configs/     # 設定クラスディレクトリ
│   │   │   │   └── SwaggerConfig.java  # Swagger設定
│   │   │   ├── controller/  # コントローラディレクトリ
│   │   │   │   └── HellowordController.java  # サンプルコントローラ
│   │   │   └── Application.java  # アプリケーションエントリークラス
│   │   └── resources/       # リソースファイルディレクトリ
│   │       └── application.yml  # アプリケーション設定ファイル
│   └── test/                # テストコードディレクトリ
├── .gitattributes           # Git属性設定
├── .gitignore               # Git無視ファイル設定
├── Dockerfile               # Dockerビルドファイル
├── LICENSE                  # ライセンスファイル
├── pom.xml                  # Mavenプロジェクトオブジェクトモデル
├── README.md                # プロジェクト説明ドキュメント
└── settings.xml             # Maven設定ファイル
```


## クイックスタート

### 1. プロジェクトコードの取得

```bash
# リポジトリのクローン
git clone <あなたのリポジトリURL>

# プロジェクトディレクトリに移動
cd 2025_ai_agent_microservice_template_3_5_4

# 公式テンプレートの最新更新をマージ
git pull https://github.com/chencongping/2025_ai_agent_microservice_template.git master --allow-unrelated-histories
```

### 2. プロジェクトのビルド

```bash
# プロジェクトのクリーンとビルド、テストをスキップしてビルドを高速化
mvn clean package -Dmaven.test.skip=true
```

### 3. プロジェクトの実行

```bash
# Javaコマンドを使用して実行
java -jar target/2025_ai_agent_microservice_template_3_5_4-0.0.1-SNAPSHOT.jar

# またはMavenを使用して実行
mvn spring-boot:run
```

### 4. APIドキュメントへのアクセス

プロジェクトの起動後、以下のアドレスから自動生成されたAPIドキュメントにアクセスできます:
```
http://localhost:8080/swagger-ui/index.html
```


## コア機能

### 1. APIドキュメントの自動生成

SpringDoc OpenAPIを統合してAPIドキュメントの自動生成と管理を実現:
- コントローラクラスとメソッドを自動的にスキャンしてドキュメントを生成
- オンラインインターフェースデバッグをサポート
- OpenAPI 3.0仕様に準拠

### 2. 基本的なWebサービスサポート

Spring Boot Starter Webに基づいて完全なWebサービス機能を提供:
- 組み込みTomcatサーバー
- RESTfulスタイルのインターフェース開発をサポート
- JSONのシリアル化/逆シリアル化を自動的に処理

### 3. コードの簡略化

Lombokによってテンプレートコードを削減:
- getter/setterメソッドを自動生成
- コンストラクタの作成を簡略化
- ロギングなどの共通機能を提供

### 4. マイクロサービス拡張機能

Spring Cloudの依存関係管理により、後続のマイクロサービス拡張をサポート:
- サービス登録とディスカバリ
- 設定センター
- サービスサーキットブレーカーとデグレード
- ロードバランシング


## 設定説明

プロジェクトの主な設定ファイルは `src/main/resources/application.yml` で、必要に応じて変更できます:

```yaml
# サーバー設定
server:
  port: 8080  # サービスポート
  servlet:
    context-path: /  # コンテキストパス

# Spring設定
spring:
  application:
    name: ai-agent-service  # サービス名

# SpringDoc OpenAPI設定
springdoc:
  api-docs:
    path: /api-docs  # APIドキュメントJSONパス
  swagger-ui:
    path: /swagger-ui.html  # Swagger UIパス
    operationsSorter: method  # インターフェースソート方法

# ログ設定
logging:
  level:
    root: INFO  # ルートログレベル
    com.ai.agent: DEBUG  # プロジェクトパッケージログレベル
```


## 依存関係の説明

プロジェクトの依存関係はMavenによって管理されています。主な依存関係は以下の通りです:

### コア依存関係

| 依存関係 | バージョン | 説明 |
|------------|---------|-------------|
| `org.springframework.boot:spring-boot-starter-parent` | 3.5.4 | Spring Boot親プロジェクト、依存バージョンの統一管理 |
| `org.springframework.boot:spring-boot-starter-web` | 親によって管理 | Web開発のコア依存、Spring MVCとTomcatを含む |
| `org.springframework.boot:spring-boot-starter-test` | 親によって管理 | テストサポート、JUnit、Mockitoなどを含む |
| `org.projectlombok:lombok` | 親によって管理 | コード簡略化ツール |
| `org.springdoc:springdoc-openapi-starter-webmvc-ui` | 2.3.0 | OpenAPIドキュメント生成ツール |
| `org.springframework.cloud:spring-cloud-dependencies` | 2025.0.0 | Spring Cloud依存関係管理 |

### ビルドプラグイン

| プラグイン | バージョン | 説明 |
|--------|---------|-------------|
| `org.springframework.boot:spring-boot-maven-plugin` | 親によって管理 | Spring Bootパッケージングプラグイン |
| `org.apache.maven.plugins:maven-surefire-plugin` | 3.0.0-M3 | Mavenテストプラグイン |


## 拡張開発ガイド

### 新しいコントローラの追加

1. `com.ai.agent.controller` パッケージの下に新しいコントローラクラスを作成
2. `@RestController` アノテーションでクラスにマーク
3. `@RequestMapping` または特定のHTTPメソッドアノテーション（`@GetMapping`、`@PostMapping`など）を使用してインターフェースを定義

例:
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
@Tag(name = "ユーザー管理", description = "ユーザー関連操作のインターフェース")
public class UserController {

    @GetMapping("/{id}")
    @Operation(summary = "ユーザー情報の取得", description = "ユーザーIDによる詳細情報の取得")
    public String getUser(@PathVariable String id) {
        return "IDのユーザー情報: " + id;
    }
}
```

### データベースの統合

1. データベース関連の依存関係を `pom.xml` に追加:
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

2. `application.yml` でデータソースを設定:
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

### メッセージキューの統合（Kafka）

1. Kafkaの依存関係を `pom.xml` に追加:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-kafka</artifactId>
</dependency>
```

2. `application.yml` でKafkaを設定:
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


## テストガイド

### 単体テスト

1. `src/test/java` ディレクトリにメインコードに対応するテストパッケージ構造を作成
2. JUnit 5を使用して単体テストを作成
3. Mockitoを使用して依存関係をモック

例:
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

### テストの実行

```bash
# すべてのテストを実行
mvn test

# 特定のテストクラスを実行
mvn test -Dtest=HellowordControllerTest
```


## デプロイメント手順

### 1. 実行可能なJARのビルド

```bash
mvn clean package -Dmaven.test.skip=true
```

ビルド完了後、実行可能なJARファイルが `target` ディ