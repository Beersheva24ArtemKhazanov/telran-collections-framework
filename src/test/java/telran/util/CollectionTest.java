package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public abstract class CollectionTest {
    protected Collection<Integer> collection;
    Integer[] array = {3, -10, 20, 1, 10, 8, 100 , 17};
    void setUp() {
        Arrays.stream(array).forEach(collection::add);
    }
    @Test
    void addTest() {
        assertTrue(collection.add(200));
        assertTrue(collection.add(17));
        runTest(new Integer[]{3, -10, 20, 1, 10, 8, 100 , 17, 200, 17});
    }
    @Test
    void sizeTest() {
        assertEquals(array.length, collection.size());
    }
@Test
    void iteratorTest() {
        Integer[] actual = new Integer[array.length];
        int index = 0;
        Iterator<Integer> it = collection.iterator();
        while(it.hasNext()) {
            actual[index++] = it.next();
        }
        assertArrayEquals(array, actual);
        assertThrowsExactly(NoSuchElementException.class, it::next );
    }

    protected void runTest(Integer[] expected) {
        assertArrayEquals(expected, collection.stream().toArray(Integer[]::new));
        assertEquals(expected.length, collection.size());
    }
    @Test
    void streamTest() {
       runTest(array);
    }
    @Test
    void removeTest() {
        Integer[] expected = {-10, 20, 1,  8, 100 };
        assertTrue(collection.remove(10));
        assertTrue(collection.remove(3));
        assertTrue(collection.remove(17));
        runTest(expected);
        assertFalse(collection.remove(10));
        assertFalse(collection.remove(3));
        assertFalse(collection.remove(17));
        clear();
        runTest(new Integer[0]);

    }
    private void clear() {
        Arrays.stream(array).forEach(n -> collection.remove(n));
    }
    @Test
    void isEmptyTest() {
        assertFalse(collection.isEmpty());
        clear();
        assertTrue(collection.isEmpty());
    }
    @Test
    void containsTest(){
       Arrays.stream(array).forEach(n -> assertTrue(collection.contains(n)));
       assertFalse(collection.contains(10000000));
    }
}