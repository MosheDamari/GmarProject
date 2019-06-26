package finalproj;

import java.util.*;

public final class Statistic
{

    public static AlgoResult run(Graph g, Customer customer)
    {
        // Copy the graph and reshape it Statistically
        Graph newGraph = new Graph(g);
        reshapeGraphStatistically(newGraph);

        // Run greedy algorithm on the new graph and return the result
        return Greedy.run(newGraph, customer);
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
