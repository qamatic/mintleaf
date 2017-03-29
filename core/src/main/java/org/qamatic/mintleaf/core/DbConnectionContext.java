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

/**
 * Created by QAmatic Team on 3/19/17.
 */
public class DbConnectionContext<T extends DbQueries> implements ConnectionContext<T> {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(DbConnectionContext.class);

    private Connection connection;
    private DriverSource driverSource;
    private boolean autoCloseable = true;
    private boolean inTransaction = false;

    public DbConnectionContext(DriverSource driverSource, boolean autoCloseable) {
        this.driverSource = driverSource;
        this.autoCloseable = autoCloseable;
    }

    private static DbQueries createDbQueryInstance(String url, ConnectionContext connectionContext) {
        Class<? extends StandardQueries> queryImplClaz = StandardQueries.getQueryImplementation(url);
        DbQueries dbQueries = null;
        try {
            Constructor constructor =
                    queryImplClaz.getConstructor(new Class[]{ConnectionContext.class});
            dbQueries = (DbQueries) constructor.newInstance(connectionContext);
        } catch (InstantiationException e) {
            logger.error(e);
            MintLeafException.throwException(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
            MintLeafException.throwException(e);
        } catch (NoSuchMethodException e) {
            logger.error(e);
            MintLeafException.throwException(e);
        } catch (InvocationTargetException e) {
            logger.error(e);
            MintLeafException.throwException(e);
        }

        return dbQueries;
    }

    @Override
    public Connection getConnection() throws MintLeafException {
        if (this.connection == null) {
            try {
                this.connection = driverSource.getConnection();
            } catch (SQLException e) {
                throw new MintLeafException(e);
            }
        }
        return this.connection;
    }

    @Override
    public boolean isCloseable() {
        return this.autoCloseable;
    }

    public void close() throws MintLeafException {
        if (isCloseable() && (this.connection != null)) {
            try {
                commitTransaction();
                this.connection.close();
                this.connection = null;
            } catch (SQLException e) {
                throw new MintLeafException(e);
            }
        }
    }

    @Override
    public ConnectionContext beginTransaction() throws MintLeafException {
        try {
            if (!inTransaction) {
                inTransaction = true;
                getConnection().setAutoCommit(false);
            }
            return this;
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    @Override
    public void commitTransaction() throws MintLeafException {
        if (!inTransaction) {
            return;
        }

        try {
            getConnection().commit();
            inTransaction = false;
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    @Override
    public void rollbackTransaction() throws MintLeafException {
        if (!inTransaction) {
            return;
        }
        try {
            getConnection().rollback();
            inTransaction = false;
        } catch (SQLException e) {
            throw new MintLeafException(e);
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

}
