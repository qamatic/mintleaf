package org.qamatic.mintleaf;

import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by senips on 4/2/17.
 */
public interface CallableParameterGets extends ParameterGets {
    ResultSetMetaData getMetaData() throws MintLeafException;

    @Override
    ParameterMetaData getParameterMetaData() throws MintLeafException;

    Array getArray(int parameterIndex) throws MintLeafException;

    Array getArray(String parameterName) throws MintLeafException;

    BigDecimal getBigDecimal(int parameterIndex) throws MintLeafException;

    @Deprecated
    BigDecimal getBigDecimal(int parameterIndex, int scale) throws MintLeafException;

    BigDecimal getBigDecimal(String parameterName) throws MintLeafException;

    Blob getBlob(int parameterIndex) throws MintLeafException;

    Blob getBlob(String parameterName) throws MintLeafException;

    boolean getBoolean(int parameterIndex) throws MintLeafException;

    boolean getBoolean(String parameterName) throws MintLeafException;

    byte getByte(int parameterIndex) throws MintLeafException;

    byte getByte(String parameterName) throws MintLeafException;

    byte[] getBytes(int parameterIndex) throws MintLeafException;

    byte[] getBytes(String parameterName) throws MintLeafException;

    Reader getCharacterStream(int parameterIndex) throws MintLeafException;

    Reader getCharacterStream(String parameterName) throws MintLeafException;

    Clob getClob(int parameterIndex) throws MintLeafException;

    Clob getClob(String parameterName) throws MintLeafException;

    Date getDate(int parameterIndex) throws MintLeafException;

    Date getDate(int parameterIndex, Calendar cal) throws MintLeafException;

    Date getDate(String parameterName) throws MintLeafException;

    Date getDate(String parameterName, Calendar cal) throws MintLeafException;

    double getDouble(int parameterIndex) throws MintLeafException;

    double getDouble(String parameterName) throws MintLeafException;

    float getFloat(int parameterIndex) throws MintLeafException;

    float getFloat(String parameterName) throws MintLeafException;

    int getInt(int parameterIndex) throws MintLeafException;

    int getInt(String parameterName) throws MintLeafException;

    long getLong(int parameterIndex) throws MintLeafException;

    long getLong(String parameterName) throws MintLeafException;

    Reader getNCharacterStream(int parameterIndex) throws MintLeafException;

    Reader getNCharacterStream(String parameterName) throws MintLeafException;

    NClob getNClob(int parameterIndex) throws MintLeafException;

    NClob getNClob(String parameterName) throws MintLeafException;

    String getNString(int parameterIndex) throws MintLeafException;

    String getNString(String parameterName) throws MintLeafException;

    Object getObject(int parameterIndex) throws MintLeafException;

    Object getObject(int parameterIndex, Map<String, Class<?>> map) throws MintLeafException;

    <T> T getObject(int parameterIndex, Class<T> type) throws MintLeafException;

    Object getObject(String parameterName) throws MintLeafException;

    Object getObject(String parameterName, Map<String, Class<?>> map) throws MintLeafException;

    <T> T getObject(String parameterName, Class<T> type) throws MintLeafException;

    Ref getRef(int parameterIndex) throws MintLeafException;

    Ref getRef(String parameterName) throws MintLeafException;

    RowId getRowId(int parameterIndex) throws MintLeafException;

    RowId getRowId(String parameterName) throws MintLeafException;

    short getShort(int parameterIndex) throws MintLeafException;

    short getShort(String parameterName) throws MintLeafException;

    SQLXML getSQLXML(int parameterIndex) throws MintLeafException;

    SQLXML getSQLXML(String parameterName) throws MintLeafException;

    String getString(int parameterIndex) throws MintLeafException;

    String getString(String parameterName) throws MintLeafException;

    Time getTime(int parameterIndex) throws MintLeafException;

    Time getTime(int parameterIndex, Calendar cal) throws MintLeafException;

    Time getTime(String parameterName) throws MintLeafException;

    Time getTime(String parameterName, Calendar cal) throws MintLeafException;

    Timestamp getTimestamp(int parameterIndex) throws MintLeafException;

    Timestamp getTimestamp(int parameterIndex, Calendar cal) throws MintLeafException;

    Timestamp getTimestamp(String parameterName) throws MintLeafException;

    Timestamp getTimestamp(String parameterName, Calendar cal) throws MintLeafException;

    URL getURL(int parameterIndex) throws MintLeafException;

    URL getURL(String parameterName) throws MintLeafException;
}
