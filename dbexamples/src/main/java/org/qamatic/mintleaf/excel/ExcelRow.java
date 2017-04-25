package org.qamatic.mintleaf.excel;

import org.qamatic.mintleaf.ColumnMetaDataCollection;
import org.qamatic.mintleaf.MetaDataCollection;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.Row;

/**
 * Created by QAmatic Team on 4/25/17.
 */
public class ExcelRow<T> implements Row {

    private MetaDataCollection metaDataCollection;
    private org.apache.poi.ss.usermodel.Row poiRow;

    public ExcelRow(org.apache.poi.ss.usermodel.Row poiRow){
        this.poiRow = poiRow;
    }

    @Override
    public Object getValue(int columnIndex) throws MintleafException {
        return this.poiRow.getCell(columnIndex).getStringCellValue();
    }

    @Override
    public MetaDataCollection getMetaData() throws MintleafException {
        return this.metaDataCollection;
    }

    @Override
    public void setMetaData(MetaDataCollection metaDataCollection) {
        this.metaDataCollection = metaDataCollection;

    }
}
