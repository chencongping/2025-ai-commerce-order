# 2025 AI Commerce Order System

一个基于Spring Boot的AI电商订单管理系统，提供完整的客户管理、商品管理和订单管理功能。

## 功能特性

- 🏪 **客户管理**: 客户信息的增删改查、搜索功能
- 📦 **商品管理**: 商品信息管理、库存管理、分类管理
- 🛒 **订单管理**: 订单创建、状态跟踪、订单取消
- 📊 **数据统计**: 订单统计、销售分析
- 🔍 **搜索功能**: 支持客户和商品的模糊搜索
- 📚 **API文档**: 完整的Swagger API文档
- 📈 **监控指标**: Prometheus指标收集和监控

## 技术栈

- **后端框架**: Spring Boot 3.5.4
- **数据库**: H2 (开发环境) / MySQL (生产环境)
- **ORM**: Spring Data JPA
- **API文档**: Swagger/OpenAPI 3
- **监控**: Spring Boot Actuator + Prometheus
- **构建工具**: Maven
- **Java版本**: 17

## 快速开始

### 1. 环境要求

- Java 17+
- Maven 3.6+

### 2. 克隆项目

```bash
git clone <repository-url>
cd 2025-ai-commerce-order
```

### 3. 运行应用

```bash
# 使用Maven运行
mvn spring-boot:run

# 或者先编译再运行
mvn clean package
java -jar target/2025-ai-commerce-order-0.0.1-SNAPSHOT.jar
```

### 4. 访问应用

- **应用主页**: http://localhost:8080
- **Swagger API文档**: http://localhost:8080/swagger-ui.html
- **H2数据库控制台**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`
- **健康检查**: http://localhost:8080/actuator/health
- **Prometheus指标**: http://localhost:8080/actuator/prometheus

## API接口

### 客户管理 API

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/customers` | 创建客户 |
| GET | `/api/customers` | 获取所有客户 |
| GET | `/api/customers/{id}` | 根据ID获取客户 |
| GET | `/api/customers/email/{email}` | 根据邮箱获取客户 |
| GET | `/api/customers/search?keyword={keyword}` | 搜索客户 |
| PUT | `/api/customers/{id}` | 更新客户 |
| DELETE | `/api/customers/{id}` | 删除客户 |

### 商品管理 API

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/products` | 创建商品 |
| GET | `/api/products` | 获取所有商品 |
| GET | `/api/products/available` | 获取可用商品 |
| GET | `/api/products/{id}` | 根据ID获取商品 |
| GET | `/api/products/sku/{sku}` | 根据SKU获取商品 |
| GET | `/api/products/search?keyword={keyword}` | 搜索商品 |
| GET | `/api/products/category/{category}` | 根据分类获取商品 |
| PUT | `/api/products/{id}` | 更新商品 |
| PUT | `/api/products/{id}/stock?quantity={quantity}` | 更新库存 |
| DELETE | `/api/products/{id}` | 删除商品 |

### 订单管理 API

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/orders` | 创建订单 |
| GET | `/api/orders` | 获取所有订单 |
| GET | `/api/orders/{id}` | 根据ID获取订单 |
| GET | `/api/orders/number/{orderNumber}` | 根据订单号获取订单 |
| GET | `/api/orders/customer/{customerId}` | 获取客户订单 |
| GET | `/api/orders/status/{status}` | 根据状态获取订单 |
| PUT | `/api/orders/{id}/status?status={status}` | 更新订单状态 |
| PUT | `/api/orders/{id}/cancel` | 取消订单 |
| DELETE | `/api/orders/{id}` | 删除订单 |

## 数据模型

### 客户 (Customer)
- 基本信息：姓名、邮箱、手机、地址
- 状态：活跃、非活跃、暂停

### 商品 (Product)
- 基本信息：名称、描述、价格、SKU
- 库存：库存数量、状态
- 分类：商品分类

### 订单 (Order)
- 基本信息：订单号、客户、总金额
- 状态：待确认、已确认、已发货、已送达、已取消、已退款
- 配送：收货地址、备注

### 订单项 (OrderItem)
- 关联：订单、商品
- 数量：购买数量、单价

## 测试数据

系统启动时会自动创建测试数据：

### 测试客户
- 张三 (zhangsan@example.com)
- 李四 (lisi@example.com)  
- 王五 (wangwu@example.com)

### 测试商品
- iPhone 15 Pro (¥7,999)
- MacBook Pro 14英寸 (¥15,999)
- AirPods Pro 2 (¥1,899)
- iPad Air 5 (¥4,399)
- Apple Watch Series 9 (¥2,999) - 缺货

## 部署

### Docker部署

```bash
# 构建镜像
docker build -t ai-commerce-order .

# 运行容器
docker run -p 8080:8080 ai-commerce-order
```

### Docker Compose部署

```bash
cd deploy
docker-compose up -d
```

## 开发指南

### 项目结构

```
src/main/java/com/ai/agent/
├── configs/          # 配置类
├── controller/       # 控制器层
├── dto/             # 数据传输对象
├── entity/          # 实体类
├── exception/       # 异常处理
├── repository/      # 数据访问层
├── service/         # 业务逻辑层
└── OrderApplication.java  # 主应用类
```

### 添加新功能

1. 在`entity`包中创建实体类
2. 在`repository`包中创建Repository接口
3. 在`dto`包中创建DTO类
4. 在`service`包中创建Service类
5. 在`controller`包中创建Controller类
6. 更新Swagger文档注解

## 监控和运维

### 健康检查
- `/actuator/health` - 应用健康状态
- `/actuator/info` - 应用信息

### 指标监控
- `/actuator/metrics` - 应用指标
- `/actuator/prometheus` - Prometheus格式指标

### 日志
- 应用日志：`/app/logs/app.log`
- 日志级别：INFO
- 日志格式：时间戳 + 线程 + 级别 + 类名 + 消息

## 许可证

MIT License

## 贡献

欢迎提交Issue和Pull Request来改进这个项目。

## 联系方式

- 作者：ChenCongPing
- GitHub：https://github.com/chencongping/2025-ai-commerce-order
