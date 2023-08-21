-- 사용자 생성
CREATE USER 'hsjkdss228'@'localhost' IDENTIFIED BY 'Password!1';

-- 특정 데이터베이스에 대해 특정 동작에 대한 권한 부여
GRANT SELECT, INSERT, UPDATE, DELETE ON sql_booster.* TO 'hsjkds228'@'localhost';

-- 권한을 부여할 때, 가장 마지막에 다음을 추가하면
-- 해당 계정이 자신이 부여받은 해당 동작의 권한에 대해서는 다른 계정에게 권한을 부여할 수 있음.
WITH GRANT OPTION

-- 모든 데이터베이스에 대해 모든 동작에 대한 권한 및
-- 다른 계정들에 해당 모든 권한들을 부여할 수 있는 권한까지 부여
GRANT ALL PRIVILEGES ON *.* TO 'hsjkdss228'@'localhost' WITH GRANT OPTION;

-- 부여한 권한 취소
REVOKE SELECT, INSERT ON sql_booster.* FROM 'hsjkdss228'@'localhost';

-- 변경된 권한 확인
SHOW GRANTS FOR 'hsjkdss228'@'localhost';

-- 변경된 권한 적용
FLUSH PRIVILEGES;
