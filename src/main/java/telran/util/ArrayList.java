package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private Object[] array;
    private int size;

    public ArrayList(int capacity) {
        array = new Object[capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public boolean add(T obj) {
        if (size == array.length) {
            reallocate();
        }
        array[size++] = obj;
        return true;
    }

    private void reallocate() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        int index = indexOf(pattern);
        if (index >= 0 && index < size) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
            array[size--] = null;
            res = true;
        }
        return res;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T pattern) {
        return indexOf(pattern) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public void add(int index, T obj) {
        System.arraycopy(array, 0, array, 0, index);
        array[index] = obj;
        System.arraycopy(array, index, array, index + 1, size - index);
    }

    @Override
    public T remove(int index) {
        T removedObj = get(index);
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        return removedObj;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not correct");
        }
        return (T) array[index];
    }

    @Override
    public int indexOf(T pattern) {
        int i = 0;
        int res = -1;
        while (i < size && res == -1) {
            if (array[i].equals(pattern)) {
                res = i;
            }
            i++;
        }
        return res;
    }

    @Override
    public int lastIndexOf(T pattern) {
        int index = indexOf(pattern);
        int lastIndex = -1;
        if (index >= 0 && index < size) {
            lastIndex = findLastIndex(index);
        }
        return lastIndex;
    }

    private int findLastIndex(int index) {
        int i = size - 1;
        int res = index;
        while (i >= index && res == index) {
            if (array[i].equals(array[index])) {
                res = i;
            }
            i--;
        }
        return res;
    }

    private class ArrayListIterator implements Iterator<T> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) array[index++];
        }
    }
}
