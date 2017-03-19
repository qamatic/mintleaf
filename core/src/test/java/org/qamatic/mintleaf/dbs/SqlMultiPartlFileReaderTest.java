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

import org.junit.Test;
import org.qamatic.mintleaf.ChangeSet;
import org.qamatic.mintleaf.ChangeSetReader;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.SqlReaderListener;
import org.qamatic.mintleaf.core.SqlChangeSetFileReader;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.*;

public class SqlMultiPartlFileReaderTest {

    private String actual_part1;
    private String actual_part2;
    private String actual_part3;

    @Test
    public void testSqlChangeSetReaderCount() throws IOException, SQLException, MintLeafException {
        InputStream iStream = this.getClass().getResourceAsStream("/multipart2.sql");
        ChangeSetReader reader = new SqlChangeSetFileReader(iStream);
        reader.read();
        assertEquals(3, reader.getChangeSets().size());
    }

    @Test
    public void testSqlChangeSetReaderSections() throws IOException, SQLException, MintLeafException {
        ChangeSetReader reader = new SqlChangeSetFileReader("res:/multipart2.sql");
        reader.read();

        assertTrue(reader.getChangeSets().containsKey("part1"));
        assertTrue(reader.getChangeSets().containsKey("part2"));
        assertTrue(reader.getChangeSets().containsKey("part3"));
        ChangeSet part1 = reader.getChangeSets().get("part1");
        assertEquals(getPart1Data(), part1.getChangeSetSource());

        ChangeSet part2 = reader.getChangeSets().get("part2");
        assertEquals(getPart2Data(), part2.getChangeSetSource());

        ChangeSet part3 = reader.getChangeSets().get("part3");
        assertEquals(getPart3Data(), part3.getChangeSetSource());
    }

    @Test
    public void testgetSectionDetail() {
        String json = "ffccc";
        ChangeSet detail = ChangeSet.xmlToChangeSet(json);

        assertNull(detail);

        detail = ChangeSet.xmlToChangeSet("<ChangeSet id=\"delete tables\" delimiter=\"/\" />");
        assertNotNull(detail);
        assertEquals("delete tables", detail.getId());
        assertEquals("/", detail.getDelimiter());

        detail = ChangeSet.xmlToChangeSet("<ChangeSet id=\"delete tables\"  />");
        assertNotNull(detail);
        assertEquals("delete tables", detail.getId());
        assertEquals("", detail.getDelimiter());

    }

    private String getPart1Data() {
        StringBuilder expected = new StringBuilder();
        expected.append("-- empty package\n");
        expected.append("create or replace package EmptyPackage\n");
        expected.append("as\n");
        expected.append("end EmptyPackage;\n");
        expected.append("/");
        return expected.toString();
    }

    private String getPart2Data() {
        StringBuilder expected = new StringBuilder();
        expected.append("create or replace\n");
        expected.append("package body EmptyPackage\n");
        expected.append("as\n");
        expected.append("end EmptyPackage;\n");
        expected.append("/");
        return expected.toString();
    }

    private String getPart3Data() {
        StringBuilder expected = new StringBuilder();
        expected.append("CREATE TABLE TABLE1\n");
        expected.append("(\n");
        expected.append("ID NUMBER (18)  NOT NULL ,\n");
        expected.append("NAME VARCHAR2 (60 CHAR)  NOT NULL\n");
        expected.append(")\n");
        expected.append(";\n");
        expected.append("CREATE TABLE TABLE2\n");
        expected.append("(\n");
        expected.append("ID NUMBER (18)  NOT NULL ,\n");
        expected.append("NAME VARCHAR2 (60 CHAR)  NOT NULL\n");
        expected.append(")\n");
        expected.append(";");
        return expected.toString();
    }

    @Test
    public void testSqlChangeSetReaderListnerTest() throws IOException, SQLException, MintLeafException {

        SqlReaderListener listner = new ChangeSetFileReadListner();
        InputStream iStream = this.getClass().getResourceAsStream("/multipart.sql");
        SqlChangeSetFileReader reader = new SqlChangeSetFileReader(iStream);
        reader.setReaderListener(listner);
        actual_part1 = null;
        actual_part2 = null;
        actual_part3 = null;

        reader.read();

        assertEquals(getPart1Data(), actual_part1);

        assertEquals(getPart2Data(), actual_part2);
        assertEquals(getPart3Data(), actual_part3);

    }


    private class ChangeSetFileReadListner implements SqlReaderListener {

        @Override
        public void onReadChild(StringBuilder sql, Object context) throws MintLeafException {
            if (actual_part1 == null) {
                actual_part1 = sql.toString();
            } else if (actual_part2 == null) {
                actual_part2 = sql.toString();
            } else if (actual_part3 == null) {
                actual_part3 = sql.toString();
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
