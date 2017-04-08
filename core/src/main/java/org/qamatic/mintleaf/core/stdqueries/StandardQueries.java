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

package org.qamatic.mintleaf.core.stdqueries;

import org.qamatic.mintleaf.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class StandardQueries implements DbQueryExtension {

    private static final MintleafLogger logger = MintleafLogger.getLogger(StandardQueries.class);
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

    public static String[] utilsSplitDottedObjectNames(String objectName) throws MintleafException {
        final String[] objectNames = objectName.split(Pattern.quote("."));
        if (objectNames.length != 2) {
            throw new MintleafException(String.format("getMetaData expects objectName parameter to be <SCHEMA NAME>.<OBJECT NAME> for example HRDB.COUNTRIES.  Invalid value: %s", objectName));
        }
        return objectNames;
    }

    public <T> List<T> query(String sql, ParameterBinding parameterBinding, final DataRowListener<T> listener) throws MintleafException {
        return this.connectionContext.query(sql, parameterBinding, listener);
    }

    @Override
    public int queryInt(String sql, ParameterBinding parameterBinding) throws MintleafException {
        try (SqlResultSet sqlResultSet = this.connectionContext.queryBuilder().withSql(sql).withParamValues(parameterBinding).buildSelect()) {

            try {
                return sqlResultSet.first().getInt(1);
            } catch (SQLException e) {
                throw new MintleafException(e);
            }
        }
    }


}
