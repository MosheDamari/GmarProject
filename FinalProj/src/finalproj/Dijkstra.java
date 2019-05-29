package finalproj;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class Dijkstra {

    private static HashMap<Node, Integer> map = new HashMap<Node, Integer>();
    private static Set<Integer> settled = new HashSet<Integer>();
    private static HashMap<Integer, Integer> parents = new HashMap<Integer, Integer>();

    public static AlgoResult run(Graph graph, Customer customer){

        AlgoResult aResult = new AlgoResult(customer);

        // Rebuild graph with only available capacity
        Graph newGraph = Utilities.getAvailableCapGraph(graph, customer.getBandWidth());

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
        getRoute(customer.getTargerId(), aResult);

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
                    }
                }
                else {
                    map.put(curr, newDistance);
                    parents.put(curr.getId(), node.getId());
                }
            }
        }
    }

    private static void getRoute(Integer nTargetId, AlgoResult a)
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
