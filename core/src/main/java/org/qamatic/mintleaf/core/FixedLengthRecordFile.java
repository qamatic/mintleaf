package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.Row;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * Created by QAmatic Team on 4/11/17.
 */
public class FixedLengthRecordFile<T extends Row> implements BinaryDataIterable {

    private int recordSize;
    private File filePath;
    private RandomAccessFile reader;
    private int recordNumber;
    private long offset;

    public FixedLengthRecordFile(File filePath, int recordSize) {
        this.filePath = filePath;
        this.recordSize = recordSize;
        this.offset = 0;
    }


    public FixedLengthRecordFile(File filePath) {
        this.filePath = filePath;
        this.recordSize = 1;
    }


    private RandomAccessFile getReader() throws MintleafException {
        if (reader == null) {
            try {
                reader = new RandomAccessFile(filePath, "r");
            } catch (IOException e) {
                throw new MintleafException(e);
            }
        }
        return reader;
    }

    @Override
    public long getCurrentPos() throws MintleafException {
        try {
            return getReader().getFilePointer();
        } catch (IOException e) {
            throw new MintleafException(e);
        } catch (MintleafException e) {
            throw new MintleafException(e);
        }
    }


    @Override
    public BinaryDataIterable recordAt(int recordNumber) throws MintleafException {
        this.recordNumber = recordNumber - 1;
        return this;
    }

    @Override
    public BinaryDataIterable recordSize(int recordSize) throws MintleafException {
        this.recordSize = recordSize;
        return this;
    }

    @Override
    public BinaryDataIterable reset() throws MintleafException {

        return reset(0);
    }

    @Override
    public BinaryDataIterable reset(long bytesPos) throws MintleafException {
        try {
            this.recordNumber = 0;
            getReader().seek(bytesPos);
            offset = getCurrentPos();
        } catch (IOException e) {
            throw new MintleafException(e);
        }
        return this;
    }

    @Override
    public Iterator<byte[]> iterator() {
        try {
            return new BinaryFileIterator(getReader(), this.recordSize, this.recordNumber, this.offset);
        } catch (MintleafException e) {
            MintleafException.throwException(e);
        }
        return null;
    }


    @Override
    public void close() {
        if (reader != null)
            try {
                reader.close();
            } catch (IOException e) {
                MintleafException.throwException(e);
            }
    }

    private static final class BinaryFileIterator implements Iterator<byte[]> {

        private final RandomAccessFile file;
        private final int recordSize;
        private final byte[] chunk;
        private int currentRecordIndex;
        private long offset;
        private long fileLength = -1;

        public BinaryFileIterator(RandomAccessFile file, int recordSize, int startAt, long offset) {
            this.file = file;
            this.recordSize = recordSize;
            currentRecordIndex = startAt;
            this.offset = offset;
            chunk = new byte[recordSize];
        }

        private long getFileLength() throws MintleafException {
            if (this.fileLength == -1) {
                try {
                    this.fileLength = file.length();
                } catch (IOException e) {
                    throw new MintleafException(e);
                }
            }
            return this.fileLength;
        }


        @Override
        public boolean hasNext() {
            try {
                return this.offset + (this.recordSize * (this.currentRecordIndex + 1)) <= getFileLength();
            } catch (MintleafException e) {
                MintleafException.throwException(e);
            }
            return false;
        }

        public void moveToCurrent() throws MintleafException {
            try {
                file.seek(this.offset + (this.currentRecordIndex * this.recordSize));
            } catch (IOException e) {
                throw new MintleafException(e);
            }
        }

        @Override
        public byte[] next() {

            try {
                moveToCurrent();
                file.readFully(chunk, 0, this.recordSize);
                this.currentRecordIndex++;
            } catch (MintleafException e) {
                MintleafException.throwException(e);
            } catch (IOException e) {
                MintleafException.throwException(e);
            }
            return chunk;
        }
    }
}
