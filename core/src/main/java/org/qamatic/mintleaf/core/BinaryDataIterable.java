package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintleafException;

/**
 * Created by QAmatic Team on 4/17/17.
 */
public interface BinaryDataIterable extends Iterable<byte[]>, AutoCloseable {
    long getCurrentPos() throws MintleafException;

    BinaryDataIterable recordAt(int recordNumber) throws MintleafException;

    BinaryDataIterable recordSize(int recordSize) throws MintleafException;

    BinaryDataIterable reset() throws MintleafException;

    BinaryDataIterable reset(long bytesPos) throws MintleafException;

    default void close() {

    }

}
