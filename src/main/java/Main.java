public class Main {

    private static final String path = "ressources/graph.txt";

    public static void main(String[] args) {
        FlowNetwork[] problems = {new MaxFlow(), new MinCut(), new MaxFlowMinCost()};
        for (FlowNetwork p : problems) {
            System.out.println("*".repeat(15) + p.name() + " problem " + "*".repeat(15));
            p.run(path);
        }
    }

}
