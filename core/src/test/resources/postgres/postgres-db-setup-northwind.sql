-- https://github.com/pthom/northwind_psql
-- <ChangeSet id="create northwind database" delimiter=";" />

DROP DATABASE IF EXISTS northwind;
CREATE DATABASE northwind;



DROP USER IF EXISTS  northwind_user;
create user  northwind_user with  password 'thewindisblowing';
grant all privileges on database northwind to northwind_user;

