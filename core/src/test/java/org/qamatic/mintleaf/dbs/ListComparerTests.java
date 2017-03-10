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
import org.qamatic.mintleaf.data.ComparerListener;
import org.qamatic.mintleaf.data.RowState;

import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;


/**
 * Created by qamatic on 3/5/16.
 */
public class ListComparerTests {


    @Test
    public void compareListWithEqualSize() throws SQLException, IOException, MintLeafException {
        final List<String> expected = new ArrayList<String>() {
            {
                add("[Source:RowNo:0, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}] [Target:RowNo:0, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}]");
                add("[Source:RowNo:0, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}] [Target:RowNo:0, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}]");
                add("[Source:RowNo:1, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}] [Target:RowNo:1, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}]");
                add("[Source:RowNo:1, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}] [Target:RowNo:1, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}]");
            }
        };
        List<String> actuals = assertCompareTable(getDefaultUsers(), getDefaultUsers());

        assertEquals(expected, actuals);
    }

    @Test
    public void compareListSourceSurplus() throws SQLException, IOException, MintLeafException {
        final List<String> expected = new ArrayList<String>() {
            {
                add("[Source:RowNo:0, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}] [Target:RowNo:0, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}]");
                add("[Source:RowNo:0, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}] [Target:RowNo:0, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}]");
                add("[Source:RowNo:1, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}] [Target:RowNo:1, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}]");
                add("[Source:RowNo:1, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}] [Target:RowNo:1, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}]");
                add("[Source:RowNo:2, ColumnNo:0, Surplus:1, Row:{COUNTRY=USA, USERNAME=XMEN}] [Target:RowNo:1, ColumnNo:-1, Surplus:-1, Row:null]");
                add("[Source:RowNo:2, ColumnNo:1, Surplus:1, Row:{COUNTRY=USA, USERNAME=XMEN}] [Target:RowNo:1, ColumnNo:-1, Surplus:-1, Row:null]");
            }
        };
        List<ListRowWrapper> sourceList = getDefaultUsers();
        sourceList.add(new ListRowWrapper("XMEN", "USA"));
        List<String> actuals = assertCompareTable(sourceList, getDefaultUsers());

        assertEquals(expected, actuals);
    }


    @Test
    public void compareListSourceDeficit() throws SQLException, IOException, MintLeafException {
        final List<String> expected = new ArrayList<String>() {
            {
                add("[Source:RowNo:0, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}] [Target:RowNo:0, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}]");
                add("[Source:RowNo:0, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}] [Target:RowNo:0, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=Vallr}]");
                add("[Source:RowNo:1, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}] [Target:RowNo:1, ColumnNo:0, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}]");
                add("[Source:RowNo:1, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}] [Target:RowNo:1, ColumnNo:1, Surplus:0, Row:{COUNTRY=USA, USERNAME=SM}]");
                add("[Source:RowNo:1, ColumnNo:-1, Surplus:-1, Row:null] [Target:RowNo:2, ColumnNo:0, Surplus:1, Row:{COUNTRY=USA, USERNAME=XMEN}]");
                add("[Source:RowNo:1, ColumnNo:-1, Surplus:-1, Row:null] [Target:RowNo:2, ColumnNo:1, Surplus:1, Row:{COUNTRY=USA, USERNAME=XMEN}]");
            }
        };
        List<ListRowWrapper> targetList = getDefaultUsers();
        targetList.add(new ListRowWrapper("XMEN", "USA"));
        List<String> actuals = assertCompareTable(getDefaultUsers(), targetList);

        assertEquals(expected, actuals);
    }


    private List<String> assertCompareTable(List<ListRowWrapper> sourceList, List<ListRowWrapper> targetListList) throws MintLeafException {
        final List<String> actuals = new ArrayList<>();
        final ConsoleLogger logger = new ConsoleLogger();
        DataComparer dataComparer = new Mintleaf.ComparerBuilder().
                withSourceTable(sourceList).
                withTargetTable(targetListList).
                withMatchingResult(new ComparerListener() {
                    @Override
                    public void OnCompare(RowState sourceRow, RowState targetRow) {
                        actuals.add(String.format("[Source:%s] [Target:%s]", sourceRow, targetRow));
                        logger.info(actuals.get(actuals.size() - 1));
                    }
                }).
                build();


        dataComparer.execute();

        return actuals;
    }


    private ArrayList<ListRowWrapper> getDefaultUsers() {
        return new ArrayList<ListRowWrapper>() {
            {
                add(new ListRowWrapper("Vallr", "USA"));
                add(new ListRowWrapper("SM", "USA"));
            }
        };
    }


    private class ListRowWrapper extends Properties implements ComparableRow {

        private Properties properties = new Properties();
        private ColumnMetaDataCollection columnMetaDataCollection = new ColumnMetaDataCollection("LIST.USERS") {
            {
                add(new Column("USERNAME", Types.VARCHAR));
                add(new Column("COUNTRY", Types.VARCHAR));
            }
        };

        public ListRowWrapper(String userName, String country) {
            setProperty("USERNAME", userName);
            setProperty("COUNTRY", country);
        }

        @Override
        public Object getValue(int columnIndex) throws MintLeafException {
            return getValue(columnMetaDataCollection.get(columnIndex).getColumnName());
        }

        @Override
        public Object getValue(String columnName) throws MintLeafException {
            Object obj = getValue(columnName.toUpperCase());
            if (obj == null) {
                return "NULL";
            }
            return obj;
        }

        @Override
        public int count() throws MintLeafException {
            return columnMetaDataCollection.size();
        }

        @Override
        public ResultSetMetaData getMetaData() throws MintLeafException {
            return columnMetaDataCollection;
        }
    }


    private class User implements ComparableRow {

        private ColumnMetaDataCollection metaDataCollection;

        public String UserName;
        public String Country;

        @Override
        public Object getValue(int columnIndex) throws MintLeafException {
            switch (columnIndex) {
                case 0:
                    return UserName;
                case 1:
                    return Country;
            }
            return null;
        }

        @Override
        public ColumnMetaDataCollection getMetaData() throws MintLeafException {
            if (metaDataCollection == null) {
                metaDataCollection = new ColumnMetaDataCollection("USERS");
                metaDataCollection.add(new Column("UserName"));
                metaDataCollection.add(new Column("Country"));
            }
            return metaDataCollection;
        }
    }


    @Test
    public void compareList() throws SQLException, IOException, MintLeafException {
        List<User> sourceUserList = new ArrayList<User> (){
            {
                add(new User(){
                    {
                        UserName = "SM";
                        Country = "USA";
                    }
                });
            }
        };
        List<User> targetUserList = new ArrayList<User> (){
            {
                add(new User(){
                    {
                        UserName = "SM";
                        Country = "USA";
                    }
                });
            }
        };
        final ConsoleLogger logger = new ConsoleLogger();
        DataComparer dataComparer = new Mintleaf.ComparerBuilder().
                withSourceTable(sourceUserList).
                withTargetTable(targetUserList).
                withMatchingResult(new ComparerListener() {
                    @Override
                    public void OnCompare(RowState sourceRow, RowState targetRow) throws MintLeafException {

                        logger.info(String.format("[Source:%s] [Target:%s]", sourceRow, targetRow));
                        assertEquals(sourceRow.asString(), targetRow.asString());

                        String sourceColumnValue = sourceRow.Row.getValue(sourceRow.ColumnNumber).toString();
                        String targetColumnValue = sourceRow.Row.getValue(sourceRow.ColumnNumber).toString();
                        if (sourceColumnValue.equals(targetColumnValue)) {

                            logger.info("matches");
                        } else {
                            logger.info("no match");
                        }

                    }
                }).
                build();

        dataComparer.execute();
    }

}
