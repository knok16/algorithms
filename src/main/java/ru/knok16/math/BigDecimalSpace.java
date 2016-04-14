package ru.knok16.math;

import java.math.BigDecimal;

public class BigDecimalSpace implements Space<BigDecimal> {
    private final BigDecimal eps;

    public BigDecimalSpace(final BigDecimal eps) {
        this.eps = eps;
    }

    @Override
    public BigDecimal zero() {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal one() {
        return BigDecimal.ONE;
    }

    @Override
    public BigDecimal e() {
        return BigDecimal.valueOf(Math.E);
    }

    @Override
    public BigDecimal ten() {
        return BigDecimal.TEN;
    }

    @Override
    public int compare(final BigDecimal left, final BigDecimal right) {
        return (left.subtract(right).compareTo(eps) < 0) ? 0 : left.compareTo(right);
    }

    @Override
    public BigDecimal negate(final BigDecimal node) {
        return node.negate();
    }

    @Override
    public BigDecimal add(final BigDecimal left, final BigDecimal right) {
        return left.add(right);
    }

    @Override
    public BigDecimal subtract(final BigDecimal left, final BigDecimal right) {
        return left.subtract(right);
    }

    @Override
    public BigDecimal multiply(final BigDecimal left, final BigDecimal right) {
        return left.multiply(right);
    }

    @Override
    public BigDecimal divide(final BigDecimal left, final BigDecimal right) {
        return left.divide(right);
    }

    @Override
    public BigDecimal power(final BigDecimal left, final BigDecimal right) {
        //TODO
        return BigDecimal.valueOf(Math.pow(left.doubleValue(), right.doubleValue()));
    }

    @Override
    public BigDecimal ln(final BigDecimal node) {
        //TODO
        return BigDecimal.valueOf(Math.log(node.doubleValue()));
    }
}
