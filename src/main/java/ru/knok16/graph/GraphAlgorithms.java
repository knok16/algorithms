package ru.knok16.graph;

import java.util.*;

public class GraphAlgorithms {
    public static List<Graph.Vertex> topologicalSort(final Graph graph) {
        if (graph.getEdges().filter(edge -> !edge.isOriented()).findAny().isPresent()) {
            throw new IllegalArgumentException("Graph should contain only oriented edges");
        }

        final List<Graph.Vertex> result = new ArrayList<>();

        final Set<Graph.Vertex> black = new HashSet<>();
        final Set<Graph.Vertex> grey = new HashSet<>();

        final DepthFirstSearch dfs = new DepthFirstSearch.Builder()
                .setProcessVertex((v, e) -> {
                    if (grey.contains(v)) throw new IllegalArgumentException("Graph should be acyclic");
                    return !black.contains(v);
                })
                .setBefore((v, e) -> grey.add(v))
                .setAfter((v, e) -> {
                    grey.remove(v);
                    black.add(v);
                    result.add(v);
                })
                .build();

        graph.getVertexes().forEach(dfs::run);
        Collections.reverse(result);
        return result;
    }
}
