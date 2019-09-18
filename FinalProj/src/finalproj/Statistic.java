package finalproj;

import java.util.*;

public final class Statistic
{

    public static AlgoResult run(Graph g, Customer customer)
    {
        // Copy the graph to remove unavailable edges later if needed
        Graph gCopy = new Graph(g);

        // Variables for the statistic algorithm
        Graph newGraph;
        AlgoResult djkResult = null;
        int nParent, edgesWeWalkThrough = 0;
        LinkedHashMap<Integer, Integer> currRoute;
        List<Edge> unavailableEdges;
        boolean bIsAvailable = false;


        // Find the best route we can catch:
        // 1. run dijkstra on the graph without the unavailable edge
        // 2. check if we can catch this route
        // 3. if not, remove the unavailable edge and do the while again
        while (!bIsAvailable) {
            // Copy the graph and reshape it Statistically
            newGraph = new Graph(gCopy);
            reshapeGraphStatistically(newGraph);

            // Run dijkstra algorithm on the new graph and return the result
            djkResult = Dijkstra.run(newGraph, customer, false);

            currRoute = new LinkedHashMap<>();
            nParent = djkResult.getRoute().get(0);

            for (int i = 1; i < djkResult.getRoute().size(); i++) {
                currRoute.put(nParent, djkResult.getRoute().get(i));
                nParent = djkResult.getRoute().get(i);
            }

            unavailableEdges = gCopy.checkRoute(currRoute, customer.getBandWidth());

            if (unavailableEdges != null)
            {
                edgesWeWalkThrough += unavailableEdges.size();
            }
            else{
                bIsAvailable = true;
            }
        }

        // get the costs of the route
        HashMap<Integer, Integer> hmCosts = djkResult.getRouteCosts(gCopy);

        int routeCost = (int)hmCosts.keySet().toArray()[0];
        int totalCost = hmCosts.get(hmCosts.keySet().toArray()[0]);
        djkResult.setRouteCost(routeCost);
        djkResult.setTotalCost(edgesWeWalkThrough + routeCost + totalCost);

        return djkResult;
    }

    private static void reshapeGraphStatistically(Graph g)
    {
        List<Node> lstNodes = g.getNodes();
        List<Node> lstVisitedNodes = new ArrayList<Node>();
        List<Edge> lstRelevantEdges;
        List<Edge> lstEdgesToUpdate = new ArrayList<>();
        int nSumOfCost, nNumOfEdges, nAvgCost;

        // For each node in the graph
        for (Node n : lstNodes)
        {
            lstVisitedNodes.add(n);

            // Loop over his neighbors
            for (Node nNeighbor : n.getNeighbors())
            {
                // If this node isn't visited already
                if (!lstVisitedNodes.contains(nNeighbor))
                {
                    lstEdgesToUpdate.clear();
                    nNumOfEdges = 0;
                    nSumOfCost = 0;

                    // Get the edges between those nodes
                    lstRelevantEdges = g.getEdgesBetweenNodes(n, nNeighbor);

                    // Sum the costs of those edges
                    for (Edge eCurr : lstRelevantEdges)
                    {
                        nNumOfEdges++;
                        nSumOfCost += eCurr.getEdgeCost();
                        lstEdgesToUpdate.add(eCurr);
                    }

                    // Calculate the average cost and update the relevant edges
                    nAvgCost = (int)(nSumOfCost/nNumOfEdges);

                    for (Edge edgeToUpd : lstEdgesToUpdate)
                    {
                        edgeToUpd.setEdgeCost(nAvgCost);
                    }
                }
            }
        }
    }
}
