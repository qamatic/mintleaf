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

package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.configuration.ArgPatternHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SqlStreamReader extends BaseSqlReader {

    private final static MintleafLogger logger = MintleafLogger.getLogger(SqlStreamReader.class);
    protected final StringBuilder content = new StringBuilder();
    protected InputStream inputStream;
    protected String resource;
    protected boolean skipLineFeeds;

    public SqlStreamReader(InputStream stream) {
        this.inputStream = stream;

    }

    public SqlStreamReader(String resource) {
        this.resource = resource;

    }


    protected InputStream getInputStream() throws IOException {
        if (this.inputStream == null) {
            //logger.info(String.format("reading resource : %s", this.resource));
            this.inputStream = BaseSqlReader.getInputStreamFromFile(this.resource);
        }
        return this.inputStream;
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

                    if (!readLine().processInternal(line)) {
                        continue;
                    }

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

    protected Readerline readLine() {
        return line -> {
            if (line.startsWith("show err") || line.startsWith("--") && !line.contains("--@")) {
                return false;
            }

            if (isDelimiter(line)) {

                String[] splits = line.split(getDelimiter());
                if (splits.length >= 1) {
                    content.append(splits[0]);
                }

                String sql = new ArgPatternHandler(content.toString().trim()).
                        withUserProperties(this.getUserVariableMapping()).
                        getText();

                if (changeSetListener != null && sql.length() != 0) {
                    changeSetListener.onChangeSetRead(new StringBuilder(sql), null);
                }
                content.setLength(0);

            } else {
                content.append(line);
                content.append("\n");
            }
            return true;
        };
    }

}
