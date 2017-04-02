package org.qamatic.mintleaf;

import org.qamatic.mintleaf.MintLeafException;

import java.sql.SQLType;

/**
 * Created by senips on 4/2/17.
 */
public interface CallableParameterSets extends ParameterSets {
    void registerOutParameter(int parameterIndex, int sqlType) throws MintLeafException;

    void registerOutParameter(int parameterIndex, int sqlType, int scale) throws MintLeafException;

    void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws MintLeafException;

    void registerOutParameter(int parameterIndex, SQLType sqlType) throws MintLeafException;

    void registerOutParameter(int parameterIndex, SQLType sqlType, int scale) throws MintLeafException;

    void registerOutParameter(int parameterIndex, SQLType sqlType, String typeName) throws MintLeafException;

    void registerOutParameter(String parameterName, int sqlType) throws MintLeafException;

    void registerOutParameter(String parameterName, int sqlType, int scale) throws MintLeafException;

    void registerOutParameter(String parameterName, int sqlType, String typeName) throws MintLeafException;

    void registerOutParameter(String parameterName, SQLType sqlType) throws MintLeafException;

    void registerOutParameter(String parameterName, SQLType sqlType, int scale) throws MintLeafException;

    void registerOutParameter(String parameterName, SQLType sqlType, String typeName) throws MintLeafException;
}
