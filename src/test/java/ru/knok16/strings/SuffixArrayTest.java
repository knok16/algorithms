package ru.knok16.strings;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class SuffixArrayTest {
    @Test
    public void test1() {
        assertTrue(Arrays.equals(new int[]{5, 2, 3, 0, 4, 1}, SuffixArray.compute("abaab#")));
    }

    @Test
    public void test2() {
        assertTrue(Arrays.equals(new int[]{2, 0, 3, 1, 4}, SuffixArray.compute("abaab")));
    }

    @Test
    public void test3() {
        assertTrue(Arrays.equals(new int[]{3, 0, 1, 2}, SuffixArray.compute("aaba")));
    }
}
