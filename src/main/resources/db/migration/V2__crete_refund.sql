CREATE TABLE refunds (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            user_id BIGINT NOT NULL,
                            order_id BIGINT NOT NULL,
                            reason TEXT NOT NULL,
                            total_price DECIMAL(10, 2) NOT NULL,
                            status VARCHAR(10) NOT NULL DEFAULT 'pending',
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                            updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE refund_items (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              refund_id BIGINT NOT NULL,
                              order_item_id BIGINT NOT NULL,
                              quantity INT NOT NULL,
                              price DECIMAL(10, 2) NOT NULL,
                              created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                              updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);