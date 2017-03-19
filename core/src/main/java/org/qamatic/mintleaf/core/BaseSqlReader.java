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
import org.qamatic.mintleaf.SqlReader;
import org.qamatic.mintleaf.SqlReaderListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public abstract class BaseSqlReader implements SqlReader {

    private final static MintLeafLogger logger = MintLeafLogger.getLogger(BaseSqlReader.class);
    protected SqlReaderListener readerListener;
    private String delimiter = "/";

    public static InputStream getInputStreamFromFile(String resourceOrFileName) {
        InputStream stream = null;
        logger.info("reading file: " + resourceOrFileName);
        if (resourceOrFileName.startsWith("res:")) {
            String resFile = resourceOrFileName.substring(4);

            stream = Thread.currentThread().getContextClassLoader().getClass().getResourceAsStream(resFile);
            if (stream == null) {
                logger.error("file not found " + resourceOrFileName);
            }
        } else {
            try {
                stream = new FileInputStream(resourceOrFileName);
            } catch (FileNotFoundException e) {
                logger.error("file not found " + resourceOrFileName, e);
            }
        }
        return stream;
    }

    @Override
    public String getDelimiter() {
        return delimiter;
    }

    @Override
    public void setDelimiter(String delimStr) {
        this.delimiter = delimStr;

    }

    @Override
    public abstract void read() throws MintLeafException;

    @Override
    public SqlReaderListener getReaderListener() {
        return readerListener;
    }

    @Override
    public void setReaderListener(SqlReaderListener readerListener) {
        this.readerListener = readerListener;
    }
}
