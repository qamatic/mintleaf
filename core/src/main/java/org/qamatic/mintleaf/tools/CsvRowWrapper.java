package org.qamatic.mintleaf.tools;

import org.apache.commons.csv.CSVRecord;
import org.qamatic.mintleaf.*;

import java.sql.ResultSetMetaData;

/**
 * Created by Senthil on 3/10/2017.
 */
public class CsvRowWrapper implements ComparableRow {
    private CSVRecord record;
    private MetaDataCollection metaDataCollection;

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
    public void setMetaData(MetaDataCollection metaDataCollection) {
        this.metaDataCollection = metaDataCollection;

    }

    @Override
    public MetaDataCollection getMetaData() throws MintLeafException {
        return this.metaDataCollection;
    }
}
