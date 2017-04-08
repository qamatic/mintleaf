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

package org.qamatic.mintleaf;

import org.junit.Before;
import org.junit.Test;
import org.qamatic.mintleaf.configuration.ConfigurationRoot;
import org.qamatic.mintleaf.configuration.DbConnectionInfo;
import org.qamatic.mintleaf.configuration.Property;
import org.qamatic.mintleaf.configuration.SchemaInfo;
import org.qamatic.mintleaf.core.ChangeSets;
import org.qamatic.mintleaf.core.JdbcDriverSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MigrateTest   {

    private Database testDb;

    @Before
    public void cleanDb() throws MintLeafException {
        deleteFile("./target/h2testdb.h2.db");
        deleteFile("./target/h2testdb.trace.db");

        testDb = new Mintleaf.DatabaseBuilder().
                withDriverSource(JdbcDriverSource.class).
                withUrl("jdbc:h2:file:./target/h2testdb;mv_store=false;").
                build();
    }

    @Test
    public void testDbById() throws SQLException, IOException, MintLeafException {
        ConfigurationRoot conf = getTestConfig("myh2", "jdbc:h2:file:./target/h2testdb;mv_store=false;", "", "", "clean db, create schema", "");
        assertNull(conf.getDatabase("dfsdf"));
        assertEquals(DbType.H2, conf.getDatabase("myh2").getSupportedDbType());
    }

    private ConfigurationRoot getTestConfig(String id, String url, String userName, String password, String changeSetsToApply, String sqlPaths) {
        ConfigurationRoot dbConfiguration = new ConfigurationRoot();
        DbConnectionInfo dbConnectionSetting = new DbConnectionInfo(id, DbType.getDbType(url),
                url, userName, password);
        dbConfiguration.getDatabases().add(dbConnectionSetting);

        SchemaInfo versionSetting = new SchemaInfo();
        versionSetting.setId("1.0");
        versionSetting.setChangeSets(changeSetsToApply);

        versionSetting.setScriptLocation(sqlPaths);
        dbConfiguration.getSchemaVersions().getVersion().add(versionSetting);
        return dbConfiguration;
    }

    private static void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }


}
