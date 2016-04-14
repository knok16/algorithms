package ru.knok16.geometry;

import java.util.Comparator;

public class PointAngleComparator implements Comparator<Point> {
    private final Point center;

    public PointAngleComparator(final Point center) {
        this.center = center;
    }

    @Override
    public int compare(final Point p1, final Point p2) {
        final double d1 = Math.atan2(p1.getY() - center.getY(), p1.getX() - center.getX());
        final double d2 = Math.atan2(p2.getY() - center.getY(), p2.getX() - center.getX());
        return Double.compare(d1, d2);
    }
}
