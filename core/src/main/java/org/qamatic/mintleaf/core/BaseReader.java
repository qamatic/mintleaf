package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QAmatic Team on 4/28/17.
 */
public abstract class BaseReader<T extends Row> implements MintleafReader {
    private final static MintleafLogger logger = MintleafLogger.getLogger(BaseReader.class);
    private List<ReadListener> preProcessors = new ArrayList<>();
    private List<ReadListener> postProcessors = new ArrayList<>();
    private Charset charset;
    private String delimiter = "/";
    private Map<String, Object> userVariableMapping;
    private Class<T> rowClassType;

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
            if (!canContinueRead(row)) {
                return false;
            }
            for (ReadListener listener : getPreProcessors()) {
                listener.eachRow(rowNum, row);
                if (!canContinueRead(row)) {
                    return false;
                }
            }
            for (ReadListener listener : getPostProcessors()) {
                listener.eachRow(rowNum, row);
                if (!canContinueRead(row)) {
                    return false;
                }
            }
        }
        return true;
    }

    public final List<ReadListener> getPreProcessors() throws MintleafException {
        return preProcessors;
    }


    public final List<ReadListener> getPostProcessors() throws MintleafException {
        return postProcessors;
    }


    public final Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }


    @Override
    public Map<String, Object> getUserVariableMapping() {
        if (userVariableMapping == null) {
            userVariableMapping = new HashMap<>();
        }
        return userVariableMapping;
    }

    @Override
    public void setUserVariableMapping(Map userVariableMapping) {
        this.userVariableMapping = userVariableMapping;
    }

    @Override
    public String getDelimiter() {
        return delimiter;
    }

    @Override
    public void setDelimiter(String delimStr) {
        this.delimiter = delimStr.toUpperCase();
    }

    @Override
    public abstract void read() throws MintleafException;

    public Class<T> getRowClassType() {
        return rowClassType;
    }

    public void setRowClassType(Class<T> rowClassType) {
        this.rowClassType = rowClassType;
    }

    public T eachRow(int rowNum, Row row) throws MintleafException {
        return (T) row;
    }
}
