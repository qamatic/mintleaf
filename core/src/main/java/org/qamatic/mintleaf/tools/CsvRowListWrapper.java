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
