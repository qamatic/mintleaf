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

import org.qamatic.mintleaf.DataRowListener;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.MintLeafLogger;
import org.qamatic.mintleaf.core.FluentJdbc;
import org.qamatic.mintleaf.core.ResultSetRowWrapper;

import java.sql.SQLException;

/**
 * Created by qamatic on 2/18/6/16.
 */
public class DbImportFlavour implements ImportFlavour {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(DbImportFlavour.class);
    private FluentJdbc fluentJdbc;

    public DbImportFlavour(FluentJdbc fluentJdbc) {

        this.fluentJdbc = fluentJdbc;
    }

    @Override
    public void doImport(DataRowListener listener) throws MintLeafException {
        final ResultSetRowWrapper dbRowWrapper = new ResultSetRowWrapper();
        int i = 0;
        try {
            while (this.fluentJdbc.getResultSet().next()) {
                dbRowWrapper.setResultSet(this.fluentJdbc.getResultSet());
                listener.eachRow(i++, dbRowWrapper);
            }
        } catch (SQLException e) {
            throw new MintLeafException(e);
        }
    }

    @Override
    public void close() throws MintLeafException {
        fluentJdbc.close();
    }

}
