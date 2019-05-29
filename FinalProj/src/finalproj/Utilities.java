/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import com.sun.istack.internal.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Liron Levi
 */
public final class Utilities
{
    // enterance: the function gets node and customer band width
    // output: the function returns the cheapest available edge
    public static Edge getCheapestEdge(Node node, int customerBandWidth, @Nullable Edge eLastUsed)
    {
        int minCost;
        int minCostIndex;
        List<Edge> copyNodeEdges = CreateCopyList(node.getEdges());
        boolean isDone = false;
        int i;

        while (isDone == false)
        {
            isDone = true;
            for (i = 0; i < copyNodeEdges.size(); i++)
            {
                if((eLastUsed != null && (eLastUsed.isEquals(copyNodeEdges.get(i)))) || (!(copyNodeEdges.get(i).getSlotCurrentUsage() + customerBandWidth <= copyNodeEdges.get(i).getTotalSlots())))
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
        
        if(!copyNodeEdges.isEmpty())
        {
            Edge e = node.getEdgeById(copyNodeEdges.get(minCostIndex).getId());
            return e;
        }
        
        return null;
    }
    
    public static List<Edge> CreateCopyList(List<Edge> list)
    {
        List<Edge> newList = new ArrayList<Edge>();
        
        for(int i = 0; i < list.size(); i++)
        {
            newList.add(list.get(i));
        }
        
        return newList;
    }
  
    public static Edge getFirstEdge(Node node, int customerBandWidth, @Nullable Edge eLastUsed)
    {
        List<Edge> copyNodeEdges = CreateCopyList(node.getEdges());
        boolean isDone = false;
        int i;

        while (isDone == false)
        {
            isDone = true;
            for (i = 0; i < copyNodeEdges.size(); i++)
            {
                if((eLastUsed != null && (eLastUsed.isEquals(copyNodeEdges.get(i)))) || (!(copyNodeEdges.get(i).getSlotCurrentUsage() + customerBandWidth <= copyNodeEdges.get(i).getTotalSlots())))
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
        
        if(!copyNodeEdges.isEmpty())
        {
            Edge e = node.getEdgeById(copyNodeEdges.get(0).getId());
            return e;
        }
        
        return null;
    }

    public static Graph getAvailableCapGraph(Graph oldG, int nCustomerBW)
    {
        List<EdgeParameters> lstEP = new ArrayList<EdgeParameters>();

        // Go over all edges in the graph
        for (Edge e : oldG.getEdgeParametersList())
        {
            // Check if the current edge has enough
            // capacity (bandwith) to transfer the customer
            if (e.getTotalSlots() - e.getSlotCurrentUsage() >= nCustomerBW)
            {
                lstEP.add(new EdgeParameters(e.getNode1().getId(),
                                             e.getNode2().getId(),
                                             e.getEdgeCost(),
                                             e.getTotalSlots()));
            }
        }

        return new Graph(oldG.getDiscoverCost(), lstEP);
    }

    public static void assignCustomer(Customer c, AlgoResult a)
    {

    }
}
