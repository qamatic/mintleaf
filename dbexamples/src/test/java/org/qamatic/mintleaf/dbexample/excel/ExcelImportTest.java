package org.qamatic.mintleaf.dbexample.excel;

import org.junit.Test;
import org.qamatic.mintleaf.Executable;
import org.qamatic.mintleaf.Mintleaf;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.RowListWrapper;
import org.qamatic.mintleaf.core.BaseSqlReader;
import org.qamatic.mintleaf.core.ObjectRowListWrapper;
import org.qamatic.mintleaf.excel.ExcelImportFlavour;
import org.qamatic.mintleaf.excel.ExcelRow;
import org.qamatic.mintleaf.tools.ImportFlavour;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by senips on 4/25/17.
 */
public class ExcelImportTest {


    @Test
    public void importFromExcelToObjectListTest() throws MintleafException {

        ImportFlavour importFlavour = new ExcelImportFlavour(BaseSqlReader.getInputStreamFromFile("res:/users.xls"));
        Executable<ExcelRow> importToList = new Mintleaf.AnyDataToListTransferBuilder<>().
                withSource(importFlavour).
                build();

        RowListWrapper<ExcelRow> rows = (RowListWrapper<ExcelRow>) importToList.execute();
        assertEquals(7, rows.size());
        assertEquals("qamatic", rows.getRow(6).getValue(1));
        assertEquals("qamatic", rows.getRow(6).getValue("USERNAME"));
    }
}
