package finalproj;

public final class Naive {

    public static AlgoResult run(Graph graph, Customer customer)
    {
        AlgoResult aResult = new AlgoResult(customer);
        Node source = graph.getNodeById(customer.getSourceId());
        Node target = graph.getNodeById(customer.getTargerId());
        Edge tempEdge = null;
        Node next = null;
        boolean isBreaked = false;

        while (source.getId() != target.getId())
        {
            tempEdge = Utilities.getFirstEdge(source, customer.getBandWidth(), tempEdge);

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

            graph.catchSlots(tempEdge, customer.getBandWidth());

            aResult.addEdgeParameter(new EdgeParameters(tempEdge));

            source = next;
        }

        if(isBreaked == true)
        {
            //aResult.resetRoute();
            aResult.setRouteCost(0);
            graph.rollbackRoute(aResult.getRoute(), customer.getBandWidth());
        }

        return aResult;
    }

}
