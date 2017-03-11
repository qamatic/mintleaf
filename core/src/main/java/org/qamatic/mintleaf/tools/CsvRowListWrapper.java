package org.qamatic.mintleaf.tools;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.qamatic.mintleaf.ComparableRow;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.RowListWrapper;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * Created by Senthil on 3/10/2017.
 */
public class CsvRowListWrapper implements RowListWrapper {
    private Reader afileReader;
    protected CSVParser parser;
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
    public void resetAll() throws MintLeafException {
        iterator=null;
    }

    private Iterator<CSVRecord> getIterator() throws IOException {
        if (iterator == null){
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
        return csvRowWrapper;
    }
}
