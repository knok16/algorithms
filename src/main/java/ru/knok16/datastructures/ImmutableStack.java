package ru.knok16.datastructures;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

public class ImmutableStack<T> implements Iterable<T> { //TODO implements List ?
    public static final ImmutableStack EMPTY_STACK = new ImmutableStack(null, 0, null);

    public static <T> ImmutableStack<T> emptyStack() {
        return EMPTY_STACK;
    }

    private final T value;
    private final int size;
    private final ImmutableStack<T> tail;

    private ImmutableStack(final T value, final int size, final ImmutableStack<T> tail) {
        this.value = value;
        this.size = size;
        this.tail = tail;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public ImmutableStack<T> push(final T value) {
        return new ImmutableStack<>(value, size + 1, tail);
    }

    public ImmutableStack<T> pop() {
        assertNotEmpty();
        return tail;
    }

    public T peek() {
        assertNotEmpty();
        return value;
    }

    public Iterator<T> iterator() {
        return new ImmutableStackIterator<>(this);
    }

    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(iterator(), size, 0); //TODO
    }

    private void assertNotEmpty() {
        if (size == 0) {
            throw new EmptyStackException();
        }
    }

    private static class ImmutableStackIterator<T> implements Iterator<T> {
        private ImmutableStack<T> stack;

        private ImmutableStackIterator(final ImmutableStack<T> stack) {
            this.stack = stack;
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public T next() {
            final T value = stack.peek();
            stack = stack.pop();
            return value;
        }
    }
}
