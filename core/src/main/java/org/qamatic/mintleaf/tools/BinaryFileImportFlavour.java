package org.qamatic.mintleaf.tools;

import org.qamatic.mintleaf.DataRowListener;
import org.qamatic.mintleaf.MintleafException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by QAmatic Team on 4/11/17.
 */
public class BinaryFileImportFlavour implements ImportFlavour, Iterable<byte[]> {

    private int recordSize;
    private File filePath;
    private RandomAccessFile reader;

    public BinaryFileImportFlavour(File filePath, int recordSize) {
        this.filePath = filePath;
        this.recordSize = recordSize;
    }

    private RandomAccessFile getReader() throws MintleafException {
        if (reader == null) {
            try {
                reader = new RandomAccessFile(filePath, "r");
            }  catch (IOException e) {
                throw new MintleafException(e);
            }
        }
        return reader;
    }


    @Override
    public void doImport(DataRowListener listener) throws MintleafException {
        Iterator<byte[]> iterator = iterator();
        while (iterator.hasNext()) {
            System.out.println(new String(iterator.next(), Charset.forName("Cp1047")));
            if (!listener.canContinue()){
                break;
            }
        }
    }

    @Override
    public void close() throws MintleafException {
        try {
            reader.close();
        } catch (IOException e) {
            throw new MintleafException(e);
        }
    }

    @Override
    public Iterator<byte[]> iterator() {
        try {
            return new BinaryFileIterator(getReader(), this.recordSize);
        } catch (MintleafException e) {
            MintleafException.throwException(e);
        }
        return null;
    }

    private static final class BinaryFileIterator implements Iterator<byte[]> {

        private RandomAccessFile file;
        private int recordSize;
        private int currentRecordIndex = -1;
        private long fileLength = -1;
        private final byte[] chunk;

        public BinaryFileIterator(RandomAccessFile file, int recordSize) {
            this.file = file;
            this.recordSize = recordSize;
            currentRecordIndex = -1;
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
                return (this.recordSize * (this.currentRecordIndex + 1)) < getFileLength();
            } catch (MintleafException e) {
                MintleafException.throwException(e);
            }
            return false;
        }

        public void moveTo(int recordIdx) throws MintleafException {
            this.currentRecordIndex = recordIdx;

            try {
                file.seek(recordIdx * this.recordSize);
            } catch (IOException e) {
                throw new MintleafException(e);
            }
        }

        @Override
        public byte[] next() {
            if (!hasNext()) {
                MintleafException.throwException("eof reached, use hasNext() to deteremine");
            }
            this.currentRecordIndex++;
            try {
                moveTo(this.currentRecordIndex);
                file.readFully(chunk, 0, this.recordSize);
            } catch (MintleafException e) {
                MintleafException.throwException(e);
            } catch (IOException e) {
                MintleafException.throwException(e);
            }
            return chunk;
        }
    }
}
