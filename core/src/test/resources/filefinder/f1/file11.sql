--<ChangeSet id="change1" delimiter="/" />

--*--preconditions

  IfExists(ABCDB.TABLE1) = true
  IfUsingClass(com.qamatic.assert, 1, PACKAGE) = true
  IfCount(SELECT count(*) FROM ABCDB.TABLE1) = 0
  IfProc( dberify, 1, 2 ) = S

--*--rollback


--*--tests

   assertSql(SELECT ID, NAME FROM USERS) = [2, SEN]


--<ChangeSet id="change2" delimiter="/" />

--*--<rollback-changes />


--*--<test />

