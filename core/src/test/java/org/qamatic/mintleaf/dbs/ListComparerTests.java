/*
 *
 *  *
 *  *  * <!--
 *  *  *   ~
 *  *  *   ~ The MIT License (MIT)
 *  *  *   ~
 *  *  *   ~ Copyright (c) 2010-2017 QAMatic
 *  *  *   ~
 *  *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *  *  *   ~ in the Software without restriction, including without limitation the rights
 *  *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *  *  *   ~ furnished to do so, subject to the following conditions:
 *  *  *   ~
 *  *  *   ~ The above copyright notice and this permission notice shall be included in all
 *  *  *   ~ copies or substantial portions of the Software.
 *  *  *   ~
 *  *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  *   ~ SOFTWARE.
 *  *  *   ~
 *  *  *   ~
 *  *  *   -->
 *  *
 *  *
 *
 */

package org.qamatic.mintleaf.dbs;

import org.junit.Test;
import org.qamatic.mintleaf.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by qamatic on 3/5/16.
 */
public class ListComparerTests {

    private static ColumnMetaDataCollection columnDefs = new ColumnMetaDataCollection("LIST.USERS") {
        {
            add(new Column("USERNAME", Types.VARCHAR));
            add(new Column("COUNTRY", Types.VARCHAR));
        }
    };


    @Test
    public void compareListWithEqualSize() throws SQLException, IOException, MintLeafException {
        final List<String> expected = new ArrayList<String>() {
            {
                add("[Source:RowNo:0, ColumnNo:0, Surplus:0] [Target:RowNo:0, ColumnNo:0, Surplus:0]");
                add("[Source:RowNo:0, ColumnNo:1, Surplus:0] [Target:RowNo:0, ColumnNo:1, Surplus:0]");
                add("[Source:RowNo:1, ColumnNo:0, Surplus:0] [Target:RowNo:1, ColumnNo:0, Surplus:0]");
                add("[Source:RowNo:1, ColumnNo:1, Surplus:0] [Target:RowNo:1, ColumnNo:1, Surplus:0]");
            }
        };
        List<String> actuals = assertCompareTable(getSampleData1(), getSampleData1());
        assertEquals(expected, actuals);
    }

    @Test
    public void compareListSourceSurplus() throws SQLException, IOException, MintLeafException {
        final List<String> expected = new ArrayList<String>() {
            {
                add("[Source:RowNo:0, ColumnNo:0, Surplus:0] [Target:RowNo:0, ColumnNo:0, Surplus:0]");
                add("[Source:RowNo:0, ColumnNo:1, Surplus:0] [Target:RowNo:0, ColumnNo:1, Surplus:0]");
                add("[Source:RowNo:1, ColumnNo:0, Surplus:0] [Target:RowNo:1, ColumnNo:0, Surplus:0]");
                add("[Source:RowNo:1, ColumnNo:1, Surplus:0] [Target:RowNo:1, ColumnNo:1, Surplus:0]");
                add("[Source:RowNo:2, ColumnNo:0, Surplus:1] [Target:RowNo:1, ColumnNo:-1, Surplus:-1]");
                add("[Source:RowNo:2, ColumnNo:1, Surplus:1] [Target:RowNo:1, ColumnNo:-1, Surplus:-1]");
            }
        };
        List<User> sourceList = getSampleData1();
        sourceList.add(new User("XMEN", "USA"));
        List<String> actuals = assertCompareTable(sourceList, getSampleData1());

        assertEquals(expected, actuals);
    }


    @Test
    public void compareListSourceDeficit() throws SQLException, IOException, MintLeafException {
        final List<String> expected = new ArrayList<String>() {
            {
                add("[Source:RowNo:0, ColumnNo:0, Surplus:0] [Target:RowNo:0, ColumnNo:0, Surplus:0]");
                add("[Source:RowNo:0, ColumnNo:1, Surplus:0] [Target:RowNo:0, ColumnNo:1, Surplus:0]");
                add("[Source:RowNo:1, ColumnNo:0, Surplus:0] [Target:RowNo:1, ColumnNo:0, Surplus:0]");
                add("[Source:RowNo:1, ColumnNo:1, Surplus:0] [Target:RowNo:1, ColumnNo:1, Surplus:0]");
                add("[Source:RowNo:1, ColumnNo:-1, Surplus:-1] [Target:RowNo:2, ColumnNo:0, Surplus:1]");
                add("[Source:RowNo:1, ColumnNo:-1, Surplus:-1] [Target:RowNo:2, ColumnNo:1, Surplus:1]");
            }
        };
        List<User> targetList = getSampleData1();
        targetList.add(new User("XMEN", "USA"));
        List<String> actuals = assertCompareTable(getSampleData1(), targetList);

        assertEquals(expected, actuals);
    }


    private static List<String> assertCompareTable(List<User> sourceList, List<User> targetListList) throws MintLeafException {
        final List<String> actuals = new ArrayList<>();
        final ConsoleLogger logger = new ConsoleLogger();
        DataComparer dataComparer = new Mintleaf.ComparerBuilder().
                withSourceTable(sourceList, columnDefs).
                withTargetTable(targetListList, columnDefs).
                withMatchingResult((sourceRow, targetRow) -> {
                    actuals.add(String.format("[Source:%s] [Target:%s]", sourceRow, targetRow));
                    logger.info(actuals.get(actuals.size() - 1));
                }).
                build();


        dataComparer.execute();

        return actuals;
    }



    @Test
    public void compareList() throws SQLException, IOException, MintLeafException {
        List<User> sourceUserList = getSampleData2();
        List<User> targetUserList = getSampleData2();
        final ConsoleLogger logger = new ConsoleLogger();
        DataComparer dataComparer = new Mintleaf.ComparerBuilder().
                withSourceTable(sourceUserList, columnDefs).
                withTargetTable(targetUserList, columnDefs).
                withMatchingResult((sourceRow, targetRow) -> {

                    logger.info(String.format("[Source:%s] [Target:%s]", sourceRow, targetRow));
                    assertEquals(sourceRow.asString(), targetRow.asString());


                }).
                build();

        dataComparer.execute();
    }

    private ArrayList<User> getSampleData2() {
        return new ArrayList<User>() {
            {
                add(new User("SM", "USA"));
            }
        };
    }

    private ArrayList<User> getSampleData1() {
        return new ArrayList<User>() {
            {
                add(new User("Vallr", "USA"));
                add(new User("SM", "USA"));
            }
        };
    }

}
