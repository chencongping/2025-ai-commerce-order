USE ai_commerce_order;

-- 插入分类数据
INSERT INTO categories (name, icon, image_url, count) VALUES
('电子产品', 'fa-mobile-screen', 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Electronic%20gadgets%20smartphones%20laptops%20minimal%20style%20flat%20lay&sign=f9b8e8d72841967274bee5a44e6856b9', 128),
('时尚服饰', 'fa-tshirt', 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Modern%20fashion%20clothing%20minimal%20style%20flat%20lay&sign=74499e14e8c263b7160c9a8541cfd2f9', 256),
('家居生活', 'fa-home', 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Home%20decor%20minimal%20style%20cozy%20modern&sign=82f7532e83b83aa1cd0154c755c0e6a5', 184),
('美妆个护', 'fa-spa', 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Beauty%20products%20cosmetics%20minimal%20style%20flat%20lay&sign=7a1efd3ab66a0b0160af104ca3b03bf2', 92),
('运动户外', 'fa-person-running', 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Sports%20equipment%20minimal%20style%20flat%20lay&sign=4647832ba9fd3debc6e540bed834c38f', 68),
('食品饮料', 'fa-utensils', 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Healthy%20food%20minimal%20style%20flat%20lay&sign=02526ace82756df7c52769682085f9a2', 135);

-- 插入商品数据
INSERT INTO products (name, description, price, original_price, rating, review_count, image_url, category, tags, is_new, is_bestseller, status, stock_quantity)
VALUES
('超薄笔记本电脑', '高性能轻薄笔记本，适合工作和娱乐', 5999.00, 6999.00, 4.8, 245, 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Slim%20laptop%20on%20white%20background%20minimal%20style&sign=cb4509e817c9b72c033b4d6902cbd788', '电子产品', '热销,限时优惠', false, true, 'ACTIVE', 50),
('无线降噪耳机', '主动降噪，高清音质，舒适佩戴体验', 1299.00, NULL, 4.7, 189, 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Wireless%20noise%20cancelling%20headphones%20minimal%20style&sign=dd7dea1736db4e5c3bace5e4f534fad4', '电子产品', '新品,畅销', true, false, 'ACTIVE', 100),
('智能手表', '全面健康监测，多功能运动追踪', 1999.00, 2299.00, 4.6, 156, 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Smartwatch%20on%20white%20background%20minimal%20style&sign=c96148dd8a64f95726fd8590e790271e', '电子产品', '限时优惠', false, false, 'ACTIVE', 75),
('机械键盘', 'RGB背光，青轴机械键盘，游戏办公皆宜', 499.00, NULL, 4.9, 320, 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Mechanical%20keyboard%20RGB%20minimal%20style&sign=97d209f3b6e7ede33c8aeee8b1a9862a', '电子产品', '热销', false, true, 'ACTIVE', 200),
('运动跑鞋', '轻量缓震，透气网面，适合各种运动场景', 799.00, 999.00, 4.7, 178, 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Running%20shoes%20on%20white%20background%20minimal%20style&sign=8e1cb477591e59c54d57830b19dcf83f', '运动户外', '限时优惠', false, false, 'ACTIVE', 150),
('智能台灯', '护眼无频闪，智能调光，触控开关', 299.00, NULL, 4.5, 98, 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Smart%20desk%20lamp%20minimal%20style&sign=1af23e4ffbaffbfcc543660e06ab360c', '家居生活', '新品', true, false, 'ACTIVE', 80),
('便携蓝牙音箱', '防水设计，超长续航，360°环绕音效', 399.00, 499.00, 4.6, 125, 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Portable%20Bluetooth%20speaker%20minimal%20style&sign=a5d646d20042edda2ac061f218ae9c50', '电子产品', '限时优惠', false, false, 'ACTIVE', 60);

-- 插入用户评价数据
INSERT INTO testimonials (name, avatar, rating, comment, product)
VALUES
('张先生', 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Portrait%20of%20middle-aged%20Asian%20man%20smiling%20minimal%20style&sign=3886f6e58a30411c7b500864a7466ea4', 5, '产品质量非常好，包装精美，物流也很快。客服态度很好，有问必答，非常满意的一次购物体验！', '超薄笔记本电脑'),
('李女士', 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Portrait%20of%20young%20Asian%20woman%20smiling%20minimal%20style&sign=a906d134bcffae1518378999031c4104', 5, '使用了一段时间，效果比预期的还要好。性价比很高，推荐给了身边的朋友，大家都很喜欢！', '护肤精华套装'),
('王先生', 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Portrait%20of%20young%20Asian%20man%20smiling%20minimal%20style&sign=067e3c0beb85fd38309acc6753eef16b', 4, '产品整体不错，有一点小瑕疵，但客服很快就给解决了。物流速度很快，包装也很安全。', '智能手表'),
('赵女士', 'https://space.coze.cn/api/coze_space/gen_image?image_size=square&prompt=Portrait%20of%20middle-aged%20Asian%20woman%20smiling%20minimal%20style&sign=f4363e21421dd80f886ff4601de69010', 5, '已经是第三次购买了，质量一直很稳定。客服很专业，推荐的产品很适合我，会一直支持的！', '运动跑鞋');

-- 显示数据插入成功
SELECT 'Frontend test data inserted successfully!' as message;