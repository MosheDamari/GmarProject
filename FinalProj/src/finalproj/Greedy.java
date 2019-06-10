package finalproj;

public final class Greedy{

    public static AlgoResult run(Graph graph, Customer customer)
    {
        boolean bIsAvailable = false;
        AlgoResult djkResult = new AlgoResult(customer);
        Edge unavailableEdge;

        // Find the best route we can catch:
        // 1. run dijkstra on the graph without the unavailable edge
        // 2. check if we can catch this route
        // 3. if not, remove the unavailable edge and do the while again
        while (!bIsAvailable)
        {
            djkResult = Dijkstra.run(graph, customer);

            unavailableEdge = graph.checkRoute(djkResult.getRoute(), customer.getBandWidth());

            if (unavailableEdge != null) {
                graph.removeEdge(unavailableEdge);
            }
            else {
                bIsAvailable = true;
            }
        }

        return djkResult;
    }
}
