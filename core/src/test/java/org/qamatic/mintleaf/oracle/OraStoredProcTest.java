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

import org.junit.BeforeClass;
import org.junit.Test;
import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.core.ChangeSets;
import org.qamatic.mintleaf.core.StoredProcedure;

import java.sql.Types;

import static org.junit.Assert.assertEquals;

/**
 * Created by qamatic on 3/4/16.
 */

public class OraStoredProcTest extends OracleTestCase {

    private static Database payrollDb = createOracleDbContext("PAYROLL1", "PAYROLL1");

    @BeforeClass
    public static void migrate() throws MintLeafException {
        ChangeSets.migrate(payrollDb.getNewConnection(), "res:/oracle/payroll-changesets/payroll-ddl.sql", "create countries, few countries, create procs");
    }

    @Test
    public void testProcName() throws MintLeafException {
        StoredProcedure proc = new StoredProcedure(null, "add_country(?, ?)", StoredProcedure.CallType.PROC, null);
        assertEquals("{ CALL ADD_COUNTRY(?, ?) }", proc.getSql());
    }

    @Test
    public void testProcNameWithCall() throws MintLeafException {
        StoredProcedure proc = new StoredProcedure(null, "call add_country(?, ?)", StoredProcedure.CallType.PROC, null);
        assertEquals("CALL ADD_COUNTRY(?, ?)", proc.getSql());
    }

    @Test
    public void testProcNameFunction() throws MintLeafException {
        StoredProcedure proc = new StoredProcedure(null, "add_country(?, ?)", StoredProcedure.CallType.FUNCTION, null);
        assertEquals("{ ? = CALL ADD_COUNTRY(?, ?) }", proc.getSql());
    }

    @Test
    public void testProcNameCustom() throws MintLeafException {
        StoredProcedure proc = new StoredProcedure(null, "declare begin end;", StoredProcedure.CallType.CUSTOMCALL, null);
        assertEquals("declare begin end;", proc.getSql());
    }


    @Test
    public void testInsertCountrySP() throws MintLeafException {
        try (ConnectionContext ctx = payrollDb.getNewConnection()) {

            StoredProcedure proc = new StoredProcedure(ctx, "add_country(?, ?)", StoredProcedure.CallType.PROC, new ParameterBinding.Callable() {
                @Override
                public void bindParameters(CallableParameterSets parameterSets) throws MintLeafException {
                    parameterSets.setInt(1, 13);
                    parameterSets.setString(2, "Lost Country");
                }
            });

            proc.execute();
            assertEquals(13, ctx.getDbQueries().getCount("PAYROLL1.COUNTRIES"));
        }
    }

    @Test
    public void testAddNumbersFunction() throws MintLeafException {
        try (ConnectionContext ctx = payrollDb.getNewConnection()) {

            Executable proc = ctx.executeStoredProc("{?= call sum_numbers(?,?)}", StoredProcedure.CallType.CUSTOMCALL,
                    parameterSets -> {
                        parameterSets.registerOutParameter(1, Types.INTEGER);
                        parameterSets.setInt(2, 10);
                        parameterSets.setInt(3, 43);
                    },
                    result -> {
                        assertEquals(53, result.getInt(1));

                    });

            proc.execute();


        }
    }

}
