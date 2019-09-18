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
        AlgoResult optResult, greedyResult, statisticResult, treeResult;
        List<String> arrCurrFile = new ArrayList<>();

        // Generate random graph 1
        FilesUtils.generateGraphFile("graph1");
        FilesUtils.generateCustomerFile("customers1");

        // Read Decision tree topology graph
        Graph g2 = FilesUtils.readGraph("Graphs\\graph1");
        
        // Get the customers data
        List<Customer> lstC = FilesUtils.readCustomers("Customers\\customers1");

        // Read cap slots
        List<Integer> lstSC = FilesUtils.readSlotCaps();

        // Go over all percentages and calc all algorithms
        for (Integer currPercentage : lstSC)
        {
            Graph gCurrPer = new Graph(g2);
            Double point = (double)currPercentage / 100;

            // Catch slots in the graph by percentage
            gCurrPer.catchRandomSlots(point);

            // Iterate over the customers and run the algorithms
            for (Customer c : lstC)
            {
                System.out.println("OPT: \n");
                optResult = Dijkstra.run(gCurrPer, c, true);
                optResult.print();
                System.out.println("\n**************************************************************************\n");

                System.out.println("Greedy: \n");
                greedyResult = Greedy.run(gCurrPer, c);
                greedyResult.print();
                System.out.println("\n**************************************************************************\n");

                System.out.println("Statistic: \n");
                statisticResult = Statistic.run(gCurrPer, c);
                statisticResult.print();
                System.out.println("\n**************************************************************************\n");

                // Variables for the decision tree
                HashMap<Double, HashMap<Integer, Integer>> hmDTResult = null;
                List<Edge> lstTreeUnavailableEdges = new ArrayList<>();
                Graph gCopy = new Graph(gCurrPer);

                while (lstTreeUnavailableEdges != null)
                {
                    TreeNode tr = gCopy.getDecisionTree(c.getSourceId(), 0, c.getTargetId(), new ArrayList<>());
                    tr.setTreeCost(tr, true, new ArrayList<>(), new ArrayList<>(), c.getSourceId(), c.getTargetId());
                    hmDTResult = tr.getBestPathCost(tr, true, new ArrayList<>(), c.getSourceId(), c.getTargetId());
                    lstTreeUnavailableEdges = gCopy.checkRoute(hmDTResult.get(hmDTResult.keySet().toArray()[0]), c.getBandWidth());
                }

                System.out.println("Desicion Tree: \n");
                treeResult = new AlgoResult(c);
                treeResult.setTotalCost(((Double)hmDTResult.keySet().toArray()[0]).intValue());
                treeResult.setRouteCost(treeResult.getTotalCost() - hmDTResult.get(hmDTResult.keySet().toArray()[0]).size());
                List<Integer> lstDTRoute = new ArrayList<Integer>(hmDTResult.get(hmDTResult.keySet().toArray()[0]).keySet());
                lstDTRoute.add(c.getTargetId());
                treeResult.setRoute(lstDTRoute);
                treeResult.print();
                System.out.println("\n**************************************************************************\n");

                arrCurrFile.add(optResult.getTotalCost()+ "\t" +
                                greedyResult.getTotalCost() + "\t" +
                                statisticResult.getTotalCost() + "\t" +
                                ((Double)hmDTResult.keySet().toArray()[0]).intValue());
            }

            // Write result to file
            FilesUtils.writeResult(arrCurrFile, currPercentage.toString());
            arrCurrFile.clear();
        }
    }
}
