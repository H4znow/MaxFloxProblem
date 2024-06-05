import java.io.IOException;

public class MaxFlowMinCost extends FlowNetwork {

    public void run(String path) {
        try {
            Graph graph = new Graph(path);
            int[] result = minCostMaxFlow(graph);
            System.out.println("Max Flow: " + result[0]);
            System.out.println("Min Cost: " + result[1]);
            graph.printFlows();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String name() {
        return "Max Flow - Min Cost";
    }
}
