package ru.knok16.datastructures;

import java.util.HashMap;
import java.util.Map;

public class CountingSet<T> {
    private Map<T, Integer> map = new HashMap<>();
    private int size = 0;

    public int add(final T key) {
        return update(key, 1);
    }

    public int remove(final T key) {
        return update(key, -1);
    }

    public int update(final T key, final int delta) {
        final int oldValue = map.containsKey(key) ? map.get(key) : 0;
        final int newValue = oldValue + delta;
        if (newValue < 0)
            throw new IllegalArgumentException("New count is negative for key '" + key + "'");
        if (newValue == 0)
            map.remove(key);
        else
            map.put(key, newValue);
        size += delta;
        return oldValue;
    }

    public int size(final T key) {
        return map.containsKey(key) ? map.get(key) : 0;
    }

    public int size() {
        return size;
    }
}