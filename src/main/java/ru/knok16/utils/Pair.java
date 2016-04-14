package ru.knok16.utils;

import java.util.Objects;

public class Pair<L, R> {
    public static Pair EMPTY_PAIR = new Pair(null, null);

    public static <L, R> Pair<L, R> emptyPair() {
        return EMPTY_PAIR;
    }

    private final L left;
    private final R right;

    public static <L, R> Pair<L, R> of(final L left, final R right) {
        return new Pair<>(left, right);
    }

    private Pair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public L getFirst() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public R getSecond() {
        return right;
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) &&
                Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "(" + left + ", " + right + ")";
    }
}
