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

import org.qamatic.mintleaf.Database;
import org.qamatic.mintleaf.Executable;
import org.qamatic.mintleaf.Mintleaf;
import org.qamatic.mintleaf.ParameterBinding;
import org.qamatic.mintleaf.tools.DbImporter;

/**
 * Created by QAmatic Team on 3/25/17.
 */
public final class DbToDbBuilder {
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
