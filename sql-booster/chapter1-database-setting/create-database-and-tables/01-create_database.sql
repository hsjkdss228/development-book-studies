-- https://blog.naver.com/ryu1hwan/221525912860
-- 교재에서 제공되는 실습을 위한 데이터베이스, 테이블 세팅을 진행한다.
-- 단, Oracle로 제공되고 있기 때문에 쿼리문을 MySQL로 변환해 삽입한다.

CREATE DATABASE sql_booster;

USE sql_booster;

-- 이름에는 hyphen('-')을 넣지 않는 것이 좋다.
-- '-'이 들어간 경우, 다음과 같이 백틱으로 이름을 감싸야 한다.
DROP DATABASE `sql-booster`;
