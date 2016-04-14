package ru.knok16.datastructures.segmenttree;

import org.junit.Test;
import ru.knok16.datastructures.segmenttree.SegmentTree;
import ru.knok16.sorting.Sortings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SegmentTreeTest {
    @Test
    public void test() {
        final SegmentTree<List<Integer>> tree = new SegmentTree<>(new Integer[]{1, 23, 4, 3}, Collections::singletonList, Sortings::merge);
        assertEquals(Arrays.asList(1, 4, 23), tree.query(0, 2));
        assertEquals(Arrays.asList(1, 3, 4, 23), tree.query(0, 3));
        assertEquals(Arrays.asList(3, 4, 23), tree.query(1, 3));
    }
}
