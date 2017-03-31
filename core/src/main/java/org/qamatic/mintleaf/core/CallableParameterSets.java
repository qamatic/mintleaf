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

package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.MintLeafLogger;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

/**
 * Created by qamatic on 2/20/16.
 */
public class CallableParameterSets extends ParameterSets {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(CallableParameterSets.class);
    private CallableStatement callableStatement;

    public CallableParameterSets(CallableStatement preparedStatement) {
        super(preparedStatement);
        this.callableStatement = preparedStatement;
    }

    /**
     * Registers the OUT parameter in ordinal position
     * <code>parameterIndex</code> to the JDBC type
     * <code>sqlType</code>.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by <code>sqlType</code> for an OUT
     * parameter determines the Java type that must be used
     * in the <code>get</code> method to read the value of that parameter.
     * <p>
     * If the JDBC type expected to be returned to this output parameter
     * is specific to this particular database, <code>sqlType</code>
     * should be <code>java.sql.Types.OTHER</code>.  The method
     * {@link #getObject} retrieves the value.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @param sqlType        the JDBC type code defined by <code>java.sql.Types</code>.
     *                       If the parameter is of JDBC type <code>NUMERIC</code>
     *                       or <code>DECIMAL</code>, the version of
     *                       <code>registerOutParameter</code> that accepts a scale value
     *                       should be used.
     * @throws SQLException                    if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if <code>sqlType</code> is
     *                                         a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     *                                         <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     *                                         <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *                                         <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     *                                         or  <code>STRUCT</code> data type and the JDBC driver does not support
     *                                         this data type
     * @see Types
     */
    public void registerOutParameter(int parameterIndex, int sqlType) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Registers the parameter named
     * <code>parameterName</code> to be of JDBC type
     * <code>sqlType</code>.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by <code>sqlType</code> for an OUT
     * parameter determines the Java type that must be used
     * in the <code>get</code> method to read the value of that parameter.
     * <p>
     * This version of <code>registerOutParameter</code> should be
     * used when the parameter is of JDBC type <code>NUMERIC</code>
     * or <code>DECIMAL</code>.
     *
     * @param parameterName the name of the parameter
     * @param sqlType       SQL type code defined by <code>java.sql.Types</code>.
     * @param scale         the desired number of digits to the right of the
     *                      decimal point.  It must be greater than or equal to zero.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if <code>sqlType</code> is
     *                                         a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     *                                         <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     *                                         <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *                                         <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     *                                         or  <code>STRUCT</code> data type and the JDBC driver does not support
     *                                         this data type or if the JDBC driver does not support
     *                                         this method
     * @see Types
     * @since 1.4
     */
    public void registerOutParameter(String parameterName, int sqlType, int scale) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType, scale);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given
     * <code>java.math.BigDecimal</code> value.
     * The driver converts this to an SQL <code>NUMERIC</code> value when
     * it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getBigDecimal
     * @since 1.4
     */
    public void setBigDecimal(String parameterName, BigDecimal x) throws MintLeafException {
        try {
            callableStatement.setBigDecimal(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to a <code>java.sql.NClob</code> object. The object
     * implements the <code>java.sql.NClob</code> interface. This <code>NClob</code>
     * object maps to a SQL <code>NCLOB</code>.
     *
     * @param parameterName the name of the parameter to be set
     * @param value         the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setNClob(String parameterName, NClob value) throws MintLeafException {
        try {
            callableStatement.setNClob(parameterName, value);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the SQL cursor name to the given <code>String</code>, which
     * will be used by subsequent <code>Statement</code> object
     * <code>execute</code> methods. This name can then be
     * used in SQL positioned update or delete statements to identify the
     * current row in the <code>ResultSet</code> object generated by this
     * statement.  If the database does not support positioned update/delete,
     * this method is a noop.  To insure that a cursor has the proper isolation
     * level to support updates, the cursor's <code>SELECT</code> statement
     * should have the form <code>SELECT FOR UPDATE</code>.  If
     * <code>FOR UPDATE</code> is not present, positioned updates may fail.
     * <p>
     * <P><B>Note:</B> By definition, the execution of positioned updates and
     * deletes must be done by a different <code>Statement</code> object than
     * the one that generated the <code>ResultSet</code> object being used for
     * positioning. Also, cursor names must be unique within a connection.
     *
     * @param name the new cursor name, which must be unique within
     *             a connection
     * @throws SQLException                    if a database access error occurs or
     *                                         this method is called on a closed <code>Statement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     */
    public void setCursorName(String name) throws MintLeafException {
        try {
            callableStatement.setCursorName(name);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Registers the parameter in ordinal position
     * <code>parameterIndex</code> to be of JDBC type
     * <code>sqlType</code>. All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by <code>sqlType</code> for an OUT
     * parameter determines the Java type that must be used
     * in the <code>get</code> method to read the value of that parameter.
     * <p>
     * This version of <code>registerOutParameter</code> should be
     * used when the parameter is of JDBC type <code>NUMERIC</code>
     * or <code>DECIMAL</code>.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @param sqlType        the SQL type code defined by <code>java.sql.Types</code>.
     * @param scale          the desired number of digits to the right of the
     *                       decimal point.  It must be greater than or equal to zero.
     * @throws SQLException                    if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if <code>sqlType</code> is
     *                                         a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     *                                         <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     *                                         <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *                                         <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     *                                         or  <code>STRUCT</code> data type and the JDBC driver does not support
     *                                         this data type
     * @see Types
     */
    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType, scale);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>TIME</code> value,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the time
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @param cal           the <code>Calendar</code> object the driver will use
     *                      to construct the time
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getTime
     * @since 1.4
     */
    public void setTime(String parameterName, Time x, Calendar cal) throws MintLeafException {
        try {
            callableStatement.setTime(parameterName, x, cal);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the value of the designated parameter with the given object.
     * <p>
     * This method is similar to {@link #setObject(String parameterName,
     * Object x, SQLType targetSqlType, int scaleOrLength)},
     * except that it assumes a scale of zero.
     * <p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * @param parameterName the name of the parameter
     * @param x             the object containing the input parameter value
     * @param targetSqlType the SQL type to be sent to the database
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs
     *                                         or this method is called on a closed {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if
     *                                         the JDBC driver does not support the specified targetSqlType
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    public void setObject(String parameterName, Object x, SQLType targetSqlType) throws MintLeafException {
        try {
            callableStatement.setObject(parameterName, x, targetSqlType);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Registers the designated output parameter.
     * This version of
     * the method <code>registerOutParameter</code>
     * should be used for a user-defined or <code>REF</code> output parameter.  Examples
     * of user-defined types include: <code>STRUCT</code>, <code>DISTINCT</code>,
     * <code>JAVA_OBJECT</code>, and named array types.
     * <p>
     * All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>  For a user-defined parameter, the fully-qualified SQL
     * type name of the parameter should also be given, while a <code>REF</code>
     * parameter requires that the fully-qualified type name of the
     * referenced type be given.  A JDBC driver that does not need the
     * type code and type name information may ignore it.   To be portable,
     * however, applications should always provide these values for
     * user-defined and <code>REF</code> parameters.
     * <p>
     * Although it is intended for user-defined and <code>REF</code> parameters,
     * this method may be used to register a parameter of any JDBC type.
     * If the parameter does not have a user-defined or <code>REF</code> type, the
     * <i>typeName</i> parameter is ignored.
     * <p>
     * <P><B>Note:</B> When reading the value of an out parameter, you
     * must use the getter method whose Java type corresponds to the
     * parameter's registered SQL type.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,...
     * @param sqlType        a value from {@link Types}
     * @param typeName       the fully-qualified name of an SQL structured type
     * @throws SQLException                    if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if <code>sqlType</code> is
     *                                         a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     *                                         <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     *                                         <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *                                         <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     *                                         or  <code>STRUCT</code> data type and the JDBC driver does not support
     *                                         this data type
     * @see Types
     * @since 1.2
     */
    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType, typeName);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>Reader</code>
     * object, which is the given number of characters long.
     * When a very large UNICODE value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.Reader</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     * <p>
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterName the name of the parameter
     * @param reader        the <code>java.io.Reader</code> object that
     *                      contains the UNICODE data used as the designated parameter
     * @param length        the number of characters in the stream
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setCharacterStream(String parameterName, Reader reader, long length) throws MintLeafException {
        try {
            callableStatement.setCharacterStream(parameterName, reader, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Timestamp</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>TIMESTAMP</code> value,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the timestamp
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @param cal           the <code>Calendar</code> object the driver will use
     *                      to construct the timestamp
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getTimestamp
     * @since 1.4
     */
    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws MintLeafException {
        try {
            callableStatement.setTimestamp(parameterName, x, cal);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object.  The <code>reader</code> must contain  the number
     * of characters specified by length otherwise a <code>SQLException</code> will be
     * generated when the <code>CallableStatement</code> is executed.
     * This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
     *
     * @param parameterName the name of the parameter to be set
     * @param reader        An object that contains the data to set the parameter value to.
     * @param length        the number of characters in the parameter data.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if the length specified is less than zero;
     *                                         a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setClob(String parameterName, Reader reader, long length) throws MintLeafException {
        try {
            callableStatement.setClob(parameterName, reader, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Registers the designated output parameter.  This version of
     * the method {@code  registerOutParameter}
     * should be used for a user-named or REF output parameter.  Examples
     * of user-named types include: STRUCT, DISTINCT, JAVA_OBJECT, and
     * named array types.
     * <p>
     * All OUT parameters must be registered
     * before a stored procedure is executed.
     * </p>
     * For a user-named parameter the fully-qualified SQL
     * type name of the parameter should also be given, while a REF
     * parameter requires that the fully-qualified type name of the
     * referenced type be given.  A JDBC driver that does not need the
     * type code and type name information may ignore it.   To be portable,
     * however, applications should always provide these values for
     * user-named and REF parameters.
     * <p>
     * Although it is intended for user-named and REF parameters,
     * this method may be used to register a parameter of any JDBC type.
     * If the parameter does not have a user-named or REF type, the
     * typeName parameter is ignored.
     * <p>
     * <P><B>Note:</B> When reading the value of an out parameter, you
     * must use the {@code getXXX} method whose Java type XXX corresponds to the
     * parameter's registered SQL type.
     * <p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * @param parameterName the name of the parameter
     * @param sqlType       the JDBC type code defined by {@code SQLType} to use to
     *                      register the OUT Parameter.
     * @param typeName      the fully-qualified name of an SQL structured type
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if
     *                                         the JDBC driver does not support the specified sqlType
     *                                         or if the JDBC driver does not support this method
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    public void registerOutParameter(String parameterName, SQLType sqlType, String typeName) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType, typeName);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


    /**
     * Sets the designated parameter to a <code>InputStream</code> object.
     * This method differs from the <code>setBinaryStream (int, InputStream)</code>
     * method because it informs the driver that the parameter value should be
     * sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used,
     * the driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
     * <p>
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setBlob</code> which takes a length parameter.
     *
     * @param parameterName the name of the parameter
     * @param inputStream   An object that contains the data to set the parameter
     *                      value to.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @since 1.6
     */
    public void setBlob(String parameterName, InputStream inputStream) throws MintLeafException {
        try {
            callableStatement.setBlob(parameterName, inputStream);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     * This version of the method <code>setNull</code> should
     * be used for user-defined types and REF type parameters.  Examples
     * of user-defined types include: STRUCT, DISTINCT, JAVA_OBJECT, and
     * named array types.
     * <p>
     * <P><B>Note:</B> To be portable, applications must give the
     * SQL type code and the fully-qualified SQL type name when specifying
     * a NULL user-defined or REF parameter.  In the case of a user-defined type
     * the name is the type name of the parameter itself.  For a REF
     * parameter, the name is the type name of the referenced type.
     * <p>
     * Although it is intended for user-defined and Ref parameters,
     * this method may be used to set a null parameter of any JDBC type.
     * If the parameter does not have a user-defined or REF type, the given
     * typeName is ignored.
     *
     * @param parameterName the name of the parameter
     * @param sqlType       a value from <code>java.sql.Types</code>
     * @param typeName      the fully-qualified name of an SQL user-defined type;
     *                      ignored if the parameter is not a user-defined type or
     *                      SQL <code>REF</code> value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    public void setNull(String parameterName, int sqlType, String typeName) throws MintLeafException {
        try {
            callableStatement.setNull(parameterName, sqlType, typeName);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large ASCII value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code>. Data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from ASCII to the database char format.
     * <p>
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterName the name of the parameter
     * @param x             the Java input stream that contains the ASCII parameter value
     * @param length        the number of bytes in the stream
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setAsciiStream(String parameterName, InputStream x, long length) throws MintLeafException {
        try {
            callableStatement.setAsciiStream(parameterName, x, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


    /**
     * Sets the value of the designated parameter with the given object.
     * <p>
     * This method is similar to {@link #setObject(String parameterName,
     * Object x, int targetSqlType, int scaleOrLength)},
     * except that it assumes a scale of zero.
     *
     * @param parameterName the name of the parameter
     * @param x             the object containing the input parameter value
     * @param targetSqlType the SQL type (as defined in java.sql.Types) to be
     *                      sent to the database
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if
     *                                         the JDBC driver does not support the specified targetSqlType
     * @see #getObject
     * @since 1.4
     */
    public void setObject(String parameterName, Object x, int targetSqlType) throws MintLeafException {
        try {
            callableStatement.setObject(parameterName, x, targetSqlType);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large ASCII value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code>. Data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from ASCII to the database char format.
     * <p>
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterName the name of the parameter
     * @param x             the Java input stream that contains the ASCII parameter value
     * @param length        the number of bytes in the stream
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    public void setAsciiStream(String parameterName, InputStream x, int length) throws MintLeafException {
        try {
            callableStatement.setAsciiStream(parameterName, x, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to a <code>InputStream</code> object.  The <code>inputstream</code> must contain  the number
     * of characters specified by length, otherwise a <code>SQLException</code> will be
     * generated when the <code>CallableStatement</code> is executed.
     * This method differs from the <code>setBinaryStream (int, InputStream, int)</code>
     * method because it informs the driver that the parameter value should be
     * sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used,
     * the driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
     *
     * @param parameterName the name of the parameter to be set
     *                      the second is 2, ...
     * @param inputStream   An object that contains the data to set the parameter
     *                      value to.
     * @param length        the number of bytes in the parameter data.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if the length specified
     *                                         is less than zero; if the number of bytes in the inputstream does not match
     *                                         the specified length; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setBlob(String parameterName, InputStream inputStream, long length) throws MintLeafException {
        try {
            callableStatement.setBlob(parameterName, inputStream, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>Reader</code>
     * object.
     * When a very large UNICODE value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.Reader</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     * <p>
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setCharacterStream</code> which takes a length parameter.
     *
     * @param parameterName the name of the parameter
     * @param reader        the <code>java.io.Reader</code> object that contains the
     *                      Unicode data
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @since 1.6
     */
    public void setCharacterStream(String parameterName, Reader reader) throws MintLeafException {
        try {
            callableStatement.setCharacterStream(parameterName, reader);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object.
     * This method differs from the <code>setCharacterStream (int, Reader)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
     * <p>
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setClob</code> which takes a length parameter.
     *
     * @param parameterName the name of the parameter
     * @param reader        An object that contains the data to set the parameter value to.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or this method is called on
     *                                         a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @since 1.6
     */
    public void setClob(String parameterName, Reader reader) throws MintLeafException {
        try {
            callableStatement.setClob(parameterName, reader);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the value of the designated parameter with the given object.
     * <p>
     * <p>The given Java object will be converted to the given targetSqlType
     * before being sent to the database.
     * <p>
     * If the object has a custom mapping (is of a class implementing the
     * interface <code>SQLData</code>),
     * the JDBC driver should call the method <code>SQLData.writeSQL</code> to write it
     * to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,  <code>NClob</code>,
     * <code>Struct</code>, <code>java.net.URL</code>,
     * or <code>Array</code>, the driver should pass it to the database as a
     * value of the corresponding SQL type.
     * <p>
     * Note that this method may be used to pass datatabase-
     * specific abstract data types.
     *
     * @param parameterName the name of the parameter
     * @param x             the object containing the input parameter value
     * @param targetSqlType the SQL type (as defined in java.sql.Types) to be
     *                      sent to the database. The scale argument may further qualify this type.
     * @param scale         for java.sql.Types.DECIMAL or java.sql.Types.NUMERIC types,
     *                      this is the number of digits after the decimal point.  For all other
     *                      types, this value will be ignored.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if
     *                                         the JDBC driver does not support the specified targetSqlType
     * @see Types
     * @see #getObject
     * @since 1.4
     */
    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws MintLeafException {
        try {
            callableStatement.setObject(parameterName, x, targetSqlType, scale);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>short</code> value.
     * The driver converts this
     * to an SQL <code>SMALLINT</code> value when it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getShort
     * @since 1.4
     */
    public void setShort(String parameterName, short x) throws MintLeafException {
        try {
            callableStatement.setShort(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>Reader</code>
     * object, which is the given number of characters long.
     * When a very large UNICODE value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.Reader</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     * <p>
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterName the name of the parameter
     * @param reader        the <code>java.io.Reader</code> object that
     *                      contains the UNICODE data used as the designated parameter
     * @param length        the number of characters in the stream
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    public void setCharacterStream(String parameterName, Reader reader, int length) throws MintLeafException {
        try {
            callableStatement.setCharacterStream(parameterName, reader, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object.
     * This method differs from the <code>setCharacterStream (int, Reader)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setNClob</code> which takes a length parameter.
     *
     * @param parameterName the name of the parameter
     * @param reader        An object that contains the data to set the parameter value to.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if the driver does not support national character sets;
     *                                         if the driver can detect that a data conversion
     *                                         error could occur;  if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @since 1.6
     */
    public void setNClob(String parameterName, Reader reader) throws MintLeafException {
        try {
            callableStatement.setNClob(parameterName, reader);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * <p>Sets the value of the designated parameter with the given object.
     * <p>
     * If the second argument is an {@code InputStream} then the stream
     * must contain the number of bytes specified by scaleOrLength.
     * If the second argument is a {@code Reader} then the reader must
     * contain the number of characters specified
     * by scaleOrLength. If these conditions are not true the driver
     * will generate a
     * {@code SQLException} when the prepared statement is executed.
     * <p>
     * <p>The given Java object will be converted to the given targetSqlType
     * before being sent to the database.
     * <p>
     * If the object has a custom mapping (is of a class implementing the
     * interface {@code SQLData}),
     * the JDBC driver should call the method {@code SQLData.writeSQL} to
     * write it to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * {@code Ref}, {@code Blob}, {@code Clob},  {@code NClob},
     * {@code Struct}, {@code java.net.URL},
     * or {@code Array}, the driver should pass it to the database as a
     * value of the corresponding SQL type.
     * <p>
     * <p>Note that this method may be used to pass database-specific
     * abstract data types.
     * <p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * @param parameterName the name of the parameter
     * @param x             the object containing the input parameter value
     * @param targetSqlType the SQL type to be
     *                      sent to the database. The scale argument may further qualify this type.
     * @param scaleOrLength for {@code java.sql.JDBCType.DECIMAL}
     *                      or {@code java.sql.JDBCType.NUMERIC types},
     *                      this is the number of digits after the decimal point. For
     *                      Java Object types {@code InputStream} and {@code Reader},
     *                      this is the length
     *                      of the data in the stream or reader.  For all other types,
     *                      this value will be ignored.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs
     *                                         or this method is called on a closed {@code CallableStatement}  or
     *                                         if the Java Object specified by x is an InputStream
     *                                         or Reader object and the value of the scale parameter is less
     *                                         than zero
     * @throws SQLFeatureNotSupportedException if
     *                                         the JDBC driver does not support the specified targetSqlType
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    public void setObject(String parameterName, Object x, SQLType targetSqlType, int scaleOrLength) throws MintLeafException {
        try {
            callableStatement.setObject(parameterName, x, targetSqlType, scaleOrLength);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the value of the designated parameter with the given object.
     * <p>
     * <p>The JDBC specification specifies a standard mapping from
     * Java <code>Object</code> types to SQL types.  The given argument
     * will be converted to the corresponding SQL type before being
     * sent to the database.
     * <p>Note that this method may be used to pass datatabase-
     * specific abstract data types, by using a driver-specific Java
     * type.
     * <p>
     * If the object is of a class implementing the interface <code>SQLData</code>,
     * the JDBC driver should call the method <code>SQLData.writeSQL</code>
     * to write it to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,  <code>NClob</code>,
     * <code>Struct</code>, <code>java.net.URL</code>,
     * or <code>Array</code>, the driver should pass it to the database as a
     * value of the corresponding SQL type.
     * <p>
     * This method throws an exception if there is an ambiguity, for example, if the
     * object is of a class implementing more than one of the interfaces named above.
     * <p>
     * <b>Note:</b> Not all databases allow for a non-typed Null to be sent to
     * the backend. For maximum portability, the <code>setNull</code> or the
     * <code>setObject(String parameterName, Object x, int sqlType)</code>
     * method should be used
     * instead of <code>setObject(String parameterName, Object x)</code>.
     * <p>
     *
     * @param parameterName the name of the parameter
     * @param x             the object containing the input parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs,
     *                                         this method is called on a closed <code>CallableStatement</code> or if the given
     *                                         <code>Object</code> parameter is ambiguous
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getObject
     * @since 1.4
     */
    public void setObject(String parameterName, Object x) throws MintLeafException {
        try {
            callableStatement.setObject(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given input stream.
     * When a very large binary value is input to a <code>LONGVARBINARY</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the
     * stream as needed until end-of-file is reached.
     * <p>
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setBinaryStream</code> which takes a length parameter.
     *
     * @param parameterName the name of the parameter
     * @param x             the java input stream which contains the binary parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @since 1.6
     */
    public void setBinaryStream(String parameterName, InputStream x) throws MintLeafException {
        try {
            callableStatement.setBinaryStream(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given input stream.
     * When a very large ASCII value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code>. Data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from ASCII to the database char format.
     * <p>
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setAsciiStream</code> which takes a length parameter.
     *
     * @param parameterName the name of the parameter
     * @param x             the Java input stream that contains the ASCII parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @since 1.6
     */
    public void setAsciiStream(String parameterName, InputStream x) throws MintLeafException {
        try {
            callableStatement.setAsciiStream(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object.  The <code>reader</code> must contain  the number
     * of characters specified by length otherwise a <code>SQLException</code> will be
     * generated when the <code>CallableStatement</code> is executed.
     * This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     *
     * @param parameterName the name of the parameter to be set
     * @param reader        An object that contains the data to set the parameter value to.
     * @param length        the number of characters in the parameter data.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if the length specified is less than zero;
     *                                         if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setNClob(String parameterName, Reader reader, long length) throws MintLeafException {
        try {
            callableStatement.setNClob(parameterName, reader, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Blob</code> object.
     * The driver converts this to an SQL <code>BLOB</code> value when it
     * sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             a <code>Blob</code> object that maps an SQL <code>BLOB</code> value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setBlob(String parameterName, Blob x) throws MintLeafException {
        try {
            callableStatement.setBlob(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Registers the OUT parameter named
     * <code>parameterName</code> to the JDBC type
     * {@code sqlType}.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by {@code sqlType} for an OUT
     * parameter determines the Java type that must be used
     * in the {@code get} method to read the value of that parameter.
     * <p>
     * If the JDBC type expected to be returned to this output parameter
     * is specific to this particular database, {@code sqlType}
     * should be {@code JDBCType.OTHER} or a {@code SQLType} that is supported
     * by the JDBC driver..  The method
     * {@link #getObject} retrieves the value.
     * <p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * @param parameterName the name of the parameter
     * @param sqlType       the JDBC type code defined by {@code SQLType} to use to
     *                      register the OUT Parameter.
     *                      If the parameter is of JDBC type {@code JDBCType.NUMERIC}
     *                      or {@code JDBCType.DECIMAL}, the version of
     *                      {@code  registerOutParameter} that accepts a scale value
     *                      should be used.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if
     *                                         the JDBC driver does not support the specified sqlType
     *                                         or if the JDBC driver does not support
     *                                         this method
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    public void registerOutParameter(String parameterName, SQLType sqlType) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>int</code> value.
     * The driver converts this
     * to an SQL <code>INTEGER</code> value when it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getInt
     * @since 1.4
     */
    public void setInt(String parameterName, int x) throws MintLeafException {
        try {
            callableStatement.setInt(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>String</code> value.
     * The driver converts this
     * to an SQL <code>VARCHAR</code> or <code>LONGVARCHAR</code> value
     * (depending on the argument's
     * size relative to the driver's limits on <code>VARCHAR</code> values)
     * when it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getString
     * @since 1.4
     */
    public void setString(String parameterName, String x) throws MintLeafException {
        try {
            callableStatement.setString(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.net.URL</code> object.
     * The driver converts this to an SQL <code>DATALINK</code> value when
     * it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param val           the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs;
     *                                         this method is called on a closed <code>CallableStatement</code>
     *                                         or if a URL is malformed
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getURL
     * @since 1.4
     */
    public void setURL(String parameterName, URL val) throws MintLeafException {
        try {
            callableStatement.setURL(parameterName, val);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Timestamp</code> value.
     * The driver
     * converts this to an SQL <code>TIMESTAMP</code> value when it sends it to the
     * database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getTimestamp
     * @since 1.4
     */
    public void setTimestamp(String parameterName, Timestamp x) throws MintLeafException {
        try {
            callableStatement.setTimestamp(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value.
     * The driver converts this
     * to an SQL <code>TIME</code> value when it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getTime
     * @since 1.4
     */
    public void setTime(String parameterName, Time x) throws MintLeafException {
        try {
            callableStatement.setTime(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the limit for the maximum number of bytes that can be returned for
     * character and binary column values in a <code>ResultSet</code>
     * object produced by this <code>Statement</code> object.
     * <p>
     * This limit applies
     * only to <code>BINARY</code>, <code>VARBINARY</code>,
     * <code>LONGVARBINARY</code>, <code>CHAR</code>, <code>VARCHAR</code>,
     * <code>NCHAR</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code> and
     * <code>LONGVARCHAR</code> fields.  If the limit is exceeded, the excess data
     * is silently discarded. For maximum portability, use values
     * greater than 256.
     *
     * @param max the new column size limit in bytes; zero means there is no limit
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed <code>Statement</code>
     *                      or the condition {@code max >= 0} is not satisfied
     * @see #getMaxFieldSize
     */
    public void setMaxFieldSize(int max) throws MintLeafException {
        try {
            callableStatement.setMaxFieldSize(max);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>long</code> value.
     * The driver converts this
     * to an SQL <code>BIGINT</code> value when it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getLong
     * @since 1.4
     */
    public void setLong(String parameterName, long x) throws MintLeafException {
        try {
            callableStatement.setLong(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


    /**
     * Sets the limit for the maximum number of rows that any
     * <code>ResultSet</code> object  generated by this <code>Statement</code>
     * object can contain to the given number.
     * If the limit is exceeded, the excess
     * rows are silently dropped.
     * <p>
     * This method should be used when the row limit may exceed
     * {@link Integer#MAX_VALUE}.
     * <p>
     * The default implementation will throw {@code UnsupportedOperationException}
     *
     * @param max the new max rows limit; zero means there is no limit
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed <code>Statement</code>
     *                      or the condition {@code max >= 0} is not satisfied
     * @see #getMaxRows
     * @since 1.8
     */
    public void setLargeMaxRows(long max) throws MintLeafException {
        try {
            callableStatement.setLargeMaxRows(max);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


    /**
     * Registers the designated output parameter.
     * This version of
     * the method {@code  registerOutParameter}
     * should be used for a user-defined or {@code REF} output parameter.
     * Examples
     * of user-defined types include: {@code STRUCT}, {@code DISTINCT},
     * {@code JAVA_OBJECT}, and named array types.
     * <p>
     * All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>  For a user-defined parameter, the fully-qualified SQL
     * type name of the parameter should also be given, while a {@code REF}
     * parameter requires that the fully-qualified type name of the
     * referenced type be given.  A JDBC driver that does not need the
     * type code and type name information may ignore it.   To be portable,
     * however, applications should always provide these values for
     * user-defined and {@code REF} parameters.
     * <p>
     * Although it is intended for user-defined and {@code REF} parameters,
     * this method may be used to register a parameter of any JDBC type.
     * If the parameter does not have a user-defined or {@code REF} type, the
     * <i>typeName</i> parameter is ignored.
     * <p>
     * <P><B>Note:</B> When reading the value of an out parameter, you
     * must use the getter method whose Java type corresponds to the
     * parameter's registered SQL type.
     * <p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * @param parameterIndex the first parameter is 1, the second is 2,...
     * @param sqlType        the JDBC type code defined by {@code SQLType} to use to
     *                       register the OUT Parameter.
     * @param typeName       the fully-qualified name of an SQL structured type
     * @throws SQLException                    if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if
     *                                         the JDBC driver does not support the specified sqlType
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    public void registerOutParameter(int parameterIndex, SQLType sqlType, String typeName) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType, typeName);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java array of bytes.
     * The driver converts this to an SQL <code>VARBINARY</code> or
     * <code>LONGVARBINARY</code> (depending on the argument's size relative
     * to the driver's limits on <code>VARBINARY</code> values) when it sends
     * it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getBytes
     * @since 1.4
     */
    public void setBytes(String parameterName, byte[] x) throws MintLeafException {
        try {
            callableStatement.setBytes(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


    /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     * <p>
     * <P><B>Note:</B> You must specify the parameter's SQL type.
     *
     * @param parameterName the name of the parameter
     * @param sqlType       the SQL type code defined in <code>java.sql.Types</code>
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    public void setNull(String parameterName, int sqlType) throws MintLeafException {
        try {
            callableStatement.setNull(parameterName, sqlType);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Registers the OUT parameter named
     * <code>parameterName</code> to the JDBC type
     * <code>sqlType</code>.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by <code>sqlType</code> for an OUT
     * parameter determines the Java type that must be used
     * in the <code>get</code> method to read the value of that parameter.
     * <p>
     * If the JDBC type expected to be returned to this output parameter
     * is specific to this particular database, <code>sqlType</code>
     * should be <code>java.sql.Types.OTHER</code>.  The method
     * {@link #getObject} retrieves the value.
     *
     * @param parameterName the name of the parameter
     * @param sqlType       the JDBC type code defined by <code>java.sql.Types</code>.
     *                      If the parameter is of JDBC type <code>NUMERIC</code>
     *                      or <code>DECIMAL</code>, the version of
     *                      <code>registerOutParameter</code> that accepts a scale value
     *                      should be used.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if <code>sqlType</code> is
     *                                         a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     *                                         <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     *                                         <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *                                         <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     *                                         or  <code>STRUCT</code> data type and the JDBC driver does not support
     *                                         this data type or if the JDBC driver does not support
     *                                         this method
     * @see Types
     * @since 1.4
     */
    public void registerOutParameter(String parameterName, int sqlType) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>boolean</code> value.
     * The driver converts this
     * to an SQL <code>BIT</code> or <code>BOOLEAN</code> value when it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getBoolean
     * @since 1.4
     */
    public void setBoolean(String parameterName, boolean x) throws MintLeafException {
        try {
            callableStatement.setBoolean(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Gives the driver a hint as to the direction in which
     * rows will be processed in <code>ResultSet</code>
     * objects created using this <code>Statement</code> object.  The
     * default value is <code>ResultSet.FETCH_FORWARD</code>.
     * <p>
     * Note that this method sets the default fetch direction for
     * result sets generated by this <code>Statement</code> object.
     * Each result set has its own methods for getting and setting
     * its own fetch direction.
     *
     * @param direction the initial direction for processing rows
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed <code>Statement</code>
     *                      or the given direction
     *                      is not one of <code>ResultSet.FETCH_FORWARD</code>,
     *                      <code>ResultSet.FETCH_REVERSE</code>, or <code>ResultSet.FETCH_UNKNOWN</code>
     * @see #getFetchDirection
     * @since 1.2
     */
    public void setFetchDirection(int direction) throws MintLeafException {
        try {
            callableStatement.setFetchDirection(direction);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


    /**
     * Sets the designated parameter to the given Java <code>byte</code> value.
     * The driver converts this
     * to an SQL <code>TINYINT</code> value when it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getByte
     * @since 1.4
     */
    public void setByte(String parameterName, byte x) throws MintLeafException {
        try {
            callableStatement.setByte(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


    /**
     * Registers the OUT parameter in ordinal position
     * {@code parameterIndex} to the JDBC type
     * {@code sqlType}.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by {@code sqlType} for an OUT
     * parameter determines the Java type that must be used
     * in the {@code get} method to read the value of that parameter.
     * <p>
     * If the JDBC type expected to be returned to this output parameter
     * is specific to this particular database, {@code sqlType}
     * may be {@code JDBCType.OTHER} or a {@code SQLType} that is supported by
     * the JDBC driver.  The method
     * {@link #getObject} retrieves the value.
     * <p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @param sqlType        the JDBC type code defined by {@code SQLType} to use to
     *                       register the OUT Parameter.
     *                       If the parameter is of JDBC type {@code JDBCType.NUMERIC}
     *                       or {@code JDBCType.DECIMAL}, the version of
     *                       {@code registerOutParameter} that accepts a scale value
     *                       should be used.
     * @throws SQLException                    if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if
     *                                         the JDBC driver does not support the specified sqlType
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    public void registerOutParameter(int parameterIndex, SQLType sqlType) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the number of seconds the driver will wait for a
     * <code>Statement</code> object to execute to the given number of seconds.
     * By default there is no limit on the amount of time allowed for a running
     * statement to complete. If the limit is exceeded, an
     * <code>SQLTimeoutException</code> is thrown.
     * A JDBC driver must apply this limit to the <code>execute</code>,
     * <code>executeQuery</code> and <code>executeUpdate</code> methods.
     * <p>
     * <strong>Note:</strong> JDBC driver implementations may also apply this
     * limit to {@code ResultSet} methods
     * (consult your driver vendor documentation for details).
     * <p>
     * <strong>Note:</strong> In the case of {@code Statement} batching, it is
     * implementation defined as to whether the time-out is applied to
     * individual SQL commands added via the {@code addBatch} method or to
     * the entire batch of SQL commands invoked by the {@code executeBatch}
     * method (consult your driver vendor documentation for details).
     *
     * @param seconds the new query timeout limit in seconds; zero means
     *                there is no limit
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed <code>Statement</code>
     *                      or the condition {@code seconds >= 0} is not satisfied
     * @see #getQueryTimeout
     */
    public void setQueryTimeout(int seconds) throws MintLeafException {
        try {
            callableStatement.setQueryTimeout(seconds);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>double</code> value.
     * The driver converts this
     * to an SQL <code>DOUBLE</code> value when it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getDouble
     * @since 1.4
     */
    public void setDouble(String parameterName, double x) throws MintLeafException {
        try {
            callableStatement.setDouble(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large binary value is input to a <code>LONGVARBINARY</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.
     * <p>
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterName the name of the parameter
     * @param x             the java input stream which contains the binary parameter value
     * @param length        the number of bytes in the stream
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    public void setBinaryStream(String parameterName, InputStream x, int length) throws MintLeafException {
        try {
            callableStatement.setBinaryStream(parameterName, x, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Registers the designated output parameter.  This version of
     * the method <code>registerOutParameter</code>
     * should be used for a user-named or REF output parameter.  Examples
     * of user-named types include: STRUCT, DISTINCT, JAVA_OBJECT, and
     * named array types.
     * <p>
     * All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * For a user-named parameter the fully-qualified SQL
     * type name of the parameter should also be given, while a REF
     * parameter requires that the fully-qualified type name of the
     * referenced type be given.  A JDBC driver that does not need the
     * type code and type name information may ignore it.   To be portable,
     * however, applications should always provide these values for
     * user-named and REF parameters.
     * <p>
     * Although it is intended for user-named and REF parameters,
     * this method may be used to register a parameter of any JDBC type.
     * If the parameter does not have a user-named or REF type, the
     * typeName parameter is ignored.
     * <p>
     * <P><B>Note:</B> When reading the value of an out parameter, you
     * must use the <code>getXXX</code> method whose Java type XXX corresponds to the
     * parameter's registered SQL type.
     *
     * @param parameterName the name of the parameter
     * @param sqlType       a value from {@link Types}
     * @param typeName      the fully-qualified name of an SQL structured type
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if <code>sqlType</code> is
     *                                         a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     *                                         <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     *                                         <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *                                         <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     *                                         or  <code>STRUCT</code> data type and the JDBC driver does not support
     *                                         this data type or if the JDBC driver does not support
     *                                         this method
     * @see Types
     * @since 1.4
     */
    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType, typeName);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the limit for the maximum number of rows that any
     * <code>ResultSet</code> object  generated by this <code>Statement</code>
     * object can contain to the given number.
     * If the limit is exceeded, the excess
     * rows are silently dropped.
     *
     * @param max the new max rows limit; zero means there is no limit
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed <code>Statement</code>
     *                      or the condition {@code max >= 0} is not satisfied
     * @see #getMaxRows
     */
    public void setMaxRows(int max) throws MintLeafException {
        try {
            callableStatement.setMaxRows(max);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


    /**
     * Sets the designated parameter to the given Java <code>float</code> value.
     * The driver converts this
     * to an SQL <code>FLOAT</code> value when it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getFloat
     * @since 1.4
     */
    public void setFloat(String parameterName, float x) throws MintLeafException {
        try {
            callableStatement.setFloat(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Registers the parameter in ordinal position
     * {@code parameterIndex} to be of JDBC type
     * {@code sqlType}. All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by {@code sqlType} for an OUT
     * parameter determines the Java type that must be used
     * in the {@code get} method to read the value of that parameter.
     * <p>
     * This version of {@code  registerOutParameter} should be
     * used when the parameter is of JDBC type {@code JDBCType.NUMERIC}
     * or {@code JDBCType.DECIMAL}.
     * <p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @param sqlType        the JDBC type code defined by {@code SQLType} to use to
     *                       register the OUT Parameter.
     * @param scale          the desired number of digits to the right of the
     *                       decimal point.  It must be greater than or equal to zero.
     * @throws SQLException                    if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if
     *                                         the JDBC driver does not support the specified sqlType
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    public void registerOutParameter(int parameterIndex, SQLType sqlType, int scale) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType, scale);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>DATE</code> value,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the date
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @param cal           the <code>Calendar</code> object the driver will use
     *                      to construct the date
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getDate
     * @since 1.4
     */
    public void setDate(String parameterName, Date x, Calendar cal) throws MintLeafException {
        try {
            callableStatement.setDate(parameterName, x, cal);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>String</code> object.
     * The driver converts this to a SQL <code>NCHAR</code> or
     * <code>NVARCHAR</code> or <code>LONGNVARCHAR</code>
     *
     * @param parameterName the name of the parameter to be set
     * @param value         the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setNString(String parameterName, String value) throws MintLeafException {
        try {
            callableStatement.setNString(parameterName, value);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Registers the parameter named
     * <code>parameterName</code> to be of JDBC type
     * {@code sqlType}.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by {@code sqlType} for an OUT
     * parameter determines the Java type that must be used
     * in the {@code get} method to read the value of that parameter.
     * <p>
     * This version of {@code  registerOutParameter} should be
     * used when the parameter is of JDBC type {@code JDBCType.NUMERIC}
     * or {@code JDBCType.DECIMAL}.
     * <p>
     * The default implementation will throw {@code SQLFeatureNotSupportedException}
     *
     * @param parameterName the name of the parameter
     * @param sqlType       the JDBC type code defined by {@code SQLType} to use to
     *                      register the OUT Parameter.
     * @param scale         the desired number of digits to the right of the
     *                      decimal point.  It must be greater than or equal to zero.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed {@code CallableStatement}
     * @throws SQLFeatureNotSupportedException if
     *                                         the JDBC driver does not support the specified sqlType
     *                                         or if the JDBC driver does not support
     *                                         this method
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    public void registerOutParameter(String parameterName, SQLType sqlType, int scale) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType, scale);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code> value
     * using the default time zone of the virtual machine that is running
     * the application.
     * The driver converts this
     * to an SQL <code>DATE</code> value when it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #getDate
     * @since 1.4
     */
    public void setDate(String parameterName, Date x) throws MintLeafException {
        try {
            callableStatement.setDate(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Gives the JDBC driver a hint as to the number of rows that should
     * be fetched from the database when more rows are needed for
     * <code>ResultSet</code> objects generated by this <code>Statement</code>.
     * If the value specified is zero, then the hint is ignored.
     * The default value is zero.
     *
     * @param rows the number of rows to fetch
     * @throws SQLException if a database access error occurs,
     *                      this method is called on a closed <code>Statement</code> or the
     *                      condition {@code rows >= 0} is not satisfied.
     * @see #getFetchSize
     * @since 1.2
     */
    public void setFetchSize(int rows) throws MintLeafException {
        try {
            callableStatement.setFetchSize(rows);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.SQLXML</code> object. The driver converts this to an
     * <code>SQL XML</code> value when it sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param xmlObject     a <code>SQLXML</code> object that maps an <code>SQL XML</code> value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs;
     *                                         this method is called on a closed <code>CallableStatement</code> or
     *                                         the <code>java.xml.transform.Result</code>,
     *                                         <code>Writer</code> or <code>OutputStream</code> has not been closed for the <code>SQLXML</code> object
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setSQLXML(String parameterName, SQLXML xmlObject) throws MintLeafException {
        try {
            callableStatement.setSQLXML(parameterName, xmlObject);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object. The
     * <code>Reader</code> reads the data till end-of-file is reached. The
     * driver does the necessary conversion from Java character format to
     * the national character set in the database.
     *
     * @param parameterName the name of the parameter to be set
     * @param value         the parameter value
     * @param length        the number of characters in the parameter data.
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setNCharacterStream(String parameterName, Reader value, long length) throws MintLeafException {
        try {
            callableStatement.setNCharacterStream(parameterName, value, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object. The
     * <code>Reader</code> reads the data till end-of-file is reached. The
     * driver does the necessary conversion from Java character format to
     * the national character set in the database.
     * <p>
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setNCharacterStream</code> which takes a length parameter.
     *
     * @param parameterName the name of the parameter
     * @param value         the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; if a database access error occurs; or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @since 1.6
     */
    public void setNCharacterStream(String parameterName, Reader value) throws MintLeafException {
        try {
            callableStatement.setNCharacterStream(parameterName, value);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large binary value is input to a <code>LONGVARBINARY</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.
     * <p>
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterName the name of the parameter
     * @param x             the java input stream which contains the binary parameter value
     * @param length        the number of bytes in the stream
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setBinaryStream(String parameterName, InputStream x, long length) throws MintLeafException {
        try {
            callableStatement.setBinaryStream(parameterName, x, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Clob</code> object.
     * The driver converts this to an SQL <code>CLOB</code> value when it
     * sends it to the database.
     *
     * @param parameterName the name of the parameter
     * @param x             a <code>Clob</code> object that maps an SQL <code>CLOB</code> value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setClob(String parameterName, Clob x) throws MintLeafException {
        try {
            callableStatement.setClob(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.RowId</code> object. The
     * driver converts this to a SQL <code>ROWID</code> when it sends it to the
     * database.
     *
     * @param parameterName the name of the parameter
     * @param x             the parameter value
     * @throws SQLException                    if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public void setRowId(String parameterName, RowId x) throws MintLeafException {
        try {
            callableStatement.setRowId(parameterName, x);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }
}
