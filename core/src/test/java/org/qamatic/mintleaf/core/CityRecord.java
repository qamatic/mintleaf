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


import org.qamatic.mintleaf.MetaDataCollection;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.core.rows.InMemoryRow;

/**
 * Created by QAmatic Team on 3/11/17.
 */
public class CityRecord extends InMemoryRow {

    public CityRecord() {

    }

    public CityRecord(MetaDataCollection metaDataCollection) {
        super(metaDataCollection);
    }


    public CityRecord(int id, String city, String state, String country) {
        getValues().add(id);
        getValues().add(city);
        getValues().add(state);
        getValues().add(country);
    }


    @Override
    public void setValue(int columnIndex, Object value) {
        super.setValue(columnIndex, value.toString().replaceAll("\\*", ""));
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        try {
            sb.append(formattedStr(0));
            sb.append(formattedStr(1));
            sb.append(formattedStr(2));
            sb.append(formattedStr(3));

        } catch (MintleafException e) {
            MintleafException.throwException(e);
        }
        return sb.toString();
    }

    private String formattedStr(int idx) throws MintleafException {
        return String.format("%-" + getMetaData().getColumn(idx).getColumnSize() + "s", getValue(idx).toString()).replace(' ', '*');
    }

    public int getId() {

        return asInt("Id");

    }

}
