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

package org.qamatic.mintleaf.oracle;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.qamatic.mintleaf.ColumnMetaDataCollection;
import org.qamatic.mintleaf.Database;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.core.ChangeSets;
import org.qamatic.mintleaf.core.stdqueries.StandardQueries;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by qamatic on 3/4/16.
 */

public class OraUtilityTest extends OracleTestCase {

    private static Database hrdb2 = createOracleDbContext("HRDB2", "HRDB2");

    @BeforeClass
    public static void migrate() throws MintLeafException {

        //do some DDL
        ChangeSets.migrate(hrdb2.getNewConnection(), "res:/oracle/hrdb-changesets/hrdb-ddl.sql", "create countries");
    }

    @Test
    public void testCountriesCount() throws MintLeafException {
        ChangeSets.migrate(hrdb2.getNewConnection(), "res:/oracle/hrdb-changesets/hrdb-sampledata.sql", "seed data for countries");
        assertEquals(12, hrdb2.getNewConnection().getDbQueries().getCount("HRDB2.COUNTRIES"));

    }

    @Test
    public void testPackageExists() throws MintLeafException {
        ChangeSets.migrate(hrdb2.getNewConnection(), "res:/oracle/hrdb-changesets/hrdb-proc-packages.sql", "create some test packages");
        assertTrue("Package by name SOMEPACKAGE does not exists", hrdb2.getNewConnection().getDbQueries().isSqlObjectExists("HRDB2.SOMEPACKAGE", "PACKAGE", false));
        assertTrue("Package by name SOMEPACKAGE body does not exists", hrdb2.getNewConnection().getDbQueries().isSqlObjectExists("HRDB2.SOMEPACKAGE", "PACKAGE BODY", false));
    }

    @Test
    public void testCountryMetaData() throws MintLeafException {
        ColumnMetaDataCollection metaData = hrdb2.getNewConnection().getDbQueries().getMetaData("HRDB2.COUNTRIES");
        assertEquals(3, metaData.size());
    }

    @Test
    public void testIsColumnExists() throws MintLeafException {
        Assert.assertTrue(hrdb2.getNewConnection().getDbQueries().isColumnExists("HRDB2.COUNTRIES", "COUNTRY_NAME"));
    }

    @Test
    public void testgetSqlObjectsFound() throws MintLeafException {
        Assert.assertTrue(hrdb2.getNewConnection().getDbQueries().getSqlObjects("TABLE").contains("COUNTRIES"));
    }

    @Test(expected = MintLeafException.class)
    public void testInvalidObjectName() throws MintLeafException {
        StandardQueries.utilsSplitDottedObjectNames("employee_typ");
    }

    @Test
    public void testEmployeeObjectMetaData() throws MintLeafException {
        if (!hrdb2.getNewConnection().getDbQueries().isSqlObjectExists("HRDB2.employee_typ", "TYPE", true)) {
            ChangeSets.migrate(hrdb2.getNewConnection(), "res:/oracle/hrdb-changesets/hrdb-ddl-typeobjects.sql", "create employee object type");
        }
        ColumnMetaDataCollection metaData = hrdb2.getNewConnection().getDbQueries().getMetaData("HRDB2.employee_typ");
        assertEquals(12, metaData.size());
    }

}
