package ru.knok16.math;

import ru.knok16.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class EratosthenesSieve {
    /**
     * 1 is not prime
     *
     * @param n last limit primes (inclusive)
     * @return all list of prime numbers up to n
     */
    public static Pair<List<Integer>, int[]> primesUpTo(final int n) {
        final List<Integer> primes = new ArrayList<>();
        final int[] leastFactor = new int[n + 1];
        leastFactor[1] = 1;
        for (int i = 2; i <= n; i++) {
            if (leastFactor[i] == 0) {
                primes.add(i);
                leastFactor[i] = i;
            }
            for (int j = 0; j < primes.size() && primes.get(j) <= leastFactor[i] && primes.get(j) * i <= n; j++) {
                leastFactor[i * primes.get(j)] = primes.get(j);
            }
        }
        return Pair.of(primes, leastFactor);
    }
}
