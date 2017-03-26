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

package org.qamatic.mintleaf.builders;

import org.qamatic.mintleaf.Database;
import org.qamatic.mintleaf.DriverSource;
import org.qamatic.mintleaf.core.BasicDatabase;
import org.qamatic.mintleaf.core.JdbcDriverSource;

/**
 * Created by QAmatic Team on 3/25/17.
 */
public class DatabaseBuilder {

    private Class<? extends DriverSource> driverSourceClazz = JdbcDriverSource.class;
    private String url;
    private String username;
    private String password;

    public DatabaseBuilder withDriverSource(Class<? extends DriverSource> driverSourceClazz) {
        this.driverSourceClazz = driverSourceClazz;
        return this;
    }

    public DatabaseBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public DatabaseBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public DatabaseBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public Database build() {
        BasicDatabase databaseContext = new BasicDatabase(this.driverSourceClazz, this.url, this.username, this.password);
        return databaseContext;
    }

}
