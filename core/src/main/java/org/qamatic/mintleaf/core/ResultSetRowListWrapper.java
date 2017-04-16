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
public class ResultSetRowListWrapper<T extends Row> implements RowListWrapper<T> {

    private ResultSet resultSet;
    private MetaDataCollection resultSetMetaData;

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public T getRow(int index) throws MintleafException {
        throw new MintleafException("unsupported");
    }

    @Override
    public boolean isEmpty() {
        MintleafException.throwException("unsupported");
        return false;
    }

    @Override
    public void clear() {
        MintleafException.throwException("unsupported");
    }

    @Override
    public int size() {
        MintleafException.throwException("unsupported");
        return -1;
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

    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private ResultSet current;

            private ResultSet getNextRecord() {
                try {
                    if (ResultSetRowListWrapper.this.resultSet.next()){
                        return ResultSetRowListWrapper.this.resultSet;
                    }
                } catch (SQLException e) {
                    MintleafException.throwException(e);
                }

                return null;
            }

            @Override
            public boolean hasNext() {
                if (this.current == null) {
                    this.current = this.getNextRecord();
                }

                return this.current != null;
            }

            @Override
            public T next() {
                ResultSet next = this.current;
                this.current = null;
                if(next == null) {
                    next = this.getNextRecord();
                    if(next == null) {
                        MintleafException.throwException("reached end of records, no more elements");
                    }
                }
                T t = (T) new ResultSetRowWrapper<T>(next);
                return t;
            }
        };
    }
}
