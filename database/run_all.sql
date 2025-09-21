-- 执行所有数据库初始化脚本
-- 按顺序执行：创建数据库 -> 创建表 -> 插入测试数据

-- 1. 创建数据库
SOURCE 01_create_database.sql;

-- 2. 创建表结构
SOURCE 02_create_tables.sql;

-- 3. 插入测试数据
SOURCE 03_insert_test_data.sql;

-- 显示完成信息
SELECT 'All database initialization scripts executed successfully!' as message;
