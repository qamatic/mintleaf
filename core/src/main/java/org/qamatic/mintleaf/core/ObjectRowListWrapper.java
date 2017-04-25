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
import org.qamatic.mintleaf.Row;
import org.qamatic.mintleaf.RowListWrapper;

import java.util.*;

/**
 * Created by qamatic on 3/4/16.
 */
public class ObjectRowListWrapper<T extends Row> implements RowListWrapper<T>, List<T> {

    private final List<T> list;
    private MetaDataCollection metaDataCollection;

    public ObjectRowListWrapper(List<T> list, MetaDataCollection metaDataCollection) {
        this.metaDataCollection = metaDataCollection;
        this.list = list;
    }

    public ObjectRowListWrapper(MetaDataCollection metaDataCollection) {
        this.metaDataCollection = metaDataCollection;
        this.list = new ArrayList<>();
    }

    public ObjectRowListWrapper(){
        this.list = new ArrayList<>();
    }

    @Override
    public MetaDataCollection getMetaData() throws MintleafException {
        return this.metaDataCollection;
    }


    @Override
    public T getRow(int index) throws MintleafException {
        return (T) get(index);
    }


    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.list.contains(o);
    }

    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        setMetaData(t);
        return list.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return this.list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return this.list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.list.remove(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.list.retainAll(c);
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public T get(int index) {
        T row = this.list.get(index);
        setMetaData(row);
        return (T) row;
    }

    @Override
    public T set(int index, T element) {
        return this.list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        element.setMetaData(this.metaDataCollection);
        this.list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return this.list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ObjectListIterator<T>(this.list.listIterator(), this.metaDataCollection);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ObjectListIterator<T>(this.list.listIterator(index), this.metaDataCollection);
    }

    private void setMetaData(Row row){
        if (this.metaDataCollection != null) {
            row.setMetaData(this.metaDataCollection);
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }

    @Override
    public Iterator<T> iterator() {
        return listIterator();
    }

    private static final class ObjectListIterator<T extends Row> implements ListIterator<T> {

        private final ListIterator<T> listIterator;
        private final MetaDataCollection metaDataCollection;

        public ObjectListIterator(ListIterator<T> listIterator, MetaDataCollection metaDataCollection) {

            this.listIterator = listIterator;
            this.metaDataCollection = metaDataCollection;
        }

        @Override
        public void add(T t) {
            setMetaData(t);
            listIterator.add(t);
        }

        @Override
        public boolean hasNext() {
            return listIterator.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return listIterator.hasPrevious();
        }

        @Override
        public T next() {
            T t = listIterator.next();
            setMetaData(t);
            return t;
        }

        @Override
        public int nextIndex() {
            return listIterator.nextIndex();
        }

        @Override
        public T previous() {
            T  t = listIterator.previous();
            setMetaData(t);
            return t;
        }

        @Override
        public int previousIndex() {
            return listIterator.previousIndex();
        }

        @Override
        public void remove() {
            listIterator.remove();
        }

        @Override
        public void set(T t) {
            setMetaData(t);
            listIterator.set(t);
        }

        private void setMetaData(Row row){
            if (this.metaDataCollection != null) {
                row.setMetaData(this.metaDataCollection);
            }
        }
    }
}
