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
import org.qamatic.mintleaf.DbType;
import org.qamatic.mintleaf.core.BasicDatabaseContext;
import org.qamatic.mintleaf.core.Database;

import static org.junit.Assert.assertEquals;

public class UtilTests {

    BasicDatabaseContext builder = new BasicDatabaseContext(null, null, null, null);

    @Test
    public void testDbType() {
        assertEquals(DbType.H2, DbType.getDbType("jdbc:H2:/"));
        assertEquals(DbType.MYSQL, DbType.getDbType("jdbc:MySql:/"));
        assertEquals(DbType.MSSQL, DbType.getDbType("/jdbc:SqlServer:/"));
        assertEquals(DbType.ORACLE, DbType.getDbType("jdbc:Oracle:/"));
    }

    @Test
    public void testQueryImpl() {
        assertEquals(H2Db.class, Database.getQueryImplementation("jdbc:H2:/"));
        assertEquals(MySqlDb.class, Database.getQueryImplementation("jdbc:MySql:/"));
        assertEquals(MSSqlDb.class, Database.getQueryImplementation("jdbc:SqlServer:/"));
        assertEquals(OracleDb.class, Database.getQueryImplementation("jdbc:Oracle:/"));
    }

}
