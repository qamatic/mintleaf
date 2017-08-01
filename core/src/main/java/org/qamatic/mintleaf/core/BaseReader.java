package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.ReadListener;
import org.qamatic.mintleaf.Row;

import java.nio.charset.Charset;

/**
 * Created by QAmatic Team on 4/28/17.
 */
public abstract class BaseReader {
    private ReadListener readListener;
    private Charset charset;


//    protected Boolean eachRow(int rowNum, Row row) throws MintleafException {
//        if (getReadListener() == null)
//            return true;
//
//        if (getReadListener().matches(row)) {
//            getReadListener().eachRow(rowNum, row);
//        }
//        if (!getReadListener().canContinueRead(row)) {
//            return false;
//        }
//        return true;
//    }
//
    public final ReadListener getReadListener() throws MintleafException {
        return readListener;
    }

    public void setReadListener(ReadListener readListener) {
        this.readListener = readListener;
    }

    public final Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }


    public Object eachRow(int rowNum, Row row) throws MintleafException {
        return null;
    }

    public boolean matches(Row row) {
        return true;
    }

    public boolean canContinueRead(Row row) {
        return true;
    }
}
