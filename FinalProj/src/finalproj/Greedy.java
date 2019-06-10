package finalproj;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public final class Greedy{

    public static AlgoResult run(Graph g, Customer customer)
    {
        int nParent;
        LinkedHashMap<Integer, Integer> currRoute;
        boolean bIsAvailable = false;
        AlgoResult djkResult = new AlgoResult(customer);
        List<Edge> unavailableEdges;

        // Create copy to not damage the original
        Graph graph = new Graph(g);

        // Find the best route we can catch:
        // 1. run dijkstra on the graph without the unavailable edge
        // 2. check if we can catch this route
        // 3. if not, remove the unavailable edge and do the while again
        while (!bIsAvailable)
        {
            djkResult = Dijkstra.run(graph, customer, false);

            currRoute = new LinkedHashMap<>();
            nParent = djkResult.getRoute().get(0);

            for (int i = 1; i < djkResult.getRoute().size(); i++)
            {
                currRoute.put(nParent, djkResult.getRoute().get(i));
                nParent = djkResult.getRoute().get(i);
            }

            unavailableEdges = graph.checkRoute(currRoute, customer.getBandWidth());

            if (unavailableEdges != null) {
                unavailableEdges.stream().forEach(x -> graph.removeEdge(x));
            }
            else {
                bIsAvailable = true;
            }
        }

        return djkResult;
    }
}
