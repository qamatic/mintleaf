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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by qamatic on 2/20/16.
 */
public class ExecuteQuery implements Executable<int[]> {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(ExecuteQuery.class);
    private ConnectionContext connectionContext;
    private StatementListener statementListener;

    private String sql;
    private ParameterBinding parameterBinding;
    private List<String> batchSqls; // ugly.. so need deferred execution but come back later

    public ExecuteQuery(ConnectionContext connectionContext, String sql, ParameterBinding parameterBinding) {
        this.connectionContext = connectionContext;
        this.parameterBinding = parameterBinding;
        this.sql = sql;
    }

    public ExecuteQuery(ConnectionContext connectionContext, List<String> batchSqls) {
        this.connectionContext = connectionContext;
        this.batchSqls = batchSqls;
    }

    @Override
    public int[] execute() throws MintLeafException {

        if (batchSqls != null) {
            try (Statement statement = connectionContext.getConnection().createStatement()) {
                for (String sqlItem : batchSqls) {
                    statement.addBatch(sqlItem);
                }
                int[] result = statement.executeBatch();
                if (this.statementListener != null) {
                    this.statementListener.onAfterExecuteSql(statement);
                }
                return result;
            } catch (SQLException e) {
                logger.error(e);
                throw new MintLeafException(e);
            }
        }

        try (PreparedStatement preparedStatement = connectionContext.getConnection().prepareStatement(this.sql)) {
            ParameterSets parameterSets = new ParameterSets(preparedStatement);
            if (parameterBinding != null) {
                parameterBinding.bindParameters(parameterSets);
            }
            if (parameterSets.isBatch()) {
                return preparedStatement.executeBatch();

            }
            int[] result = new int[]{preparedStatement.execute() ? 1 : 0};
            if (this.statementListener != null) {
                this.statementListener.onAfterExecuteSql(preparedStatement);
            }
            return result;

        } catch (MintLeafException e) {
            logger.error("error fetching data", e);
            throw new MintLeafException(e);
        } catch (SQLException e) {
            logger.error(e);
            throw new MintLeafException(e);
        }
    }


    public void setStatementListener(StatementListener statementListener) {
        this.statementListener = statementListener;
    }
}
