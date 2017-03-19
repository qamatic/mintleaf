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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.qamatic.mintleaf.*;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * Created by Senthil on 3/10/2017.
 */
public class CsvRowListWrapper implements RowListWrapper {
    protected CSVParser parser;
    private Reader afileReader;
    private ColumnMetaDataCollection metaDataCollection = new ColumnMetaDataCollection("CSV");
    private CsvRowWrapper csvRowWrapper = new CsvRowWrapper();
    private Iterator<CSVRecord> iterator;

    public CsvRowListWrapper(Reader afileReader) {
        this.afileReader = afileReader;
    }

    protected CSVParser getCSVParser() throws IOException {
        if (parser == null) {
            parser = new CSVParser(afileReader, CSVFormat.EXCEL.withHeader().withIgnoreEmptyLines());
        }
        return parser;
    }

    @Override
    public ColumnMetaDataCollection getMetaData() throws MintLeafException {
        return this.metaDataCollection;
    }

    @Override
    public void resetAll() throws MintLeafException {
        iterator = null;
    }

    private Iterator<CSVRecord> getIterator() throws IOException, MintLeafException {
        if (iterator == null) {
            for (String columnName : getCSVParser().getHeaderMap().keySet()) {
                metaDataCollection.add(new Column(columnName));
            }
            iterator = getCSVParser().iterator();
        }
        return iterator;
    }

    @Override
    public boolean moveNext() throws MintLeafException {
        try {
            if (getIterator().hasNext()) {
                csvRowWrapper.setRecord(getIterator().next());
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new MintLeafException(e);
        }
    }

    @Override
    public ComparableRow row() throws MintLeafException {
        csvRowWrapper.setMetaData(this.metaDataCollection);
        return csvRowWrapper;
    }


}
