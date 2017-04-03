package org.qamatic.mintleaf;

import org.qamatic.mintleaf.MintLeafException;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

/**
 * Created by QAmatic Team on 4/2/17.
 */
public interface ParameterSets {
    void setBlob(int parameterIndex, InputStream inputStream, long length) throws MintLeafException;

    void setBlob(int parameterIndex, Blob x) throws MintLeafException;

    void setEscapeProcessing(boolean enable) throws MintLeafException;

    void setURL(int parameterIndex, URL x) throws MintLeafException;

    void setNCharacterStream(int parameterIndex, Reader value) throws MintLeafException;

    void setBytes(int parameterIndex, byte[] x) throws MintLeafException;

    @Deprecated
    void setUnicodeStream(int parameterIndex, InputStream x, int length) throws MintLeafException;

    void clearParameters() throws MintLeafException;

    void setByte(int parameterIndex, byte x) throws MintLeafException;

    void setCharacterStream(int parameterIndex, Reader reader, long length) throws MintLeafException;

    void setClob(int parameterIndex, Reader reader) throws MintLeafException;

    void setNClob(int parameterIndex, NClob value) throws MintLeafException;

    void setRowId(int parameterIndex, RowId x) throws MintLeafException;

    void setLong(int parameterIndex, long x) throws MintLeafException;

    void setRef(int parameterIndex, Ref x) throws MintLeafException;

    void setTimestamp(int parameterIndex, Timestamp x) throws MintLeafException;

    void setAsciiStream(int parameterIndex, InputStream x, long length) throws MintLeafException;

    void setBinaryStream(int parameterIndex, InputStream x, int length) throws MintLeafException;

    void setSQLXML(int parameterIndex, SQLXML xmlObject) throws MintLeafException;

    void setPoolable(boolean poolable) throws MintLeafException;

    void setInt(int parameterIndex, int x) throws MintLeafException;

    void setBoolean(int parameterIndex, boolean x) throws MintLeafException;

    void setString(int parameterIndex, String x) throws MintLeafException;

    void setBinaryStream(int parameterIndex, InputStream x) throws MintLeafException;

    void setNull(int parameterIndex, int sqlType) throws MintLeafException;

    void setDouble(int parameterIndex, double x) throws MintLeafException;

    void setArray(int parameterIndex, Array x) throws MintLeafException;

    void setDate(int parameterIndex, Date x, Calendar cal) throws MintLeafException;

    void setNull(int parameterIndex, int sqlType, String typeName) throws MintLeafException;

    void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws MintLeafException;

    void setAsciiStream(int parameterIndex, InputStream x, int length) throws MintLeafException;

    void setShort(int parameterIndex, short x) throws MintLeafException;

    void setNClob(int parameterIndex, Reader reader) throws MintLeafException;

    void setTime(int parameterIndex, Time x, Calendar cal) throws MintLeafException;

    void setCharacterStream(int parameterIndex, Reader reader, int length) throws MintLeafException;

    void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws MintLeafException;

    void setNString(int parameterIndex, String value) throws MintLeafException;

    void setBlob(int parameterIndex, InputStream inputStream) throws MintLeafException;

    void setClob(int parameterIndex, Clob x) throws MintLeafException;

    void setBigDecimal(int parameterIndex, BigDecimal x) throws MintLeafException;

    void setNClob(int parameterIndex, Reader reader, long length) throws MintLeafException;

    void setAsciiStream(int parameterIndex, InputStream x) throws MintLeafException;

    void setTime(int parameterIndex, Time x) throws MintLeafException;

    void setNCharacterStream(int parameterIndex, Reader value, long length) throws MintLeafException;

    void setFloat(int parameterIndex, float x) throws MintLeafException;

    void setClob(int parameterIndex, Reader reader, long length) throws MintLeafException;

    void setObject(int parameterIndex, Object x) throws MintLeafException;

    void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws MintLeafException;

    void setCharacterStream(int parameterIndex, Reader reader) throws MintLeafException;

    void setObject(int parameterIndex, Object x, int targetSqlType) throws MintLeafException;

    void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws MintLeafException;

    void setDate(int parameterIndex, Date x) throws MintLeafException;

    void setBinaryStream(int parameterIndex, InputStream x, long length) throws MintLeafException;
}
