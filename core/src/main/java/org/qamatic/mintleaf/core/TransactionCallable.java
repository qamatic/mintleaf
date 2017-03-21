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

package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.DbCallable;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.MintLeafLogger;

import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * Created by QAmatic Team on 3/19/17.
 */
public class TransactionCallable<T> implements Callable<T> {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(TransactionCallable.class);
    private DbCallable dbCallable;

    public TransactionCallable(DbCallable dbCallable) {

        this.dbCallable = dbCallable;
    }

    public T doCall() throws MintLeafException {
        T t = null;
        boolean prevStatus = true;
        Object prevStatusNullableObject = null;
        try {
            prevStatus = this.dbCallable.getConnection().getAutoCommit();
            this.dbCallable.getConnection().setAutoCommit(false);
            prevStatusNullableObject = prevStatus;
            t = (T) this.dbCallable.execute();
            this.dbCallable.getConnection().commit();

        } catch (SQLException e) {
            rollback();
            logger.error("transaction error:", e);
            throw new MintLeafException(e);
        } catch (Exception e) {
            rollback();
            logger.error("transaction error:", e);
            throw new MintLeafException(e);
        } finally {
            try {
                if (prevStatusNullableObject != null) {
                    this.dbCallable.getConnection().setAutoCommit(prevStatus);
                }
            } catch (SQLException e) {
                logger.error("transaction error/error restoring previous autocommit status:", e);
                throw new MintLeafException(e);
            }
        }

        return t;
    }

    private void rollback() {
        try {
            this.dbCallable.getConnection().rollback();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public T call() throws Exception {
        return doCall();
    }
}
