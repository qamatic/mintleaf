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

import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.RowMatcher;

/**
 * Created by qamatic on 3/5/16.
 */
public class OrderedColumnMatcher implements RowMatcher {
    @Override
    public void match(RowState leftRowState, RowState rightRowState, ComparerListener listener) throws MintLeafException {
        if ((leftRowState.Row != null) && (rightRowState.Row != null)) {

            while (leftRowState.ColumnNumber < leftRowState.Row.count() - 1) {
                leftRowState.ColumnNumber++;
                rightRowState.ColumnNumber++;


                if (listener != null) {
                    listener.OnCompare(leftRowState, rightRowState);
                }


            }
            while (rightRowState.ColumnNumber < rightRowState.Row.count() - 1) {
                rightRowState.ColumnNumber++;
                if (listener != null) {
                    listener.OnCompare(leftRowState, rightRowState);
                }
            }

        } else if ((leftRowState.Row != null) && (rightRowState.Row == null)) {

            while (leftRowState.ColumnNumber < leftRowState.Row.count() - 1) {
                leftRowState.ColumnNumber++;
                if (listener != null) {
                    listener.OnCompare(leftRowState, rightRowState);
                }
            }

        } else if ((leftRowState.Row == null) && (rightRowState.Row != null)) {

            while (rightRowState.ColumnNumber < rightRowState.Row.count() - 1) {
                rightRowState.ColumnNumber++;
                if (listener != null) {
                    listener.OnCompare(leftRowState, rightRowState);
                }
            }
        }

    }
}
