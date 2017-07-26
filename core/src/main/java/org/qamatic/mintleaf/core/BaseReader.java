package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.*;

import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by QAmatic Team on 4/28/17.
 */
public abstract class BaseReader  {
    private ReadListener readListener;
    private Charset charset;

    protected Boolean listenerCast(int rowNum, Row row) throws MintleafException {
        if (getReadListener() == null)
            return true;

        if (getReadListener().matches(row)) {
            getReadListener().eachRow(rowNum, row);
        }
        if (!getReadListener().canContinueRead(row)) {
            return false;
        }
        return true;
    }

    public final ReadListener getReadListener() throws MintleafException {
        return readListener;
    }

    public void setReadListener(ReadListener readListener) {
        this.readListener = readListener;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }


    public final Charset getCharset() {
        return charset;
    }


}
