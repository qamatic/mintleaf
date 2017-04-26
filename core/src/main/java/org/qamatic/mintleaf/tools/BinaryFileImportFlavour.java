package org.qamatic.mintleaf.tools;

import org.qamatic.mintleaf.DataRowListener;
import org.qamatic.mintleaf.RowDelegate;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.Row;
import org.qamatic.mintleaf.core.BinaryReader;

import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by QAmatic Team on 4/11/17.
 */
public abstract class BinaryFileImportFlavour implements ImportFlavour, RowDelegate {

    private BinaryReader binaryReader;
    private Charset charset;
    private RowDelegate rowDelegate;

    public BinaryFileImportFlavour(BinaryReader binaryReader, Charset charset) {
        this.binaryReader = binaryReader;
        this.charset = charset;
    }

    @Override
    public abstract Row createRowInstance(Object... params);

    @Override
    public void doImport(DataRowListener listener) throws MintleafException {
        this.rowDelegate = listener;
        Iterator<byte[]> iterator = binaryReader.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            byte[] bytes = iterator.next();
            Row row = createRowInstance(bytes);
            row.setValues(bytes, this.charset);
            if (matches(row))
                listener.eachRow(i++, row);
            if (!canContinue(row)) {
                break;
            }
        }

    }

    @Override
    public boolean canContinue(Row row) {
        if (this.rowDelegate !=null)
            return this.rowDelegate.canContinue(row);
        return true;
    }

    @Override
    public boolean matches(Row row) {
        if (this.rowDelegate !=null)
            return this.rowDelegate.matches(row);
        return true;
    }
}
