package ru.knok16.geometry;

import static java.lang.Math.abs;

public class Line {
    private static final double E = 1e-9;
    private final double a, b, c;

    public static Line withCoefficient(final double a, final double b, final double c) {
        //TODO maybe simplify
        final double z = Math.sqrt(a * a + b * b);
        if (abs(a) < E) {
            final double signum = Math.signum(b);
            return new Line(0, signum * b / z, signum * c / z);
        } else if (a < 0) {
            return new Line(-a / z, -b / z, -c / z);
        } else {
            return new Line(a / z, b / z, c / z);
        }
    }

    public static Line withCoefficientThroughPoint(final double a, final double b, final Point point) {
        //TODO improve: normalize A and B before calculating C
        return withCoefficient(a, b, -a * point.getX() - b * point.getY());
    }

    public static Line by2Points(final Point p1, final Point p2) {
        return withCoefficientThroughPoint(p2.getY() - p1.getY(), p1.getX() - p2.getX(), p1);
    }

    private Line(final double a, final double b, final double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double evaluateAt(final Point p) {
        return a * p.getX() + b * p.getY() + c;
    }

    public Line perpendicularThrough(final Point p) {
        return withCoefficientThroughPoint(-b, a, p);
    }

    public Point intersect(final Line line) {
        final double zn = det(this.a, this.b, line.a, line.b);
        if (abs(zn) < E) return null;
        return new Point(-det(this.c, this.b, line.c, line.b) / zn, -det(this.a, this.c, line.a, line.c) / zn);
    }

    public boolean isParallel(final Line line) {
        return abs(det(this.a, this.b, line.a, line.b)) < E;
    }

    public boolean isEquals(final Line line) {
        return isParallel(line)
                && abs(det(this.a, this.c, line.a, line.c)) < E
                && abs(det(this.b, this.c, line.b, line.c)) < E;
    }

    public Point project(final Point point) {
        if (abs(evaluateAt(point)) <= E) {
            return point;
        } else {
            return intersect(perpendicularThrough(point)); // TODO optimize
        }
    }

    public Line parallelThroughPoint(final Point point) {
        return withCoefficientThroughPoint(a, b, point);
    }

    public Line perpendicularThroughPoint(final Point point) {
        return withCoefficientThroughPoint(b, -a, point);
    }

    private static double det(final double a, final double b, final double c, final double d) {
        return a * d - b * c;
    }
}
