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

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.ReadListener;
import org.qamatic.mintleaf.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qamatic on 3/6/16.
 */
public class SqlTemplateBasedReadListener<T> implements ReadListener<T> {
    private static final MintleafLogger logger = MintleafLogger.getLogger(SqlTemplateBasedReadListener.class);
    final Pattern columnPattern = Pattern.compile("\\$(\\w+)\\$", Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    private Matcher columns;

    private List<String> batchSqls = new ArrayList<>();
    private String templateSql;

    public SqlTemplateBasedReadListener(String templateSql) {
        this.templateSql = templateSql;
        this.columns = columnPattern.matcher(this.templateSql);
    }

    public final List<String> getBatchSqls() {
        return batchSqls;
    }

    @Override
    public T eachRow(int rowNum, Row row) throws MintleafException {

        StringBuffer buffer = new StringBuffer(this.templateSql);
        columns.reset();
        while (columns.find()) {
            int idx = buffer.indexOf("$" + columns.group(1));
            buffer.replace(idx, idx + columns.group(1).length() + 2, row.asString(columns.group(1)));
        }
        batchSqls.add(buffer.toString());

        return null;
    }

}
