package ru.knok16.strings;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class ZFunctionTest {
    @Test
    public void test1() {
        assertTrue(Arrays.equals(new int[]{0, 4, 3, 2, 1}, ZFunction.compute("aaaaa")));
    }

    @Test
    public void test2() {
        assertTrue(Arrays.equals(new int[]{0, 2, 1, 0, 2, 1, 0}, ZFunction.compute("aaabaab")));
    }

    @Test
    public void test3() {
        assertTrue(Arrays.equals(new int[]{0, 0, 1, 0, 3, 0, 1}, ZFunction.compute("abacaba")));
    }
}
