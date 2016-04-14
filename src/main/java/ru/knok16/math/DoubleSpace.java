package ru.knok16.math;

public class DoubleSpace implements Space<Double> {
    private final double eps;

    public DoubleSpace(final double eps) {
        this.eps = eps;
    }

    @Override
    public Double zero() {
        return 0d;
    }

    @Override
    public Double one() {
        return 1d;
    }

    @Override
    public Double e() {
        return Math.E;
    }

    @Override
    public Double ten() {
        return 10d;
    }

    @Override
    public int compare(final Double left, final Double right) {
        return Math.abs(left - right) < eps ? 0 : Double.compare(left, right);
    }

    @Override
    public Double negate(final Double node) {
        return -node;
    }

    @Override
    public Double add(final Double left, final Double right) {
        return left + right;
    }

    @Override
    public Double subtract(final Double left, final Double right) {
        return left - right;
    }

    @Override
    public Double multiply(final Double left, final Double right) {
        return left * right;
    }

    @Override
    public Double divide(final Double left, final Double right) {
        return left / right;
    }

    @Override
    public Double power(final Double left, final Double right) {
        return Math.pow(left, right);
    }

    @Override
    public Double ln(final Double node) {
        return Math.log(node);
    }
}
