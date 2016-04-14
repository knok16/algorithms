package ru.knok16.datastructures.fenwicktree;

public class LongSumFenwickTree {
    private final int n;
    private final long[] tree;

    public LongSumFenwickTree(final int n) {
        this.n = n;
        this.tree = new long[n];
    }

    public long query(final int upTo) {
        long result = 0;
        for (int i = upTo; i >= 0; i = (i & (i + 1)) - 1)
            result += tree[i];
        return result;
    }

    public long query(final int l, final int r) {
        return query(r) - query(l - 1);
    }

    public void update(final int index, final long delta) {
        for (int i = index; i < n; i = (i | (i + 1)))
            tree[i] += delta;
    }
}
