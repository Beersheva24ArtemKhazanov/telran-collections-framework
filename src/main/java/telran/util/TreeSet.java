package telran.util;

import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class TreeSet<T> implements Set<T> {
    private static class Node<T> {
        T obj;
        Node<T> parent;
        Node<T> left;
        Node<T> right;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class TreeSetIterator implements Iterator<T> {
        Node<T> current = getLeastCurrent(root);
        Node<T> prev = null;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        private Node<T> getLeastCurrent(Node<T> leastCurrent) {
            if (leastCurrent != null) {
                while (leastCurrent.left != null) {
                    leastCurrent = leastCurrent.left;
                }
            }
			return leastCurrent;
		}

		@Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T res = current.obj;
            prev = current;
            current = getNextCurrent(current);
            return res;
        }

        private Node<T> getNextCurrent(Node<T> nextCurrent) {
			if (nextCurrent.right != null) {
                nextCurrent = getLeastCurrent(current.right);
            } else {
                nextCurrent = getGreaterParent(current);
            }
            return nextCurrent;
		}

		private Node<T> getGreaterParent(Node<T> current) {
			Node<T> parent = current.parent;
            while (parent != null && current == parent.right) {
                current = parent;
                parent = parent.parent;
            }
            return parent;
		}

		@Override
        public void remove() {
            if (prev == null) {
                throw new IllegalStateException();
            }
            removeNode(prev);
            prev = null;
        }

    }

    private Node<T> root;
    private Comparator<T> comparator;
    int size;

    public TreeSet(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public TreeSet() {
        this((Comparator<T>) Comparator.naturalOrder());
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            Node<T> node = new Node<>(obj);
            if (root == null) {
                addRoot(node);
            } else {
                addAfterParent(node);
            }
            size++;
        }
        return res;
    }

    private void addAfterParent(Node<T> node) {
        Node<T> parent = getParent(node.obj);
        if (comparator.compare(node.obj, parent.obj) > 0) {
            parent.right = node;
        } else {
            parent.left = node;
        }
        node.parent = parent;
    }

    private void addRoot(Node<T> node) {
        root = node;
    }

    @Override
    public boolean remove(T pattern) {
        boolean removed = false;
        if (contains(pattern)) {
            Node<T> toRemoveNode = getNode(pattern);
            removeNode(toRemoveNode);
            removed = true;
        }
        return removed;
    }

    private void removeNode(Node<T> toRemoveNode) {
		if (toRemoveNode.left == null || toRemoveNode.right == null) {
            removeNonJunction(toRemoveNode);
        } else {
            removeJunction(toRemoveNode);
        }
        size--;
	}

	private void removeJunction(Node<T> toRemoveNode) {
		Node<T> node = getGreatestFrom(toRemoveNode.left);
        toRemoveNode.obj = node.obj;
        removeNonJunction(node);
	}

	private Node<T> getGreatestFrom(Node<T> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
	}

	private void removeNonJunction(Node<T> toRemoveNode) {
        Node<T> child = toRemoveNode.left != null ? toRemoveNode.left : toRemoveNode.right;
		if (toRemoveNode.parent != null) {
            if (toRemoveNode == toRemoveNode.parent.left) {
                toRemoveNode.parent.left = child;
            } else {
                toRemoveNode.parent.right = child;
            }
            if (child != null) {
                child.parent = toRemoveNode.parent;
            }
        } else {
            root = child;
            if (child != null) {
                child.parent = null;
            }
        }
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
        boolean res = false;
        Node<T> node = getNode(pattern);
        if (node != null) {
            res = true;
        }
        return res; 
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeSetIterator();
    }

    @Override
    public T get(Object pattern) {
        T res = (T) pattern;
        Node<T> node = getNode(res);
        return node != null ? node.obj : null;
    }

    private Node<T> getParentOrNode(T pattern) {
        Node<T> current = root;
        Node<T> parent = null;
        int compRes = 0;
        while(current != null && (compRes = comparator.compare(pattern, current.obj)) != 0) {
            parent = current;
            current = compRes > 0 ? current.right : current.left;
        }
        return current == null ? parent : current; 
    }
    
    private Node<T> getNode(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        if(res != null) {
            int compRes = comparator.compare(pattern, res.obj);
            res = compRes == 0 ? res : null;
        }
        
        return res;
    }

    private Node<T> getParent(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        int compRes = comparator.compare(pattern, res.obj);
        return compRes == 0 ? null : res;
    }

}
