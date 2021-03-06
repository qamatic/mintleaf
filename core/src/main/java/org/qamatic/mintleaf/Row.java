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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by qamatic on 3/3/16.
 */
public interface Row {

    public static int INTERNAL_OBJECT_VALUE = -99999;

    Object getValue(int columnIndex) throws MintleafException;

    default Object getValue(String columnName) throws MintleafException {
        return getValue(getIndex(columnName));
    }

    default String asString(String columnName) {
        try {
            return getValue(columnName).toString();
        } catch (MintleafException e) {
            MintleafException.throwException(e);
        }
        return null;
    }

    default int asInt(String columnName) {
        try {
            Object o = getValue(columnName);
            if (o instanceof String) {
                return new Integer(o.toString());
            } else if (o instanceof Integer) {
                return (int) o;
            }
        } catch (MintleafException e) {
            MintleafException.throwException(e);
        }
        MintleafException.throwException(columnName + " is not of integer type");
        return -1;
    }

    default int getIndex(String columnName) throws MintleafException {
        columnName = columnName.toUpperCase();
        int j = 0;
        try {
            for (int i = 0; i < getMetaData().getColumnCount(); i++) {
                if (getMetaData().getColumnName(i).equalsIgnoreCase(columnName)) {
                    return i;
                }
            }
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
        return -1;
    }

    ResultSetMetaData getMetaData() throws MintleafException;

    void setMetaData(MetaDataCollection metaDataCollection);

    default void setValue(int columnIndex, Object value) {

    }

    default void setValue(int columnIndex, byte[] value, Charset charset) {

    }

    default void setValues(byte[] byteRecord, Charset charset) {

    }
}
