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

/**
 * Created by qamatic on 2/20/16.
 */
public class FluentJdbc {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(FluentJdbc.class);

    public static final class Builder {
        private String sql;
        private ParameterBinding parameterBinding;
        private ConnectionContext connectionContext;
        private ExecutionResultListener executionResultListener;

        public Builder(ConnectionContext connectionContext) {
            this.connectionContext = connectionContext;
        }

        public Builder withSql(String sql) {
            this.sql = sql;
            return this;
        }


        public Builder withParamValues(ParameterBinding parameterBinding) {
            this.parameterBinding = parameterBinding;
            return this;
        }

        public Builder withParamValues(final Object[] parameterValues) {
            this.parameterBinding = parameterSets -> {
                for (int i = 0; i < parameterValues.length; i++) {
                    parameterSets.setObject(i + 1, parameterValues[i]);
                }
            };
            return this;
        }


        public Builder withListener(ExecutionResultListener executionResultListener) throws MintLeafException {
            this.executionResultListener = executionResultListener;
            return this;
        }


        public Executable<int[]> buildExecute() {
            try {
                ExecuteQuery query = new ExecuteQuery(this.connectionContext, this.sql, this.parameterBinding);
                query.setExecutionResultListener(this.executionResultListener);
                return query;
            } catch (Exception e) {
                MintLeafException.throwException(e.getMessage());
            }
            return null;
        }

        public SqlResultSet buildSelect() {

            try {
                return new SelectQuery(this.connectionContext, this.sql, this.parameterBinding).execute();
            } catch (Exception e) {
                MintLeafException.throwException(e.getMessage());
            }
            return null;
        }
    }
}
