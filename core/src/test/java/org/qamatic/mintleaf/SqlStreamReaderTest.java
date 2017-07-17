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
import org.qamatic.mintleaf.core.SqlStreamReader;

import java.io.InputStream;

import static org.junit.Assert.*;

public class SqlStreamReaderTest {

    private String actual_emptypackage_block1;
    private String actual_emptypackage_block2;

    @Test
    public void testDelimiterString() {
        SqlStreamReader reader = new SqlStreamReader("nofile");
        reader.setDelimiter(";");
        assertEquals(";", reader.getDelimiter());
    }

    @Test
    public void testDelimiterStringDefault() {
        SqlStreamReader reader = new SqlStreamReader("nofile");
        assertEquals("/", reader.getDelimiter());
    }

    @Test
    public void testSqlReaderListnerDefault() throws MintleafException {
        SqlStreamReader reader = new SqlStreamReader("nofile");
        assertNull(reader.getReadListener());
    }

    @Test
    public void testSqlReaderListnerTest1() throws MintleafException {
        SqlStreamReader reader = new SqlStreamReader("nofile");
        reader.setReadListener(new EmptyPackageReadListner());
        assertNotNull(reader.getReadListener());
    }

    @Test
    public void testSqlReaderReadTest() throws MintleafException {

        InputStream iStream = this.getClass().getResourceAsStream("/EmptyPackage.sql");
        SqlStreamReader reader = new SqlStreamReader(iStream);


        final StringBuilder actual = new StringBuilder();
        ReadListener listner = new EmptyPackageReadListner() {

            @Override
            public Object eachRow(int rowNum, Row row) throws MintleafException {
                actual.append(((ChangeSet) row).getChangeSetSource());
                return null;
            }


        };
        reader.setReadListener(listner);
        reader.read();

        StringBuilder expected = new StringBuilder();

        expected.append("create or replace package EmptyPackage\n");
        expected.append("as\n");
        expected.append("end EmptyPackage;");
        expected.append("create or replace\n");
        expected.append("package body EmptyPackage\n");
        expected.append("as\n");
        expected.append("end EmptyPackage;");

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testSqlReaderListnerTest2() throws MintleafException {

        ReadListener listner = new EmptyPackageReadListner();
        InputStream iStream = this.getClass().getResourceAsStream("/EmptyPackage.sql");
        SqlStreamReader reader = new SqlStreamReader(iStream);
        reader.setReadListener(listner);
        actual_emptypackage_block1 = null;
        actual_emptypackage_block2 = null;

        reader.read();

        StringBuilder expected = new StringBuilder();

        expected.append("create or replace package EmptyPackage\n");
        expected.append("as\n");
        expected.append("end EmptyPackage;");

        assertEquals(expected.toString(), actual_emptypackage_block1);

        expected.setLength(0);
        expected.append("create or replace\n");
        expected.append("package body EmptyPackage\n");
        expected.append("as\n");
        expected.append("end EmptyPackage;");

        assertEquals(expected.toString(), actual_emptypackage_block2);

    }

    private class EmptyPackageReadListner implements ReadListener {


        @Override
        public Object eachRow(int rowNum, Row row) throws MintleafException {

            if (actual_emptypackage_block1 == null) {
                actual_emptypackage_block1 = ((ChangeSet) row).getChangeSetSource();
            } else if (actual_emptypackage_block2 == null) {
                actual_emptypackage_block2 = ((ChangeSet) row).getChangeSetSource();
            }
            return null;
        }
    }
}
