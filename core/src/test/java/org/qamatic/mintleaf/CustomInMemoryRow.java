package org.qamatic.mintleaf;


import org.qamatic.mintleaf.core.rows.InMemoryRow;

public class CustomInMemoryRow extends InMemoryRow {
    private int name;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
