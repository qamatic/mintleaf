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
import org.qamatic.mintleaf.SqlReaderListener;
import org.qamatic.mintleaf.core.SqlStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.*;

public class SqlStreamReaderTest {

    private String actual_emptypackage_block1;
    private String actual_emptypackage_block2;

    @Test
    public void testDelimiterString() {
        SqlStreamReader reader = new SqlStreamReader(null);
        reader.setDelimiter(";");
        assertEquals(";", reader.getDelimiter());
    }

    @Test
    public void testDelimiterStringDefault() {
        SqlStreamReader reader = new SqlStreamReader(null);
        assertEquals("/", reader.getDelimiter());
    }

    @Test
    public void testSqlReaderListnerDefault() {
        SqlStreamReader reader = new SqlStreamReader(null);
        assertNull(reader.getReaderListener());
    }

    @Test
    public void testSqlReaderListnerTest1() {
        SqlStreamReader reader = new SqlStreamReader(null);
        reader.setReaderListener(new EmptyPackageReadListner());
        assertNotNull(reader.getReaderListener());
    }

    @Test
    public void testSqlReaderReadTest() throws IOException, SQLException {

        InputStream iStream = this.getClass().getResourceAsStream("/EmptyPackage.sql");
        SqlStreamReader reader = new SqlStreamReader(iStream);


        final StringBuilder actual = new StringBuilder();
        SqlReaderListener listner = new EmptyPackageReadListner() {
            @Override
            public void onReadChild(StringBuilder sql, Object context) throws SQLException, IOException {
                actual.append(sql.toString());
            }
        };
        reader.setReaderListener(listner);
        reader.read();

        StringBuilder expected = new StringBuilder();

        expected.append("create or replace package EmptyPackage\n");
        expected.append("as\n");
        expected.append("\n");
        expected.append("end EmptyPackage;");
        expected.append("create or replace\n");
        expected.append("package body EmptyPackage\n");
        expected.append("as\n");
        expected.append("\n");
        expected.append("end EmptyPackage;");

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testSqlReaderListnerTest2() throws IOException, SQLException {

        SqlReaderListener listner = new EmptyPackageReadListner();
        InputStream iStream = this.getClass().getResourceAsStream("/EmptyPackage.sql");
        SqlStreamReader reader = new SqlStreamReader(iStream);
        reader.setReaderListener(listner);
        actual_emptypackage_block1 = null;
        actual_emptypackage_block2 = null;

        reader.read();

        StringBuilder expected = new StringBuilder();

        expected.append("create or replace package EmptyPackage\n");
        expected.append("as\n");
        expected.append("\n");
        expected.append("end EmptyPackage;");

        assertEquals(expected.toString(), actual_emptypackage_block1);

        expected.setLength(0);
        expected.append("create or replace\n");
        expected.append("package body EmptyPackage\n");
        expected.append("as\n");
        expected.append("\n");
        expected.append("end EmptyPackage;");

        assertEquals(expected.toString(), actual_emptypackage_block2);

    }


    private class EmptyPackageReadListner implements SqlReaderListener {

        @Override
        public void onReadChild(StringBuilder sql, Object context) throws SQLException, IOException {
            if (actual_emptypackage_block1 == null) {
                actual_emptypackage_block1 = sql.toString();
            } else if (actual_emptypackage_block2 == null) {
                actual_emptypackage_block2 = sql.toString();
            }
        }

        @Override
        public SqlReaderListener getChildReaderListener() {
            return null;
        }

        @Override
        public void setChildReaderListener(SqlReaderListener childListener) {

        }

        @Override
        public Map<String, String> getTemplateValues() {

            throw new UnsupportedOperationException();
        }
    }
}
