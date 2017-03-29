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

package org.qamatic.mintleaf;

import org.junit.Test;
import org.qamatic.mintleaf.configuration.ConfigurationRoot;
import org.qamatic.mintleaf.configuration.Property;
import org.qamatic.mintleaf.configuration.DbConnectionInfo;
import org.qamatic.mintleaf.configuration.SchemaInfo;
import org.qamatic.mintleaf.core.BaseSqlReader;

import java.io.IOException;
import java.io.InputStream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by qamatic on 5/6/16.
 */
public class MigrationConfigTest {

    @Test
    public void testConfigurationDump() throws IOException {


        ConfigurationRoot dbConfiguration = new ConfigurationRoot();
        DbConnectionInfo dbConnectionSetting = new DbConnectionInfo("abcdb", DbType.ORACLE,
                    "jdbc:oracle:thin:your-db-connection-url-here", "your-user-name", "your-password");
        dbConfiguration.getDatabases().add(dbConnectionSetting);
        dbConnectionSetting.getConnectionProperties().getItems().add(new Property("poolsize","100"));
        dbConnectionSetting.getConnectionProperties().getItems().add(new Property("internal_logon","sysdba"));


        SchemaInfo versionSetting = new SchemaInfo();
        versionSetting.setId("1.0");
        versionSetting.setChangeSets("create schema, load seed data");

        versionSetting.setScriptLocation("./path/*.sql");
        dbConfiguration.getSchemaVersions().getVersion().add(versionSetting);

        //a sanity check whether dump works
        assertTrue(dbConfiguration.toString().contains("databases"));
    }

    @Test
    public void testConfigurationLoad() throws IOException {
        InputStream inputStream = BaseSqlReader.getInputStreamFromFile("res:/test-config.yml");
    }
}
