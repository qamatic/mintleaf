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

package org.qamatic.mintleaf.dbs;

import org.junit.Before;
import org.junit.Test;
import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.core.ChangeSets;
import org.qamatic.mintleaf.core.FluentJdbc;
import org.qamatic.mintleaf.tools.DbImporter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by qamatic on 3/5/16.
 */
public class DbComparerTests extends H2TestCase {


    @Before
    public void applyChangeSet() throws IOException, SQLException, MintLeafException {

        ChangeSets.migrate(h2DatabaseContext.getCloseableConnection(), "res:/example-changesets.sql", "create schema, DataForDbCompareTest, DROP_CREATE_USERS_IMPORT_TABLE");
        DataAction action = new DbImporter(h2DatabaseContext.getDriverSource(),
                "SELECT * FROM HRDB.USERS",
                h2DatabaseContext.getDriverSource(),
                "INSERT INTO HRDB.USERS_IMPORT_TABLE (USERID, USERNAME) VALUES ($USERID$, '$USERNAME$')"
        );
        action.execute();
        assertEquals(2, h2DbQueries.getCount("HRDB.USERS_IMPORT_TABLE"));

    }


    @Test
    public void compareListWithEqualSize() throws SQLException, IOException, MintLeafException {
        final List<String> expected = new ArrayList<String>() {
            {
                add("[Source:RowNo:0, ColumnNo:1, Surplus:0, Value:1] [Target:RowNo:0, ColumnNo:1, Surplus:0, Value:1]");
                add("[Source:RowNo:0, ColumnNo:2, Surplus:0, Value:Aiden] [Target:RowNo:0, ColumnNo:2, Surplus:0, Value:Aiden]");
                add("[Source:RowNo:0, ColumnNo:3, Surplus:0, Value:NULL] [Target:RowNo:0, ColumnNo:3, Surplus:0, Value:NULL]");
                add("[Source:RowNo:1, ColumnNo:1, Surplus:0, Value:2] [Target:RowNo:1, ColumnNo:1, Surplus:0, Value:2]");
                add("[Source:RowNo:1, ColumnNo:2, Surplus:0, Value:qamatic] [Target:RowNo:1, ColumnNo:2, Surplus:0, Value:qamatic]");
                add("[Source:RowNo:1, ColumnNo:3, Surplus:0, Value:NULL] [Target:RowNo:1, ColumnNo:3, Surplus:0, Value:NULL]");
            }
        };

        FluentJdbc sourceTable = h2DatabaseContext.queryBuilder().withSql("SELECT * FROM HRDB.USERS");
        FluentJdbc targetTable = h2DatabaseContext.queryBuilder().withSql("SELECT * FROM HRDB.USERS_IMPORT_TABLE");

        List<String> actuals = assertCompareTable(sourceTable.asRowListWrapper(), targetTable.asRowListWrapper());

        assertEquals(expected, actuals);
    }


    private List<String> assertCompareTable(RowListWrapper sourceList, RowListWrapper targetListList) throws MintLeafException {
        final List<String> actuals = new ArrayList<>();
        final ConsoleLogger logger = new ConsoleLogger();
        DataComparer dataComparer = new Mintleaf.ComparerBuilder().
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
