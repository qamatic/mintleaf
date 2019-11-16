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

package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.readers.SqlStringReader;

/**
 * Created by qamatic on 7/18/16.
 */
public class SqlChangeSets extends BaseSqlScript {

    private final static MintleafLogger logger = MintleafLogger.getLogger(SqlScriptFile.class);
    private ChangeSetReader changeSetReader;
    private String[] changeSetsToApply;


    public SqlChangeSets(ConnectionContext connectionContext, ChangeSetReader changeSetReader, String[] changeSetsToApply) {
        super(connectionContext);
        this.changeSetReader = changeSetReader;
        this.changeSetsToApply = changeSetsToApply;
    }

    @Override
    public void apply() throws MintleafException {
        this.changeSetReader.read();
        for (String changeSetName : changeSetsToApply) {
            if (changeSetReader.getChangeSets().containsKey(changeSetName.trim())) {
                final ChangeSet section = changeSetReader.getChangeSet(changeSetName.trim());
                SqlScript script = new BaseSqlScript(connectionContext) {
                    @Override
                    public MintleafReader getReader() {
                        SqlStringReader reader = new SqlStringReader(section.getChangeSetSource());
                        reader.setDelimiter(section.getDelimiter());
                        return reader;
                    }

                    @Override
                    public void close() throws MintleafException {

                    }

                };
                script.apply();
            } else {
                logger.error("apply a changeset does not exist: " + changeSetName);
            }
        }
        this.close();
    }

    @Override
    public void close() {
        try {
            connectionContext.close();
        } catch (Exception e) {
            MintleafException.throwException(e);
        }
    }

    @Override
    public MintleafReader getReader() {
        return this.changeSetReader;
    }
}
