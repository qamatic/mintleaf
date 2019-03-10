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

import org.qamatic.mintleaf.*;

import java.util.Iterator;

/**
 * Created by qamatic on 3/5/16.
 */
public class OrderedListComparator implements DataComparer {

    private Table<? extends Row> sourceTable;
    private Table<? extends Row> targetTable;
    private ComparerListener comparerListener;
    private ColumnMatcher columnMatcher;


    public OrderedListComparator(Table<? extends Row> sourceTable, Table<? extends Row> targetTable) {
        setSourceTable(sourceTable);
        setTargetTable(targetTable);
        setColumnMatcher(new OrderedColumnMatcher());
    }


    protected CompareRowState getRowStateInstance() {
        return new CompareRowState();
    }

    @Override
    public Boolean execute() throws MintleafException {
        assertBefore();
        if (this.comparerListener != null) {
            this.comparerListener.OnBeginCompare(this.sourceTable, this.targetTable);
        }
        final CompareRowState sourceRowState = getRowStateInstance();
        sourceRowState.setMetaData(this.sourceTable.getMetaData());

        final CompareRowState targetRowState = getRowStateInstance();
        targetRowState.setMetaData(this.targetTable.getMetaData());


        Iterator<? extends Row> sourceIterator = this.sourceTable.iterator();
        Iterator<? extends Row> targetIterator = this.targetTable.iterator();

        while (sourceIterator.hasNext()) {
            sourceRowState.RowNumber++;
            if (targetIterator.hasNext()) {
                beforeRowCompare(sourceRowState, targetRowState);

                if (!sourceIterator.hasNext()) {
                    throw new MintleafException("source list does not have anymore elements to iterate thru");
                }
                sourceRowState.Row = sourceIterator.next();
                sourceRowState.IsSurplusRow = 0;

                targetRowState.RowNumber++;
                targetRowState.Row = targetIterator.next();
                targetRowState.IsSurplusRow = 0;

                onRowCompare(sourceRowState, targetRowState);
                columnMatcher.match(sourceRowState, targetRowState, this.comparerListener);
                afterRowCompare(sourceRowState, targetRowState);
            } else {
                beforeRowCompare(sourceRowState, targetRowState);
                if (!sourceIterator.hasNext()) {
                    throw new MintleafException("source list does not have anymore elements to iterate thru");
                }
                sourceRowState.Row = sourceIterator.next();
                sourceRowState.IsSurplusRow = 1;

                targetRowState.Row = null;
                targetRowState.IsSurplusRow = -1;
                onRowCompare(sourceRowState, targetRowState);
                columnMatcher.match(sourceRowState, targetRowState, this.comparerListener);
                afterRowCompare(sourceRowState, targetRowState);
            }
        }
        while (targetIterator.hasNext()) {

            beforeRowCompare(sourceRowState, targetRowState);
            sourceRowState.Row = null;
            sourceRowState.IsSurplusRow = -1;

            targetRowState.RowNumber++;
            targetRowState.Row = targetIterator.next();
            targetRowState.IsSurplusRow = 1;
            onRowCompare(sourceRowState, targetRowState);
            columnMatcher.match(sourceRowState, targetRowState, this.comparerListener);
            afterRowCompare(sourceRowState, targetRowState);
        }
        if (this.comparerListener != null) {
            this.comparerListener.OnEndCompare(sourceRowState, targetRowState);
        }
        return true;
    }

    private void assertBefore() throws MintleafException {
        if (columnMatcher == null)
            throw new MintleafException("RowMatcher must be set");
        if (sourceTable == null)
            throw new MintleafException("SourceTable is missing");
        if (targetTable == null)
            throw new MintleafException("TargetTable is missing");
    }

    private void onRowCompare(final CompareRowState sourceRowState, final CompareRowState targetRowState) throws MintleafException {
        if (this.comparerListener != null) {
            this.comparerListener.OnRowCompare(sourceRowState, targetRowState);
        }
    }

    private void beforeRowCompare(final CompareRowState sourceRowState, final CompareRowState targetRowState) throws MintleafException {
        if (this.comparerListener != null) {
            this.comparerListener.onBeforeRowCompare(sourceRowState, targetRowState);
        }
    }

    private void afterRowCompare(final CompareRowState sourceRowState, final CompareRowState targetRowState) throws MintleafException {
        if (this.comparerListener != null) {
            this.comparerListener.onAfterRowCompare(sourceRowState, targetRowState);
        }
    }

    @Override
    public void setComparerListener(ComparerListener comparerListener) {
        this.comparerListener = comparerListener;
    }

    public void setColumnMatcher(ColumnMatcher columnMatcher) {
        this.columnMatcher = columnMatcher;
    }

    @Override
    public Table getSourceTable() {
        return this.sourceTable;
    }

    @Override
    public void setSourceTable(Table<? extends Row> sourceTable) {
        this.sourceTable = sourceTable;
    }

    @Override
    public Table getTargetTable() {
        return this.targetTable;
    }

    @Override
    public void setTargetTable(Table<? extends Row> targetTable) {
        this.targetTable = targetTable;
    }


}
