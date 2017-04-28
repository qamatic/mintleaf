package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.ParameterGets;
import org.qamatic.mintleaf.ParameterSets;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;


public class BindingParameterSets implements ParameterSets, ParameterGets {

    private static final MintleafLogger logger = MintleafLogger.getLogger(BindingParameterSets.class);
    private Statement statement;
    private boolean batch = false;

    public BindingParameterSets(Statement preparedStatement) {
        this.statement = preparedStatement;
    }

    private PreparedStatement getPreparedStatement() {
        return (PreparedStatement) this.statement;
    }


    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws MintleafException {
        try {

            getPreparedStatement().setBlob(parameterIndex, inputStream, length);

        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setBlob(int parameterIndex, Blob x) throws MintleafException {
        try {
            getPreparedStatement().setBlob(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setEscapeProcessing(boolean enable) throws MintleafException {
        try {
            getPreparedStatement().setEscapeProcessing(enable);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setURL(int parameterIndex, URL x) throws MintleafException {
        try {
            getPreparedStatement().setURL(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws MintleafException {
        try {
            getPreparedStatement().setNCharacterStream(parameterIndex, value);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setBytes(int parameterIndex, byte[] x) throws MintleafException {
        try {
            getPreparedStatement().setBytes(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    @Deprecated
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws MintleafException {
        try {
            getPreparedStatement().setUnicodeStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void clearParameters() throws MintleafException {
        try {
            getPreparedStatement().clearParameters();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public ParameterMetaData getParameterMetaData() throws MintleafException {
        try {
            return getPreparedStatement().getParameterMetaData();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setByte(int parameterIndex, byte x) throws MintleafException {
        try {
            getPreparedStatement().setByte(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws MintleafException {
        try {
            getPreparedStatement().setCharacterStream(parameterIndex, reader, length);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setClob(int parameterIndex, Reader reader) throws MintleafException {
        try {
            getPreparedStatement().setClob(parameterIndex, reader);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setNClob(int parameterIndex, NClob value) throws MintleafException {
        try {
            getPreparedStatement().setNClob(parameterIndex, value);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setRowId(int parameterIndex, RowId x) throws MintleafException {
        try {
            getPreparedStatement().setRowId(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setLong(int parameterIndex, long x) throws MintleafException {
        try {
            getPreparedStatement().setLong(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setRef(int parameterIndex, Ref x) throws MintleafException {
        try {
            getPreparedStatement().setRef(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws MintleafException {
        try {
            getPreparedStatement().setTimestamp(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws MintleafException {
        try {
            getPreparedStatement().setAsciiStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws MintleafException {
        try {
            getPreparedStatement().setBinaryStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws MintleafException {
        try {
            getPreparedStatement().setSQLXML(parameterIndex, xmlObject);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setInt(int parameterIndex, int x) throws MintleafException {
        try {
            getPreparedStatement().setInt(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setBoolean(int parameterIndex, boolean x) throws MintleafException {
        try {
            getPreparedStatement().setBoolean(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setString(int parameterIndex, String x) throws MintleafException {
        try {
            getPreparedStatement().setString(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws MintleafException {
        try {
            getPreparedStatement().setBinaryStream(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setNull(int parameterIndex, int sqlType) throws MintleafException {
        try {
            getPreparedStatement().setNull(parameterIndex, sqlType);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setDouble(int parameterIndex, double x) throws MintleafException {
        try {
            getPreparedStatement().setDouble(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setArray(int parameterIndex, Array x) throws MintleafException {
        try {
            getPreparedStatement().setArray(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws MintleafException {
        try {
            getPreparedStatement().setDate(parameterIndex, x, cal);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws MintleafException {
        try {
            getPreparedStatement().setNull(parameterIndex, sqlType, typeName);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws MintleafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws MintleafException {
        try {
            getPreparedStatement().setAsciiStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setShort(int parameterIndex, short x) throws MintleafException {
        try {
            getPreparedStatement().setShort(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setNClob(int parameterIndex, Reader reader) throws MintleafException {
        try {
            getPreparedStatement().setNClob(parameterIndex, reader);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws MintleafException {
        try {
            getPreparedStatement().setTime(parameterIndex, x, cal);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws MintleafException {
        try {
            getPreparedStatement().setCharacterStream(parameterIndex, reader, length);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws MintleafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setNString(int parameterIndex, String value) throws MintleafException {
        try {
            getPreparedStatement().setNString(parameterIndex, value);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws MintleafException {
        try {
            getPreparedStatement().setBlob(parameterIndex, inputStream);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setClob(int parameterIndex, Clob x) throws MintleafException {
        try {
            getPreparedStatement().setClob(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws MintleafException {
        try {
            getPreparedStatement().setBigDecimal(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws MintleafException {
        try {
            getPreparedStatement().setNClob(parameterIndex, reader, length);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws MintleafException {
        try {
            getPreparedStatement().setAsciiStream(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setTime(int parameterIndex, Time x) throws MintleafException {
        try {
            getPreparedStatement().setTime(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws MintleafException {
        try {
            getPreparedStatement().setNCharacterStream(parameterIndex, value, length);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setFloat(int parameterIndex, float x) throws MintleafException {
        try {
            getPreparedStatement().setFloat(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws MintleafException {
        try {
            getPreparedStatement().setClob(parameterIndex, reader, length);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setObject(int parameterIndex, Object x) throws MintleafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws MintleafException {
        try {
            getPreparedStatement().setTimestamp(parameterIndex, x, cal);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws MintleafException {
        try {
            getPreparedStatement().setCharacterStream(parameterIndex, reader);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws MintleafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws MintleafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setDate(int parameterIndex, Date x) throws MintleafException {
        try {
            getPreparedStatement().setDate(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws MintleafException {
        try {
            getPreparedStatement().setBinaryStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public boolean isBatch() {
        return this.batch;
    }


    @Override
    public int getFetchDirection() throws MintleafException {
        try {
            return statement.getFetchDirection();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public int getFetchSize() throws MintleafException {
        try {
            return statement.getFetchSize();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public ResultSet getGeneratedKeys() throws MintleafException {
        try {
            return statement.getGeneratedKeys();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public long getLargeMaxRows() throws MintleafException {
        try {
            return statement.getLargeMaxRows();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public long getLargeUpdateCount() throws MintleafException {
        try {
            return statement.getLargeUpdateCount();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public int getMaxFieldSize() throws MintleafException {
        try {
            return statement.getMaxFieldSize();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public int getMaxRows() throws MintleafException {
        try {
            return statement.getMaxRows();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public boolean getMoreResults() throws MintleafException {
        try {
            return statement.getMoreResults();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public boolean getMoreResults(int current) throws MintleafException {
        try {
            return statement.getMoreResults(current);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public int getQueryTimeout() throws MintleafException {
        try {
            return statement.getQueryTimeout();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public ResultSet getResultSet() throws MintleafException {
        try {
            return statement.getResultSet();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public int getResultSetConcurrency() throws MintleafException {
        try {
            return statement.getResultSetConcurrency();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public int getResultSetHoldability() throws MintleafException {
        try {
            return statement.getResultSetHoldability();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public int getResultSetType() throws MintleafException {
        try {
            return statement.getResultSetType();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public int getUpdateCount() throws MintleafException {
        try {
            return statement.getUpdateCount();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public SQLWarning getWarnings() throws MintleafException {
        try {
            return statement.getWarnings();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public boolean isClosed() throws MintleafException {
        try {
            return statement.isClosed();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public boolean isCloseOnCompletion() throws MintleafException {
        try {
            return statement.isCloseOnCompletion();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public boolean isPoolable() throws MintleafException {
        try {
            return statement.isPoolable();
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public void setPoolable(boolean poolable) throws MintleafException {
        try {
            getPreparedStatement().setPoolable(poolable);
        } catch (SQLException e) {
            throw new MintleafException(e);
        }
    }


}
