package ru.knok16.combinatorics;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Permutations {
    //TODO assert k and n
    private int k, n;
    private long count;
    private boolean ordered, distinct, kSet, nSet, orderedSet, distinctSet, countComputed;

    public Permutations by(final int k) {
        checkIsSet(kSet, "k");
        assertPositive(k, "k");
        kSet = true;
        this.k = k;
        return this;
    }


    public Permutations outOf(final int n) {
        checkIsSet(nSet, "n");
        assertPositive(n, "n");
        nSet = true;
        this.n = n;
        return this;
    }

    public Permutations withRepetitions() {
        checkIsSet(distinctSet, "distinct");
        distinctSet = true;
        this.distinct = false;
        return this;
    }

    public Permutations distinct() {
        checkIsSet(distinctSet, "distinct");
        distinctSet = true;
        this.distinct = true;
        return this;
    }

    public Permutations ordered() {
        checkIsSet(orderedSet, "ordered");
        orderedSet = true;
        this.ordered = true;
        return this;
    }

    public Permutations unordered() {
        checkIsSet(orderedSet, "ordered");
        orderedSet = true;
        this.ordered = false;
        return this;
    }

    public long count() {
        if (countComputed) return count;
        checkIsComplete();
        if (distinct) {
            //TODO corner cases
            //if (n == 0) return 0;
            if (ordered) {
                count = 1;
                for (long i = 1, j = n; i <= k; i++, j--) {
                    count *= j;
                    //TODO is it enough?
                    //TODO improve error
                    if (count < 0) throw new IllegalArgumentException("Overflow");
                }
            } else {
                //TODO improve to work for bigger numbers
                count = 1;
                final int l = Math.min(k, n - k);
                final boolean[] d = new boolean[l];
                for (long i = 1, j = n; i <= l; i++, j--) {
                    count *= j;
                    for (int f = 0; f < l; f++) {
                        if (!d[f] && count % (f + 1) == 0) {
                            count /= f + 1;
                            d[f] = true;
                        }
                    }
                    //TODO is it enough?
                    //TODO improve error
                    if (count < 0) throw new IllegalArgumentException("Overflow");
                }
            }
        } else {
            if (ordered) {
                count = 1;
                for (int i = 1; i <= k; i++) {
                    count *= n;
                    //TODO is it enough?
                    //TODO improve error
                    if (count < 0) throw new IllegalArgumentException("Overflow");
                }
            } else {
                throw new IllegalArgumentException("unimplemented");
            }
        }
        countComputed = true;
        return count;
    }

    public Iterator<int[]> iterator() {
        checkIsComplete();
        if (distinct) {
            if (ordered) {
                return new DistinctOrderedIterator();
            } else {
                return new DistinctUnorderedIterator();
            }
        } else {
            if (ordered) {
                return new WithRepetitionsOrderedIterator();
            } else {
                throw new IllegalArgumentException("unimplemented");
            }
        }
    }

    public Stream<int[]> stream() {
        return StreamSupport.stream(Spliterators.spliterator(iterator(), count(), Spliterator.ORDERED), false);
    }

    private void checkIsSet(final boolean isSet, final String property) {
        if (isSet) throw new IllegalStateException("'" + property + "' property already set");
    }

    private void assertPositive(final int v, final String name) {
        if (v < 0) throw new IllegalArgumentException("'" + name + "' can not be negative");
    }

    private void checkIsComplete() {
        //TODO improve error message
        if (!kSet || !nSet || !orderedSet || !distinctSet) throw new IllegalStateException("Builder is not finished");
    }

    private class DistinctOrderedIterator implements Iterator<int[]> {
        private final Stack<Integer> result = new Stack<>();
        private final boolean[] used;
        private long current = 0;

        DistinctOrderedIterator() {
            this.used = new boolean[n];
            for (int i = 0; i < k; i++) {
                result.push(i);
                used[i] = true;
            }
        }

        @Override
        public boolean hasNext() {
            return current < count();
        }

        @Override
        public int[] next() {
            if (current > 0) genNext();
            current++;
            final int[] result = new int[k];
            for (int i = 0; i < k; i++)
                result[i] = this.result.get(i);
            return result;
        }

        private void genNext() {
            if (result.isEmpty()) throw new NoSuchElementException();
            int r = result.pop();
            used[r] = false;
            r++;
            while (r < n && used[r]) r++;
            if (r == n) {
                genNext();
                r = 0;
                while (r < n && used[r]) r++;
                //TODO assert that r < n
            }
            result.push(r);
            used[r] = true;
        }
    }

    private class DistinctUnorderedIterator implements Iterator<int[]> {
        private final Stack<Integer> result = new Stack<>();
        private long current = 0;

        DistinctUnorderedIterator() {
            for (int i = 0; i < k; i++) {
                result.push(i);
            }
        }

        @Override
        public boolean hasNext() {
            return current < count();
        }

        protected void genNext(final int upTo) {
            //TODO check
            if (result.isEmpty()) throw new NoSuchElementException();
            int r = result.pop() + 1;
            if (r == upTo) {
                genNext(upTo - 1);
                r = result.peek() + 1;
                //TODO assert that r < n
            }
            result.push(r);
        }

        @Override
        public int[] next() {
            if (current > 0) genNext(n);
            current++;
            final int[] result = new int[k];
            for (int i = 0; i < k; i++)
                result[i] = this.result.get(i);
            return result;
        }

    }

    private class WithRepetitionsOrderedIterator implements Iterator<int[]> {
        private final int[] result;
        private long current = 0;

        WithRepetitionsOrderedIterator() {
            this.result = new int[k];
        }

        @Override
        public boolean hasNext() {
            return current < count();
        }

        @Override
        public int[] next() {
            if (current > 0) genNext();
            current++;
            return Arrays.copyOf(result, k);
        }

        private void genNext() {
            int i = k - 1;
            while (i >= 0) {
                result[i]++;
                if (result[i] == n) {
                    result[i] = 0;
                    i--;
                } else {
                    return;
                }
            }
            if (i < 0) throw new NoSuchElementException();
        }
    }
}
