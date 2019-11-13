package org.qamatic.mintleaf.readers;

import org.qamatic.mintleaf.ConnectionContext;
import org.qamatic.mintleaf.Executable;
import org.qamatic.mintleaf.MintleafException;

public class SqlFileExecute extends SqlFileStreamReader implements Executable<Boolean> {
    public SqlFileExecute(ConnectionContext connectionContext, String filename) {
        super(filename);
    }

    public SqlFileExecute(ConnectionContext connectionContext, String filename, String delimiter) {
        super(filename);
        setDelimiter(delimiter);
    }

    @Override
    public Boolean execute() throws MintleafException {


        return true;
    }

    @Override
    public void close() {
        super.close();
    }
}
