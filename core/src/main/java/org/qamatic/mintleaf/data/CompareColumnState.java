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

package org.qamatic.mintleaf.data;

import org.qamatic.mintleaf.MintleafException;

/**
 * Created by qamatic on 3/5/16.
 */
public class CompareColumnState {

    private int columnNumber = -1;
    private int rowNumber = -1;
    private int isSurplusRow;
    private String columnName;
    private Object columnValue;

    public CompareColumnState() {
        reset();
    }

    public String asString() throws MintleafException {
        return getColumnValue().toString();
    }

    public int asInt() throws MintleafException {
        return (int) getColumnValue();
    }

    public double asDouble() throws MintleafException {
        return (double) getColumnValue();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return getColumnValue() == null ? "null" : getColumnValue().toString();
    }

    public void reset() {
        setColumnNumber(-1);
        setRowNumber(-1);
        setIsSurplusRow(0);
    }


    public void reset(int rowNumber, int columnNumber, int isSurplusRow, Object columnValue) {
        setColumnNumber(columnNumber);
        setRowNumber(rowNumber);
        setIsSurplusRow(isSurplusRow);
        setColumnValue(columnValue);
    }

    public void reset(int rowNumber, int columnNumber, int isSurplusRow, String columnName, Object columnValue) {
        reset(columnNumber, rowNumber, isSurplusRow, columnValue);
        setColumnName(columnName);
    }

    public Object getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(Object columnValue) {
        this.columnValue = columnValue;
    }


    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getIsSurplusRow() {
        return isSurplusRow;
    }

    public void setIsSurplusRow(int isSurplusRow) {
        this.isSurplusRow = isSurplusRow;
    }

    public String toLog() {
        return String.format("RowNo:%d, ColumnNo:%d, Surplus:%d, Value:%s", getRowNumber(), getColumnNumber(), getIsSurplusRow(), toString());
    }


}
