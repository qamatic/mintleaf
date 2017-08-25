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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.readers.CsvRowWrapper;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by QAmatic on 3/10/2017.
 */
public class CsvRowListWrapper<T extends Row> implements RowListWrapper<T> {
    protected CSVParser parser;
    private Reader afileReader;
    private ColumnMetaDataCollection metaDataCollection = new ColumnMetaDataCollection("CSV");


    public CsvRowListWrapper(Reader afileReader) {
        this.afileReader = afileReader;
    }

    protected CSVParser getCSVParser() throws MintleafException {
        if (parser == null) {
            try {
                parser = new CSVParser(afileReader, CSVFormat.EXCEL.withHeader().withIgnoreEmptyLines());
            } catch (IOException e) {
                throw new MintleafException(e);
            }
        }
        return parser;
    }

    @Override
    public T getRow(int index) throws MintleafException {
        try {
            T t = (T) new CsvRowWrapper<T>(getCSVParser().getRecords().get(index));
            t.setMetaData(getMetaData());
            return t;
        } catch (IOException e) {
            throw new MintleafException(e);
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        MintleafException.throwException("unsupported");
    }

    @Override
    public int size() {
        try {
            return getCSVParser().getRecords().size();
        } catch (IOException e) {
            MintleafException.throwException(e);
        } catch (MintleafException e) {
            MintleafException.throwException(e);
        }
        return 0;
    }

    @Override
    public ColumnMetaDataCollection getMetaData() throws MintleafException {
        return this.metaDataCollection;
    }

    private Iterator<CSVRecord> getIterator() throws MintleafException {
        if (metaDataCollection.size() == 0) {
            for (String columnName : getCSVParser().getHeaderMap().keySet()) {
                metaDataCollection.add(new Column(columnName));
            }
        }
        return getCSVParser().iterator();
    }


    @Override
    public Iterator<T> iterator() {
        try {
            return new CsvRowIterator<T>(this.getIterator(), getMetaData());
        } catch (MintleafException e) {
            MintleafException.throwException(e);
        }
        return null;
    }

    private static final class CsvRowIterator<T extends Row> implements Iterator<T> {
        private final Iterator<CSVRecord> iterator;
        private final ColumnMetaDataCollection metaDataCollection;
        private CSVRecord current;

        public CsvRowIterator(Iterator<CSVRecord> iterator, ColumnMetaDataCollection metaDataCollection) {
            this.iterator = iterator;
            this.metaDataCollection = metaDataCollection;
        }

        private CSVRecord getNextRecord() {
            CSVRecord record;
            try {
                record = this.iterator.next();
            } catch (NoSuchElementException var2) {
                record = null;
            }
            return record;
        }

        @Override
        public boolean hasNext() {
            if (this.current == null) {
                this.current = this.getNextRecord();
            }

            return this.current != null;
        }

        @Override
        public T next() {
            CSVRecord next = this.current;
            this.current = null;
            if (next == null) {
                next = this.getNextRecord();
                if (next == null) {
                    throw new NoSuchElementException("No more CSV records available");
                }
            }
            T t = (T) new CsvRowWrapper<T>(next);
            t.setMetaData(this.metaDataCollection);
            return t;
        }
    }
}
