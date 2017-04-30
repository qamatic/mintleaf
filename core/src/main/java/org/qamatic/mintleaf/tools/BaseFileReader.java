package org.qamatic.mintleaf.tools;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.ReadListener;

/**
 * Created by senips on 4/28/17.
 */
public class BaseFileReader {
    private ReadListener readListener;

    public ReadListener getReadListener() {
        if (this.readListener == null) {
            MintleafException.throwException("read listener can't be null");
        }
        return readListener;
    }

    public void setReadListener(ReadListener readListener) {
        this.readListener = readListener;
    }
}
