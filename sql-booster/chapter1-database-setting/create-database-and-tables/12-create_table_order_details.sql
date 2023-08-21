-- 주문 상세 테이블 생성

CREATE TABLE order_details(
    order_sequence DECIMAL(18) NOT NULL,
    order_detail_number DECIMAL(18) NOT NULL,
    item_id VARCHAR(40) NOT NULL,
    order_quantity DECIMAL(18) NULL,
    unit_price DECIMAL(18, 3) NULL
);

ALTER TABLE order_details
ADD PRIMARY KEY (
    order_sequence,
    order_detail_number
);
