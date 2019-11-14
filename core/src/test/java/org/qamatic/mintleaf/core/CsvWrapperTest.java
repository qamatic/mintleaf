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

package org.qamatic.mintleaf.core;

import org.junit.Test;
import org.qamatic.mintleaf.ColumnMetaDataCollection;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.Row;
import org.qamatic.mintleaf.readers.CsvTable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by QAmatic Team on 3/10/17.
 */
public class CsvWrapperTest {

    @Test
    public void testReader() throws MintleafException {
        InputStream csvStream = BaseReader.getInputStreamFromFile("res:/users.csv");
        CsvTable<Row> csvRowListWrapper = new CsvTable<Row>(new InputStreamReader(csvStream));
        Row row = csvRowListWrapper.iterator().next();
        assertNotNull(row);
        assertEquals("1", row.asString("USERID"));
        assertNotNull(csvRowListWrapper.iterator().next());
        assertNotNull(csvRowListWrapper.iterator().next());
        assertNotNull(csvRowListWrapper.iterator().next());
        assertNotNull(csvRowListWrapper.iterator().next());
        assertNotNull(csvRowListWrapper.iterator().next());

        row = csvRowListWrapper.iterator().next();
        assertEquals("qamatic", row.asString("USERNAME"));
    }

    @Test
    public void testCSVMetaData() throws MintleafException, SQLException {
        InputStream csvStream = BaseReader.getInputStreamFromFile("res:/users.csv");
        CsvTable<Row> csvRowListWrapper = new CsvTable<Row>(new InputStreamReader(csvStream));
        Row row = csvRowListWrapper.iterator().next();
        assertNotNull(csvRowListWrapper.iterator().next());

        assertEquals(4, row.getMetaData().getColumnCount());
        assertEquals("CREATE_TIME", row.getMetaData().getColumnName(3));
        ColumnMetaDataCollection metaDataCollection = (ColumnMetaDataCollection) row.getMetaData();
        assertEquals("USERID", metaDataCollection.findColumn("USERID").getColumnName());
    }


}
