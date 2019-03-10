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
import org.qamatic.mintleaf.core.ChangeSets;
import org.qamatic.mintleaf.tools.DbImporter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by qamatic on 3/6/16.
 */
public class ImportExportTest extends H2TestCase {


    @Before
    public void applyChangeSet() throws IOException, SQLException, MintleafException {
        ChangeSets.migrate(testDb.getNewConnection(), "res:/example-changesets.sql", "create schema,load seed data");
    }


    @Test
    public void writeCSVTest() throws SQLException, IOException, MintleafException {
        File f = new File("target/users.csv");
        if (f.exists())
            f.delete();

        assertFalse(new File("target/users.csv").exists());

        Executable dataAction = new Mintleaf.DbToCsvDataTransferBuilder().
                withSourceDb(testDb).
                withSourceSql("select * from HRDB.USERS").
                withTargetCsvFile("target/users.csv").
                build();


        dataAction.execute();
        assertTrue(new File("target/users.csv").exists());
    }


    @Test
    public void importCSVTest() throws SQLException, IOException, MintleafException {
        writeCSVTest();//dependent..

        Executable action = new Mintleaf.CsvToDbDataTransferBuilder().
                withSourceCsvFile("target/users.csv").
                withTargetDb(testDb).
                withTargetSqlTemplate("UPDATE HRDB.USERS SET USERNAME = '$USERNAME$-changed' WHERE USERID=$USERID$").
                build();

        action.execute();
        testDbQueries.query("SELECT USERNAME FROM HRDB.USERS", (row, resultSet) -> {
            assertTrue(resultSet.asString("USERNAME").contains("-changed"));

        });

    }

    @Test
    public void testInlineParamRegEx() {
        Pattern pattern = Pattern.compile("\\$(\\w+)\\$", Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("UPDATE HRDB.USERS SET USERID= $USERID$, USERNAME = '$USERNAME$'");
        matcher.find();
        assertEquals("USERID", matcher.group(1));
    }


    @Test
    public void DbToDbImport() throws SQLException, IOException, MintleafException {
        ChangeSets.migrate(testDb.getNewConnection(), "res:/example-changesets.sql", "DROP_CREATE_USERS_IMPORT_TABLE");

        Executable action = new Mintleaf.DbToDbDataTransferBuilder().
                withSourceDb(testDb).
                withSourceSql("SELECT * FROM HRDB.USERS").
                withTargetDb(testDb).
                withTargetSqlTemplate("INSERT INTO HRDB.USERS_IMPORT_TABLE (USERID, USERNAME) VALUES ($USERID$, '$USERNAME$')").
                build();

        action.execute();
        assertEquals(7, testDbQueries.getCount("HRDB.USERS_IMPORT_TABLE"));

    }


    @Test
    public void DbToDbImportNullIssue() throws SQLException, IOException, MintleafException {
        ChangeSets.migrate(testDb.getNewConnection(), "res:/example-changesets.sql", "DROP_CREATE_USERS_IMPORT_TABLE");

        Executable action = new DbImporter(testDb.getNewConnection(),
                "SELECT * FROM HRDB.USERS",
                testDb.getNewConnection(),
                "INSERT INTO HRDB.USERS_IMPORT_TABLE (USERID, USERNAME, RATE) VALUES ($USERID$, '$USERNAME$', $RATE$)"
        );
        action.execute();

        assertEquals(7, testDbQueries.getCount("HRDB.USERS_IMPORT_TABLE"));


    }

}
