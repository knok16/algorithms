package ru.knok16.datastructures;

import java.util.NoSuchElementException;

public class ImmutableQueue<T> {
    public static final ImmutableQueue EMPTY_QUEUE = new ImmutableQueue(ImmutableStack.EMPTY_STACK, ImmutableStack.EMPTY_STACK);

    public static <T> ImmutableQueue<T> emptyQueue() {
        return EMPTY_QUEUE;
    }

    private ImmutableStack<T> forward, backward;

    private ImmutableQueue(final ImmutableStack<T> forward, final ImmutableStack<T> backward) {
        this.forward = forward;
        this.backward = backward;
    }

    public T peek() {
        assertNotEmpty();
        if (forward.isEmpty()) {
            rebalance();
        }
        return forward.peek();
    }

    public ImmutableQueue<T> pop() {
        assertNotEmpty();
        if (forward.isEmpty()) {
            rebalance();
        }
        return new ImmutableQueue<>(forward.pop(), backward);
    }

    public ImmutableQueue<T> push(final T value) {
        return new ImmutableQueue<>(forward, backward.push(value));
    }

    public boolean isEmpty() {
        return forward.isEmpty() && backward.isEmpty();
    }

    public int size() {
        return forward.size() + backward.size();
    }

    private void assertNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private void rebalance() {
        //this.forward = this.backward.revert();
        this.backward = ImmutableStack.emptyStack();
    }
}
