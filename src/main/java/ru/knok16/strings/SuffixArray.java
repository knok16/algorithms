package ru.knok16.strings;

import java.util.Arrays;

import static java.lang.Math.max;

public class SuffixArray {
    public static int[] compute(final String s) {
        return compute(s.toCharArray());
    }

    public static int[] compute(final char[] s) {
        final int n = s.length;
        final int[] result = new int[n];
        final int[] classes = new int[n];

        final int[] classCardinality = new int[max(n, Character.MAX_VALUE)];
        for (final char c : s) {
            classCardinality[c]++;
        }
        for (int i = 1; i < classCardinality.length; i++) {
            classCardinality[i] += classCardinality[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            result[--classCardinality[s[i]]] = i;
        }
        classes[0] = 0;
        int classCount = 1;
        for (int i = 1; i < n; i++) {
            if (s[result[i]] != s[result[i - 1]])
                classCount++;
            classes[result[i]] = classCount - 1;
        }

        final int[] t1 = new int[n];
        final int[] t2 = new int[n];
        for (int alreadySortedLength = 1; alreadySortedLength < n; alreadySortedLength *= 2) {
            for (int i = 0; i < n; i++) {
                t1[i] = (result[i] - alreadySortedLength + n) % n;
            }
            Arrays.fill(classCardinality, 0);
            for (int i = 0; i < n; i++) {
                classCardinality[classes[i]]++;
            }
            for (int i = 1; i < classCardinality.length; i++) {
                classCardinality[i] += classCardinality[i - 1];
            }
            for (int i = n - 1; i >= 0; i--) {
                result[--classCardinality[classes[t1[i]]]] = t1[i];
            }
            t2[0] = 0;
            classCount = 1;
            for (int i = 1; i < n; i++) {
                final int mid1 = (result[i] + alreadySortedLength) % n;
                final int mid2 = (result[i - 1] + alreadySortedLength) % n;
                if (classes[result[i]] != classes[result[i - 1]] || classes[mid1] != classes[mid2])
                    classCount++;
                t2[result[i]] = classCount - 1;
            }
            System.arraycopy(t2, 0, classes, 0, n);
        }

        return result;
    }
}
