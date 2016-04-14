package ru.knok16.graph;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class DepthFirstSearch {
    private static final BiConsumer<Graph.Vertex, Graph.Edge> EMPTY_CONSUMER = (v, e) -> {
    };
    private static final BiFunction<Graph.Vertex, Graph.Edge, Boolean> EMPTY_FUNCTION = (v, e) -> true;

    private final BiFunction<Graph.Vertex, Graph.Edge, Boolean> processVertex;
    private final BiConsumer<Graph.Vertex, Graph.Edge> before, after, beforeChild, afterChild;

    private DepthFirstSearch(final BiFunction<Graph.Vertex, Graph.Edge, Boolean> processVertex,
                             final BiConsumer<Graph.Vertex, Graph.Edge> before,
                             final BiConsumer<Graph.Vertex, Graph.Edge> after,
                             final BiConsumer<Graph.Vertex, Graph.Edge> beforeChild,
                             final BiConsumer<Graph.Vertex, Graph.Edge> afterChild) {
        this.processVertex = processVertex;
        this.before = before;
        this.after = after;
        this.beforeChild = beforeChild;
        this.afterChild = afterChild;
    }

    public void run(final Graph.Vertex vertex) {
        run(vertex, null);
    }

    private void run(final Graph.Vertex vertex, final Graph.Edge edge) {
        if (processVertex.apply(vertex, edge)) {
            before.accept(vertex, edge);
            vertex.getEdges().forEach(e -> {
                final Graph.Vertex next = e.other(vertex);
                beforeChild.accept(next, e);
                run(next, e);
                afterChild.accept(next, e);
            });
            after.accept(vertex, edge);
        }
    }

    public static class VertexVisited implements BiFunction<Graph.Vertex, Graph.Edge, Boolean> {
        private final Set<Graph.Vertex> visited = new HashSet<>();

        @Override
        public Boolean apply(final Graph.Vertex vertex, final Graph.Edge edge) {
            return visited.add(vertex);
        }
    }

    public static class EdgeVisited implements BiFunction<Graph.Vertex, Graph.Edge, Boolean> {
        private final Set<Graph.Edge> visited = new HashSet<>();

        @Override
        public Boolean apply(final Graph.Vertex vertex, final Graph.Edge edge) {
            return visited.add(edge);
        }
    }

    public static class Builder {
        private BiFunction<Graph.Vertex, Graph.Edge, Boolean> processVertex = EMPTY_FUNCTION;
        private BiConsumer<Graph.Vertex, Graph.Edge> before = EMPTY_CONSUMER, after = EMPTY_CONSUMER,
                beforeChild = EMPTY_CONSUMER, afterChild = EMPTY_CONSUMER;

        public Builder setProcessVertex(final BiFunction<Graph.Vertex, Graph.Edge, Boolean> processVertex) {
            this.processVertex = processVertex;
            return this;
        }

        public Builder setBefore(final BiConsumer<Graph.Vertex, Graph.Edge> before) {
            this.before = before;
            return this;
        }

        public Builder setAfter(final BiConsumer<Graph.Vertex, Graph.Edge> after) {
            this.after = after;
            return this;
        }

        public Builder setBeforeChild(final BiConsumer<Graph.Vertex, Graph.Edge> beforeChild) {
            this.beforeChild = beforeChild;
            return this;
        }

        public Builder setAfterChild(final BiConsumer<Graph.Vertex, Graph.Edge> afterChild) {
            this.afterChild = afterChild;
            return this;
        }

        public DepthFirstSearch build() {
            return new DepthFirstSearch(processVertex, before, after, beforeChild, afterChild);
        }
    }
}
