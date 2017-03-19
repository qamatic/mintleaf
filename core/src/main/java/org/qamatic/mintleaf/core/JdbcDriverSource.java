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

import org.qamatic.mintleaf.DriverSource;
import org.qamatic.mintleaf.MintLeafLogger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by qamatic on 3/3/16.
 */
public class JdbcDriverSource implements DriverSource {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(JdbcDriverSource.class);
    protected Properties mvProperties;
    private ClassLoader driverClassLoader;
    private Driver driver;

    static {
        DriverManager.getDrivers();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(getUsername(), getPassword());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getDriver().connect(getUrl(), getProperties());
    }

    public synchronized ClassLoader getDriverClassLoader() {
        return this.driverClassLoader;
    }

    public synchronized void setDriverClassLoader(final ClassLoader driverClassLoader) {
        this.driverClassLoader = driverClassLoader;
    }

    protected Properties getProperties() {
        if (mvProperties == null) {
            mvProperties = new Properties();
        }
        return mvProperties;
    }

    private synchronized Driver getDriver() throws SQLException {
        if (driver == null) {
           this.driver = createDriverInstance(getDriverClassLoader(), getDriverClassName(), getUrl());
        }
        return driver;
    }

    private static Driver createDriverInstance(ClassLoader driverClassLoader, String driverClassName, String url) throws SQLException {
        Driver driver = null;
        final String message = "Unable to create JDBC driver '" +
                (driverClassName != null ? driverClassName : "auto-find") +
                "' for connect URL '" + url + "'";
        try {
            if (driverClassName == null) {
                driver = DriverManager.getDriver(url);
            } else {
                Class<?> driverClaz  = findDriverClass(driverClassLoader, driverClassName);
                driver = (Driver) driverClaz.newInstance();
                if (!driver.acceptsURL(url)) {
                    throw new SQLException("No driver is found!");
                }
            }
        } catch (final Exception t) {
            logger.error(message);
            throw new SQLException(message, t);
        }

        if (driver == null){
            logger.error(message);
            throw new SQLException(message);
        }
        return driver;
    }

    private static Class<?> findDriverClass(ClassLoader driverClassLoader, String driverClassName) throws SQLException {
        Class<?> driverClaz;
        try {
            try {
                if (driverClassLoader == null) {
                    driverClaz = Class.forName(driverClassName);
                } else {
                    driverClaz = Class.forName(
                            driverClassName, true, driverClassLoader);
                }
            } catch (final ClassNotFoundException cnfe) {
                driverClaz = Thread.currentThread(
                ).getContextClassLoader().loadClass(
                        driverClassName);
            }
        } catch (final Exception t) {
            final String message = "Unable to load JDBC driver:" +
                    driverClassName;
            logger.error(message);

            throw new SQLException(message, t);
        }
        return driverClaz;
    }


    public String getProperty(String propName) {
        return getProperties().getProperty(propName);
    }

    public void setProperty(String propName, String value) {
        getProperties().setProperty(propName, value);
    }

    @Override
    public String getUrl() {
        return getProperty(PROP_URL);
    }

    @Override
    public void setUrl(String jdbcUrl) {
        setProperty(PROP_URL, jdbcUrl);
    }

    @Override
    public String getUsername() {
        return getProperty(PROP_USERNAME);
    }

    @Override
    public void setUsername(String userName) {
        setProperty(PROP_USERNAME, userName);
    }

    @Override
    public String getPassword() {
        return getProperty(PROP_PASSWORD);
    }

    @Override
    public void setPassword(String password) {
        setProperty(PROP_PASSWORD, password);
    }

    @Override
    public boolean isDebugEnabled() {
        return Boolean.parseBoolean(getProperty(PROP_ENABLE_DEBUG));
    }

    @Override
    public void setDebugEnabled(boolean devMode) {
        setProperty(PROP_ENABLE_DEBUG, devMode + "");
    }

    @Override
    public String getDriverClassName() {
        return getProperty(PROP_DRIVER_CLASSNAME);
    }

    @Override
    public void setDriverClassName(String driverClassName) {
        setProperty(PROP_DRIVER_CLASSNAME, driverClassName);
    }


}
