package ru.knok16.math;

public interface Space<T extends Number> {
    T zero();

    T one();

    T e();

    T ten();

    int compare(T left, T right);

    default boolean equals(T left, T right) {
        return compare(left, right) == 0;
    }

    default boolean isZero(T node) {
        return equals(node, zero());
    }

    default boolean isOne(T node) {
        return equals(node, one());
    }

    default T negate(T node) {
        return subtract(zero(), node);
    }

    T add(T left, T right);

    T subtract(T left, T right);

    T multiply(T left, T right);

    T divide(T left, T right);

    T power(T left, T right);

    T ln(T node);

    default T log(T base, T arg) {
        return divide(ln(arg), ln(base));
    }

    default T lg(T arg) {
        return log(ten(), arg);
    }
}
