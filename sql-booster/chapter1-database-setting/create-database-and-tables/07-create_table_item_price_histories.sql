-- 아이템 단가 이력 테이블 생성

CREATE TABLE item_price_histories(
    item_id VARCHAR(40) NOT NULL,
    to_ymd VARCHAR(8) NOT NULL,
    from_ymd VARCHAR(8) NULL,
    unit_price DECIMAL(18, 3) NULL
);

ALTER TABLE item_price_histories
ADD PRIMARY KEY (
    item_id,
    to_ymd
);
