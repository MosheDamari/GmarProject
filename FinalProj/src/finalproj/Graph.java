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

    // Check if we can route this path
    public List<Edge> checkRoute(HashMap<Integer, Integer> hRoute, int nCustBandWidth)
    {
        List<Edge> lstE;
        List<Edge> lstVisitedEdges = new ArrayList<>();

        Comparator<Edge> edgeComparator = Comparator.comparing(Edge::getEdgeCost);

        // for each source in our path
        for (int nCurrNode : hRoute.keySet())
        {
            // get the edges between the source and the destiny nodes and sort them by cost
            lstE = this.getEdgesBetweenNodes(this.getNodeById(nCurrNode), this.getNodeById(hRoute.get(nCurrNode)));
            lstE.sort(edgeComparator);

            // check if we can navigate through each edge that our dijkstra found
            // if not, one of the edges are catched, than return this unavailable edge
            for (int i = 0; i < nCustBandWidth; i++)
            {
                lstVisitedEdges.add(lstE.get(i));

                if (lstE.get(i).getSlot() == 1)
                {
                    this.removeEdge(lstE.get(i));
                    return lstVisitedEdges;
                }
            }
        }

        return null;
    }

    public List<Edge> navigateThroughPath(HashMap<Integer, Integer> hRoute, int nCustBandWidth)
    {
        List<Edge> lstE;
        List<Edge> lstVisitedEdges = new ArrayList<>();

        Comparator<Edge> edgeComparator = Comparator.comparing(Edge::getEdgeCost);

        // for each source in our path
        for (int nCurrNode : hRoute.keySet())
        {
            // get the edges between the source and the destiny nodes and sort them by cost
            lstE = this.getEdgesBetweenNodes(this.getNodeById(nCurrNode), this.getNodeById(hRoute.get(nCurrNode)));
            lstE.sort(edgeComparator);

            // check if we can navigate through each edge that our dijkstra found
            // if not, one of the edges are catched, than return this unavailable edge
            for (int i = 0; i < nCustBandWidth; i++)
            {
                lstVisitedEdges.add(lstE.get(i));
            }
        }

        return lstVisitedEdges;
    }

    // Catch random slots in the graph by percentage
    // parameter percentage is int between 0-1
    public void catchRandomSlots(double percentage)
    {
        List<Node> lstVisitedNodes = new ArrayList<Node>();
        List<Edge> lstRelevantEdges;
        Comparator<Edge> edgeComparator = Comparator.comparing(Edge::getEdgeCost);
        int nNumOfConns = Utilities.getNumOfConnections(this);
        int perc = (int)(percentage * 100);
        List<Double> lstPercentages = Utilities.getRandomPercentages(perc, nNumOfConns);
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
                    numOfEdgesToCatch = (int)(lstRelevantEdges.size() * lstPercentages.remove(0));

                    for (int i = 0; i < numOfEdgesToCatch; i++)
                    {
                        lstRelevantEdges.get(i).setSlot(1);
                    }
                }
            }
        }
    }
    
}

