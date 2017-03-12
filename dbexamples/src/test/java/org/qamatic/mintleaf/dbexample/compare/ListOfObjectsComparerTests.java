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
import org.qamatic.mintleaf.data.ComparerListener;
import org.qamatic.mintleaf.dbexample.reportgenerator.ComparisonResultReportGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by qamatic on 3/5/16.
 * these are examples only but not unit tests
 */
public class ListOfObjectsComparerTests {

    private static ColumnMetaDataCollection getMetaData() throws MintLeafException {

        ColumnMetaDataCollection metaDataCollection = new ColumnMetaDataCollection("USERS");
        metaDataCollection.add(new Column("UserName"));
        metaDataCollection.add(new Column("Country"));

        return metaDataCollection;
    }

    @Test
    public void compareList() throws SQLException, IOException, MintLeafException {
        List<User> sourceUserList = getUsers();
        List<User> targetUserList = getUsers();
        final ConsoleLogger logger = new ConsoleLogger();
        doCompare(sourceUserList, targetUserList, (sourceColumn, targetColumn) -> {

            assertEquals(sourceColumn.asString(), targetColumn.asString());


        });
    }

    @Test
    public void compareDataGenerateReport() throws SQLException, IOException, MintLeafException {
        List<User> sourceUserList = getUsers();
        List<User> targetUserList = getUsers();
        ((User) targetUserList.get(0)).setUserName("SM1");
        final ConsoleLogger logger = new ConsoleLogger();
        ComparerListener reportListener = new ComparisonResultReportGenerator(new FileWriter("report.html"));
        doCompare(sourceUserList, targetUserList, reportListener);
    }

    private void doCompare(List<User> sourceUserList, List<User> targetUserList, ComparerListener listener) throws MintLeafException {

        DataComparer dataComparer = new Mintleaf.ComparerBuilder().
                withSourceTable(sourceUserList, getMetaData()).
                withTargetTable(targetUserList, getMetaData()).
                withMatchingResult(listener).
                build();

        dataComparer.execute();
    }

    private ArrayList<User> getUsers() {
        return new ArrayList<User>() {
            {
                add(new User("SM", "USA"));
            }
        };
    }


}
