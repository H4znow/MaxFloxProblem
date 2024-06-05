import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Graph {
    int numNodes, numArcs, sourceNode, sinkNode;
    List<Edge> edges = new ArrayList<>();
    List<Edge> allEdges = new ArrayList<>(); // Includes original and reverse edges
    List<Edge>[] adjList;
    int[] dist, parent;
    Edge[] parentEdge;
    boolean[] inQueue;

    @SuppressWarnings("unchecked")
    Graph(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String[] firstLine = br.readLine().split(" ");
        numNodes = Integer.parseInt(firstLine[0]);
        numArcs = Integer.parseInt(firstLine[1]);
        sourceNode = Integer.parseInt(firstLine[2]);
        sinkNode = Integer.parseInt(firstLine[3]);

        adjList = new ArrayList[numNodes];
        for (int i = 0; i < numNodes; i++) {
            adjList[i] = new ArrayList<>();
        }

        dist = new int[numNodes];
        parent = new int[numNodes];
        parentEdge = new Edge[numNodes];
        inQueue = new boolean[numNodes];

        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            int from = Integer.parseInt(parts[0]);
            int to = Integer.parseInt(parts[1]);
            int capacity = Integer.parseInt(parts[2]);
            int cost = Integer.parseInt(parts[3]);
            addEdge(from, to, capacity, cost);
        }
        br.close();
    }

    void addEdge(int from, int to, int capacity, int cost) {
        Edge forward = new Edge(from, to, capacity, cost);
        Edge backward = new Edge(to, from, 0, -cost);
        forward.reverse = backward;
        backward.reverse = forward;
        adjList[from].add(forward);
        adjList[to].add(backward);
        edges.add(forward);
        allEdges.add(forward);
        allEdges.add(backward);
    }

    void printFlows() {
        for (int i = 0; i < numNodes; i++) {
            for (Edge edge : adjList[i]) {
                if (edge.capacity > 0 && edge.flow > 0) {
                    System.out.println("Edge (" + edge.from + " -> " + edge.to + ") Flow: " + edge.flow);
                    if(Math.abs(edge.reverse.flow) != Math.abs(edge.flow)){
                        Edge edgeReversed = edge.reverse;
                        System.out.println("Edge (" + edgeReversed.from + " -> " + edgeReversed.to + ") Flow: " + edgeReversed.flow);
                    }
                }
            }
        }
    }
}