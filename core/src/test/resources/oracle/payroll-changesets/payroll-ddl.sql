-- <ChangeSet id="create countries" delimiter=";" />

CREATE TABLE COUNTRIES
(
  COUNTRY_ID   CHAR(2 BYTE) NOT NULL,
  COUNTRY_NAME VARCHAR2(40 BYTE),
  REGION_ID    NUMBER
) LOGGING;

CREATE UNIQUE INDEX COUNTRY_C_ID_PKX ON COUNTRIES
(
  COUNTRY_ID ASC
);

ALTER TABLE COUNTRIES
ADD CONSTRAINT COUNTRY_C_ID_PK PRIMARY KEY (COUNTRY_ID);


-- <ChangeSet id="few countries" delimiter=";" />

INSERT INTO countries VALUES ( 'IT','Italy',1);
INSERT INTO countries VALUES ( 'JP','Japan',3);
INSERT INTO countries VALUES ( 'US','United States of America',2);
INSERT INTO countries VALUES ( 'CA','Canada',2);
INSERT INTO countries VALUES ( 'CN','China',3);
INSERT INTO countries VALUES ( 'IN','India',3);
INSERT INTO countries VALUES ( 'AU','Australia',3);
INSERT INTO countries VALUES ( 'ZW','Zimbabwe',4);
INSERT INTO countries VALUES ( 'SG','Singapore',3);
INSERT INTO countries VALUES ( 'UK','United Kingdom',1);
INSERT INTO countries VALUES ( 'FR','France',1);
INSERT INTO countries VALUES ( 'DE','Germany',1);


-- <ChangeSet id="create procs" delimiter="/" />


CREATE OR REPLACE PROCEDURE add_country
  (  p_country_id          COUNTRIES.COUNTRY_ID%type
   , p_country_name       COUNTRIES.COUNTRY_NAME%type
   )
IS
BEGIN
  INSERT INTO COUNTRIES (COUNTRY_ID, COUNTRY_NAME)
    VALUES(p_country_id, p_country_name);
END add_country;

/