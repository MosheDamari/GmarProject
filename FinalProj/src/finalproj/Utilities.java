/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liron Levi
 */
public final class Utilities
{
    // enterance: the function gets node and costumer band width
    // output: the function returns the cheapest available edge
    public static Edge getCheapestEdge(Node node, int costumerBandWidth)
    {
        int minCost;
        int minCostIndex;
        List<Edge> copyNodeEdges = new ArrayList<>(node.getEdges());
        boolean isDone = false;
        int i;

        while (isDone == false)
        {
            isDone = true;
            for (i = 0; i < copyNodeEdges.size(); i++)
            {
                if(!(copyNodeEdges.get(i).getSlotCurrentUsage() + costumerBandWidth <= copyNodeEdges.get(i).getTotalSlots()))
                {
                    isDone = false;
                    break;
                }
            }
            
            if(isDone == false)
            {
                copyNodeEdges.remove(i);
            }
        }
        
        minCost = copyNodeEdges.get(0).getEdgeCost();
        minCostIndex = 0;
        
        for(i = 1; i < copyNodeEdges.size(); i++)
        {
            if(copyNodeEdges.get(i).getEdgeCost() < minCost)
            {
                minCost = copyNodeEdges.get(i).getEdgeCost();
                minCostIndex = i;
            }
        }
        
        Edge e = node.getEdgeById(copyNodeEdges.get(minCostIndex).getId());
        
        return e;
    }
}
