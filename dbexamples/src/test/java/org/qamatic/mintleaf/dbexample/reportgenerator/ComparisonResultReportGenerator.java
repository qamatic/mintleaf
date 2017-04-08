/*
 *
 *   *
 *   *  *
 *   *  *   ~
 *   *  *   ~ The MIT License (MIT)
 *   *  *   ~
 *   *  *   ~ Copyright (c) 2010-2017 QAMatic Team
 *   *  *   ~
 *   *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *   *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *   *  *   ~ in the Software without restriction, including without limitation the rights
 *   *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *   *  *   ~ furnished to do so, subject to the following conditions:
 *   *  *   ~
 *   *  *   ~ The above copyright notice and this permission notice shall be included in all
 *   *  *   ~ copies or substantial portions of the Software.
 *   *  *   ~
 *   *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   *  *   ~ SOFTWARE.
 *   *  *   ~
 *   *  *   ~
 *   *  *
 *   *
 *   *
 *
 * /
 */

package org.qamatic.mintleaf.dbexample.reportgenerator;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.RowListWrapper;
import org.qamatic.mintleaf.data.ColumnState;
import org.qamatic.mintleaf.data.ComparerListener;
import org.qamatic.mintleaf.data.RowState;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by QAmatic Team on 3/11/17.
 */
public class ComparisonResultReportGenerator implements ComparerListener {

    private static final MintleafLogger logger = MintleafLogger.getLogger(ComparisonResultReportGenerator.class);

    private FileWriter fileWriter;

    public ComparisonResultReportGenerator(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    @Override
    public void OnBeginCompare(RowListWrapper sourceTable, RowListWrapper targetTable) throws MintleafException {
        try {
            this.fileWriter.write("<html><head><style> tr,td,table{border:1px silver solid} .pass{ background-color:green;color:white} .fail {background-color:red;color:white} </style></head><body><table>");
            fileWriter.write("<tr>");
            for (int i = 0; i < sourceTable.getMetaData().getColumnCount(); i++) {
                fileWriter.write("<td>" + sourceTable.getMetaData().getColumnName(i) + "</td>");
            }
            fileWriter.write("</tr>");
        } catch (SQLException e) {
            throw new MintleafException(e);

        } catch (IOException e) {
            throw new MintleafException(e);
        }
    }

    @Override
    public void onBeforeRowCompare(RowState sourceRow, RowState targetRow) throws MintleafException {

    }

    @Override
    public void onAfterRowCompare(RowState sourceRow, RowState targetRow) throws MintleafException {

    }

    @Override
    public void OnRowCompare(RowState sourceRow, RowState targetRow) throws MintleafException {
        if (sourceRow.RowNumber == 0) {

        }
    }

    @Override
    public void OnColumnCompare(final ColumnState sourceColumn, final ColumnState targetColumn) throws MintleafException {
        try {
            if (sourceColumn.equals(targetColumn)) {
                fileWriter.write(String.format("<td class='%s'>%s</td>", "pass", sourceColumn));
            } else {
                fileWriter.write(String.format("<td class='%s'>%s [expected %s]</td>", "fail", sourceColumn, targetColumn));
            }

        } catch (IOException e) {
            throw new MintleafException(e);
        }
    }

    @Override
    public void OnEndCompare(RowState sourceRow, RowState targetRow) throws MintleafException {
        try {
            this.fileWriter.write("</table></body></html>");
            fileWriter.close();
        } catch (IOException e) {
            throw new MintleafException(e);
        }
    }

    private void writeRow(String[] colValue, String[] className) throws MintleafException {
        try {
            for (int i = 0; i < colValue.length; i++)
                fileWriter.write(String.format("<td class='%s'>%s</td>", className[i], colValue[i]));
        } catch (IOException e) {
            throw new MintleafException(e);
        }
    }

}
