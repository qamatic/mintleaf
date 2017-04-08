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

package org.qamatic.mintleaf.configuration;

import org.qamatic.mintleaf.Database;
import org.qamatic.mintleaf.DbType;
import org.qamatic.mintleaf.Mintleaf;
import org.qamatic.mintleaf.core.JdbcDriverSource;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by qamatic on 3/6/16.
 */
@XmlType(propOrder = {"id", "type", "url", "username", "password", "connectionProperties"})
public class DbConnectionInfo {
    private String id;
    private DbType type = DbType.H2;
    private String url;
    private String username;
    private String password;
    private ConnectionProperties connectionProperties = new ConnectionProperties();


    public DbConnectionInfo() {

    }

    public DbConnectionInfo(String id, DbType type, String url) {
        this.id = id;
        this.type = type;
        this.url = url;
    }

    public DbConnectionInfo(String id, DbType type, String url, String username, String password) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.username = username;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DbType getType() {
        return type;
    }

    public void setType(DbType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public ConnectionProperties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(ConnectionProperties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public Database getNewDatabaseInstance() {
        return new Mintleaf.DatabaseBuilder().
                withDriverSource(JdbcDriverSource.class).
                withUrl(this.url).
                withUsername(this.username).
                withPassword(this.password).
                build();
    }
}
