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

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public interface MintleafReader<T extends Row> extends AutoCloseable {


    void read() throws MintleafException;

    default Map<String, Object> getUserVariableMapping() {
        MintleafException.throwException("not implemented");
        return null;
    }

    default void setUserVariableMapping(Map<String, Object>  userVariableMapping) {
        MintleafException.throwException("not implemented");
    }

    default List<ReadListener> getReadListener() throws MintleafException {
        MintleafException.throwException("not implemented");
        return null;
    }



    default String getDelimiter() {
        MintleafException.throwException("not implemented");
        return null;
    }

    default void setDelimiter(String delimStr) {
        MintleafException.throwException("not implemented");
    }

    default Charset getCharset() {
        return Charset.defaultCharset();
    }

    @Override
    default void close() {

    }

    default boolean canContinueRead(Row row) {
        return true;
    }

    default boolean matches(T row) {
        return true;
    }



}
