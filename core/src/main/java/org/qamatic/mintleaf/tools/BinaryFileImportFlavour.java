package org.qamatic.mintleaf.tools;

import org.qamatic.mintleaf.DataRowListener;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.Row;
import org.qamatic.mintleaf.core.BinaryReader;
import org.qamatic.mintleaf.core.ByteArrayRow;

import java.util.Iterator;

/**
 * Created by QAmatic Team on 4/11/17.
 */
public class BinaryFileImportFlavour implements ImportFlavour {

    private BinaryReader binaryReader;

    public BinaryFileImportFlavour(BinaryReader binaryReader) {
        this.binaryReader = binaryReader;
    }

    protected Row createRowInstance(byte[] bytes) {
        return new ByteArrayRow(bytes);
    }


    @Override
    public void doImport(DataRowListener listener) throws MintleafException {

        Iterator<byte[]> iterator = binaryReader.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Row row = createRowInstance(iterator.next());
            listener.eachRow(i++, row);
            if (!listener.canContinue()) {
                break;
            }
        }

    }

}
