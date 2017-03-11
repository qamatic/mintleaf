package org.qamatic.mintleaf.tools;

import org.apache.commons.csv.CSVRecord;
import org.qamatic.mintleaf.Column;
import org.qamatic.mintleaf.ColumnMetaDataCollection;
import org.qamatic.mintleaf.ComparableRow;
import org.qamatic.mintleaf.MintLeafException;

import java.sql.ResultSetMetaData;

/**
 * Created by Senthil on 3/10/2017.
 */
public class CsvRowWrapper implements ComparableRow {
    private CSVRecord record;
    private ColumnMetaDataCollection metaDataCollection;

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
    public ColumnMetaDataCollection getMetaData() throws MintLeafException {
        if (metaDataCollection == null) {
            metaDataCollection = new ColumnMetaDataCollection("CSV");
        }
        return metaDataCollection;
    }
}
