package ru.knok16.datastructures.fenwicktree;

public class IntSumFenwickTree {
    private final int n;
    private final int[] tree;

    public IntSumFenwickTree(final int n) {
        this.n = n;
        this.tree = new int[n];
    }

    public int query(final int upTo) {
        int result = 0;
        for (int i = upTo; i >= 0; i = (i & (i + 1)) - 1)
            result += tree[i];
        return result;
    }

    public int query(final int l, final int r) {
        return query(r) - query(l - 1);
    }

    public void update(final int index, final int delta) {
        for (int i = index; i < n; i = (i | (i + 1)))
            tree[i] += delta;
    }
}