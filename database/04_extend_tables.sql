USE ai_commerce_order;

-- 修改商品表，添加前端需要的字段
ALTER TABLE products
ADD COLUMN original_price DECIMAL(10,2) NULL COMMENT '原价',
ADD COLUMN rating DECIMAL(3,2) NULL COMMENT '评分',
ADD COLUMN review_count INT NULL DEFAULT 0 COMMENT '评论数量',
ADD COLUMN image_url VARCHAR(255) NULL COMMENT '图片URL',
ADD COLUMN tags VARCHAR(255) NULL COMMENT '标签，用逗号分隔',
ADD COLUMN is_new BOOLEAN NULL DEFAULT FALSE COMMENT '是否新品',
ADD COLUMN is_bestseller BOOLEAN NULL DEFAULT FALSE COMMENT '是否畅销';

-- 创建分类表
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    icon VARCHAR(50) NULL COMMENT '图标类名',
    image_url VARCHAR(255) NULL COMMENT '图片URL',
    count INT NOT NULL DEFAULT 0 COMMENT '商品数量',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';

-- 创建用户评价表
CREATE TABLE IF NOT EXISTS testimonials (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '用户姓名',
    avatar VARCHAR(255) NULL COMMENT '用户头像URL',
    rating INT NOT NULL COMMENT '评分(1-5)',
    comment TEXT NOT NULL COMMENT '评价内容',
    product VARCHAR(200) NULL COMMENT '关联产品',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_name (name),
    INDEX idx_product (product)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户评价表';

-- 显示表扩展成功
SELECT 'Tables extended successfully!' as message;