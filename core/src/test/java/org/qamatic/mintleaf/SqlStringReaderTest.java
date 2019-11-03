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

import org.junit.Test;
import org.qamatic.mintleaf.readers.SqlStringReader;

import static org.junit.Assert.assertEquals;

public class SqlStringReaderTest {

    private String actual_emptypackage_block1;
    private String actual_emptypackage_block2;

    private String getSamplePackageData() {
        StringBuilder expected = new StringBuilder();

        expected.append("create or replace package EmptyPackage\n");
        expected.append("as\n");
        expected.append("\n");
        expected.append("end EmptyPackage;\n");
        expected.append("\n");
        expected.append("/\n");
        expected.append("\n");
        expected.append("create or replace\n");
        expected.append("package body EmptyPackage\n");
        expected.append("as\n");
        expected.append("\n");
        expected.append("end EmptyPackage;\n");
        expected.append("\n");
        expected.append("/\n");
        expected.append("\n");
        return expected.toString();
    }


    @Test
    public void testSqlReaderListnerTest2() throws MintleafException {

        SqlStringReader reader = new SqlStringReader(getSamplePackageData());

        reader.getReadListener().add((ReadListener) (rowNum, row) -> {
            if (actual_emptypackage_block1 == null) {
                actual_emptypackage_block1 = ((ChangeSet) row).getChangeSetSource();
            } else if (actual_emptypackage_block2 == null) {
                actual_emptypackage_block2 = ((ChangeSet) row).getChangeSetSource();
            }
        });


        actual_emptypackage_block1 = null;
        actual_emptypackage_block2 = null;

        reader.read();

        StringBuilder expected = new StringBuilder();

        expected.append("create or replace package EmptyPackage\n");
        expected.append("as\n");
        expected.append("\n");
        expected.append("end EmptyPackage;");

        assertEquals(expected.toString(), actual_emptypackage_block1);

        expected.setLength(0);
        expected.append("create or replace\n");
        expected.append("package body EmptyPackage\n");
        expected.append("as\n");
        expected.append("\n");
        expected.append("end EmptyPackage;");

        assertEquals(expected.toString(), actual_emptypackage_block2);

    }


}
