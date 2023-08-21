-- 기준 코드 테이블 생성

-- ORACLE의 다음 타입은 MySQL의 다음 타입에 대응될 수 있다.
-- VARCHAR2(size) -> VARCHAR(size)
-- NUMBER(precision, scale) -> DECIMAL(precision, scale)

CREATE TABLE basis_codes(
    basis_code_division VARCHAR(40) NOT NULL,
    language_code VARCHAR(40) NOT NULL,
    basis_code VARCHAR(40) NOT NULL,
    basis_code_name VARCHAR(100) NOT NULL,
    sort_order DECIMAL(18) NOT NULL
);

ALTER TABLE basis_codes
ADD PRIMARY KEY (
    basis_code_division,
    language_code,
    basis_code
);

-- MySQL에서는 PRIMARY KEY를 생성할 때, 해당 필드들에 대한 고유한 인덱스가 같이 생성되므로
-- UNIQUE INDEX를 직접 생성해주지는 않아도 됨.
-- 추가적인 INDEX가 필요한 경우에는 다음과 같이 쿼리문을 작성할 수 있음.
CREATE UNIQUE INDEX pk_basis_code
ON basis_codes(basis_code_number);

-- 이미 존재하는 인덱스를 삭제하는 명령어
DROP INDEX pk_basis_code ON basis_codes;

-- 특정 테이블의 Column 레퍼런스를 조회하는 명령어
SHOW COLUMNS FROM basis_codes;

-- 특정 테이블의 Column 이름을 변경하는 명령어
ALTER TABLE basis_codes
RENAME COLUMN basis_code_number TO basis_code_name;

-- 특정 테이블의 Column 속성을 변경하는 명령어 (타입, Nullable 등)
ALTER TABLE basis_codes
MODIFY COLUMN basis_code_name VARCHAR(100);
