package ru.knok16.geometry;

import ru.knok16.sorting.Sortings;
import ru.knok16.utils.Pair;

import java.util.*;

public class ClosestPoints {
    //should be at least 3
    private static final int PROBLEM_SIZE_FOR_BF_ALGO = 3;

    public static Optional<Pair<Point, Point>> closestPoints(final List<Point> points) {
        if (points.size() <= 1) return Optional.empty();
        final int size = points.size();
        final Point[] array = points.toArray(new Point[size]);
        Arrays.sort(array, Comparator.comparing(Point::getX));
        return closestPoints(array, 0, size);
    }

    private static Optional<Pair<Point, Point>> closestPoints(final Point[] points, final int l, final int r) {
        if (r - l <= PROBLEM_SIZE_FOR_BF_ALGO) {
            double minDist = 0;
            Optional<Pair<Point, Point>> best = Optional.empty();
            for (int i = l; i < r - 1; i++)
                for (int j = i + 1; j < r; j++) {
                    final double h = points[i].euclideanDist(points[j]);
                    if (!best.isPresent() || h < minDist) {
                        minDist = h;
                        best = Optional.of(Pair.of(points[i], points[j]));
                    }
                }
            Arrays.sort(points, l, r, Comparator.comparing(Point::getY));
            return best;
        } else {
            final int m = (l + r) / 2;
            final double middleX = points[m].getX();
            final Optional<Pair<Point, Point>> leftAnswer = closestPoints(points, l, m);
            final double lh = leftAnswer.map(p -> p.getLeft().euclideanDist(p.getRight())).orElse(Double.POSITIVE_INFINITY);
            final Optional<Pair<Point, Point>> rightAnswer = closestPoints(points, m, r);
            final double rh = rightAnswer.map(p -> p.getLeft().euclideanDist(p.getRight())).orElse(Double.POSITIVE_INFINITY);
            double minDist = Math.min(lh, rh);
            Optional<Pair<Point, Point>> best = lh <= rh ? leftAnswer : rightAnswer;

            Sortings.inPlaceMerge(points, l, m, r, Comparator.comparing(Point::getY));

            final List<Point> b = new ArrayList<>();
            for (int i = l; i < r; i++) {
                final Point p1 = points[i];
                if (Math.abs(middleX - p1.getX()) < minDist) {
                    for (int j = b.size() - 1; j >= 0 && (p1.getY() - b.get(j).getY()) < minDist; j--) {
                        final Point p2 = b.get(j);
                        final double dist = p1.euclideanDist(p2);
                        if (dist < minDist) {
                            minDist = dist;
                            best = Optional.of(Pair.of(p1, p2));
                        }
                    }
                    b.add(p1);
                }
            }

            return best;
        }
    }
}
