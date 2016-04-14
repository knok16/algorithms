package ru.knok16.graph;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;

//TODO check loop edges
public class GraphTest {
    private final Graph graph;
    private final Graph.Vertex[] vertexes;
    private final Graph.Edge[] edges;

    public GraphTest() {
        graph = new Graph();
        final int n = 5;
        vertexes = new Graph.Vertex[n];
        for (int i = 0; i < n; i++) {
            vertexes[i] = graph.newVertex();
        }
        edges = new Graph.Edge[]{
                vertexes[0].addEdge(vertexes[1], false),
                vertexes[0].addEdge(vertexes[1], true),
                vertexes[0].addEdge(vertexes[1], true),
                vertexes[1].addEdge(vertexes[0], true),

                vertexes[0].addEdge(vertexes[2], true),

                vertexes[1].addEdge(vertexes[3], false),

                vertexes[1].addEdge(vertexes[1], true),
        };
    }

    @Test
    public void addEdge() {
        assertEquals(vertexes[0], edges[0].getV1());
        assertEquals(vertexes[1], edges[0].getV2());
        assertFalse(edges[0].isOriented());

        assertEquals(vertexes[0], edges[4].getV1());
        assertEquals(vertexes[2], edges[4].getV2());
        assertTrue(edges[4].isOriented());

        assertEquals(vertexes[1], edges[5].getV1());
        assertEquals(vertexes[3], edges[5].getV2());
        assertFalse(edges[5].isOriented());
    }

    @Test
    public void getVertexCount() {
        assertEquals(vertexes.length, graph.getVertexCount());
    }

    @Test
    public void getEdgeCount() {
        assertEquals(edges.length, graph.getEdgeCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void outOfContext() {
        vertexes[0].getEdges(new Graph().newVertex());
    }

    @Test
    public void getVertexes() {
        final Set<Graph.Vertex> actual = graph.getVertexes().collect(toSet());
        assertEquals(vertexes.length, actual.size());
        assertEquals(Arrays.stream(vertexes).collect(toSet()), actual);
    }

    @Test
    public void getAllEdges() {
        final Set<Graph.Edge> actual = graph.getEdges().collect(toSet());
        assertEquals(edges.length, actual.size());
        assertEquals(Arrays.stream(edges).collect(toSet()), actual);
    }

    @Test
    public void getAdjacentVertexes() {
        assertEquals(
                Arrays.stream(new Graph.Vertex[]{vertexes[1], vertexes[2]}).collect(toSet()),
                vertexes[0].getVertexes().collect(toSet())
        );
        assertEquals(2, vertexes[0].getVertexes().collect(toList()).size());

        assertTrue(vertexes[2].getVertexes().collect(toList()).isEmpty());
        assertTrue(vertexes[4].getVertexes().collect(toList()).isEmpty());
    }

    @Test
    public void getEdgesFromVertex() {
        assertEquals(
                Arrays.stream(new Graph.Edge[]{edges[0], edges[1], edges[2], edges[4]}).collect(toSet()),
                vertexes[0].getEdges().collect(toSet())
        );

        assertTrue(vertexes[2].getEdges().collect(toList()).isEmpty());
        assertTrue(vertexes[4].getEdges().collect(toList()).isEmpty());
    }

    @Test
    public void getEdgesFromOneVertexToAnother() {
        assertEquals(
                Arrays.stream(new Graph.Edge[]{edges[0], edges[1], edges[2]}).collect(toSet()),
                vertexes[0].getEdges(vertexes[1]).collect(toSet())
        );
    }

    @Test
    public void checkEdge() {
        final boolean[][] a = {
                {false, true, true, false, false},
                {true, true, false, true, false},
                {false, false, false, false, false},
                {false, true, false, false, false},
                {false, false, false, false, false}
        };

        for (int from = 0; from < vertexes.length; from++)
            for (int to = 0; to < vertexes.length; to++)
                assertEquals(a[from][to], vertexes[from].checkEdge(vertexes[to]));
    }

    @Test
    @Ignore
    public void dfs() {
        new DepthFirstSearch.Builder()
                .setProcessVertex(new DepthFirstSearch.VertexVisited())
                .setBefore((v, e) -> System.out.println(v))
                .build()
                .run(vertexes[0]);
    }

    @Test
    @Ignore
    public void dfs2() {
        new DepthFirstSearch.Builder()
                .setProcessVertex(new DepthFirstSearch.EdgeVisited())
                .setBefore((v, e) -> System.out.println(v))
                .build()
                .run(vertexes[0]);
    }

    @Test
    public void topologicalSort() {
        final Graph graph = new Graph();
        final Graph.Vertex v1 = graph.newVertex();
        final Graph.Vertex v2 = graph.newVertex();
        final Graph.Vertex v3 = graph.newVertex();
        final Graph.Vertex v4 = graph.newVertex();
        final Graph.Vertex v5 = graph.newVertex();

        v1.addEdge(v2, true);
        v2.addEdge(v3, true);
        v1.addEdge(v3, true);
        v1.addEdge(v5, true);

        assertEquals(Arrays.asList(v4, v1, v5, v2, v3), GraphAlgorithms.topologicalSort(graph));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cyclicGraph() {
        final Graph graph = new Graph();
        final Graph.Vertex v1 = graph.newVertex();
        final Graph.Vertex v2 = graph.newVertex();
        final Graph.Vertex v3 = graph.newVertex();
        final Graph.Vertex v4 = graph.newVertex();
        final Graph.Vertex v5 = graph.newVertex();

        v1.addEdge(v2, true);
        v2.addEdge(v3, true);
        v1.addEdge(v5, true);
        v3.addEdge(v1, true);

        GraphAlgorithms.topologicalSort(graph);
    }

    @Test(expected = IllegalArgumentException.class)
    public void graphWithNonOrientedEdges() {
        final Graph graph = new Graph();
        final Graph.Vertex v1 = graph.newVertex();
        final Graph.Vertex v2 = graph.newVertex();

        v1.addEdge(v2, false);

        GraphAlgorithms.topologicalSort(graph);
    }

    @Test
    public void testToString() {
        assertEquals("{(*<-->1)(*-->1)(*-->1)(1-/->*)(*-->2)(1<-->3)(1-->1)(4)}", graph.toString(
                Collections.singletonMap(vertexes[0], "*"),
                Collections.singletonMap(edges[3], "/")
        ));
    }
}
