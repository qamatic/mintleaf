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

import org.qamatic.mintleaf.ConnectionContext;
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.MintleafReader;
import org.qamatic.mintleaf.readers.SqlFileStreamReader;

import java.io.InputStream;

/**
 * Created by qamatic on 7/18/16.
 */
public class SqlScriptFile extends BaseSqlScript {

    private final static MintleafLogger logger = MintleafLogger.getLogger(SqlScriptFile.class);
    private final String filename;
    private final String delimiter;
    private MintleafReader reader;

    public SqlScriptFile(ConnectionContext connectionContext, String filename, String delimiter) {
        super(connectionContext);
        this.filename = filename;
        this.delimiter = delimiter;
    }

    @Override
    public MintleafReader getReader() {
        if (this.reader == null) {
            InputStream stream = BaseReader.getInputStreamFromFile(this.filename);
            this.reader = new SqlFileStreamReader(stream);
            this.reader.setDelimiter(this.delimiter);
        }
        return this.reader;
    }


}
