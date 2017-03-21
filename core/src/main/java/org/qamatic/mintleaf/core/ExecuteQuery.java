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

import org.qamatic.mintleaf.DbCallable;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.MintLeafLogger;
import org.qamatic.mintleaf.ParameterBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by qamatic on 2/20/16.
 */
public class ExecuteQuery implements DbCallable<Boolean> {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(ExecuteQuery.class);
    private Connection connection;
    private PreparedStatement preparedStatement;
    private String sql;
    private ParameterBinding parameterBinding;

    public ExecuteQuery(Connection connection, String sql, ParameterBinding parameterBinding) {
        this.connection = connection;
        this.parameterBinding = parameterBinding;
        this.sql = sql;
    }

    @Override
    public Boolean execute() throws MintLeafException {

        try {
            this.preparedStatement = connection.prepareStatement(this.sql);

            if (parameterBinding != null) {
                parameterBinding.bindParameters(new ParameterSets(this.preparedStatement));
            }
            return this.preparedStatement.execute();

        } catch (MintLeafException e) {
            logger.error("error fetching data", e);
            throw new MintLeafException(e);
        } catch (SQLException e) {
            logger.error(e);
            throw new MintLeafException(e);
        } finally {
            close();
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        return this.connection;
    }

    @Override
    public void close() throws MintLeafException {
        try {
            if (this.preparedStatement != null) {
                this.preparedStatement.close();
            }

            this.preparedStatement = null;
        } catch (SQLException e) {
            logger.error("FluentJdbc close()", e);
            throw new MintLeafException(e);
        }
    }


}
