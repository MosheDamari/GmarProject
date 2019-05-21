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
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        EdgeParameters ep01 = new EdgeParameters(1, 2, 4, 2);
        EdgeParameters ep02 = new EdgeParameters(1, 2, 6, 3);
        EdgeParameters ep03 = new EdgeParameters(1, 3, 5, 2);
        EdgeParameters ep04 = new EdgeParameters(1, 4, 2, 4);
        EdgeParameters ep05 = new EdgeParameters(2, 5, 7, 4);
        EdgeParameters ep06 = new EdgeParameters(4, 5, 3, 5);
        EdgeParameters ep07 = new EdgeParameters(4, 6, 9, 2);
        EdgeParameters ep08 = new EdgeParameters(4, 7, 5, 7);
        EdgeParameters ep09 = new EdgeParameters(5, 7, 10, 1);
        EdgeParameters ep10 = new EdgeParameters(5, 7, 3, 3);
        EdgeParameters ep11 = new EdgeParameters(6, 7, 8, 9);
        EdgeParameters ep12 = new EdgeParameters(3, 8, 6, 3);
        EdgeParameters ep13 = new EdgeParameters(6, 9, 2, 3);
        EdgeParameters ep14 = new EdgeParameters(7, 9, 10, 13);
        EdgeParameters ep15 = new EdgeParameters(8, 9, 5, 4);
        List<EdgeParameters> lst = new ArrayList<EdgeParameters>();
        lst.add(ep01);
        lst.add(ep02);
        lst.add(ep03);
        lst.add(ep04);
        lst.add(ep05);
        lst.add(ep06);
        lst.add(ep07);
        lst.add(ep08);
        lst.add(ep09);
        lst.add(ep10);
        lst.add(ep11);
        lst.add(ep12);
        lst.add(ep13);
        lst.add(ep14);
        lst.add(ep15);
        
        Graph g = new Graph(1, lst);
        g.printGraph();
    }
    
    public AlgoResult Griddy(Graph graph, Costumer costumer)
    {
        AlgoResult aResult = new AlgoResult(costumer);
        Node source = graph.getNode(costumer.getSourceId());
        Node target = graph.getNode(costumer.getTargerId());
        Edge tempEdge;
        Node next;
        boolean isBreaked = false;
        
        while (source.getId() != target.getId())
        {
            tempEdge = Utilities.getCheapestEdge(source, costumer.getBandWidth());
            if(tempEdge.getNode1().getId() == source.getId())
            {
                next = tempEdge.getNode1();
            }
            else
            {
                next = tempEdge.getNode2();
            }
            
            // check if all the edges are not available
            // or (check if the next node is a leaf and not the target)
            // cuz if it is the target we will get to it eventually
            if(tempEdge == null || ( next.getEdgeCount() == 1 && next.getId() != target.getId()))
            {
                isBreaked = true;
                break;
            }
            
            aResult.addToCost(tempEdge.getEdgeCost());
            
            aResult.addEdgeParameter(new EdgeParameters(tempEdge));
            
            source = next;
        }
        
        if(isBreaked == true)
        {
            aResult.resetRoute();
        }
        
        return aResult;
    }
}
