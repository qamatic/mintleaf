/*
 *
 *  *
 *  *  * <!--
 *  *  *   ~
 *  *  *   ~ The MIT License (MIT)
 *  *  *   ~
 *  *  *   ~ Copyright (c) 2010-2017 QAMatic
 *  *  *   ~
 *  *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *  *  *   ~ in the Software without restriction, including without limitation the rights
 *  *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *  *  *   ~ furnished to do so, subject to the following conditions:
 *  *  *   ~
 *  *  *   ~ The above copyright notice and this permission notice shall be included in all
 *  *  *   ~ copies or substantial portions of the Software.
 *  *  *   ~
 *  *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  *   ~ SOFTWARE.
 *  *  *   ~
 *  *  *   ~
 *  *  *   -->
 *  *
 *  *
 *
 */
package org.qamatic.mintleaf.data;

import org.qamatic.mintleaf.DataComparer;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.RowListWrapper;
import org.qamatic.mintleaf.RowMatcher;
import org.qamatic.mintleaf.core.ObjectRowListWrapper;

import java.util.List;

/**
 * Created by qamatic on 3/5/16.
 */
public class OrderedListComparator implements DataComparer {

    private RowListWrapper sourceTable;
    private RowListWrapper targetTable;
    private ComparerListener comparerListener;
    private RowMatcher rowMatcher;

    public OrderedListComparator() {

    }

    public static DataComparer create(RowListWrapper sourceTable, RowListWrapper targetTable) {
        DataComparer dataComparer = new OrderedListComparator();
        dataComparer.setSourceTable(sourceTable);
        dataComparer.setTargetTable(targetTable);
        dataComparer.setRowMatcher(new OrderedColumnMatcher());
        return dataComparer;
    }

    public static DataComparer create(List sourceList, List targetList) {
        DataComparer dataComparer = OrderedListComparator.create(
                new ObjectRowListWrapper() {{
                    setList(sourceList);
                }},
                new ObjectRowListWrapper() {{
                    setList(targetList);
                }});

        return dataComparer;
    }

    protected RowState getRowStateInstance(){
        return new RowState();
    }

    @Override
    public void execute() throws MintLeafException {
        assertBefore();
        final RowState sourceRowState = getRowStateInstance();
        final RowState targetRowState = getRowStateInstance();
        this.sourceTable.resetAll();
        this.targetTable.resetAll();
        while (this.sourceTable.moveNext()) {
            sourceRowState.RowNumber++;
            if (this.targetTable.moveNext()) {
                beforeRowCompare(sourceRowState, targetRowState);

                sourceRowState.Row = this.sourceTable.row();
                sourceRowState.IsSurplusRow = 0;
                sourceRowState.ColumnNumber = -1;

                targetRowState.RowNumber++;
                targetRowState.Row = this.targetTable.row();
                targetRowState.IsSurplusRow = 0;
                targetRowState.ColumnNumber = -1;

                rowMatcher.match(sourceRowState, targetRowState, this.comparerListener);
                afterRowCompare(sourceRowState, targetRowState);
            } else {
                beforeRowCompare(sourceRowState, targetRowState);
                sourceRowState.Row = this.sourceTable.row();
                sourceRowState.IsSurplusRow = 1;
                sourceRowState.ColumnNumber = -1;

                targetRowState.Row = null;
                targetRowState.IsSurplusRow = -1;
                targetRowState.ColumnNumber = -1;

                rowMatcher.match(sourceRowState, targetRowState, this.comparerListener);
                afterRowCompare(sourceRowState, targetRowState);
            }
        }
        while (this.targetTable.moveNext()) {

            beforeRowCompare(sourceRowState, targetRowState);
            sourceRowState.Row = null;
            sourceRowState.IsSurplusRow = -1;
            sourceRowState.ColumnNumber = -1;

            targetRowState.RowNumber++;
            targetRowState.Row = this.targetTable.row();
            targetRowState.IsSurplusRow = 1;
            targetRowState.ColumnNumber = -1;
            rowMatcher.match(sourceRowState, targetRowState, this.comparerListener);
            afterRowCompare(sourceRowState, targetRowState);
        }
    }

    private void assertBefore() throws MintLeafException {
        if (rowMatcher == null)
            throw new MintLeafException("RowMatcher must be set");
        if (sourceTable == null)
            throw new MintLeafException("SourceTable is missing");
        if (targetTable == null)
            throw new MintLeafException("TargetTable is missing");
    }

    private void beforeRowCompare(final RowState sourceRowState, final RowState targetRowState) {
        if (this.comparerListener != null) {
            this.comparerListener.onBeforeRowCompare(sourceRowState, targetRowState);
        }
    }

    private void afterRowCompare(final RowState sourceRowState, final RowState targetRowState) {
        if (this.comparerListener != null) {
            this.comparerListener.onAfterRowCompare(sourceRowState, targetRowState);
        }
    }

    @Override
    public void setComparerListener(ComparerListener comparerListener) {
        this.comparerListener = comparerListener;
    }

    @Override
    public void setRowMatcher(RowMatcher rowMatcher) {
        this.rowMatcher = rowMatcher;
    }

    @Override
    public void setSourceTable(RowListWrapper sourceTable) {
        this.sourceTable = sourceTable;
    }

    @Override
    public void setTargetTable(RowListWrapper targetTable) {
        this.targetTable = targetTable;
    }


}
