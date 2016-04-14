package ru.knok16.datastructures.sparsetable;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SparseTableTest {
    private final Random rnd = new Random(123);

    @Test
    public void test() {
        final int n = 100;
        final Integer[] array = getRandomArray(n);
        final SparseTable<Integer> st = new SparseTable<>(array, Math::min);
        for (int from = 0; from < n; from++) {
            for (int to = from + 1; to <= n; to++) {
                Integer expected = Integer.MAX_VALUE;
                for (int i = from; i < to; i++) expected = Math.min(expected, array[i]);
                assertEquals(expected, st.query(from, to));
            }
        }
    }

    private Integer[] getRandomArray(final int n) {
        final Integer[] result = new Integer[n];
        for (int i = 0; i < n; i++) result[i] = rnd.nextInt();
        return result;
    }
}
