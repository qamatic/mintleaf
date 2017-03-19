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
import org.qamatic.mintleaf.ColumnMetaDataCollection;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.core.BaseSqlReader;
import org.qamatic.mintleaf.tools.CsvRowListWrapper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by QAmatic Team on 3/10/17.
 */
public class CsvWrapperTest {

    @Test
    public void testReader() throws MintLeafException {
        InputStream csvStream = BaseSqlReader.getInputStreamFromFile("res:/users.csv");
        CsvRowListWrapper csvRowListWrapper = new CsvRowListWrapper(new InputStreamReader(csvStream));
        assertTrue(csvRowListWrapper.moveNext());
        assertEquals("1", csvRowListWrapper.row().asString("USERID"));
        assertTrue(csvRowListWrapper.moveNext());
        assertTrue(csvRowListWrapper.moveNext());
        assertTrue(csvRowListWrapper.moveNext());
        assertTrue(csvRowListWrapper.moveNext());
        assertTrue(csvRowListWrapper.moveNext());
        assertTrue(csvRowListWrapper.moveNext());
        assertFalse(csvRowListWrapper.moveNext());
        assertEquals("qamatic", csvRowListWrapper.row().asString("USERNAME"));
    }

    @Test
    public void testCSVMetaData() throws MintLeafException, SQLException {
        InputStream csvStream = BaseSqlReader.getInputStreamFromFile("res:/users.csv");
        CsvRowListWrapper csvRowListWrapper = new CsvRowListWrapper(new InputStreamReader(csvStream));
        assertTrue(csvRowListWrapper.moveNext());

        assertEquals(4, csvRowListWrapper.row().getMetaData().getColumnCount());
        assertEquals("CREATE_TIME", csvRowListWrapper.row().getMetaData().getColumnName(3));
        ColumnMetaDataCollection metaDataCollection = (ColumnMetaDataCollection) csvRowListWrapper.row().getMetaData();
        assertEquals("USERID", metaDataCollection.findColumn("USERID").getColumnName());
    }


}
