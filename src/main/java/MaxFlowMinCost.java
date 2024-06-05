import java.io.*;
import java.util.*;

public class MaxFlowMinCost {

    public static void main(String[] args) throws IOException {
        Graph graph = new Graph("ressources/graph.txt");
        int[] result = FlowNetwork.minCostMaxFlow(graph);
        System.out.println("Max Flow: " + result[0]);
        System.out.println("Min Cost: " + result[1]);
        graph.printFlows();
    }
}
