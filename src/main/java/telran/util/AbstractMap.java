package telran.util;

import java.util.Objects;

@SuppressWarnings("unchecked")
public abstract class AbstractMap<K, V> implements Map<K, V> {
    protected Set<Entry<K, V>> set;

    protected abstract Set<K> getEmptyKeySet();

    @Override
    public V get(Object key) {
        Entry<K, V> pattern = new Entry<>((K) key, null);
        Entry<K, V> entry = set.get(pattern);
        V res = null;
        if (entry != null) {
            res = entry.getValue();
        }
        return res;
    }

    @Override
    public V put(K key, V value) {
        Entry<K, V> entry = new Entry<K, V>(key, value);
        V res = null;
        if (!containsKey(key)) {
            set.add(entry);
        } else {
            res = entry.getValue();
            entry.setValue(value);
        }
        return res;
    }

    @Override
    public boolean containsKey(Object key) {
        return set.contains(new Entry<K, V>((K) key, null));
    }

    @Override
    public boolean containsValue(Object value) {
        return set.stream().anyMatch(entry -> Objects.equals(entry.getValue(), value));
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = getEmptyKeySet();
        for (Entry<K, V> entry : set) {
            keySet.add(entry.getKey());
        }
        return keySet;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return set;
    }

    @Override
    public Collection<V> values() {
        Collection<V> collection = new ArrayList<>();
        for (Entry<K, V> entry : set) {
            collection.add(entry.getValue());
        }
        return collection;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

}
