package finalproj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Dijkstra {

    private static HashMap<Node, Integer> map;
    private static Set<Integer> settled;
    private static HashMap<Integer, Integer> parents;
    private static List<Edge> lstEdges;

    public static AlgoResult run(Graph graph, Customer customer){
    	
    	lstEdges = new ArrayList<Edge>();
    	map = new HashMap<Node, Integer>();
    	settled = new HashSet<Integer>();
    	parents = new HashMap<Integer, Integer>();

        AlgoResult aResult = new AlgoResult(customer);

        // Rebuild graph with only available capacity
        // TODO - Change func getAvailableCapGraph
        Graph newGraph = graph; //Utilities.getAvailableCapGraph(graph, customer.getBandWidth());

        // Initialize variables
        int nNumberOfNodes = newGraph.getNumberOfNodes();
        Node nSource = newGraph.getNodeById(customer.getSourceId());

        // Add source node
        map.put(nSource, 0);
        parents.put(nSource.getId(), -1);

        while (settled.size() != nNumberOfNodes) {
            // Find minimum in map
            Node nMinimum = findMinimumNode();

            // adding the node whose distance is
            // finalized
            settled.add(nMinimum.getId());

            processNeighbors(nMinimum);
        }

        aResult.setRouteCost(map.get(newGraph.getNodeById(customer.getTargerId())));
        getRoute(customer.getTargerId(), aResult, newGraph);

        return aResult;
    }

    private static void processNeighbors(Node node)
    {
        int edgeDistance = -1;
        int newDistance = -1;

        for (Edge e:node.getEdges()) {
            Node curr = e.getOtherNode(node.getId());

            // Check if we visited the current node already
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
                        lstEdges.add(e);
                    }
                }
                else {
                    map.put(curr, newDistance);
                    parents.put(curr.getId(), node.getId());
                    lstEdges.add(e);
                }
            }
        }
    }

    private static void getRoute(Integer nTargetId, AlgoResult a, Graph g)
    {
        Integer curr = parents.get(nTargetId);
        Edge edge = null;
        
        for (Edge e : lstEdges) {
        	if ((nTargetId == e.getNode1().getId() && 
				curr == e.getNode2().getId()) || 
    			(nTargetId == e.getNode2().getId() && 
				curr == e.getNode1().getId()))
			{
        		edge = e;
        		break;
			}
        }
        
        a.addEdgeParameter(new EdgeParameters(nTargetId, curr, edge.getEdgeCost(), edge.getTotalSlots()));

        while (curr != -1)
        {    
        	a.addEdgeParameter(new EdgeParameters(curr, parents.get(curr), edge.getEdgeCost(), edge.getTotalSlots()));
            curr = parents.get(curr);
            
        	for (Edge e : lstEdges) {
	        	if ((nTargetId == e.getNode1().getId() && 
					curr == e.getNode2().getId()) || 
	    			(nTargetId == e.getNode2().getId() && 
					curr == e.getNode1().getId()))
				{
	        		edge = e;
	        		break;
				}
	        }
        }
    }

    private static Node findMinimumNode()
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
