package ru.knok16.datastructures.dsu;

import org.junit.Test;
import ru.knok16.datastructures.dsu.DSU;
import ru.knok16.datastructures.dsu.DSUWithMetadata;

import static org.junit.Assert.*;

public class DSUTest {
    @Test
    public void withMetadata() {
        final String[] metadata = {"a", "x", "y", "b", "k", "c", "e", "z"};
        final DSUWithMetadata<String> dsu = new DSUWithMetadata<>(metadata, (a, b) -> a + b);

        for (int i = 0; i < metadata.length; i++)
            assertEquals(metadata[i], dsu.getMetadata(i));

        assertFalse(dsu.inTheSameSet(0, 5));
        assertEquals(1, dsu.size(0));
        assertEquals(1, dsu.size(5));
        assertTrue(dsu.union(0, 5));
        assertTrue(dsu.inTheSameSet(0, 5));
        assertEquals("ac", dsu.getMetadata(0));
        assertEquals(2, dsu.size(0));
        assertEquals(2, dsu.size(5));


        assertFalse(dsu.inTheSameSet(3, 0));
        assertEquals(1, dsu.size(3));
        assertTrue(dsu.union(3, 0));
        assertTrue(dsu.inTheSameSet(3, 5));
        assertEquals("acb", dsu.getMetadata(3));
        assertEquals(3, dsu.size(3));
        assertEquals(3, dsu.size(5));
        assertEquals(3, dsu.size(0));
    }

    @Test
    public void withoutMetadata() {
        final DSU dsu = new DSU(4);

        assertFalse(dsu.inTheSameSet(3, 0));
        assertEquals(1, dsu.size(3));
        assertTrue(dsu.union(3, 0));
        assertTrue(dsu.inTheSameSet(0, 3));
        assertEquals(2, dsu.size(0));
        assertEquals(1, dsu.size(1));
        assertEquals(1, dsu.size(2));
        assertEquals(2, dsu.size(3));
    }
}
