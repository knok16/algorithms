package ru.knok16.datastructures.treap;

import ru.knok16.utils.Pair;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.lang.Math.max;
import static java.lang.Math.min;

//TODO add revert(final int l, final int r)

/**
 * Implementation of {@link java.util.List<E>} based on Treap (https://en.wikipedia.org/wiki/Treap)
 */
public class Treap<E, A> extends AbstractList<E> implements List<E>, Cloneable, RandomAccess {
    protected final Random random = new Random();//TODO
    private final Function<E, A> converter;
    private final BinaryOperator<A> aggregator;
    protected Node root;

    /**
     * Creates list from array of elements.
     * Average time complexity: O(n log n).
     *
     * @param elements elements of new list
     * @return created list
     */
    public static <E> Treap<E, ?> asList(final E... elements) {
        final Treap<E, ?> result = new Treap<>();
        //TODO improve to linear time complexity
        Collections.addAll(result, elements);
        return result;
    }

    private Treap(final Node root, final Function<E, A> converter, final BinaryOperator<A> aggregator) {
        this.root = root;
        this.converter = converter;
        this.aggregator = aggregator;
    }

    /**
     * Creates empty list.
     * Time complexity: O(1).
     */
    public Treap() {
        this(null, null);
    }

    /**
     * Creates empty list.
     * Time complexity: O(1).
     */
    public Treap(final Function<E, A> converter, final BinaryOperator<A> aggregator) {
        this(null, converter, aggregator);
    }

    /**
     * Returns the number of elements in this list.
     * Time complexity: O(1)
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size(root);
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * Amortized time complexity: O(n).
     * Operates on collection from which was created -
     * no matter which operations was issued later on this collection
     * (in this sense this iterator is immutable).
     * <b>DO NOT</b> support {@link Iterator::remove} method,
     * but {@link #remove(int)} can be used on original collection.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<E> iterator() {
        return new TreapIterator(root);
    }

    /**
     * Appends the specified element to the end of this list (optional
     * operation).
     * Average time complexity: O(log n).
     *
     * @param e element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    @Override
    public boolean add(final E e) {
        root = merge(root, new Node(null, null, random.nextInt(), e));
        return true;
    }

    /**
     * Removes all of the elements from this list (optional operation).
     * The list will be empty after this call returns.
     * Time complexity: O(1).
     */
    @Override
    public void clear() {
        root = null;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public E get(final int index) {
        rangeCheck(index);
        return root.get(index);
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * Average time complexity: O(log n).
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public E set(final int index, final E element) {
        rangeCheck(index);
        final Pair<Node, E> result = root.set(index, element);
        root = result.getFirst();
        return result.getSecond();
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * Average time complexity: O(log n).
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    @Override
    public void add(final int index, final E element) {
        if (index == size()) add(element);
        rangeCheck(index);
        final Pair<Node, Node> s = split(root, index);
        root = merge(
                merge(
                        s.getLeft(),
                        new Node(null, null, random.nextInt(), element)
                ),
                s.getRight()
        );
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from
     * their indices).  Returns the element that was removed from the list.
     * Average time complexity: O(log n).
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    public E remove(final int index) {
        rangeCheck(index);
        final Pair<Node, E> result = root.remove(index);
        root = result.getFirst();
        return result.getSecond();
    }

    /**
     * Returns a view of the portion of this list between the specified
     * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive.  (If
     * <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, the returned list is
     * empty.)
     * <p>
     * In opposite to {@link List#subList(int, int)}
     * the returned list is whole new list and do not relate to original one:
     * any modification performed on original do not influence returned subList
     * and any modification performed on subList do not influence original one.
     * Average time complexity: O(log n).
     *
     * @param fromIndex low endpoint (inclusive) of the cutSubList
     * @param toIndex   high endpoint (exclusive) of the cutSubList
     * @return new list contained of the elements in specified range within this list
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *                                   (<tt>fromIndex &lt; 0 || toIndex &gt; size ||
     *                                   fromIndex &gt; toIndex</tt>)
     */
    public Treap<E, A> cutSubList(final int fromIndex, final int toIndex) {
        rangeCheck(fromIndex);
        rangeCheckTo(toIndex);
        return new Treap<>(split(split(root, toIndex).getLeft(), fromIndex).getRight(), converter, aggregator);
    }

    /**
     * Creates new list. Newly created and original lists are not bounded:
     * modification operations on one of it do not influence another one.
     * Time complexity: O(1).
     * <p>
     * Because inner parts are fully immutable and O(1) time complexity
     * this method can be used to saving state of the list, and for latter
     * changes reverting.
     *
     * @return new list
     */
    @Override
    public Treap<E, A> clone() {
        return new Treap<>(root, converter, aggregator);
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        if (c instanceof Treap &&
                Objects.equals(aggregator, ((Treap) c).aggregator) &&
                Objects.equals(converter, ((Treap) c).converter)) {
            root = merge(root, ((Treap) c).root);
            return c.isEmpty();
        } else {
            return super.addAll(c);
        }
    }

    public A aggregate() {
        if (aggregator == null) throw new IllegalStateException();
        return root.aggregate;
    }

    public A aggregate(final int fromIndex, final int toIndex) {
        if (aggregator == null) throw new IllegalStateException();
        rangeCheck(fromIndex);
        rangeCheckTo(toIndex);
        return root.aggregate(fromIndex, toIndex);
    }

    private void rangeCheck(final int index) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
    }

    private void rangeCheckTo(final int toIndex) {
        if (toIndex < 0 || toIndex > size()) throw new IndexOutOfBoundsException();
    }

    protected int size(final Node node) {
        return node == null ? 0 : node.size;
    }

    protected Node merge(final Node left, final Node right) {
        if (left == null) return right;
        if (right == null) return left;
        if (left.priority >= right.priority) {
            return new Node(left.left, merge(left.right, right), left.priority, left.data);
        } else {
            return new Node(merge(left, right.left), right.right, right.priority, right.data);
        }
    }

    //TODO what if toLeft == 0
    protected Pair<Node, Node> split(final Node node, final int toLeft) {
        if (node == null) {
            return Pair.emptyPair();
        }
        final int index = size(node.left) + 1;
        if (toLeft < index) {
            final Pair<Node, Node> result = split(node.left, toLeft);
            return Pair.of(
                    result.getLeft(),
                    new Node(result.getRight(), node.right, node.priority, node.data)
            );
        } else {
            final Pair<Node, Node> result = split(node.right, toLeft - index);
            return Pair.of(
                    new Node(node.left, result.getLeft(), node.priority, node.data),
                    result.getRight()
            );
        }
    }

    class Node {
        private final Node left, right;
        private final int priority, size;
        private final E data;
        private final A aggregate;

        Node(final Node left, final Node right, final int priority, final E data) {
            this.left = left;
            this.right = right;
            this.priority = priority;
            this.data = data;
            this.size = size(left) + 1 + size(right);
            if (aggregator != null) { //TODO maybe more sophisticated check
                final A a1 = left == null ? converter.apply(data) : aggregator.apply(left.aggregate, converter.apply(data));
                this.aggregate = right == null ? a1 : aggregator.apply(a1, right.aggregate);
            } else {
                this.aggregate = null;
            }
        }

        E get(final int index) {
            final int curIndex = size(left);
            if (index == curIndex) {
                return data;
            } else if (index < curIndex) {
                return left.get(index);
            } else {
                return right.get(index - curIndex - 1);
            }
        }

        Pair<Node, E> set(final int index, final E element) {
            final int curIndex = size(left);
            if (index == curIndex) {
                return Pair.of(new Node(left, right, priority, element), data);
            } else if (index < curIndex) {
                final Pair<Node, E> result = left.set(index, element);
                return Pair.of(new Node(result.getFirst(), right, priority, data), result.getSecond());
            } else {
                final Pair<Node, E> result = right.set(index - curIndex - 1, element);
                return Pair.of(new Node(left, result.getFirst(), priority, data), result.getSecond());
            }
        }

        Pair<Node, E> remove(final int index) {
            final int curIndex = size(left);
            if (index == curIndex) {
                return Pair.of(merge(left, right), data);
            } else if (index < curIndex) {
                final Pair<Node, E> result = left.remove(index);
                return Pair.of(new Node(result.getFirst(), right, priority, data), result.getSecond());
            } else {
                final Pair<Node, E> result = right.remove(index - curIndex - 1);
                return Pair.of(new Node(left, result.getFirst(), priority, data), result.getSecond());
            }
        }

        A aggregate(final int from, final int to) {
            if (from == 0 && to == size) return aggregate;

            final int curIndex = size(left);

            A result = null;
            boolean wasResult = false;

            if (from < curIndex) {
                result = left.aggregate(from, min(to, curIndex));
                wasResult = true;
            }
            if (from <= curIndex && curIndex < to) {
                final A converted = converter.apply(data);
                result = !wasResult ? converted : aggregator.apply(result, converted);
                wasResult = true;
            }
            final int nextIndex = curIndex + 1;
            if (nextIndex < to) {
                final A rightResult = right.aggregate(max(from - nextIndex, 0), to - nextIndex);
                result = !wasResult ? rightResult : aggregator.apply(result, rightResult);
            }

            return result;
        }

//    void toArray(final int offset, final E[] array) {
//        final int curIndex = size(left) + offset;
//        array[curIndex] = data;
//        if (left != null) left.toArray(offset, array);
//        if (right != null) right.toArray(curIndex + 1, array);
//    }
    }

    private class TreapIterator implements Iterator<E> {
        private final Stack<Node> stack = new Stack<>();
        private boolean needGoLeft = true;

        TreapIterator(final Node root) {
            stack.push(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (needGoLeft) goToLeft();
            final Node node = stack.pop();
            if (needGoLeft = node.right != null) {
                stack.push(node.right);
            }
            return node.data;
        }

        private void goToLeft() {
            if (stack.isEmpty()) throw new NoSuchElementException("Has no more elements");
            while (stack.peek().left != null) {
                stack.push(stack.peek().left);
            }
        }
    }
}
