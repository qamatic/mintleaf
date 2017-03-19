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

public class SqlChangeSetFileReader extends BaseSqlReader implements ChangeSetReader {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(SqlChangeSetFileReader.class);
    private final HashMap<String, ChangeSet> changeSets = new HashMap<>();
    protected InputStream inputStream;
    private String resource;
    private boolean bRead;

    public SqlChangeSetFileReader(InputStream stream) {
        this.inputStream = stream;

    }

    public SqlChangeSetFileReader(String resource) {
        this.resource = resource;

    }

    @Override
    public ChangeSet getChangeSet(String changeSetId) {
        return getChangeSets().get(changeSetId);
    }

    @Override
    public HashMap<String, ChangeSet> getChangeSets() {

        try {
            read();
        } catch (Exception e) {
            new MintLeafException(e);
        }

        return changeSets;
    }

    private InputStream getInputStream() throws IOException {
        if (this.inputStream == null) {
            logger.info(String.format("reading resource : %s", this.resource));
            this.inputStream = BaseSqlReader.getInputStreamFromFile(this.resource);
        }
        return this.inputStream;
    }

    @Override
    public void read() throws MintLeafException {

        if (bRead) {
            return;
        }
        bRead = true;
        StringBuilder childContents = new StringBuilder();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(getInputStream(), "UTF-8"));
            ChangeSet currentChangeSet = null;
            try {
                String line = null; // not declared within while loop

                while ((line = input.readLine()) != null) {
                    line = line.trim();
                    if (line.length() == 0) {
                        continue;
                    }

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
                        continue;
                    }
                    childContents.append(line);
                    childContents.append("\n");
                }
            } finally {
                input.close();
            }

            String sql = childContents.toString().trim();
            if ((currentChangeSet != null) && (currentChangeSet.getId() != null) && (currentChangeSet.getId().length() != 0) && (sql.length() != 0)) {
                if (changeSetListener != null) {
                    changeSetListener.onChangeSetRead(new StringBuilder(sql), null);
                }
                currentChangeSet.setChangeSetSource(sql);
                getChangeSets().put(currentChangeSet.getId(), currentChangeSet);
            }
        } catch (IOException e) {
            logger.error("error reading changeset ", e);
            throw new MintLeafException(e);
        }
    }

    @Override
    public String getDelimiter() {
        throw new UnsupportedOperationException("changeset reader does not support delimiters but declared as part changeset");
    }

    @Override
    public void setDelimiter(String delimStr) {
        throw new UnsupportedOperationException("changeset reader does not support delimiters but declared as part changeset");
    }

}
