import java.io.IOException;
import java.util.*;

public class MinCut extends FlowNetwork {
    public static void main(String[] args) {
        try {
            Graph graph = new Graph("ressources/graph2.txt");
            Result maxFlowResult = new MinCut().fordFulkerson(graph);
            List<Edge> minCutEdges = new MinCut().findMinCut(graph, maxFlowResult.flows);
            System.out.println("Minimum Cut Edges:");
            for (Edge edge : minCutEdges) {
                System.out.printf("Arc (%d -> %d)\n", edge.from, edge.to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Edge> findMinCut(Graph graph, int[][] flows) {
        boolean[] visited = new boolean[graph.numNodes];
        int[][] residualCapacity = new int[graph.numNodes][graph.numNodes];

        // Calculate residual capacities
        for (Edge edge : graph.edges) {
            residualCapacity[edge.from][edge.to] = edge.capacity - flows[edge.from][edge.to];
            residualCapacity[edge.to][edge.from] = flows[edge.from][edge.to];
        }

        dfsOnResidualGraph(graph.sourceNode, residualCapacity, visited);

        List<Edge> minCutEdges = new ArrayList<>();
        for (Edge edge : graph.edges) {
            if (visited[edge.from] && !visited[edge.to]) {
                minCutEdges.add(edge);
            }
        }
        return minCutEdges;
    }

    private void dfsOnResidualGraph(int node, int[][] residualCapacity, boolean[] visited) {
        visited[node] = true;
        for (int i = 0; i < residualCapacity.length; i++) {
            if (!visited[i] && residualCapacity[node][i] > 0) {
                dfsOnResidualGraph(i, residualCapacity, visited);
            }
        }
    }
}