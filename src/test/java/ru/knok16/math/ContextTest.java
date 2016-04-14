package ru.knok16.math;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ContextTest {
    @Test
    public void test() {
        final Context<Double> context = new Context<>(new DoubleSpace(1e-9));
        final Context<Double>.Variable x = context.var("x");
        final Context<Double>.Node derivative = context.add(
                context.power(x, context.constant(2d)),
                context.multiply(x, context.constant(-3d)),
                context.constant(-4d)
        ).derivative("x");

        double xd = -100;
        for (int i = 1; i <= 100; i++) {
            xd -= ((Context<Double>.Constant) derivative.calculate(Collections.singletonMap("x", xd))).getValue() / i;
        }
        assertEquals(1.5, xd, 1e9);
    }
}
