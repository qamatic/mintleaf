package org.qamatic.mintleaf;

/**
 * Created by QAmatic Team on 4/8/17.
 */
public class NoLogger extends MintleafLogger {

    private Class<?> clazz;

    public NoLogger(Class<?> clazz) {

        this.clazz = clazz;
    }

    @Override
    public void error(Throwable e) {

    }

    @Override
    public void error(String message, Throwable e) {

    }

    @Override
    public void debug(String message) {

    }

    @Override
    public void info(String message) {

    }

    @Override
    public void error(String message) {

    }

    @Override
    public void warn(String message) {

    }
}
