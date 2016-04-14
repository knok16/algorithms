package ru.knok16.geometry;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class AreaTest {
    private double E = 1e-6;

    @Test
    public void negative() {
        final Point p1 = new Point(0, 0);
        final Point p2 = new Point(2, 2);
        final Point p3 = new Point(10, 0);
        assertEquals(Area.signedTriangleArea(p1, p2, p3), Area.signedArea(Arrays.asList(p1, p2, p3)), E);
    }

    @Test
    public void zero() {
        final Point p1 = new Point(1, 1);
        final Point p2 = new Point(3, 2);
        final Point p3 = new Point(11, 6);
        assertEquals(0, Area.signedArea(Arrays.asList(p1, p2, p3)), E);
        final Point p4 = new Point(-3, -1);
        assertEquals(0, Area.signedArea(Arrays.asList(p1, p2, p4)), E);
    }

    @Test
    public void positive() {
        final Point p1 = new Point(Math.PI, Math.PI);
        final Point p2 = new Point(0, 0);
        final Point p3 = new Point(Math.PI, -Math.PI);
        assertEquals(Area.signedTriangleArea(p1, p2, p3), Area.signedArea(Arrays.asList(p1, p2, p3)), E);
    }
}
