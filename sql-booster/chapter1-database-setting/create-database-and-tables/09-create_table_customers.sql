-- 고객 마스터 테이블 생성

CREATE TABLE customers(
    customer_id VARCHAR(40) NOT NULL,
    customer_name VARCHAR(100) NULL,
    mobile_number VARCHAR(100) NULL,
    email_address VARCHAR(100) NULL,
    password VARCHAR(200) NULL,
    region_id VARCHAR(40) NULL,
    address_text VARCHAR(200) NULL,
    gender_type VARCHAR(40) NULL,
    birthday_ymd VARCHAR(8) NULL,
    customer_grade VARCHAR(40) NULL
);

ALTER TABLE customers
ADD PRIMARY KEY (customer_id);
