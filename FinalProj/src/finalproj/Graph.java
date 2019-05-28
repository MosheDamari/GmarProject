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
            n1 = addNode(n1);
            n2 = addNode(n2);
            Edge e1 = new Edge(edgeParameters.get(i).getNumOfSlots(), edgeParameters.get(i).getEdgeCost(), n1, n2);
            addEdge(e1);
        }
    }

    public void addEdge(Edge newEdge)
    {
        if(!isEdgeExist(newEdge))
        {
            this.lstEdges.add(newEdge);
            getNodeById(newEdge.getNode1().getId()).addEdge(newEdge);
            getNodeById(newEdge.getNode2().getId()).addEdge(newEdge);
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
    
    public Node addNode(Node node)
    {
        boolean isFound = false;
        int i;
        
        for(i = 0; i < this.lstNodes.size() && isFound == false; i++)
        {
            if(this.lstNodes.get(i).getId() == node.getId())
            {
                isFound = true;
                break;
            }
        }
        
        if(!isFound)
        {
            this.lstNodes.add(node);
            return node;
        }
        else
        {
            return this.lstNodes.get(i);
        }
    }  
    
    public void printGraph()
    {
        for(int i = 0; i < lstEdges.size(); i++)
        {
            System.out.println(lstEdges.get(i).getNode1().getId() + " " + lstEdges.get(i).getNode2().getId() + " " + lstEdges.get(i).getTotalSlots());
        }
    }
    
    public Node getNodeById(int id)
    {
        for(int i = 0; i < this.lstNodes.size(); i++)
        {
            if(this.lstNodes.get(i).getId() == id) 
            {
                return this.lstNodes.get(i);
            }
        }
        return null;
    } 
    
    public void catchSlots(Edge e, int nBandwidth)
    {
        for(int i=0;i<this.lstEdges.size(); i++)
        {
            if(this.lstEdges.get(i).isEquals(e))
            {
                this.lstEdges.get(i).setSlotCurrentUsage(this.lstEdges.get(i).getSlotCurrentUsage() + nBandwidth);
            }
        }
    }
    
    // If path not found return to the last configuration
    public void rollbackRoute(List<EdgeParameters> list, int nBandwidth)
    {
        for(int i = 0; i < list.size(); i++)
        {
            for(int j =0; j < this.lstEdges.size(); j++)
            {
                if((( this.lstEdges.get(j).getNode1().getId() == list.get(i).getN1() && 
                      this.lstEdges.get(j).getNode2().getId() == list.get(i).getN2()) ||
                    ( this.lstEdges.get(j).getNode1().getId() == list.get(i).getN2() &&
                      this.lstEdges.get(j).getNode2().getId() == list.get(i).getN1())) &&
                      this.lstEdges.get(j).getEdgeCost() == list.get(i).getEdgeCost())
                {
                    this.lstEdges.get(j).setSlotCurrentUsage(this.lstEdges.get(j).getSlotCurrentUsage() - nBandwidth);
                    break;
                }
            }
        }
    }

    public int getNumberOfNodes()
    {
        return (this.lstNodes.size());
    }
}

