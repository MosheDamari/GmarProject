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
        AlgoResult optResult, greedyResult, statisticResult;
        List<String> arrCurrFile = new ArrayList<>();

        // Read the topology and build the graph
        Graph g = FilesUtils.readGraph();
        
        // Get the customers data
        List<Customer> lstC = FilesUtils.readCustomers();

        // Read cap slots
        List<Integer> lstSC = FilesUtils.readSlotCaps();

        // Go over all percentages and calc all algorithms
        for (Integer currPercentage : lstSC)
        {
            Graph gCurrPer = new Graph(g);
            Double point = (double)currPercentage / 100;

            // Catch slots in the graph by percentage
            gCurrPer.catchRandomSlots(point);

            // Iterate over the customers and run the algorithms
            for (Customer c : lstC)
            {
                optResult = Dijkstra.run(gCurrPer, c, true);
                optResult.print();
                System.out.println("\n**************************************************************************\n");

                greedyResult = Greedy.run(gCurrPer, c);
                greedyResult.print();
                System.out.println("\n**************************************************************************\n");

                statisticResult = Statistic.run(gCurrPer, c);
                statisticResult.print();
                System.out.println("\n**************************************************************************\n");

                arrCurrFile.add(optResult.getTotalCost()+ "\t" +
                                greedyResult.getTotalCost() + "\t" +
                                statisticResult.getTotalCost());
            }

            // Write result to file
            FilesUtils.writeResult(arrCurrFile, currPercentage.toString());
            arrCurrFile.clear();
            
          
           
        }
        
        // Liron And Kim check Decision Tree
           System.out.println("");
          Graph g2 = FilesUtils.readGraph2();
//          
          TreeNode tr = g2.getDecisionTree(1, 0, 4, new ArrayList<>());
          tr.setTreeCost(tr, true, new ArrayList<>(), new ArrayList<>(), 1, 4);
          double minCost = tr.getBestPathCost(tr);
          tr.printTree();
    }
}
