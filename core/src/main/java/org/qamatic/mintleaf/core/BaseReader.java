package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.*;

import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by QAmatic Team on 4/28/17.
 */
public class BaseReader {
    private ReadListener readListener;
    private Charset charset;


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
