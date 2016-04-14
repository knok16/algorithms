package ru.knok16.datastructures;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountingSetTest {
    @Test
    public void test() {
        final CountingSet<Integer> countingSet = new CountingSet<>();

        assertEquals(0, countingSet.add(1));
        assertEquals(1, countingSet.size(1));
        assertEquals(0, countingSet.size(2));
        assertEquals(0, countingSet.size(3));
        assertEquals(1, countingSet.size());

        assertEquals(1, countingSet.add(1));
        assertEquals(2, countingSet.size(1));
        assertEquals(0, countingSet.size(2));
        assertEquals(0, countingSet.size(3));
        assertEquals(2, countingSet.size());

        assertEquals(0, countingSet.add(2));
        assertEquals(2, countingSet.size(1));
        assertEquals(1, countingSet.size(2));
        assertEquals(0, countingSet.size(3));
        assertEquals(3, countingSet.size());

        assertEquals(1, countingSet.remove(2));
        assertEquals(2, countingSet.size(1));
        assertEquals(0, countingSet.size(2));
        assertEquals(0, countingSet.size(3));
        assertEquals(2, countingSet.size());

        assertEquals(0, countingSet.update(3, 2));
        assertEquals(2, countingSet.size(1));
        assertEquals(0, countingSet.size(2));
        assertEquals(2, countingSet.size(3));
        assertEquals(4, countingSet.size());

        assertEquals(2, countingSet.update(1, -2));
        assertEquals(0, countingSet.size(1));
        assertEquals(0, countingSet.size(2));
        assertEquals(2, countingSet.size(3));
        assertEquals(2, countingSet.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() {
        final CountingSet<Integer> countingSet = new CountingSet<>();

        countingSet.add(1);
        countingSet.update(3, -4);
    }
}
