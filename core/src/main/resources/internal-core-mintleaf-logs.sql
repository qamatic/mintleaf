-- <ChangeSet id="delete-mintleaf-logs" delimiter=";" />

drop table if exists mintleaf.mintleaf_logs

-- <ChangeSet id="h2-core-mintleaf-logs" delimiter=";" />

CREATE TABLE if not exists mintleaf.mintleaf_logs
    (

     id INT  NOT NULL ,
     version VARCHAR2 (60 CHAR) NOT NULL,
     change_sets VARCHAR2 (60 CHAR) NOT NULL,
     script_location VARCHAR2 (60 CHAR) NOT NULL,
     status NUMBER (5) NULL,
     last_error TEXT NULL,

    )
;



-- <ChangeSet id="postgres-core-mintleaf-logs" delimiter=";" />

CREATE TABLE mintleaf.mintleaf_logs (
  id              SERIAL PRIMARY KEY,
  version         VARCHAR(20) NOT NULL,
  change_sets     VARCHAR(250) NOT NULL,
  script_location VARCHAR(120) NOT NULL,
  status          SMALLINT DEFAULT 0 NULL,
  last_error      TEXT NULL

);

-- <ChangeSet id="mysql-core-mintleaf-logs" delimiter=";" />