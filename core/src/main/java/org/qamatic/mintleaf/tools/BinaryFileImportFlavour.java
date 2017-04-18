package org.qamatic.mintleaf.tools;

import org.qamatic.mintleaf.DataRowListener;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.Row;
import org.qamatic.mintleaf.core.BinaryReader;

import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by QAmatic Team on 4/11/17.
 */
public abstract class BinaryFileImportFlavour implements ImportFlavour {

    private BinaryReader binaryReader;
    private Charset charset;

    public BinaryFileImportFlavour(BinaryReader binaryReader, Charset charset) {
        this.binaryReader = binaryReader;
        this.charset = charset;
    }

    public abstract Row createRowInstance();

    @Override
    public void doImport(DataRowListener listener) throws MintleafException {

        Iterator<byte[]> iterator = binaryReader.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Row row = createRowInstance();
            row.setValues(iterator.next(), this.charset);
            listener.eachRow(i++, row);
            if (!listener.canContinue()) {
                break;
            }
        }

    }

}
