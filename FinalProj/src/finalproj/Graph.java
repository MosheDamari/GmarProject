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
public class Graph
{
    private int nDiscoverCost;
    private int nGeneralCost; // num of edges * discovery cost + sum of each edge UseCost 
    private int nSourceId;
    private int nDestId;
    private List<Node> lstNodes;
    private List<Edge> lstEdges;

    public Graph(int nDiscoverCost, List<EdgeParameters> edgeParameters)
    {
        this.lstEdges = new ArrayList<Edge>();
        this.lstNodes = new ArrayList<Node>();
        this.nDiscoverCost = nDiscoverCost;
        this.nGeneralCost = 0; // will calc after graph creation: 
        
        for(int i = 0; i < edgeParameters.size(); i++)
        {
            Node n1 = new Node(edgeParameters.get(i).getN1());
            Node n2 = new Node(edgeParameters.get(i).getN2());
            addNode(n1);
            addNode(n2);
            Edge e1 = new Edge(edgeParameters.get(i).getNumOfSlots(), edgeParameters.get(i).getEdgeCost(), n1, n2);
            addEdge(e1);
        }
    }

    public void addEdge(Edge newEdge)
    {
        if(!isEdgeExist(newEdge))
        {
            this.lstEdges.add(newEdge);
        }
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
                this.lstEdges.get(i).setTotalSlots(this.lstEdges.get(i).getTotalSlots() + edge.getTotalSlots());
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
    
    public void printGraph()
    {
        for(int i = 0; i < lstEdges.size(); i++)
        {
            System.out.println(lstEdges.get(i).getNode1().getId() + " " + lstEdges.get(i).getNode2().getId() + " " + lstEdges.get(i).getTotalSlots());
        }
    }
    
    public Node getNode(int i)
    {
        return lstNodes.get(i);
    }
}
