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

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public interface DbQueryExtension extends AutoCloseable {


    default boolean isSqlObjectExists(String objectName, String objectType, boolean ignoreValidity) throws MintleafException {
        throw new NotImplementedException();
    }

    default int getCount(String tableName) throws MintleafException {
        return queryInt(String.format("select count(*) from %s", tableName), null);
    }

    default boolean isTableExists(String tableName) throws MintleafException {
        throw new NotImplementedException();
    }

    default void close() throws MintleafException {

    }

    ;

    default boolean isDbOptionExists(String optionName) throws MintleafException {
        throw new NotImplementedException();
    }

    <T> List<T> query(String sql, ParameterBinding parameterBinding, final ReadListener<T> listener) throws MintleafException;

    default <T> List<T> query(String sql, final ReadListener<T> listener) throws MintleafException {
        return query(sql, null, listener);
    }

    default List<String> queryString(String sql, ParameterBinding parameterBinding, String columnName) throws MintleafException {
        return query(sql, parameterBinding, (row, resultSet) -> resultSet.asString(columnName));
    }


    int queryInt(String sql, ParameterBinding parameterBinding) throws MintleafException;

    default void truncateTable(String tableName) throws MintleafException {
        throw new NotImplementedException();
    }

    default boolean isUserExists(String userName) throws MintleafException {
        throw new NotImplementedException();
    }

    default List<String> getSqlObjects(String objectType) throws MintleafException {
        throw new NotImplementedException();
    }

    default List<String> getPrimaryKeys(String ownerName, String tableName) throws MintleafException {
        throw new NotImplementedException();
    }

    default ColumnMetaDataCollection getMetaData(String objectName) throws MintleafException {
        throw new NotImplementedException();
    }

    default boolean isPrivilegeExists(String granteeName, String privilegeName, String objectName) throws MintleafException {
        throw new NotImplementedException();
    }

    default boolean isColumnExists(String tableName, String columnName) throws MintleafException {
        throw new NotImplementedException();
    }


}
