package org.qamatic.mintleaf.dbexample.excel;

import org.junit.Test;
import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.core.BaseSqlReader;
import org.qamatic.mintleaf.excel.ExcelImportReader;
import org.qamatic.mintleaf.excel.ExcelRow;
import org.qamatic.mintleaf.tools.ImportReader;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by senips on 4/25/17.
 */
public class ExcelImportTest {


    @Test
    public void importFromExcelToObjectListTest() throws MintleafException {

        ImportReader importReader = new ExcelImportReader(BaseSqlReader.getInputStreamFromFile("res:/users.xls"));
        Executable<ExcelRow> importToList = new Mintleaf.AnyDataToListTransferBuilder<>().
                withSource(importReader).
                build();

        RowListWrapper<ExcelRow> rows = (RowListWrapper<ExcelRow>) importToList.execute();
        assertEquals(7, rows.size());
        assertEquals("qamatic", rows.getRow(6).getValue(1));
        assertEquals("qamatic", rows.getRow(6).getValue("USERNAME"));
    }

//    @Test
//    public void importFromExcelToObjectListTestMatches() throws MintleafException {
//
//        ImportReader importReader = new ExcelImportReader(BaseSqlReader.getInputStreamFromFile("res:/users.xls"));
//        Executable<ExcelRow> importToList = new Mintleaf.AnyDataToListTransferBuilder<>().
//                withSource(importReader).
//                withMatchingCriteria(new MintleafReadListener() {
//                    @Override
//                    public Object eachRow(int rowNum, Row row) throws MintleafException {
//                        return null;
//                    }
//
//
//                }).
//                build();
//
//        RowListWrapper<ExcelRow> rows = (RowListWrapper<ExcelRow>) importToList.execute();
//        assertEquals(7, rows.size());
//        assertEquals("qamatic", rows.getRow(6).getValue(1));
//        assertEquals("qamatic", rows.getRow(6).getValue("USERNAME"));
//    }
}
