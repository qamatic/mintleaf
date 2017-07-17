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

import org.junit.Test;
import org.qamatic.mintleaf.core.BaseSqlReader;
import org.qamatic.mintleaf.data.CompareColumnState;
import org.qamatic.mintleaf.data.CompareRowState;
import org.qamatic.mintleaf.data.ComparerListener;
import org.qamatic.mintleaf.tools.CsvRowListWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by qamatic on 3/5/16.
 */
public class CsvVsListComparerTest {

    private static final MintleafLogger logger = MintleafLogger.getLogger(CsvVsListComparerTest.class);

    private static ColumnMetaDataCollection getMetaData() throws MintleafException {

        ColumnMetaDataCollection metaDataCollection = new ColumnMetaDataCollection("USERS");
        metaDataCollection.add(new Column("UserName"));
        metaDataCollection.add(new Column("Country"));
        metaDataCollection.add(new Column("UserLastName"));
        metaDataCollection.add(new Column("Id"));

        return metaDataCollection;
    }

    @Test
    public void compareList() throws SQLException, IOException, MintleafException {

        InputStream csvStream = BaseSqlReader.getInputStreamFromFile("res:/users.csv");
        CsvRowListWrapper csvRowListWrapper = new CsvRowListWrapper(new InputStreamReader(csvStream));


        List<User> targetUserList = new ArrayList<User>() {
            {
                add(new User("Aiden"));
                add(new User("Ethan, Allen"));
                add(new User("Liam"));
                add(new User("Noah"));
                add(new User("Jacob"));
                add(new User("SM"));
                add(new User("qamatic"));
            }
        };


        DataComparer dataComparer = new Mintleaf.DataComparerBuilder().
                withSourceTable(csvRowListWrapper).
                withTargetTable(targetUserList, getMetaData()).
                withMatchingResult(new ComparerListener() {

                    @Override
                    public void OnRowCompare(CompareRowState sourceRow, CompareRowState targetRow) throws MintleafException {
                        logger.info(String.format("[Source:%s] [Target:%s]", sourceRow, targetRow));
                        assertEquals(sourceRow.Row.getValue(1), targetRow.Row.getValue(0));
                    }

                    @Override
                    public void OnColumnCompare(CompareColumnState sourceColumn, CompareColumnState targetColumn) throws MintleafException {

                    }


                }).
                build();

        dataComparer.execute();
    }
}
