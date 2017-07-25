package ru.knok16.math;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class EulerFunctionTest {
    @Test
    public void eulerFunction() {
        final int[] expected = {
                0, 1, 1, 2, 2, 4, 2, 6, 4, 6,
                4, 10, 4, 12, 6, 8, 8, 16, 6, 18,
                8, 12, 10, 22, 8, 20, 12, 18, 12, 28,
                8, 30, 16, 20, 16, 24, 12, 36, 18, 24,
                16, 40, 12, 42, 20, 24, 22, 46, 16, 42,
                20, 32, 24, 52, 18, 40, 24, 36, 28, 58,
                16, 60, 30, 36, 32, 48, 20, 66, 32, 44,
                24, 70, 24, 72, 36, 40, 36, 60, 24, 78,
                32, 54, 40, 82, 24, 64, 42, 56, 40, 88,
                24, 72, 44, 60, 46, 72, 32, 96, 42, 60
        };
        final int n = expected.length;
        final int[] factorization = EratosthenesSieve.primesUpTo(n - 1).getSecond();
        for (int i = 1; i < n; i++) {
            assertEquals(expected[i], EulerFunction.eulerFunction(i, factorization));
        }
    }
}
