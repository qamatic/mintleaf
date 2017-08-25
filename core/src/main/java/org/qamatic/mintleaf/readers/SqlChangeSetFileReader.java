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

package org.qamatic.mintleaf.readers;

import org.qamatic.mintleaf.ChangeSet;
import org.qamatic.mintleaf.ChangeSetReader;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.configuration.ArgPatternHandler;
import org.qamatic.mintleaf.core.Readerline;

import java.io.InputStream;
import java.util.HashMap;

public class SqlChangeSetFileReader<T> extends SqlStreamReader implements ChangeSetReader<T> {

    private static final MintleafLogger logger = MintleafLogger.getLogger(SqlChangeSetFileReader.class);
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
    public T read() throws MintleafException {

        super.read();

        String sql = content.toString().trim();
        if ((currentChangeSet != null) && (currentChangeSet.getId() != null) && (currentChangeSet.getId().length() != 0) && (sql.length() != 0)) {
            currentChangeSet.setChangeSetSource(sql);

            getChangeSets().put(currentChangeSet.getId(), currentChangeSet);
            if (getReadListener() != null) {
                getReadListener().eachRow(getChangeSets().size() - 1, currentChangeSet);
            }
        }
        return null;
    }

    @Override
    protected Readerline readLine() {
        return line -> {
            if ((line.trim().contains("<ChangeSet")) && ChangeSet.xmlToChangeSet(line) != null) {
                if (currentChangeSet == null) {
                    currentChangeSet = ChangeSet.xmlToChangeSet(line);
                }
                String sql = new ArgPatternHandler(content.toString().trim()).
                        withUserProperties(this.getUserVariableMapping()).
                        getText();
                if (sql.length() != 0) {
                    currentChangeSet.setChangeSetSource(sql);

                    getChangeSets().put(currentChangeSet.getId(), currentChangeSet);
                    if (getReadListener() != null) {
                        getReadListener().eachRow(getChangeSets().size() - 1, currentChangeSet);
                    }
                    currentChangeSet = ChangeSet.xmlToChangeSet(line);
                }
                content.setLength(0);
                return false;
            }
            content.append(line);
            content.append("\n");
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
