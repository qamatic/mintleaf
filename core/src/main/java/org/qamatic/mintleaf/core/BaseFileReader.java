package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.ReadListener;

/**
 * Created by senips on 4/28/17.
 */
public class BaseFileReader {
    private ReadListener readListener;

    public ReadListener getReadListener()  throws MintleafException {
        return readListener;
    }

    public void setReadListener(ReadListener readListener) {
        this.readListener = readListener;
    }
}
