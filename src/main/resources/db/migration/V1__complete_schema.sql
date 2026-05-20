-- V1__complete_schema.sql
-- Complete database schema for Zylo Avenue E-commerce Platform
-- Includes all tables, relationships, indexes, and seed data

-- ============================================================================
-- SCHEMA INITIALIZATION
-- ============================================================================

CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(190) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  full_name VARCHAR(190) NULL,
  phone VARCHAR(50) NULL,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP NULL
);

CREATE TABLE roles (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL UNIQUE,
  description TEXT NULL
);

CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE categories (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(120) NOT NULL,
  slug VARCHAR(140) NOT NULL UNIQUE,
  parent_id BIGINT NULL,
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP NULL,
  FOREIGN KEY (parent_id) REFERENCES categories(id),
  INDEX idx_categories_name (name)
);

CREATE TABLE products (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(190) NOT NULL,
  slug VARCHAR(220) NOT NULL UNIQUE,
  description TEXT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP NULL,
  INDEX idx_products_name (name)
);

CREATE TABLE product_images (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id BIGINT NOT NULL,
  url VARCHAR(500) NOT NULL,
  storage_key VARCHAR(500) NULL,
  sort_order INT NOT NULL DEFAULT 0,
  is_primary BOOLEAN NOT NULL DEFAULT FALSE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP NULL,
  FOREIGN KEY (product_id) REFERENCES products(id),
  INDEX idx_product_images_product (product_id)
);

CREATE TABLE product_variants (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id BIGINT NOT NULL,
  sku VARCHAR(80) NOT NULL UNIQUE,
  size VARCHAR(30) NOT NULL,
  color VARCHAR(60) NOT NULL,
  price_cents INT NOT NULL,
  stock_qty INT NOT NULL DEFAULT 0,
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  low_stock_threshold INT NOT NULL DEFAULT 5,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP NULL,
  FOREIGN KEY (product_id) REFERENCES products(id),
  INDEX idx_variants_product_active (product_id, is_active),
  INDEX idx_variants_stock (stock_qty)
);

CREATE TABLE product_category (
  product_id BIGINT NOT NULL,
  category_id BIGINT NOT NULL,
  PRIMARY KEY (product_id, category_id),
  FOREIGN KEY (product_id) REFERENCES products(id),
  FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE orders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_number VARCHAR(40) NOT NULL UNIQUE,
  user_id BIGINT NULL,
  customer_email VARCHAR(190) NOT NULL,
  customer_name VARCHAR(190) NULL,
  customer_phone VARCHAR(50) NULL,
  shipping_address1 VARCHAR(190) NOT NULL,
  shipping_city VARCHAR(120) NOT NULL,
  shipping_state VARCHAR(120) NOT NULL,
  shipping_postal VARCHAR(30) NOT NULL,
  shipping_country VARCHAR(80) NOT NULL DEFAULT 'IN',
  status VARCHAR(30) NOT NULL DEFAULT 'PLACED',
  payment_status VARCHAR(30) NOT NULL DEFAULT 'UNPAID',
  subtotal_cents INT NOT NULL,
  shipping_cents INT NOT NULL DEFAULT 0,
  total_cents INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  INDEX idx_orders_created_at (created_at),
  INDEX idx_orders_status (status),
  INDEX idx_orders_payment_status (payment_status),
  INDEX idx_orders_customer_email (customer_email)
);

CREATE TABLE order_items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  variant_id BIGINT NOT NULL,
  product_name_snapshot VARCHAR(190) NOT NULL,
  sku_snapshot VARCHAR(80) NOT NULL,
  size_snapshot VARCHAR(30) NOT NULL,
  color_snapshot VARCHAR(60) NOT NULL,
  unit_price_cents INT NOT NULL,
  qty INT NOT NULL,
  line_total_cents INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (product_id) REFERENCES products(id),
  FOREIGN KEY (variant_id) REFERENCES product_variants(id),
  INDEX idx_order_items_order (order_id)
);

CREATE TABLE payments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  provider VARCHAR(40) NULL,
  status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
  amount_cents INT NOT NULL,
  currency VARCHAR(10) NOT NULL DEFAULT 'INR',
  gateway_transaction_ref VARCHAR(120) NULL,
  gateway_payment_id VARCHAR(120) NULL,
  gateway_raw_response JSON NULL,
  idempotency_key VARCHAR(80) NULL,
  method VARCHAR(40) NULL,
  failure_code VARCHAR(80) NULL,
  failure_message VARCHAR(255) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_payments_order (order_id),
  FOREIGN KEY (order_id) REFERENCES orders(id),
  UNIQUE KEY uk_payments_idempotency (idempotency_key),
  INDEX idx_payments_status (status),
  INDEX idx_payments_gateway_ref (gateway_transaction_ref)
);

CREATE TABLE inventory_movements (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  variant_id BIGINT NOT NULL,
  movement_type VARCHAR(30) NOT NULL,
  qty_delta INT NOT NULL,
  reason VARCHAR(190) NULL,
  reference_order_id BIGINT NULL,
  actor_user_id BIGINT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (variant_id) REFERENCES product_variants(id),
  FOREIGN KEY (reference_order_id) REFERENCES orders(id),
  FOREIGN KEY (actor_user_id) REFERENCES users(id),
  INDEX idx_inventory_movements_variant_created (variant_id, created_at)
);

CREATE TABLE addresses (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  label VARCHAR(60) NULL,
  full_name VARCHAR(190) NULL,
  phone VARCHAR(50) NULL,
  address1 VARCHAR(190) NOT NULL,
  address2 VARCHAR(190) NULL,
  city VARCHAR(120) NOT NULL,
  state VARCHAR(120) NOT NULL,
  postal VARCHAR(30) NOT NULL,
  country VARCHAR(80) NOT NULL DEFAULT 'IN',
  is_default BOOLEAN NOT NULL DEFAULT FALSE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP NULL,
  FOREIGN KEY (user_id) REFERENCES users(id),
  INDEX idx_addresses_user (user_id)
);

CREATE TABLE order_status_history (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  from_status VARCHAR(30) NULL,
  to_status VARCHAR(30) NOT NULL,
  note VARCHAR(255) NULL,
  actor_user_id BIGINT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (actor_user_id) REFERENCES users(id),
  INDEX idx_osh_order (order_id),
  INDEX idx_osh_created (created_at)
);

CREATE TABLE carts (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NULL,
  guest_token VARCHAR(64) NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_carts_guest_token (guest_token),
  FOREIGN KEY (user_id) REFERENCES users(id),
  INDEX idx_carts_user (user_id)
);

CREATE TABLE cart_items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  cart_id BIGINT NOT NULL,
  variant_id BIGINT NOT NULL,
  qty INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_cart_variant (cart_id, variant_id),
  FOREIGN KEY (cart_id) REFERENCES carts(id),
  FOREIGN KEY (variant_id) REFERENCES product_variants(id),
  INDEX idx_cart_items_cart (cart_id)
);

CREATE TABLE audit_logs (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  actor_user_id BIGINT NULL,
  action VARCHAR(80) NOT NULL,
  entity_type VARCHAR(80) NULL,
  entity_id VARCHAR(80) NULL,
  payload JSON NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (actor_user_id) REFERENCES users(id),
  INDEX idx_audit_logs_created (created_at),
  INDEX idx_audit_logs_actor (actor_user_id)
);

-- ============================================================================
-- SEED DATA
-- ============================================================================

-- Roles
INSERT INTO roles (id, name) VALUES
  (1, 'ADMIN'),
  (2, 'STAFF'),
  (3, 'CUSTOMER');

-- Categories
INSERT INTO categories (id, name, slug, parent_id, is_active) VALUES
  (1, 'Tees', 'tees', NULL, TRUE),
  (2, 'Hoodies', 'hoodies', NULL, TRUE),
  (3, 'Jackets', 'jackets', NULL, TRUE);

-- Products
INSERT INTO products (id, name, slug, description, status) VALUES
  (1, 'ZYLO AVENUE Razor Tee', 'zylo-avenue-razor-tee', 'Premium heavy cotton tee. Sharp silhouette. Built for the street.', 'ACTIVE'),
  (2, 'ZYLO AVENUE Night Ops Hoodie', 'zylo-avenue-night-ops-hoodie', 'Oversized hoodie with aggressive minimal branding. Dark, bold, premium.', 'ACTIVE');

-- Product images
INSERT INTO product_images (id, product_id, url, storage_key, sort_order, is_primary) VALUES
  (1, 1, 'http://localhost:8080/assets/sample/razor-tee-1.jpg', NULL, 0, TRUE),
  (2, 1, 'http://localhost:8080/assets/sample/razor-tee-2.jpg', NULL, 1, FALSE),
  (3, 2, 'http://localhost:8080/assets/sample/night-ops-hoodie-1.jpg', NULL, 0, TRUE);

-- Product variants
INSERT INTO product_variants (id, product_id, sku, size, color, price_cents, stock_qty, is_active) VALUES
  (1, 1, 'ZYLO-TEE-RAZOR-BLK-S', 'S', 'Black', 199900, 15, TRUE),
  (2, 1, 'ZYLO-TEE-RAZOR-BLK-M', 'M', 'Black', 199900, 10, TRUE),
  (3, 1, 'ZYLO-TEE-RAZOR-WHT-M', 'M', 'White', 189900, 0, TRUE),
  (4, 2, 'ZYLO-HOOD-NOPS-BLK-M', 'M', 'Black', 349900, 8, TRUE),
  (5, 2, 'ZYLO-HOOD-NOPS-BLK-L', 'L', 'Black', 349900, 4, TRUE);

-- Product-category mapping
INSERT INTO product_category (product_id, category_id) VALUES
  (1, 1),
  (2, 2);
