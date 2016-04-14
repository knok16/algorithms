package ru.knok16.combinatorics;

import org.junit.Test;

import java.util.*;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class PermutationOrderedWithoutRepetitionsTest {
    @Test
    public void checkCount() {
        assertEquals(1, new Permutations().by(0).outOf(3).distinct().ordered().count());
        assertEquals(3, new Permutations().by(1).outOf(3).distinct().ordered().count());
        assertEquals(6, new Permutations().by(2).outOf(3).distinct().ordered().count());
        assertEquals(6, new Permutations().by(3).outOf(3).distinct().ordered().count());

        assertEquals(1, new Permutations().by(0).outOf(5).distinct().ordered().count());
        assertEquals(5, new Permutations().by(1).outOf(5).distinct().ordered().count());
        assertEquals(20, new Permutations().by(2).outOf(5).distinct().ordered().count());
        assertEquals(60, new Permutations().by(3).outOf(5).distinct().ordered().count());
        assertEquals(120, new Permutations().by(4).outOf(5).distinct().ordered().count());
        assertEquals(120, new Permutations().by(5).outOf(5).distinct().ordered().count());

        assertEquals(1860480, new Permutations().by(5).outOf(20).distinct().ordered().count());

        assertEquals(235989936000L, new Permutations().by(8).outOf(30).distinct().ordered().count());
    }

    @Test
    public void checkIteratorSize() {
        assertEquals(1, getIteratorSize(new Permutations().by(0).outOf(3).distinct().ordered().iterator()));
        assertEquals(3, getIteratorSize(new Permutations().by(1).outOf(3).distinct().ordered().iterator()));
        assertEquals(6, getIteratorSize(new Permutations().by(2).outOf(3).distinct().ordered().iterator()));
        assertEquals(6, getIteratorSize(new Permutations().by(3).outOf(3).distinct().ordered().iterator()));

        assertEquals(1, getIteratorSize(new Permutations().by(0).outOf(5).distinct().ordered().iterator()));
        assertEquals(5, getIteratorSize(new Permutations().by(1).outOf(5).distinct().ordered().iterator()));
        assertEquals(20, getIteratorSize(new Permutations().by(2).outOf(5).distinct().ordered().iterator()));
        assertEquals(60, getIteratorSize(new Permutations().by(3).outOf(5).distinct().ordered().iterator()));
        assertEquals(120, getIteratorSize(new Permutations().by(4).outOf(5).distinct().ordered().iterator()));
        assertEquals(120, getIteratorSize(new Permutations().by(5).outOf(5).distinct().ordered().iterator()));

        assertEquals(1860480, getIteratorSize(new Permutations().by(5).outOf(20).distinct().ordered().iterator()));
    }

    @Test
    public void hasNoNextAfterLast() {
        final Iterator<int[]> iterator = new Permutations().by(3).outOf(5).distinct().ordered().iterator();
        for (int i = 0; i < 60; i++) {
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
        final long count = new Permutations().by(4).outOf(6).distinct().ordered().stream()
                .map(it -> {
                    final List<Integer> result = new ArrayList<>();
                    assertEquals(4, it.length);
                    assertEquals(4, Arrays.stream(it).distinct().count());
                    for (int i = 0; i < 4; i++) {
                        assertTrue(0 <= it[i]);
                        assertTrue(it[i] < 6);
                        result.add(it[i]);
                    }
                    return result;
                })
                .distinct()
                .count();
        assertEquals(360, count);
    }

    private long getIteratorSize(final Iterator<?> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false).count();
    }
}
