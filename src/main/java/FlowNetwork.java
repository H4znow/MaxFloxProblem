import java.util.*;

public abstract class FlowNetwork {
    protected static final int INF = Integer.MAX_VALUE;

    protected static class Result {
        int maxFlow;
        int[][] flows;
        int minCost;

        Result(int maxFlow, int[][] flows) {
            this.maxFlow = maxFlow;
            this.flows = flows;
        }
        Result(int maxFlow, int minCost, int[][] flows) {
            this.maxFlow = maxFlow;
            this.minCost = minCost;
            this.flows = flows;
        }
    }

    protected Result fordFulkerson(Graph graph) {
        int[][] capacity = new int[graph.numNodes][graph.numNodes];
        int[][] flow = new int[graph.numNodes][graph.numNodes];

        for (Edge edge : graph.edges) {
            capacity[edge.from][edge.to] = edge.capacity;
        }

        int[] parent = new int[graph.numNodes];
        int maxFlow = 0;

        while (bfs(capacity, flow, graph.sourceNode, graph.sinkNode, parent)) {
            int pathFlow = INF;
            for (int v = graph.sinkNode; v != graph.sourceNode; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v] - flow[u][v]);
            }

            for (int v = graph.sinkNode; v != graph.sourceNode; v = parent[v]) {
                int u = parent[v];
                flow[u][v] += pathFlow;
                flow[v][u] -= pathFlow;
            }

            maxFlow += pathFlow;
        }

        return new Result(maxFlow, flow);
    }

    protected boolean bfs(int[][] capacity, int[][] flow, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[capacity.length];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < capacity.length; v++) {
                if (!visited[v] && capacity[u][v] - flow[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return visited[sink];
    }

    protected void dfs(Graph graph, int[][] flows, int node, boolean[] visited) {
        visited[node] = true;
        for (int i = 0; i < graph.numNodes; i++) {
            if (!visited[i] && flows[node][i] > 0) {
                dfs(graph, flows, i, visited);
            }
        }
    }
}