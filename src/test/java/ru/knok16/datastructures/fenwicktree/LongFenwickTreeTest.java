package ru.knok16.datastructures.fenwicktree;

import org.junit.Test;
import ru.knok16.datastructures.fenwicktree.LongSumFenwickTree;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class LongFenwickTreeTest {
    @Test
    public void test() {
        final long[] a = {10, 2, 2, 3, -7, 271, 9, 128, 16};
        final LongSumFenwickTree tree = new LongSumFenwickTree(a.length);

        for (int i = 0; i < a.length; i++)
            tree.update(i, a[i]);

        check(a, tree);

        tree.update(4, -5);
        a[4] += -5;

        check(a, tree);

        tree.update(3, 100);
        a[3] += 100;

        check(a, tree);
    }

    @Test
    public void checkForOverflow() {
        final long[] a = new long[100];
        Arrays.fill(a, Integer.MAX_VALUE);
        final LongSumFenwickTree tree = new LongSumFenwickTree(a.length);

        for (int i = 0; i < a.length; i++)
            tree.update(i, a[i]);

        assertEquals(Integer.MAX_VALUE * 100L, tree.query(0, a.length - 1));
        check(a, tree);
    }

    private void check(final long[] a, final LongSumFenwickTree tree) {
        for (int l = 0; l < a.length; l++) {
            long result = 0;
            for (int r = l; r < a.length; r++) {
                result += a[r];
                assertEquals(result, tree.query(l, r));
            }
        }
    }
}
