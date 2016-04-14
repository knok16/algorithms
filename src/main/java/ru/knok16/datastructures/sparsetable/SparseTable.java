package ru.knok16.datastructures.sparsetable;

import java.util.function.BinaryOperator;

public class SparseTable<T> {
    private final int n;
    private final T[][] table;
    private final BinaryOperator<T> operator;

    public SparseTable(final T[] array, final BinaryOperator<T> operator) {
        this.n = array.length;
        this.operator = operator;

        final int k = getHighestPowerOf2(n);
        this.table = (T[][]) new Object[k + 1][];
        this.table[0] = array.clone();
        for (int level = 1; level <= k; level++) {
            final int cells = n - (1 << level) + 1;
            this.table[level] = (T[]) new Object[cells];
            for (int i = 0; i < cells; i++) {
                final int offset = 1 << (level - 1);
                this.table[level][i] = operator.apply(
                        this.table[level - 1][i],
                        this.table[level - 1][i + offset]
                );
            }
        }
    }

    public T query(final int l, final int r) {
        //TODO add range checks
        final int len = r - l;
        if (len <= 0) return null;
        final int h = getHighestPowerOf2(len);

        return operator.apply(table[h][l], table[h][r - (1 << h)]);
    }

    private int getHighestPowerOf2(int i) {
        i |= (i >> 1);
        i |= (i >> 2);
        i |= (i >> 4);
        i |= (i >> 8);
        i |= (i >> 16);
        return Integer.bitCount(i) - 1;
    }
}
