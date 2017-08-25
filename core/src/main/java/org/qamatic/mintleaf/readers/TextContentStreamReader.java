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
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.configuration.ArgPatternHandler;
import org.qamatic.mintleaf.core.Readerline;

import java.io.InputStream;

public class TextContentStreamReader<T> extends SqlStreamReader {

    private final static MintleafLogger logger = MintleafLogger.getLogger(TextContentStreamReader.class);

    public TextContentStreamReader(String resource) {
        super(resource);
    }

    public TextContentStreamReader(InputStream stream) {
        super(stream);
    }


    @Override
    public T read() throws MintleafException {
        skipLineFeeds = true;
        super.read();

        if (getReadListener() != null && content.length() != 0) {
            getReadListener().eachRow(0, new ChangeSet("0", getDelimiter(), content.toString()));
        }
        return null;
    }

    protected Readerline readLine() {
        return line -> {
            content.append(line);
            return true;
        };
    }

    public String getContent() {
        ArgPatternHandler argPatternHandler = new ArgPatternHandler(content.toString());
        argPatternHandler.withUserProperties(getUserVariableMapping());
        return argPatternHandler.getText();
    }

    @Override
    public String toString() {
        return getContent();
    }

}
