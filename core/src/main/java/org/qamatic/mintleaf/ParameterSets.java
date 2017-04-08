package org.qamatic.mintleaf;

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
    void setBlob(int parameterIndex, InputStream inputStream, long length) throws MintleafException;

    void setBlob(int parameterIndex, Blob x) throws MintleafException;

    void setEscapeProcessing(boolean enable) throws MintleafException;

    void setURL(int parameterIndex, URL x) throws MintleafException;

    void setNCharacterStream(int parameterIndex, Reader value) throws MintleafException;

    void setBytes(int parameterIndex, byte[] x) throws MintleafException;

    @Deprecated
    void setUnicodeStream(int parameterIndex, InputStream x, int length) throws MintleafException;

    void clearParameters() throws MintleafException;

    void setByte(int parameterIndex, byte x) throws MintleafException;

    void setCharacterStream(int parameterIndex, Reader reader, long length) throws MintleafException;

    void setClob(int parameterIndex, Reader reader) throws MintleafException;

    void setNClob(int parameterIndex, NClob value) throws MintleafException;

    void setRowId(int parameterIndex, RowId x) throws MintleafException;

    void setLong(int parameterIndex, long x) throws MintleafException;

    void setRef(int parameterIndex, Ref x) throws MintleafException;

    void setTimestamp(int parameterIndex, Timestamp x) throws MintleafException;

    void setAsciiStream(int parameterIndex, InputStream x, long length) throws MintleafException;

    void setBinaryStream(int parameterIndex, InputStream x, int length) throws MintleafException;

    void setSQLXML(int parameterIndex, SQLXML xmlObject) throws MintleafException;

    void setPoolable(boolean poolable) throws MintleafException;

    void setInt(int parameterIndex, int x) throws MintleafException;

    void setBoolean(int parameterIndex, boolean x) throws MintleafException;

    void setString(int parameterIndex, String x) throws MintleafException;

    void setBinaryStream(int parameterIndex, InputStream x) throws MintleafException;

    void setNull(int parameterIndex, int sqlType) throws MintleafException;

    void setDouble(int parameterIndex, double x) throws MintleafException;

    void setArray(int parameterIndex, Array x) throws MintleafException;

    void setDate(int parameterIndex, Date x, Calendar cal) throws MintleafException;

    void setNull(int parameterIndex, int sqlType, String typeName) throws MintleafException;

    void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws MintleafException;

    void setAsciiStream(int parameterIndex, InputStream x, int length) throws MintleafException;

    void setShort(int parameterIndex, short x) throws MintleafException;

    void setNClob(int parameterIndex, Reader reader) throws MintleafException;

    void setTime(int parameterIndex, Time x, Calendar cal) throws MintleafException;

    void setCharacterStream(int parameterIndex, Reader reader, int length) throws MintleafException;

    void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws MintleafException;

    void setNString(int parameterIndex, String value) throws MintleafException;

    void setBlob(int parameterIndex, InputStream inputStream) throws MintleafException;

    void setClob(int parameterIndex, Clob x) throws MintleafException;

    void setBigDecimal(int parameterIndex, BigDecimal x) throws MintleafException;

    void setNClob(int parameterIndex, Reader reader, long length) throws MintleafException;

    void setAsciiStream(int parameterIndex, InputStream x) throws MintleafException;

    void setTime(int parameterIndex, Time x) throws MintleafException;

    void setNCharacterStream(int parameterIndex, Reader value, long length) throws MintleafException;

    void setFloat(int parameterIndex, float x) throws MintleafException;

    void setClob(int parameterIndex, Reader reader, long length) throws MintleafException;

    void setObject(int parameterIndex, Object x) throws MintleafException;

    void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws MintleafException;

    void setCharacterStream(int parameterIndex, Reader reader) throws MintleafException;

    void setObject(int parameterIndex, Object x, int targetSqlType) throws MintleafException;

    void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws MintleafException;

    void setDate(int parameterIndex, Date x) throws MintleafException;

    void setBinaryStream(int parameterIndex, InputStream x, long length) throws MintleafException;
}
