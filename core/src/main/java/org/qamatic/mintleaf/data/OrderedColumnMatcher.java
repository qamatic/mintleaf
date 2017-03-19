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

import org.qamatic.mintleaf.ColumnMatcher;
import org.qamatic.mintleaf.MintLeafException;

import java.sql.SQLException;

/**
 * Created by qamatic on 3/5/16.
 */
public class OrderedColumnMatcher implements ColumnMatcher {


    @Override
    public void match(RowState leftRowState, RowState rightRowState, ComparerListener listener) throws MintLeafException {
        if (listener == null) {
            return;
        }


        final ColumnState sourceColumnState = createSourceColumnStateInstance();
        final ColumnState targetColumnState = createTargetColumnStateInstance();

        int sourceColumnNumber = sourceColumnState.getColumnNumber();
        int targetColumnNumber = targetColumnState.getColumnNumber();


        try {
            if ((leftRowState.Row != null) && (rightRowState.Row != null)) {

                while (sourceColumnNumber < leftRowState.Row.getMetaData().getColumnCount() - 1) {
                    sourceColumnNumber++;
                    targetColumnNumber++;

                    sourceColumnState.reset(leftRowState.RowNumber, sourceColumnNumber, 0,
                            leftRowState.Row.getValue(sourceColumnNumber));
                    targetColumnState.reset(rightRowState.RowNumber, targetColumnNumber, 0,
                            rightRowState.Row.getValue(targetColumnNumber));

                    listener.OnColumnCompare(sourceColumnState, targetColumnState);

                }

                while (targetColumnNumber < rightRowState.Row.getMetaData().getColumnCount() - 1) {
                    targetColumnNumber++;

                    sourceColumnState.reset(-1, -1, -1, null);
                    targetColumnState.reset(rightRowState.RowNumber, targetColumnNumber, 0,
                            rightRowState.Row.getValue(targetColumnNumber));

                    listener.OnColumnCompare(sourceColumnState, targetColumnState);

                }

            } else if ((leftRowState.Row != null) && (rightRowState.Row == null)) {

                while (sourceColumnNumber < leftRowState.Row.getMetaData().getColumnCount() - 1) {
                    sourceColumnNumber++;

                    sourceColumnState.reset(leftRowState.RowNumber, sourceColumnNumber, 1,
                            leftRowState.Row.getValue(sourceColumnNumber));
                    targetColumnState.reset(-1, -1, -1, null);

                    listener.OnColumnCompare(sourceColumnState, targetColumnState);

                }

            } else if ((leftRowState.Row == null) && (rightRowState.Row != null)) {

                while (targetColumnNumber < rightRowState.Row.getMetaData().getColumnCount() - 1) {
                    targetColumnNumber++;

                    sourceColumnState.reset(-1, -1, -1, null);
                    targetColumnState.reset(rightRowState.RowNumber, targetColumnNumber, 1,
                            rightRowState.Row.getValue(targetColumnNumber));
                    listener.OnColumnCompare(sourceColumnState, targetColumnState);

                }
            }
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    protected ColumnState createSourceColumnStateInstance() {
        return new ColumnState();
    }

    protected ColumnState createTargetColumnStateInstance() {
        return new ColumnState();
    }
}
