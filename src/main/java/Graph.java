import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Graph {
    int numNodes, numArcs, sourceNode, sinkNode;
    List<Edge> edges = new ArrayList<>();
    List<Edge> allEdges = new ArrayList<>(); // Includes original and reverse edges

    Graph(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String[] firstLine = br.readLine().split(" ");
        numNodes = Integer.parseInt(firstLine[0]);
        numArcs = Integer.parseInt(firstLine[1]);
        sourceNode = Integer.parseInt(firstLine[2]);
        sinkNode = Integer.parseInt(firstLine[3]);

        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            int from = Integer.parseInt(parts[0]);
            int to = Integer.parseInt(parts[1]);
            int capacity = Integer.parseInt(parts[2]);
            int cost = Integer.parseInt(parts[3]);
            Edge edge = new Edge(from, to, capacity, cost);
            edges.add(edge);
            allEdges.add(edge);
            allEdges.add(edge.reverse());
        }
        br.close();
    }
}