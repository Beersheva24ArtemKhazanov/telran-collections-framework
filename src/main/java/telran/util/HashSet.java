package telran.util;

import java.util.Iterator;

import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class HashSet<T> implements Set<T> {
    private static final int DEFAULT_HASH_TABLE_LENGTH = 16;
    private static final float DEFAULT_FACTOR = 0.75f;
    List<T>[] hashTable;
    float factor;
    int size;
    private class HashSetIterator implements Iterator<T> {
        //Hint:
        int indexIterator;
        Iterator<T> currentIterator;
        Iterator<T> prevIterator;

        HashSetIterator() {
			indexIterator = 0;
			currentIterator = getIterator(0);
			setIteratorIndex();
		}

        private Iterator<T> getIterator(int index) {
			List<T> list = hashTable[index];
			return list == null ? null : list.iterator();
		}

        private void setIteratorIndex() {
            int limit = hashTable.length - 1;
			while (indexIterator < limit && (currentIterator == null || !currentIterator.hasNext())) {
                currentIterator = getIterator(++indexIterator);
			}
            if (indexIterator == limit && (hashTable[indexIterator] == null || !currentIterator.hasNext())) {
				currentIterator = null;
			}
        }

        @Override
        public boolean hasNext() {
            return currentIterator != null ;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T res = currentIterator.next(); 
            prevIterator = currentIterator; 
            setIteratorIndex();
            return res;
        }
        @Override
        public void remove() {
            if (prevIterator == null) {
                throw new IllegalStateException();
            }

            prevIterator.remove();
            prevIterator = null;
            size--;
        }
    }

    public HashSet(int hashTableLength, float factor) {
        hashTable = new List[hashTableLength];
        this.factor = factor;
    }

    public HashSet() {
        this(DEFAULT_HASH_TABLE_LENGTH, DEFAULT_FACTOR);
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            if (size >= hashTable.length * factor) {
                hashTableReallocation();
            }

            addObjInHashTable(obj, hashTable);
            size++;
        }
        return res;

    }

    private void addObjInHashTable(T obj, List<T>[] table) {
        int index = getIndex(obj, table.length);
        List<T> list = table[index];
        if (list == null) {
            list = new ArrayList<>(3);
            table[index] = list;
        }
        list.add(obj);
    }

    private int getIndex(T obj, int length) {
        int hashCode = obj.hashCode();
        return Math.abs(hashCode % length);
    }

    private void hashTableReallocation() {
       List<T> []tempTable = new List[hashTable.length * 2];
       for(List<T> list: hashTable) {
        if(list != null) {
            list.forEach(obj -> addObjInHashTable(obj, tempTable));
            list.clear(); //??? for testing if it doesn't work remove this statement
        }
       }
       hashTable = tempTable;

    }

    @Override
    public boolean remove(T pattern) {
        boolean removed = false;
        if (contains(pattern)) {
            int index = getIndex(pattern, hashTable.length);
            List<T> list = hashTable[index];
            list.remove(pattern);
            size--;
            removed = true;
        }
        return removed;
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
        int index = getIndex(pattern, hashTable.length);
        List<T> list = hashTable[index];
        return list != null && list.contains(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new HashSetIterator();
    }

    @Override
    public T get(Object pattern) {
        T res = null;
        int index = getIndex((T) pattern, hashTable.length);
        List<T> list = hashTable[index];

        if (list != null) {
            int i = 0;
            while (i < list.size() && res == null) {
                T element = list.get(i);
                if (element.equals(pattern)) {
                    res = element;
                }
                i++;
            }
        }
		return res;
    }

}