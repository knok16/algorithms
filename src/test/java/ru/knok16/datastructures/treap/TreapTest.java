package ru.knok16.datastructures.treap;

import org.junit.Ignore;
import org.junit.Test;
import ru.knok16.utils.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static java.lang.Math.min;
import static org.junit.Assert.*;

public class TreapTest {
    private final Random rnd = new Random(123);

    @Test
    public void basicTest() {
        final int n = 100;
        final Integer[] randomArray = getRandomArray(n);
        final Treap<Integer, ?> list = Treap.asList(randomArray);

        assertEqualsListToArray(randomArray, list);
    }

    @Test
    public void size() {
        final int n = 100;
        assertEquals(n, Treap.asList(new Integer[n]).size());
    }

    @Test
    public void isEmpty() {
        assertFalse(Treap.asList(new Integer[10]).isEmpty());
        assertTrue(new Treap<>().isEmpty());
    }

    @Test
    public void contains() {
        //TODO
    }

    @Test
    public void iterator() {
        //TODO
    }

    @Test
    public void toArray() {
        final int n = 100;
        final Integer[] randomArray = getRandomArray(n);
        final Treap<Integer, ?> list = Treap.asList(randomArray);

        final Object[] array = list.toArray();
        assertTrue(Arrays.equals(randomArray, array));
    }

    @Test
    public void toArrayParametrized() {
        final int n = 100;
        final Integer[] randomArray = getRandomArray(n);
        final List<Integer> list = Treap.asList(randomArray);

        assertTrue(Arrays.equals(randomArray, list.toArray(new Integer[3])));
    }

    @Test
    public void add() {
        final int n = 100;
        final List<Integer> list = Treap.asList(getRandomArray(n));
        assertEquals(n, list.size());
        list.add(200);
        assertEquals(n + 1, list.size());
        assertEquals(Integer.valueOf(200), list.get(n));
    }

    @Test
    public void removeObject() {
        //TODO
    }

    @Test
    public void containsAll() {
        //TODO
    }

    @Test
    public void addAll() {
        final int n = 100;
        final Integer[] randomArray = getRandomArray(n);
        final Treap<Integer, ?> list = new Treap<>();

        list.addAll(Arrays.asList(randomArray));
        assertEqualsListToArray(randomArray, list);
    }

    @Test
    public void addAllWithIndex() {
        //TODO 2
    }

    @Test
    public void removeAll() {
        //TODO
    }

    @Test
    public void retainAll() {
        //TODO
    }

    @Test
    public void clear() {
        final int n = 100;
        final Treap<Integer, ?> list = Treap.asList(new Integer[n]);

        assertFalse(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void get() {
        final int n = 100;
        final Integer[] randomArray = getRandomArray(n);
        final Treap<Integer, ?> list = Treap.asList(randomArray);

        assertEqualsListToArray(randomArray, list);
    }

    @Test
    public void set() {
        final int n = 100;
        final Treap<Integer, ?> list = Treap.asList(new Integer[n]);

        for (int i = 0; i < n; i++) {
            assertNotEquals(Integer.valueOf(i + 1), list.get(i));
            list.set(i, i + 1);
            assertEquals(Integer.valueOf(i + 1), list.get(i));
        }
    }

    @Test
    public void addWithIndex() {
        final Treap<Integer, ?> list = Treap.asList(3, 5, 1, 2);
        list.add(1, 10);
        assertEqualsListToArray(new Integer[]{3, 10, 5, 1, 2}, list);
    }

    @Test
    public void remove() {
        final Treap<Integer, ?> list = Treap.asList(3, 5, 1, 2);
        list.remove(1);
        assertEqualsListToArray(new Integer[]{3, 1, 2}, list);
    }

    @Test
    public void indexOf() {
        //TODO
    }

    @Test
    public void lastIndexOf() {
        //TODO
    }

    @Test
    public void listIterator() {
        //TODO
    }

    @Test
    public void listIteratorWithIndex() {
        //TODO
    }

    @Test
    public void subList() {
        final int n = 100;
        final Integer[] array = getRandomArray(100);
        final Treap<Integer, ?> list = Treap.asList(array);
        for (int from = 0; from < n; from++) {
            for (int to = from + 1; to <= n; to++) {
                final List<Integer> result = list.cutSubList(from, to);
                final int size = to - from;
                assertEquals(size, result.size());
                for (int i = 0; i < size; i++) {
                    assertEquals(array[i + from], result.get(i));
                }
            }
        }
    }

    private Integer[] getRandomArray(final int n) {
        final Integer[] result = new Integer[n];
        for (int i = 0; i < n; i++) result[i] = rnd.nextInt();
        return result;
    }

    private <T> void assertEqualsListToArray(final T[] randomArray, final List<T> list) {
        assertNotNull(list);
        assertEquals(randomArray.length, list.size());
        for (int i = 0; i < randomArray.length; i++) {
            assertEquals(randomArray[i], list.get(i));
        }
    }

    @Test
    public void aggregateWeirdSum() {
        final int n = 100;
        final Integer[] randomArray = getRandomArray(n);
        final Treap<Integer, Pair<Integer, Integer>> list = new Treap<>(
                i -> Pair.of(i, 1),
                (a, b) -> Pair.of(
                        a.getFirst() + (a.getSecond() % 2 == 0 ? b.getFirst() : -b.getFirst()),
                        a.getSecond() + b.getSecond()
                )
        );

        list.addAll(Arrays.asList(randomArray));

        for (int from = 0; from < n; from++) {
            for (int to = from + 1; to <= n; to++) {
                Integer a = 0;
                for (int i = from, j = 0; i < to; i++, j++) {
                    a += j % 2 == 0 ? randomArray[i] : -randomArray[i];
                }
                assertEquals(a, list.aggregate(from, to).getFirst());
            }
        }
    }

    @Test
    public void aggregateMin() {
        final int n = 1000;
        final Integer[] randomArray = getRandomArray(n);
        final Treap<Integer, Integer> list = new Treap<>(Function.identity(), Math::min);
        list.addAll(Arrays.asList(randomArray));

        long t1 = 0;
        long t2 = 0;
        for (int from = 0; from < n; from++) {
            int min = Integer.MAX_VALUE;
            for (int to = from + 1; to <= n; to++) {
                t1 -= System.currentTimeMillis();
                final Integer actual = list.aggregate(from, to);
                t1 += System.currentTimeMillis();
                t2 -= System.currentTimeMillis();
                min = min(min, randomArray[to - 1]);
                t2 += System.currentTimeMillis();
                assertEquals(min, actual.intValue());
            }
        }

        System.out.println("Treap: " + t1);
        System.out.println("BruteForce:" + t2);
    }

    private Integer findMinimum(final Integer[] randomArray, int from, int to) {
        int min = Integer.MAX_VALUE;
        for (int i = from; i < to; i++) {
            min = min(min, randomArray[i]);
        }
        return min;
    }

    @Test
    public void aggregateWithShift() {
        final Treap<Integer, Pair<Integer, Integer>> list = new Treap<>(
                i -> Pair.of(i, 1),
                (a, b) -> Pair.of(
                        a.getFirst() + (a.getSecond() % 2 == 0 ? b.getFirst() : -b.getFirst()),
                        a.getSecond() + b.getSecond()
                )
        );
        list.addAll(Arrays.asList(5, 1, 4, 2));

        assertEquals(Integer.valueOf(6), list.aggregate().getFirst());
        list.add(2, -10); //5, 1, -10, 4, 2
        assertEquals(Integer.valueOf(-8), list.aggregate().getFirst());
    }
}
