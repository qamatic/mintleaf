package org.qamatic.mintleaf.dbexample.excel;

import org.junit.Test;
import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.core.BaseSqlReader;
import org.qamatic.mintleaf.excel.ExcelReader;
import org.qamatic.mintleaf.excel.ExcelRow;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by QAmatic Team on 4/25/17.
 */
public class ExcelImportTest {


    @Test
    public void importFromExcelToObjectListTest() throws MintleafException {

        MintleafReader importReader = new ExcelReader(BaseSqlReader.getInputStreamFromFile("res:/users.xls"));
        Executable<ExcelRow> importToList = new Mintleaf.AnyDataToListTransferBuilder<>().
                withSource(importReader).
                build();

        Table<ExcelRow> rows = (Table<ExcelRow>) importToList.execute();
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
//        Table<ExcelRow> rows = (Table<ExcelRow>) importToList.execute();
//        assertEquals(7, rows.size());
//        assertEquals("qamatic", rows.getRow(6).getValue(1));
//        assertEquals("qamatic", rows.getRow(6).getValue("USERNAME"));
//    }
}
