/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

//import sun.istack.internal.Nullable;
import java.util.*;

/**
 *
 * @author Liron Levi
 */
public final class Utilities
{
    public static Graph getAvailableCapGraph(Graph oldG, int nCustomerBW)
    {
        // Copy to avoid changing the original reference
        List<Edge> lstE = new ArrayList<>(oldG.getEdges());

        // Remove blocked paths
        lstE.removeIf(x -> x.getSlot() == 1);

        return new Graph(oldG.getDiscoverCost(), new ArrayList<Node>(oldG.getNodes()), lstE);
    }

    public static int getCheapestPathBetweenNodes(Graph g, Node n1, Node n2, int custBW)
    {
        int nSum = 0;
        Comparator<Edge> edgeComparator = Comparator.comparing(Edge::getEdgeCost);

        List<Edge> lstE = g.getEdgesBetweenNodes(n1, n2);
        lstE.sort(edgeComparator);

        if (custBW <= lstE.size())
        {
            for (int i = 0; i < custBW; i++) {
                nSum += lstE.get(i).getEdgeCost();
            }
        }
        else{
            nSum = -1;
        }

        return nSum;
    }

    public static boolean checkNodeExist(List<Node> lstN, int nId)
    {
        return (lstN.stream().filter(o -> o.getId() == nId).findFirst().isPresent());
    }

    // Return the number of connections between nodes in the graph
    // If between two nodes there is more then one edge it will be counted as one connection
    public static int getNumOfConnections(Graph g)
    {
        List<Node> lstNodes = g.getNodes();
        List<Node> lstVisitedNodes = new ArrayList<>();
        int nCounter = 0;

        for (Node n : lstNodes)
        {
            lstVisitedNodes.add(n);

            for (Node nNeighbor : n.getNeighbors())
            {
                if (!lstVisitedNodes.contains(nNeighbor))
                {
                    nCounter++;
                }
            }
        }

        return nCounter;
    }

    // Get an array of random numbers with specific average
    // Parameters: nExpectedPercentage - the average we want to achieve
    //             nNumOfConns         - the number of connections in our graph (edges)
    // Return:
    //             lstPercentages      - List of percentages between 0 to 1
    public static ArrayList<Double> getRandomPercentages(int nExpectedPercentage, int nNumOfConns)
    {
        ArrayList<Double> lstPercentages = new ArrayList<>();
        Random rnd = new Random();
        int nNumToRand, nRandomNumber, nMaxRand, nMinRand;

        // Find our borders to satisfy our expected average
        nMaxRand = Math.min(100, nExpectedPercentage * 2);
        nMinRand = Math.max(0, nExpectedPercentage - (100-nExpectedPercentage));

        // Random half of the connections
        nNumToRand = nNumOfConns / 2;

        // If its an odd number
        if (nNumOfConns % 2 != 0)
        {
            double ohad = (double)nExpectedPercentage / 100;
            lstPercentages.add(ohad);
        }

        // loop to random
        for(int i = 0; i < nNumToRand; i++)
        {
            nRandomNumber = rnd.nextInt(nMaxRand - nMinRand) + nMinRand;
            lstPercentages.add((double)nRandomNumber / 100);

            // for each random number add his inverse number
            lstPercentages.add((double)(nExpectedPercentage-(nRandomNumber - nExpectedPercentage)) / 100);
        }

        return lstPercentages;
    }
}


