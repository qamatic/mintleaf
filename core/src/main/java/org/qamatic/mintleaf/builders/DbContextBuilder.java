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

import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.dbs.H2Db;
import org.qamatic.mintleaf.dbs.MSSqlDb;
import org.qamatic.mintleaf.dbs.MySqlDb;
import org.qamatic.mintleaf.dbs.OracleDb;

/**
 * Created by qamatic on 3/6/16.
 */
public abstract class DbContextBuilder extends DriverSourceBuilder {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(DbContextBuilder.class);

    protected DbType dbType;
    protected DbQueries dbQueries;
    protected DriverSource driverSource;

    public DbContextBuilder() {
        this.dbType = DbType.H2;
        this.driverClassName = "org.h2.Driver";
    }


    public DbContextBuilder(DbType dbType) {
        this.dbType = dbType;
    }

    public DbContextBuilder(DbType dbType, DriverSource driverSource) {
        this.driverSource = driverSource;
        dbQueries = createNewInstance(dbType, driverSource);
    }

    protected static DbQueries createNewInstance(DbType dbType, DriverSource driverSource) {
        switch (dbType) {
            case H2:
                return new H2Db(driverSource);
            case MYSQL:
                return new MySqlDb(driverSource);
            case ORACLE:
                return new OracleDb(driverSource);
            case MSSQL:
                return new MSSqlDb(driverSource);
            default:
                MintLeafException.throwException("Unable to proceed without setting right database type or unsupported database with MintLeaf");
        }
        return null;
    }

    public DbQueries getDbQueries() {
        return dbQueries;
    }

    public DriverSource getDriverSource() {
        return this.driverSource;
    }

    public abstract DatabaseContext build();


}
