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

import org.qamatic.mintleaf.builders.*;
import org.qamatic.mintleaf.core.ExecuteQuery;
import org.qamatic.mintleaf.core.FluentJdbc;
import org.qamatic.mintleaf.core.JdbcDriverSource;

import java.util.List;

/**
 * Created by qamatic on 3/1/16.
 */
public final class Mintleaf {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(Mintleaf.class);

    public static FluentJdbc.Builder selectQuery(ConnectionContext connectionContext) {
        return new FluentJdbc.Builder(connectionContext);
    }

    public static SqlResultSet selectQuery(ConnectionContext connectionContext, String sql) {
        return selectQuery(connectionContext).withSql(sql).buildSelect();
    }

    public static FluentJdbc.Builder executeSql(ConnectionContext connectionContext) {
        return new FluentJdbc.Builder(connectionContext);
    }

    public static Executable<int[]> executeBatchSqls(ConnectionContext connectionContext, List<String> batchSqls) {
        return new ExecuteQuery(connectionContext, batchSqls);
    }

    public static Executable<int[]> executeSql(ConnectionContext connectionContext, String sql) {
        return executeSql(connectionContext).withSql(sql).buildExecute();
    }

    public static Executable<int[]> executeSql(ConnectionContext connectionContext, String sql, Object[] parameterValues) {
        return executeSql(connectionContext).withSql(sql).withParamValues(parameterValues).buildExecute();
    }

    public static ComparerBuilder comparer() {
        return new ComparerBuilder();
    }

    public static DatabaseBuilder database() {
        return new DatabaseBuilder();
    }

    public static Database database(String url, String username, String password) {
        return new DatabaseBuilder().withDriverSource(JdbcDriverSource.class).withUrl(url).withUsername(username).withPassword(password).build();
    }

    public static DbToCsvBuilder dbTocsvTransfer() {
        return new DbToCsvBuilder();
    }

    public static CsvToDbBuilder csvTodbTransfer() {
        return new CsvToDbBuilder();
    }

    public static DbToDbBuilder dbTodbTransfer() {
        return new DbToDbBuilder();
    }

}
