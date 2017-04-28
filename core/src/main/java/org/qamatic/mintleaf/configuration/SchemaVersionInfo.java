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

package org.qamatic.mintleaf.configuration;

import javax.xml.bind.annotation.XmlType;
import java.util.regex.Pattern;

/**
 * Created by qamatic on 3/6/16.
 */

@XmlType(propOrder = {"id", "changeSets", "scriptLocation"})
public class SchemaVersionInfo {


    private String id;

    private String[] changeSets;
    private String[] paths;


    public SchemaVersionInfo() {

    }

    public SchemaVersionInfo(String id, String changeSets, String scriptLocation) {
        this.id = id;
        this.changeSets = splitItems(changeSets);
        this.paths = splitItems(scriptLocation);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getScriptLocation() {
        return String.join(", ", this.paths);
    }

    public void setScriptLocation(String scriptLocation) {
        this.paths = splitItems(scriptLocation);
    }

    public String getChangeSets() {

        return String.join(", ", this.changeSets);
    }

    public void setChangeSets(String changeSets) {
        this.changeSets = splitItems(changeSets);
    }

    public String[] getChangeSetsAsList() {
        return this.changeSets;
    }

    public String[] getScriptLocationAsList() {
        return this.paths;
    }

    private String[] splitItems(String changeSets) {
        String[] splits = changeSets.split(Pattern.quote(","));
        for (int i = 0; i < splits.length; i++) {
            splits[i] = splits[i].trim();
        }
        return splits;
    }
}
