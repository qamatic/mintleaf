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

package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Database implements DbQueries {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(Database.class);
    protected final DriverSource driverSource;


    public Database(DriverSource datasource) {

        this.driverSource = datasource;
        this.driverSource.setDbQueries(this);

    }


    public <T> List<T> query(String sql, final DataRowListener<T> listener) throws SQLException, MintLeafException {

        final List<T> rows = new ArrayList<T>();

        FluentJdbc fluentJdbc = driverSource.queryBuilder().withSql(sql).query(new DataRowListener<Object>() {

            public T eachRow(int row, RowWrapper dr) {
                try {
                    rows.add(listener.eachRow(row, dr));
                } catch (MintLeafException e) {
                    logger.error("error iterating resultset", e);
                }
                return null;
            }
        }).close();
        return rows;
    }

    @Override
    public int queryInt(String sql, Object[] whereClauseValues) {
        FluentJdbc fluentJdbc = null;
        try {
            fluentJdbc = driverSource.queryBuilder().withSql(sql).withParamValues(whereClauseValues).first();
            return fluentJdbc.getResultSet().getInt(1);

        } catch (SQLException e) {
            logger.error("getCount()", e);

        } finally {
            if (fluentJdbc != null) {
                fluentJdbc.close();
            }
        }
        return -1;
    }

    @Override
    public int getCount(String tableName, String whereClause, Object[] whereClauseValues) {

        String sql = "";
        if (whereClause != null) {
            sql = String.format("select count(*) from %s where %s", tableName, whereClause);
        } else {
            sql = String.format("select count(*) from %s", tableName);
        }

        return queryInt(sql, whereClauseValues);
    }


    @Override
    public int getCount(String tableName) {
        return getCount(tableName, null, null);

    }


}
