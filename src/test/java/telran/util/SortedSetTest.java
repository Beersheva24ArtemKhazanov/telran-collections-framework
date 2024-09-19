package telran.util;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
// { 3, -10, 20, 1, 10, 8, 100, 17 };
public abstract class SortedSetTest extends SetTest {
    SortedSet<Integer> sortedSet;

    @Override
    void setUp() {
        super.setUp();
        sortedSet = (SortedSet<Integer>) collection;

    }

    @Test
    void floorTest() {
        assertEquals(10, sortedSet.floor(10));
        assertNull(sortedSet.floor(-11));
        assertEquals(10, sortedSet.floor(11));
        assertEquals(100, sortedSet.floor(101));
    }

    @Test
    void ceilingTest() {
        assertEquals(10, sortedSet.ceiling(10));
        assertNull(sortedSet.ceiling(101));
        assertEquals(17, sortedSet.ceiling(11));
        assertEquals(-10, sortedSet.ceiling(-11));
    }

    @Test
    void firstTest() {
        assertEquals(-10, sortedSet.first());
    }

    @Test
    void lastTest() {
        assertEquals(100, sortedSet.last());
    }

    @Test
    void subSetTest() {
        Integer[] expected = { 10, 17 };
        Integer[] actual = sortedSet.subSet(10, 20).stream().toArray(Integer[]::new);
        assertArrayEquals(expected, actual);
    }

    @Override
    protected void fillBigCollection () {
        Integer [] array = getBigArrayCW();
        Arrays.stream(array).forEach(collection::add); 
    }

    protected Integer[] getBigArrayCW() {
        return new Random().ints().distinct().limit(N_ELEMENTS).boxed().toArray(Integer[]::new);
    }

    protected Integer[] getBigArrayHW() {
        Integer[] array = IntStream.rangeClosed(1, N_ELEMENTS).boxed().toArray(Integer[]::new);
        Integer[] balancedArray = new Integer[array.length];
        fillBalancedArray(array, balancedArray, 0, array.length - 1, 0);
        return balancedArray;
    }

    private int fillBalancedArray(Integer[] array, Integer[] balancedArray, int left, int right, int index) {
        if (left <= right) {
            int middle = (left + right) / 2;
            balancedArray[index++] = array[middle];
            index = fillBalancedArray(array, balancedArray, left, middle - 1, index);
            index = fillBalancedArray(array, balancedArray, middle + 1, right, index);
        }
        return index;
    }

    @Override
    protected void runTest(Integer[] expected) {
        Integer[] expectedSorted = Arrays.copyOf(expected, expected.length);
        Arrays.sort(expectedSorted);
        Integer[] actual = collection.stream().toArray(Integer[]::new);
        assertArrayEquals(expectedSorted, actual);
        assertEquals(expected.length, collection.size());
    }
}
