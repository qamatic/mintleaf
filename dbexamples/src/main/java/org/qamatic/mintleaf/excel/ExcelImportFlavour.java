package org.qamatic.mintleaf.excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.tools.ImportFlavour;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by senips on 4/25/17.
 */
public class ExcelImportFlavour implements ImportFlavour {

    private InputStream inputStream;
    private int activeWorkSheet;
    private int headerRowIndex;

    public ExcelImportFlavour(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setHeaderRow(int headerRowIndex) {
        this.headerRowIndex = headerRowIndex;
    }

    private MetaDataCollection getMetaData(Row row){
        ColumnMetaDataCollection metaDataCollection = new ColumnMetaDataCollection();
        for(Cell cell : row){
            metaDataCollection.add(new Column(cell.getStringCellValue(), cell.getCellType()));
        }
        return metaDataCollection;
    }

    @Override
    public void doImport(DataRowListener listener) throws MintleafException {
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(this.inputStream);
            HSSFSheet sheet = workbook.getSheetAt(getActiveWorkSheet());
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            boolean headerRowFound = this.headerRowIndex == -1;
            MetaDataCollection metaDataCollection =null;
            while (rowIterator.hasNext()) {
                if ((!headerRowFound) && (i == this.headerRowIndex)) {
                    headerRowFound = true;
                    metaDataCollection = getMetaData(rowIterator.next());
                    continue;
                }
                if (!headerRowFound) {
                    continue;
                }
                ExcelRow row = new ExcelRow(rowIterator.next());
                row.setMetaData(metaDataCollection);
                listener.eachRow(i++, row);
                if (!listener.canContinue()) {
                    break;
                }

            }

        } catch (IOException e) {
            throw new MintleafException(e);
        }
    }

    public int getActiveWorkSheet() {
        return activeWorkSheet;
    }

    public void setActiveWorkSheet(int activeWorkSheet) {
        this.activeWorkSheet = activeWorkSheet;
    }
}
