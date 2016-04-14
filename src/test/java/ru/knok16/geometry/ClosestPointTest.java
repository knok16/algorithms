package ru.knok16.geometry;

import org.junit.Test;
import ru.knok16.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClosestPointTest {
    private final double E = 1e-6;
    private final Random rnd = new Random(123);

    @Test
    public void test() {
        for (int i = 20; i < 120; i++) {
            final List<Point> points = randomPoints(i);

            final Optional<Pair<Point, Point>> actual = ClosestPoints.closestPoints(points);
            assertTrue(actual.isPresent());
            assertEquals(bf(points), actual.get().getFirst().euclideanDist(actual.get().getSecond()), E);
        }
    }

    private double bf(final List<Point> points) {
        double result = Double.POSITIVE_INFINITY;

        for (final Point p1 : points) {
            for (final Point p2 : points) {
                if (p1 != p2) {
                    result = Math.min(result, p1.euclideanDist(p2));
                }
            }
        }

        return result;
    }

    private List<Point> randomPoints(final int n) {
        final List<Point> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(new Point(rnd.nextDouble(), rnd.nextDouble()));
        }
        return result;
    }
}
