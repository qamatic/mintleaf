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

import org.qamatic.mintleaf.ChangeSet;
import org.qamatic.mintleaf.ChangeSetReader;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.MintLeafLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class SqlChangeSetFileReader extends SqlStreamReader implements ChangeSetReader {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(SqlChangeSetFileReader.class);
    private final HashMap<String, ChangeSet> changeSets = new HashMap<>();
    private ChangeSet currentChangeSet;

    public SqlChangeSetFileReader(InputStream stream) {
        super(stream);
    }

    public SqlChangeSetFileReader(String resource) {
        super(resource);
    }

    @Override
    public ChangeSet getChangeSet(String changeSetId) {
        return getChangeSets().get(changeSetId);
    }

    @Override
    public HashMap<String, ChangeSet> getChangeSets() {
        return changeSets;
    }

    @Override
    public void read() throws MintLeafException {
        this.changeSets.clear();

        super.read();

        String sql = childContents.toString().trim();
        if ((currentChangeSet != null) && (currentChangeSet.getId() != null) && (currentChangeSet.getId().length() != 0) && (sql.length() != 0)) {
            if (changeSetListener != null) {
                changeSetListener.onChangeSetRead(new StringBuilder(sql), null);
            }
            currentChangeSet.setChangeSetSource(sql);
            getChangeSets().put(currentChangeSet.getId(), currentChangeSet);
        }
    }

    @Override
    protected SqlLineProcessor getLineProcessor() {
        return line -> {
            if ((line.trim().contains("<ChangeSet")) && ChangeSet.xmlToChangeSet(line) != null) {
                if (currentChangeSet == null) {
                    currentChangeSet = ChangeSet.xmlToChangeSet(line);
                }
                String sql = childContents.toString().trim();
                if (sql.length() != 0) {
                    if (changeSetListener != null) {
                        changeSetListener.onChangeSetRead(new StringBuilder(sql), currentChangeSet);
                    }
                    currentChangeSet.setChangeSetSource(sql);
                    getChangeSets().put(currentChangeSet.getId(), currentChangeSet);
                    currentChangeSet = ChangeSet.xmlToChangeSet(line);
                }
                childContents.setLength(0);
                return false;
            }
            childContents.append(line);
            childContents.append("\n");
            return true;
        };
    }

    @Override
    public String getDelimiter() {
        throw new UnsupportedOperationException("Not applicable");
    }

    @Override
    public void setDelimiter(String delimStr) {
        throw new UnsupportedOperationException("Not applicable");
    }

}
