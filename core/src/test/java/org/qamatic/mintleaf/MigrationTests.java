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
import org.qamatic.mintleaf.configuration.DbConnectionInfo;
import org.qamatic.mintleaf.configuration.MintleafConfigurationRoot;
import org.qamatic.mintleaf.configuration.SchemaVersionInfo;
import org.qamatic.mintleaf.core.JdbcDriverSource;
import org.qamatic.mintleaf.cli.FileFinder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MigrationTests {

    private Database testDb;

    @Before
    public void cleanDb() throws MintleafException {
        deleteFile("./target/h2testdb.h2.db");
        deleteFile("./target/h2testdb.trace.db");

        testDb = new Mintleaf.DatabaseBuilder().
                withDriverSource(JdbcDriverSource.class).
                withUrl("jdbc:h2:file:./target/h2testdb;mv_store=false;").
                build();
    }

    @Test
    public void testDbById() throws SQLException, IOException, MintleafException {
        MintleafConfigurationRoot conf = getTestConfig("myh2", "jdbc:h2:file:./target/h2testdb;mv_store=false;", "", "", "clean db, create schema", "");
        assertNull(conf.getDatabase("dfsdf"));
        assertEquals(DbType.H2, conf.getDatabase("myh2").getSupportedDbType());
    }

    private MintleafConfigurationRoot getTestConfig(String id, String url, String userName, String password, String changeSetsToApply, String sqlPaths) {
        MintleafConfigurationRoot dbConfiguration = new MintleafConfigurationRoot();
        DbConnectionInfo dbConnectionSetting = new DbConnectionInfo(id, DbType.getDbType(url),
                url, userName, password);
        dbConfiguration.getDatabases().add(dbConnectionSetting);

        SchemaVersionInfo versionSetting = new SchemaVersionInfo();
        versionSetting.setId("1.0");
        versionSetting.setChangeSets(changeSetsToApply);

        versionSetting.setScriptLocation(sqlPaths);
        dbConfiguration.getSchemaVersions().getVersion().add(versionSetting);
        return dbConfiguration;
    }

    @Test
    public void fileScanTest() throws MintleafException {

        FileFinder fileFinder = new FileFinder("./target/test-classes/filefinder/*.sql");

        List<String> list = fileFinder.list();
        for(String file : list){
            System.out.println(file);
        }
        assertEquals(3, list.size());

    }

    @Test
    public void fileScanTestWithRegex() throws MintleafException {

        FileFinder fileFinder = new FileFinder("./target/regex:^.*\\b(filefinder)\\b.*$");

        List<String> list = fileFinder.list();
        for(String file : list){
            System.out.println(file);
        }
        assertEquals(3, list.size());

    }


    @Test
    public void fileScanTestWithExactFile() throws MintleafException {

        FileFinder fileFinder = new FileFinder("./target/test-classes/filefinder/f1/file11.sql");

        List<String> list = fileFinder.list();
        for(String file : list){
            System.out.println(file);
        }
        assertEquals(1, list.size());
        assertTrue(list.get(0).contains("target/test-classes/filefinder/f1/file11.sql"));
    }

    private static void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }


}
