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

package org.qamatic.mintleaf.builders;

import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.core.JdbcDriverSource;

/**
 * Created by qamatic on 3/6/16.
 */
public abstract class DriverSourceBuilder {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(DriverSourceBuilder.class);

    protected Class<? extends DriverSource> driverSourceClazz = JdbcDriverSource.class;
    protected String url;
    protected String username;
    protected String password;
    protected String driverClassName;

    protected DbQueries dbQueries;
    protected DriverSource driverSource;

    public DriverSourceBuilder() {
        this.driverClassName = "org.h2.Driver";
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

    public DriverSourceBuilder withDriverSource(Class<? extends DriverSource> driverSourceClazz) {
        this.driverSourceClazz = driverSourceClazz;
        return this;
    }

    public DriverSourceBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public DriverSourceBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public DriverSourceBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public DriverSourceBuilder withDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    public DriverSource buildDriverSource() {
        DriverSource driverSource = getDriverSource(this.driverSourceClazz);
        driverSource.setUrl(this.url);
        if (this.username != null) {
            driverSource.setUsername(this.username);
            driverSource.setPassword(this.password);
        }
        driverSource.setDriverClassName(this.driverClassName);
        return driverSource;
    }

    public abstract DatabaseContext build();

}
