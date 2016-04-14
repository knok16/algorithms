package ru.knok16.combinatorics;

import org.junit.Test;

import java.util.*;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class PermutationOrderedWithRepetitionsTest {
    @Test
    public void checkCount() {
        assertEquals(1, new Permutations().by(0).outOf(3).withRepetitions().ordered().count());
        assertEquals(3, new Permutations().by(1).outOf(3).withRepetitions().ordered().count());
        assertEquals(9, new Permutations().by(2).outOf(3).withRepetitions().ordered().count());
        assertEquals(27, new Permutations().by(3).outOf(3).withRepetitions().ordered().count());

        assertEquals(1, new Permutations().by(0).outOf(5).withRepetitions().ordered().count());
        assertEquals(5, new Permutations().by(1).outOf(5).withRepetitions().ordered().count());
        assertEquals(25, new Permutations().by(2).outOf(5).withRepetitions().ordered().count());
        assertEquals(125, new Permutations().by(3).outOf(5).withRepetitions().ordered().count());
        assertEquals(625, new Permutations().by(4).outOf(5).withRepetitions().ordered().count());
        assertEquals(3125, new Permutations().by(5).outOf(5).withRepetitions().ordered().count());

        assertEquals(3200000, new Permutations().by(5).outOf(20).withRepetitions().ordered().count());

        assertEquals(656100000000L, new Permutations().by(8).outOf(30).withRepetitions().ordered().count());
    }

    @Test
    public void checkIteratorSize() {
        assertEquals(1, getIteratorSize(new Permutations().by(0).outOf(3).withRepetitions().ordered().iterator()));
        assertEquals(3, getIteratorSize(new Permutations().by(1).outOf(3).withRepetitions().ordered().iterator()));
        assertEquals(9, getIteratorSize(new Permutations().by(2).outOf(3).withRepetitions().ordered().iterator()));
        assertEquals(27, getIteratorSize(new Permutations().by(3).outOf(3).withRepetitions().ordered().iterator()));

        assertEquals(1, getIteratorSize(new Permutations().by(0).outOf(5).withRepetitions().ordered().iterator()));
        assertEquals(5, getIteratorSize(new Permutations().by(1).outOf(5).withRepetitions().ordered().iterator()));
        assertEquals(25, getIteratorSize(new Permutations().by(2).outOf(5).withRepetitions().ordered().iterator()));
        assertEquals(125, getIteratorSize(new Permutations().by(3).outOf(5).withRepetitions().ordered().iterator()));
        assertEquals(625, getIteratorSize(new Permutations().by(4).outOf(5).withRepetitions().ordered().iterator()));
        assertEquals(3125, getIteratorSize(new Permutations().by(5).outOf(5).withRepetitions().ordered().iterator()));

        assertEquals(3200000, getIteratorSize(new Permutations().by(5).outOf(20).withRepetitions().ordered().iterator()));
    }

    @Test
    public void hasNoNextAfterLast() {
        final Iterator<int[]> iterator = new Permutations().by(3).outOf(5).withRepetitions().ordered().iterator();
        for (int i = 0; i < 125; i++) {
            assertTrue(iterator.hasNext());
            final int[] next = iterator.next();
            assertNotNull(next);
            assertEquals(3, next.length);
        }
        for (int i = 0; i < 10; i++) {
            assertFalse(iterator.hasNext());
        }
    }

    @Test
    public void checkStream() {
        final long count = new Permutations().by(4).outOf(6).withRepetitions().ordered().stream()
                .map(it -> {
                    final List<Integer> result = new ArrayList<>();
                    assertEquals(4, it.length);
                    for (int i = 0; i < 4; i++) {
                        assertTrue(0 <= it[i]);
                        assertTrue(it[i] < 6);
                        result.add(it[i]);
                    }
                    return result;
                })
                .distinct()
                .count();
        assertEquals(1296, count);
    }

    private long getIteratorSize(final Iterator<?> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false).count();
    }
}
