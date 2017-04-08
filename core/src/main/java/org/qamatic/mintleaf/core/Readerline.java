package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.MintleafException;

/**
 * Created by QAmatic Team on 4/7/17.
 */
public interface Readerline {
    boolean processInternal(String line) throws MintleafException;
}
