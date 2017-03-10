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

package org.qamatic.mintleaf.dbs.oracle;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.qamatic.mintleaf.DbMetaDataCollection;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.core.ChangeSets;

import java.io.IOException;
import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by qamatic on 3/4/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OraUtilityTest extends OracleTestCase {


    @BeforeClass
    public static void migrate() throws IOException, SQLException {
        //clean db, create hrdb-schema
        ChangeSets.migrate(oracleSysDbaCtx.getDriverSource(), "res:/oracle/hrdb-changesets/hrdb-schema-setup.sql", "create schema");
        //do some DDL
        ChangeSets.migrate(oracleHrDbCtx.getDriverSource(), "res:/oracle/hrdb-changesets/hrdb-ddl.sql", "create countries");
    }

    @Test
    public void testCountriesCount() throws SQLException, IOException, MintLeafException {
        ChangeSets.migrate(oracleHrDbCtx.getDriverSource(), "res:/oracle/hrdb-changesets/hrdb-sampledata.sql", "seed data for countries");
        assertEquals(12, oracleHrDbCtx.getDbQueries().getCount("HRDB.COUNTRIES"));

    }

    @Test
    public void testPackageExists() throws SQLException, IOException, MintLeafException {
        ChangeSets.migrate(oracleHrDbCtx.getDriverSource(), "res:/oracle/hrdb-changesets/hrdb-proc-packages.sql", "create some test packages");
        assertTrue("Package by name SOMEPACKAGE does not exists", oracleHrDbCtx.getDbQueries().isSqlObjectExists("HRDB.SOMEPACKAGE", "PACKAGE", false));
        assertTrue("Package by name SOMEPACKAGE body does not exists", oracleHrDbCtx.getDbQueries().isSqlObjectExists("HRDB.SOMEPACKAGE", "PACKAGE BODY", false));
    }

    @Test
    public void testCountryMetaData() throws SQLException, IOException, MintLeafException {
        DbMetaDataCollection metaData = oracleHrDbCtx.getDbQueries().getMetaData("HRDB.COUNTRIES");
        assertEquals(3, metaData.size());
    }

    @Test
    public void testIsColumnExists() throws SQLException, IOException, MintLeafException {
        Assert.assertTrue(oracleHrDbCtx.getDbQueries().isColumnExists("HRDB.COUNTRIES", "COUNTRY_NAME"));
    }

    @Test
    public void testgetSqlObjectsFound() throws SQLException, IOException, MintLeafException {
        Assert.assertTrue(oracleHrDbCtx.getDbQueries().getSqlObjects("TABLE").contains("COUNTRIES"));
    }

    @Test(expected = MintLeafException.class)
    public void testInvalidObjectName() throws SQLException, IOException, MintLeafException {
        oracleHrDbCtx.getDbQueries().getObjectNames("employee_typ");
    }

    @Test
    public void testEmployeeObjectMetaData() throws SQLException, IOException, MintLeafException {
        if (!oracleHrDbCtx.getDbQueries().isSqlObjectExists("HRDB.employee_typ", "TYPE", true)) {
            ChangeSets.migrate(oracleHrDbCtx.getDriverSource(), "res:/oracle/hrdb-changesets/hrdb-ddl-typeobjects.sql", "create employee object type");
        }
        DbMetaDataCollection metaData = oracleHrDbCtx.getDbQueries().getMetaData("HRDB.employee_typ");
        assertEquals(12, metaData.size());
    }

}
