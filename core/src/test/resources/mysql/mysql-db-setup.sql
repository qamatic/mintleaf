-- MYSQL SETUP
-- <ChangeSet id="create database and users" delimiter=";" />

DROP USER IF EXISTS 'testuser1';
CREATE USER IF NOT EXISTS 'testuser1' IDENTIFIED BY 'testpassword1';


DROP DATABASE IF EXISTS employees;
CREATE DATABASE IF NOT EXISTS employees;

GRANT ALL ON employees.* TO 'testuser1';
FLUSH PRIVILEGES;
