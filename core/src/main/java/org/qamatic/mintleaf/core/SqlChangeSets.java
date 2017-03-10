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

import org.qamatic.mintleaf.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by qamatic on 7/18/16.
 */
public class SqlChangeSets extends BaseSqlScript {

    private final static MintLeafLogger logger = MintLeafLogger.getLogger(SqlScriptFile.class);
    private ChangeSetReader changeSetReader;
    private String[] changeSetsToApply;


    public SqlChangeSets(DriverSource driverSource, ChangeSetReader changeSetReader, String[] changeSetsToApply) {
        super(driverSource);
        this.changeSetReader = changeSetReader;
        this.changeSetsToApply = changeSetsToApply;
    }


    @Override
    public void apply() throws SQLException, IOException {
        for (String changeSetName : changeSetsToApply) {
            if (changeSetReader.getChangeSets().containsKey(changeSetName.trim())) {
                final ChangeSet section = changeSetReader.getChangeSet(changeSetName.trim());
                SqlScript script = new BaseSqlScript(driverSource) {
                    @Override
                    protected SqlReader getSourceReader() {
                        SqlReader reader = new SqlStringReader(section.getChangeSetSource());
                        reader.setDelimiter(section.getDelimiter());
                        return reader;
                    }
                };
                script.apply();
            } else {
                logger.error("apply a changeset does not exist: " + changeSetName);
            }
        }
    }

    @Override
    protected SqlReader getSourceReader() {
        return this.changeSetReader;
    }
}
