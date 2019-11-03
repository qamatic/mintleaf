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

package org.qamatic.mintleaf.core;

import org.junit.Before;
import org.junit.Test;
import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.tools.DbImporter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by qamatic on 3/5/16.
 */
public class DbComparerTest extends H2TestCase {

    private static final MintleafLogger logger = MintleafLogger.getLogger(DbComparerTest.class);

    @Before
    public void applyChangeSet() throws IOException, SQLException, MintleafException {

        ChangeSets.migrate(testDb.getNewConnection(), "res:/example-changesets.sql", "create schema, DataForDbCompareTest, DROP_CREATE_USERS_IMPORT_TABLE");
        Executable action = new DbImporter(testDb.getNewConnection(),
                "SELECT * FROM HRDB.USERS",
                testDb.getNewConnection(),
                "INSERT INTO HRDB.USERS_IMPORT_TABLE (USERID, USERNAME) VALUES ($USERID$, '$USERNAME$')"
        );
        action.execute();
        assertEquals(2, testDbQueries.getCount("HRDB.USERS_IMPORT_TABLE"));

    }


    @Test
    public void compareListWithEqualSize() throws SQLException, IOException, MintleafException {
        final List<String> expected = new ArrayList<String>() {
            {
                add("[Source:RowNo:0, ColumnNo:1, Surplus:0, Value:1] [Target:RowNo:0, ColumnNo:1, Surplus:0, Value:1]");
                add("[Source:RowNo:0, ColumnNo:2, Surplus:0, Value:Aiden] [Target:RowNo:0, ColumnNo:2, Surplus:0, Value:Aiden]");
                add("[Source:RowNo:0, ColumnNo:3, Surplus:0, Value:NULL] [Target:RowNo:0, ColumnNo:3, Surplus:0, Value:NULL]");
                add("[Source:RowNo:0, ColumnNo:4, Surplus:0, Value:NULL] [Target:RowNo:0, ColumnNo:4, Surplus:0, Value:NULL]");
                add("[Source:RowNo:1, ColumnNo:1, Surplus:0, Value:2] [Target:RowNo:1, ColumnNo:1, Surplus:0, Value:2]");
                add("[Source:RowNo:1, ColumnNo:2, Surplus:0, Value:qamatic] [Target:RowNo:1, ColumnNo:2, Surplus:0, Value:qamatic]");
                add("[Source:RowNo:1, ColumnNo:3, Surplus:0, Value:NULL] [Target:RowNo:1, ColumnNo:3, Surplus:0, Value:NULL]");
                add("[Source:RowNo:1, ColumnNo:4, Surplus:0, Value:NULL] [Target:RowNo:1, ColumnNo:4, Surplus:0, Value:NULL]");
            }
        };

        try (ConnectionContext ctx = testDb.getNewConnection()) {

            SqlResultSet sourceTable = ctx.selectQuery("SELECT * FROM HRDB.USERS");


            SqlResultSet targetTable = ctx.selectQuery("SELECT * FROM HRDB.USERS_IMPORT_TABLE");

            List<String> actuals = assertCompareTable(sourceTable.asRowListWrapper(), targetTable.asRowListWrapper());

            assertEquals(expected, actuals);
        }
    }


    private List<String> assertCompareTable(Table sourceList, Table targetListList) throws MintleafException {
        final List<String> actuals = new ArrayList<>();

        DataComparer dataComparer = new Mintleaf.DataComparerBuilder().
                withSourceTable(sourceList).
                withTargetTable(targetListList).
                withMatchingResult((sourceColumn, targetColumn) -> {
                    actuals.add(String.format("[Source:%s] [Target:%s]", sourceColumn.toLog(), targetColumn.toLog()));
                    logger.info(actuals.get(actuals.size() - 1));

                }).
                build();


        dataComparer.execute();

        return actuals;
    }


}
