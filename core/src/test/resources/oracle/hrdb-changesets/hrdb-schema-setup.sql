-- <ChangeSet id="create schema" delimiter="/" />

DECLARE

  PROCEDURE CREATE_TESTUSER(
      v_username VARCHAR,
      v_password VARCHAR )
  IS
    CNT INT;
  BEGIN

    FOR REC IN  (SELECT s.SID, s.SERIAL#  FROM v$session s
      WHERE s.username = upper(v_username))
    LOOP
      EXECUTE immediate 'alter system kill session ''' || rec.sid || ', ' || rec.serial# || '''' ;
    END LOOP;

    SELECT COUNT(*) INTO CNT FROM ALL_USERS WHERE USERNAME = UPPER(v_username);
    IF CNT = 1 THEN
      DBMS_OUTPUT.PUT_LINE ('DROP USER' || v_username); 
      EXECUTE IMMEDIATE 'DROP USER '|| v_username || ' CASCADE';
    END IF;

   
    DBMS_OUTPUT.PUT_LINE ( 'USER created: user=  ' || v_username || ', ' 
      || 'password = ' || v_password || '.' );
      
    EXECUTE IMMEDIATE 'CREATE USER '||v_username||' IDENTIFIED BY '||v_password;

    EXECUTE IMMEDIATE 'GRANT CREATE SESSION TO '||v_username;
    EXECUTE immediate 'GRANT CONNECT, RESOURCE, CREATE TABLE, CREATE SESSION, CREATE SEQUENCE, CREATE VIEW, CREATE TRIGGER, ALTER SESSION, CREATE SYNONYM to ' || v_username;

  END CREATE_TESTUSER;

BEGIN
  CREATE_TESTUSER('HRDB1', 'HRDB1');
  CREATE_TESTUSER('HRDB2', 'HRDB2');
END;

/
