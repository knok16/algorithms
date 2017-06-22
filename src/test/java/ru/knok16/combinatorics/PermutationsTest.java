package ru.knok16.combinatorics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PermutationsTest {
    @Test
    public void checkCount() {
        assertEquals(1, new Permutations().permutationsOf(1).count());
        assertEquals(2, new Permutations().permutationsOf(2).count());
        assertEquals(6, new Permutations().permutationsOf(3).count());
        assertEquals(24, new Permutations().permutationsOf(4).count());
        assertEquals(120, new Permutations().permutationsOf(5).count());
        assertEquals(3628800, new Permutations().permutationsOf(10).count());
    }
}
