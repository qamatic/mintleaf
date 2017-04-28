package org.qamatic.mintleaf.tools;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafReadListener;

/**
 * Created by senips on 4/28/17.
 */
public class BaseFileReader {
    private MintleafReadListener readListener;

    public MintleafReadListener getReadListener() {
        if (this.readListener == null) {
            MintleafException.throwException("read listener can't be null");
        }
        return readListener;
    }

    public void setReadListener(MintleafReadListener readListener) {
        this.readListener = readListener;
    }
}
