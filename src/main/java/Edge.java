class Edge {
    int from, to, capacity, cost, flow;
    Edge reverse;

    Edge(int from, int to, int capacity, int cost) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.cost = cost;
        this.flow = 0; // Initialize flow
        this.reverse = null;
    }

    // Method to create a reverse edge
    Edge reverse() {
        return new Edge(to, from, 0, -cost); // Note the reverse edge has zero initial capacity and negative cost
    }
}