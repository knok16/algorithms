package ru.knok16.math;

public class EulerFunction {
    public static int eulerFunction(int n, final int[] factorization) {
        //TODO improve
        assert n < factorization.length;
        int result = 1;
        while (n > 1) {
            int t = factorization[n];
            result *= t - 1;
            while (factorization[n] == factorization[n / t]) {
                result *= t;
                n /= t;
            }
            n /= t;
        }
        return result;
    }
}
