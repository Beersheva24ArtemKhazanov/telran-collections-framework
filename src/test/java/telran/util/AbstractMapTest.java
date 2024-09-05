package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Map.Entry;

public abstract class AbstractMapTest {
    Integer[] keys = { 1, -1, 5, 16, -13 };
    Map<Integer, Integer> map;
    abstract <T> void runTest(T[] expected, T[] actual);

    @BeforeEach
    void setUp() {
        // TODO
        map = new HashMap<>();
        for (Integer key : keys) {
            map.put(key, key + 5);
        }
    }

    @Test
    void getTest() {
        for (Integer key : keys) {
            Integer expectedValue = key + 5;
            Integer actualValue = map.get(key);
            assertEquals(expectedValue, actualValue);
        }
    }

    @Test
    void putTest() {
        map.put(26, 15);
        map.put(-14, 21);
        assertEquals(13, map.put(5, 13));
        
        assertEquals(6, map.get(1));
        assertEquals(4, map.get(-1));
        assertEquals(10, map.get(5));
        assertEquals(21, map.get(16));
        assertEquals(-8, map.get(-13));
    }
    
    @Test
    void keySetTest() {
        Integer[] expectedKeys = {1, -1, 5, 16, -13};
        Integer[] expectedValues = {6, 4, 10, 21, -8};
        Set<Integer> actualKeySet = map.keySet();
        Integer[] actualKeys = new Integer[actualKeySet.size()];
        Integer[] actualValues = new Integer[map.size()];

        int index = 0;
        for (Integer key : actualKeySet) {
            actualKeys[index] = key;
            actualValues[index] = map.get(key);
            index++;
        }
        runTest(expectedKeys, actualKeys);
        runTest(expectedValues, actualValues);
    }

    @Test
    void valuesCollectionTest() {
        Collection<Integer> values = map.values();
        assertEquals(5, values.size());
        assertFalse(values.contains(352));
        assertTrue(values.contains(21));
        assertTrue(values.contains(-8));
    }

    @SuppressWarnings("unchecked")
    @Test
    void entrySetTest() {
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        assertEquals(5, entries.size());
        Entry<Integer, Integer>[] expectedEntries = new Entry[]{
            new Entry<>(1, map.get(1)),
            new Entry<>(-1, map.get(-1)),
            new Entry<>(5, map.get(5)),
            new Entry<>(16, map.get(16)),
            new Entry<>(-13, map.get(-13)),
        };
        Entry<Integer, Integer>[] actualEntries = new Entry[entries.size()];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : entries) {
            actualEntries[index++] = new Entry<>(entry.getKey(), entry.getValue());
        }

        runTest(expectedEntries, actualEntries);
    }

    @Test
    void containsKeyTest() {
        for (var key : keys) {
            assertTrue(map.containsKey(key));
        }
        assertFalse(map.containsKey(36));
    }

    @Test
    void containsValueTest() {
        for (var key : keys) {
            assertTrue(map.containsValue(key + 5));
        }
        assertFalse(map.containsValue(36 + 5));
    }

    @Test
    void sizeTest() {
        assertEquals(5, map.size());
        map.put(4, 1);
        assertEquals(6, map.size());
    }

    @Test
    void isEmptyTest() {
        assertFalse(map.isEmpty());
        map = new HashMap<>();
        assertTrue(map.isEmpty());
    }
}
