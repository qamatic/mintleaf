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

package org.qamatic.mintleaf.core.stdqueries;

import org.qamatic.mintleaf.Column;
import org.qamatic.mintleaf.ColumnMetaDataCollection;
import org.qamatic.mintleaf.ConnectionContext;
import org.qamatic.mintleaf.MintleafException;

/**
 * Created by qamatic on 3/6/16.
 */
public class H2Queries extends StandardQueries {

    public H2Queries(ConnectionContext connectionContext) {
        super(connectionContext);
    }


    @Override
    public ColumnMetaDataCollection getMetaData(String objectName) throws MintleafException {
        final ColumnMetaDataCollection metaData = new ColumnMetaDataCollection(objectName);

        String[] splits = cleanObjectName(objectName);

        final String select = "select * from information_schema.columns ";
        String sql = splits.length == 2? String.format("%s WHERE TABLE_SCHEMA ='%s' and  TABLE_NAME='%s'", select, splits[0], splits[1]) :
                String.format("%s WHERE  TABLE_NAME='%s'", select, objectName);
        query(sql, (rowNum, rs) -> {
            metaData.add(new Column() {
                {
                    setColumnName(rs.asString("COLUMN_NAME"));
                    setNullable(rs.asString("IS_NULLABLE") != "NO");
                    setColumnSize(1);
                    setDatatype(rs.asInt("DATA_TYPE"));
                    setColumnSize(rs.asInt("CHARACTER_OCTET_LENGTH"));
                    if (rs.asString("TYPE_NAME").equals("CHAR")) {

                    } else if (rs.asString("TYPE_NAME").equals("DECIMAL")) {
                        setColumnSize(rs.asInt("NUMERIC_PRECISION"));
                    }
                    setDecimalDigits(rs.asInt("NUMERIC_SCALE"));
                }
            });


        });
        return metaData;
    }

    @Override
    public boolean isTableExists(String tableName) throws MintleafException {
        return getMetaData(tableName).size() != 0;
    }

}
