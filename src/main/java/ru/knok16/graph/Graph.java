package ru.knok16.graph;

import java.util.*;
import java.util.stream.Stream;

public class Graph {
    private int vertexCount = 0;
    private int edgeCount = 0;
    private List<Vertex> vertexes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private List<Map<Integer, List<Edge>>> graph = new ArrayList<>();

    public class Vertex {
        private final int id = vertexCount++;

        private Vertex() {
        }

        public Graph getGraph() {
            return Graph.this;
        }

        public Edge addEdge(final Vertex to, final boolean oriented) {
            assertContext(to);
            final Edge result = new Edge(this, to, oriented);
            edges.add(result);
            graph.get(id).computeIfAbsent(to.id, key -> new ArrayList<>()).add(result);
            if (!oriented) {
                graph.get(to.id).computeIfAbsent(id, key -> new ArrayList<>()).add(result);
            }
            return result;
        }

        public Stream<Vertex> getVertexes() {
            return getEdges().map(edge -> edge.other(this)).distinct();
        }

        public Stream<Edge> getEdges() {
            return graph.get(id).values().stream().flatMap(Collection::stream);
        }

        public Stream<Edge> getEdges(final Vertex to) {
            assertContext(to);
            return graph.get(id).getOrDefault(to.id, Collections.emptyList()).stream();
        }

        public boolean checkEdge(final Vertex to) {
            return getEdges(to).findAny().isPresent();
        }

        @Override
        public String toString() {
            return "Vertex#" + id;
        }
    }

    public class Edge {
        private final int id = edgeCount++;
        private final Vertex v1;
        private final Vertex v2;
        private final boolean oriented;

        private Edge(final Vertex v1, final Vertex v2, final boolean oriented) {
            this.v1 = v1;
            this.v2 = v2;
            this.oriented = oriented;
        }

        public Vertex getV1() {
            return v1;
        }

        public Vertex getV2() {
            return v2;
        }

        public boolean isOriented() {
            return oriented;
        }

        public Vertex other(final Vertex vertex) {
            assertContext(vertex);
            if (v1.equals(vertex)) return v2;
            if (v2.equals(vertex)) return v1;
            throw new IllegalArgumentException("Edge doesn't adjacent to this vertex");
        }

        @Override
        public String toString() {
            return "Edge#" + id + "{" + v1 + (oriented ? "->" : "<->") + v2 + "}";
        }
    }

    public Vertex newVertex() {
        final Vertex result = new Vertex();
        vertexes.add(result);
        graph.add(new HashMap<>());
        return result;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public Stream<Vertex> getVertexes() {
        return vertexes.stream();
    }

    public Stream<Edge> getEdges() {
        return edges.stream();
    }

    public String toString(final Map<Vertex, String> vertexLabels, final Map<Edge, String> edgeLabels) {
        final StringBuilder sb = new StringBuilder();

        final Set<Vertex> used = new HashSet<>();

        sb.append('{');
        for (final Edge edge : edges) {
            sb.append('(')
                    .append(vertexLabels != null && vertexLabels.containsKey(edge.v1) ? vertexLabels.get(edge.v1) : edge.v1.id)
                    .append(edge.oriented ? "-" : "<-")
                    .append(edgeLabels != null && edgeLabels.containsKey(edge) ? edgeLabels.get(edge) : "")
                    .append("->")
                    .append(vertexLabels != null && vertexLabels.containsKey(edge.v2) ? vertexLabels.get(edge.v2) : edge.v2.id)
                    .append(')');

            used.add(edge.v1);
            used.add(edge.v2);
        }
        vertexes.stream().filter(vertex -> !used.contains(vertex)).forEach(
                vertex -> sb.append('(')
                        .append(vertexLabels != null && vertexLabels.containsKey(vertex) ? vertexLabels.get(vertex) : vertex.id)
                        .append(')')
        );
        sb.append('}');

        return sb.toString();
    }

    /*
    * ------- Private methods -------
    */
    private void assertContext(final Vertex vertex) {
        if (vertex.getGraph() != this) {
            throw new IllegalArgumentException("Vertex doesn't belong to this graph");
        }
    }
}
