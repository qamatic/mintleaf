package org.qamatic.mintleaf;

import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by QAmatic Team on 4/2/17.
 */
public interface CallableParameterGets extends ParameterGets {
    ResultSetMetaData getMetaData() throws MintleafException;

    @Override
    ParameterMetaData getParameterMetaData() throws MintleafException;

    Array getArray(int parameterIndex) throws MintleafException;

    Array getArray(String parameterName) throws MintleafException;

    BigDecimal getBigDecimal(int parameterIndex) throws MintleafException;

    @Deprecated
    BigDecimal getBigDecimal(int parameterIndex, int scale) throws MintleafException;

    BigDecimal getBigDecimal(String parameterName) throws MintleafException;

    Blob getBlob(int parameterIndex) throws MintleafException;

    Blob getBlob(String parameterName) throws MintleafException;

    boolean getBoolean(int parameterIndex) throws MintleafException;

    boolean getBoolean(String parameterName) throws MintleafException;

    byte getByte(int parameterIndex) throws MintleafException;

    byte getByte(String parameterName) throws MintleafException;

    byte[] getBytes(int parameterIndex) throws MintleafException;

    byte[] getBytes(String parameterName) throws MintleafException;

    Reader getCharacterStream(int parameterIndex) throws MintleafException;

    Reader getCharacterStream(String parameterName) throws MintleafException;

    Clob getClob(int parameterIndex) throws MintleafException;

    Clob getClob(String parameterName) throws MintleafException;

    Date getDate(int parameterIndex) throws MintleafException;

    Date getDate(int parameterIndex, Calendar cal) throws MintleafException;

    Date getDate(String parameterName) throws MintleafException;

    Date getDate(String parameterName, Calendar cal) throws MintleafException;

    double getDouble(int parameterIndex) throws MintleafException;

    double getDouble(String parameterName) throws MintleafException;

    float getFloat(int parameterIndex) throws MintleafException;

    float getFloat(String parameterName) throws MintleafException;

    int getInt(int parameterIndex) throws MintleafException;

    int getInt(String parameterName) throws MintleafException;

    long getLong(int parameterIndex) throws MintleafException;

    long getLong(String parameterName) throws MintleafException;

    Reader getNCharacterStream(int parameterIndex) throws MintleafException;

    Reader getNCharacterStream(String parameterName) throws MintleafException;

    NClob getNClob(int parameterIndex) throws MintleafException;

    NClob getNClob(String parameterName) throws MintleafException;

    String getNString(int parameterIndex) throws MintleafException;

    String getNString(String parameterName) throws MintleafException;

    Object getObject(int parameterIndex) throws MintleafException;

    Object getObject(int parameterIndex, Map<String, Class<?>> map) throws MintleafException;

    <T> T getObject(int parameterIndex, Class<T> type) throws MintleafException;

    Object getObject(String parameterName) throws MintleafException;

    Object getObject(String parameterName, Map<String, Class<?>> map) throws MintleafException;

    <T> T getObject(String parameterName, Class<T> type) throws MintleafException;

    Ref getRef(int parameterIndex) throws MintleafException;

    Ref getRef(String parameterName) throws MintleafException;

    RowId getRowId(int parameterIndex) throws MintleafException;

    RowId getRowId(String parameterName) throws MintleafException;

    short getShort(int parameterIndex) throws MintleafException;

    short getShort(String parameterName) throws MintleafException;

    SQLXML getSQLXML(int parameterIndex) throws MintleafException;

    SQLXML getSQLXML(String parameterName) throws MintleafException;

    String getString(int parameterIndex) throws MintleafException;

    String getString(String parameterName) throws MintleafException;

    Time getTime(int parameterIndex) throws MintleafException;

    Time getTime(int parameterIndex, Calendar cal) throws MintleafException;

    Time getTime(String parameterName) throws MintleafException;

    Time getTime(String parameterName, Calendar cal) throws MintleafException;

    Timestamp getTimestamp(int parameterIndex) throws MintleafException;

    Timestamp getTimestamp(int parameterIndex, Calendar cal) throws MintleafException;

    Timestamp getTimestamp(String parameterName) throws MintleafException;

    Timestamp getTimestamp(String parameterName, Calendar cal) throws MintleafException;

    URL getURL(int parameterIndex) throws MintleafException;

    URL getURL(String parameterName) throws MintleafException;
}
