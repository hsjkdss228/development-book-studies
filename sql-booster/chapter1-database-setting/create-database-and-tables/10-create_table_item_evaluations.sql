-- 아이템 평가 테이블 생성

CREATE TABLE item_evaluations(
    item_id VARCHAR(40) NOT NULL,
    evaluation_list_number DECIMAL(18) NOT NULL,
    customer_id VARCHAR(40) NOT NULL,
    evaluation_description VARCHAR(1000) NULL,
    evaluation_datetime DATETIME NULL,
    evaluation_point DECIMAL(18, 2) NULL
);

ALTER TABLE item_evaluations
ADD PRIMARY KEY (
    item_id,
    evaluation_list_number
);
