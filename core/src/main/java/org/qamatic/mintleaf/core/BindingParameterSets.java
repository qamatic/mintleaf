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
import org.qamatic.mintleaf.ParameterGets;
import org.qamatic.mintleaf.ParameterSets;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

/**
 * Created by qamatic on 2/20/16.
 */
public class BindingParameterSets implements ParameterSets, ParameterGets {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(BindingParameterSets.class);
    private Statement statement;
    private boolean batch = false;

    public BindingParameterSets(Statement preparedStatement) {
        this.statement = preparedStatement;
    }

    private PreparedStatement getPreparedStatement(){
        return (PreparedStatement) this.statement;
    }
    
    /**
     * Sets the designated parameter to a <code>InputStream</code> object.  The inputstream must contain  the number
     * of characters specified by length otherwise a <code>MintLeafException</code> will be
     * generated when the <code>PreparedStatement</code> is executed.
     * This method differs from the <code>setBinaryStream (int, InputStream, int)</code>
     * method because it informs the driver that the parameter value should be
     * sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used,
     * the driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
     *
     * @param parameterIndex index of the first parameter is 1,
     *                       the second is 2, ...
     * @param inputStream    An object that contains the data to set the parameter
     *                       value to.
     * @param length         the number of bytes in the parameter data.
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs;
     *                           this method is called on a closed <code>PreparedStatement</code>;
     *                           if the length specified
     *                           is less than zero or if the number of bytes in the inputstream does not match
     *                           the specified length.
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws MintLeafException {
        try {

             getPreparedStatement().setBlob(parameterIndex, inputStream, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Blob</code> object.
     * The driver converts this to an SQL <code>BLOB</code> value when it
     * sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              a <code>Blob</code> object that maps an SQL <code>BLOB</code> value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.2
     */
    @Override
    public void setBlob(int parameterIndex, Blob x) throws MintLeafException {
        try {
            getPreparedStatement().setBlob(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets escape processing on or off.
     * If escape scanning is on (the default), the driver will do
     * escape substitution before sending the SQL statement to the database.
     * <p>
     * The {@code Connection } catch (SQLException e) {             throw new MintLeafException(e);         }} and {@code DataSource } catch (SQLException e) {             throw new MintLeafException(e);         }} property
     * {@code escapeProcessing } catch (SQLException e) {             throw new MintLeafException(e);         }} may be used to change the default escape processing
     * behavior.  A value of true (the default) enables escape Processing for
     * all {@code Statement } catch (SQLException e) {             throw new MintLeafException(e);         }} objects. A value of false disables escape processing
     * for all {@code Statement } catch (SQLException e) {             throw new MintLeafException(e);         }} objects.  The {@code setEscapeProcessing } catch (SQLException e) {             throw new MintLeafException(e);         }}
     * method may be used to specify the escape processing behavior for an
     * individual {@code Statement } catch (SQLException e) {             throw new MintLeafException(e);         }} object.
     * <p>
     * Note: Since prepared statements have usually been parsed prior
     * to making this call, disabling escape processing for
     * <code>PreparedStatements</code> objects will have no effect.
     *
     * @param enable <code>true</code> to enable escape processing;
     *               <code>false</code> to disable it
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     */
    @Override
    public void setEscapeProcessing(boolean enable) throws MintLeafException {
        try {
            getPreparedStatement().setEscapeProcessing(enable);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.net.URL</code> value.
     * The driver converts this to an SQL <code>DATALINK</code> value
     * when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the <code>java.net.URL</code> object to be set
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.4
     */
    @Override
    public void setURL(int parameterIndex, URL x) throws MintLeafException {
        try {
            getPreparedStatement().setURL(parameterIndex, x);
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
     * <B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setNCharacterStream</code> which takes a length parameter.
     *
     * @param parameterIndex of the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if the driver does not support national
     *                           character sets;  if the driver can detect that a data conversion
     *                           error could occur; if a database access error occurs; or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws MintLeafException {
        try {
            getPreparedStatement().setNCharacterStream(parameterIndex, value);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java array of bytes.  The driver converts
     * this to an SQL <code>VARBINARY</code> or <code>LONGVARBINARY</code>
     * (depending on the argument's size relative to the driver's limits on
     * <code>VARBINARY</code> values) when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setBytes(int parameterIndex, byte[] x) throws MintLeafException {
        try {
            getPreparedStatement().setBytes(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given input stream, which
     * will have the specified number of bytes.
     * <p>
     * When a very large Unicode value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the
     * stream as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from Unicode to the database char format.
     * <p>
     * The byte format of the Unicode stream must be a Java UTF-8, as defined in the
     * Java Virtual Machine Specification.
     * <p>
     * <B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              a <code>java.io.InputStream</code> object that contains the
     *                       Unicode parameter value
     * @param length         the number of bytes in the stream
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support
     *                           this method
     * @deprecated Use {@code setCharacterStream } catch (SQLException e) {             throw new MintLeafException(e);         }}
     */
    @Override
    @Deprecated
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws MintLeafException {
        try {
            getPreparedStatement().setUnicodeStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Clears the current parameter values immediately.
     * In general, parameter values remain in force for repeated use of a
     * statement. Setting a parameter value automatically clears its
     * previous value.  However, in some cases it is useful to immediately
     * release the resources used by the current parameter values; this can
     * be done by calling the method <code>clearParameters</code>.
     *
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void clearParameters() throws MintLeafException {
        try {
            getPreparedStatement().clearParameters();
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
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @see ParameterMetaData
     * @since 1.4
     */
    @Override
    public ParameterMetaData getParameterMetaData() throws MintLeafException {
        try {
            return getPreparedStatement().getParameterMetaData();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>byte</code> value.
     * The driver converts this
     * to an SQL <code>TINYINT</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setByte(int parameterIndex, byte x) throws MintLeafException {
        try {
            getPreparedStatement().setByte(parameterIndex, x);
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
     * <B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param reader         the <code>java.io.Reader</code> object that contains the
     *                       Unicode data
     * @param length         the number of characters in the stream
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @since 1.6
     */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws MintLeafException {
        try {
            getPreparedStatement().setCharacterStream(parameterIndex, reader, length);
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
     * data should be sent to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
     * <p>
     * <B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setClob</code> which takes a length parameter.
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader         An object that contains the data to set the parameter value to.
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs; this method is called on
     *                           a closed <code>PreparedStatement</code>or if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setClob(int parameterIndex, Reader reader) throws MintLeafException {
        try {
            getPreparedStatement().setClob(parameterIndex, reader);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to a <code>java.sql.NClob</code> object. The driver converts this to a
     * SQL <code>NCLOB</code> value when it sends it to the database.
     *
     * @param parameterIndex of the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if the driver does not support national
     *                           character sets;  if the driver can detect that a data conversion
     *                           error could occur; if a database access error occurs; or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setNClob(int parameterIndex, NClob value) throws MintLeafException {
        try {
            getPreparedStatement().setNClob(parameterIndex, value);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.RowId</code> object. The
     * driver converts this to a SQL <code>ROWID</code> value when it sends it
     * to the database
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setRowId(int parameterIndex, RowId x) throws MintLeafException {
        try {
            getPreparedStatement().setRowId(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>long</code> value.
     * The driver converts this
     * to an SQL <code>BIGINT</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setLong(int parameterIndex, long x) throws MintLeafException {
        try {
            getPreparedStatement().setLong(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given
     * <code>REF(&lt;structured-type&gt;)</code> value.
     * The driver converts this to an SQL <code>REF</code> value when it
     * sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              an SQL <code>REF</code> value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.2
     */
    @Override
    public void setRef(int parameterIndex, Ref x) throws MintLeafException {
        try {
            getPreparedStatement().setRef(parameterIndex, x);
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
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws MintLeafException {
        try {
            getPreparedStatement().setTimestamp(parameterIndex, x);
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
     * <B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the Java input stream that contains the ASCII parameter value
     * @param length         the number of bytes in the stream
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @since 1.6
     */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws MintLeafException {
        try {
            getPreparedStatement().setAsciiStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large binary value is input to a <code>LONGVARBINARY</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the
     * stream as needed until end-of-file is reached.
     * <p>
     * <B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the java input stream which contains the binary parameter value
     * @param length         the number of bytes in the stream
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws MintLeafException {
        try {
            getPreparedStatement().setBinaryStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.SQLXML</code> object.
     * The driver converts this to an
     * SQL <code>XML</code> value when it sends it to the database.
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param xmlObject      a <code>SQLXML</code> object that maps an SQL <code>XML</code> value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs;
     *                           this method is called on a closed <code>PreparedStatement</code>
     *                           or the <code>java.xml.transform.Result</code>,
     *                           <code>Writer</code> or <code>OutputStream</code> has not been closed for
     *                           the <code>SQLXML</code> object
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws MintLeafException {
        try {
            getPreparedStatement().setSQLXML(parameterIndex, xmlObject);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>int</code> value.
     * The driver converts this
     * to an SQL <code>INTEGER</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setInt(int parameterIndex, int x) throws MintLeafException {
        try {
            getPreparedStatement().setInt(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>boolean</code> value.
     * The driver converts this
     * to an SQL <code>BIT</code> or <code>BOOLEAN</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement;
     *                           if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setBoolean(int parameterIndex, boolean x) throws MintLeafException {
        try {
            getPreparedStatement().setBoolean(parameterIndex, x);
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
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setString(int parameterIndex, String x) throws MintLeafException {
        try {
            getPreparedStatement().setString(parameterIndex, x);
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
     * <B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setBinaryStream</code> which takes a length parameter.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the java input stream which contains the binary parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws MintLeafException {
        try {
            getPreparedStatement().setBinaryStream(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     * <p>
     * <B>Note:</B> You must specify the parameter's SQL type.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param sqlType        the SQL type code defined in <code>java.sql.Types</code>
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if <code>sqlType</code> is
     *                           a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     *                           <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     *                           <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *                           <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     *                           or  <code>STRUCT</code> data type and the JDBC driver does not support
     *                           this data type
     */
    @Override
    public void setNull(int parameterIndex, int sqlType) throws MintLeafException {
        try {
            getPreparedStatement().setNull(parameterIndex, sqlType);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>double</code> value.
     * The driver converts this
     * to an SQL <code>DOUBLE</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setDouble(int parameterIndex, double x) throws MintLeafException {
        try {
            getPreparedStatement().setDouble(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Array</code> object.
     * The driver converts this to an SQL <code>ARRAY</code> value when it
     * sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              an <code>Array</code> object that maps an SQL <code>ARRAY</code> value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.2
     */
    @Override
    public void setArray(int parameterIndex, Array x) throws MintLeafException {
        try {
            getPreparedStatement().setArray(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>DATE</code> value,
     * which the driver then sends to the database.  With
     * a <code>Calendar</code> object, the driver can calculate the date
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @param cal            the <code>Calendar</code> object the driver will use
     *                       to construct the date
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @since 1.2
     */
    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws MintLeafException {
        try {
            getPreparedStatement().setDate(parameterIndex, x, cal);
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
     * <B>Note:</B> To be portable, applications must give the
     * SQL type code and the fully-qualified SQL type name when specifying
     * a NULL user-defined or REF parameter.  In the case of a user-defined type
     * the name is the type name of the parameter itself.  For a REF
     * parameter, the name is the type name of the referenced type.  If
     * a JDBC driver does not need the type code or type name information,
     * it may ignore it.
     * <p>
     * Although it is intended for user-defined and Ref parameters,
     * this method may be used to set a null parameter of any JDBC type.
     * If the parameter does not have a user-defined or REF type, the given
     * typeName is ignored.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param sqlType        a value from <code>java.sql.Types</code>
     * @param typeName       the fully-qualified name of an SQL user-defined type;
     *                       ignored if the parameter is not a user-defined type or REF
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if <code>sqlType</code> is
     *                           a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     *                           <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     *                           <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *                           <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     *                           or  <code>STRUCT</code> data type and the JDBC driver does not support
     *                           this data type or if the JDBC driver does not support this method
     * @since 1.2
     */
    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws MintLeafException {
        try {
            getPreparedStatement().setNull(parameterIndex, sqlType, typeName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the value of the designated parameter with the given object.
     * <p>
     * This method is similar to {@link #setObject(int parameterIndex,
     * Object x, SQLType targetSqlType, int scaleOrLength) } catch (SQLException e) {             throw new MintLeafException(e);         }},
     * except that it assumes a scale of zero.
     * <p>
     * The default implementation will throw {@code MintLeafException } catch (SQLException e) {             throw new MintLeafException(e);         }}
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the object containing the input parameter value
     * @param targetSqlType  the SQL type to be sent to the database
     * @throws MintLeafException if parameterIndex does not correspond to a
     *                           parameter marker in the SQL statement; if a database access error occurs
     *                           or this method is called on a closed {@code PreparedStatement } catch (SQLException e) {             throw new MintLeafException(e);         }}
     * @throws MintLeafException if
     *                           the JDBC driver does not support the specified targetSqlType
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws MintLeafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType);
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
     * <B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the Java input stream that contains the ASCII parameter value
     * @param length         the number of bytes in the stream
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws MintLeafException {
        try {
            getPreparedStatement().setAsciiStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>short</code> value.
     * The driver converts this
     * to an SQL <code>SMALLINT</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setShort(int parameterIndex, short x) throws MintLeafException {
        try {
            getPreparedStatement().setShort(parameterIndex, x);
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
     * data should be sent to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     * <B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setNClob</code> which takes a length parameter.
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader         An object that contains the data to set the parameter value to.
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement;
     *                           if the driver does not support national character sets;
     *                           if the driver can detect that a data conversion
     *                           error could occur;  if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setNClob(int parameterIndex, Reader reader) throws MintLeafException {
        try {
            getPreparedStatement().setNClob(parameterIndex, reader);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>TIME</code> value,
     * which the driver then sends to the database.  With
     * a <code>Calendar</code> object, the driver can calculate the time
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @param cal            the <code>Calendar</code> object the driver will use
     *                       to construct the time
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @since 1.2
     */
    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws MintLeafException {
        try {
            getPreparedStatement().setTime(parameterIndex, x, cal);
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
     * <B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param reader         the <code>java.io.Reader</code> object that contains the
     *                       Unicode data
     * @param length         the number of characters in the stream
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @since 1.2
     */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws MintLeafException {
        try {
            getPreparedStatement().setCharacterStream(parameterIndex, reader, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the value of the designated parameter with the given object.
     * <p>
     * If the second argument is an {@code InputStream } catch (SQLException e) {             throw new MintLeafException(e);         }} then the stream
     * must contain the number of bytes specified by scaleOrLength.
     * If the second argument is a {@code Reader } catch (SQLException e) {             throw new MintLeafException(e);         }} then the reader must
     * contain the number of characters specified by scaleOrLength. If these
     * conditions are not true the driver will generate a
     * {@code MintLeafException } catch (SQLException e) {             throw new MintLeafException(e);         }} when the prepared statement is executed.
     * <p>
     * The given Java object will be converted to the given targetSqlType
     * before being sent to the database.
     * <p>
     * If the object has a custom mapping (is of a class implementing the
     * interface {@code SQLData } catch (SQLException e) {             throw new MintLeafException(e);         }}),
     * the JDBC driver should call the method {@code SQLData.writeSQL } catch (SQLException e) {             throw new MintLeafException(e);         }} to
     * write it to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * {@code Ref } catch (SQLException e) {             throw new MintLeafException(e);         }}, {@code Blob } catch (SQLException e) {             throw new MintLeafException(e);         }}, {@code Clob } catch (SQLException e) {             throw new MintLeafException(e);         }},  {@code NClob } catch (SQLException e) {             throw new MintLeafException(e);         }},
     * {@code Struct } catch (SQLException e) {             throw new MintLeafException(e);         }}, {@code java.net.URL } catch (SQLException e) {             throw new MintLeafException(e);         }},
     * or {@code Array } catch (SQLException e) {             throw new MintLeafException(e);         }}, the driver should pass it to the database as a
     * value of the corresponding SQL type.
     * <p>
     * Note that this method may be used to pass database-specific
     * abstract data types.
     * <p>
     * The default implementation will throw {@code MintLeafException } catch (SQLException e) {             throw new MintLeafException(e);         }}
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the object containing the input parameter value
     * @param targetSqlType  the SQL type to be sent to the database. The
     *                       scale argument may further qualify this type.
     * @param scaleOrLength  for {@code java.sql.JDBCType.DECIMAL } catch (SQLException e) {             throw new MintLeafException(e);         }}
     *                       or {@code java.sql.JDBCType.NUMERIC types } catch (SQLException e) {             throw new MintLeafException(e);         }},
     *                       this is the number of digits after the decimal point. For
     *                       Java Object types {@code InputStream } catch (SQLException e) {             throw new MintLeafException(e);         }} and {@code Reader } catch (SQLException e) {             throw new MintLeafException(e);         }},
     *                       this is the length
     *                       of the data in the stream or reader.  For all other types,
     *                       this value will be ignored.
     * @throws MintLeafException if parameterIndex does not correspond to a
     *                           parameter marker in the SQL statement; if a database access error occurs
     *                           or this method is called on a closed {@code PreparedStatement } catch (SQLException e) {             throw new MintLeafException(e);         }}  or
     *                           if the Java Object specified by x is an InputStream
     *                           or Reader object and the value of the scale parameter is less
     *                           than zero
     * @throws MintLeafException if
     *                           the JDBC driver does not support the specified targetSqlType
     * @see JDBCType
     * @see SQLType
     * @since 1.8
     */
    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws MintLeafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>String</code> object.
     * The driver converts this to a SQL <code>NCHAR</code> or
     * <code>NVARCHAR</code> or <code>LONGNVARCHAR</code> value
     * (depending on the argument's
     * size relative to the driver's limits on <code>NVARCHAR</code> values)
     * when it sends it to the database.
     *
     * @param parameterIndex of the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if the driver does not support national
     *                           character sets;  if the driver can detect that a data conversion
     *                           error could occur; if a database access error occurs; or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setNString(int parameterIndex, String value) throws MintLeafException {
        try {
            getPreparedStatement().setNString(parameterIndex, value);
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
     * data should be sent to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
     * <p>
     * <B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setBlob</code> which takes a length parameter.
     *
     * @param parameterIndex index of the first parameter is 1,
     *                       the second is 2, ...
     * @param inputStream    An object that contains the data to set the parameter
     *                       value to.
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs;
     *                           this method is called on a closed <code>PreparedStatement</code> or
     *                           if parameterIndex does not correspond
     *                           to a parameter marker in the SQL statement,
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws MintLeafException {
        try {
            getPreparedStatement().setBlob(parameterIndex, inputStream);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Clob</code> object.
     * The driver converts this to an SQL <code>CLOB</code> value when it
     * sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              a <code>Clob</code> object that maps an SQL <code>CLOB</code> value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.2
     */
    @Override
    public void setClob(int parameterIndex, Clob x) throws MintLeafException {
        try {
            getPreparedStatement().setClob(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.math.BigDecimal</code> value.
     * The driver converts this to an SQL <code>NUMERIC</code> value when
     * it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws MintLeafException {
        try {
            getPreparedStatement().setBigDecimal(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object.  The reader must contain  the number
     * of characters specified by length otherwise a <code>MintLeafException</code> will be
     * generated when the <code>PreparedStatement</code> is executed.
     * This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader         An object that contains the data to set the parameter value to.
     * @param length         the number of characters in the parameter data.
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if the length specified is less than zero;
     *                           if the driver does not support national character sets;
     *                           if the driver can detect that a data conversion
     *                           error could occur;  if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws MintLeafException {
        try {
            getPreparedStatement().setNClob(parameterIndex, reader, length);
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
     * <B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setAsciiStream</code> which takes a length parameter.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the Java input stream that contains the ASCII parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws MintLeafException {
        try {
            getPreparedStatement().setAsciiStream(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value.
     * The driver converts this
     * to an SQL <code>TIME</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setTime(int parameterIndex, Time x) throws MintLeafException {
        try {
            getPreparedStatement().setTime(parameterIndex, x);
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
     * @param parameterIndex of the first parameter is 1, the second is 2, ...
     * @param value          the parameter value
     * @param length         the number of characters in the parameter data.
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if the driver does not support national
     *                           character sets;  if the driver can detect that a data conversion
     *                           error could occur; if a database access error occurs; or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws MintLeafException {
        try {
            getPreparedStatement().setNCharacterStream(parameterIndex, value, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given Java <code>float</code> value.
     * The driver converts this
     * to an SQL <code>REAL</code> value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setFloat(int parameterIndex, float x) throws MintLeafException {
        try {
            getPreparedStatement().setFloat(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object.  The reader must contain  the number
     * of characters specified by length otherwise a <code>MintLeafException</code> will be
     * generated when the <code>PreparedStatement</code> is executed.
     * This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
     *
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader         An object that contains the data to set the parameter value to.
     * @param length         the number of characters in the parameter data.
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs; this method is called on
     *                           a closed <code>PreparedStatement</code> or if the length specified is less than zero.
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws MintLeafException {
        try {
            getPreparedStatement().setClob(parameterIndex, reader, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the value of the designated parameter using the given object.
     * <p>
     * The JDBC specification specifies a standard mapping from
     * Java <code>Object</code> types to SQL types.  The given argument
     * will be converted to the corresponding SQL type before being
     * sent to the database.
     * <p>
     * Note that this method may be used to pass datatabase-
     * specific abstract data types, by using a driver-specific Java
     * type.
     * <p>
     * If the object is of a class implementing the interface <code>SQLData</code>,
     * the JDBC driver should call the method <code>SQLData.writeSQL</code>
     * to write it to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,  <code>NClob</code>,
     * <code>Struct</code>, <code>java.net.URL</code>, <code>RowId</code>, <code>SQLXML</code>
     * or <code>Array</code>, the driver should pass it to the database as a
     * value of the corresponding SQL type.
     * <p>
     * <b>Note:</b> Not all databases allow for a non-typed Null to be sent to
     * the backend. For maximum portability, the <code>setNull</code> or the
     * <code>setObject(int parameterIndex, Object x, int sqlType)</code>
     * method should be used
     * instead of <code>setObject(int parameterIndex, Object x)</code>.
     * <p>
     * <b>Note:</b> This method throws an exception if there is an ambiguity, for example, if the
     * object is of a class implementing more than one of the interfaces named above.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the object containing the input parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs;
     *                           this method is called on a closed <code>PreparedStatement</code>
     *                           or the type of the given object is ambiguous
     */
    @Override
    public void setObject(int parameterIndex, Object x) throws MintLeafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Timestamp</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>TIMESTAMP</code> value,
     * which the driver then sends to the database.  With a
     * <code>Calendar</code> object, the driver can calculate the timestamp
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @param cal            the <code>Calendar</code> object the driver will use
     *                       to construct the timestamp
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @since 1.2
     */
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws MintLeafException {
        try {
            getPreparedStatement().setTimestamp(parameterIndex, x, cal);
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
     * <B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setCharacterStream</code> which takes a length parameter.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param reader         the <code>java.io.Reader</code> object that contains the
     *                       Unicode data
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @throws MintLeafException if the JDBC driver does not support this method
     * @since 1.6
     */
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws MintLeafException {
        try {
            getPreparedStatement().setCharacterStream(parameterIndex, reader);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the value of the designated parameter with the given object.
     * <p>
     * This method is similar to {@link #setObject(int parameterIndex,
     * Object x, int targetSqlType, int scaleOrLength) } catch (SQLException e) {             throw new MintLeafException(e);         }},
     * except that it assumes a scale of zero.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the object containing the input parameter value
     * @param targetSqlType  the SQL type (as defined in java.sql.Types) to be
     *                       sent to the database
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or this
     *                           method is called on a closed PreparedStatement
     * @throws MintLeafException if
     *                           the JDBC driver does not support the specified targetSqlType
     * @see Types
     */
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws MintLeafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the value of the designated parameter with the given object.
     * <p>
     * If the second argument is an <code>InputStream</code> then the stream must contain
     * the number of bytes specified by scaleOrLength.  If the second argument is a
     * <code>Reader</code> then the reader must contain the number of characters specified
     * by scaleOrLength. If these conditions are not true the driver will generate a
     * <code>MintLeafException</code> when the prepared statement is executed.
     * <p>
     * The given Java object will be converted to the given targetSqlType
     * before being sent to the database.
     * <p>
     * If the object has a custom mapping (is of a class implementing the
     * interface <code>SQLData</code>),
     * the JDBC driver should call the method <code>SQLData.writeSQL</code> to
     * write it to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,  <code>NClob</code>,
     * <code>Struct</code>, <code>java.net.URL</code>,
     * or <code>Array</code>, the driver should pass it to the database as a
     * value of the corresponding SQL type.
     * <p>
     * Note that this method may be used to pass database-specific
     * abstract data types.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the object containing the input parameter value
     * @param targetSqlType  the SQL type (as defined in java.sql.Types) to be
     *                       sent to the database. The scale argument may further qualify this type.
     * @param scaleOrLength  for <code>java.sql.Types.DECIMAL</code>
     *                       or <code>java.sql.Types.NUMERIC types</code>,
     *                       this is the number of digits after the decimal point. For
     *                       Java Object types <code>InputStream</code> and <code>Reader</code>,
     *                       this is the length
     *                       of the data in the stream or reader.  For all other types,
     *                       this value will be ignored.
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs;
     *                           this method is called on a closed <code>PreparedStatement</code> or
     *                           if the Java Object specified by x is an InputStream
     *                           or Reader object and the value of the scale parameter is less
     *                           than zero
     * @throws MintLeafException if
     *                           the JDBC driver does not support the specified targetSqlType
     * @see Types
     */
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws MintLeafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
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
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the parameter value
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     */
    @Override
    public void setDate(int parameterIndex, Date x) throws MintLeafException {
        try {
            getPreparedStatement().setDate(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large binary value is input to a <code>LONGVARBINARY</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the
     * stream as needed until end-of-file is reached.
     * <p>
     * <B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x              the java input stream which contains the binary parameter value
     * @param length         the number of bytes in the stream
     * @throws MintLeafException if parameterIndex does not correspond to a parameter
     *                           marker in the SQL statement; if a database access error occurs or
     *                           this method is called on a closed <code>PreparedStatement</code>
     * @since 1.6
     */
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws MintLeafException {
        try {
            getPreparedStatement().setBinaryStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


    @Override
    public boolean isBatch() {
        return this.batch;
    }

    /**
     * Retrieves the direction for fetching rows from
     * database tables that is the default for result sets
     * generated from this <code>Statement</code> object.
     * If this <code>Statement</code> object has not set
     * a fetch direction by calling the method <code>setFetchDirection</code>,
     * the return value is implementation-specific.
     *
     * @return the default fetch direction for result sets generated
     * from this <code>Statement</code> object
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     * @since 1.2
     */
    @Override
    public int getFetchDirection() throws MintLeafException {
        try {
            return statement.getFetchDirection();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the number of result set rows that is the default
     * fetch size for <code>ResultSet</code> objects
     * generated from this <code>Statement</code> object.
     * If this <code>Statement</code> object has not set
     * a fetch size by calling the method <code>setFetchSize</code>,
     * the return value is implementation-specific.
     *
     * @return the default fetch size for result sets generated
     * from this <code>Statement</code> object
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>

     * @since 1.2
     */
    @Override
    public int getFetchSize() throws MintLeafException {
        try {
            return statement.getFetchSize();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves any auto-generated keys created as a result of executing this
     * <code>Statement</code> object. If this <code>Statement</code> object did
     * not generate any keys, an empty <code>ResultSet</code>
     * object is returned.
     * <p>
     * <p><B>Note:</B>If the columns which represent the auto-generated keys were not specified,
     * the JDBC driver implementation will determine the columns which best represent the auto-generated keys.
     *
     * @return a <code>ResultSet</code> object containing the auto-generated key(s)
     * generated by the execution of this <code>Statement</code> object
     * @throws MintLeafException               if a database access error occurs or
     *                                         this method is called on a closed <code>Statement</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support this method
     * @since 1.4
     */
    @Override
    public ResultSet getGeneratedKeys() throws MintLeafException {
        try {
            return statement.getGeneratedKeys();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the maximum number of rows that a
     * <code>ResultSet</code> object produced by this
     * <code>Statement</code> object can contain.  If this limit is exceeded,
     * the excess rows are silently dropped.
     * <p>
     * This method should be used when the returned row limit may exceed
     * {@link Integer#MAX_VALUE}.
     * <p>
     * The default implementation will return {@code 0}
     *
     * @return the current maximum number of rows for a <code>ResultSet</code>
     * object produced by this <code>Statement</code> object;
     * zero means there is no limit
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     * @since 1.8
     */
    @Override
    public long getLargeMaxRows() throws MintLeafException {
        try {
            return statement.getLargeMaxRows();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the current result as an update count; if the result
     * is a <code>ResultSet</code> object or there are no more results, -1
     * is returned. This method should be called only once per result.
     * <p>
     * This method should be used when the returned row count may exceed
     * {@link Integer#MAX_VALUE}.
     * <p>
     * The default implementation will throw {@code UnsupportedOperationException}
     *
     * @return the current result as an update count; -1 if the current result
     * is a <code>ResultSet</code> object or there are no more results
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     * @since 1.8
     */
    @Override
    public long getLargeUpdateCount() throws MintLeafException {
        try {
            return statement.getLargeUpdateCount();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the maximum number of bytes that can be
     * returned for character and binary column values in a <code>ResultSet</code>
     * object produced by this <code>Statement</code> object.
     * This limit applies only to  <code>BINARY</code>, <code>VARBINARY</code>,
     * <code>LONGVARBINARY</code>, <code>CHAR</code>, <code>VARCHAR</code>,
     * <code>NCHAR</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>
     * and <code>LONGVARCHAR</code> columns.  If the limit is exceeded, the
     * excess data is silently discarded.
     *
     * @return the current column size limit for columns storing character and
     * binary values; zero means there is no limit
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     */
    @Override
    public int getMaxFieldSize() throws MintLeafException {
        try {
            return statement.getMaxFieldSize();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the maximum number of rows that a
     * <code>ResultSet</code> object produced by this
     * <code>Statement</code> object can contain.  If this limit is exceeded,
     * the excess rows are silently dropped.
     *
     * @return the current maximum number of rows for a <code>ResultSet</code>
     * object produced by this <code>Statement</code> object;
     * zero means there is no limit
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     */
    @Override
    public int getMaxRows() throws MintLeafException {
        try {
            return statement.getMaxRows();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Moves to this <code>Statement</code> object's next result, returns
     * <code>true</code> if it is a <code>ResultSet</code> object, and
     * implicitly closes any current <code>ResultSet</code>
     * object(s) obtained with the method <code>getResultSet</code>.
     * <p>
     * <P>There are no more results when the following is true:
     * <PRE>{@code
     * // stmt is a Statement object
     * ((stmt.getMoreResults() == false) && (stmt.getUpdateCount() == -1))
     * }</PRE>
     *
     * @return <code>true</code> if the next result is a <code>ResultSet</code>
     * object; <code>false</code> if it is an update count or there are
     * no more results
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>

     */
    @Override
    public boolean getMoreResults() throws MintLeafException {
        try {
            return statement.getMoreResults();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Moves to this <code>Statement</code> object's next result, deals with
     * any current <code>ResultSet</code> object(s) according  to the instructions
     * specified by the given flag, and returns
     * <code>true</code> if the next result is a <code>ResultSet</code> object.
     * <p>
     * <P>There are no more results when the following is true:
     * <PRE>{@code
     * // stmt is a Statement object
     * ((stmt.getMoreResults(current) == false) && (stmt.getUpdateCount() == -1))
     * }</PRE>
     *
     * @param current one of the following <code>Statement</code>
     *                constants indicating what should happen to current
     *                <code>ResultSet</code> objects obtained using the method
     *                <code>getResultSet</code>:
     *                <code>Statement.CLOSE_CURRENT_RESULT</code>,
     *                <code>Statement.KEEP_CURRENT_RESULT</code>, or
     *                <code>Statement.CLOSE_ALL_RESULTS</code>
     * @return <code>true</code> if the next result is a <code>ResultSet</code>
     * object; <code>false</code> if it is an update count or there are no
     * more results
     * @throws MintLeafException               if a database access error occurs,
     *                                         this method is called on a closed <code>Statement</code> or the argument
     *                                         supplied is not one of the following:
     *                                         <code>Statement.CLOSE_CURRENT_RESULT</code>,
     *                                         <code>Statement.KEEP_CURRENT_RESULT</code> or
     *                                         <code>Statement.CLOSE_ALL_RESULTS</code>
     * @throws SQLFeatureNotSupportedException if
     *                                         <code>DatabaseMetaData.supportsMultipleOpenResults</code> returns
     *                                         <code>false</code> and either
     *                                         <code>Statement.KEEP_CURRENT_RESULT</code> or
     *                                         <code>Statement.CLOSE_ALL_RESULTS</code> are supplied as
     *                                         the argument.

     * @since 1.4
     */
    @Override
    public boolean getMoreResults(int current) throws MintLeafException {
        try {
            return statement.getMoreResults(current);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the number of seconds the driver will
     * wait for a <code>Statement</code> object to execute.
     * If the limit is exceeded, a
     * <code>SQLException</code> is thrown.
     *
     * @return the current query timeout limit in seconds; zero means there is
     * no limit
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     */
    @Override
    public int getQueryTimeout() throws MintLeafException {
        try {
            return statement.getQueryTimeout();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the current result as a <code>ResultSet</code> object.
     * This method should be called only once per result.
     *
     * @return the current result as a <code>ResultSet</code> object or
     * <code>null</code> if the result is an update count or there are no more results
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     */
    @Override
    public ResultSet getResultSet() throws MintLeafException {
        try {
            return statement.getResultSet();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the result set concurrency for <code>ResultSet</code> objects
     * generated by this <code>Statement</code> object.
     *
     * @return either <code>ResultSet.CONCUR_READ_ONLY</code> or
     * <code>ResultSet.CONCUR_UPDATABLE</code>
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     * @since 1.2
     */
    @Override
    public int getResultSetConcurrency() throws MintLeafException {
        try {
            return statement.getResultSetConcurrency();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the result set holdability for <code>ResultSet</code> objects
     * generated by this <code>Statement</code> object.
     *
     * @return either <code>ResultSet.HOLD_CURSORS_OVER_COMMIT</code> or
     * <code>ResultSet.CLOSE_CURSORS_AT_COMMIT</code>
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     * @since 1.4
     */
    @Override
    public int getResultSetHoldability() throws MintLeafException {
        try {
            return statement.getResultSetHoldability();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the result set type for <code>ResultSet</code> objects
     * generated by this <code>Statement</code> object.
     *
     * @return one of <code>ResultSet.TYPE_FORWARD_ONLY</code>,
     * <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or
     * <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     * @since 1.2
     */
    @Override
    public int getResultSetType() throws MintLeafException {
        try {
            return statement.getResultSetType();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the current result as an update count;
     * if the result is a <code>ResultSet</code> object or there are no more results, -1
     * is returned. This method should be called only once per result.
     *
     * @return the current result as an update count; -1 if the current result is a
     * <code>ResultSet</code> object or there are no more results
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     */
    @Override
    public int getUpdateCount() throws MintLeafException {
        try {
            return statement.getUpdateCount();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves the first warning reported by calls on this <code>Statement</code> object.
     * Subsequent <code>Statement</code> object warnings will be chained to this
     * <code>SQLWarning</code> object.
     * <p>
     * <p>The warning chain is automatically cleared each time
     * a statement is (re)executed. This method may not be called on a closed
     * <code>Statement</code> object; doing so will cause an <code>SQLException</code>
     * to be thrown.
     * <p>
     * <P><B>Note:</B> If you are processing a <code>ResultSet</code> object, any
     * warnings associated with reads on that <code>ResultSet</code> object
     * will be chained on it rather than on the <code>Statement</code>
     * object that produced it.
     *
     * @return the first <code>SQLWarning</code> object or <code>null</code>
     * if there are no warnings
     * @throws MintLeafException if a database access error occurs or
     *                           this method is called on a closed <code>Statement</code>
     */
    @Override
    public SQLWarning getWarnings() throws MintLeafException {
        try {
            return statement.getWarnings();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Retrieves whether this <code>Statement</code> object has been closed. A <code>Statement</code> is closed if the
     * method close has been called on it, or if it is automatically closed.
     *
     * @return true if this <code>Statement</code> object is closed; false if it is still open
     * @throws MintLeafException if a database access error occurs
     * @since 1.6
     */
    @Override
    public boolean isClosed() throws MintLeafException {
        try {
            return statement.isClosed();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Returns a value indicating whether this {@code Statement} will be
     * closed when all its dependent result sets are closed.
     *
     * @return {@code true} if the {@code Statement} will be closed when all
     * of its dependent result sets are closed; {@code false} otherwise
     * @throws MintLeafException if this method is called on a closed
     *                           {@code Statement}
     * @since 1.7
     */
    @Override
    public boolean isCloseOnCompletion() throws MintLeafException {
        try {
            return statement.isCloseOnCompletion();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Returns a  value indicating whether the <code>Statement</code>
     * is poolable or not.
     * <p>
     *
     * @return <code>true</code> if the <code>Statement</code>
     * is poolable; <code>false</code> otherwise
     * <p>
     * @throws MintLeafException if this method is called on a closed
     *                           <code>Statement</code>
     *                           <p>
     * @see Statement#setPoolable(boolean) setPoolable(boolean)
     * @since 1.6
     * <p>
     */
    @Override
    public boolean isPoolable() throws MintLeafException {
        try {
            return statement.isPoolable();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    /**
     * Requests that a <code>Statement</code> be pooled or not pooled.  The value
     * specified is a hint to the statement pool implementation indicating
     * whether the application wants the statement to be pooled.  It is up to
     * the statement pool manager as to whether the hint is used.
     * <p>
     * The poolable value of a statement is applicable to both internal
     * statement caches implemented by the driver and external statement caches
     * implemented by application servers and other applications.
     * <p>
     * By default, a <code>Statement</code> is not poolable when created, and
     * a <code>PreparedStatement</code> and <code>CallableStatement</code>
     * are poolable when created.
     *
     * @param poolable requests that the statement be pooled if true and
     *                 that the statement not be pooled if false
     * @throws MintLeafException if this method is called on a closed
     *                           <code>Statement</code>
     * @since 1.6
     */
    @Override
    public void setPoolable(boolean poolable) throws MintLeafException {
        try {
            getPreparedStatement().setPoolable(poolable);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


}
