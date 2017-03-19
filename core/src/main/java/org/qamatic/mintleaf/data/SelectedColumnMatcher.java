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
import org.qamatic.mintleaf.MetaDataCollection;
import org.qamatic.mintleaf.MintLeafException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by qamatic on 3/5/16.
 */
public class SelectedColumnMatcher implements ColumnMatcher {


    private String selectedColumnMaps;
    private List<String> sourceColumns;
    private List<String> targetColumns;
    private int[] sourceColumnsIndexes;
    private int[] targetColumnsIndexes;

    public SelectedColumnMatcher(String selectedColumnMaps) {
        this.selectedColumnMaps = selectedColumnMaps;
    }

    public List<String> getSourceColumns() {
        prepareColumns();
        return sourceColumns;
    }

    public List<String> getTargetColumns() {
        prepareColumns();
        return targetColumns;
    }

    private int[] getColumnIndexes(List<String> columns, MetaDataCollection metaDataCollection) {
        int[] indices = new int[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            indices[i] = metaDataCollection.getIndex(columns.get(i));
        }
        return indices;
    }


    @Override
    public void match(RowState leftRowState, RowState rightRowState, ComparerListener listener) throws MintLeafException {
        if (listener == null) {
            return;
        }

        final ColumnState sourceColumnState = createSourceColumnStateInstance();
        final ColumnState targetColumnState = createTargetColumnStateInstance();

        sourceColumnsIndexes = getColumnIndexes(getSourceColumns(), leftRowState.getMetaData());
        targetColumnsIndexes = getColumnIndexes(getTargetColumns(), rightRowState.getMetaData());


        if ((leftRowState.Row != null) && (rightRowState.Row != null)) {

            for (int i = 0; i < sourceColumns.size(); i++) {

                sourceColumnState.reset(leftRowState.RowNumber, sourceColumnsIndexes[i], 0,
                        leftRowState.Row.getValue(sourceColumnsIndexes[i]));
                targetColumnState.reset(rightRowState.RowNumber, targetColumnsIndexes[i], 0,
                        rightRowState.Row.getValue(targetColumnsIndexes[i]));

                listener.OnColumnCompare(sourceColumnState, targetColumnState);

            }


        } else if ((leftRowState.Row != null) && (rightRowState.Row == null)) {

            for (int i = 0; i < sourceColumns.size(); i++) {

                sourceColumnState.reset(leftRowState.RowNumber, sourceColumnsIndexes[i], 1,
                        leftRowState.Row.getValue(sourceColumnsIndexes[i]));

                targetColumnState.reset(-1, -1, -1, null);

                listener.OnColumnCompare(sourceColumnState, targetColumnState);

            }


        } else if ((leftRowState.Row == null) && (rightRowState.Row != null)) {


            for (int i = 0; i < targetColumns.size(); i++) {

                sourceColumnState.reset(-1, -1, -1, null);
                targetColumnState.reset(rightRowState.RowNumber, targetColumnsIndexes[i], 1,
                        rightRowState.Row.getValue(targetColumnsIndexes[i]));
                listener.OnColumnCompare(sourceColumnState, targetColumnState);

            }


        }
    }

    protected ColumnState createSourceColumnStateInstance() {
        return new ColumnState();
    }

    protected ColumnState createTargetColumnStateInstance() {
        return new ColumnState();
    }

    private void prepareColumns() {
        if (sourceColumns != null) {
            return;
        }
        sourceColumns = new ArrayList<>();
        targetColumns = new ArrayList<>();

        String[] commaSplits = selectedColumnMaps.split(Pattern.quote(","));
        if (commaSplits.length <= 0) {
            MintLeafException.throwException("selected column maps is empty, unable to proceed");
        }
        for (String sp : commaSplits) {
            String[] maps = sp.split(Pattern.quote("="));
            if (maps.length != 2) {
                MintLeafException.throwException("selected column maps is not defined right. format sourcecolumn=targetcolumn");
            }
            sourceColumns.add(maps[0].trim());
            targetColumns.add(maps[1].trim());
        }
    }
}
