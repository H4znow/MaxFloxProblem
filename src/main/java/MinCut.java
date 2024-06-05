import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MinCut extends FlowNetwork {
    public void run(String path) {
        try {
            Graph graph = new Graph(path);
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

    @Override
    public String name() {
        return "Min Cut";
    }

}