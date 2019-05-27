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
        AlgoResult aResult = null;
        Costumer c01 = new Costumer(1, 1, 9, 30, 1);
        EdgeParameters ep01 = new EdgeParameters(1, 2, 4, 20);
        EdgeParameters ep02 = new EdgeParameters(1, 2, 6, 30);
        EdgeParameters ep03 = new EdgeParameters(1, 3, 5, 20);
        EdgeParameters ep04 = new EdgeParameters(1, 4, 2, 40);
        EdgeParameters ep05 = new EdgeParameters(2, 5, 7, 40);
        EdgeParameters ep06 = new EdgeParameters(4, 5, 3, 50);
        EdgeParameters ep07 = new EdgeParameters(4, 6, 9, 20);
        EdgeParameters ep08 = new EdgeParameters(4, 7, 5, 70);
        EdgeParameters ep09 = new EdgeParameters(5, 7, 10, 10);
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
        lst.add(ep16);
        lst.add(ep17);
        lst.add(ep18);
        
        Graph g = new Graph(1, lst);
        g.printGraph();
        System.out.println("\n**************************************************************************\n");
        aResult = Greedy(g, c01);
        aResult.print();
        
    }
    
    public static AlgoResult Greedy(Graph graph, Costumer costumer)
    {
        AlgoResult aResult = new AlgoResult(costumer);
        Node source = graph.getNodeById(costumer.getSourceId());
        Node target = graph.getNodeById(costumer.getTargerId());
        Edge tempEdge = null;
        Node next;
        boolean isBreaked = false;
        
        while (source.getId() != target.getId())
        {
            tempEdge = Utilities.getCheapestEdge(source, costumer.getBandWidth(), tempEdge);
            if(tempEdge.getNode1().getId() == source.getId())
            {
                next = tempEdge.getNode2();
            }
            else
            {
                next = tempEdge.getNode1();
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
            
            graph.catchSlots(tempEdge, costumer.getBandWidth());
            
            aResult.addEdgeParameter(new EdgeParameters(tempEdge));
            
            source = next;
        }
        
        if(isBreaked == true)
        {
            aResult.resetRoute();
        }
        
        return aResult;
    }
    
    public static AlgoResult Naive(Graph graph, Costumer costumer)
    {
        return null;
    }
}
