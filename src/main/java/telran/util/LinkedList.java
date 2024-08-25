package telran.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {
    private static class Node<T> {
        T obj;
        Node<T> next;
        Node<T> prev;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class LinkedListIterator implements Iterator<T> {
        Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T res = current.obj;
            current = current.next;
            return res;
        }

    }

    private void checkIndex(int index, boolean sizeInclusive) {
        int limit = sizeInclusive ? size : size - 1;
        if (index < 0 || index > limit) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    Node<T> head;
    Node<T> tail;
    int size = 0;

    private Node<T> getNode(int index) {
        return index < size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void addNode(Node<T> node, int index) {
        if (index == 0) {
            addHead(node);
        } else if (index == size) {
            addTail(node);
        } else {
            addMiddle(node, index);
        }
        size++;
    }

    private void addMiddle(Node<T> nodeToInsert, int index) {
        Node<T> nodeBefore = getNode(index);
        Node<T> nodeAfter = nodeBefore.prev;
        nodeToInsert.next = nodeBefore;
        nodeToInsert.prev = nodeAfter;
        nodeBefore.prev = nodeToInsert;
        nodeAfter.next = nodeToInsert;

    }

    private void addTail(Node<T> node) {
        tail.next = node;
        node.prev = tail;
        node.next = null;
        tail = node;
    }

    private void addHead(Node<T> node) {
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    @Override
    public boolean add(T obj) {
        Node<T> node = new Node<>(obj);
        addNode(node, size);
        return true;
    }

    @Override
    public boolean remove(T pattern) {
        Node<T> current = head;
        boolean res = false;
        while (current !=null && !Objects.equals(pattern, current.obj)) {
            current = current.next;
        } 
        if (current != null) {
            res = true;
            removeNode(current);
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
        return indexOf(pattern) > -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public void add(int index, T obj) {
        checkIndex(index, true);
        Node<T> node = new Node<>(obj);
        addNode(node, index);
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> removedNode = getNode(index);
        removeNode(removedNode);
        return removedNode.obj;
    }

    private Node<T> removeNode(Node<T> node) {
        if(node == head) {
            removeHead();
        } else if ( node == tail) {
            removeTail();
        } else {
            removeMiddle(node);
        }
        size--;
        return node;
    }

    private void removeMiddle(Node<T> removedNode) {
        Node<T> nodeBefore = removedNode.prev;
        Node<T> nodeAfter = removedNode.next;
        nodeBefore.next = nodeAfter;
        nodeAfter.prev = nodeBefore;
    }

    private void removeTail() {
        tail = tail.prev;
        tail.next = null;
    }

    private void removeHead() {
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return getNode(index).obj;
    }

    @Override
    public int indexOf(T pattern) {
        Node<T> current = head;
        int index = 0;
        while (current != null && !Objects.equals(current.obj, pattern)) {
            current = current.next;
            index++;
        }
        return current == null ? -1 : index;
    }

    @Override
    public int lastIndexOf(T pattern) {
        Node<T> current = tail;
        int index = size - 1;
        while (current != null && !Objects.equals(current.obj, pattern)) {
            current = current.prev;
            index--;
        }
        return index;
    }

}
