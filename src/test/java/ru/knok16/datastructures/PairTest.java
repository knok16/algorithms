package ru.knok16.datastructures;

import org.junit.Test;
import ru.knok16.utils.Pair;

import static org.junit.Assert.*;

public class PairTest {
    @Test
    public void create() {
        final Pair<String, Integer> pair = Pair.of("a", 2);
        assertNotNull(pair);
        assertEquals("a", pair.getFirst());
        assertEquals("a", pair.getLeft());
        assertEquals(Integer.valueOf(2), pair.getSecond());
        assertEquals(Integer.valueOf(2), pair.getRight());
    }

    @Test
    public void equals() {
        final Pair<String, Integer> pair1 = Pair.of("a", 2);
        final Pair<String, Integer> pair2 = Pair.of("a", 2);
        assertNotSame(pair1, pair2);
        assertEquals(pair1, pair2);
        assertNotEquals(pair1, Pair.of("b", 2));
    }

    @Test
    public void hashCodeEquals() {
        final Pair<String, Integer> pair1 = Pair.of("a", 2);
        final Pair<String, Integer> pair2 = Pair.of("a", 2);
        assertNotSame(pair1, pair2);
        assertEquals(pair1.hashCode(), pair2.hashCode());
        assertNotEquals(pair1.hashCode(), Pair.of("b", 2).hashCode());
    }

    @Test
    public void testToString() {
        final Pair<String, Integer> pair = Pair.of("a", 2);
        assertEquals("(a, 2)", pair.toString());
    }
}
