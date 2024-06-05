import java.io.*;
import java.util.*;

public class MaxFlowMinCost {

    static class Edge {
        int from, to, capacity, cost, flow;
        Edge reverse;

        Edge(int from, int to, int capacity, int cost) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.cost = cost;
            this.flow = 0;
            this.reverse = null;
        }
    }

    static class Graph {
        int numNodes, numArcs, source, sink;
        List<Edge>[] adjList;
        int[] dist, parent;
        Edge[] parentEdge;
        boolean[] inQueue;

        @SuppressWarnings("unchecked")
        Graph(int numNodes) {
            this.numNodes = numNodes;
            adjList = new ArrayList[numNodes];
            for (int i = 0; i < numNodes; i++) {
                adjList[i] = new ArrayList<>();
            }
            dist = new int[numNodes];
            parent = new int[numNodes];
            parentEdge = new Edge[numNodes];
            inQueue = new boolean[numNodes];
        }

        void addEdge(int from, int to, int capacity, int cost) {
            Edge forward = new Edge(from, to, capacity, cost);
            Edge backward = new Edge(to, from, 0, -cost);
            forward.reverse = backward;
            backward.reverse = forward;
            adjList[from].add(forward);
            adjList[to].add(backward);
        }

        boolean spfa() {
            Arrays.fill(dist, Integer.MAX_VALUE);
            Arrays.fill(inQueue, false);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(source);
            dist[source] = 0;
            inQueue[source] = true;

            while (!queue.isEmpty()) {
                int u = queue.poll();
                inQueue[u] = false;
                for (Edge edge : adjList[u]) {
                    if (edge.capacity > edge.flow && dist[edge.to] > dist[u] + edge.cost) {
                        dist[edge.to] = dist[u] + edge.cost;
                        parent[edge.to] = u;
                        parentEdge[edge.to] = edge;
                        if (!inQueue[edge.to]) {
                            queue.add(edge.to);
                            inQueue[edge.to] = true;
                        }
                    }
                }
            }
            return dist[sink] != Integer.MAX_VALUE;
        }

        int[] minCostMaxFlow() {
            int flow = 0, cost = 0;
            while (spfa()) {
                int pathFlow = Integer.MAX_VALUE;
                for (int v = sink; v != source; v = parent[v]) {
                    pathFlow = Math.min(pathFlow, parentEdge[v].capacity - parentEdge[v].flow);
                }
                for (int v = sink; v != source; v = parent[v]) {
                    parentEdge[v].flow += pathFlow;
                    parentEdge[v].reverse.flow -= pathFlow;
                    cost += pathFlow * parentEdge[v].cost;
                }
                flow += pathFlow;
            }
            return new int[]{flow, cost};
        }

        void printFlows() {
            for (List<Edge> edges : adjList) {
                for (Edge edge : edges) {
                    if (edge.capacity > 0) {
                        System.out.println("Edge (" + edge.from + " -> " + edge.to + ") Flow: " + edge.flow);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("ressources/graph.txt"));
        String[] firstLine = br.readLine().split(" ");
        int numNodes = Integer.parseInt(firstLine[0]);
        int numArcs = Integer.parseInt(firstLine[1]);
        int source = Integer.parseInt(firstLine[2]);
        int sink = Integer.parseInt(firstLine[3]);

        Graph graph = new Graph(numNodes);
        graph.source = source;
        graph.sink = sink;

        for (int i = 0; i < numArcs; i++) {
            String[] line = br.readLine().split(" ");
            int from = Integer.parseInt(line[0]);
            int to = Integer.parseInt(line[1]);
            int capacity = Integer.parseInt(line[2]);
            int cost = Integer.parseInt(line[3]);
            graph.addEdge(from, to, capacity, cost);
        }

        br.close();

        int[] result = graph.minCostMaxFlow();
        System.out.println("Max Flow: " + result[0]);
        System.out.println("Min Cost: " + result[1]);
        graph.printFlows();
    }
}
