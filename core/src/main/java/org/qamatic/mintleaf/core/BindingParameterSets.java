

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
    
    
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws MintLeafException {
        try {

             getPreparedStatement().setBlob(parameterIndex, inputStream, length);

        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setBlob(int parameterIndex, Blob x) throws MintLeafException {
        try {
            getPreparedStatement().setBlob(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setEscapeProcessing(boolean enable) throws MintLeafException {
        try {
            getPreparedStatement().setEscapeProcessing(enable);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setURL(int parameterIndex, URL x) throws MintLeafException {
        try {
            getPreparedStatement().setURL(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


    
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws MintLeafException {
        try {
            getPreparedStatement().setNCharacterStream(parameterIndex, value);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setBytes(int parameterIndex, byte[] x) throws MintLeafException {
        try {
            getPreparedStatement().setBytes(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    @Deprecated
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws MintLeafException {
        try {
            getPreparedStatement().setUnicodeStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void clearParameters() throws MintLeafException {
        try {
            getPreparedStatement().clearParameters();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public ParameterMetaData getParameterMetaData() throws MintLeafException {
        try {
            return getPreparedStatement().getParameterMetaData();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setByte(int parameterIndex, byte x) throws MintLeafException {
        try {
            getPreparedStatement().setByte(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws MintLeafException {
        try {
            getPreparedStatement().setCharacterStream(parameterIndex, reader, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setClob(int parameterIndex, Reader reader) throws MintLeafException {
        try {
            getPreparedStatement().setClob(parameterIndex, reader);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setNClob(int parameterIndex, NClob value) throws MintLeafException {
        try {
            getPreparedStatement().setNClob(parameterIndex, value);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setRowId(int parameterIndex, RowId x) throws MintLeafException {
        try {
            getPreparedStatement().setRowId(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setLong(int parameterIndex, long x) throws MintLeafException {
        try {
            getPreparedStatement().setLong(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setRef(int parameterIndex, Ref x) throws MintLeafException {
        try {
            getPreparedStatement().setRef(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws MintLeafException {
        try {
            getPreparedStatement().setTimestamp(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws MintLeafException {
        try {
            getPreparedStatement().setAsciiStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws MintLeafException {
        try {
            getPreparedStatement().setBinaryStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws MintLeafException {
        try {
            getPreparedStatement().setSQLXML(parameterIndex, xmlObject);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setInt(int parameterIndex, int x) throws MintLeafException {
        try {
            getPreparedStatement().setInt(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setBoolean(int parameterIndex, boolean x) throws MintLeafException {
        try {
            getPreparedStatement().setBoolean(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setString(int parameterIndex, String x) throws MintLeafException {
        try {
            getPreparedStatement().setString(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws MintLeafException {
        try {
            getPreparedStatement().setBinaryStream(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setNull(int parameterIndex, int sqlType) throws MintLeafException {
        try {
            getPreparedStatement().setNull(parameterIndex, sqlType);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setDouble(int parameterIndex, double x) throws MintLeafException {
        try {
            getPreparedStatement().setDouble(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setArray(int parameterIndex, Array x) throws MintLeafException {
        try {
            getPreparedStatement().setArray(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws MintLeafException {
        try {
            getPreparedStatement().setDate(parameterIndex, x, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws MintLeafException {
        try {
            getPreparedStatement().setNull(parameterIndex, sqlType, typeName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws MintLeafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws MintLeafException {
        try {
            getPreparedStatement().setAsciiStream(parameterIndex, x, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setShort(int parameterIndex, short x) throws MintLeafException {
        try {
            getPreparedStatement().setShort(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setNClob(int parameterIndex, Reader reader) throws MintLeafException {
        try {
            getPreparedStatement().setNClob(parameterIndex, reader);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws MintLeafException {
        try {
            getPreparedStatement().setTime(parameterIndex, x, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws MintLeafException {
        try {
            getPreparedStatement().setCharacterStream(parameterIndex, reader, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws MintLeafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setNString(int parameterIndex, String value) throws MintLeafException {
        try {
            getPreparedStatement().setNString(parameterIndex, value);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws MintLeafException {
        try {
            getPreparedStatement().setBlob(parameterIndex, inputStream);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setClob(int parameterIndex, Clob x) throws MintLeafException {
        try {
            getPreparedStatement().setClob(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws MintLeafException {
        try {
            getPreparedStatement().setBigDecimal(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws MintLeafException {
        try {
            getPreparedStatement().setNClob(parameterIndex, reader, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws MintLeafException {
        try {
            getPreparedStatement().setAsciiStream(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setTime(int parameterIndex, Time x) throws MintLeafException {
        try {
            getPreparedStatement().setTime(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws MintLeafException {
        try {
            getPreparedStatement().setNCharacterStream(parameterIndex, value, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setFloat(int parameterIndex, float x) throws MintLeafException {
        try {
            getPreparedStatement().setFloat(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws MintLeafException {
        try {
            getPreparedStatement().setClob(parameterIndex, reader, length);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setObject(int parameterIndex, Object x) throws MintLeafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws MintLeafException {
        try {
            getPreparedStatement().setTimestamp(parameterIndex, x, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws MintLeafException {
        try {
            getPreparedStatement().setCharacterStream(parameterIndex, reader);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws MintLeafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws MintLeafException {
        try {
            getPreparedStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setDate(int parameterIndex, Date x) throws MintLeafException {
        try {
            getPreparedStatement().setDate(parameterIndex, x);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
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

    
    @Override
    public int getFetchDirection() throws MintLeafException {
        try {
            return statement.getFetchDirection();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public int getFetchSize() throws MintLeafException {
        try {
            return statement.getFetchSize();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public ResultSet getGeneratedKeys() throws MintLeafException {
        try {
            return statement.getGeneratedKeys();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public long getLargeMaxRows() throws MintLeafException {
        try {
            return statement.getLargeMaxRows();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public long getLargeUpdateCount() throws MintLeafException {
        try {
            return statement.getLargeUpdateCount();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public int getMaxFieldSize() throws MintLeafException {
        try {
            return statement.getMaxFieldSize();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public int getMaxRows() throws MintLeafException {
        try {
            return statement.getMaxRows();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public boolean getMoreResults() throws MintLeafException {
        try {
            return statement.getMoreResults();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public boolean getMoreResults(int current) throws MintLeafException {
        try {
            return statement.getMoreResults(current);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public int getQueryTimeout() throws MintLeafException {
        try {
            return statement.getQueryTimeout();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public ResultSet getResultSet() throws MintLeafException {
        try {
            return statement.getResultSet();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public int getResultSetConcurrency() throws MintLeafException {
        try {
            return statement.getResultSetConcurrency();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public int getResultSetHoldability() throws MintLeafException {
        try {
            return statement.getResultSetHoldability();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public int getResultSetType() throws MintLeafException {
        try {
            return statement.getResultSetType();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public int getUpdateCount() throws MintLeafException {
        try {
            return statement.getUpdateCount();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public SQLWarning getWarnings() throws MintLeafException {
        try {
            return statement.getWarnings();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public boolean isClosed() throws MintLeafException {
        try {
            return statement.isClosed();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public boolean isCloseOnCompletion() throws MintLeafException {
        try {
            return statement.isCloseOnCompletion();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public boolean isPoolable() throws MintLeafException {
        try {
            return statement.isPoolable();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void setPoolable(boolean poolable) throws MintLeafException {
        try {
            getPreparedStatement().setPoolable(poolable);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }


}
