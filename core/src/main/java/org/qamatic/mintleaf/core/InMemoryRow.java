package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.*;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by QAmatic Team on 4/16/17.
 */
public class InMemoryRow implements Row {
    private MetaDataCollection metaDataCollection;
    private List<Object> rowValues = new ArrayList<>();

    public InMemoryRow(){

    }

    public InMemoryRow(MetaDataCollection metaDataCollection){
        this.metaDataCollection = metaDataCollection;
    }


    @Override
    public Object getValue(int columnIndex) throws MintleafException {
        return rowValues.get(columnIndex);
    }

    @Override
    public MetaDataCollection getMetaData() throws MintleafException {
        return this.metaDataCollection;
    }

    @Override
    public void setMetaData(MetaDataCollection metaDataCollection) {
        this.metaDataCollection = metaDataCollection;
    }


    @Override
    public void setValue(int columnIndex, Object value){
        getValues().add(value);//override if you need differently
    }

    @Override
    public void setValue(int columnIndex, byte[] value, Charset charset){
        setValue(columnIndex, new String(value, charset).trim());
    }

    @Override
    public void setValues(byte[] byteRecord, Charset charset) {

        int bstart = 0;
        try {
            if (getMetaData() == null){
                MintleafException.throwException("metadata must be set on the object before calling this method");
            }
            for (int i = 0; i < getMetaData().getColumnCount(); i++) {
                Column c = getMetaData().getColumn(i);
                byte[] bytes = Arrays.copyOfRange(byteRecord, bstart, bstart+c.getColumnSize());
                bstart += bytes.length;
                setValue(i, bytes, charset);
            }
        } catch (SQLException e) {
            MintleafException.throwException(e);
        } catch (MintleafException e) {
            MintleafException.throwException(e);
        }
    }

    public List<Object> getValues(){
        return rowValues;
    }
}
