/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;

/**
 *
 *
 */
public class Graph
{
    private int nDiscoverCost;
    private List<Node> lstNodes;
    private List<Edge> lstEdges;

    public Graph(int nDiscoverCost, List<Node> nodes, List<Edge> edges)
    {
        this.nDiscoverCost = nDiscoverCost;
        this.lstNodes = nodes;
        this.lstEdges = edges;
    }

    public Graph(Graph g)
    {
        this.nDiscoverCost = g.nDiscoverCost;
        this.lstNodes = new ArrayList<>(g.lstNodes);
        this.lstEdges = new ArrayList<>(g.lstEdges);
    }

    public int getDiscoverCost(){ return this.nDiscoverCost; }

    public List<Node> getNodes() { return this.lstNodes; }

    public List<Edge> getEdges() { return this.lstEdges; }
    
    public void printGraph()
    {
        for(int i = 0; i < lstEdges.size(); i++)
        {
            System.out.println(lstEdges.get(i).getNode1().getId() + " " + lstEdges.get(i).getNode2().getId() + " " + lstEdges.get(i).getSlot());
        }
    }
    
    public Node getNodeById(int id)
    {
        return this.lstNodes.stream().filter(o -> o.getId() == id).findFirst().orElse(null);
    }

    public void removeEdge(Edge delEdge)
    {
        this.lstEdges.remove(delEdge);
        this.getNodeById(delEdge.getNode1().getId()).removeEdge(delEdge);
        this.getNodeById(delEdge.getNode2().getId()).removeEdge(delEdge);
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

    public List<Edge> checkRoute(HashMap<Integer, Integer> hRoute, int nCustBandWidth)
    {
        List<Edge> lstE;

        for (int nCurrNode : hRoute.keySet())
        {
            lstE = this.getEdgesBetweenNodes(this.getNodeById(nCurrNode), this.getNodeById(hRoute.get(nCurrNode)));

            if (lstE.size() - lstE.stream().mapToInt(x -> x.getSlot()).sum() < nCustBandWidth)
            {
                return lstE;
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

