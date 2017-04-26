package org.qamatic.mintleaf;

/**
 * Created by senips on 4/25/17.
 */
public interface RowDelegate {
    default boolean matches(Row row){
        return true;
    }
    default boolean canContinue(Row row){
        return true;
    }
    default Row createRowInstance(Object... params){
        return null;
    }
}
