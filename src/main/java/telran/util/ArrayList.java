package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

@SuppressWarnings("unchecked")
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
        reallocationIfNeeded();
        array[size++] = obj;
        return true;
    }

    private void reallocationIfNeeded() {
        if (size == array.length) {
            reallocate();
        }
    }

    private void reallocate() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        int index = indexOf(pattern);
        if (index >= 0) {
            res = true;
            remove(index);
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
        checkIndex(index, true);
        reallocationIfNeeded();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = obj;
        size++;
    }

    private void checkIndex(int index, boolean sizeInclusive) {
        int limit = sizeInclusive ? size : size - 1;
        if (index < 0 || index > limit) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        T res = (T) array[index];
        size--;
        System.arraycopy(array, index + 1, array, index, size - index);
        return res;
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return (T) array[index];
    }

    @Override
    public int indexOf(T pattern) {
        int index = 0;
        while (index < size && !Objects.equals(array[index], pattern)) {
            index++;
        }
        return index == size ? -1 : index;
    }

    @Override
    public int lastIndexOf(T pattern) {
        int index = size - 1;
        while (index >= 0 && !Objects.equals(array[index], pattern)) {
            index--;
        }
        return index;
    }

    private class ArrayListIterator implements Iterator<T> {
        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) array[currentIndex++];
        }

    }

}