package ru.knok16.sorting;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KthOrder {
    private final Random rnd = new Random(123);

    @Test
    public void test() {
        final int testCases = 120;
        for (int i = 1; i < testCases; i++) {
            final Integer[] randomArray = getRandomArray(i);
            final Integer[] sortedArray = randomArray.clone();
            Arrays.sort(sortedArray);

            for (int j = 0; j < i; j++) {
                assertEquals(sortedArray[j], Sortings.kthOrder(randomArray, j));
            }
        }
    }

    @Test
    public void sortedArray() {
        final int n = 100;
        final Integer[] randomArray = getRandomArray(n);
        Arrays.sort(randomArray);
        final Integer[] sortedArray = randomArray.clone();

        for (int j = 0; j < n; j++) {
            assertEquals(sortedArray[j], Sortings.kthOrder(randomArray, j));
        }
    }

    @Test
    public void arraySortedBackward() {
        final int n = 100;
        final Integer[] randomArray = getRandomArray(n);
        Arrays.sort(randomArray, Comparator.reverseOrder());
        final Integer[] sortedArray = randomArray.clone();
        Arrays.sort(sortedArray);

        assertEquals(sortedArray[n / 3], Sortings.kthOrder(randomArray, n / 3));
    }

    @Test
    public void originalArrayWasNotChanged() {
        final int n = 100;
        final Integer[] randomArray = getRandomArray(n);
        Arrays.sort(randomArray, Comparator.reverseOrder());
        final Integer[] expected = randomArray.clone();

        for (int i = 0; i < n; i++) {
            assertEquals(expected[n - i - 1], Sortings.kthOrder(randomArray, i));
            assertTrue(Arrays.equals(expected, randomArray));
        }
    }

    private Integer[] getRandomArray(final int n) {
        final Integer[] result = new Integer[n];
        for (int i = 0; i < n; i++) result[i] = rnd.nextInt();
        return result;
    }
}
