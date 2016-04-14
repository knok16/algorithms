package ru.knok16.datastructures.dsu;

public class DSU {
    protected final int[] parent;
    protected final int[] size;

    public DSU(final int n) {
        this.parent = new int[n];
        this.size = new int[n];
        init(n);
    }

    private void init(final int n) {
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int getLeader(final int a) {
        return a == parent[a] ? a : (parent[a] = getLeader(parent[a]));
    }

    public int size(final int a) {
        return size[getLeader(a)];
    }

    public boolean inTheSameSet(final int a, final int b) {
        return getLeader(a) == getLeader(b);
    }

    public boolean union(final int a, final int b) {
        final int la = getLeader(a);
        final int lb = getLeader(b);
        if (la == lb) return false;
        if (size[la] >= size[lb]) {
            parent[lb] = la;
            size[la] += size[lb];
            _union(la, lb);
        } else {
            parent[la] = lb;
            size[lb] += size[la];
            _union(lb, la);
        }
        return true;
    }

    protected void _union(final int parent, final int child) {
        // can be implemented in inherited classes
    }
}
