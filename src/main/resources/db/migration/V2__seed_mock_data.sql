-- V2__seed_mock_data.sql
-- Comprehensive mock data for frontend testing and development
-- Includes users, products, orders, payments, inventory, and more

-- ============================================================================
-- USERS & ADDRESSES
-- ============================================================================

-- Sample users (passwords are hashed but for demo, using bcrypt hashes)
-- Note: These are example hashes. In production, use real bcrypt hashed passwords
INSERT INTO users (id, email, full_name, phone, password_hash, enabled) VALUES
  (1, 'admin@zyloavenue.com', 'Admin User', '+91-9876543210', '$2a$10$Yd0zFR0Zd0zFR0Zd0zFR0zFR0Zd0zFR0zFR0zFR0zFR0zFR0zFR0zFR0', TRUE),
  (2, 'john.doe@example.com', 'John Doe', '+91-9876543211', '$2a$10$Yd0zFR0Zd0zFR0Zd0zFR0zFR0Zd0zFR0zFR0zFR0zFR0zFR0zFR0zFR0', TRUE),
  (3, 'jane.smith@example.com', 'Jane Smith', '+91-9876543212', '$2a$10$Yd0zFR0Zd0zFR0Zd0zFR0zFR0Zd0zFR0zFR0zFR0zFR0zFR0zFR0zFR0', TRUE),
  (4, 'mike.wilson@example.com', 'Mike Wilson', '+91-9876543213', '$2a$10$Yd0zFR0Zd0zFR0Zd0zFR0zFR0Zd0zFR0zFR0zFR0zFR0zFR0zFR0zFR0', TRUE),
  (5, 'sarah.johnson@example.com', 'Sarah Johnson', '+91-9876543214', '$2a$10$Yd0zFR0Zd0zFR0Zd0zFR0zFR0Zd0zFR0zFR0zFR0zFR0zFR0zFR0zFR0', TRUE),
  (6, 'alex.kumar@example.com', 'Alex Kumar', '+91-9876543215', '$2a$10$Yd0zFR0Zd0zFR0Zd0zFR0zFR0Zd0zFR0zFR0zFR0zFR0zFR0zFR0zFR0', TRUE),
  (7, 'priya.sharma@example.com', 'Priya Sharma', '+91-9876543216', '$2a$10$Yd0zFR0Zd0zFR0Zd0zFR0zFR0Zd0zFR0zFR0zFR0zFR0zFR0zFR0zFR0', TRUE),
  (8, 'rahul.patel@example.com', 'Rahul Patel', '+91-9876543217', '$2a$10$Yd0zFR0Zd0zFR0Zd0zFR0zFR0Zd0zFR0zFR0zFR0zFR0zFR0zFR0zFR0', TRUE);

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES
  (1, 1), -- admin@zyloavenue.com = ADMIN
  (2, 3), -- john.doe = CUSTOMER
  (3, 3), -- jane.smith = CUSTOMER
  (4, 2), -- mike.wilson = STAFF
  (5, 3), -- sarah.johnson = CUSTOMER
  (6, 3), -- alex.kumar = CUSTOMER
  (7, 3), -- priya.sharma = CUSTOMER
  (8, 3); -- rahul.patel = CUSTOMER

-- Addresses for users
INSERT INTO addresses (id, user_id, label, full_name, phone, address1, address2, city, state, postal, country, is_default) VALUES
  (1, 2, 'Home', 'John Doe', '+91-9876543211', '123 Main Street', 'Apartment 4B', 'New York', 'NY', '10001', 'IN', TRUE),
  (2, 2, 'Work', 'John Doe', '+91-9876543211', '456 Business Ave', 'Suite 200', 'New York', 'NY', '10002', 'IN', FALSE),
  (3, 3, 'Home', 'Jane Smith', '+91-9876543212', '789 Oak Road', NULL, 'Los Angeles', 'CA', '90001', 'IN', TRUE),
  (4, 5, 'Home', 'Sarah Johnson', '+91-9876543214', '321 Elm Street', 'Floor 5', 'Chicago', 'IL', '60601', 'IN', TRUE),
  (5, 5, 'Office', 'Sarah Johnson', '+91-9876543214', '654 Tech Park', 'Building C', 'Chicago', 'IL', '60602', 'IN', FALSE),
  (6, 6, 'Home', 'Alex Kumar', '+91-9876543215', '111 Desert Lane', NULL, 'Phoenix', 'AZ', '85001', 'IN', TRUE),
  (7, 7, 'Home', 'Priya Sharma', '+91-9876543216', '222 Maple Drive', 'Apt 10', 'Houston', 'TX', '77001', 'IN', TRUE),
  (8, 8, 'Home', 'Rahul Patel', '+91-9876543217', '333 Pine Street', NULL, 'Miami', 'FL', '33101', 'IN', TRUE);

-- ============================================================================
-- PRODUCTS & VARIANTS (EXPAND EXISTING DATA)
-- ============================================================================

-- Add more products
INSERT INTO products (id, name, slug, description, status) VALUES
  (3, 'ZYLO AVENUE Classic Cap', 'zylo-avenue-classic-cap', 'Structured 6-panel cap with embroidered logo. Adjustable fit. Timeless streetwear essential.', 'ACTIVE'),
  (4, 'ZYLO AVENUE Cargo Shorts', 'zylo-avenue-cargo-shorts', 'Premium cotton cargo shorts with utility pockets. Comfortable and functional for street style.', 'ACTIVE'),
  (5, 'ZYLO AVENUE Track Pants', 'zylo-avenue-track-pants', 'Tapered track pants with gold stripe detail. Perfect for casual layering.', 'ACTIVE'),
  (6, 'ZYLO AVENUE Bomber Jacket', 'zylo-avenue-bomber-jacket', 'Oversized bomber with satin lining. Bold silhouette with subtle branding.', 'ACTIVE'),
  (7, 'ZYLO AVENUE Beanie', 'zylo-avenue-beanie', 'Chunky knit beanie in premium wool blend. Winter essential with attitude.', 'ACTIVE'),
  (8, 'ZYLO AVENUE Logo Tee', 'zylo-avenue-logo-tee', 'Classic logo tee printed on premium cotton. Versatile foundation piece.', 'ACTIVE');

-- Add product images for new products
INSERT INTO product_images (id, product_id, url, storage_key, sort_order, is_primary) VALUES
  (4, 3, 'http://localhost:8080/assets/sample/classic-cap-1.jpg', NULL, 0, TRUE),
  (5, 3, 'http://localhost:8080/assets/sample/classic-cap-2.jpg', NULL, 1, FALSE),
  (6, 4, 'http://localhost:8080/assets/sample/cargo-shorts-1.jpg', NULL, 0, TRUE),
  (7, 4, 'http://localhost:8080/assets/sample/cargo-shorts-2.jpg', NULL, 1, FALSE),
  (8, 5, 'http://localhost:8080/assets/sample/track-pants-1.jpg', NULL, 0, TRUE),
  (9, 5, 'http://localhost:8080/assets/sample/track-pants-2.jpg', NULL, 1, FALSE),
  (10, 6, 'http://localhost:8080/assets/sample/bomber-jacket-1.jpg', NULL, 0, TRUE),
  (11, 6, 'http://localhost:8080/assets/sample/bomber-jacket-2.jpg', NULL, 1, FALSE),
  (12, 7, 'http://localhost:8080/assets/sample/beanie-1.jpg', NULL, 0, TRUE),
  (13, 8, 'http://localhost:8080/assets/sample/logo-tee-1.jpg', NULL, 0, TRUE),
  (14, 8, 'http://localhost:8080/assets/sample/logo-tee-2.jpg', NULL, 1, FALSE);

-- Add variants for new products
INSERT INTO product_variants (id, product_id, sku, size, color, price_cents, stock_qty, is_active) VALUES
  (6, 3, 'ZYLO-CAP-CLASSIC-BLK', 'ONE', 'Black', 89900, 20, TRUE),
  (7, 3, 'ZYLO-CAP-CLASSIC-WHT', 'ONE', 'White', 89900, 15, TRUE),
  (8, 4, 'ZYLO-SHORT-CARGO-BLK-S', 'S', 'Black', 149900, 12, TRUE),
  (9, 4, 'ZYLO-SHORT-CARGO-BLK-M', 'M', 'Black', 149900, 18, TRUE),
  (10, 4, 'ZYLO-SHORT-CARGO-BLK-L', 'L', 'Black', 149900, 10, TRUE),
  (11, 5, 'ZYLO-PANT-TRACK-BLK-S', 'S', 'Black', 249900, 22, TRUE),
  (12, 5, 'ZYLO-PANT-TRACK-BLK-M', 'M', 'Black', 249900, 25, TRUE),
  (13, 5, 'ZYLO-PANT-TRACK-BLK-L', 'L', 'Black', 249900, 8, TRUE),
  (14, 6, 'ZYLO-BOMB-JACKET-BLK-M', 'M', 'Black', 449900, 6, TRUE),
  (15, 6, 'ZYLO-BOMB-JACKET-BLK-L', 'L', 'Black', 449900, 5, TRUE),
  (16, 7, 'ZYLO-BEANIE-KNIT-BLK', 'ONE', 'Black', 79900, 30, TRUE),
  (17, 7, 'ZYLO-BEANIE-KNIT-GRY', 'ONE', 'Gray', 79900, 25, TRUE),
  (18, 8, 'ZYLO-LOGTEE-CLASSIC-BLK-S', 'S', 'Black', 139900, 40, TRUE),
  (19, 8, 'ZYLO-LOGTEE-CLASSIC-BLK-M', 'M', 'Black', 139900, 50, TRUE),
  (20, 8, 'ZYLO-LOGTEE-CLASSIC-BLK-L', 'L', 'Black', 139900, 35, TRUE),
  (21, 8, 'ZYLO-LOGTEE-CLASSIC-WHT-M', 'M', 'White', 139900, 28, TRUE);

-- Update product-category mapping
INSERT INTO product_category (product_id, category_id) VALUES
  (3, 1), -- Cap in Tees
  (4, 1), -- Shorts in Tees
  (5, 1), -- Pants in Tees
  (6, 3), -- Bomber in Jackets
  (7, 1), -- Beanie in Tees
  (8, 1); -- Logo Tee in Tees

-- ============================================================================
-- ORDERS & ORDER ITEMS
-- ============================================================================

-- Order 1: John Doe - Completed order
INSERT INTO orders (id, order_number, user_id, customer_email, customer_name, customer_phone, 
                    shipping_address1, shipping_city, shipping_state, shipping_postal, shipping_country, 
                    status, payment_status, subtotal_cents, shipping_cents, total_cents)
VALUES (1, 'ORD-20240515001', 2, 'john.doe@example.com', 'John Doe', '+91-9876543211',
        '123 Main Street, Apartment 4B', 'New York', 'NY', '10001', 'IN',
        'DELIVERED', 'PAID', 569800, 5000, 574800);

INSERT INTO order_items (id, order_id, product_id, variant_id, product_name_snapshot, sku_snapshot,
                         size_snapshot, color_snapshot, unit_price_cents, qty, line_total_cents)
VALUES
  (1, 1, 1, 2, 'ZYLO AVENUE Razor Tee', 'ZYLO-TEE-RAZOR-BLK-M', 'M', 'Black', 199900, 2, 399800),
  (2, 1, 8, 19, 'ZYLO AVENUE Logo Tee', 'ZYLO-LOGTEE-CLASSIC-BLK-M', 'M', 'Black', 139900, 1, 139900),
  (3, 1, 3, 6, 'ZYLO AVENUE Classic Cap', 'ZYLO-CAP-CLASSIC-BLK', 'ONE', 'Black', 89900, 1, 89900);

-- Order 2: Jane Smith - Processing order
INSERT INTO orders (id, order_number, user_id, customer_email, customer_name, customer_phone,
                    shipping_address1, shipping_city, shipping_state, shipping_postal, shipping_country,
                    status, payment_status, subtotal_cents, shipping_cents, total_cents)
VALUES (2, 'ORD-20240515002', 3, 'jane.smith@example.com', 'Jane Smith', '+91-9876543212',
        '789 Oak Road', 'Los Angeles', 'CA', '90001', 'IN',
        'CONFIRMED', 'PAID', 699800, 5000, 704800);

INSERT INTO order_items (id, order_id, product_id, variant_id, product_name_snapshot, sku_snapshot,
                         size_snapshot, color_snapshot, unit_price_cents, qty, line_total_cents)
VALUES
  (4, 2, 2, 4, 'ZYLO AVENUE Night Ops Hoodie', 'ZYLO-HOOD-NOPS-BLK-M', 'M', 'Black', 349900, 2, 699800);

-- Order 3: Sarah Johnson - Pending order
INSERT INTO orders (id, order_number, user_id, customer_email, customer_name, customer_phone,
                    shipping_address1, shipping_city, shipping_state, shipping_postal, shipping_country,
                    status, payment_status, subtotal_cents, shipping_cents, total_cents)
VALUES (3, 'ORD-20240515003', 5, 'sarah.johnson@example.com', 'Sarah Johnson', '+91-9876543214',
        '321 Elm Street, Floor 5', 'Chicago', 'IL', '60601', 'IN',
        'PLACED', 'UNPAID', 1349700, 5000, 1354700);

INSERT INTO order_items (id, order_id, product_id, variant_id, product_name_snapshot, sku_snapshot,
                         size_snapshot, color_snapshot, unit_price_cents, qty, line_total_cents)
VALUES
  (5, 3, 5, 12, 'ZYLO AVENUE Track Pants', 'ZYLO-PANT-TRACK-BLK-M', 'M', 'Black', 249900, 2, 499800),
  (6, 3, 6, 14, 'ZYLO AVENUE Bomber Jacket', 'ZYLO-BOMB-JACKET-BLK-M', 'M', 'Black', 449900, 1, 449900),
  (7, 3, 7, 16, 'ZYLO AVENUE Beanie', 'ZYLO-BEANIE-KNIT-BLK', 'ONE', 'Black', 79900, 1, 79900);

-- Order 4: Alex Kumar - Shipped order
INSERT INTO orders (id, order_number, user_id, customer_email, customer_name, customer_phone,
                    shipping_address1, shipping_city, shipping_state, shipping_postal, shipping_country,
                    status, payment_status, subtotal_cents, shipping_cents, total_cents)
VALUES (4, 'ORD-20240515004', 6, 'alex.kumar@example.com', 'Alex Kumar', '+91-9876543215',
        '111 Desert Lane', 'Phoenix', 'AZ', '85001', 'IN',
        'SHIPPED', 'PAID', 279900, 5000, 284900);

INSERT INTO order_items (id, order_id, product_id, variant_id, product_name_snapshot, sku_snapshot,
                         size_snapshot, color_snapshot, unit_price_cents, qty, line_total_cents)
VALUES
  (8, 4, 1, 1, 'ZYLO AVENUE Razor Tee', 'ZYLO-TEE-RAZOR-BLK-S', 'S', 'Black', 199900, 1, 199900),
  (9, 4, 3, 7, 'ZYLO AVENUE Classic Cap', 'ZYLO-CAP-CLASSIC-WHT', 'ONE', 'White', 89900, 1, 89900);

-- Order 5: Priya Sharma - Guest checkout
INSERT INTO orders (id, order_number, user_id, customer_email, customer_name, customer_phone,
                    shipping_address1, shipping_city, shipping_state, shipping_postal, shipping_country,
                    status, payment_status, subtotal_cents, shipping_cents, total_cents)
VALUES (5, 'ORD-20240515005', NULL, 'priya.sharma@example.com', 'Priya Sharma', '+91-9876543216',
        '222 Maple Drive, Apt 10', 'Houston', 'TX', '77001', 'IN',
        'PLACED', 'UNPAID', 349900, 5000, 354900);

INSERT INTO order_items (id, order_id, product_id, variant_id, product_name_snapshot, sku_snapshot,
                         size_snapshot, color_snapshot, unit_price_cents, qty, line_total_cents)
VALUES
  (10, 5, 2, 5, 'ZYLO AVENUE Night Ops Hoodie', 'ZYLO-HOOD-NOPS-BLK-L', 'L', 'Black', 349900, 1, 349900);

-- Order 6: Rahul Patel - Delivered
INSERT INTO orders (id, order_number, user_id, customer_email, customer_name, customer_phone,
                    shipping_address1, shipping_city, shipping_state, shipping_postal, shipping_country,
                    status, payment_status, subtotal_cents, shipping_cents, total_cents)
VALUES (6, 'ORD-20240515006', 8, 'rahul.patel@example.com', 'Rahul Patel', '+91-9876543217',
        '333 Pine Street', 'Miami', 'FL', '33101', 'IN',
        'DELIVERED', 'PAID', 799700, 5000, 804700);

INSERT INTO order_items (id, order_id, product_id, variant_id, product_name_snapshot, sku_snapshot,
                         size_snapshot, color_snapshot, unit_price_cents, qty, line_total_cents)
VALUES
  (11, 6, 4, 8, 'ZYLO AVENUE Cargo Shorts', 'ZYLO-SHORT-CARGO-BLK-S', 'S', 'Black', 149900, 2, 299800),
  (12, 6, 8, 18, 'ZYLO AVENUE Logo Tee', 'ZYLO-LOGTEE-CLASSIC-BLK-S', 'S', 'Black', 139900, 2, 279900),
  (13, 6, 7, 17, 'ZYLO AVENUE Beanie', 'ZYLO-BEANIE-KNIT-GRY', 'ONE', 'Gray', 79900, 1, 79900);

-- ============================================================================
-- PAYMENTS
-- ============================================================================

-- Payment for Order 1 (John Doe) - Completed
INSERT INTO payments (id, order_id, provider, status, amount_cents, currency, gateway_transaction_ref, gateway_payment_id)
VALUES (1, 1, 'STRIPE', 'COMPLETED', 574800, 'INR', 'txn_1A1A1A1A1A1A1A1A', 'ch_1A1A1A1A1A1A1A1A');

-- Payment for Order 2 (Jane Smith) - Completed
INSERT INTO payments (id, order_id, provider, status, amount_cents, currency, gateway_transaction_ref, gateway_payment_id)
VALUES (2, 2, 'RAZORPAY', 'COMPLETED', 704800, 'INR', 'txn_2B2B2B2B2B2B2B2B', 'pay_2B2B2B2B2B2B2B2B');

-- Payment for Order 3 (Sarah Johnson) - Pending
INSERT INTO payments (id, order_id, provider, status, amount_cents, currency, gateway_transaction_ref, gateway_payment_id)
VALUES (3, 3, 'STRIPE', 'PENDING', 1354700, 'INR', NULL, NULL);

-- Payment for Order 4 (Alex Kumar) - Completed
INSERT INTO payments (id, order_id, provider, status, amount_cents, currency, gateway_transaction_ref, gateway_payment_id)
VALUES (4, 4, 'RAZORPAY', 'COMPLETED', 284900, 'INR', 'txn_4D4D4D4D4D4D4D4D', 'pay_4D4D4D4D4D4D4D4D');

-- Payment for Order 5 (Priya Sharma) - Pending
INSERT INTO payments (id, order_id, provider, status, amount_cents, currency, gateway_transaction_ref, gateway_payment_id)
VALUES (5, 5, 'STRIPE', 'PENDING', 354900, 'INR', NULL, NULL);

-- Payment for Order 6 (Rahul Patel) - Completed
INSERT INTO payments (id, order_id, provider, status, amount_cents, currency, gateway_transaction_ref, gateway_payment_id)
VALUES (6, 6, 'RAZORPAY', 'COMPLETED', 804700, 'INR', 'txn_6F6F6F6F6F6F6F6F', 'pay_6F6F6F6F6F6F6F6F');

-- ============================================================================
-- INVENTORY MOVEMENTS
-- ============================================================================

-- Initial stock movements from restock
INSERT INTO inventory_movements (id, variant_id, movement_type, qty_delta, reason, actor_user_id)
VALUES
  (1, 1, 'INBOUND', 50, 'Initial stock receive from supplier', 1),
  (2, 2, 'INBOUND', 40, 'Initial stock receive from supplier', 1),
  (3, 3, 'INBOUND', 20, 'Initial stock receive from supplier', 1),
  (4, 4, 'INBOUND', 30, 'Initial stock receive from supplier', 1),
  (5, 5, 'INBOUND', 15, 'Initial stock receive from supplier', 1),
  (6, 6, 'INBOUND', 60, 'Initial stock receive from supplier', 1),
  (7, 7, 'INBOUND', 50, 'Initial stock receive from supplier', 1);

-- Order fulfillment movements
INSERT INTO inventory_movements (id, variant_id, movement_type, qty_delta, reason, reference_order_id, actor_user_id)
VALUES
  (8, 2, 'OUTBOUND', -2, 'Order fulfillment', 1, 4),
  (9, 19, 'OUTBOUND', -1, 'Order fulfillment', 1, 4),
  (10, 6, 'OUTBOUND', -1, 'Order fulfillment', 1, 4),
  (11, 4, 'OUTBOUND', -2, 'Order fulfillment', 2, 4),
  (12, 12, 'OUTBOUND', -2, 'Order fulfillment', 4, 4),
  (13, 7, 'OUTBOUND', -1, 'Order fulfillment', 4, 4),
  (14, 8, 'OUTBOUND', -2, 'Order fulfillment', 6, 4),
  (15, 18, 'OUTBOUND', -2, 'Order fulfillment', 6, 4),
  (16, 17, 'OUTBOUND', -1, 'Order fulfillment', 6, 4);

-- Adjustment for damaged goods
INSERT INTO inventory_movements (id, variant_id, movement_type, qty_delta, reason, actor_user_id)
VALUES
  (17, 3, 'ADJUSTMENT', -5, 'Damaged stock adjustment', 1),
  (18, 5, 'ADJUSTMENT', -2, 'Damaged stock adjustment', 1);

-- ============================================================================
-- ORDER STATUS HISTORY
-- ============================================================================

-- Order 1 status history
INSERT INTO order_status_history (id, order_id, from_status, to_status, note, actor_user_id)
VALUES
  (1, 1, NULL, 'PLACED', 'Order placed by customer', NULL),
  (2, 1, 'PLACED', 'CONFIRMED', 'Payment confirmed', 4),
  (3, 1, 'CONFIRMED', 'SHIPPED', 'Order shipped via courier', 4),
  (4, 1, 'SHIPPED', 'DELIVERED', 'Order delivered to customer', 4);

-- Order 2 status history
INSERT INTO order_status_history (id, order_id, from_status, to_status, note, actor_user_id)
VALUES
  (5, 2, NULL, 'PLACED', 'Order placed by customer', NULL),
  (6, 2, 'PLACED', 'CONFIRMED', 'Payment confirmed', 4);

-- Order 3 status history
INSERT INTO order_status_history (id, order_id, from_status, to_status, note, actor_user_id)
VALUES
  (7, 3, NULL, 'PLACED', 'Order placed by customer', NULL);

-- Order 4 status history
INSERT INTO order_status_history (id, order_id, from_status, to_status, note, actor_user_id)
VALUES
  (8, 4, NULL, 'PLACED', 'Order placed by customer', NULL),
  (9, 4, 'PLACED', 'CONFIRMED', 'Payment confirmed', 4),
  (10, 4, 'CONFIRMED', 'SHIPPED', 'Order shipped via courier', 4);

-- Order 5 status history
INSERT INTO order_status_history (id, order_id, from_status, to_status, note, actor_user_id)
VALUES
  (11, 5, NULL, 'PLACED', 'Order placed by guest checkout', NULL);

-- Order 6 status history
INSERT INTO order_status_history (id, order_id, from_status, to_status, note, actor_user_id)
VALUES
  (12, 6, NULL, 'PLACED', 'Order placed by customer', NULL),
  (13, 6, 'PLACED', 'CONFIRMED', 'Payment confirmed', 4),
  (14, 6, 'CONFIRMED', 'SHIPPED', 'Order shipped via courier', 4),
  (15, 6, 'SHIPPED', 'DELIVERED', 'Order delivered to customer', 4);

-- ============================================================================
-- AUDIT LOGS
-- ============================================================================

INSERT INTO audit_logs (id, actor_user_id, action, entity_type, entity_id, payload)
VALUES
  (1, 1, 'PRODUCT_CREATED', 'Product', '1', '{"name":"ZYLO AVENUE Razor Tee","status":"ACTIVE"}'),
  (2, 1, 'PRODUCT_CREATED', 'Product', '2', '{"name":"ZYLO AVENUE Night Ops Hoodie","status":"ACTIVE"}'),
  (3, 1, 'INVENTORY_RESTOCK', 'ProductVariant', '1', '{"sku":"ZYLO-TEE-RAZOR-BLK-S","qty":50}'),
  (4, 4, 'ORDER_CONFIRMED', 'Order', '1', '{"orderNumber":"ORD-20240515001","status":"CONFIRMED"}'),
  (5, 4, 'ORDER_SHIPPED', 'Order', '1', '{"orderNumber":"ORD-20240515001","carrier":"FEDEX"}'),
  (6, 4, 'PAYMENT_PROCESSED', 'Payment', '1', '{"provider":"STRIPE","amount":574800}');
