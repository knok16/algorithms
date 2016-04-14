package ru.knok16.sorting;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class Merge {
    private final Random rnd = new Random(123);

    @Test
    public void merge() {
        assertEquals(Arrays.asList(1, 3, 5), Sortings.merge(Arrays.asList(1, 3, 5), Collections.emptyList()));
        assertEquals(Arrays.asList(1, 3, 5), Sortings.merge(Collections.emptyList(), Arrays.asList(1, 3, 5)));
        assertEquals(Arrays.asList(1, 3, 5, 100), Sortings.merge(Arrays.asList(1, 3, 5), Collections.singletonList(100)));
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), Sortings.merge(Arrays.asList(1, 3, 5), Arrays.asList(2, 4)));
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), Sortings.merge(Arrays.asList(1, 3, 5), Arrays.asList(2, 4, 6)));
    }

    @Test
    public void inPlaceMerge() {

        //TODO
    }
}
