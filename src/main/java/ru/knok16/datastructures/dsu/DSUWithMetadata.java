package ru.knok16.datastructures.dsu;

import java.util.Arrays;
import java.util.function.BinaryOperator;

public class DSUWithMetadata<T> extends DSU {
    private final T[] metadata;
    private final BinaryOperator<T> operator;

    public DSUWithMetadata(final T[] metadata, final BinaryOperator<T> operator) {
        super(metadata.length);
        this.metadata = Arrays.copyOf(metadata, metadata.length);
        this.operator = operator;
    }

    public T getMetadata(final int a) {
        return metadata[getLeader(a)];
    }

    @Override
    protected void _union(final int parent, final int child) {
        metadata[parent] = operator.apply(metadata[parent], metadata[child]);
        metadata[child] = null;
    }
}
