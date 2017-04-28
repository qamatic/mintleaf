package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.Row;

/**
 * Created by QAmatic Team on 4/16/17.
 */
public class ByteArrayRow extends InMemoryRow {

    private byte[] bytes;

    public ByteArrayRow(byte[] bytes) {

        this.bytes = bytes;
    }

    @Override
    public Object getValue(int columnIndex) throws MintleafException {
        if (columnIndex == Row.INTERNAL_OBJECT_VALUE) {
            return this.bytes;
        }
        return super.getValue(columnIndex);
    }
}
