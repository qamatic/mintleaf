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

package org.qamatic.mintleaf.dbexample.compare;

import org.junit.Test;
import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.core.BaseSqlReader;
import org.qamatic.mintleaf.data.ComparerListener;
import org.qamatic.mintleaf.data.RowState;
import org.qamatic.mintleaf.tools.CsvRowListWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by qamatic on 3/5/16.
 */
public class CsvVsListComparerTests {

    @Test
    public void compareList() throws SQLException, IOException, MintLeafException {

        InputStream csvStream = BaseSqlReader.getInputStreamFromFile("res:/user.csv");
        CsvRowListWrapper csvRowListWrapper = new CsvRowListWrapper(new InputStreamReader(csvStream));

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
                withSourceTable(csvRowListWrapper).
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

}
