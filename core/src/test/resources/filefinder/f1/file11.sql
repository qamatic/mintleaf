--<ChangeSet id="change1" delimiter="/" />

--*--preconditions

  IfObjectExists('ABCDB.TABLE1') = true
  IfUsingClass('com.qamatic.assert, 1, PACKAGE') = true
  IfCount('SELECT count(*) FROM ABCDB.TABLE1') = 0

--*--rollback-changes

--*--tests


--<ChangeSet id="change2" delimiter="/" />

--*--<rollback-changes />


--*--<test />

