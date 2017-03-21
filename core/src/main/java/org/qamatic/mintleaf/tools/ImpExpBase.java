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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qamatic on 3/6/16.
 */
public abstract class ImpExpBase {
    private static final MintLeafLogger logger = MintLeafLogger.getLogger(ImpExpBase.class);

    protected abstract ConnectionContext getConnectionContext();


    //this is meant for testing purpose of loading data but for production side you should consider using param binds..
    protected void importDataFrom(final ImportFlavour dataImport, final String sqlTemplate) throws MintLeafException {
        final Pattern columnPattern = Pattern.compile("\\$(\\w+)\\$", Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        final Matcher columns = columnPattern.matcher(sqlTemplate);
        logger.info("importing using template:" + sqlTemplate);
        final FluentJdbc fluentJdbc =  this.getConnectionContext().queryBuilder();
        dataImport.doImport((rowNum, row) -> {


            StringBuffer buffer = new StringBuffer(sqlTemplate);
            columns.reset();
            while (columns.find()) {
                int idx = buffer.indexOf("$" + columns.group(1));
                buffer.replace(idx, idx + columns.group(1).length() + 2, row.asString(columns.group(1)));
            }
            fluentJdbc.addBatch(buffer.toString());


            return null;
        });
        dataImport.close();
        fluentJdbc.executeBatch();
        fluentJdbc.close();
    }

    protected void exportDataTo(final ExportFlavour dataExport, String sql, ParameterBinding parameterBinding) throws MintLeafException {
        FluentJdbc fluentJdbc = this.getConnectionContext().queryBuilder().withSql(sql).withParamValues(parameterBinding);
        try {
            dataExport.export(fluentJdbc.getResultSet());
        } finally {
            fluentJdbc.close();
        }
    }
}
