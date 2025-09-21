-- 使用数据库
USE ai_commerce_order;

-- 插入测试客户数据
INSERT INTO customers (name, email, phone, address, status) VALUES
('张三', 'zhangsan@example.com', '13800138001', '北京市朝阳区建国门外大街1号', 'ACTIVE'),
('李四', 'lisi@example.com', '13800138002', '上海市浦东新区陆家嘴环路1000号', 'ACTIVE'),
('王五', 'wangwu@example.com', '13800138003', '广州市天河区珠江新城花城大道85号', 'ACTIVE'),
('赵六', 'zhaoliu@example.com', '13800138004', '深圳市南山区科技园南区深南大道10000号', 'ACTIVE'),
('钱七', 'qianqi@example.com', '13800138005', '杭州市西湖区文三路259号', 'INACTIVE');

-- 插入测试商品数据
INSERT INTO products (name, description, price, sku, stock_quantity, status, category) VALUES
('iPhone 15 Pro', '苹果最新旗舰手机，搭载A17 Pro芯片，256GB存储', 7999.00, 'IPHONE15PRO-256GB', 50, 'ACTIVE', '手机'),
('MacBook Pro 14英寸', '苹果专业级笔记本电脑，M3 Pro芯片，512GB存储', 15999.00, 'MBP14-M3PRO-512GB', 20, 'ACTIVE', '电脑'),
('AirPods Pro 2', '苹果主动降噪无线耳机，支持空间音频', 1899.00, 'AIRPODS-PRO-2', 100, 'ACTIVE', '耳机'),
('iPad Air 5', '苹果平板电脑，M1芯片，64GB存储', 4399.00, 'IPAD-AIR5-64GB', 30, 'ACTIVE', '平板'),
('Apple Watch Series 9', '苹果智能手表，健康监测，45mm表盘', 2999.00, 'AW-S9-45MM', 0, 'OUT_OF_STOCK', '手表'),
('MacBook Air 13英寸', '苹果轻薄笔记本电脑，M2芯片，256GB存储', 8999.00, 'MBA13-M2-256GB', 15, 'ACTIVE', '电脑'),
('iPhone 14', '苹果智能手机，A15芯片，128GB存储', 5999.00, 'IPHONE14-128GB', 80, 'ACTIVE', '手机'),
('iPad Pro 12.9英寸', '苹果专业平板电脑，M2芯片，256GB存储', 8999.00, 'IPADPRO-129-M2-256GB', 10, 'ACTIVE', '平板');

-- 插入测试订单数据
INSERT INTO orders (order_number, customer_id, status, total_amount, shipping_address, notes) VALUES
('ORD2025092100001', 1, 'DELIVERED', 15998.00, '北京市朝阳区建国门外大街1号', '请尽快发货'),
('ORD2025092100002', 2, 'SHIPPED', 7999.00, '上海市浦东新区陆家嘴环路1000号', '包装要仔细'),
('ORD2025092100003', 3, 'CONFIRMED', 6298.00, '广州市天河区珠江新城花城大道85号', ''),
('ORD2025092100004', 1, 'PENDING', 1899.00, '北京市朝阳区建国门外大街1号', ''),
('ORD2025092100005', 4, 'CANCELLED', 2999.00, '深圳市南山区科技园南区深南大道10000号', '客户取消');

-- 插入测试订单项数据
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
-- 订单1: iPhone 15 Pro x2
(1, 1, 2, 7999.00),
-- 订单2: iPhone 15 Pro x1
(2, 1, 1, 7999.00),
-- 订单3: iPhone 14 x1 + AirPods Pro 2 x1
(3, 7, 1, 5999.00),
(3, 3, 1, 1899.00),
-- 订单4: AirPods Pro 2 x1
(4, 3, 1, 1899.00),
-- 订单5: Apple Watch Series 9 x1 (已取消)
(5, 5, 1, 2999.00);

-- 显示数据插入成功
SELECT 'Test data inserted successfully!' as message;

-- 查询统计信息
SELECT 
    'Customers' as table_name, 
    COUNT(*) as record_count 
FROM customers
UNION ALL
SELECT 
    'Products' as table_name, 
    COUNT(*) as record_count 
FROM products
UNION ALL
SELECT 
    'Orders' as table_name, 
    COUNT(*) as record_count 
FROM orders
UNION ALL
SELECT 
    'Order Items' as table_name, 
    COUNT(*) as record_count 
FROM order_items;
