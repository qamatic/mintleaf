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
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.tools.FileFinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiChangeSetFileReader<T> extends BaseFileReader implements ChangeSetReader {

    private static final MintleafLogger logger = MintleafLogger.getLogger(MultiChangeSetFileReader.class);
    private final HashMap<String, ChangeSet> changeSets = new HashMap<>();
    private Map<String, String> userVariableMapping;
    private String[] paths;

    public MultiChangeSetFileReader(String[] paths) {
        this.paths = paths;
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
    public String getDelimiter() {
        return null;
    }

    @Override
    public void setDelimiter(String delimStr) {

    }

    @Override
    public T read() throws MintleafException {
        for (String path : this.paths) {
            FileFinder fileFinder = new FileFinder(path);
            List<String> files = fileFinder.list();
            for (String file : files) {
                ChangeSetReader changeSetReader = new SqlChangeSetFileReader(file) {
                    @Override
                    public HashMap<String, ChangeSet> getChangeSets() {
                        return changeSets;
                    }
                };
                changeSetReader.setReadListener(this.getReadListener());
                changeSetReader.setUserVariableMapping(getUserVariableMapping());
                changeSetReader.read();
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getUserVariableMapping() {
        if (userVariableMapping == null) {
            userVariableMapping = new HashMap<>();
        }
        return userVariableMapping;
    }

    @Override
    public void setUserVariableMapping(Map userVariableMapping) {
        this.userVariableMapping = userVariableMapping;
    }
}
