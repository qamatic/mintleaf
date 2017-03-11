package org.qamatic.mintleaf.tools;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.qamatic.mintleaf.ComparableRow;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.RowListWrapper;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Senthil on 3/10/2017.
 */
public class CsvRowListWrapper implements RowListWrapper {
    private Reader afileReader;
    protected CSVParser parser;
    private CsvRowWrapper csvRowWrapper = new CsvRowWrapper();

    public CsvRowListWrapper(Reader afileReader) {
        this.afileReader = afileReader;
    }

    protected CSVParser getCSVParser() throws IOException {
        if (parser == null){
            parser = new CSVParser(afileReader, CSVFormat.EXCEL.withHeader().withIgnoreEmptyLines());
        }
        return parser;
    }

    @Override
    public void resetAll() throws MintLeafException {

    }

    @Override
    public boolean moveNext() throws MintLeafException {
        try {
            if (getCSVParser().iterator().hasNext()) {
                csvRowWrapper.setRecord(getCSVParser().iterator().next());
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
