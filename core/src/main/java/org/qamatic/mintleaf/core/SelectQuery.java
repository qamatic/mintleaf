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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by qamatic on 2/20/16.
 */
public class SelectQuery implements Executable<SqlResultSet> {

    private static final MintleafLogger logger = MintleafLogger.getLogger(SelectQuery.class);
    private ConnectionContext connectionContext;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String sql;
    private ParameterBinding parameterBinding;

    public SelectQuery(ConnectionContext connectionContext, String sql, ParameterBinding parameterBinding) {
        this.connectionContext = connectionContext;
        this.parameterBinding = parameterBinding;
        this.sql = sql;
    }

    private ResultSet getResultSet() throws MintleafException {
        if (this.resultSet == null) {
            try {
                this.preparedStatement = connectionContext.getConnection().prepareStatement(this.sql);
                if (this.resultSet == null) {
                    if (parameterBinding != null) {
                        parameterBinding.bindParameters(new BindingParameterSets(this.preparedStatement));
                    }
                    logger.info("executing select query: " + this.sql);
                    this.resultSet = this.preparedStatement.executeQuery();
                }
            } catch (MintleafException e) {
                logger.error("error fetching data", e);
                throw new MintleafException(e);
            } catch (SQLException e) {
                logger.error(e);
                throw new MintleafException(e);
            }
        }

        return this.resultSet;
    }


    @Override
    public void close() throws MintleafException {
        try {
            if (this.resultSet != null) {
                this.resultSet.close();
            }
            if (this.preparedStatement != null) {
                this.preparedStatement.close();
            }
            this.resultSet = null;
            this.preparedStatement = null;
        } catch (SQLException e) {
            logger.error("FluentJdbc close()", e);
            throw new MintleafException(e);
        }
    }


    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws MintleafException if unable to compute a result
     */
    @Override
    public SqlResultSet execute() throws MintleafException {
        return new SelectQueryWrapper(this);
    }

    private class SelectQueryWrapper implements SqlResultSet {
        private SelectQuery selectQuery;

        private SelectQueryWrapper(SelectQuery selectQuery) {
            this.selectQuery = selectQuery;
        }

        @Override
        public ResultSet getResultSet() throws MintleafException {
            return this.selectQuery.getResultSet();
        }

        @Override
        public void close() throws MintleafException {
            this.selectQuery.close();
        }

        @Override
        public ResultSet first() throws MintleafException {
            try {
                getResultSet().next();
                return getResultSet();
            } catch (SQLException e) {

                logger.error(e);
                throw new MintleafException(e);
            }
        }

        @Override
        public <T> void iterate(MintleafReadListener<T> listener) throws MintleafException, MintleafException {
            try {
                int i = 0;
                while (getResultSet().next()) {
                    Row row = new ResultSetRowWrapper<T>(getResultSet());
                    if (listener.matches(row))
                        listener.eachRow(i++, row);
                    if (!listener.canContinueRead(row))
                        break;
                }
            } catch (SQLException e) {

                logger.error(e);
                throw new MintleafException(e);
            }
        }

        @Override
        public RowListWrapper asRowListWrapper() throws MintleafException {
            ResultSetRowListWrapper rowListWrapper = new ResultSetRowListWrapper();
            rowListWrapper.setResultSet(this.getResultSet());
            return rowListWrapper;
        }
    }
}
