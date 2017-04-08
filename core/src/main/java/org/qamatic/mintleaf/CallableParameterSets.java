package org.qamatic.mintleaf;

import java.sql.SQLType;

/**
 * Created by QAmatic Team on 4/2/17.
 */
public interface CallableParameterSets extends ParameterSets {
    void registerOutParameter(int parameterIndex, int sqlType) throws MintleafException;

    void registerOutParameter(int parameterIndex, int sqlType, int scale) throws MintleafException;

    void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws MintleafException;

    void registerOutParameter(int parameterIndex, SQLType sqlType) throws MintleafException;

    void registerOutParameter(int parameterIndex, SQLType sqlType, int scale) throws MintleafException;

    void registerOutParameter(int parameterIndex, SQLType sqlType, String typeName) throws MintleafException;

    void registerOutParameter(String parameterName, int sqlType) throws MintleafException;

    void registerOutParameter(String parameterName, int sqlType, int scale) throws MintleafException;

    void registerOutParameter(String parameterName, int sqlType, String typeName) throws MintleafException;

    void registerOutParameter(String parameterName, SQLType sqlType) throws MintleafException;

    void registerOutParameter(String parameterName, SQLType sqlType, int scale) throws MintleafException;

    void registerOutParameter(String parameterName, SQLType sqlType, String typeName) throws MintleafException;
}
