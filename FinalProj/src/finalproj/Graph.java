/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 *
 * @author Liron Levi
 */
public class Graph
{
    private int nDiscoverCost;
    private List<Node> lstNodes;
    private List<Edge> lstEdges;

    public Graph(int nDiscoverCost, List<EdgeParameters> edgeParameters)
    {
        this.lstEdges = new ArrayList<Edge>();
        this.lstNodes = new ArrayList<Node>();
        this.nDiscoverCost = nDiscoverCost;
        
        for(int i = 0; i < edgeParameters.size(); i++)
        {
            Node n1 = new Node(edgeParameters.get(i).getN1());
            Node n2 = new Node(edgeParameters.get(i).getN2());
            n1 = addNode(n1);
            n2 = addNode(n2);

            Edge e1;

            for (int j = 0; j < edgeParameters.get(i).getNumOfSlots(); j++) {
                e1 = new Edge(1, edgeParameters.get(i).getEdgeCost(), n1, n2);
                addEdge(e1);
            }
        }
    }

    public void addEdge(Edge newEdge)
    {
        //if(!isEdgeExist(newEdge))
        //{
            this.lstEdges.add(newEdge);
            getNodeById(newEdge.getNode1().getId()).addEdge(newEdge);
            getNodeById(newEdge.getNode2().getId()).addEdge(newEdge);
        //}
    }

    public void removeEdge(Edge delEdge)
    {
        this.lstEdges.remove(delEdge);
        getNodeById(delEdge.getNode1().getId()).removeEdge(delEdge);
        getNodeById(delEdge.getNode2().getId()).removeEdge(delEdge);
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

    public int getDiscoverCost(){ return this.nDiscoverCost; }

    public List<Edge> getEdgeParametersList() { return this.lstEdges; }
    
    public List<Node> getNodesList() { return this.lstNodes; }
    
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
    
    public int getNumberOfEdges()
    {
        return (this.lstEdges.size());
    }

    public List<Edge> getEdgesBetweenNodes(Node n1, Node n2)
    {
        List<Edge> lst = new ArrayList<Edge>();

        for (Edge e : this.lstEdges)
        {
            if ((e.getNode1() == n1 &&
                 e.getNode2() == n2) ||
                (e.getNode1() == n2 &&
                 e.getNode2() == n1))
            {
                lst.add(e);
            }
        }

        return lst;
    }

    public void updateSlots(List<EdgeParameters> lstEdgs, int custBandWidth)
    {
    	for (EdgeParameters ep : lstEdgs)
    	{
    		for (Edge e : this.getEdgeParametersList())
    		{
        		if (Utilities.compareEdgeToEdgeParam(ep, e))
        		{
        			e.setSlotCurrentUsage(e.getSlotCurrentUsage() + custBandWidth);
        		}
    		}
    	}
    }

    public Edge checkRoute(List<EdgeParameters> lstEdgs, int custBandWidth)
    {
        for (EdgeParameters ep : lstEdgs)
        {
            for (Edge e : this.getEdgeParametersList())
            {
                if (Utilities.compareEdgeToEdgeParam(ep, e))
                {
                    if ((e.getTotalSlots() - e.getSlotCurrentUsage()) < custBandWidth)
                    {
                        return e;
                    }
                }
            }
        }

        return null;
    }

    // Catch random slots in the graph by percentage
    // parameter percentage is int between 0-1
    public void CatchRandomSlots(int percentage)
    {
        List<Node> lstVisitedNodes = new ArrayList<Node>();
        List<Edge> lstRelevantEdges;
        Comparator<Edge> edgeComparator = Comparator.comparing(Edge::getEdgeCost);
        int numOfEdgesToCatch;

        // For each node in the graph
        for (Node n : this.lstNodes)
        {
            lstVisitedNodes.add(n);

            // Loop over his neighbors
            for (Node nNeighbor : n.getNeighbors())
            {
                // If this node isn't visited already
                if (!lstVisitedNodes.contains(nNeighbor))
                {
                    // Get the edges between those nodes, sort them by cost and catch some of them
                    // by the percentage parameter
                    lstRelevantEdges = this.getEdgesBetweenNodes(n, nNeighbor);
                    lstRelevantEdges.sort(edgeComparator);
                    numOfEdgesToCatch = (int)(lstRelevantEdges.size() * percentage);

                    for (int i = 0; i < numOfEdgesToCatch; i++)
                    {
                        this.removeEdge(lstRelevantEdges.get(i));
                    }
                }
            }
        }
    }
    
}

