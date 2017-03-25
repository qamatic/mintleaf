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

package org.qamatic.mintleaf.builders;

import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.core.ObjectRowListWrapper;
import org.qamatic.mintleaf.core.ResultSetRowListWrapper;
import org.qamatic.mintleaf.data.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by QAmatic Team on 3/25/17.
 */
public final class ComparerBuilder {
    private static final MintLeafLogger logger = MintLeafLogger.getLogger(ComparerBuilder.class);
    private RowListWrapper sourceTable;
    private RowListWrapper targetTable;
    private ComparerListener comparerListener;//= new ConsoleComparerListener();
    private ColumnMatcher columnMatcher;
    private String selectedColumnMaps;

    public ComparerBuilder withSourceTable(List<? extends ComparableRow> sourceTable, MetaDataCollection metaDataCollection) {
        this.sourceTable = new ObjectRowListWrapper(sourceTable, metaDataCollection);
        return this;
    }

    public ComparerBuilder withSourceTable(RowListWrapper sourceTable) {
        this.sourceTable = sourceTable;
        return this;
    }

    public ComparerBuilder withTargetTable(List<? extends ComparableRow> targetTable, MetaDataCollection metaDataCollection) {
        this.targetTable = new ObjectRowListWrapper(targetTable, metaDataCollection);
        return this;
    }

    public ComparerBuilder withTargetTable(RowListWrapper targetTable) {
        this.targetTable = targetTable;
        return this;
    }

    public ComparerBuilder withColumnMatchingLogic(ColumnMatcher columnMatcher) {
        this.columnMatcher = columnMatcher;
        return this;
    }


    public ComparerBuilder withSelectedColumnMaps(String selectedColumnMaps) {
        this.selectedColumnMaps = selectedColumnMaps;
        return this;
    }


    public ComparerBuilder withMatchingResult(ComparerListener comparerListener) {
        this.comparerListener = comparerListener;
        return this;
    }


    public DataComparer buildWith(Class<? extends DataComparer> dataComparerClazz) {

        DataComparer listComparator = null;
        try {
            Constructor constructor =
                    dataComparerClazz.getConstructor(new Class[]{RowListWrapper.class, RowListWrapper.class});
            listComparator = (DataComparer) constructor.newInstance(this.sourceTable, this.targetTable);
            if (this.columnMatcher != null) {
                listComparator.setColumnMatcher(this.columnMatcher);
            } else {

                if (this.selectedColumnMaps == null) {
                    listComparator.setColumnMatcher(getOrderedColumnMatcher(sourceTable instanceof ResultSetRowListWrapper,
                            targetTable instanceof ResultSetRowListWrapper));
                } else {
                    listComparator.setColumnMatcher(new SelectedColumnMatcher(this.selectedColumnMaps));
                }


            }
            listComparator.setComparerListener(this.comparerListener);

        } catch (InstantiationException e) {
            logger.error(e);
            MintLeafException.throwException(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
            MintLeafException.throwException(e);
        } catch (NoSuchMethodException e) {
            logger.error(e);
            MintLeafException.throwException(e);
        } catch (InvocationTargetException e) {
            logger.error(e);
            MintLeafException.throwException(e);
        }


        return listComparator;
    }

    private OrderedColumnMatcher getOrderedColumnMatcher(final boolean dbSourceColumnState, final boolean dbTargetColumnState) {
        return new OrderedColumnMatcher() {
            @Override
            protected ColumnState createSourceColumnStateInstance() {
                if (dbSourceColumnState)
                    return new DbColumnState();
                return super.createSourceColumnStateInstance();
            }

            @Override
            protected ColumnState createTargetColumnStateInstance() {
                if (dbTargetColumnState)
                    return new DbColumnState();
                return super.createTargetColumnStateInstance();
            }
        };
    }

    public DataComparer build() {
        return buildWith(OrderedListComparator.class);
    }
}
