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

package org.qamatic.mintleaf.tools;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.qamatic.mintleaf.DataRowListener;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.ComparableRow;

import java.io.IOException;
import java.io.Reader;
import java.sql.ResultSetMetaData;

/**
 * Created by qamatic on 2/18/6/16.
 */
public class CsvImportFlavour implements ImportFlavour {

    private Reader afileReader;

    public CsvImportFlavour(Reader afileReader) {
        this.afileReader = afileReader;
    }

    protected CSVParser getCSVParser() throws IOException {
        return new CSVParser(afileReader, CSVFormat.EXCEL.withHeader().withIgnoreEmptyLines());
    }

    public void doImport(DataRowListener listener) throws MintLeafException {

        final CSVParser parser;
        try {
            parser = getCSVParser();

            final CsvSourceRowWrapper csvRowWrapper = new CsvSourceRowWrapper();
            int i = 0;
            for (CSVRecord record : parser) {
                csvRowWrapper.setRecord(record);
                listener.eachRow(i++, csvRowWrapper);
            }

        } catch (IOException e) {
            throw new MintLeafException(e);
        }
    }


    private class CsvSourceRowWrapper implements ComparableRow {
        private CSVRecord record;


        public CSVRecord getRecord() {
            return record;
        }


        public void setRecord(CSVRecord record) {
            this.record = record;
        }

        @Override
        public String getValue(int columnIndex) {
            return record.get(columnIndex);
        }

        @Override
        public String getValue(String columnName) {
            return record.get(columnName);
        }

        @Override
        public int count() {
            return record.size();
        }

        @Override
        public ResultSetMetaData getMetaData() throws MintLeafException {
            return null;
        }
    }

}
