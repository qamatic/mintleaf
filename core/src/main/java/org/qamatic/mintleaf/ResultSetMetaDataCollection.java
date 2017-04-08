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

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultSetMetaDataCollection extends ArrayList<Column> implements MetaDataCollection {

    private String objectName;
    private ResultSetMetaData resultSetMetaData;

    public ResultSetMetaDataCollection(ResultSetMetaData resultSetMetaData) {
        this.resultSetMetaData = resultSetMetaData;

        //need to move from constructor but bad here
        try {
            for (int i = 1; i < this.resultSetMetaData.getColumnCount(); i++) {
                add(new Column(this.resultSetMetaData.getColumnName(i), this.resultSetMetaData.getColumnType(i)));
            }
        } catch (SQLException e) {
            MintleafException.throwException(e);
        }
    }


    @Override
    public boolean add(Column column) {
        Column col = this.findColumn(column.getColumnName());
        if (col != null) {
            MintleafException.throwException("column already exists " + column.getColumnName());
        }
        return super.add(column);
    }

    public void add(int idx, Column column) {
        Column col = this.findColumn(column.getColumnName());
        if (col != null) {
            MintleafException.throwException("column already exists " + column.getColumnName());
        }
        add(idx, column);
    }


    @Override
    public int getIndex(String columnName) {
        columnName = columnName.toUpperCase();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getColumnName().equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Column findColumn(String columnName) {
        int idx = getIndex(columnName);
        if (idx != -1) {
            return this.get(idx);
        }
        return null;
    }


    public String getObjectName() {
        return objectName;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Column mdata : this) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(mdata.getColumnName());
        }
        return sb.toString();
    }

    @Override
    public int getColumnCount() throws SQLException {
        return this.resultSetMetaData.getColumnCount();
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        return this.resultSetMetaData.isAutoIncrement(column);
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        return this.resultSetMetaData.isCaseSensitive(column);
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        return this.resultSetMetaData.isSearchable(column);
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        return this.resultSetMetaData.isCurrency(column);
    }

    @Override
    public int isNullable(int column) throws SQLException {
        return this.resultSetMetaData.isNullable(column);
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        return this.resultSetMetaData.isSigned(column);
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        return this.resultSetMetaData.getColumnDisplaySize(column);
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        return this.resultSetMetaData.getColumnLabel(column);
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        return this.resultSetMetaData.getColumnName(column);
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        return this.resultSetMetaData.getSchemaName(column);
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        return this.resultSetMetaData.getPrecision(column);
    }

    @Override
    public int getScale(int column) throws SQLException {
        return this.resultSetMetaData.getScale(column);
    }

    @Override
    public String getTableName(int column) throws SQLException {
        return this.resultSetMetaData.getTableName(column);
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        return this.resultSetMetaData.getCatalogName(column);
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        return this.resultSetMetaData.getColumnType(column);
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        return this.resultSetMetaData.getColumnTypeName(column);
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        return this.resultSetMetaData.isReadOnly(column);
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        return this.resultSetMetaData.isWritable(column);
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        return this.resultSetMetaData.isDefinitelyWritable(column);
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        return this.resultSetMetaData.getColumnClassName(column);
    }


}
