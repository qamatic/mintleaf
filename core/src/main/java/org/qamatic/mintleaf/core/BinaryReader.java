package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintleafException;

/**
 * Created by senips on 4/17/17.
 */
public interface BinaryReader extends Iterable<byte[]>, AutoCloseable {
    long getCurrentPos() throws MintleafException;

    BinaryReader recordAt(int recordNumber) throws MintleafException;

    BinaryReader recordSize(int recordSize) throws MintleafException;

    BinaryReader reset() throws MintleafException;

    BinaryReader reset(long bytesPos) throws MintleafException;

    @Override
    void close();
}
