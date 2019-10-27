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

import org.qamatic.mintleaf.core.BasicDatabase;
import org.qamatic.mintleaf.core.FluentJdbc;
import org.qamatic.mintleaf.core.JdbcDriverSource;
import org.qamatic.mintleaf.core.tables.InMemoryTable;
import org.qamatic.mintleaf.core.tables.SqlQueryTable;
import org.qamatic.mintleaf.data.*;
import org.qamatic.mintleaf.tools.BinaryFileImporter;
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

    private static final MintleafLogger logger = MintleafLogger.getLogger(Mintleaf.class);

    public static FluentJdbc.Builder queryBuilder(ConnectionContext connectionContext) {
        return new FluentJdbc.Builder(connectionContext);
    }

    public static Database database(String url, String username, String password) {
        return new DatabaseBuilder().withDriverSource(JdbcDriverSource.class).withUrl(url).withUsername(username).withPassword(password).build();
    }

    /* Builder classes
     * */
    public static final class DbToDbDataTransferBuilder {
        private String sourceSql;
        private ParameterBinding sqlaramValueBindings;
        private Database sourceDb;

        private Database targetDb;
        private String targetSqlTemplate;

        public DbToDbDataTransferBuilder withSqlaramValueBindings(ParameterBinding sqlaramValueBindings) {
            this.sqlaramValueBindings = sqlaramValueBindings;
            return this;
        }

        public DbToDbDataTransferBuilder withSourceDb(Database sourceDbDriverSource) {
            this.sourceDb = sourceDbDriverSource;
            return this;
        }

        public DbToDbDataTransferBuilder withSourceSql(String sourceSql) {
            this.sourceSql = sourceSql;
            return this;
        }

        public DbToDbDataTransferBuilder withTargetDb(Database targetDbDriverSource) {
            this.targetDb = targetDbDriverSource;
            return this;
        }

        public DbToDbDataTransferBuilder withTargetSqlTemplate(String targetSqlTemplate) {
            this.targetSqlTemplate = targetSqlTemplate;
            return this;
        }

        public Executable<Boolean> build() {
            DbImporter dbImporter = new DbImporter(
                    sourceDb.getNewConnection(),
                    sourceSql,
                    targetDb.getNewConnection(),
                    targetSqlTemplate);

            dbImporter.setSourceSqlParamValueBindings(sqlaramValueBindings);
            return dbImporter;
        }
    }

    public static final class DbToCsvDataTransferBuilder {
        private String sourceSql;
        private ParameterBinding sqlaramValueBindings;
        private Database sourceDb;
        private String targetCsvFile;

        public DbToCsvDataTransferBuilder withSqlaramValueBindings(ParameterBinding sqlaramValueBindings) {
            this.sqlaramValueBindings = sqlaramValueBindings;
            return this;
        }

        public DbToCsvDataTransferBuilder withSourceDb(Database sourceDb) {
            this.sourceDb = sourceDb;
            return this;
        }

        public DbToCsvDataTransferBuilder withSourceSql(String sourceSql) {
            this.sourceSql = sourceSql;
            return this;
        }

        public DbToCsvDataTransferBuilder withTargetCsvFile(String targetCsvFile) {
            this.targetCsvFile = targetCsvFile;
            return this;
        }


        public Executable<Boolean> build() {
            CsvExporter csvExporter = new CsvExporter(
                    sourceDb.getNewConnection(),
                    sourceSql,
                    targetCsvFile
            );
            csvExporter.setSqlaramValueBindings(sqlaramValueBindings);
            return csvExporter;
        }
    }

    public static final class CsvToDbDataTransferBuilder {

        private Database targetDb;
        private String targetSqlTemplate;
        private String sourceCsvFile;

        public CsvToDbDataTransferBuilder withTargetDb(Database targetDb) {
            this.targetDb = targetDb;
            return this;
        }


        public CsvToDbDataTransferBuilder withTargetSqlTemplate(String targetSqlTemplate) {
            this.targetSqlTemplate = targetSqlTemplate;
            return this;
        }

        public CsvToDbDataTransferBuilder withSourceCsvFile(String sourceCsvFile) {
            this.sourceCsvFile = sourceCsvFile;
            return this;
        }

        public Executable<Boolean> build() {
            CsvImporter csvImporter = new CsvImporter(
                    sourceCsvFile,
                    targetDb.getNewConnection(),
                    targetSqlTemplate);

            return csvImporter;
        }
    }

    public static final class AnyDataToDbDataTransferBuilder {

        private Database targetDb;
        private String targetSqlTemplate;
        private MintleafReader importReader;


        public AnyDataToDbDataTransferBuilder withTargetDb(Database targetDb) {
            this.targetDb = targetDb;
            return this;
        }


        public AnyDataToDbDataTransferBuilder withTargetSqlTemplate(String targetSqlTemplate) {
            this.targetSqlTemplate = targetSqlTemplate;
            return this;
        }

        public AnyDataToDbDataTransferBuilder withImportFlavour(MintleafReader importReader) {
            this.importReader = importReader;
            return this;
        }

        public Executable<Boolean> build() {
            BinaryFileImporter binaryFileImporter = new BinaryFileImporter(
                    this.importReader,
                    targetDb.getNewConnection(),
                    targetSqlTemplate) {
            };

            return binaryFileImporter;
        }
    }

    public static final class AnyDataToListTransferBuilder<T extends Row> {

        private MintleafReader importReader;
        private ReadListener readListener;

        public AnyDataToListTransferBuilder withSource(MintleafReader importReader) {
            this.importReader = importReader;
            return this;
        }

        public AnyDataToListTransferBuilder withMatchingCriteria(ReadListener rowDelegate) {
            this.readListener = rowDelegate;
            return this;
        }

        public Executable<Table<T>> build() throws MintleafException {
            return new Executable<Table<T>>() {
                @Override
                public Table<T> execute() throws MintleafException {
                    final Table<T> list = new InMemoryTable<T>();

                    importReader.setReadListener(new ReadListener() {
                        @Override
                        public void eachRow(int rowNum, Row row) throws MintleafException {
                            list.add((T) row);
                        }
                    });
                    importReader.read();

                    return list;
                }
            };
        }
    }

    public static final class DataComparerBuilder {
        private static final MintleafLogger logger = MintleafLogger.getLogger(DataComparerBuilder.class);
        private Table sourceTable;
        private Table targetTable;
        private ComparerListener comparerListener;//= new ConsoleComparerListener();
        private ColumnMatcher columnMatcher;
        private String selectedColumnMaps;

        public DataComparerBuilder withSourceTable(List<? extends Row> sourceTable, MetaDataCollection metaDataCollection) {
            this.sourceTable = new InMemoryTable(sourceTable, metaDataCollection);
            return this;
        }

        public DataComparerBuilder withSourceTable(Table sourceTable) {
            this.sourceTable = sourceTable;
            return this;
        }

        public DataComparerBuilder withTargetTable(List<? extends Row> targetTable, MetaDataCollection metaDataCollection) {
            this.targetTable = new InMemoryTable(targetTable, metaDataCollection);
            return this;
        }

        public DataComparerBuilder withTargetTable(Table targetTable) {
            this.targetTable = targetTable;
            return this;
        }

        public DataComparerBuilder withColumnMatchingLogic(ColumnMatcher columnMatcher) {
            this.columnMatcher = columnMatcher;
            return this;
        }


        public DataComparerBuilder withSelectedColumnMaps(String selectedColumnMaps) {
            this.selectedColumnMaps = selectedColumnMaps;
            return this;
        }


        public DataComparerBuilder withMatchingResult(ComparerListener comparerListener) {
            this.comparerListener = comparerListener;
            return this;
        }


        public DataComparer buildWith(Class<? extends DataComparer> dataComparerClazz) {

            DataComparer listComparator = null;
            try {
                Constructor constructor =
                        dataComparerClazz.getConstructor(new Class[]{Table.class, Table.class});
                listComparator = (DataComparer) constructor.newInstance(this.sourceTable, this.targetTable);
                if (this.columnMatcher != null) {
                    listComparator.setColumnMatcher(this.columnMatcher);
                } else {

                    if (this.selectedColumnMaps == null) {
                        listComparator.setColumnMatcher(getOrderedColumnMatcher(sourceTable instanceof SqlQueryTable,
                                targetTable instanceof SqlQueryTable));
                    } else {
                        listComparator.setColumnMatcher(new SelectedColumnMatcher(this.selectedColumnMaps));
                    }


                }
                listComparator.setComparerListener(this.comparerListener);

            } catch (InstantiationException e) {
                logger.error(e);
                MintleafException.throwException(e);
            } catch (IllegalAccessException e) {
                logger.error(e);
                MintleafException.throwException(e);
            } catch (NoSuchMethodException e) {
                logger.error(e);
                MintleafException.throwException(e);
            } catch (InvocationTargetException e) {
                logger.error(e);
                MintleafException.throwException(e);
            }


            return listComparator;
        }

        private OrderedColumnMatcher getOrderedColumnMatcher(final boolean dbSourceColumnState, final boolean dbTargetColumnState) {
            return new OrderedColumnMatcher() {
                @Override
                protected CompareColumnState createSourceColumnStateInstance() {
                    if (dbSourceColumnState) {
                        setColumnCountOffset(1);
                    }
                    return super.createSourceColumnStateInstance();
                }

                @Override
                protected CompareColumnState createTargetColumnStateInstance() {
                    if (dbTargetColumnState) {
                        setColumnCountOffset(1);
                    }
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

}
