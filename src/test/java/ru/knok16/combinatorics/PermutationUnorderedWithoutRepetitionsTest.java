package ru.knok16.combinatorics;

import org.junit.Test;

import java.util.*;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class PermutationUnorderedWithoutRepetitionsTest {
    @Test
    public void checkCount() {
        assertEquals(1, new Permutations().by(0).outOf(3).distinct().unordered().count());
        assertEquals(3, new Permutations().by(1).outOf(3).distinct().unordered().count());
        assertEquals(3, new Permutations().by(2).outOf(3).distinct().unordered().count());
        assertEquals(1, new Permutations().by(3).outOf(3).distinct().unordered().count());

        assertEquals(1, new Permutations().by(0).outOf(5).distinct().unordered().count());
        assertEquals(5, new Permutations().by(1).outOf(5).distinct().unordered().count());
        assertEquals(10, new Permutations().by(2).outOf(5).distinct().unordered().count());
        assertEquals(10, new Permutations().by(3).outOf(5).distinct().unordered().count());
        assertEquals(5, new Permutations().by(4).outOf(5).distinct().unordered().count());
        assertEquals(1, new Permutations().by(5).outOf(5).distinct().unordered().count());

        assertEquals(15504, new Permutations().by(5).outOf(20).distinct().unordered().count());

        assertEquals(5852925, new Permutations().by(8).outOf(30).distinct().unordered().count());

        assertEquals(273438880, new Permutations().by(31).outOf(40).distinct().unordered().count());
        assertEquals(137846528820L, new Permutations().by(20).outOf(40).distinct().unordered().count());

        assertEquals(244662670200L, new Permutations().by(19).outOf(41).distinct().unordered().count());
    }

    @Test
    public void checkIteratorSize() {
        assertEquals(1, getIteratorSize(new Permutations().by(0).outOf(3).distinct().unordered().iterator()));
        assertEquals(3, getIteratorSize(new Permutations().by(1).outOf(3).distinct().unordered().iterator()));
        assertEquals(3, getIteratorSize(new Permutations().by(2).outOf(3).distinct().unordered().iterator()));
        assertEquals(1, getIteratorSize(new Permutations().by(3).outOf(3).distinct().unordered().iterator()));

        assertEquals(1, getIteratorSize(new Permutations().by(0).outOf(5).distinct().unordered().iterator()));
        assertEquals(5, getIteratorSize(new Permutations().by(1).outOf(5).distinct().unordered().iterator()));
        assertEquals(10, getIteratorSize(new Permutations().by(2).outOf(5).distinct().unordered().iterator()));
        assertEquals(10, getIteratorSize(new Permutations().by(3).outOf(5).distinct().unordered().iterator()));
        assertEquals(5, getIteratorSize(new Permutations().by(4).outOf(5).distinct().unordered().iterator()));
        assertEquals(1, getIteratorSize(new Permutations().by(5).outOf(5).distinct().unordered().iterator()));

        assertEquals(15504, getIteratorSize(new Permutations().by(5).outOf(20).distinct().unordered().iterator()));

        assertEquals(1081575, getIteratorSize(new Permutations().by(8).outOf(25).distinct().unordered().iterator()));
    }

    @Test
    public void hasNoNextAfterLast() {
        final Iterator<int[]> iterator = new Permutations().by(3).outOf(5).distinct().unordered().iterator();
        for (int i = 0; i < 10; i++) {
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
        final long count = new Permutations().by(4).outOf(6).distinct().unordered().stream()
                .map(it -> {
                    final List<Integer> result = new ArrayList<>();
                    assertEquals(4, it.length);
                    assertEquals(4, Arrays.stream(it).distinct().count());
                    Arrays.sort(it);
                    for (int i = 0; i < 4; i++) {
                        assertTrue(0 <= it[i]);
                        assertTrue(it[i] < 6);
                        result.add(it[i]);
                    }
                    return result;
                })
                .distinct()
                .count();
        assertEquals(15, count);
    }

    private long getIteratorSize(final Iterator<?> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false).count();
    }
}
