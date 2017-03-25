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

package org.qamatic.mintleaf.dbqueries;


import org.qamatic.mintleaf.*;
import org.qamatic.mintleaf.core.FluentJdbc;
import org.qamatic.mintleaf.core.StandardQueries;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by QAMatic on 4/6/15.
 */
public class OracleQueries extends StandardQueries {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(OracleQueries.class);

    public OracleQueries(ConnectionContext connectionContext) {
        super(connectionContext);
    }

    @Override
    public boolean isSqlObjectExists(String objectName, String objectType, boolean ignoreValidity) throws MintLeafException {
        final String[] objectNames = getObjectNames(objectName.toUpperCase());
        String validSql = ignoreValidity ? "" : "status='VALID' AND ";
        int cnt = getCount("all_objects", validSql + "OWNER = ? AND object_name = ? AND Object_Type = ?", parameterSets -> {
            parameterSets.setString(1, objectNames[0]);
            parameterSets.setString(2, objectNames[1]);
            parameterSets.setString(3, objectType);
        });
        return cnt != 0;
    }


    public boolean isColumnExists(String tableName, String columnName) throws MintLeafException {
        final String[] objectNames = getObjectNames(tableName.toUpperCase());
        int cnt = getCount("all_tab_columns", "OWNER = ? AND table_name = ? AND column_name = ?", parameterSets -> {
            parameterSets.setString(1, objectNames[0]);
            parameterSets.setString(2, objectNames[1]);
            parameterSets.setString(3, columnName.toUpperCase());
        });
        return cnt != 0;
    }

    @Override
    public boolean isTableExists(String tableName) throws MintLeafException {
        return isSqlObjectExists(tableName, "TABLE", false);
    }

    @Override
    public boolean isDbOptionExists(String optionName) throws MintLeafException {

        int cnt = getCount("dba_server_registry", "comp_id=? and status=?", parameterSets -> {
            parameterSets.setString(1, optionName.toUpperCase());
            parameterSets.setString(2, "VALID");
        });
        return cnt != 0;
    }

    @Override
    public void truncateTable(String tableName) throws MintLeafException {
        final String[] objectNames = getObjectNames(tableName.toUpperCase());
        String sql = String.format("truncate  table %s.%s", objectNames[0], objectNames[1]);
        try (Executable<int[]> executable = new FluentJdbc.Builder(connectionContext).withSql(sql).buildExecute()) {
            executable.execute();
        }
    }

    @Override
    public boolean isUserExists(String userName) throws MintLeafException {
        return getCount("all_users", "username = upper(?)", parameterSets -> {
            parameterSets.setString(1, userName);
        }) != 0;
    }

    @Override
    public List<String> getSqlObjects(String objectType) throws MintLeafException {

        return queryString(String.format("select object_name from user_objects where object_type='%s'", objectType), null, "object_name");
    }

    @Override
    public List<String> getPrimaryKeys(String ownerName, String tableName) throws MintLeafException {

        String owner = "";
        if (ownerName != null) {
            owner = String.format(" and ucc.owner=Upper('%s')", ownerName);
        }
        String sql = String
                .format("select ucc.column_name as keyname from all_constraints uc, all_cons_columns ucc where uc.table_name = upper('%s') and uc.constraint_type = 'P' and (uc.constraint_name=ucc.constraint_name) and  uc.owner=ucc.owner %s",
                        tableName, owner);
        return queryString(sql, null, "keyname");
    }

    @Override
    public boolean isPrivilegeExists(String granteeName, String privilegeName, String objectName) throws MintLeafException {
        int cnt = getCount("all_tab_privs", "grantee = ? AND table_name = ? AND privilege=?", parameterSets -> {
            parameterSets.setString(1, granteeName.toUpperCase());
            parameterSets.setString(2, objectName.toUpperCase());
            parameterSets.setString(3, privilegeName.toUpperCase());
        });
        return cnt != 0;
    }


    private String getSqlObjectMetaSql(String objectName) throws SQLException, MintLeafException {

        final String[] objectNames = getObjectNames(objectName);

        final StringBuilder sql = new StringBuilder(
                String.format(
                        "SELECT COLUMN_NAME,DATA_TYPE TYPE_NAME,DATA_LENGTH,DATA_PRECISION,DATA_SCALE,CHAR_LENGTH FROM ALL_TAB_COLUMNS WHERE \n" +
                                "OWNER = UPPER('%s') AND TABLE_NAME = UPPER('%s') ORDER BY COLUMN_ID",
                        objectNames[0], objectNames[1]));

        final String typeCheckSql = String.format("SELECT DECODE(OBJECT_TYPE, 'TYPE', 1, 0) ISTYPEOBJECT FROM ALL_OBJECTS WHERE OWNER = UPPER('%s') AND OBJECT_NAME = UPPER(\n" +
                "'%s')", objectNames[0], objectNames[1]);
        query(typeCheckSql,
                (DataRowListener) (row, resultSet) -> {
                    if (resultSet.asInt("ISTYPEOBJECT") == 1) {
                        sql.setLength(0);
                        sql.append(String
                                .format("SELECT ATTR_NAME column_name,ATTR_TYPE_NAME TYPE_NAME,LENGTH DATA_LENGTH,PRECISION DATA_PRECISION,SCALE Data_Scale, \n" +
                                                "LENGTH Char_Length FROM ALL_TYPE_ATTRS WHERE OWNER = UPPER('%s') AND TYPE_NAME = UPPER('%s') ORDER BY ATTR_NO ",
                                        objectNames[0], objectNames[1]));
                    }

                    return null;
                });

        return sql.toString();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ColumnMetaDataCollection getMetaData(String objectName) throws MintLeafException {
        final ColumnMetaDataCollection metaData = new ColumnMetaDataCollection(objectName);
        if (objectName != null) {
            objectName = objectName.toUpperCase();
        }

        String sql = null;
        try {
            sql = getSqlObjectMetaSql(objectName);

            query(sql, (row, rs) -> {


                metaData.add(new Column() {
                    {
                        setColumnName(rs.asString("COLUMN_NAME"));
                        setTypeName(rs.asString("TYPE_NAME"));

                        if (rs.asString("TYPE_NAME").equals("CHAR")) {

                            setColumnSize(rs.asInt("CHAR_LENGTH"));

                        } else if (rs.asString("TYPE_NAME").equals("NUMBER")) {
                            setColumnSize(rs.asInt("DATA_PRECISION"));
                            setDecimalDigits(rs.asInt("DATA_SCALE"));

                        } else {
                            setColumnSize(rs.asInt("DATA_LENGTH"));
                            setDecimalDigits(rs.asInt("DATA_PRECISION"));
                        }
                    }
                });


                return metaData.get(metaData.size() - 1);
            });

        } catch (SQLException e) {
            logger.error("error getting meta data", e);
            throw new MintLeafException(e);
        }
        return metaData;

    }


}
