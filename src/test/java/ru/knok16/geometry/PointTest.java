package ru.knok16.geometry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PointTest {
    private double E = 1e-6;

    @Test
    public void angle() {
        assertEquals(-Math.PI / 4, new Point(1, -1).getAngle(), E);
    }

    @Test
    public void angleOfO() {
        assertEquals(0, Point.O.getAngle(), E);
    }

    @Test
    public void getX() {
        assertEquals(0, Point.O.getX(), E);
        assertEquals(0.2, new Point(0.2, -1).getX(), E);
    }

    @Test
    public void getY() {
        assertEquals(0, Point.O.getY(), E);
        assertEquals(-1, new Point(0.2, -1).getY(), E);
    }

    @Test
    public void rightTurn() {
        final Point p1 = new Point(1, 1);
        final Point p2 = new Point(2, 2);
        final Point p3 = new Point(10, 0);
        assertEquals(-1, Point.sign(p1, p2, p3), E);
        assertTrue(Point.isCounterClockwise(p1, p2, p3));
        assertFalse(Point.isClockwise(p1, p2, p3));
    }

    @Test
    public void straight() {
        final Point p1 = new Point(1, 1);
        final Point p2 = new Point(3, 2);
        final Point p3 = new Point(11, 6);
        final Point p4 = new Point(-3, -1);
        assertEquals(0, Point.sign(p1, p2, p3), E);
        assertFalse(Point.isCounterClockwise(p1, p2, p3));
        assertFalse(Point.isClockwise(p1, p2, p3));
        assertEquals(0, Point.sign(p1, p2, p4), E);
        assertFalse(Point.isCounterClockwise(p1, p2, p4));
        assertFalse(Point.isClockwise(p1, p2, p4));
    }

    @Test
    public void leftTurn() {
        final Point p1 = new Point(Math.PI, Math.PI);
        final Point p2 = new Point(0, 0);
        final Point p3 = new Point(Math.PI, -Math.PI);
        assertEquals(1, Point.sign(p1, p2, p3), E);
        assertTrue(Point.isClockwise(p1, p2, p3));
        assertFalse(Point.isCounterClockwise(p1, p2, p3));
    }

    @Test
    public void euclideanDistTo() {
        assertEquals(5, Point.O.euclideanDist(new Point(3, 4)), E);
        assertEquals(9.219544, new Point(1, -5).euclideanDist(new Point(3, 4)), E);
    }

    @Test
    public void manhattanDistance() {
        assertEquals(7, Point.O.manhattanDistance(new Point(3, 4)), E);
        assertEquals(11, new Point(1, -5).manhattanDistance(new Point(3, 4)), E);
    }
}
