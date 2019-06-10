package finalproj;

import java.util.*;

public final class Dijkstra {

    private static HashMap<Node, Integer> map;
    private static Set<Integer> settled;
    private static HashMap<Integer, Integer> parents;
    private static List<Edge> lstEdges;

    public static AlgoResult run(Graph graph, Customer customer, boolean bKnowledge){

        lstEdges = new ArrayList<Edge>();
        map = new HashMap<Node, Integer>();
        settled = new HashSet<Integer>();
        parents = new HashMap<Integer, Integer>();
        Graph newGraph;

        AlgoResult aResult = new AlgoResult(customer);

        // Rebuild graph with only available capacity
        if (bKnowledge)
        {
            newGraph = Utilities.getAvailableCapGraph(graph, customer.getBandWidth());
        }
        else {
            newGraph = graph;
        }

        // Initialize variables
        int nNumberOfNodes = newGraph.getNodes().size();
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

            processNeighbors(nMinimum, customer.getBandWidth(), newGraph);
        }

        aResult.setRouteCost(map.get(newGraph.getNodeById(customer.getTargetId())));
        getRoute(customer.getTargetId(), aResult);

        return aResult;
    }

    private static void processNeighbors(Node node, int custBW, Graph graph)
    {
        int edgeDistance = -1;
        int newDistance = -1;

        for (Node curr : node.getNeighbors()) {

            // Check if we visited the current node already
            if (!settled.contains(curr.getId()))
            {
                edgeDistance = Utilities.getCheapestPathBetweenNodes(graph, node, curr, custBW);

                if (edgeDistance != -1) {
                    newDistance = map.get(node) + edgeDistance;

                    // Check if exist
                    if (map.containsKey(curr)) {
                        // If new distance is cheaper in cost
                        if (newDistance < map.get(curr)) {
                            map.put(curr, newDistance);
                            parents.put(curr.getId(), node.getId());
                        }
                    } else {
                        map.put(curr, newDistance);
                        parents.put(curr.getId(), node.getId());
                    }
                }
            }
        }
    }

    private static void getRoute(int nTargetId, AlgoResult a)
    {
        int curr = parents.get(nTargetId);
        a.addNode(nTargetId);

        while (curr != -1)
        {
            a.addNode(curr);
            curr = parents.get(curr);
        }

        Collections.reverse(a.getRoute());
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