-- 기준 코드 구분 테이블 생성

CREATE TABLE basis_code_divisions(
    basis_code_division VARCHAR(40) NOT NULL,
    basis_code_division_number VARCHAR(100) NULL
);

ALTER TABLE basis_code_divisions
ADD PRIMARY KEY (basis_code_division);
