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

import org.junit.Assert;
import org.junit.Test;
import org.qamatic.mintleaf.Column;
import org.qamatic.mintleaf.DbMetaDataCollection;
import org.qamatic.mintleaf.MintLeafException;

import java.sql.Date;
import java.sql.Types;

import static org.junit.Assert.assertEquals;

public class ColumnTest {
    @Test
    public void testSqlDataTypeCheckNULL() {
        Assert.assertEquals(Types.NULL, new Column().getDatatype());
    }

    @Test
    public void testJavaDataTypeDecimal() {
        Column d = new Column();
        d.setDatatype(Types.DECIMAL);
        assertEquals(d.getJavaDataType(), Integer.class);
        d.setDecimalDigits(2);
        assertEquals(d.getJavaDataType(), Double.class);
    }

    @Test
    public void testJavaDataTypeDouble() {
        Column d = new Column();
        d.setDatatype(Types.DOUBLE);
        assertEquals(d.getJavaDataType(), Integer.class);
        d.setDecimalDigits(2);
        assertEquals(d.getJavaDataType(), Double.class);
    }

    @Test
    public void testJavaDataTypeFloat() {
        Column d = new Column();
        d.setDatatype(Types.FLOAT);
        assertEquals(d.getJavaDataType(), Integer.class);
        d.setDecimalDigits(2);
        assertEquals(d.getJavaDataType(), Double.class);
    }

    @Test
    public void testJavaDataTypeNumeric() {
        Column d = new Column();
        d.setDatatype(Types.NUMERIC);
        assertEquals(d.getJavaDataType(), Integer.class);
        d.setDecimalDigits(2);
        assertEquals(d.getJavaDataType(), Double.class);
    }

    @Test
    public void testJavaDataTypeDate() {
        Column d = new Column();
        d.setDatatype(Types.DATE);
        assertEquals(d.getJavaDataType(), Date.class);
    }

    @Test
    public void testJavaDataTypeDefault() {
        Column d = new Column();
        d.setDatatype(Types.NCHAR);
        assertEquals(d.getJavaDataType(), String.class);
    }

    @Test
    public void testMetaDataCollection() {
        DbMetaDataCollection dbMetaDataCollection = new DbMetaDataCollection("HRDB.COUNTRIES");
        assertEquals("HRDB.COUNTRIES", dbMetaDataCollection.getObjectName());
    }

    @Test(expected = MintLeafException.class)
    public void testMetaDataCollectionDupeColumn() {
        DbMetaDataCollection dbMetaDataCollection = new DbMetaDataCollection("HRDB.COUNTRIES");
        dbMetaDataCollection.add(new Column("X", Types.NCHAR));
        dbMetaDataCollection.add(new Column("Y", Types.DATE));
        dbMetaDataCollection.add(new Column("Y", Types.DOUBLE));
        assertEquals("HRDB.COUNTRIES", dbMetaDataCollection.getObjectName());
    }


    @Test(expected = MintLeafException.class)
    public void testMetaDataCollectionDupeColumn2() {
        DbMetaDataCollection dbMetaDataCollection = new DbMetaDataCollection("HRDB.COUNTRIES");
        dbMetaDataCollection.add(new Column("X", Types.NCHAR));
        dbMetaDataCollection.add(new Column("Y", Types.DATE));
        dbMetaDataCollection.add(1, new Column("Y", Types.DOUBLE));
        assertEquals("HRDB.COUNTRIES", dbMetaDataCollection.getObjectName());
    }

}
