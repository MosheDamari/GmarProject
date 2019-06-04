/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.io.IOException;
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
    public static void main(String[] args) throws IOException
    {
        AlgoResult aResult = null;

        // Read the topology and build the graph
        Graph g = FilesUtils.readGraph();

        // Get the customers data
        List<Customer> lstC = FilesUtils.readCustomers();

        // Print the current graph - for debug
        g.printGraph();
        System.out.println("\n**************************************************************************\n");

        aResult = Dijkstra.run(g, lstC.get(0));

//        // Iterate over the customers and run the algorithms
//        for (Customer c : lstC)
//        {
//            //aResult = Greedy.run(g, lstC.get(0));
//            //aResult = Naive.run(g, lstC.get(0));
//            aResult = Dijkstra.run(g, c);
//        }

        aResult.print();
        
        Graph g1 = new Graph(1, aResult.getRoute());
        FilesUtils.writeGraph(g1);
        
        
        
    }
}
