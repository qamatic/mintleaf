/*
 *
 *  *
 *  *  * <!--
 *  *  *   ~
 *  *  *   ~ The MIT License (MIT)
 *  *  *   ~
 *  *  *   ~ Copyright (c) 2010-2017 QAMatic
 *  *  *   ~
 *  *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *  *  *   ~ in the Software without restriction, including without limitation the rights
 *  *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *  *  *   ~ furnished to do so, subject to the following conditions:
 *  *  *   ~
 *  *  *   ~ The above copyright notice and this permission notice shall be included in all
 *  *  *   ~ copies or substantial portions of the Software.
 *  *  *   ~
 *  *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  *   ~ SOFTWARE.
 *  *  *   ~
 *  *  *   ~
 *  *  *   -->
 *  *
 *  *
 *
 */

package org.qamatic.mintleaf;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by qamatic on 3/3/16.
 */
public interface ComparableRow {

    Object getValue(int columnIndex) throws MintLeafException;

    default Object getValue(String columnName) throws MintLeafException{
        return getValue(getIndex(columnName));
    }

    default String asString(String columnName) throws MintLeafException {
        return getValue(columnName).toString();
    }

    default int asInt(String columnName) throws MintLeafException {
        return -1;
    }

    default int getIndex(String columnName) throws MintLeafException {
        columnName = columnName.toUpperCase();
        int j = 0;
        try {
            for (int i = 0; i < getMetaData().getColumnCount(); i++) {
                if (getMetaData().getColumnName(i).equalsIgnoreCase(columnName)) {
                    return i;
                }
            }
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
        return -1;
    }

    default int count() throws MintLeafException {
        try {
            return getMetaData().getColumnCount();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    ResultSetMetaData getMetaData() throws MintLeafException;
}
