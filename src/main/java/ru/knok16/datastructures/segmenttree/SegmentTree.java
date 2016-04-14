package ru.knok16.datastructures.segmenttree;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public class SegmentTree<T> {
    private final int n;
    private final T[] tree;
    private final BinaryOperator<T> combine;

    public SegmentTree(final int n, final T element, final BinaryOperator<T> combine) {
        this.n = n;
        this.tree = (T[]) new Object[4 * n];
        this.combine = combine;
        build(1, 0, n - 1, i -> element);
    }

    public SegmentTree(final T[] array, final BinaryOperator<T> combine) {
        this.n = array.length;
        this.tree = (T[]) new Object[4 * n];
        this.combine = combine;
        build(1, 0, n - 1, i -> array[i]);
    }

    public <E> SegmentTree(final E[] array, final Function<E, T> map, final BinaryOperator<T> combine) {
        this.n = array.length;
        this.tree = (T[]) new Object[4 * n];
        this.combine = combine;
        build(1, 0, n - 1, i -> map.apply(array[i]));
    }

    public T query(final int l, final int r) {
        return query(1, 0, n - 1, l, r);
    }

    private T query(final int v, final int tl, final int tr, final int l, final int r) {
        if (tl == l && tr == r) {
            return tree[v];
        }
        final int tm = getMiddle(tl, tr);
        if (r <= tm) {
            return query(2 * v, tl, tm, l, r);
        } else if (l > tm) {
            return query(2 * v + 1, tm + 1, tr, l, r);
        } else {
            return combine.apply(
                    query(2 * v, tl, tm, l, tm),
                    query(2 * v + 1, tm + 1, tr, tm + 1, r)
            );
        }
    }

    private void update(final int v, final int tl, final int tr, final int index, final T value) {
        if (tl == tr) {
            tree[v] = value;
        } else {
            final int tm = getMiddle(tl, tr);
            if (index <= tm) {
                update(2 * v, tl, tm, index, value);
            } else {
                update(2 * v + 1, tm + 1, tr, index, value);
            }
            tree[v] = combine.apply(tree[2 * v], tree[2 * v + 1]);
        }
    }

    private void build(final int v, final int tl, final int tr, final Function<Integer, T> getElement) {
        if (tl == tr) {
            tree[v] = getElement.apply(tl);
        } else {
            final int tm = getMiddle(tl, tr);
            build(2 * v, tl, tm, getElement);
            build(2 * v + 1, tm + 1, tr, getElement);
            tree[v] = combine.apply(tree[2 * v], tree[2 * v + 1]);
        }
    }

    private int getMiddle(final int tl, final int tr) {
        return tl + (tr - tl) / 2;
    }
}
