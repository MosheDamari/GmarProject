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
}


