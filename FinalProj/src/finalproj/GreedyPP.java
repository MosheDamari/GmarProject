package finalproj;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public final class GreedyPP{

/*    public static AlgoResult run(Graph g, Customer customer)
    {
        int nParent;
        LinkedHashMap<Integer, Integer> currRoute;
        boolean bIsAvailable = false;
        AlgoResult djkResult = new AlgoResult(customer);
        List<Edge> unavailableEdges;
        int lastNode;
        int nodeForContinuum;
        AlgoResult djkRealResult = new AlgoResult(customer);
        

        // Create copy to not damage the original
        Graph graph = new Graph(g);

        // Find the best route we can catch:
        // 1. run dijkstra on the graph without the unavailable edge
        // 2. check if we can catch this route
        // 3. if not, remove the unavailable edge and do the while again
        while (!bIsAvailable)
        {
            djkResult = Dijkstra.run(graph, customer, false);
            lastNode = -1;
            nodeForContinuum = -1;
            currRoute = new LinkedHashMap<>();
            nParent = djkResult.getRoute().get(0);

            for (int i = 1; i < djkResult.getRoute().size(); i++)
            {
                currRoute.put(nParent, djkResult.getRoute().get(i));
                nParent = djkResult.getRoute().get(i);
            }

            unavailableEdge = graph.checkRoute(currRoute, customer.getBandWidth());

            if (unavailableEdges != null) {
                
                for (int nCurrNode : currRoute.keySet())
                {
                    if( ( unavailableEdges.get(0).getNode1().getId() == nCurrNode &&
                          unavailableEdges.get(0).getNode2().getId() == currRoute.get(nCurrNode) ) ||
                        ( unavailableEdges.get(0).getNode1().getId() == currRoute.get(nCurrNode) &&
                          unavailableEdges.get(0).getNode2().getId() == nCurrNode) )
                    {
                        break;
                    } else {
                        djkRealResult.addNode(nCurrNode);
                        
                    }
                    
                    lastNode = nCurrNode;
                }
                
                if(lastNode != -1)
                {
                    if( unavailableEdges.get(0).getNode1().getId() == lastNode ||
                        unavailableEdges.get(0).getNode2().getId() == lastNode )
                    {
                        nodeForContinuum = lastNode;
                    }
                    else if( unavailableEdges.get(0).getNode1().getId() == currRoute.get(lastNode) ||
                             unavailableEdges.get(0).getNode2().getId() == currRoute.get(lastNode) )
                    {
                        nodeForContinuum = currRoute.get(lastNode);
                    }
                }
                
                if(nodeForContinuum != -1)
                {
                    customer.setSourceId(nodeForContinuum);
                }
                
                unavailableEdges.stream().forEach(x -> graph.removeEdge(x));
            }
            else {
                for (int nCurrNode : currRoute.keySet())
                {
                    if(!djkRealResult.isExist(nCurrNode))
                    {
                        djkRealResult.addNode(nCurrNode);
                    }
                    if(!djkRealResult.isExist(currRoute.get(nCurrNode)))
                    {
                        djkRealResult.addNode(currRoute.get(nCurrNode));
                    }
                }

                bIsAvailable = true;
            }
        }

        return djkRealResult;
    }*/
}
