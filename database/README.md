# 数据库初始化脚本

这个目录包含了AI电商订单管理系统的数据库初始化脚本。

## 脚本说明

### 1. 01_create_database.sql
- 创建数据库 `ai_commerce_order`
- 设置字符集为 `utf8mb4`
- 设置排序规则为 `utf8mb4_unicode_ci`

### 2. 02_create_tables.sql
- 创建所有必要的表结构
- 包括：customers（客户表）、products（商品表）、orders（订单表）、order_items（订单项表）
- 设置适当的索引和外键约束

### 3. 03_insert_test_data.sql
- 插入测试客户数据（5个客户）
- 插入测试商品数据（8个商品）
- 插入测试订单数据（5个订单）
- 插入测试订单项数据

### 4. run_all.sql
- 按顺序执行所有初始化脚本
- 一键完成数据库初始化

## 使用方法

### 方法1：使用MySQL命令行
```bash
# 连接到MySQL
mysql -u root -p123456

# 执行所有脚本
source /path/to/database/run_all.sql;
```

### 方法2：逐个执行脚本
```bash
# 连接到MySQL
mysql -u root -p123456

# 逐个执行
source /path/to/database/01_create_database.sql;
source /path/to/database/02_create_tables.sql;
source /path/to/database/03_insert_test_data.sql;
```

### 方法3：使用mysql命令直接执行
```bash
# 执行所有脚本
mysql -u root -p123456 < database/run_all.sql

# 或者逐个执行
mysql -u root -p123456 < database/01_create_database.sql
mysql -u root -p123456 < database/02_create_tables.sql
mysql -u root -p123456 < database/03_insert_test_data.sql
```

## 数据库配置

### 默认环境（localhost）
- 主机：localhost:3306
- 数据库：ai_commerce_order
- 用户名：root
- 密码：123456

### 开发环境（Docker）
- 主机：2025_ai_agent_infrastructure_mysql:3306
- 数据库：ai_commerce_order
- 用户名：root
- 密码：123456

## 表结构说明

### customers（客户表）
- id: 主键，自增
- name: 客户姓名（必填）
- email: 邮箱地址（唯一）
- phone: 手机号码
- address: 地址
- status: 客户状态（ACTIVE/INACTIVE/SUSPENDED）
- created_at/updated_at: 创建/更新时间

### products（商品表）
- id: 主键，自增
- name: 商品名称（必填）
- description: 商品描述
- price: 商品价格（必填）
- sku: 商品SKU（唯一）
- stock_quantity: 库存数量
- status: 商品状态（ACTIVE/INACTIVE/OUT_OF_STOCK）
- category: 商品分类
- created_at/updated_at: 创建/更新时间

### orders（订单表）
- id: 主键，自增
- order_number: 订单号（唯一，必填）
- customer_id: 客户ID（外键）
- status: 订单状态（PENDING/CONFIRMED/SHIPPED/DELIVERED/CANCELLED/REFUNDED）
- total_amount: 订单总金额
- shipping_address: 收货地址
- notes: 订单备注
- created_at/updated_at: 创建/更新时间

### order_items（订单项表）
- id: 主键，自增
- order_id: 订单ID（外键）
- product_id: 商品ID（外键）
- quantity: 购买数量（必填）
- price: 商品单价（必填）
- created_at/updated_at: 创建/更新时间

## 测试数据说明

### 客户数据
- 张三：zhangsan@example.com
- 李四：lisi@example.com
- 王五：wangwu@example.com
- 赵六：zhaoliu@example.com
- 钱七：qianqi@example.com（非活跃状态）

### 商品数据
- iPhone 15 Pro：¥7,999（库存50）
- MacBook Pro 14英寸：¥15,999（库存20）
- AirPods Pro 2：¥1,899（库存100）
- iPad Air 5：¥4,399（库存30）
- Apple Watch Series 9：¥2,999（缺货）
- MacBook Air 13英寸：¥8,999（库存15）
- iPhone 14：¥5,999（库存80）
- iPad Pro 12.9英寸：¥8,999（库存10）

### 订单数据
- 订单1：张三购买2台iPhone 15 Pro（已送达）
- 订单2：李四购买1台iPhone 15 Pro（已发货）
- 订单3：王五购买iPhone 14 + AirPods Pro 2（已确认）
- 订单4：张三购买AirPods Pro 2（待确认）
- 订单5：赵六购买Apple Watch Series 9（已取消）

## 注意事项

1. 执行脚本前请确保MySQL服务已启动
2. 确保有足够的权限创建数据库和表
3. 如果数据库已存在，脚本会跳过创建步骤
4. 测试数据会重复插入，建议先清空表再执行
5. 外键约束确保数据完整性，删除操作会受限
