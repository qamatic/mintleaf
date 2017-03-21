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

package org.qamatic.mintleaf.dbs;

import org.qamatic.mintleaf.Column;
import org.qamatic.mintleaf.ColumnMetaDataCollection;
import org.qamatic.mintleaf.ConnectionContext;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.core.Database;

import java.util.regex.Pattern;

/**
 * Created by qamatic on 3/6/16.
 */
public class H2Db extends Database {

    public H2Db(ConnectionContext connectionContext) {
        super(connectionContext);
    }


    @Override
    public ColumnMetaDataCollection getMetaData(String objectName) throws MintLeafException {
        final ColumnMetaDataCollection metaData = new ColumnMetaDataCollection(objectName);
        if (objectName != null) {
            objectName = objectName.toUpperCase();
        }

        String[] splits = objectName.split(Pattern.quote("."));


        String sql = String.format("select * from information_schema.columns where TABLE_SCHEMA ='%s' and TABLE_NAME='%s'", splits[0], splits[1]);
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

            return metaData.get(metaData.size() - 1);
        });
        return metaData;
    }

    @Override
    public boolean isTableExists(String tableName) throws MintLeafException {
        return getMetaData(tableName).size() != 0;
    }

}
