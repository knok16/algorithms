package ru.knok16.strings;

import static java.lang.Math.min;

public class ZFunction {
    public static int[] compute(final CharSequence s) {
        final int n = s.length();
        final int[] result = new int[n];

        int l = 0, r = -1;
        for (int i = 1; i < n; i++) {
            if (i <= r) {
                result[i] = min(result[i - l], r - i + 1);
            }
            while (result[i] + i < n && s.charAt(result[i]) == s.charAt(result[i] + i))
                result[i]++;
            if (i + result[i] - 1 > r) {
                l = i;
                r = result[i] + i - 1;
            }
        }

        return result;
    }
}
