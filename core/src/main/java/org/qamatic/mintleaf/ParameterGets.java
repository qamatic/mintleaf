package org.qamatic.mintleaf;

import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.SQLWarning;

/**
 * Created by QAmatic Team on 4/2/17.
 */
public interface ParameterGets {
    ParameterMetaData getParameterMetaData() throws MintleafException;

    boolean isBatch();

    int getFetchDirection() throws MintleafException;

    int getFetchSize() throws MintleafException;

    ResultSet getGeneratedKeys() throws MintleafException;

    long getLargeMaxRows() throws MintleafException;

    long getLargeUpdateCount() throws MintleafException;

    int getMaxFieldSize() throws MintleafException;

    int getMaxRows() throws MintleafException;

    boolean getMoreResults() throws MintleafException;

    boolean getMoreResults(int current) throws MintleafException;

    int getQueryTimeout() throws MintleafException;

    ResultSet getResultSet() throws MintleafException;

    int getResultSetConcurrency() throws MintleafException;

    int getResultSetHoldability() throws MintleafException;

    int getResultSetType() throws MintleafException;

    int getUpdateCount() throws MintleafException;

    SQLWarning getWarnings() throws MintleafException;

    boolean isClosed() throws MintleafException;

    boolean isCloseOnCompletion() throws MintleafException;

    boolean isPoolable() throws MintleafException;
}
