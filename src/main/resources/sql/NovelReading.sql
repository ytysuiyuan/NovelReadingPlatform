# CREATE DATABASE novel;
USE novel;

-- 删除表（如果存在），按依赖关系从最末尾开始删除
DROP TABLE IF EXISTS participant;
DROP TABLE IF EXISTS discussion;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS rating;
DROP TABLE IF EXISTS favorite;
DROP TABLE IF EXISTS reading_history;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS account_balance;
DROP TABLE IF EXISTS novel;
DROP TABLE IF EXISTS user;


-- 创建用户表
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 用户ID，主键
    username VARCHAR(255) NOT NULL, -- 用户名
    password VARCHAR(255) NOT NULL, -- 密码
    nickname VARCHAR(255) NOT NULL, -- 昵称
    salt VARCHAR(255) NOT NULL, -- 盐值
    avatar VARCHAR(255), -- 用户头像的URL
    role INT NOT NULL DEFAULT 0, -- 角色，0表示普通用户，1表示管理员
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
);


-- 创建小说表
CREATE TABLE novel (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 小说ID，主键
    title VARCHAR(255) NOT NULL UNIQUE, -- 小说标题，唯一约束
    author_id INT NOT NULL, -- 作者ID，外键
    genre VARCHAR(255) NOT NULL, -- 小说类型
    content TEXT NOT NULL, -- 小说内容
    price DECIMAL(10, 2) NOT NULL DEFAULT 0.00, -- 小说的观看费用
    cover VARCHAR(255), -- 小说封面URL或文件路径
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新时间
    FOREIGN KEY (author_id) REFERENCES user(id) -- 关联作者ID
);



-- 创建阅读历史表
CREATE TABLE reading_history (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 阅读历史ID，主键
    user_id INT NOT NULL, -- 用户ID，外键
    novel_id INT NOT NULL, -- 小说ID，外键
    last_read_page INT DEFAULT 1, -- 最后阅读页码
    last_read_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 最后阅读时间
    FOREIGN KEY (user_id) REFERENCES user(id), -- 关联用户ID
    FOREIGN KEY (novel_id) REFERENCES novel(id) -- 关联小说ID
);

-- 创建评分表
CREATE TABLE rating (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 评分ID，主键
    user_id INT NOT NULL, -- 用户ID，外键
    novel_id INT NOT NULL, -- 小说ID，外键
    rating INT CHECK (rating >= 1 AND rating <= 5), -- 评分，范围为1到5
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 评分时间
    FOREIGN KEY (user_id) REFERENCES user(id), -- 关联用户ID
    FOREIGN KEY (novel_id) REFERENCES novel(id) -- 关联小说ID
);

-- 创建收藏表
CREATE TABLE favorite (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 收藏ID，主键
    user_id INT NOT NULL, -- 用户ID，外键
    novel_id INT NOT NULL, -- 小说ID，外键
    collected_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 收藏时间
    FOREIGN KEY (user_id) REFERENCES user(id), -- 关联用户ID
    FOREIGN KEY (novel_id) REFERENCES novel(id), -- 关联小说ID
    UNIQUE (user_id, novel_id) -- 添加唯一约束，防止同一用户重复收藏同一本小说
);


-- 创建讨论表
CREATE TABLE discussion (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 讨论ID，主键
    novel_id INT NOT NULL, -- 小说ID，外键
    user_id INT NOT NULL, -- 用户ID，外键
    content TEXT NOT NULL, -- 讨论内容
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 讨论时间
    FOREIGN KEY (novel_id) REFERENCES novel(id), -- 关联小说ID
    FOREIGN KEY (user_id) REFERENCES user(id) -- 关联用户ID
);

-- 创建参与表
CREATE TABLE participant (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 参与ID，主键
    discussion_id INT NOT NULL, -- 讨论ID，外键
    user_id INT NOT NULL, -- 用户ID，外键
    content TEXT NOT NULL, -- 参与内容
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 加入时间
    FOREIGN KEY (discussion_id) REFERENCES discussion(id), -- 关联讨论ID
    FOREIGN KEY (user_id) REFERENCES user(id) -- 关联用户ID
);

-- 创建评论表
CREATE TABLE comment (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 评论ID，主键
    user_id INT NOT NULL, -- 用户ID，外键
    novel_id INT NOT NULL, -- 小说ID，外键
    content TEXT NOT NULL, -- 评论内容
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 评论时间
    FOREIGN KEY (user_id) REFERENCES user(id), -- 关联用户ID
    FOREIGN KEY (novel_id) REFERENCES novel(id) -- 关联小说ID
);

-- 创建付费表
CREATE TABLE payment (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 付费ID，主键
    user_id INT NOT NULL, -- 用户ID，外键
    novel_id INT NOT NULL, -- 小说ID，外键
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 付费时间
    FOREIGN KEY (user_id) REFERENCES user(id), -- 关联用户ID
    FOREIGN KEY (novel_id) REFERENCES novel(id) -- 关联小说ID
);

-- 创建账户余额表
CREATE TABLE account_balance (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 账户ID，主键
    user_id INT NOT NULL, -- 用户ID，外键
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00, -- 账户余额
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 最后更新时间
    FOREIGN KEY (user_id) REFERENCES user(id) -- 关联用户ID
);

