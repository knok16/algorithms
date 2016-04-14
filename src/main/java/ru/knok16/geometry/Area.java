package ru.knok16.geometry;

import java.util.List;

public class Area {
    public static double triangleArea(final Point p1, final Point p2, final Point p3) {
        return Math.abs(signedTriangleArea(p1, p2, p3));
    }

    public static double signedTriangleArea(final Point p1, final Point p2, final Point p3) {
        return Point.signedArea(p1, p2, p3) / 2;
    }

    public static double signedArea(final List<Point> polygon) {
        double result = 0;
        final int n = polygon.size();
        for (int i = 0; i < n; i++) {
            final Point p1 = polygon.get(i);
            final Point p2 = polygon.get((i + 1) % n);
            result += (p1.getY() + p2.getY()) / 2 * (p1.getX() - p2.getX());
        }
        return result;
    }

    public static double area(final List<Point> polygon) {
        return Math.abs(signedArea(polygon));
    }
}
