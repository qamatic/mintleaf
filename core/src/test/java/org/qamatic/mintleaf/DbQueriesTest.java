/*
 *
 *   *
 *   *  *
 *   *  *   ~
 *   *  *   ~ The MIT License (MIT)
 *   *  *   ~
 *   *  *   ~ Copyright (c) 2010-2017 QAMatic Team
 *   *  *   ~
 *   *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *   *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *   *  *   ~ in the Software without restriction, including without limitation the rights
 *   *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *   *  *   ~ furnished to do so, subject to the following conditions:
 *   *  *   ~
 *   *  *   ~ The above copyright notice and this permission notice shall be included in all
 *   *  *   ~ copies or substantial portions of the Software.
 *   *  *   ~
 *   *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   *  *   ~ SOFTWARE.
 *   *  *   ~
 *   *  *   ~
 *   *  *
 *   *
 *   *
 *
 * /
 */

package org.qamatic.mintleaf;

import org.junit.Before;
import org.junit.Test;
import org.qamatic.mintleaf.core.ChangeSets;
import org.qamatic.mintleaf.core.DbConnectionContext;
import org.qamatic.mintleaf.core.ExecuteQuery;
import org.qamatic.mintleaf.core.stdqueries.StandardQueries;

import java.io.IOException;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.*;
import static org.qamatic.mintleaf.Mintleaf.executeSql;

/**
 * Created by qamatic on 3/6/16.
 */
public class DbQueriesTest extends H2TestCase {

    @Before
    public void cleanDb() throws MintLeafException {
        ChangeSets.migrate(testDb.getNewConnection(), "res:/example-changesets.sql", "create schema, DataForDbCompareTest, DROP_CREATE_USERS_IMPORT_TABLE");
    }

    @Test
    public void contextCloseCall() throws SQLException, IOException, MintLeafException {

        ConnectionContext ctx = mock(DbConnectionContext.class);

        try (ConnectionContext connectionContext = ctx) {
            assertNull(connectionContext.getConnection());
        }

        verify(ctx, times(1)).close();

    }

    @Test
    public void executeQueryCloseCall() throws SQLException, IOException, MintLeafException {

        ExecuteQuery query = mock(ExecuteQuery.class);

        try (ExecuteQuery qry = query) {
            assertNull(qry.execute());
        }

        verify(query, times(1)).close();
    }

    @Test
    public void statementCallTest() throws SQLException, IOException, MintLeafException {

        try (ConnectionContext ctx = testDb.getNewConnection()) {

            try (Executable<int[]> executable = executeSql(ctx, "DELETE HRDB.USERS WHERE USERID=1")) {

                assertEquals(2, ctx.getDbQueries().getCount("HRDB.USERS"));
                executable.execute();
                assertEquals(1, ctx.getDbQueries().getCount("HRDB.USERS"));
            }

        }
    }

    @Test
    public void statementListenTest() throws SQLException, IOException, MintLeafException {

        try (ConnectionContext ctx = testDb.getNewConnection()) {
            final int[] pkey = {-1};
            try (Executable<int[]> executable = executeSql(ctx).
                    withSql("INSERT INTO HRDB.USERS (USERID, USERNAME) VALUES (9, 'TN')").
                    withListener(statement -> {
                        pkey[0] = 1;
                    }).
                    buildExecute()) {

                assertEquals(2, ctx.getDbQueries().getCount("HRDB.USERS"));
                executable.execute();
                assertEquals(1, pkey[0]);
                assertEquals(3, ctx.getDbQueries().getCount("HRDB.USERS"));
            }

        }
    }

    @Test
    public void insertUsingBindingTest() throws SQLException, IOException, MintLeafException {

        try (ConnectionContext ctx = testDb.getNewConnection()) {

            try (Executable<int[]> executable = executeSql(ctx, "INSERT INTO HRDB.USERS (USERID, USERNAME) VALUES (?, ?)", new Object[]{11, "HI"})) {

                assertEquals(2, ctx.getDbQueries().getCount("HRDB.USERS"));
                executable.execute();
                assertEquals(3, ctx.getDbQueries().getCount("HRDB.USERS"));
            }

        }
    }


    @Test
    public void rollBackTest() throws SQLException, IOException, MintLeafException {

        try (ConnectionContext ctx = testDb.getNewConnection().beginTransaction()) {
            try (Executable<int[]> executable = executeSql(ctx, "INSERT INTO HRDB.USERS (USERID, USERNAME) VALUES (?, ?)", new Object[]{11, "HI"})) {

                assertEquals(2, ctx.getDbQueries().getCount("HRDB.USERS"));
                executable.execute();
                assertEquals(3, ctx.getDbQueries().getCount("HRDB.USERS"));
            }
            ctx.rollbackTransaction();
            assertEquals(2, ctx.getDbQueries().getCount("HRDB.USERS"));
        }
    }

    @Test
    public void commitTest() throws SQLException, IOException, MintLeafException {

        try (ConnectionContext ctx = testDb.getNewConnection().beginTransaction()) {
            ctx.beginTransaction();
            try (Executable<int[]> executable = executeSql(ctx, "INSERT INTO HRDB.USERS (USERID, USERNAME) VALUES (?, ?)", new Object[]{11, "HI"})) {

                assertEquals(2, ctx.getDbQueries().getCount("HRDB.USERS"));
                executable.execute();
                assertEquals(3, ctx.getDbQueries().getCount("HRDB.USERS"));
            }
            assertEquals(3, ctx.getDbQueries().getCount("HRDB.USERS"));
        }
    }

    @Test
    public void reRegisterDbQueryTest() throws SQLException, IOException, MintLeafException {
        StandardQueries.registerQueryImplementation(DbType.H2.getJdbcUrlPrefix(), MyH2Queries.class);

        try (ConnectionContext<MyH2Queries> ctx = testDb.getNewConnection()) {
            assertEquals("test", ctx.getDbQueries().returnSomeValue());
        }
    }

    @Test
    public void resultSetFirstTest() throws SQLException, IOException, MintLeafException {
        try (ConnectionContext ctx = testDb.getNewConnection()) {
            SqlResultSet resultSet = Mintleaf.selectQuery(ctx).withSql("Select count(*) from HRDB.USERS").buildSelect();
            assertEquals(2, resultSet.first().getInt(1));
        }
    }


}
