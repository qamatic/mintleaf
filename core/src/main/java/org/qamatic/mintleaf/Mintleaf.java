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

import org.qamatic.mintleaf.core.*;
import org.qamatic.mintleaf.data.*;
import org.qamatic.mintleaf.tools.CsvExporter;
import org.qamatic.mintleaf.tools.CsvImporter;
import org.qamatic.mintleaf.tools.DbImporter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by qamatic on 3/1/16.
 */
public final class Mintleaf {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(Mintleaf.class);


    public static final class ComparerBuilder {

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

    public static final class DatabaseBuilder {

        private Class<? extends DriverSource> driverSourceClazz = JdbcDriverSource.class;
        private String url;
        private String username;
        private String password;

        public DatabaseBuilder withDriverSource(Class<? extends DriverSource> driverSourceClazz) {
            this.driverSourceClazz = driverSourceClazz;
            return this;
        }

        public DatabaseBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public DatabaseBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public DatabaseBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Database build() {
            BasicDatabase databaseContext = new BasicDatabase(this.driverSourceClazz, this.url, this.username, this.password);
            return databaseContext;
        }

    }


    public static final class DbToCsvBuilder {
        private String sourceSql;
        private ParameterBinding sqlaramValueBindings;
        private Database sourceDb;

        private String targetCsvFile;

        public DbToCsvBuilder withSqlaramValueBindings(ParameterBinding sqlaramValueBindings) {
            this.sqlaramValueBindings = sqlaramValueBindings;
            return this;
        }

        public DbToCsvBuilder withSourceDb(Database sourceDb) {
            this.sourceDb = sourceDb;
            return this;
        }

        public DbToCsvBuilder withSourceSql(String sourceSql) {
            this.sourceSql = sourceSql;
            return this;
        }

        public DbToCsvBuilder withTargetCsvFile(String targetCsvFile) {
            this.targetCsvFile = targetCsvFile;
            return this;
        }

        public DataAction build() {
            CsvExporter csvExporter = new CsvExporter(
                    sourceDb.getNewConnection(),
                    sourceSql,
                    targetCsvFile
            );
            csvExporter.setSqlaramValueBindings(sqlaramValueBindings);
            return csvExporter;
        }
    }

    public static final class CsvToDbBuilder {

        private Database targetDb;
        private String targetSqlTemplate;
        private String sourceCsvFile;

        public CsvToDbBuilder withTargetDb(Database targetDb) {
            this.targetDb = targetDb;
            return this;
        }

        public CsvToDbBuilder withTargetSqlTemplate(String targetSqlTemplate) {
            this.targetSqlTemplate = targetSqlTemplate;
            return this;
        }

        public CsvToDbBuilder withSourceCsvFile(String sourceCsvFile) {
            this.sourceCsvFile = sourceCsvFile;
            return this;
        }

        public DataAction build() {
            CsvImporter csvImporter = new CsvImporter(
                    sourceCsvFile,
                    targetDb.getNewConnection(),
                    targetSqlTemplate);


            return csvImporter;
        }
    }

    public static final class DbToDbBuilder {
        private String sourceSql;
        private ParameterBinding sqlaramValueBindings;
        private Database sourceDb;

        private Database targetDb;
        private String targetSqlTemplate;

        public DbToDbBuilder withSqlaramValueBindings(ParameterBinding sqlaramValueBindings) {
            this.sqlaramValueBindings = sqlaramValueBindings;
            return this;
        }

        public DbToDbBuilder withSourceDb(Database sourceDbDriverSource) {
            this.sourceDb = sourceDbDriverSource;
            return this;
        }

        public DbToDbBuilder withSourceSql(String sourceSql) {
            this.sourceSql = sourceSql;
            return this;
        }

        public DbToDbBuilder withTargetDb(Database targetDbDriverSource) {
            this.targetDb = targetDbDriverSource;
            return this;
        }

        public DbToDbBuilder withTargetSqlTemplate(String targetSqlTemplate) {
            this.targetSqlTemplate = targetSqlTemplate;
            return this;
        }

        public DataAction build() {
            DbImporter dbImporter = new DbImporter(
                    sourceDb.getNewConnection(),
                    sourceSql,
                    targetDb.getNewConnection(),
                    targetSqlTemplate);

            dbImporter.setSourceSqlParamValueBindings(sqlaramValueBindings);
            return dbImporter;
        }
    }


    public static FluentJdbc.Builder selectQuery(ConnectionContext connectionContext) {
        return new FluentJdbc.Builder(connectionContext);
    }

    public static FluentJdbc.Builder executeQuery(ConnectionContext connectionContext) {
        return new FluentJdbc.Builder(connectionContext);
    }

    public static DbCallable<int[]> executeBatchSqls(ConnectionContext connectionContext, List<String> batchSqls) {
        return new ExecuteQuery(connectionContext, batchSqls);
    }

}
