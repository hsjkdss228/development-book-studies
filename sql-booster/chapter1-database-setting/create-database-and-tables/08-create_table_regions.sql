-- 지역 마스터 테이블 생성

CREATE TABLE regions(
    region_id VARCHAR(40) NOT NULL,
    region_number VARCHAR(100) NULL,
    sort_order DECIMAL(18) NULL
);

ALTER TABLE regions
ADD PRIMARY KEY (region_id);
