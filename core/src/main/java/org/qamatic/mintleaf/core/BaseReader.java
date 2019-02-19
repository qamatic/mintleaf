package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafLogger;
import org.qamatic.mintleaf.ReadListener;
import org.qamatic.mintleaf.Row;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by QAmatic Team on 4/28/17.
 */
public abstract class BaseReader {
    private final static MintleafLogger logger = MintleafLogger.getLogger(BaseReader.class);
    private ReadListener readListener;
    private Charset charset;

    public static InputStream getInputStreamFromFile(String resourceOrFileName) {
        InputStream stream = null;
        logger.info("reading file: " + resourceOrFileName);
        if (resourceOrFileName.startsWith("res:")) {
            String resFile = resourceOrFileName.substring(4);

            stream = Thread.currentThread().getContextClassLoader().getClass().getResourceAsStream(resFile);
            if (stream == null) {
                logger.error("file not found " + resourceOrFileName);
            }
        } else {
            try {
                stream = new FileInputStream(resourceOrFileName);
            } catch (FileNotFoundException e) {
                logger.error("file not found " + resourceOrFileName, e);
            }
        }
        return stream;
    }

    protected final boolean readRow(int rowNum, Row row) throws MintleafException {
        if (matches(row)) {
            eachRow(rowNum, row);
            if (getReadListener() != null) {
                getReadListener().eachRow(rowNum, row);
            }
        }
        if (!canContinueRead(row)) {
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
