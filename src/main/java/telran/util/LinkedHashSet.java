package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import telran.util.LinkedList.Node;

public class LinkedHashSet<T> implements Set<T> {
    private LinkedList<T> list = new LinkedList<>();
    HashMap<T, Node<T>> map = new HashMap<>();

    private class LinkedHashSetIterator implements Iterator<T> {
        private final Iterator<T> it = list.iterator();
        private T current = null;

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = it.next();
            return current;
        }

        @Override
        public void remove() {
            it.remove();
            map.remove(current);
        }

    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            Node<T> node = new Node<>(obj);
            list.addNode(node, list.size());
            map.put(obj, node);
        }
        return res;
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        Node<T> node = map.get(pattern);

        if (node != null) {
            list.removeNode(node);
            map.remove(pattern);
            res = true;
        }
        return res;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(T pattern) {
        return list.contains(pattern);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedHashSetIterator();
    }

    @Override
    public T get(Object pattern) {
        Node<T> node = map.get(pattern);
        return node == null ? null : node.obj;
    }

}
