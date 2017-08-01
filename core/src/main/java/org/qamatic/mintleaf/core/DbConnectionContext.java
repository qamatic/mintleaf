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
import org.qamatic.mintleaf.core.stdqueries.StandardQueries;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QAmatic Team on 3/19/17.
 */
public class DbConnectionContext<T extends DbQueryExtension> implements ConnectionContext<T> {

    private static final MintleafLogger logger = MintleafLogger.getLogger(DbConnectionContext.class);

    private Connection connection;
    private DriverSource driverSource;
    private boolean autoCloseable = true;
    private boolean inTransaction = false;

    public DbConnectionContext(DriverSource driverSource, boolean autoCloseable) {
        this.driverSource = driverSource;
        this.autoCloseable = autoCloseable;
    }

    private static DbQueryExtension createDbQueryInstance(String url, ConnectionContext connectionContext) {
        Class<? extends StandardQueries> queryImplClaz = StandardQueries.getQueryImplementation(url);
        DbQueryExtension dbQueries = null;
        try {
            Constructor constructor =
                    queryImplClaz.getConstructor(new Class[]{ConnectionContext.class});
            dbQueries = (DbQueryExtension) constructor.newInstance(connectionContext);
        } catch (InstantiationException e) {
            logger.error(e);
            MintleafException.throwException(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
            MintleafException.throwException(e);
        } catch (NoSuchMethodException e) {
            logger.error(e);
            MintleafException.throwException(e);
        } catch (InvocationTargetException e) {
            logger.error(e);
            MintleafException.throwException(e);
        }

        return dbQueries;
    }

    @Override
    public Connection getConnection() throws MintleafException {
        if (this.connection == null) {
            try {
                this.connection = driverSource.getConnection();
            } catch (SQLException e) {
                throw new MintleafException(e);
            }
        }
        return this.connection;
    }

    @Override
    public boolean isCloseable() {
        return this.autoCloseable;
    }

    @Override
    public void close() {
        if (isCloseable() && (this.connection != null)) {
            try {
                commitTransaction();
                this.connection.close();
                this.connection = null;
            } catch (Exception e) {
                MintleafException.throwException(e);
            }
        }
    }

    @Override
    public ConnectionContext beginTransaction() throws MintleafException {
        try {
            if (!inTransaction) {
                inTransaction = true;
                getConnection().setAutoCommit(false);
            }
            return this;
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }

    @Override
    public void commitTransaction() throws MintleafException {
        if (!inTransaction) {
            return;
        }

        try {
            getConnection().commit();
            inTransaction = false;
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }

    @Override
    public void rollbackTransaction() throws MintleafException {
        if (!inTransaction) {
            return;
        }
        try {
            getConnection().rollback();
            inTransaction = false;
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public T getDbQueries() {
        return (T) createDbQueryInstance(this.driverSource.getUrl(), this);
    }

    @Override
    public String toString() {
        return String.format("Driver: %s, InTransaction:%s, AutoCloseConnection:%s ", this.driverSource, this.inTransaction, isCloseable());
    }

    @Override
    public FluentJdbc.Builder queryBuilder() {
        return new FluentJdbc.Builder(this);
    }

    @Override
    public SqlResultSet selectQuery(String sql) {
        return queryBuilder().withSql(sql).buildSelect();
    }

    @Override
    public Executable<int[]> executeBatchSqls(List<String> batchSqls) {
        return new ExecuteQuery(this, batchSqls);
    }

    @Override
    public Executable<int[]> executeSql(String sql) {
        return queryBuilder().withSql(sql).buildExecute();
    }

    @Override
    public Executable<int[]> executeSql(String sql, Object[] parameterValues) {
        return queryBuilder().withSql(sql).withParamValues(parameterValues).buildExecute();
    }

    public <T> List<T> query(String sql, ParameterBinding parameterBinding, final RowMatchListener<T> listener) throws MintleafException {

        final List<T> rows = new ArrayList<T>();

        try (SqlResultSet sqlResultSet = queryBuilder().withSql(sql).withParamValues(parameterBinding).buildSelect()) {

            sqlResultSet.iterate((row, dr) -> {
                try {
                    if (listener.matches(dr))
                        rows.add(listener.eachRow(row, dr));
                    if (!listener.canContinueRead(dr)) {
                        return rows;
                    }
                } catch (MintleafException e) {
                    logger.error("error iterating resultset", e);
                }
                return null;
            });
        }
        return rows;
    }

    @Override
    public Executable<int[]> executeStoredProc(String procedureCall, StoredProcedure.CallType callType, ParameterBinding.Callable parameterBinding) {
        return executeStoredProc(procedureCall, callType, parameterBinding, null);
    }

    @Override
    public Executable<int[]> executeStoredProc(String procedureCall, StoredProcedure.CallType callType, ParameterBinding.Callable parameterBinding, ExecutionResultListener.Callable executionResultListener) {
        StoredProcedure proc = new StoredProcedure(this, procedureCall, callType, parameterBinding);
        proc.setExecutionResultListener(executionResultListener);
        return proc;
    }
}
