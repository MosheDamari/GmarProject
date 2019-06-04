/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.time.chrono.MinguoChronology;
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
        AlgoResult greedyResult = null, naiveResult = null, djkResult = null;
        List<AlgoResult> lstResults;

        // Read the topology and build the graph
        Graph g = FilesUtils.readGraph();
        Graph g2 = FilesUtils.readGraph();
        Graph g3 = FilesUtils.readGraph();
        Graph resultGraph = FilesUtils.readGraph(); 

        // Get the customers data
        List<Customer> lstC = FilesUtils.readCustomers();

        // Print the current graph - for debug
        g.printGraph();
        System.out.println("\n**************************************************************************\n");

        // Iterate over the customers and run the algorithms
        for (Customer c : lstC)
        {
        	greedyResult = Greedy.run(g, c);
            greedyResult.print();
            System.out.println("\n**************************************************************************\n");
            naiveResult = Naive.run(g2, c);
            naiveResult.print();
            System.out.println("\n**************************************************************************\n");
            djkResult = Dijkstra.run(g3, c);
            djkResult.print();
            System.out.println("\n**************************************************************************\n");
            
            lstResults = new ArrayList<AlgoResult>();
            
            if (greedyResult.getRouteCost() != 0)
            	lstResults.add(greedyResult);
            
            if (naiveResult.getRouteCost() != 0)
            	lstResults.add(naiveResult);
            
            if (djkResult.getRouteCost() != 0)
            	lstResults.add(djkResult);
            
            if (!lstResults.isEmpty())
            {            	
            	Comparator<AlgoResult> comparator = Comparator.comparing(AlgoResult::getRouteCost);
            	lstResults.sort(comparator);
            	
            	resultGraph.updateSlots(lstResults.get(0).getRoute(), c.getBandWidth());
            }
            
            System.out.println("\n**************************************************************************\n");
        }
    }
}
