package ru.knok16.datastructures.fenwicktree;

import org.junit.Test;
import ru.knok16.datastructures.fenwicktree.IntSumFenwickTree;

import static org.junit.Assert.assertEquals;

public class IntFenwickTreeTest {
    @Test
    public void test() {
        final int[] a = {10, 2, 2, 3, -7, 271, 9, 128, 16};
        final IntSumFenwickTree tree = new IntSumFenwickTree(a.length);

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

    private void check(final int[] a, final IntSumFenwickTree tree) {
        for (int l = 0; l < a.length; l++) {
            int result = 0;
            for (int r = l; r < a.length; r++) {
                result += a[r];
                assertEquals(result, tree.query(l, r));
            }
        }
    }
}
