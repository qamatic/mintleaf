-- <ChangeSet id="part1" delimiter="/" />
-- empty package
create or replace package EmptyPackage
as
    
end EmptyPackage;

/

-- <ChangeSet id="part2" delimiter="/" />

create or replace
package body EmptyPackage
as

end EmptyPackage;

/

-- <ChangeSet id="part3" delimiter="/" />




CREATE TABLE TABLE1 
    ( 
     
     ID NUMBER (18)  NOT NULL ,      
     NAME VARCHAR2 (60 CHAR)  NOT NULL
    
    )  
;

 



CREATE TABLE TABLE2 
    ( 
      ID NUMBER (18)  NOT NULL ,      
     NAME VARCHAR2 (60 CHAR)  NOT NULL
    )  
;

