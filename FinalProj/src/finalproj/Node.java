/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

/**
 *
 * @author Liron Levi
 */
public class Node
{
    //private static int idCounter = 0;
    
    private int id;
    private List<Edge> lstEdges;
    
    public Node(int id)
    {
        this.lstEdges = new ArrayList<Edge>();
        this.id = id;
    }
    
    public void addEdge(Edge newEdge) 
    {
        if(!isEdgeExist(newEdge))
        {
            this.lstEdges.add(newEdge);
        }
    }

    public void removeEdge(Edge delEdge)
    {
        if(isEdgeExist(delEdge))
        {
            this.lstEdges.remove(delEdge);
        }
    }

    public List<Node> getNeighbors()
    {
        List<Node> lst = new ArrayList<Node>();
        Node nOther;

        for (Edge e : lstEdges)
        {
            nOther = e.getOtherNode(this.id);

            if (!lst.contains(nOther))
            {
                lst.add(nOther);
            }
        }

        return lst;
    }

    public int getId()
    {
        return id;
    }
    
    public Edge getEdge(int i)
    {
        return this.lstEdges.get(i);
    }
    
    public int getEdgeCount()
    {
        return this.lstEdges.size();
    }
    
    public List<Edge> getEdges()
    {
        return this.lstEdges;
    }
    
    public Edge getEdgeById(int id)
    {
        for(int i = 0; i < this.lstEdges.size(); i++)
        {
            if(this.lstEdges.get(i).getId() == id)
            {
                return this.lstEdges.get(i);
            }
        }
        return null;
    }
    
    public boolean isEdgeExist(Edge edge)
    {
        boolean isFound = false;
        
        for(int i = 0; i < this.lstEdges.size() && isFound == false; i++)
        {
            if(((this.lstEdges.get(i).getNode1().getId() == edge.getNode1().getId()  && 
                 this.lstEdges.get(i).getNode2().getId() == edge.getNode2().getId()) || 
                (this.lstEdges.get(i).getNode1().getId() == edge.getNode2().getId()  && 
                 this.lstEdges.get(i).getNode2().getId() == edge.getNode1().getId())) &&
                 this.lstEdges.get(i).getEdgeCost() == edge.getEdgeCost())
            {
                isFound = true;
            }
        }
        
        return isFound;
    }
}
