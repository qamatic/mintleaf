package org.qamatic.mintleaf.tools;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafReader;
import org.qamatic.mintleaf.Row;
import org.qamatic.mintleaf.core.BaseReader;
import org.qamatic.mintleaf.core.BinaryDataIterable;

import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by QAmatic Team on 4/11/17.
 */
public abstract class BinaryDataReader<T> extends BaseReader implements MintleafReader {


    private BinaryDataIterable binaryDataIterable;

    public BinaryDataReader(BinaryDataIterable binaryDataIterable, Charset charset) {
        this.binaryDataIterable = binaryDataIterable;
        setCharset(charset);
    }

    public abstract Row createRowInstance(byte[] rowChunk);

    @Override
    public T read() throws MintleafException {
        Iterator<byte[]> iterator = this.binaryDataIterable.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            byte[] bytes = iterator.next();
            Row row = createRowInstance(bytes);
            row.setValues(bytes, getCharset());
            if (!readRow(i++, row)) {
                break;
            }
        }
        return null;
    }

    public BinaryDataIterable getIterator() {
        return binaryDataIterable;
    }


}
