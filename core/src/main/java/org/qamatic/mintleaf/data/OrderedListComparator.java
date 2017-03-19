/*
 *
 *  *
 *  *  *
 *  *  *  * <!--
 *  *  *  *   ~
 *  *  *  *   ~ The MIT License (MIT)
 *  *  *  *   ~
 *  *  *  *   ~ Copyright (c) 2010-2017 QAMatic Team
 *  *  *  *   ~
 *  *  *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *  *  *  *   ~ in the Software without restriction, including without limitation the rights
 *  *  *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *  *  *  *   ~ furnished to do so, subject to the following conditions:
 *  *  *  *   ~
 *  *  *  *   ~ The above copyright notice and this permission notice shall be included in all
 *  *  *  *   ~ copies or substantial portions of the Software.
 *  *  *  *   ~
 *  *  *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  *  *   ~ SOFTWARE.
 *  *  *  *   ~
 *  *  *  *   ~
 *  *  *  *   -->
 *  *  *
 *  *  *
 *  *
 *
 */
package org.qamatic.mintleaf.data;

import org.qamatic.mintleaf.ColumnMatcher;
import org.qamatic.mintleaf.DataComparer;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.RowListWrapper;

/**
 * Created by qamatic on 3/5/16.
 */
public class OrderedListComparator implements DataComparer {

    private RowListWrapper sourceTable;
    private RowListWrapper targetTable;
    private ComparerListener comparerListener;
    private ColumnMatcher columnMatcher;


    public OrderedListComparator(RowListWrapper sourceTable, RowListWrapper targetTable) {
        setSourceTable(sourceTable);
        setTargetTable(targetTable);
        setColumnMatcher(new OrderedColumnMatcher());
    }


    protected RowState getRowStateInstance() {
        return new RowState();
    }

    @Override
    public void execute() throws MintLeafException {
        assertBefore();
        if (this.comparerListener != null) {
            this.comparerListener.OnBeginCompare(this.sourceTable, this.targetTable);
        }
        final RowState sourceRowState = getRowStateInstance();
        sourceRowState.setMetaData(this.sourceTable.getMetaData());

        final RowState targetRowState = getRowStateInstance();
        targetRowState.setMetaData(this.targetTable.getMetaData());

        this.sourceTable.resetAll();
        this.targetTable.resetAll();
        while (this.sourceTable.moveNext()) {
            sourceRowState.RowNumber++;
            if (this.targetTable.moveNext()) {
                beforeRowCompare(sourceRowState, targetRowState);

                sourceRowState.Row = this.sourceTable.row();
                sourceRowState.IsSurplusRow = 0;

                targetRowState.RowNumber++;
                targetRowState.Row = this.targetTable.row();
                targetRowState.IsSurplusRow = 0;

                onRowCompare(sourceRowState, targetRowState);
                columnMatcher.match(sourceRowState, targetRowState, this.comparerListener);
                afterRowCompare(sourceRowState, targetRowState);
            } else {
                beforeRowCompare(sourceRowState, targetRowState);
                sourceRowState.Row = this.sourceTable.row();
                sourceRowState.IsSurplusRow = 1;

                targetRowState.Row = null;
                targetRowState.IsSurplusRow = -1;
                onRowCompare(sourceRowState, targetRowState);
                columnMatcher.match(sourceRowState, targetRowState, this.comparerListener);
                afterRowCompare(sourceRowState, targetRowState);
            }
        }
        while (this.targetTable.moveNext()) {

            beforeRowCompare(sourceRowState, targetRowState);
            sourceRowState.Row = null;
            sourceRowState.IsSurplusRow = -1;

            targetRowState.RowNumber++;
            targetRowState.Row = this.targetTable.row();
            targetRowState.IsSurplusRow = 1;
            onRowCompare(sourceRowState, targetRowState);
            columnMatcher.match(sourceRowState, targetRowState, this.comparerListener);
            afterRowCompare(sourceRowState, targetRowState);
        }
        if (this.comparerListener != null) {
            this.comparerListener.OnEndCompare(sourceRowState, targetRowState);
        }
    }

    private void assertBefore() throws MintLeafException {
        if (columnMatcher == null)
            throw new MintLeafException("RowMatcher must be set");
        if (sourceTable == null)
            throw new MintLeafException("SourceTable is missing");
        if (targetTable == null)
            throw new MintLeafException("TargetTable is missing");
    }

    private void onRowCompare(final RowState sourceRowState, final RowState targetRowState) throws MintLeafException {
        if (this.comparerListener != null) {
            this.comparerListener.OnRowCompare(sourceRowState, targetRowState);
        }
    }

    private void beforeRowCompare(final RowState sourceRowState, final RowState targetRowState) throws MintLeafException {
        if (this.comparerListener != null) {
            this.comparerListener.onBeforeRowCompare(sourceRowState, targetRowState);
        }
    }

    private void afterRowCompare(final RowState sourceRowState, final RowState targetRowState) throws MintLeafException {
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
    public RowListWrapper getSourceTable() {
        return this.sourceTable;
    }

    @Override
    public void setSourceTable(RowListWrapper sourceTable) {
        this.sourceTable = sourceTable;
    }

    @Override
    public RowListWrapper getTargetTable() {
        return this.targetTable;
    }

    @Override
    public void setTargetTable(RowListWrapper targetTable) {
        this.targetTable = targetTable;
    }


}
