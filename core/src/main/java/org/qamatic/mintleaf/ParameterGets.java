package org.qamatic.mintleaf;

import org.qamatic.mintleaf.MintLeafException;

import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.SQLWarning;

/**
 * Created by senips on 4/2/17.
 */
public interface ParameterGets {
    ParameterMetaData getParameterMetaData() throws MintLeafException;

    boolean isBatch();

    int getFetchDirection() throws MintLeafException;

    int getFetchSize() throws MintLeafException;

    ResultSet getGeneratedKeys() throws MintLeafException;

    long getLargeMaxRows() throws MintLeafException;

    long getLargeUpdateCount() throws MintLeafException;

    int getMaxFieldSize() throws MintLeafException;

    int getMaxRows() throws MintLeafException;

    boolean getMoreResults() throws MintLeafException;

    boolean getMoreResults(int current) throws MintLeafException;

    int getQueryTimeout() throws MintLeafException;

    ResultSet getResultSet() throws MintLeafException;

    int getResultSetConcurrency() throws MintLeafException;

    int getResultSetHoldability() throws MintLeafException;

    int getResultSetType() throws MintLeafException;

    int getUpdateCount() throws MintLeafException;

    SQLWarning getWarnings() throws MintLeafException;

    boolean isClosed() throws MintLeafException;

    boolean isCloseOnCompletion() throws MintLeafException;

    boolean isPoolable() throws MintLeafException;
}
