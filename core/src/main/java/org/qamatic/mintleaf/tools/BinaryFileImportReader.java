package org.qamatic.mintleaf.tools;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.MintleafReadListener;
import org.qamatic.mintleaf.Row;
import org.qamatic.mintleaf.core.BinaryReader;

import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by QAmatic Team on 4/11/17.
 */
public abstract class BinaryFileImportReader<T> implements ImportReader {

    private BinaryReader binaryReader;
    private Charset charset;


    public BinaryFileImportReader(BinaryReader binaryReader, Charset charset) {
        this.binaryReader = binaryReader;
        this.charset = charset;
    }

    public abstract Row createRowInstance(Object... params);

    @Override
    public T read(MintleafReadListener listener) throws MintleafException {
        Iterator<byte[]> iterator = binaryReader.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            byte[] bytes = iterator.next();
            Row row = createRowInstance(bytes);
            row.setValues(bytes, this.charset);
            if (listener.matches(row))
                listener.eachRow(i++, row);
            if (!listener.canContinue(row)) {
                break;
            }
        }
        return null;
    }


}
