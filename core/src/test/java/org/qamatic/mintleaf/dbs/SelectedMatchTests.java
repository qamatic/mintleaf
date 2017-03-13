/*
 *
 *  *
 *  *  *
 *  *  *  * <!--
 *  *  *  *   ~
 *  *  *  *   ~ The MIT License (MIT)
 *  *  *  *   ~
 *  *  *  *   ~ Copyright (c) 2010-2017 QAMatic
 *  *  *  *   ~
 *  *  *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *  *  *  *   ~ in the Software without restriction, including without limitation the rights
 *  *  *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *  *  *  *   ~ furnished to do so, subject to the following conditions:
 *  *  *  *   ~
 *  *  *  *   ~ The above copyright notice and this permission notice shall be included in all
 *  *  *  *   ~ copies or substantial portions of the Software.
 *  *  *  *   ~
 *  *  *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  *  *   ~ SOFTWARE.
 *  *  *  *   ~
 *  *  *  *   ~
 *  *  *  *   -->
 *  *  *
 *  *  *
 *  *
 *
 */

package org.qamatic.mintleaf.dbs;

import org.junit.Test;
import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.data.SelectedColumnMatcher;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by senips on 3/12/17.
 */
public class SelectedMatchTests {

    @Test
    public void testColumnSelection(){
        SelectedColumnMatcher matcher = new SelectedColumnMatcher("susername=tusername");

        assertEquals("susername", matcher.getSourceColumns().get(0));
        assertEquals("tusername", matcher.getTargetColumns().get(0));

    }

    @Test(expected= MintLeafException.class)
    public void testIncorrectMaps(){
        SelectedColumnMatcher matcher = new SelectedColumnMatcher("susernametusername");
        assertEquals("susername", matcher.getSourceColumns().get(0));
    }

    @Test(expected= MintLeafException.class)
    public void testIncorrectMaps2(){
        SelectedColumnMatcher matcher = new SelectedColumnMatcher("");
        assertEquals("susername", matcher.getSourceColumns().get(0));
    }


    @Test
    public void testCompareSelected() throws MintLeafException {

        final List<String> expected = new ArrayList<String>() {
            {
                add("[Source:RowNo:0, ColumnNo:0, Surplus:0, Value:Tom] [Target:RowNo:0, ColumnNo:0, Surplus:0, Value:Tom]");
                add("[Source:RowNo:0, ColumnNo:2, Surplus:0, Value:Hanks] [Target:RowNo:0, ColumnNo:2, Surplus:0, Value:Hanks]");
                add("[Source:RowNo:1, ColumnNo:0, Surplus:0, Value:Bruce] [Target:RowNo:1, ColumnNo:0, Surplus:0, Value:Bruce]");
                add("[Source:RowNo:1, ColumnNo:2, Surplus:0, Value:Lee] [Target:RowNo:1, ColumnNo:2, Surplus:0, Value:Lee]");
            }
        };
        List<User> sourceData =  new ArrayList<User>() {
            {
                add(new User(0, "Tom", "Hanks"));
                add(new User(1, "Bruce", "Lee"));
            }
        };
        List<User> targetData =  new ArrayList<User>() {
            {
                add(new User(0, "Tom", "Hanks"));
                add(new User(1, "Bruce", "Lee"));
            }
        };
        List<String> actuals = assertCompareTable(sourceData, targetData, "FIRSTNAME=FNAME,LASTNAME=LNAME");
        assertEquals(expected, actuals);
    }


    private static List<String> assertCompareTable(List<User> sourceList, List<User> targetListList, String selectedColumnMaps) throws MintLeafException {
        final ColumnMetaDataCollection sourceColumnDefs = new ColumnMetaDataCollection("LIST.USERS") {
            {
                add(new Column("FIRSTNAME", Types.VARCHAR));
                add(new Column("COUNTRY", Types.VARCHAR));
                add(new Column("LASTNAME", Types.VARCHAR));
                add(new Column("ID", Types.INTEGER));
            }
        };

        final ColumnMetaDataCollection targetColumnDefs = new ColumnMetaDataCollection("LIST.USERS") {
            {

                add(new Column("FNAME", Types.VARCHAR));
                add(new Column("COUNTRY", Types.VARCHAR));
                add(new Column("LNAME", Types.VARCHAR));
                add(new Column("ID", Types.INTEGER));
            }
        };

        final List<String> actuals = new ArrayList<>();
        final ConsoleLogger logger = new ConsoleLogger();
        DataComparer dataComparer = new Mintleaf.ComparerBuilder().
                withSourceTable(sourceList, sourceColumnDefs).
                withTargetTable(targetListList, targetColumnDefs).
                withSelectedColumnMaps(selectedColumnMaps).
                withMatchingResult((sourceCol, targetCol) -> {
                    actuals.add(String.format("[Source:%s] [Target:%s]", sourceCol.toLog(), targetCol.toLog()));
                    logger.info(actuals.get(actuals.size() - 1));
                }).
                build();


        dataComparer.execute();

        return actuals;
    }

}
