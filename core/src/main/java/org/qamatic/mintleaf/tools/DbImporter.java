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

package org.qamatic.mintleaf.tools;

import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.core.FluentJdbc;

import static org.qamatic.mintleaf.Mintleaf.selectQuery;

/**
 * Created by qamatic on 3/6/16.
 */
public class DbImporter extends ImpExpBase implements DataAction {
    private static final MintLeafLogger logger = MintLeafLogger.getLogger(DbImporter.class);

    private ConnectionContext targetDb;
    private String targetSqlTemplate;
    private ConnectionContext sourceDb;
    private String sourceSql;
    private ParameterBinding sourceSqlParamValueBindings;

    public DbImporter(ConnectionContext sourceDb, String sourceSql,
                      ConnectionContext targetDb,
                      String targetSqlTemplate) {
        this.sourceDb = sourceDb;
        this.sourceSql = sourceSql;
        this.targetDb = targetDb;
        this.targetSqlTemplate = targetSqlTemplate;
    }

    @Override
    public void execute() throws MintLeafException {
        try (SqlResultSet sourceSqlResultSet = selectQuery(this.getConnectionContext()).withSql(sourceSql).withParamValues(sourceSqlParamValueBindings).buildSelect()) {
            importDataFrom(new DbImportFlavour(sourceSqlResultSet), this.targetSqlTemplate);
        }
    }

    @Override
    protected ConnectionContext getConnectionContext() {
        return this.sourceDb;
    }

    public void setSourceSqlParamValueBindings(ParameterBinding sourceSqlParamValueBindings) {
        this.sourceSqlParamValueBindings = sourceSqlParamValueBindings;
    }
}
