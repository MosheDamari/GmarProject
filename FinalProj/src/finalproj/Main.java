/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.util.*;

/**
 *
 * @author Liron Levi
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        AlgoResult aResult = null;
        Costumer c01 = new Costumer(1, 1, 9, 30, 1);
        EdgeParameters ep01 = new EdgeParameters(1, 2, 4, 20);
        // TODO: Ask about double edges
        // EdgeParameters ep02 = new EdgeParameters(1, 2, 6, 30);
        EdgeParameters ep03 = new EdgeParameters(1, 3, 5, 20);
        EdgeParameters ep04 = new EdgeParameters(1, 4, 2, 40);
        EdgeParameters ep05 = new EdgeParameters(2, 5, 7, 40);
        EdgeParameters ep06 = new EdgeParameters(4, 5, 3, 50);
        EdgeParameters ep07 = new EdgeParameters(4, 6, 9, 20);
        EdgeParameters ep08 = new EdgeParameters(4, 7, 5, 70);
        // EdgeParameters ep09 = new EdgeParameters(5, 7, 10, 10);
        EdgeParameters ep10 = new EdgeParameters(5, 7, 3, 30);
        EdgeParameters ep11 = new EdgeParameters(6, 7, 8, 90);
        EdgeParameters ep12 = new EdgeParameters(3, 8, 6, 30);
        EdgeParameters ep13 = new EdgeParameters(6, 9, 2, 30);
        EdgeParameters ep14 = new EdgeParameters(7, 9, 10, 130);
        EdgeParameters ep15 = new EdgeParameters(8, 9, 5, 40);
        EdgeParameters ep16 = new EdgeParameters(5, 8, 9, 90);
        EdgeParameters ep17 = new EdgeParameters(2, 8, 6, 40);
        EdgeParameters ep18 = new EdgeParameters(3, 4, 4, 40);
        List<EdgeParameters> lst = new ArrayList<EdgeParameters>();
        lst.add(ep01);
        // lst.add(ep02);
        lst.add(ep03);
        lst.add(ep04);
        lst.add(ep05);
        lst.add(ep06);
        lst.add(ep07);
        lst.add(ep08);
        // lst.add(ep09);
        lst.add(ep10);
        lst.add(ep11);
        lst.add(ep12);
        lst.add(ep13);
        lst.add(ep14);
        lst.add(ep15);
        lst.add(ep16);
        lst.add(ep17);
        lst.add(ep18);
        
        Graph g = new Graph(1, lst);
        g.printGraph();
        System.out.println("\n**************************************************************************\n");
        //aResult = Greedy(g, c01);
        //aResult = Naive(g, c01);
        aResult = Dijkstra(g, c01);
        
        aResult.print();
    }
    
    public static AlgoResult Greedy(Graph graph, Costumer costumer)
    {
        AlgoResult aResult = new AlgoResult(costumer);
        Node source = graph.getNodeById(costumer.getSourceId());
        Node target = graph.getNodeById(costumer.getTargerId());
        Edge tempEdge = null;
        Node next = null;
        boolean isBreaked = false;
        
        while (source.getId() != target.getId())
        {
            tempEdge = Utilities.getCheapestEdge(source, costumer.getBandWidth(), tempEdge);
            
            if(tempEdge != null)
            {
                if(tempEdge.getNode1().getId() == source.getId())
                {
                    next = tempEdge.getNode2();
                }
                else
                {
                    next = tempEdge.getNode1();
                }
            }
            
            // check if all the edges are not available
            // or (check if the next node is a leaf and not the target)
            // cuz if it is the target we will get to it eventually
            if(tempEdge == null || ( next != null && next.getEdgeCount() == 1 && next.getId() != target.getId()))
            {
                isBreaked = true;
                break;
            }
            
            aResult.addToCost(tempEdge.getEdgeCost());
            
            graph.catchSlots(tempEdge, costumer.getBandWidth());
            
            aResult.addEdgeParameter(new EdgeParameters(tempEdge));
            
            source = next;
        }
        
        if(isBreaked == true)
        {
            //aResult.resetRoute();
            aResult.setRouteCost(0);
            graph.rollbackRoute(aResult.getRoute(), costumer.getBandWidth());
        }
        
        return aResult;
    }
    
    public static AlgoResult Naive(Graph graph, Costumer costumer)
    {
        AlgoResult aResult = new AlgoResult(costumer);
        Node source = graph.getNodeById(costumer.getSourceId());
        Node target = graph.getNodeById(costumer.getTargerId());
        Edge tempEdge = null;
        Node next = null;
        boolean isBreaked = false;
        
        while (source.getId() != target.getId())
        {
            tempEdge = Utilities.getFirstEdge(source, costumer.getBandWidth(), tempEdge);
            
            if(tempEdge != null)
            {
                if(tempEdge.getNode1().getId() == source.getId())
                {
                    next = tempEdge.getNode2();
                }
                else
                {
                    next = tempEdge.getNode1();
                }
            }
            
            // check if all the edges are not available
            // or (check if the next node is a leaf and not the target)
            // cuz if it is the target we will get to it eventually
            if(tempEdge == null || ( next != null && next.getEdgeCount() == 1 && next.getId() != target.getId()))
            {
                isBreaked = true;
                break;
            }
            
            aResult.addToCost(tempEdge.getEdgeCost());
            
            graph.catchSlots(tempEdge, costumer.getBandWidth());
            
            aResult.addEdgeParameter(new EdgeParameters(tempEdge));
            
            source = next;
        }
        
        if(isBreaked == true)
        {
            //aResult.resetRoute();
            aResult.setRouteCost(0);
            graph.rollbackRoute(aResult.getRoute(), costumer.getBandWidth());
        }
        
        return aResult;
    }

    public static AlgoResult Dijkstra(Graph graph, Costumer costumer){

        AlgoResult aResult = new AlgoResult(costumer);

        // Initialize variables
        int nNumberOfNodes = graph.getNumberOfNodes();
        HashMap<Node, Integer> map = new HashMap<Node, Integer>();

        Set<Integer> settled = new HashSet<Integer>();
        Node nSource = graph.getNodeById(costumer.getSourceId());

        HashMap<Integer, Integer> parents = new HashMap<Integer, Integer>();

        // Add source node
        map.put(nSource, 0);
        parents.put(nSource.getId(), -1);

        while (settled.size() != nNumberOfNodes) {
            // Find minimum in map
            Node nMinimum = Utilities.findMinimumNode(map, settled);

            // adding the node whose distance is
            // finalized
            settled.add(nMinimum.getId());

            processNeighbors(nMinimum, settled, map, parents);
        }

        aResult.setRouteCost(map.get(graph.getNodeById(costumer.getTargerId())));
        getRoute(costumer.getTargerId(), parents, aResult);

        return aResult;
    }

    private static void processNeighbors(Node node,
                                         Set<Integer> settled,
                                         HashMap<Node, Integer> map,
                                         HashMap<Integer, Integer> parents)
    {
        int edgeDistance = -1;
        int newDistance = -1;

        for (Edge e:node.getEdges()) {
            Node curr = e.getOtherNode(node.getId());

            if (!settled.contains(curr.getId()))
            {
                edgeDistance = e.getEdgeCost();
                newDistance = map.get(node) + edgeDistance;

                // Check if exist
                if (map.containsKey(curr)) {
                    // If new distance is cheaper in cost
                    if (newDistance < map.get(curr)) {
                        map.put(curr, newDistance);
                        parents.put(curr.getId(), node.getId());
                    }
                }
                else {
                    map.put(curr, newDistance);
                    parents.put(curr.getId(), node.getId());
                }
            }
        }
    }

    private static void getRoute(Integer nTargetId, HashMap<Integer, Integer> parents, AlgoResult a)
    {
        Integer curr = parents.get(nTargetId);
        a.addEdgeParameter(new EdgeParameters(nTargetId, curr, -1, -1));

        while (curr != -1)
        {
            // TODO: Ask about EdgeParameters VS Edge
            a.addEdgeParameter(new EdgeParameters(curr, parents.get(curr), -1, -1));
            curr = parents.get(curr);
        }
    }
}
