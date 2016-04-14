package ru.knok16.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sortings {
    public static <T extends Comparable<T>> List<T> merge(final List<T> list1, final List<T> list2) {
        return merge(list1, list2, Comparator.naturalOrder());
    }

    //TODO rewrite to use iterators
    public static <T> List<T> merge(final List<T> list1, final List<T> list2, final Comparator<T> comparator) {
        final int n1 = list1.size();
        final int n2 = list2.size();
        final int n = n1 + n2;
        final List<T> result = new ArrayList<>(n);

        for (int i = 0, i1 = 0, i2 = 0; i < n; i++) {
            if (i2 >= n2 || i1 < n1 && comparator.compare(list1.get(i1), list2.get(i2)) <= 0) {
                result.add(list1.get(i1++));
            } else {
                result.add(list2.get(i2++));
            }
        }

        return result;
    }

    public static <T extends Comparable<T>> void inPlaceMerge(final T[] array, final int l, final int m, final int r) {
        inPlaceMerge(array, l, m, r, Comparator.naturalOrder());
    }

    public static <T> void inPlaceMerge(final T[] array, final int l, final int m, final int r, final Comparator<T> comparator) {
        final int n = r - l;
        final T[] temp = (T[]) new Object[n];
        System.arraycopy(array, l, temp, 0, n);

        for (int i = l, i1 = 0, i2 = m - l; i < r; i++) {
            array[i] = temp[(i2 >= n || i1 < (m - l) && comparator.compare(temp[i1], temp[i2]) <= 0) ? i1++ : i2++];
        }
    }

    public static <T extends Comparable<T>> T kthOrder(final T[] array, final int k) {
        return kthOrder(array, k, Comparator.naturalOrder());
    }

    public static <T extends Comparable<T>> T kthOrder(final T[] array, final int k, final Comparator<T> comparator) {
        return kthOrder(array.clone(), 0, array.length, k, comparator);
    }

    private static <T> T kthOrder(final T[] array, final int l, final int r, final int k, final Comparator<T> comparator) {
        final int m = (l + r) / 2;
        final T pivot = array[m];

        int i = l, j = r - 1;
        while (i < j) {
            while (i < j && comparator.compare(array[i], pivot) < 0) i++;
            while (i < j && comparator.compare(pivot, array[j]) < 0) j--;
            final T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        if (i == k)
            return pivot;
        else if (k < i)
            return kthOrder(array, l, i, k, comparator);
        else // k > i
            return kthOrder(array, i + 1, r, k, comparator);
    }
}
