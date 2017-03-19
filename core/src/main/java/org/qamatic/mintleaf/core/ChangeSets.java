/*
 *
 *  *
 *  *  *
 *  *  *  * <!--
 *  *  *  *   ~
 *  *  *  *   ~ The MIT License (MIT)
 *  *  *  *   ~
 *  *  *  *   ~ Copyright (c) 2010-2017 QAMatic Team
 *  *  *  *   ~
 *  *  *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *  *  *  *   ~ in the Software without restriction, including without limitation the rights
 *  *  *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *  *  *  *   ~ furnished to do so, subject to the following conditions:
 *  *  *  *   ~
 *  *  *  *   ~ The above copyright notice and this permission notice shall be included in all
 *  *  *  *   ~ copies or substantial portions of the Software.
 *  *  *  *   ~
 *  *  *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  *  *   ~ SOFTWARE.
 *  *  *  *   ~
 *  *  *  *   ~
 *  *  *  *   -->
 *  *  *
 *  *  *
 *  *
 *
 */

package org.qamatic.mintleaf.core;


import org.qamatic.mintleaf.DriverSource;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.SqlReader;
import org.qamatic.mintleaf.SqlScript;

public final class ChangeSets {


    public static void applySource(final DriverSource driverSource, final String script, final String delimiter) throws MintLeafException {
        SqlScript sqlScript = new BaseSqlScript(driverSource) {
            @Override
            protected SqlReader getSourceReader() {
                SqlReader reader = new SqlStringReader(script);
                reader.setDelimiter(delimiter);
                return reader;
            }
        };
        sqlScript.apply();
    }


    public static void migrate(final DriverSource driverSource, String fileName, String changeSetsToLoadSeparatedByComma) throws MintLeafException {
        migrate(driverSource, fileName, changeSetsToLoadSeparatedByComma.split(","));
    }


    public static void migrate(final DriverSource driverSource, String fileName, String[] changeSetsToLoad) throws MintLeafException {
        SqlChangeSets changeSets = new SqlChangeSets(driverSource, new SqlChangeSetFileReader(fileName), changeSetsToLoad);
        changeSets.apply();
    }


}
