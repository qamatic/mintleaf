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

/**
 * Created by qamatic on 1/5/16.
 */
public enum DbType {
    H2("H2 Database", "jdbc:h2:"),
    MYSQL("MySQL database", "jdbc:mysql:"),
    POSTGRESQL("Postgres database", "jdbc:postgresql:"),
    ORACLE("Oracle database", "jdbc:oracle:"),
    MSSQL("Microsoft SQL Server database", "jdbc:sqlserver:");

    private final String name;
    private String jdbcUrlPrefix;

    private DbType(String name, String jdbcUrlPrefix) {
        this.name = name;
        this.jdbcUrlPrefix = jdbcUrlPrefix;
    }

    public static DbType getDbType(String url) {
        url = url.toLowerCase();
        for (DbType dt : DbType.values()) {
            if (url.contains(dt.jdbcUrlPrefix)) {
                return dt;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getJdbcUrlPrefix() {
        return this.jdbcUrlPrefix;
    }

}
