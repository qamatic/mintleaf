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

package org.qamatic.mintleaf.dbqueries.oracle;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.qamatic.mintleaf.ConnectionContext;
import org.qamatic.mintleaf.Database;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.core.ChangeSets;

import static org.junit.Assert.assertEquals;

/**
 * Created by qamatic on 3/4/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OraTransactionTest extends OracleTestCase {

    private static Database hrdb1 = createOracleDbContext("HRDB1", "HRDB1");

    @BeforeClass
    public static void migrate() throws MintLeafException {
        ChangeSets.migrate(hrdb1.getNewConnection(), "res:/oracle/hrdb-changesets/hrdb-ddl.sql", "create countries");
    }

    @Test
    public void testCountriesCount() throws MintLeafException {
        ChangeSets.migrate(hrdb1.getNewConnection(), "res:/oracle/hrdb-changesets/hrdb-sampledata.sql", "seed data for countries");
        try (ConnectionContext ctx = hrdb1.getNewConnection()) {
            assertEquals(12, ctx.getDbQueries().getCount("HRDB1.COUNTRIES"));
        }

    }


}
