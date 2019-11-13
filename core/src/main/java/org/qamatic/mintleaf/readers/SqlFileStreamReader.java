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

import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.core.ArgPatternHandler;
import org.qamatic.mintleaf.core.BaseSqlReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SqlFileStreamReader<T> extends BaseSqlReader<T> {

    private final static MintleafLogger logger = MintleafLogger.getLogger(SqlFileStreamReader.class);
    protected final StringBuilder content = new StringBuilder();
    protected InputStream inputStream;
    protected String resource;
    protected boolean skipLineFeeds;
    private int rowCount;

    public SqlFileStreamReader(InputStream stream) {
        this.inputStream = stream;

    }

    public SqlFileStreamReader(String resource) {
        this.resource = resource;

    }


    protected InputStream getInputStream() throws IOException {
        if (this.inputStream == null) {
            //logger.info(String.format("reading resource : %s", this.resource));
            this.inputStream = BaseSqlReader.getInputStreamFromFile(this.resource);
        }
        return this.inputStream;
    }

    protected Row createRow(Object rowData) {
        return new ChangeSet(rowCount + "", getDelimiter(), (String) rowData);
    }

    protected int onReadData(Object data) throws MintleafException {
        String line = (String) data;
        if (line.startsWith("show err") || line.startsWith("--") && !line.contains("--@")) {
            return MintleafReader.READ_SKIP;
        }

        if (isSqlDelimiter(line)) {

            String[] splits = line.split(getDelimiter());
            if (splits.length >= 1) {
                content.append(splits[0]);
            }

            String sql = new ArgPatternHandler(content.toString().trim()).
                    withUserProperties(this.getUserVariableMapping()).
                    getText();

            readRow(rowCount++, new ChangeSet(rowCount + "", getDelimiter(), sql));

            content.setLength(0);

        } else {
            content.append(line);
            content.append("\n");
        }
        return MintleafReader.READ_PROCEED;
    }

    @Override
    public void read() throws MintleafException {
        this.content.setLength(0);
        BufferedReader input = null;

        try {
            try {
                input = new BufferedReader(new InputStreamReader(getInputStream(), "UTF-8"));
                String line = null;
                while ((line = input.readLine()) != null) {
                    if (!skipLineFeeds) {
                        line = line.trim();
                        if (line.length() == 0) {
                            continue;
                        }
                    }
                    int readState = onReadData(line);
                    if (readState == MintleafReader.READ_SKIP) {
                        continue;
                    } else if (readState == MintleafReader.READ_STOP) {
                        break;
                    }

                    //Row newRow = createRow()


                }
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        } catch (IOException e) {
            logger.error(e);
            throw new MintleafException(e);
        }
    }


}
