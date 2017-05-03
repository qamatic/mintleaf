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
import org.qamatic.mintleaf.MintleafReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseSqlReader extends BaseFileReader implements MintleafReader {

    private final static MintleafLogger logger = MintleafLogger.getLogger(BaseSqlReader.class);

    private String delimiter = "/";
    private Map<String, String> userVariableMapping;

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

    protected boolean isDelimiter(String line) {
        //external config needed.
        return ((getDelimiter().equals("/") && line.equals("/")) ||
                (getDelimiter().equals(";") && line.endsWith(";")) ||
                (getDelimiter().equalsIgnoreCase("GO") && line.equalsIgnoreCase("GO"))
        );
    }

    @Override
    public String getDelimiter() {
        return delimiter;
    }

    @Override
    public void setDelimiter(String delimStr) {
        this.delimiter = delimStr.toUpperCase();
    }

    @Override
    public abstract void read() throws MintleafException;


    @Override
    public Map<String, String> getUserVariableMapping() {
        if (userVariableMapping == null) {
            userVariableMapping = new HashMap<>();
        }
        return userVariableMapping;
    }

    @Override
    public void setUserVariableMapping(Map<String, String> userVariableMapping) {
        this.userVariableMapping = userVariableMapping;
    }
}
