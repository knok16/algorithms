package ru.knok16.geometry;

import java.util.*;

public class ConvexHull {
    //TODO cover with tests
    public static List<Point> grahamAlgorithm(final Collection<Point> points) {
        Point min = null;
        final List<Point> another = new ArrayList<>();
        for (final Point p : points) {
            if (min == null || p.getX() < min.getX() || p.getX() == min.getX() && p.getY() < min.getY()) {
                if (min != null) {
                    another.add(min);
                }
                min = p;
            } else {
                another.add(p);
            }
        }

        Collections.sort(another, new PointAngleComparator(min).thenComparing(new PointDistanceComparator(min)));

        final Stack<Point> stack = new Stack<>();
        stack.push(min);

        for (final Point p : another) {
            while (stack.size() >= 2) {
                final Point prev = stack.pop();
                if (Point.isClockwise(stack.peek(), prev, p)) {
                    stack.push(prev);
                    break;
                }
            }
            stack.push(p);
        }

        return new ArrayList<>(stack);
    }
}
