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

import org.qamatic.mintleaf.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by qamatic on 3/4/16.
 */
public class ResultSetRowListWrapper implements RowListWrapper {

    private ResultSet resultSet;
    private MetaDataCollection resultSetMetaData;

    private int current = -1;

    @Override
    public void resetAll() throws MintleafException {

    }

    @Override
    public boolean next() throws MintleafException {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }

    @Override
    public Row row() throws MintleafException {
        return new ResultSetRowWrapper(resultSet);
    }

    public void setResultSet(ResultSet list) {
        this.resultSet = list;
    }

    @Override
    public MetaDataCollection getMetaData() throws MintleafException {
        if (this.resultSetMetaData == null) {
            try {
                this.resultSetMetaData = new ResultSetMetaDataCollection(this.resultSet.getMetaData());
            } catch (SQLException e) {
                throw new MintleafException(e);
            }
        }
        return this.resultSetMetaData;
    }

    public Iterator<Row> iterator() {
        MintleafException.throwException("un-implemented");
        return null;
    }
}
