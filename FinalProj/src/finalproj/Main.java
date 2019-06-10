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
        AlgoResult greedyResult, optResult;
//        List<AlgoResult> lstResults;

        // Read the topology and build the graph
        Graph g = FilesUtils.readGraph();

        // Get the customers data
        List<Customer> lstC = FilesUtils.readCustomers();

        // Print the current graph - for debug
        g.printGraph();
        System.out.println("\n**************************************************************************\n");

        // Iterate over the customers and run the algorithms
        for (Customer c : lstC)
        {
            optResult = Dijkstra.run(g, c, true);
            optResult.print();
            System.out.println("\n**************************************************************************\n");

        	greedyResult = Greedy.run(g, c);
            greedyResult.print();
//            System.out.println("\n**************************************************************************\n");
//
////            lstResults = new ArrayList<>();
////
////            if (greedyResult.getRouteCost() != 0)
////            	lstResults.add(greedyResult);
////
////            if (optResult.getRouteCost() != 0)
////            	lstResults.add(optResult);
////
////            if (!lstResults.isEmpty())
////            {
////            	Comparator<AlgoResult> comparator = Comparator.comparing(AlgoResult::getRouteCost);
////            	lstResults.sort(comparator);
////
////            	resultGraph.updateSlots(lstResults.get(0).getRoute(), c.getBandWidth());
////            }
//
            System.out.println("\n**************************************************************************\n");
        }
    }
}
