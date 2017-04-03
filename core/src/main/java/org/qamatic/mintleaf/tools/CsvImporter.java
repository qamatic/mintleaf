/*
 *
 *   *
 *   *  *
 *   *  *   ~
 *   *  *   ~ The MIT License (MIT)
 *   *  *   ~
 *   *  *   ~ Copyright (c) 2010-2017 QAMatic Team
 *   *  *   ~
 *   *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *   *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *   *  *   ~ in the Software without restriction, including without limitation the rights
 *   *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *   *  *   ~ furnished to do so, subject to the following conditions:
 *   *  *   ~
 *   *  *   ~ The above copyright notice and this permission notice shall be included in all
 *   *  *   ~ copies or substantial portions of the Software.
 *   *  *   ~
 *   *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   *  *   ~ SOFTWARE.
 *   *  *   ~
 *   *  *   ~
 *   *  *
 *   *
 *   *
 *
 * /
 */

package org.qamatic.mintleaf.tools;

import org.qamatic.mintleaf.ConnectionContext;
import org.qamatic.mintleaf.Executable;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.MintLeafLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by qamatic on 3/6/16.
 */
public class CsvImporter extends ImpExpBase implements Executable<Boolean> {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(CsvImporter.class);
    private String sourceCsvFile;
    private ConnectionContext targetDb;
    private String targetSqlTemplate;


    public CsvImporter(String sourceCsvFile, ConnectionContext targetDb,
                       String targetSqlTemplate) {
        this.sourceCsvFile = sourceCsvFile;
        this.targetDb = targetDb;
        this.targetSqlTemplate = targetSqlTemplate;
    }

    @Override
    public Boolean execute() throws MintLeafException {

        try {
            File f = new File(this.sourceCsvFile);
            if (!f.exists()) {
                logger.error("file not found " + sourceCsvFile);
                throw new MintLeafException("file not found " + sourceCsvFile);
            }
            importDataFrom(createFlavour(f), this.targetSqlTemplate);
            return true;

        } catch (IOException e) {
            throw new MintLeafException(e);
        }
    }

    protected CsvImportFlavour createFlavour(File f) throws FileNotFoundException {
        return new CsvImportFlavour(new FileReader(f));
    }


    @Override
    protected ConnectionContext getConnectionContext() {
        return targetDb;
    }
}