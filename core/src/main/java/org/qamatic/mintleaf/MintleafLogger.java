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

package org.qamatic.mintleaf;

/**
 * Created by qamatic on 2/16/16.
 */
public abstract class MintleafLogger {

    private static MintleafLogger mintLeafLogger;


    private static boolean singletonLogger = true;
    private static Class<? extends MintleafLogger> loggerType = ConsoleLogger.class;

    public synchronized static MintleafLogger getLogger(Class<?> clazz) {
        MintleafLogger logger = null;
        if (singletonLogger) {
            if ((mintLeafLogger == null)) {
                mintLeafLogger = getLoggerInstance(clazz);
            }
        } else {
            logger = getLoggerInstance(clazz);
            mintLeafLogger = logger;
            return logger;
        }
        return mintLeafLogger;
    }

    private static MintleafLogger getLoggerInstance(Class<?> clazz) {
        MintleafLogger logger = null;
        try {
            logger = loggerType.getDeclaredConstructor(Class.class).newInstance(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logger;
    }

    public static void setLoggerType(Class<? extends MintleafLogger> loggerType, boolean singletonLogger) {

        MintleafLogger.loggerType = loggerType;
        MintleafLogger.mintLeafLogger = null;
        MintleafLogger.singletonLogger = singletonLogger;

        if (loggerType == null) {
            loggerType = NoLogger.class;
            singletonLogger = true;//force to be at app level
        }
        if (singletonLogger){
            MintleafLogger.mintLeafLogger = getLoggerInstance(loggerType);
        }
    }

    public static boolean isSingletonLogger() {
        return singletonLogger;
    }

    public static Class<? extends MintleafLogger> getLoggerType() {
        return MintleafLogger.loggerType;
    }

    public static void setLoggerType(Class<? extends MintleafLogger> loggerType) {
        setLoggerType(loggerType, true);
    }

    public abstract void error(Throwable e);

    public abstract void error(String message, Throwable e);

    public abstract void debug(String message);

    public abstract void info(String message);

    public abstract void error(String message);

    public abstract void warn(String message);
}
