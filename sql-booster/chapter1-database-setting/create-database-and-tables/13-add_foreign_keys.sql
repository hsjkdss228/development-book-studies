-- FOREIGN KEY 설정

-- 외래 키 제약 조건 추가 시, `CONSTRAINT 외래_키_이름`을 제외하는 경우
-- MySQL이 자동으로 고유한 이름을 생성하여 외래 키 제약 조건을 식별.

ALTER TABLE basis_codes
ADD FOREIGN KEY (basis_code_division)
REFERENCES basis_code_divisions(basis_code_division);

-- 특정 테이블의 PRIMARY KEY나 FOREIGN KEY의 부여 상태를 확인하는 쿼리문
SHOW CREATE TABLE basis_codes;

ALTER TABLE item_price_histories
ADD FOREIGN KEY (item_id)
REFERENCES items(item_id);

ALTER TABLE customers
ADD FOREIGN KEY (region_id)
REFERENCES regions(region_id);

ALTER TABLE item_evaluations
ADD FOREIGN KEY (customer_id)
REFERENCES customers(customer_id);

ALTER TABLE item_evaluations
ADD FOREIGN KEY (item_id)
REFERENCES items(item_id);

ALTER TABLE orders
ADD FOREIGN KEY (customer_id)
REFERENCES customers(customer_id);

ALTER TABLE order_details
ADD FOREIGN KEY (order_sequence)
REFERENCES orders(order_sequence);
