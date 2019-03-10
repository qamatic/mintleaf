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

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.SqlResultSet;
import org.qamatic.mintleaf.core.BaseReader;
import org.qamatic.mintleaf.core.rows.ResultSetRow;

import java.sql.SQLException;

/**
 * Created by qamatic on 2/18/6/16.
 */
public class DbResultSetReader<T> extends BaseReader {

    private static final MintleafLogger logger = MintleafLogger.getLogger(DbResultSetReader.class);
    private SqlResultSet resultSet;

    public DbResultSetReader(SqlResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public void read() throws MintleafException {
        int i = 0;
        try {
            while (this.resultSet.getResultSet().next()) {
                ResultSetRow dbRowWrapper = new ResultSetRow();
                dbRowWrapper.setResultSet(this.resultSet.getResultSet());
                if (!readRow(i++, dbRowWrapper)) {
                    break;
                }
            }
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }

    @Override
    public void close() {
        try {
            resultSet.close();
        } catch (MintleafException e) {
            MintleafException.throwException(e);
        }
    }


}
