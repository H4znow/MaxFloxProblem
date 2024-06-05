import java.io.IOException;

public class MaxFlow extends FlowNetwork {
    public static void main(String[] args) {
        try {
            Graph graph = new Graph("ressources/graph.txt");
            Result maxFlowResult = new MaxFlow().fordFulkerson(graph);
            System.out.println("Max Flow: " + maxFlowResult.maxFlow);
            System.out.println("Flow Values Traversing Each Arc:");
            for (Edge edge : graph.edges) {
                int flow = maxFlowResult.flows[edge.from][edge.to];
                System.out.printf("Arc (%d -> %d): Flow = %d\n", edge.from, edge.to, flow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}