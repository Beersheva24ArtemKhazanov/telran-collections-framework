package telran.util;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public abstract class SetTest extends CollectionTest{
    Set<Integer> set;
    @Override
    void setUp(){
        super.setUp();
        set = (Set<Integer>) collection;

    }
     @Test
     @Override
    void addExistingTest() {
        assertFalse(set.add(17));

    }
    @Test
    void getPatternTest() {
        assertEquals(-10, set.get(-10));
        assertNull(set.get(1000000));
    }

    @Test
    void iteratorTest() {
        Integer[] expected = array.clone();
        Integer[] actual = new Integer[collection.size()];
        int index = 0;
        Iterator<Integer> it = collection.iterator();

        while (it.hasNext()) {
            actual[index++] = it.next();
        }

        Arrays.sort(expected);
        Arrays.sort(actual);

        assertArrayEquals(expected, actual);
        assertFalse(it.hasNext());
    }

}