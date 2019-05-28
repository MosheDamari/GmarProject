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
    // enterance: the function gets node and costumer band width
    // output: the function returns the cheapest available edge
    public static Edge getCheapestEdge(Node node, int costumerBandWidth, @Nullable Edge eLastUsed)
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
                if((eLastUsed != null && (eLastUsed.isEquals(copyNodeEdges.get(i)))) || (!(copyNodeEdges.get(i).getSlotCurrentUsage() + costumerBandWidth <= copyNodeEdges.get(i).getTotalSlots())))
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
  
    public static Edge getFirstEdge(Node node, int costumerBandWidth, @Nullable Edge eLastUsed)
    {
        List<Edge> copyNodeEdges = CreateCopyList(node.getEdges());
        boolean isDone = false;
        int i;

        while (isDone == false)
        {
            isDone = true;
            for (i = 0; i < copyNodeEdges.size(); i++)
            {
                if((eLastUsed != null && (eLastUsed.isEquals(copyNodeEdges.get(i)))) || (!(copyNodeEdges.get(i).getSlotCurrentUsage() + costumerBandWidth <= copyNodeEdges.get(i).getTotalSlots())))
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

    public static Node findMinimumNode(HashMap<Node, Integer> map, Set<Integer> settled)
    {
        Node minKey = null;
        int minValue = Integer.MAX_VALUE;

        for(Node key : map.keySet()) {
            if (!settled.contains(key.getId())) {
                int value = map.get(key);
                if (value < minValue) {
                    minValue = value;
                    minKey = key;
                }
            }
        }

        return minKey;
    }
}
