package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.ReadListener;

import java.nio.charset.Charset;

/**
 * Created by QAmatic Team on 4/17/17.
 */
public interface BinaryReader extends Iterable<byte[]>, AutoCloseable {
    long getCurrentPos() throws MintleafException;

    BinaryReader recordAt(int recordNumber) throws MintleafException;

    BinaryReader recordSize(int recordSize) throws MintleafException;

    BinaryReader reset() throws MintleafException;

    BinaryReader reset(long bytesPos) throws MintleafException;

    @Override
    void close();

    void iterate(Charset charset, ReadListener listener) throws MintleafException;
}
