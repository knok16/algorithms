package ru.knok16.math;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class EratosthenesSieveTest {
    @Test
    public void primesSmall() {
        final List<Integer> primes = EratosthenesSieve.primesUpTo(100).getFirst();
        assertEquals(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97), primes);
    }

    @Test
    public void primesBig() {
        final Set<Integer> primes = new HashSet<>(EratosthenesSieve.primesUpTo(100003).getFirst());
        assertTrue(primes.contains(9049));
        assertTrue(primes.contains(14401));
        assertTrue(primes.contains(21283));
        assertTrue(primes.contains(31231));
        assertTrue(primes.contains(43093));
        assertTrue(primes.contains(56569));
        assertTrue(primes.contains(84179));
        assertTrue(primes.contains(91951));
        assertTrue(primes.contains(99991));
        assertTrue(primes.contains(100003));
        assertFalse(primes.contains(100019));

        assertFalse(primes.contains(2 * 2));
        assertFalse(primes.contains(3 * 2));
        assertFalse(primes.contains(409 * 17));
        assertFalse(primes.contains(571 * 457));
        assertFalse(primes.contains(21283 * 3));
        assertFalse(primes.contains(31231 * 2));
        assertFalse(primes.contains(43093 * 2));
    }

    @Test
    public void factorization() {
        final int[] expected = {
                0, 1, 2, 3, 2, 5, 2, 7, 2, 3,
                2, 11, 2, 13, 2, 3, 2, 17, 2, 19,
                2, 3, 2, 23, 2, 5, 2, 3, 2, 29,
                2, 31, 2, 3, 2, 5, 2, 37, 2, 3,
                2, 41, 2, 43, 2, 3, 2, 47, 2, 7,
                2, 3, 2, 53, 2, 5, 2, 3, 2, 59,
                2, 61, 2, 3, 2, 5, 2, 67, 2, 3,
                2, 71, 2, 73, 2, 3, 2, 7, 2, 79,
                2, 3, 2, 83, 2, 5, 2, 3, 2, 89,
                2, 7, 2, 3, 2, 5, 2, 97, 2, 3
        };
        assertTrue(Arrays.equals(expected, EratosthenesSieve.primesUpTo(expected.length - 1).getSecond()));
    }
}
