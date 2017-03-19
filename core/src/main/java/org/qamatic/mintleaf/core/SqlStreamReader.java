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

import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.MintLeafLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SqlStreamReader extends BaseSqlReader {

    private final static MintLeafLogger logger = MintLeafLogger.getLogger(SqlStreamReader.class);
    protected InputStream mvstream;

    public SqlStreamReader(InputStream stream) {
        this.mvstream = stream;

    }

    protected boolean isDelimiterFound(String line) {
        return (getDelimiter().equals("/") && line.equals("/") || getDelimiter().equals(";") && line.endsWith(";"));
    }

    @Override
    public void read() throws MintLeafException {

        StringBuilder childContents = new StringBuilder();
        StringBuilder contents = new StringBuilder();
        BufferedReader input = null;
        try {
            try {
                input = new BufferedReader(new InputStreamReader(mvstream, "UTF-8"));

                String line = null; // not declared within while loop
                while ((line = input.readLine()) != null) {

                    line = line.trim();

                    if (line.startsWith("show err") || line.startsWith("--") && !line.contains("--@")) {
                        continue;
                    }

                    contents.append(line);
                    contents.append("\n");

                    if (isDelimiterFound(line)) {

                        String[] splits = line.split(getDelimiter());
                        if (splits.length >= 1) {
                            childContents.append(splits[0]);
                        }
                        String sql = childContents.toString().trim();
                        if (readerListener != null && sql.length() != 0) {
                            readerListener.onReadChild(new StringBuilder(sql), null);
                        }
                        childContents.setLength(0);

                    } else {
                        childContents.append(line);
                        childContents.append("\n");
                    }
                }
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        } catch (IOException e) {
            logger.error(e);
            throw new MintLeafException(e);
        }
        //return contents.toString();
    }

}
