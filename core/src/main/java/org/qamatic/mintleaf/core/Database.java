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

import org.qamatic.mintleaf.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database implements DbQueries {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(Database.class);
    protected final ConnectionContext connectionContext;
    private static final Map<String, Class<? extends Database>> registeredQueries = new HashMap<>();


    public Database(ConnectionContext connectionContext) {
        this.connectionContext = connectionContext;
    }

    public <T> List<T> query(String sql, ParameterBinding parameterBinding, final DataRowListener<T> listener) throws MintLeafException {

        final List<T> rows = new ArrayList<T>();
        FluentJdbc fluentJdbc = null;
        try {
            fluentJdbc = connectionContext.queryBuilder().withSql(sql).withParamValues(parameterBinding).query((row, dr) -> {
                try {
                    rows.add(listener.eachRow(row, dr));
                } catch (MintLeafException e) {
                    logger.error("error iterating resultset", e);
                }
                return null;
            });
        } finally {
            fluentJdbc.close();
        }
        return rows;
    }

    @Override
    public int queryInt(String sql, ParameterBinding parameterBinding) throws MintLeafException {
        FluentJdbc fluentJdbc = null;

        try {
            fluentJdbc = connectionContext.queryBuilder().withSql(sql).withParamValues(parameterBinding).first();
            return fluentJdbc.getResultSet().getInt(1);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        } finally {
            if (fluentJdbc != null)
                fluentJdbc.close();
        }
    }

    @Override
    public int getCount(String tableName, String whereClause, ParameterBinding parameterBinding) throws MintLeafException {

        String sql = "";
        if (whereClause != null) {
            sql = String.format("select count(*) from %s where %s", tableName, whereClause);
        } else {
            sql = String.format("select count(*) from %s", tableName);
        }

        return queryInt(sql, parameterBinding);
    }


    @Override
    public void executeSql(String sql, ParameterBinding parameterBinding) throws MintLeafException {
        FluentJdbc.executeSql(connectionContext, sql, parameterBinding);
    }

    public static void registerQueryImplementation(String jdbcUrlPrefix, Class<? extends Database> dbQueryClaz) {
        if (!registeredQueries.containsKey(jdbcUrlPrefix)) {
            registeredQueries.put(jdbcUrlPrefix, dbQueryClaz);
        }
    }

    public static Class<? extends Database> getQueryImplementation(String url) {
        if (DbType.getDbType(url) == null)
            return Database.class;
        return registeredQueries.get(DbType.getDbType(url).getJdbcUrlPrefix());
    }
}
