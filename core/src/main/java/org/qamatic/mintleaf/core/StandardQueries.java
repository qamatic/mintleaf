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
import java.util.regex.Pattern;

import static org.qamatic.mintleaf.Mintleaf.selectQuery;

public class StandardQueries implements DbQueries {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(StandardQueries.class);
    private static final Map<String, Class<? extends StandardQueries>> registeredQueries = new HashMap<>();

    static {
        StandardQueries.registerQueryImplementation(DbType.H2.getJdbcUrlPrefix(), H2Queries.class);
        StandardQueries.registerQueryImplementation(DbType.MSSQL.getJdbcUrlPrefix(), MSSqlQueries.class);
        StandardQueries.registerQueryImplementation(DbType.MYSQL.getJdbcUrlPrefix(), MySqlQueries.class);
        StandardQueries.registerQueryImplementation(DbType.ORACLE.getJdbcUrlPrefix(), OracleQueries.class);
    }

    protected final ConnectionContext connectionContext;

    public StandardQueries(ConnectionContext connectionContext) {
        this.connectionContext = connectionContext;
    }

    public static void registerQueryImplementation(String jdbcUrlPrefix, Class<? extends StandardQueries> dbQueryClaz) {
        registeredQueries.put(jdbcUrlPrefix, dbQueryClaz);
    }

    public static Class<? extends StandardQueries> getQueryImplementation(String url) {
        if (DbType.getDbType(url) == null)
            return StandardQueries.class;
        return registeredQueries.get(DbType.getDbType(url).getJdbcUrlPrefix());
    }

    public <T> List<T> query(String sql, ParameterBinding parameterBinding, final DataRowListener<T> listener) throws MintLeafException {

        final List<T> rows = new ArrayList<T>();

        try (SqlResultSet sqlResultSet = selectQuery(connectionContext).withSql(sql).withParamValues(parameterBinding).buildSelect()) {

            sqlResultSet.iterate((row, dr) -> {
                try {
                    rows.add(listener.eachRow(row, dr));
                } catch (MintLeafException e) {
                    logger.error("error iterating resultset", e);
                }
                return null;
            });
        }
        return rows;
    }

    @Override
    public int queryInt(String sql, ParameterBinding parameterBinding) throws MintLeafException {
        try (SqlResultSet sqlResultSet = selectQuery(connectionContext).withSql(sql).withParamValues(parameterBinding).buildSelect()) {

            try {
                return sqlResultSet.first().getInt(1);
            } catch (SQLException e) {
                throw new MintLeafException(e);
            }
        }
    }

    public static String[] utilsSplitDottedObjectNames(String objectName) throws MintLeafException {
        final String[] objectNames = objectName.split(Pattern.quote("."));
        if (objectNames.length != 2) {
            throw new MintLeafException(String.format("getMetaData expects objectName parameter to be <SCHEMA NAME>.<OBJECT NAME> for example HRDB.COUNTRIES.  Invalid value: %s", objectName));
        }
        return objectNames;
    }


}
