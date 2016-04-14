package ru.knok16.graph;

public class MaxFlow {
    public static int[][] pushRelabel(final int n,
                                      final int[][] capacity,
                                      final int source,
                                      final int target) {
        final int[][] flow = new int[n][n];
        final int[] excess = new int[n];
        final int[] height = new int[n];

        height[source] = n;

        for (int i = 0; i < n; i++) {
            flow[i][source] = -(flow[source][i] = excess[i] = capacity[source][i]);
        }

        while (true) {
            int i;
            for (i = 0; i < n; i++)
                if (i != source && i != target && excess[i] > 0) break;
            if (i == n)
                break;

            int j;
            for (j = 0; j < n; j++)
                if (flow[i][j] < capacity[i][j] && height[i] == height[j] + 1) break;

            if (j < n)
                push(i, j, capacity, flow, excess);
            else
                relabel(i, n, capacity, flow, height);
        }

        return flow;
    }

    private static void push(final int from,
                             final int to,
                             final int[][] capacity,
                             final int[][] flow,
                             final int[] excess) {
        final int d = Math.min(excess[from], capacity[from][to] - flow[from][to]);
        flow[from][to] += d;
        flow[to][from] -= d;
        excess[from] -= d;
        excess[to] += d;
    }

    private static void relabel(final int v,
                                final int n,
                                final int[][] capacity,
                                final int[][] flow,
                                final int[] height) {
        int t = -1;
        for (int i = 0; i < n; i++)
            if (flow[v][i] < capacity[v][i] &&
                    (t == -1 || height[i] < height[t]))
                t = i;

        assert t >= 0;

        height[v] = height[t] + 1;
    }
}
