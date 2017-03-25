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

package org.qamatic.mintleaf.dbqueries;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.qamatic.mintleaf.ConnectionContext;
import org.qamatic.mintleaf.DbCallable;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.core.ChangeSets;
import org.qamatic.mintleaf.core.DbConnectionContext;
import org.qamatic.mintleaf.core.ExecuteQuery;

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
        ChangeSets.migrate(h2Database.getNewConnection(), "res:/example-changesets.sql", "create schema, DataForDbCompareTest, DROP_CREATE_USERS_IMPORT_TABLE");
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

        try (ConnectionContext ctx = h2Database.getNewConnection()) {

            try (DbCallable<int[]> dbCallable = executeSql(ctx, "DELETE HRDB.USERS WHERE USERID=1")) {

                assertEquals(2, ctx.getDbQueries().getCount("HRDB.USERS"));
                dbCallable.execute();
                assertEquals(1, ctx.getDbQueries().getCount("HRDB.USERS"));
            }

        }
    }

    @Test
    public void statementListenTest() throws SQLException, IOException, MintLeafException {

        try (ConnectionContext ctx = h2Database.getNewConnection()) {
            final int[] pkey = {-1};
            try (DbCallable<int[]> dbCallable = executeSql(ctx).
                    withSql("INSERT INTO HRDB.USERS (USERID, USERNAME) VALUES (9, 'TN')").
                    withListener(statement -> {
                        pkey[0] = 1;
                    }).
                    buildExecute()) {

                assertEquals(2, ctx.getDbQueries().getCount("HRDB.USERS"));
                dbCallable.execute();
                assertEquals(1, pkey[0]);
                assertEquals(3, ctx.getDbQueries().getCount("HRDB.USERS"));
            }

        }
    }

    @Test
    public void insertUsingBindingTest() throws SQLException, IOException, MintLeafException {

        try (ConnectionContext ctx = h2Database.getNewConnection()) {

            try (DbCallable<int[]> dbCallable = executeSql(ctx, "INSERT INTO HRDB.USERS (USERID, USERNAME) VALUES (?, ?)", new Object[]{11, "HI"})) {

                assertEquals(2, ctx.getDbQueries().getCount("HRDB.USERS"));
                dbCallable.execute();
                assertEquals(3, ctx.getDbQueries().getCount("HRDB.USERS"));
            }

        }
    }

}
