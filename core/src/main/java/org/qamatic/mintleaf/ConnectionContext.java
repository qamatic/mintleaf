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

import org.qamatic.mintleaf.core.FluentJdbc;
import org.qamatic.mintleaf.core.StoredProcedure;

import java.sql.Connection;
import java.util.List;

/**
 * Created by QAmatic Team on 3/19/17.
 */
public interface ConnectionContext<T extends DbQueryExtension> extends AutoCloseable {

    Connection getConnection() throws MintleafException;

    boolean isCloseable();

    void close() throws MintleafException;

    ConnectionContext beginTransaction() throws MintleafException;

    void commitTransaction() throws MintleafException;

    void rollbackTransaction() throws MintleafException;

    T getDbQueries();


    FluentJdbc.Builder queryBuilder();

    SqlResultSet selectQuery(String sql);

    Executable<int[]> executeBatchSqls(List<String> batchSqls);

    Executable<int[]> executeSql(String sql);

    Executable<int[]> executeSql(String sql, Object[] parameterValues);

    <T> List<T> query(String sql, ParameterBinding parameterBinding, final DataRowListener<T> listener) throws MintleafException;

    Executable<int[]> executeStoredProc(String procedureCall, StoredProcedure.CallType callType, ParameterBinding.Callable parameterBinding);

    Executable<int[]> executeStoredProc(String procedureCall, StoredProcedure.CallType callType, ParameterBinding.Callable parameterBinding, ExecutionResultListener.Callable executionResultListener);
}
