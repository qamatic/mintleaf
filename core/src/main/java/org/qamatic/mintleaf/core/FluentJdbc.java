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

import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;

/**
 * Created by qamatic on 2/20/16.
 */
public class FluentJdbc implements SqlResultSet {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(FluentJdbc.class);
    private DataSource dataSource;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String sql;

    public FluentJdbc(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public FluentJdbc withSql(final String sql) throws MintLeafException {
        if (statement != null) {
            close();
        }
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(sql);
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
        this.sql = sql;
        return this;
    }


    public ResultSet getResultSet() throws MintLeafException {
        try {
            if (this.resultSet == null) {

                executeQuery();

            }
            return this.resultSet;
        } catch (MintLeafException e) {
            logger.error("error fetching data", e);
            throw new MintLeafException(e);
        }
    }

    public int[] executeBatch() throws MintLeafException {


        try {
            return this.statement.executeBatch();
        } catch (SQLException e) {
            logger.error("error executing batch:", e);
            throw new MintLeafException(e);
        }
    }


    public FluentJdbc close() {
        try {


            if (this.resultSet != null) {
                this.resultSet.close();
            }
            if (this.statement != null) {
                this.statement.close();
            }
            if (this.connection != null) {
                this.connection.close();
            }
            this.resultSet = null;
            this.connection = null;
            this.statement = null;
        } catch (SQLException e) {
            logger.error("FluentJdbc close()", e);
            throw new MintLeafException(e);
        } finally {
            return this;
        }
    }

    private PreparedStatement getPrepStmt() {
        return (PreparedStatement) statement;
    }

    public FluentJdbc executeQuery() throws MintLeafException {
        try {
            this.resultSet = getPrepStmt().executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }


    public FluentJdbc setNull(int parameterIndex, int sqlType) throws MintLeafException {
        try {
            getPrepStmt().setNull(parameterIndex, sqlType);
            return this;
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }


    public FluentJdbc setInt(int parameterIndex, int x) throws MintLeafException {
        try {
            getPrepStmt().setInt(parameterIndex, x);
            return this;
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }


    public FluentJdbc setString(int parameterIndex, String x) throws MintLeafException {
        try {
            getPrepStmt().setString(parameterIndex, x);
            return this;
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }


    public FluentJdbc clearParameters() throws MintLeafException {
        try {
            getPrepStmt().clearParameters();
            return this;
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }


    public FluentJdbc setObject(int parameterIndex, Object x, int targetSqlType) throws MintLeafException {
        try {
            getPrepStmt().setObject(parameterIndex, x, targetSqlType);
            return this;
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }


    public FluentJdbc setObject(int parameterIndex, Object x) throws MintLeafException {
        try {
            getPrepStmt().setObject(parameterIndex, x);
            return this;
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }


    public boolean execute() throws MintLeafException {
        try {
            logger.info(sql);
            return getPrepStmt().execute();
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }


    public void addBatch() throws MintLeafException {
        try {
            getPrepStmt().addBatch();
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }

    public FluentJdbc addBatch(String sql) throws MintLeafException {
        try {
            if (statement == null) {
                connection = dataSource.getConnection();
                statement = connection.createStatement();
            }
            statement.addBatch(sql);
            return this;
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }

    public FluentJdbc setDate(int parameterIndex, Date x, Calendar cal) throws MintLeafException {
        try {
            getPrepStmt().setDate(parameterIndex, x, cal);
            return this;
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }


    public FluentJdbc setTime(int parameterIndex, Time x, Calendar cal) throws MintLeafException {
        try {
            getPrepStmt().setTime(parameterIndex, x, cal);
            return this;
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }


    public FluentJdbc setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws MintLeafException {
        try {
            getPrepStmt().setTimestamp(parameterIndex, x, cal);
            return this;
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }

    public FluentJdbc withParamValues(Object[] bindingParams) throws MintLeafException {

        if (bindingParams == null)
            return this;
        for (int i = 0; i < bindingParams.length; i++) {
            if (bindingParams[i] instanceof Integer) {
                setInt(i + 1, (Integer) bindingParams[i]);
            } else {
                setString(i + 1, (String) bindingParams[i]);
            }
        }

        return this;
    }


    public FluentJdbc first() throws MintLeafException {
        try {
            getResultSet().next();
            return this;
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
    }

    public <T> FluentJdbc query(final DataRowListener<T> listener) throws MintLeafException, MintLeafException {

        try {
            int i = 0;
            while (getResultSet().next()) {
                listener.eachRow(i++, new ResultSetRowWrapper(getResultSet()));
            }
        } catch (SQLException e) {

            logger.error(e);
            throw new MintLeafException(e);
        }
        return this;
    }

    public RowListWrapper asRowListWrapper() throws MintLeafException {
        ResultSetRowListWrapper rowListWrapper = new ResultSetRowListWrapper();
        rowListWrapper.setResultSet(this.getResultSet());
        return rowListWrapper;
    }

    public static void executeSql(DriverSource driverSource, String sql, Object[] paramValues) throws MintLeafException {
        FluentJdbc fluentJdbc = null;
        try {
            fluentJdbc = driverSource.queryBuilder().withSql(sql.toString()).withParamValues(paramValues);
            fluentJdbc.execute();
        } catch (MintLeafException e) {
            logger.error("error in executing query", e);
            throw new MintLeafException(e);
        } finally {
            fluentJdbc.close();
        }
    }

}
