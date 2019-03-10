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

import org.junit.Assert;
import org.junit.Test;
import org.qamatic.mintleaf.readers.SqlStringReader;

import static org.junit.Assert.assertEquals;

public class MultiPartTest {

    @Test
    public void testChangeSet1() {
        String serialize = new ChangeSet().toString();
        Assert.assertTrue(serialize, serialize.contains("<changeSet id=\"\" delimiter=\"\"/>"));
    }

    @Test
    public void testChangeSet2() {
        String serialize = new ChangeSet("test", ";", "").toString();
        Assert.assertTrue(serialize, serialize.contains("<changeSet id=\"test\" delimiter=\";\"/>"));
    }

    @Test
    public void testMultiPartTagFromXml() {
        String xml = "<changeSet id=\"part1\" delimiter=\"/\" />";
        ChangeSet detail = ChangeSet.xmlToChangeSet(xml);
        assertEquals("part1", detail.getId());
        assertEquals("/", detail.getDelimiter());
    }


    @Test
    public void testSqlReaderWithVariablesTest() throws MintleafException {
        SqlStringReader reader = new SqlStringReader("INSERT INTO ${table_name} (USERID, USERNAME) VALUES (9, 'TN');");
        reader.getUserVariableMapping().put("table_name", "HRDB.USERS");
        reader.setDelimiter(";");
        final StringBuilder actual = new StringBuilder();

        reader.setReadListener(new ReadListener() {
            @Override
            public void eachRow(int rowNum, Row row) throws MintleafException {
                actual.append(((ChangeSet) row).getChangeSetSource());

            }
        });

        reader.read();

        assertEquals("INSERT INTO HRDB.USERS (USERID, USERNAME) VALUES (9, 'TN')", actual.toString());
    }

}
