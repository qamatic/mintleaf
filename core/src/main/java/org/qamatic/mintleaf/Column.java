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

import java.sql.Date;
import java.sql.Types;

public class Column {
    protected String columnName;
    protected String typeName;
    protected int dataType;
    protected boolean nullable;
    protected int columnSize;
    protected int decimalDigits;
    protected boolean nonDbColumn;
    protected boolean ignoreColumn;

    public Column() {

    }

    public Column(String columnName) {
        this.columnName = columnName;
        this.dataType = Types.VARCHAR;
    }

    public Column(String columnName, int columnSize, int dataType) {
        this.columnName = columnName;
        this.columnSize = columnSize;
        this.dataType = dataType;
    }


    public Column(String columnName, int dataType) {
        this.columnName = columnName;
        this.dataType = dataType;
    }


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String dataType) {
        typeName = dataType;
    }

    public int getDatatype() {
        return dataType;
    }

    public void setDatatype(int dataType) {
        this.dataType = dataType;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable == 1;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    @SuppressWarnings("rawtypes")
    public Class getJavaDataType() {

        switch (getDatatype()) {
            case Types.DECIMAL:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.NUMERIC:
                if (getDecimalDigits() != 0) {
                    return Double.class;
                }
                return Integer.class;

            case Types.DATE:
            case Types.TIMESTAMP:
                return Date.class;

            default:
                return String.class;

        }

    }

    public boolean isCalculated() {

        return nonDbColumn;
    }

    public void setCalculated(boolean calculated) {
        this.nonDbColumn = calculated;
    }

    public boolean isIgnoreColumn() {
        return ignoreColumn;
    }

    public void setIgnoreForTypeObjectCreation(boolean ignoreForTypeObjectCreation) {
        ignoreColumn = ignoreForTypeObjectCreation;
    }

}
