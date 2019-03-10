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

package org.qamatic.mintleaf.core.rows;

import org.qamatic.mintleaf.MetaDataCollection;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.Row;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by qamatic on 3/3/16.
 */
public class ResultSetRow<T> implements Row {

    private static final MintleafLogger logger = MintleafLogger.getLogger(ResultSetRow.class);
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;


    public ResultSetRow() {

    }

    public ResultSetRow(ResultSet resultSet) {
        this.resultSet = resultSet;

    }

    @Override
    public String getValue(int columnIndex) throws MintleafException {
        try {
            if (resultSet.getObject(columnIndex) == null)
                return "NULL";
            return resultSet.getObject(columnIndex).toString();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }

    }

    @Override
    public String getValue(String columnName) throws MintleafException {
        try {
            if (resultSet.getObject(columnName) == null)
                return "NULL";
            return resultSet.getObject(columnName).toString();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }

    }

    @Override
    public int asInt(String columnName) {
        try {
            return resultSet.getInt(columnName);
        } catch (SQLException e) {
            MintleafException.throwException(e);
        }
        return -1;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public ResultSetMetaData getMetaData() throws MintleafException {
        if (this.resultSetMetaData == null) {
            try {
                this.resultSetMetaData = this.resultSet.getMetaData();
            } catch (SQLException e) {
                throw new MintleafException(e);
            }
        }
        return this.resultSetMetaData;
    }

    @Override
    public void setMetaData(MetaDataCollection metaDataCollection) {
        this.resultSetMetaData = metaDataCollection;
    }


}
