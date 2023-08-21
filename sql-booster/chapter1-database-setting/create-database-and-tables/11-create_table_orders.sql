-- 주문 테이블 생성

CREATE TABLE orders(
    order_sequence DECIMAL(18) NOT NULL,
    customer_id VARCHAR(40) NOT NULL,
    order_datetime DATETIME NULL,
    order_status VARCHAR(40) NULL,
    payment_datetime DATETIME NULL,
    payment_type VARCHAR(40) NULL,
    order_amount DECIMAL(18, 3) NULL,
    payment_amount DECIMAL(18, 3) NULL
);

ALTER TABLE orders
ADD PRIMARY KEY (order_sequence);
