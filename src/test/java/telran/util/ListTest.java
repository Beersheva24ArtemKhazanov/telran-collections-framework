package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public abstract class ListTest extends CollectionTest {
    List<Integer> list;
    void setUp() {
        super.setUp();
        list = (List<Integer>) collection;
    }
    
    
    @Test
    void addTest () {
        int insertObj = 35;
        int insertIndex = 3;
        list.add(insertIndex, insertObj);
        assertTrue(list.contains(insertObj));
        assertEquals(insertObj, list.get(insertIndex));
    }

    @Test
    void removeTest () {
        int index = 5;
        int expectedRemoved = list.get(index);
        assertEquals(expectedRemoved, list.remove(index));
        assertFalse(list.contains(expectedRemoved));
    }

    @Test
    void lastIndexOfTest () {
        int expectedIndex = 6;
        assertEquals(expectedIndex, list.lastIndexOf(354));
        assertFalse(list.lastIndexOf(354) == list.indexOf(354));
    }
}
