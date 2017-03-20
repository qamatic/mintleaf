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

package org.qamatic.mintleaf.dbs.oracle;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.core.ChangeSets;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by qamatic on 3/4/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OraTransactionTest extends OracleTestCase {


    @BeforeClass
    public static void migrate() throws MintLeafException {
        //clean db, create hrdb-schema
        ChangeSets.migrate(oracleSysDbaCtx.getCloseableConnection(), "res:/oracle/hrdb-changesets/hrdb-schema-setup.sql", "create schema");
        //do some DDL
        ChangeSets.migrate(oracleHrDbCtx.getCloseableConnection(), "res:/oracle/hrdb-changesets/hrdb-ddl.sql", "create countries");
    }

    @Test
    public void testCountriesCount() throws MintLeafException {
        ChangeSets.migrate(oracleHrDbCtx.getCloseableConnection(), "res:/oracle/hrdb-changesets/hrdb-sampledata.sql", "seed data for countries");
        assertEquals(12, oracleHrDbCtx.getDbQueries().getCount("HRDB.COUNTRIES"));

    }


}
