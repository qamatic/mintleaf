

package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.CallableParameterGets;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.MintLeafLogger;
import org.qamatic.mintleaf.CallableParameterSets;

import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;


public class CallableBindingParameterSets extends BindingParameterSets implements CallableParameterGets, CallableParameterSets {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(CallableBindingParameterSets.class);
    private CallableStatement callableStatement;

    public CallableBindingParameterSets(CallableStatement preparedStatement) {
        super(preparedStatement);
        this.callableStatement = preparedStatement;

    }

    
    @Override
    public ResultSetMetaData getMetaData() throws MintLeafException {
        try {
            return callableStatement.getMetaData();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public ParameterMetaData getParameterMetaData() throws MintLeafException {
        try {
            return callableStatement.getParameterMetaData();
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Array getArray(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getArray(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Array getArray(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getArray(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public BigDecimal getBigDecimal(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getBigDecimal(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    @Deprecated
    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws MintLeafException {
        try {
            return callableStatement.getBigDecimal(parameterIndex, scale);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public BigDecimal getBigDecimal(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getBigDecimal(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Blob getBlob(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getBlob(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Blob getBlob(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getBlob(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public boolean getBoolean(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getBoolean(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public boolean getBoolean(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getBoolean(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public byte getByte(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getByte(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public byte getByte(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getByte(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public byte[] getBytes(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getBytes(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public byte[] getBytes(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getBytes(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Reader getCharacterStream(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getCharacterStream(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Reader getCharacterStream(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getCharacterStream(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Clob getClob(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getClob(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Clob getClob(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getClob(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Date getDate(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getDate(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Date getDate(int parameterIndex, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getDate(parameterIndex, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Date getDate(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getDate(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Date getDate(String parameterName, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getDate(parameterName, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public double getDouble(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getDouble(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public double getDouble(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getDouble(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public float getFloat(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getFloat(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public float getFloat(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getFloat(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public int getInt(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getInt(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public int getInt(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getInt(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public long getLong(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getLong(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public long getLong(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getLong(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Reader getNCharacterStream(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getNCharacterStream(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Reader getNCharacterStream(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getNCharacterStream(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public NClob getNClob(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getNClob(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public NClob getNClob(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getNClob(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public String getNString(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getNString(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public String getNString(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getNString(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Object getObject(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterIndex, map);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public <T> T getObject(int parameterIndex, Class<T> type) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterIndex, type);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Object getObject(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Object getObject(String parameterName, Map<String, Class<?>> map) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterName, map);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public <T> T getObject(String parameterName, Class<T> type) throws MintLeafException {
        try {
            return callableStatement.getObject(parameterName, type);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Ref getRef(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getRef(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Ref getRef(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getRef(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public RowId getRowId(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getRowId(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public RowId getRowId(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getRowId(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public short getShort(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getShort(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public short getShort(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getShort(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public SQLXML getSQLXML(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getSQLXML(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public SQLXML getSQLXML(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getSQLXML(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public String getString(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getString(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public String getString(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getString(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Time getTime(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getTime(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Time getTime(int parameterIndex, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getTime(parameterIndex, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Time getTime(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getTime(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Time getTime(String parameterName, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getTime(parameterName, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Timestamp getTimestamp(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getTimestamp(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getTimestamp(parameterIndex, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Timestamp getTimestamp(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getTimestamp(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public Timestamp getTimestamp(String parameterName, Calendar cal) throws MintLeafException {
        try {
            return callableStatement.getTimestamp(parameterName, cal);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public URL getURL(int parameterIndex) throws MintLeafException {
        try {
            return callableStatement.getURL(parameterIndex);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public URL getURL(String parameterName) throws MintLeafException {
        try {
            return callableStatement.getURL(parameterName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(int parameterIndex, int sqlType) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType, scale);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType, typeName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(int parameterIndex, SQLType sqlType) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(int parameterIndex, SQLType sqlType, int scale) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType, scale);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(int parameterIndex, SQLType sqlType, String typeName) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterIndex, sqlType, typeName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(String parameterName, int sqlType) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(String parameterName, int sqlType, int scale) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType, scale);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType, typeName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(String parameterName, SQLType sqlType) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(String parameterName, SQLType sqlType, int scale) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType, scale);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    
    @Override
    public void registerOutParameter(String parameterName, SQLType sqlType, String typeName) throws MintLeafException {
        try {
            callableStatement.registerOutParameter(parameterName, sqlType, typeName);
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

}
