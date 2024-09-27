-- 아이템 테이블 생성
-- 아이템: 실제 판매가 발생하거나 재고 관리가 되는 상품 단위

CREATE TABLE items(
    item_id VARCHAR(40) NOT NULL,
    item_number VARCHAR(100) NULL,
    item_type VARCHAR(40) NULL,
    unit_price DECIMAL(18, 3) NULL
);

ALTER TABLE items
ADD PRIMARY KEY (item_id);
