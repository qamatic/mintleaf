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

import org.qamatic.mintleaf.ApacheBasicDataSource;
import org.qamatic.mintleaf.Database;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.Mintleaf;
import org.qamatic.mintleaf.core.ChangeSets;

/**
 * Created by qamatic on 3/4/16.
 */
public class OracleTestCase {


    static {
        Database oraSysDb = createOracleDbContext(System.getenv("TEST_DB_MASTER_USERNAME"),
                System.getenv("TEST_DB_MASTER_PASSWORD"));
        try {
            ChangeSets.migrate(oraSysDb.getNewConnection(), "res:/oracle/hrdb-changesets/hrdb-schema-setup.sql", "create schema");
        } catch (MintLeafException e) {
            MintLeafException.throwException(e.getMessage());
        }
    }

    public static Database createOracleDbContext(String userName, String password) {
        Database db = new Mintleaf.DatabaseBuilder().
                withDriverSource(ApacheBasicDataSource.class).
                withUrl(System.getenv("TEST_DB_URL")).
                withUsername(userName).
                withPassword(password).
                build();
        return db;
    }
}
