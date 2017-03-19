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
import org.qamatic.mintleaf.dbs.H2Db;
import org.qamatic.mintleaf.dbs.MSSqlDb;
import org.qamatic.mintleaf.dbs.MySqlDb;
import org.qamatic.mintleaf.dbs.OracleDb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by qamatic on 3/6/16.
 */
public class BasicDatabaseContext implements DatabaseContext {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(BasicDatabaseContext.class);

    private Class<? extends DriverSource> driverSourceClazz;
    private String url;
    private String username;
    private String password;
    private DbQueries dbQueries;
    private DriverSource driverSource;

    public BasicDatabaseContext(Class<? extends DriverSource> driverSourceClazz, String url, String username, String password) {
        this.driverSourceClazz = driverSourceClazz;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public BasicDatabaseContext(DriverSource driverSource) {
        this.driverSource = driverSource;
        this.url = driverSource.getUrl();
        this.username = driverSource.getUsername();
        this.password = driverSource.getPassword();
    }

    public DbQueries getDbQueries() {
        if (dbQueries == null) {
            this.dbQueries = createDbQueryInstance(this.url, getDriverSource());
        }
        return dbQueries;
    }

    public DriverSource getDriverSource() {
        if (this.driverSource == null) {
            this.driverSource = buildDriverSource();
        }
        return this.driverSource;
    }

    private DriverSource buildDriverSource() {
        DriverSource driverSource = getDriverSource(this.driverSourceClazz);
        driverSource.setUrl(this.url);
        if (this.username != null) {
            driverSource.setUsername(this.username);
            driverSource.setPassword(this.password);
        }
        return driverSource;
    }

    private static DriverSource getDriverSource(Class<? extends DriverSource> driverSourceClazz) {
        DriverSource driverSource = null;
        try {
            driverSource = driverSourceClazz.newInstance();
        } catch (InstantiationException e) {
            logger.error(e);
            MintLeafException.throwException(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
            MintLeafException.throwException(e);
        }
        return driverSource;
    }

    private static DbQueries createDbQueryInstance(String url, DriverSource driverSource) {
        Class<? extends Database> queryImplClaz = Database.getQueryImplementation(url);
        DbQueries dbQueries = null;
        try {
            Constructor constructor =
                    queryImplClaz.getConstructor(new Class[]{DriverSource.class});
            dbQueries = (DbQueries) constructor.newInstance(driverSource);
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

    static {
        Database.registerQueryImplementation(DbType.H2.getJdbcUrlPrefix(), H2Db.class);
        Database.registerQueryImplementation(DbType.MSSQL.getJdbcUrlPrefix(), MSSqlDb.class);
        Database.registerQueryImplementation(DbType.MYSQL.getJdbcUrlPrefix(), MySqlDb.class);
        Database.registerQueryImplementation(DbType.ORACLE.getJdbcUrlPrefix(), OracleDb.class);
    }

}
