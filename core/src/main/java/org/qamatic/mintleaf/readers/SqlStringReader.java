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

package org.qamatic.mintleaf.readers;

import org.qamatic.mintleaf.ChangeSet;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.configuration.ArgPatternHandler;
import org.qamatic.mintleaf.core.BaseSqlReader;

public class SqlStringReader<T> extends BaseSqlReader<T> {

    private final String mvSqlSource;
    private int readCount;

    public SqlStringReader(String sqlSource) {
        mvSqlSource = sqlSource;
    }

    @Override
    public void read() throws MintleafException {

        StringBuilder childContents = new StringBuilder();

        String[] sqlLines = mvSqlSource.split("\n");
        try {

            for (String line : sqlLines) {

                line = line.trim();

                if (line.startsWith("show err") || line.startsWith("--")) {
                    continue;
                }

                if (isSqlDelimiter(line)) {

                    String[] splits = line.split(getDelimiter());
                    if (splits.length >= 1) {
                        childContents.append(splits[0]);
                    }

                    String sql = new ArgPatternHandler(childContents.toString().trim()).
                            withUserProperties(this.getUserVariableMapping()).
                            getText();

                    if (getReadListener() != null && sql.length() != 0) {
                        getReadListener().eachRow(readCount++, new ChangeSet(readCount + "", getDelimiter(), sql));
                    }

                    childContents.setLength(0);

                } else {
                    childContents.append(line);
                    childContents.append("\n");
                }

            }
        } finally {

        }

    }

}
