package ru.knok16.math;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Context<T extends Number> {
    private final Space<T> space;
    public final Constant ZERO;
    public final Constant ONE;
    private final Map<String, Node> variableValues = new HashMap<>();
    private final String format = "%.5f";

    protected Context(final Space<T> space) {
        this.space = space;
        this.ZERO = new Constant(space.zero());
        this.ONE = new Constant(space.one());
    }

    public Variable define(final String name, final T value) {
        return define(name, constant(value));
    }

    public Variable define(final String name, final Node value) {
        variableValues.put(name, value);
        return var(name);
    }

    public void clear(final Variable variable) {
        clear(variable.name);
    }

    public void clear(final String name) {
        variableValues.remove(name);
    }

    abstract class Node {
        public abstract Node calculate(Map<String, T> at);

        Node calculate() {
            return calculate(Collections.emptyMap());
        }

        public abstract Node derivative(String by);
    }

    Constant constant(final T value) {
        return space.compare(value, space.zero()) == 0 ? ZERO : new Constant(value);
    }

    class Constant extends Node {
        private final T value;

        private Constant(final T value) {
            this.value = value;
        }

        @Override
        public Node calculate(final Map<String, T> at) {
            return this;
        }

        @Override
        public Node derivative(final String by) {
            return ZERO;
        }

        @Override
        public String toString() {
            return String.format(Locale.ROOT, format, value);
        }

        public T getValue() {
            return value;
        }
    }

    Variable var(final String name) {
        return new Variable(name);
    }

    class Variable extends Node {
        private final String name;

        private Variable(final String name) {
            this.name = name;
        }

        @Override
        public Node calculate(final Map<String, T> at) {
            if (at.containsKey(name)) return constant(at.get(name));
            if (variableValues.containsKey(name)) return variableValues.get(name).calculate(at);
            return this;
        }

        @Override
        public Node derivative(final String by) {
            if (name.equals(by)) return ONE;
            if (variableValues.containsKey(name)) return variableValues.get(name).derivative(by);
            return ZERO;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    Node negate(final Node node) {
        if (node instanceof Context.Negate) {
            return ((Negate) node).node;
        }
        if (node instanceof Context.Constant) {
            return new Constant(space.negate(((Constant) node).value));
        }
        return new Negate(node);
    }

    class Negate extends Node {
        private final Node node;

        private Negate(final Node node) {
            this.node = node;
        }

        @Override
        public Node calculate(final Map<String, T> at) {
            return negate(node.calculate(at));
        }

        @Override
        public Node derivative(final String by) {
            return negate(node.derivative(by));
        }

        @Override
        public String toString() {
            return "-" + node;
        }
    }

    abstract class Operator extends Node {
        protected final Node left, right;
        protected final String symbol;

        protected Operator(final Node left, final Node right, final String symbol) {
            this.left = left;
            this.right = right;
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder();
            if (left instanceof Context.Constant || left instanceof Context.Variable) {
                result.append(left);
            } else {
                result.append('(').append(left).append(')');
            }
            result.append(' ').append(symbol).append(' ');
            if (right instanceof Context.Constant || right instanceof Context.Variable) {
                result.append(right);
            } else {
                result.append('(').append(right).append(')');
            }
            return result.toString();
        }
    }

    public Node add(final Node left, final Node right) {
        if (left instanceof Context.Constant && right instanceof Context.Constant) {
            return constant(space.add(((Constant) left).value, ((Constant) right).value));
        }
        if (left instanceof Context.Constant && space.isZero(((Constant) left).value)) {
            return right;
        }
        if (right instanceof Context.Constant && space.isZero(((Constant) right).value)) {
            return left;
        }
        return new Add(left, right);
    }

    class Add extends Operator {
        private Add(final Node left, final Node right) {
            super(left, right, "+");
        }

        @Override
        public Node calculate(final Map<String, T> at) {
            return add(left.calculate(at), right.calculate(at));
        }

        @Override
        public Node derivative(final String by) {
            return add(left.derivative(by), right.derivative(by));
        }

    }

    public Node add(final Node node1, final Node node2, final Node node3, final Node... nodes) {
        Node result = add(add(node1, node2), node3);
        for (final Node node : nodes) {
            result = add(result, node);
        }
        return result;
    }

    public Node subtract(final Node left, final Node right) {
        if (left instanceof Context.Constant && right instanceof Context.Constant) {
            return constant(space.subtract(((Constant) left).value, ((Constant) right).value));
        }
        if (left instanceof Context.Constant && space.isZero(((Constant) left).value)) {
            return negate(right);
        }
        if (right instanceof Context.Constant && space.isZero(((Constant) right).value)) {
            return left;
        }
        return new Subtract(left, right);
    }

    class Subtract extends Operator {
        private Subtract(final Node left, final Node right) {
            super(left, right, "-");
        }

        @Override
        public Node calculate(final Map<String, T> at) {
            return subtract(left.calculate(at), right.calculate(at));
        }

        @Override
        public Node derivative(final String by) {
            return subtract(left.derivative(by), right.derivative(by));
        }
    }

    public Node multiply(final Node left, final Node right) {
        if (left instanceof Context.Constant && right instanceof Context.Constant) {
            return constant(space.multiply(((Constant) left).value, ((Constant) right).value));
        }
        if (left instanceof Context.Constant && space.isZero(((Constant) left).value) ||
                right instanceof Context.Constant && space.isZero(((Constant) right).value)) {
            return ZERO;
        }
        if (left instanceof Context.Constant && space.isOne(((Constant) left).value)) {
            return right;
        }
        if (right instanceof Context.Constant && space.isOne(((Constant) right).value)) {
            return left;
        }
        return new Multiply(left, right);
    }

    class Multiply extends Operator {
        private Multiply(final Node left, final Node right) {
            super(left, right, "*");
        }

        @Override
        public Node calculate(final Map<String, T> at) {
            return multiply(left.calculate(at), right.calculate(at));
        }

        @Override
        public Node derivative(final String by) {
            return add(multiply(left.derivative(by), right), multiply(left, right.derivative(by)));
        }
    }

    public Node multiply(final Node node1, final Node node2, final Node node3, final Node... nodes) {
        Node result = multiply(multiply(node1, node2), node3);
        for (final Node node : nodes) {
            result = multiply(result, node);
        }
        return result;
    }

    public Node divide(final Node left, final Node right) {
        if (right instanceof Context.Constant && space.isZero(((Constant) right).value)) {
            throw new ArithmeticException("division by 0");
        }
        if (left instanceof Context.Constant && right instanceof Context.Constant) {
            return constant(space.divide(((Constant) left).value, ((Constant) right).value));
        }
        if (left instanceof Context.Constant && space.isZero(((Constant) left).value)) {
            return ZERO;
        }
        if (right instanceof Context.Constant && space.isZero(((Constant) right).value)) {
            return left;
        }
        return new Divide(left, right);
    }

    class Divide extends Operator {
        private Divide(final Node left, final Node right) {
            super(left, right, "/");
        }

        @Override
        public Node calculate(final Map<String, T> at) {
            return divide(left.calculate(at), right.calculate(at));
        }

        @Override
        public Node derivative(final String by) {
            return divide(subtract(multiply(left.derivative(by), right), multiply(left, right.derivative(by))), multiply(right, right));
        }
    }

    public Node power(final Node base, final Node power) {
        //TODO handle 0 ^ 0
        if (base instanceof Context.Constant && power instanceof Context.Constant) {
            return constant(space.power(((Constant) base).value, ((Constant) power).value));
        }
        if (base instanceof Context.Constant && space.isZero(((Constant) base).value)) {
            return ZERO;
        }
        if (base instanceof Context.Constant && space.isOne(((Constant) base).value)) {
            return ONE;
        }
        if (power instanceof Context.Constant && space.isZero(((Constant) power).value)) {
            return ONE;
        }
        if (power instanceof Context.Constant && space.isOne(((Constant) power).value)) {
            return base;
        }
        return new Power(base, power);
    }

    class Power extends Operator {
        protected Power(final Node left, final Node right) {
            super(left, right, "^");
        }

        @Override
        public Node calculate(final Map<String, T> at) {
            return power(left.calculate(at), right.calculate(at));
        }

        @Override
        public Node derivative(final String by) {
            //TODO optimize if left == const OR right == const
            if (right instanceof Context.Constant) {
                return multiply(right, power(left, constant(space.subtract(((Constant) right).getValue(), space.one()))));
            }
            //use Leibnitz formula for general case
            //(UV)' = U^V * (VU'/U + V'ln(U))
            return multiply(calculate(), add(divide(multiply(right, left.derivative(by)), left), multiply(right.derivative(by), ln(left))));
        }
    }

    Node exp(final Node node) {
        return power(constant(space.e()), node);
    }

    abstract class Function extends Node {
    }

    Node log(final T base, final Node node) {
        if (node instanceof Context.Constant) {
            return constant(space.log(base, ((Constant) node).value));
        }
        return new Log(base, node);
    }

    class Log extends Function {
        private final T base;
        private final Node node;

        private Log(final T base, final Node node) {
            this.base = base;
            this.node = node;
        }

        @Override
        public Node calculate(final Map<String, T> at) {
            return log(base, node.calculate(at));
        }

        @Override
        public Node derivative(final String by) {
            return multiply(subtract(constant(space.divide(space.one(), space.ln(base))), node), node.derivative(by));
        }

        @Override
        public String toString() {
            return String.format("log(" + format + ", %s)", base, node.toString());
        }
    }

    Node ln(final Node node) {
        if (node instanceof Context.Constant) {
            return constant(space.ln(((Constant) node).value));
        }
        return new Ln(node);
    }

    class Ln extends Function {
        private final Node node;

        private Ln(final Node node) {
            this.node = node;
        }

        @Override
        public Node calculate(final Map<String, T> at) {
            return ln(node.calculate(at));
        }

        @Override
        public Node derivative(final String by) {
            return multiply(divide(ONE, node), node.derivative(by));
        }

        @Override
        public String toString() {
            return "ln(" + node.toString() + ")";
        }
    }
}
