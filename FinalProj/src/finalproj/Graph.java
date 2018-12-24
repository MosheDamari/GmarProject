/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.util.List;

/**
 *
 * @author Liron Levi
 */
public class Graph
{
    private int nDiscoverCost;
    private int nGeneralCost; // num of edges * discovery cost + sum of each edge UseCost 
    private int nSourceId;
    private int nDestId;
    private List<Node> lstNodes;
    private List<Edge> lstEdges;

    public Graph(int nDiscoverCost, int nSourceId, int nDestId)
    {
        this.nDiscoverCost = nDiscoverCost;
        this.nSourceId = nSourceId;
        this.nDestId = nDestId;
        this.nGeneralCost = 0;
    }

    public void addEdge(Edge newEdge)
    {
        if(isEdgeExist(newEdge))
        {
            this.lstEdges.add(newEdge);
        }
        
        addNode(newEdge.getNode1());
        addNode(newEdge.getNode2());
    }
    
    public boolean isEdgeExist(Edge edge)
    {
        boolean isFound = false;
        
        for(int i = 0; i < this.lstEdges.size() && isFound == false; i++)
        {
            if(this.lstEdges.get(i).getId() == edge.getId())
            {
                isFound = true;
            }
        }
        
        return isFound;
    }
    
    public void addNode(Node node)
    {
        boolean isFound = false;
        
        for(int i = 0; i < this.lstNodes.size() && isFound == false; i++)
        {
            if(this.lstNodes.get(i).getId() == node.getId())
            {
                isFound = true;
            }
        }
        
        if(!isFound)
        {
            this.lstNodes.add(node);
        }
    }  
}
