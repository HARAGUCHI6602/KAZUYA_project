-- ============================================
-- ProductManage 用スキーマ & 初期データ（課題19）
-- 再実行しても同じ状態になるように作成
-- ============================================

-- 文字コードなど
SET NAMES utf8mb4;
SET time_zone = '+09:00';

-- データベース作成＆選択
CREATE DATABASE IF NOT EXISTS productmanage
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;
USE productmanage;

-- 依存関係の都合でFKを一時的に無効化して DROP
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
SET FOREIGN_KEY_CHECKS = 1;

-- カテゴリ
CREATE TABLE categories (
  id   INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 商品
CREATE TABLE products (
  id          INT AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(200) NOT NULL,
  price       INT NOT NULL,
  stock       INT NOT NULL,
  category_id INT NOT NULL,
  CONSTRAINT fk_products_categories
    FOREIGN KEY (category_id) REFERENCES categories(id)
      ON UPDATE CASCADE
      ON DELETE RESTRICT,
  CONSTRAINT chk_price_nonneg CHECK (price >= 0),
  CONSTRAINT chk_stock_nonneg CHECK (stock >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -------------------------
-- 初期データ（カテゴリ）
-- -------------------------
INSERT INTO categories (id, name) VALUES
  (1, 'テストA'),
  (2, 'テストB')
ON DUPLICATE KEY UPDATE
  name = VALUES(name);

-- AUTO_INCREMENT を整える（任意）
ALTER TABLE categories AUTO_INCREMENT = 3;

-- -------------------------
-- 初期データ（商品）
-- -------------------------
INSERT INTO products (id, name, price, stock, category_id) VALUES
  (1, 'テスト1', 100, 5, 1),
  (2, 'テスト2', 200, 10, 1)
ON DUPLICATE KEY UPDATE
  name = VALUES(name),
  price = VALUES(price),
  stock = VALUES(stock),
  category_id = VALUES(category_id);

-- AUTO_INCREMENT を整える（任意）
ALTER TABLE products AUTO_INCREMENT = 3;

-- 動作確認用（必要ならコメント解除）
-- SELECT * FROM categories;
-- SELECT * FROM products;

