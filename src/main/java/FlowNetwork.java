import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public abstract class FlowNetwork {
    protected static final int INF = Integer.MAX_VALUE;

    protected static boolean spfa(Graph graph) {
        Arrays.fill(graph.dist, Integer.MAX_VALUE);
        Arrays.fill(graph.inQueue, false);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(graph.sourceNode);
        graph.dist[graph.sourceNode] = 0;
        graph.inQueue[graph.sourceNode] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            graph.inQueue[u] = false;
            for (Edge edge : graph.adjList[u]) {
                if (edge.capacity > edge.flow && graph.dist[edge.to] > graph.dist[u] + edge.cost) {
                    graph.dist[edge.to] = graph.dist[u] + edge.cost;
                    graph.parent[edge.to] = u;
                    graph.parentEdge[edge.to] = edge;
                    if (!graph.inQueue[edge.to]) {
                        queue.add(edge.to);
                        graph.inQueue[edge.to] = true;
                    }
                }
            }
        }
        return graph.dist[graph.sinkNode] != Integer.MAX_VALUE;
    }

    protected static int[] minCostMaxFlow(Graph graph) {
        int flow = 0, cost = 0;

        while (spfa(graph)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = graph.sinkNode; v != graph.sourceNode; v = graph.parent[v]) {
                pathFlow = Math.min(pathFlow, graph.parentEdge[v].capacity - graph.parentEdge[v].flow);
            }
            for (int v = graph.sinkNode; v != graph.sourceNode; v = graph.parent[v]) {
                graph.parentEdge[v].flow += pathFlow;
                graph.parentEdge[v].reverse.flow -= pathFlow;
                cost += pathFlow * graph.parentEdge[v].cost;
            }
            flow += pathFlow;
        }
        return new int[]{flow, cost};
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

    protected static class Result {
        int maxFlow;
        int[][] flows;

        Result(int maxFlow, int[][] flows) {
            this.maxFlow = maxFlow;
            this.flows = flows;
        }
    }
}