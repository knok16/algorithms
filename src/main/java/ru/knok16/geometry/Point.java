package ru.knok16.geometry;

import java.util.Objects;

public class Point {
    public static final Point O = new Point(0, 0);

    public static double signedArea(final Point p1, final Point p2, final Point p3) {
        return p2.x * p3.y - p3.x * p2.y
                - p1.x * p3.y + p3.x * p1.y
                + p1.x * p2.y - p2.x * p1.y;
    }

    public static double sign(final Point p1, final Point p2, final Point p3) {
        return Math.signum(signedArea(p1, p2, p3));
    }

    public static boolean isClockwise(final Point p1, final Point p2, final Point p3) {
        return signedArea(p1, p2, p3) > 0;
    }

    public static boolean isCounterClockwise(final Point p1, final Point p2, final Point p3) {
        return signedArea(p1, p2, p3) < 0;
    }

    private final double x, y;
    private final double angle;

    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
        this.angle = Math.atan2(y, x);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public double euclideanDist(final Point point) {
        final double dx = this.x - point.x;
        final double dy = this.y - point.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double manhattanDistance(final Point point) {
        final double dx = Math.abs(this.x - point.x);
        final double dy = Math.abs(this.y - point.y);
        return dx + dy;
    }

    public Point add(final Point point) {
        return new Point(this.x + point.x, this.y + point.y);
    }

    public Point substruct(final Point point) {
        return new Point(this.x - point.x, this.y - point.y);
    }

    public Point scale(final double k) {
        return new Point(this.x * k, this.y * k);
    }

    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ')';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
