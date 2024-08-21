package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public abstract class CollectionTest {
    protected Collection<Integer> collection;
    Integer[] array = { 3, -10, 20, 354, 10, 8, 354, 100, 17 };

    void setUp() {
        Arrays.stream(array).forEach(collection::add);
    }

    @Test
    void addTest() {
        assertTrue(collection.add(200));
        assertTrue(collection.add(17));
        assertEquals(array.length + 2, collection.size());
    }

    @Test
    void sizeTest() {
        assertEquals(array.length, collection.size());
    }

    
    @Test
    void removeTest() {
        assertTrue(collection.remove(8));
        assertFalse(collection.contains(8));
        assertFalse(collection.remove(35));
    }

    @Test
    void isEmptyTest() {
        assertFalse(collection.isEmpty());
        clearCollection();
        assertTrue(collection.isEmpty());
    }

    private void clearCollection() {
        while (!collection.isEmpty()) {
            collection.remove(collection.iterator().next());
        }
    }

    void containsTest() {
        assertTrue(collection.contains(3));
        assertFalse(collection.contains(35));
    }

    @Test
    void streamTest() {
        Stream<Integer> stream = collection.stream();
        assertEquals(array.length, stream.count());
    }

    @Test
    void parallelStreamTest() {
        Stream<Integer> parallelStream = collection.parallelStream();
        assertEquals(array.length, parallelStream.count());
    }
}
