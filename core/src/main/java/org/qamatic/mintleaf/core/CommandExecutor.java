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

package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.DriverSource;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.MintLeafLogger;
import org.qamatic.mintleaf.SqlReaderListener;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

public class CommandExecutor implements SqlReaderListener {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(CommandExecutor.class);
    protected final DriverSource driverSource;
    private final Map<String, String> templateValues = new Hashtable<String, String>();
    private SqlReaderListener childListner;

    public CommandExecutor(DriverSource driverSource) {
        this.driverSource = driverSource;
    }

    public static void replaceStr(StringBuilder sqlBuilder, String findStr, String replaceStr) {
        int indexOfTarget = -1;
        while ((indexOfTarget = sqlBuilder.indexOf(findStr)) > 0) {
            sqlBuilder.replace(indexOfTarget, indexOfTarget + findStr.length(), replaceStr);
        }
    }

    @Override
    public SqlReaderListener getChildReaderListener() {
        return childListner;
    }

    @Override
    public void setChildReaderListener(SqlReaderListener childListner) {
        this.childListner = childListner;
    }

    @Override
    public Map<String, String> getTemplateValues() {
        return templateValues;
    }

    private void findAndReplace(StringBuilder sqlText) {
        for (String key : getTemplateValues().keySet()) {
            onReplaceTemplateValue(sqlText, key, getTemplateValues().get(key).toString());
        }
    }

    protected void onReplaceTemplateValue(StringBuilder sqlBuilder, String findStr, String replaceStr) {
        replaceStr(sqlBuilder, findStr, replaceStr);
    }

    protected void preProcess(StringBuilder sqlText) {
        findAndReplace(sqlText);
    }

    @Override
    public void onReadChild(StringBuilder sql, Object context) throws MintLeafException {
        preProcess(sql);
        if (getChildReaderListener() != null) {
            getChildReaderListener().onReadChild(sql, context);
            return;
        }
        execute(sql);
    }

    protected void execute(StringBuilder sql) throws MintLeafException {

        FluentJdbc.executeSql(driverSource, sql.toString(), null);


    }

}
