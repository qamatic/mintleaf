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

import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

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
     * Retrieves a <code>ResultSetMetaData</code> object that contains
     * information about the columns of the <code>ResultSet</code> object
     * that will be returned when this <code>PreparedStatement</code> object
     * is executed.
     * <p>
     * Because a <code>PreparedStatement</code> object is precompiled, it is
     * possible to know about the <code>ResultSet</code> object that it will
     * return without having to execute it.  Consequently, it is possible
     * to invoke the method <code>getMetaData</code> on a
     * <code>PreparedStatement</code> object rather than waiting to execute
     * it and then invoking the <code>ResultSet.getMetaData</code> method
     * on the <code>ResultSet</code> object that is returned.
     * <p>
     * <B>NOTE:</B> Using this method may be expensive for some drivers due
     * to the lack of underlying DBMS support.
     *
     * @return the description of a <code>ResultSet</code> object's columns or
     * <code>null</code> if the driver cannot return a
     * <code>ResultSetMetaData</code> object
     * @throws MintLeafException                   if a database access error occurs or
     *                                         this method is called on a closed <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    public ResultSetMetaData getMetaData() throws MintLeafException {
        try {
            return callableStatement.getMetaData();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the number, types and properties of this
     * <code>PreparedStatement</code> object's parameters.
     *
     * @return a <code>ParameterMetaData</code> object that contains information
     * about the number, types and properties for each
     * parameter marker of this <code>PreparedStatement</code> object
     * @throws MintLeafExceptionif a database access error occurs or
     *                      this method is called on a closed <code>PreparedStatement</code>
     * @see ParameterMetaData
     * @since 1.4
     */
    @Override
    public ParameterMetaData getParameterMetaData() throws MintLeafException {
        try {
            return callableStatement.getParameterMetaData();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>ARRAY</code> parameter as an
     * {@link Array} object in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, and
     *                       so on
     * @return the parameter value as an <code>Array</code> object in
     * the Java programming language.  If the value was SQL <code>NULL</code>, the
     * value <code>null</code> is returned.
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    public Array getArray(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getArray(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>ARRAY</code> parameter as an
     * {@link Array} object in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value as an <code>Array</code> object in
     * Java programming language.  If the value was SQL <code>NULL</code>,
     * the value <code>null</code> is returned.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    public Array getArray(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getArray(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>NUMERIC</code> parameter as a
     * <code>java.math.BigDecimal</code> object with as many digits to the
     * right of the decimal point as the value contains.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value in full precision.  If the value is
     * SQL <code>NULL</code>, the result is <code>null</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setBigDecimal
     * @since 1.2
     */
    public BigDecimal getBigDecimal(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getBigDecimal(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>NUMERIC</code> parameter as a
     * <code>java.math.BigDecimal</code> object with <i>scale</i> digits to
     * the right of the decimal point.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @param scale          the number of digits to the right of the decimal point
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setBigDecimal
     * @deprecated use <code>getBigDecimal(int parameterIndex)</code>
     * or <code>getBigDecimal(String parameterName)</code>
     */
    @Deprecated
    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws MintLeafException {
        try {
            return callableStatement.getBigDecimal(parameterIndex, scale);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>NUMERIC</code> parameter as a
     * <code>java.math.BigDecimal</code> object with as many digits to the
     * right of the decimal point as the value contains.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value in full precision.  If the value is
     * SQL <code>NULL</code>, the result is <code>null</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter;  if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setBigDecimal
     * @since 1.4
     */
    public BigDecimal getBigDecimal(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getBigDecimal(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>BLOB</code> parameter as a
     * {@link Blob} object in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, and so on
     * @return the parameter value as a <code>Blob</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>, the value
     * <code>null</code> is returned.
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    public Blob getBlob(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getBlob(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>BLOB</code> parameter as a
     * {@link Blob} object in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value as a <code>Blob</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>,
     * the value <code>null</code> is returned.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    public Blob getBlob(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getBlob(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>BIT</code>
     * or <code>BOOLEAN</code> parameter as a
     * <code>boolean</code> in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     * the result is <code>false</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setBoolean
     */
    public boolean getBoolean(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getBoolean(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>BIT</code> or <code>BOOLEAN</code>
     * parameter as a
     * <code>boolean</code> in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>false</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setBoolean
     * @since 1.4
     */
    public boolean getBoolean(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getBoolean(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>TINYINT</code> parameter
     * as a <code>byte</code> in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setByte
     */
    public byte getByte(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getByte(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>TINYINT</code> parameter as a <code>byte</code>
     * in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setByte
     * @since 1.4
     */
    public byte getByte(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getByte(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>BINARY</code> or
     * <code>VARBINARY</code> parameter as an array of <code>byte</code>
     * values in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setBytes
     */
    public byte[] getBytes(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getBytes(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>BINARY</code> or <code>VARBINARY</code>
     * parameter as an array of <code>byte</code> values in the Java
     * programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result is
     * <code>null</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setBytes
     * @since 1.4
     */
    public byte[] getBytes(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getBytes(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated parameter as a
     * <code>java.io.Reader</code> object in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @return a <code>java.io.Reader</code> object that contains the parameter
     * value; if the value is SQL <code>NULL</code>, the value returned is
     * <code>null</code> in the Java programming language.
     * @throws MintLeafExceptionif the parameterIndex is not valid; if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @since 1.6
     */
    public Reader getCharacterStream(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getCharacterStream(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated parameter as a
     * <code>java.io.Reader</code> object in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return a <code>java.io.Reader</code> object that contains the parameter
     * value; if the value is SQL <code>NULL</code>, the value returned is
     * <code>null</code> in the Java programming language
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public Reader getCharacterStream(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getCharacterStream(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>CLOB</code> parameter as a
     * <code>java.sql.Clob</code> object in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, and
     *                       so on
     * @return the parameter value as a <code>Clob</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>, the
     * value <code>null</code> is returned.
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    public Clob getClob(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getClob(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>CLOB</code> parameter as a
     * <code>java.sql.Clob</code> object in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value as a <code>Clob</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>,
     * the value <code>null</code> is returned.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    public Clob getClob(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getClob(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>DATE</code> parameter as a
     * <code>java.sql.Date</code> object.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setDate
     */
    public Date getDate(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getDate(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>DATE</code> parameter as a
     * <code>java.sql.Date</code> object, using
     * the given <code>Calendar</code> object
     * to construct the date.
     * With a <code>Calendar</code> object, the driver
     * can calculate the date taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @param cal            the <code>Calendar</code> object the driver will use
     *                       to construct the date
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setDate
     * @since 1.2
     */
    public Date getDate(int parameterIndex, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getDate(parameterIndex, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>DATE</code> parameter as a
     * <code>java.sql.Date</code> object.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setDate
     * @since 1.4
     */
    public Date getDate(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getDate(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>DATE</code> parameter as a
     * <code>java.sql.Date</code> object, using
     * the given <code>Calendar</code> object
     * to construct the date.
     * With a <code>Calendar</code> object, the driver
     * can calculate the date taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * @param parameterName the name of the parameter
     * @param cal           the <code>Calendar</code> object the driver will use
     *                      to construct the date
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     * the result is <code>null</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setDate
     * @since 1.4
     */
    public Date getDate(String parameterName, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getDate(parameterName, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>DOUBLE</code> parameter as a <code>double</code>
     * in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setDouble
     */
    public double getDouble(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getDouble(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>DOUBLE</code> parameter as a <code>double</code>
     * in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     * the result is <code>0</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setDouble
     * @since 1.4
     */
    public double getDouble(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getDouble(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>FLOAT</code> parameter
     * as a <code>float</code> in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setFloat
     */
    public float getFloat(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getFloat(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>FLOAT</code> parameter as a <code>float</code>
     * in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     * the result is <code>0</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setFloat
     * @since 1.4
     */
    public float getFloat(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getFloat(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>INTEGER</code> parameter
     * as an <code>int</code> in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setInt
     */
    public int getInt(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getInt(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>INTEGER</code> parameter as an <code>int</code>
     * in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     * the result is <code>0</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setInt
     * @since 1.4
     */
    public int getInt(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getInt(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>BIGINT</code> parameter
     * as a <code>long</code> in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setLong
     */
    public long getLong(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getLong(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>BIGINT</code> parameter as a <code>long</code>
     * in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>,
     * the result is <code>0</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setLong
     * @since 1.4
     */
    public long getLong(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getLong(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated parameter as a
     * <code>java.io.Reader</code> object in the Java programming language.
     * It is intended for use when
     * accessing  <code>NCHAR</code>,<code>NVARCHAR</code>
     * and <code>LONGNVARCHAR</code> parameters.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @return a <code>java.io.Reader</code> object that contains the parameter
     * value; if the value is SQL <code>NULL</code>, the value returned is
     * <code>null</code> in the Java programming language.
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public Reader getNCharacterStream(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getNCharacterStream(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated parameter as a
     * <code>java.io.Reader</code> object in the Java programming language.
     * It is intended for use when
     * accessing  <code>NCHAR</code>,<code>NVARCHAR</code>
     * and <code>LONGNVARCHAR</code> parameters.
     *
     * @param parameterName the name of the parameter
     * @return a <code>java.io.Reader</code> object that contains the parameter
     * value; if the value is SQL <code>NULL</code>, the value returned is
     * <code>null</code> in the Java programming language
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public Reader getNCharacterStream(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getNCharacterStream(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>NCLOB</code> parameter as a
     * <code>java.sql.NClob</code> object in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, and
     *                       so on
     * @return the parameter value as a <code>NClob</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>, the
     * value <code>null</code> is returned.
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public NClob getNClob(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getNClob(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>NCLOB</code> parameter as a
     * <code>java.sql.NClob</code> object in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value as a <code>NClob</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>,
     * the value <code>null</code> is returned.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if the driver does not support national
     *                                         character sets;  if the driver can detect that a data conversion
     *                                         error could occur; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public NClob getNClob(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getNClob(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated <code>NCHAR</code>,
     * <code>NVARCHAR</code>
     * or <code>LONGNVARCHAR</code> parameter as
     * a <code>String</code> in the Java programming language.
     * <p>
     * For the fixed-length type JDBC <code>NCHAR</code>,
     * the <code>String</code> object
     * returned has exactly the same value the SQL
     * <code>NCHAR</code> value had in the
     * database, including any padding added by the database.
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @return a <code>String</code> object that maps an
     * <code>NCHAR</code>, <code>NVARCHAR</code> or <code>LONGNVARCHAR</code> value
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setNString
     * @since 1.6
     */
    public String getNString(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getNString(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated <code>NCHAR</code>,
     * <code>NVARCHAR</code>
     * or <code>LONGNVARCHAR</code> parameter as
     * a <code>String</code> in the Java programming language.
     * <p>
     * For the fixed-length type JDBC <code>NCHAR</code>,
     * the <code>String</code> object
     * returned has exactly the same value the SQL
     * <code>NCHAR</code> value had in the
     * database, including any padding added by the database.
     *
     * @param parameterName the name of the parameter
     * @return a <code>String</code> object that maps an
     * <code>NCHAR</code>, <code>NVARCHAR</code> or <code>LONGNVARCHAR</code> value
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setNString
     * @since 1.6
     */
    public String getNString(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getNString(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated parameter as an <code>Object</code>
     * in the Java programming language. If the value is an SQL <code>NULL</code>,
     * the driver returns a Java <code>null</code>.
     * <p>
     * This method returns a Java object whose type corresponds to the JDBC
     * type that was registered for this parameter using the method
     * <code>registerOutParameter</code>.  By registering the target JDBC
     * type as <code>java.sql.Types.OTHER</code>, this method can be used
     * to read database-specific abstract data types.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return A <code>java.lang.Object</code> holding the OUT parameter value
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see Types
     * @see #setObject
     */
    public Object getObject(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Returns an object representing the value of OUT parameter
     * <code>parameterIndex</code> and uses <code>map</code> for the custom
     * mapping of the parameter value.
     * <p>
     * This method returns a Java object whose type corresponds to the
     * JDBC type that was registered for this parameter using the method
     * <code>registerOutParameter</code>.  By registering the target
     * JDBC type as <code>java.sql.Types.OTHER</code>, this method can
     * be used to read database-specific abstract data types.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, and so on
     * @param map            the mapping from SQL type names to Java classes
     * @return a <code>java.lang.Object</code> holding the OUT parameter value
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setObject
     * @since 1.2
     */
    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterIndex, map);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * <p>Returns an object representing the value of OUT parameter
     * {@code parameterIndex} and will convert from the
     * SQL type of the parameter to the requested Java data type, if the
     * conversion is supported. If the conversion is not
     * supported or null is specified for the type, a
     * <code>SQLException</code> is thrown.
     * <p>
     * At a minimum, an implementation must support the conversions defined in
     * Appendix B, Table B-3 and conversion of appropriate user defined SQL
     * types to a Java type which implements {@code SQLData}, or {@code Struct}.
     * Additional conversions may be supported and are vendor defined.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, and so on
     * @param type           Class representing the Java data type to convert the
     *                       designated parameter to.
     * @return an instance of {@code type} holding the OUT parameter value
     * @throws MintLeafException                   if conversion is not supported, type is null or
     *                                         another error occurs. The getCause() method of the
     *                                         exception may provide a more detailed exception, for example, if
     *                                         a conversion error occurs
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.7
     */
    public <T> T getObject(int parameterIndex, Class<T> type) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterIndex, type);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a parameter as an <code>Object</code> in the Java
     * programming language. If the value is an SQL <code>NULL</code>, the
     * driver returns a Java <code>null</code>.
     * <p>
     * This method returns a Java object whose type corresponds to the JDBC
     * type that was registered for this parameter using the method
     * <code>registerOutParameter</code>.  By registering the target JDBC
     * type as <code>java.sql.Types.OTHER</code>, this method can be used
     * to read database-specific abstract data types.
     *
     * @param parameterName the name of the parameter
     * @return A <code>java.lang.Object</code> holding the OUT parameter value.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see Types
     * @see #setObject
     * @since 1.4
     */
    public Object getObject(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Returns an object representing the value of OUT parameter
     * <code>parameterName</code> and uses <code>map</code> for the custom
     * mapping of the parameter value.
     * <p>
     * This method returns a Java object whose type corresponds to the
     * JDBC type that was registered for this parameter using the method
     * <code>registerOutParameter</code>.  By registering the target
     * JDBC type as <code>java.sql.Types.OTHER</code>, this method can
     * be used to read database-specific abstract data types.
     *
     * @param parameterName the name of the parameter
     * @param map           the mapping from SQL type names to Java classes
     * @return a <code>java.lang.Object</code> holding the OUT parameter value
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setObject
     * @since 1.4
     */
    public Object getObject(String parameterName, Map<String, Class<?>> map) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterName, map);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * <p>Returns an object representing the value of OUT parameter
     * {@code parameterName} and will convert from the
     * SQL type of the parameter to the requested Java data type, if the
     * conversion is supported. If the conversion is not
     * supported  or null is specified for the type, a
     * <code>SQLException</code> is thrown.
     * <p>
     * At a minimum, an implementation must support the conversions defined in
     * Appendix B, Table B-3 and conversion of appropriate user defined SQL
     * types to a Java type which implements {@code SQLData}, or {@code Struct}.
     * Additional conversions may be supported and are vendor defined.
     *
     * @param parameterName the name of the parameter
     * @param type          Class representing the Java data type to convert
     *                      the designated parameter to.
     * @return an instance of {@code type} holding the OUT parameter
     * value
     * @throws MintLeafException                   if conversion is not supported, type is null or
     *                                         another error occurs. The getCause() method of the
     *                                         exception may provide a more detailed exception, for example, if
     *                                         a conversion error occurs
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.7
     */
    public <T> T getObject(String parameterName, Class<T> type) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterName, type);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>REF(&lt;structured-type&gt;)</code>
     * parameter as a {@link Ref} object in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value as a <code>Ref</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>, the value
     * <code>null</code> is returned.
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.2
     */
    public Ref getRef(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getRef(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>REF(&lt;structured-type&gt;)</code>
     * parameter as a {@link Ref} object in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value as a <code>Ref</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>,
     * the value <code>null</code> is returned.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.4
     */
    public Ref getRef(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getRef(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>ROWID</code> parameter as a
     * <code>java.sql.RowId</code> object.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,...
     * @return a <code>RowId</code> object that represents the JDBC <code>ROWID</code>
     * value is used as the designated parameter. If the parameter contains
     * a SQL <code>NULL</code>, then a <code>null</code> value is returned.
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public RowId getRowId(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getRowId(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>ROWID</code> parameter as a
     * <code>java.sql.RowId</code> object.
     *
     * @param parameterName the name of the parameter
     * @return a <code>RowId</code> object that represents the JDBC <code>ROWID</code>
     * value is used as the designated parameter. If the parameter contains
     * a SQL <code>NULL</code>, then a <code>null</code> value is returned.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public RowId getRowId(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getRowId(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>SMALLINT</code> parameter
     * as a <code>short</code> in the Java programming language.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setShort
     */
    public short getShort(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getShort(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>SMALLINT</code> parameter as a <code>short</code>
     * in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>0</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setShort
     * @since 1.4
     */
    public short getShort(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getShort(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated <code>SQL XML</code> parameter as a
     * <code>java.sql.SQLXML</code> object in the Java programming language.
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @return a <code>SQLXML</code> object that maps an <code>SQL XML</code> value
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public SQLXML getSQLXML(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getSQLXML(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated <code>SQL XML</code> parameter as a
     * <code>java.sql.SQLXML</code> object in the Java programming language.
     *
     * @param parameterName the name of the parameter
     * @return a <code>SQLXML</code> object that maps an <code>SQL XML</code> value
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.6
     */
    public SQLXML getSQLXML(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getSQLXML(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>CHAR</code>,
     * <code>VARCHAR</code>, or <code>LONGVARCHAR</code> parameter as a
     * <code>String</code> in the Java programming language.
     * <p>
     * For the fixed-length type JDBC <code>CHAR</code>,
     * the <code>String</code> object
     * returned has exactly the same value the SQL
     * <code>CHAR</code> value had in the
     * database, including any padding added by the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value. If the value is SQL <code>NULL</code>,
     * the result
     * is <code>null</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setString
     */
    public String getString(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getString(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>CHAR</code>, <code>VARCHAR</code>,
     * or <code>LONGVARCHAR</code> parameter as a <code>String</code> in
     * the Java programming language.
     * <p>
     * For the fixed-length type JDBC <code>CHAR</code>,
     * the <code>String</code> object
     * returned has exactly the same value the SQL
     * <code>CHAR</code> value had in the
     * database, including any padding added by the database.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value. If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setString
     * @since 1.4
     */
    public String getString(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getString(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>TIME</code> parameter as a
     * <code>java.sql.Time</code> object.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setTime
     */
    public Time getTime(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getTime(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>TIME</code> parameter as a
     * <code>java.sql.Time</code> object, using
     * the given <code>Calendar</code> object
     * to construct the time.
     * With a <code>Calendar</code> object, the driver
     * can calculate the time taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @param cal            the <code>Calendar</code> object the driver will use
     *                       to construct the time
     * @return the parameter value; if the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setTime
     * @since 1.2
     */
    public Time getTime(int parameterIndex, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getTime(parameterIndex, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>TIME</code> parameter as a
     * <code>java.sql.Time</code> object.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setTime
     * @since 1.4
     */
    public Time getTime(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getTime(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>TIME</code> parameter as a
     * <code>java.sql.Time</code> object, using
     * the given <code>Calendar</code> object
     * to construct the time.
     * With a <code>Calendar</code> object, the driver
     * can calculate the time taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * @param parameterName the name of the parameter
     * @param cal           the <code>Calendar</code> object the driver will use
     *                      to construct the time
     * @return the parameter value; if the value is SQL <code>NULL</code>, the result is
     * <code>null</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setTime
     * @since 1.4
     */
    public Time getTime(String parameterName, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getTime(parameterName, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>TIMESTAMP</code> parameter as a
     * <code>java.sql.Timestamp</code> object.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setTimestamp
     */
    public Timestamp getTimestamp(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getTimestamp(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>TIMESTAMP</code> parameter as a
     * <code>java.sql.Timestamp</code> object, using
     * the given <code>Calendar</code> object to construct
     * the <code>Timestamp</code> object.
     * With a <code>Calendar</code> object, the driver
     * can calculate the timestamp taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     *                       and so on
     * @param cal            the <code>Calendar</code> object the driver will use
     *                       to construct the timestamp
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafExceptionif the parameterIndex is not valid;
     *                      if a database access error occurs or
     *                      this method is called on a closed <code>CallableStatement</code>
     * @see #setTimestamp
     * @since 1.2
     */
    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getTimestamp(parameterIndex, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>TIMESTAMP</code> parameter as a
     * <code>java.sql.Timestamp</code> object.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result
     * is <code>null</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setTimestamp
     * @since 1.4
     */
    public Timestamp getTimestamp(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getTimestamp(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>TIMESTAMP</code> parameter as a
     * <code>java.sql.Timestamp</code> object, using
     * the given <code>Calendar</code> object to construct
     * the <code>Timestamp</code> object.
     * With a <code>Calendar</code> object, the driver
     * can calculate the timestamp taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * @param parameterName the name of the parameter
     * @param cal           the <code>Calendar</code> object the driver will use
     *                      to construct the timestamp
     * @return the parameter value.  If the value is SQL <code>NULL</code>, the result is
     * <code>null</code>.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs or
     *                                         this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setTimestamp
     * @since 1.4
     */
    public Timestamp getTimestamp(String parameterName, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getTimestamp(parameterName, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of the designated JDBC <code>DATALINK</code> parameter as a
     * <code>java.net.URL</code> object.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,...
     * @return a <code>java.net.URL</code> object that represents the
     * JDBC <code>DATALINK</code> value used as the designated
     * parameter
     * @throws MintLeafException                   if the parameterIndex is not valid;
     *                                         if a database access error occurs,
     *                                         this method is called on a closed <code>CallableStatement</code>,
     *                                         or if the URL being returned is
     *                                         not a valid URL on the Java platform
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setURL
     * @since 1.4
     */
    public URL getURL(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getURL(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the value of a JDBC <code>DATALINK</code> parameter as a
     * <code>java.net.URL</code> object.
     *
     * @param parameterName the name of the parameter
     * @return the parameter value as a <code>java.net.URL</code> object in the
     * Java programming language.  If the value was SQL <code>NULL</code>, the
     * value <code>null</code> is returned.
     * @throws MintLeafException                   if parameterName does not correspond to a named
     *                                         parameter; if a database access error occurs,
     *                                         this method is called on a closed <code>CallableStatement</code>,
     *                                         or if there is a problem with the URL
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setURL
     * @since 1.4
     */
    public URL getURL(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getURL(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
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
     * @throws MintLeafException                   if the parameterIndex is not valid;
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
     * @throws MintLeafException                   if the parameterIndex is not valid;
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
     * @throws MintLeafException                   if the parameterIndex is not valid;
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
     * @throws MintLeafException                   if the parameterIndex is not valid;
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
     * @throws MintLeafException                   if the parameterIndex is not valid;
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
     * @throws MintLeafException                   if the parameterIndex is not valid;
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
     * @throws MintLeafException                   if parameterName does not correspond to a named
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
     * @throws MintLeafException                   if parameterName does not correspond to a named
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
     * @throws MintLeafException                   if parameterName does not correspond to a named
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
     * @throws MintLeafException                   if parameterName does not correspond to a named
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
     * @throws MintLeafException                   if parameterName does not correspond to a named
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
     * @throws MintLeafException                   if parameterName does not correspond to a named
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

}
