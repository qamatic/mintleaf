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

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.qamatic.mintleaf.core.SqlScriptFile;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by qamatic on 3/6/16.
 */
public class SingleLoadScriptTests extends H2TestCase {


    @BeforeClass
    public static void setup() throws MintLeafException {
        SqlScript script = new SqlScriptFile(testDb.getNewConnection(), "res:/h2singlescript.sql", ";");
        script.apply();
    }

    @Test
    public void simpleScriptLoad() throws SQLException, IOException, MintLeafException {

        Assert.assertTrue(testDbQueries.isTableExists("HRDB.USERS"));
    }

    @Test
    public void simpleScriptLoadWithVar() throws  MintLeafException {

        SqlScript script = new SqlScriptFile(testDb.getNewConnection(), "res:/variable-changesets.sql", ";");
        script.getReader().getUserVariableMapping().put("user_id", "1");
        script.getReader().getUserVariableMapping().put("user_name", "TINTIN");
        script.apply();
        Assert.assertTrue(testDbQueries.isTableExists("HRDB.USERS"));
    }


}
