
-- <ChangeSet id="variable insert" delimiter=";" />

INSERT INTO HRDB.USERS (USERID, USERNAME) VALUES (${user_id}, '${user_name}');


-- <ChangeSet id="schema name var from connection properties" delimiter=";" />

INSERT INTO ${DEFAULT_SCHEMA}.USERS (USERID, USERNAME) VALUES ("33", 'senthil');