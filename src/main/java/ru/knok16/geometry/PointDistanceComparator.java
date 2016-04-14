package ru.knok16.geometry;

import java.util.Comparator;

public class PointDistanceComparator implements Comparator<Point> {
    private final Point center;

    public PointDistanceComparator(final Point center) {
        this.center = center;
    }

    @Override
    public int compare(final Point p1, final Point p2) {
        return Double.compare(center.euclideanDist(p1), center.euclideanDist(p2));
    }
}
