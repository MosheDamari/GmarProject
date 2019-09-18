package finalproj;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class FilesUtils {

    private static int MAX_LINES = 15;
    private static int MIN_LINES = 10;
    private static int NUM_OF_CUSTOMERS = 5;

    public static Graph readGraph(String readFileName){
        // Initialize variables
        int nDiscoverCost = 1;
        List<Node> lstN = new ArrayList<Node>();
        List<Edge> lstE = new ArrayList<Edge>();

        try {
            // Try to read from the topology file
            Files.lines(Paths.get(System.getProperty("user.dir") + "\\FinalProj\\files\\" + readFileName + ".txt")).forEach((line) -> {
                if (line.matches(".*\\d.*")) {
            		String[] split = line.substring(1, line.indexOf("]")).split(",");
                    Node newN1;
                    Node newN2;
                    int nNode1Id = Integer.parseInt(split[0]);
                    int nNode2Id = Integer.parseInt(split[1]);
                    int nNumOfEdges = Integer.parseInt(split[2]);
                    int nEdgesCost = Integer.parseInt(split[3]);

            		// Adding a new node1 if we need to
            		if (!Utilities.checkNodeExist(lstN, nNode1Id))
                    {
                        newN1 = new Node(nNode1Id);
                        lstN.add(newN1);
                    }
            		else
                    {
                        newN1 = lstN.stream().filter(o -> o.getId() == nNode1Id).findFirst().get();
                    }

                    // Adding a new node2 if we need to
            		if (!Utilities.checkNodeExist(lstN, nNode2Id))
            		{
            		    newN2 = new Node(nNode2Id);
                        lstN.add(newN2);
                    }
            		else
            		{
                        newN2 = lstN.stream().filter(o -> o.getId() == nNode2Id).findFirst().get();
                    }

            		// Adding edges
                    for (int i = 0 ; i < nNumOfEdges; i++)
                    {
                        Edge e = new Edge(0, nEdgesCost, newN1, newN2);
                        newN1.addEdge(e);
                        newN2.addEdge(e);
                        lstE.add(e);
                    }
            	}
            });
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Return new graph
        return new Graph(nDiscoverCost, lstN, lstE);
    }

    public static List<Customer> readCustomers(String readFileName){
        List<Customer> lstC = new ArrayList<Customer>();

        try {
            // Try to read from the customers file
            Files.lines(Paths.get(System.getProperty("user.dir") + "\\FinalProj\\files\\" + readFileName + ".txt")).forEach((line) -> {
                if (line.matches(".*\\d.*")) {
                    String[] split = line.substring(1, line.indexOf("]")).split(",");
                    Customer currC = new Customer(Integer.parseInt(split[0]),
                                                  Integer.parseInt(split[1]),
                                                  Integer.parseInt(split[2]));
                    lstC.add(currC);
                }
            });
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return lstC;
    }

    public static List<Integer> readSlotCaps(){
        List<Integer> lstSC = new ArrayList<Integer>();

        try {
            Files.lines(Paths.get(System.getProperty("user.dir") + "\\FinalProj\\files\\slotsCap.txt")).forEach((line) -> {
                lstSC.add(Integer.parseInt(line));
            });
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return lstSC;
    }

    public static void writeResult(List<String> lines, String fileName)
    {
        try {
            Files.write(Paths.get(System.getProperty("user.dir") + "\\FinalProj\\files\\ResultPercentages\\" + fileName + ".txt"), lines, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void generateGraphFile(String graphFileName)
    {
        try {
            String currLine;
            int nRandNode, nRandAmount, nRandCost, nRandSecNode, nPrevRand = 1, j = 1;
            List<Integer> allNodes = new ArrayList<>();
            allNodes.add(nPrevRand);

            List<String> lines = new ArrayList<>();
            lines.add("[");

            int nNumberOfLines = ThreadLocalRandom.current().nextInt(MIN_LINES, MAX_LINES + 1);

            // Generate first 100 nodes
            for (int i = 1; i < MIN_LINES; i++)
            {
                nRandNode = ThreadLocalRandom.current().nextInt(1, MIN_LINES + 1);

                // Check if we got the same node
                while (nRandNode == nPrevRand || allNodes.contains(nRandNode)) {
                    nRandNode = j++;
                }

                nRandAmount = ThreadLocalRandom.current().nextInt(1, 10 + 1);
                nRandCost = ThreadLocalRandom.current().nextInt(1, 20 + 1);

                currLine = "[" + nPrevRand + "," + nRandNode + "," + nRandAmount + "," + nRandCost + "],";
                lines.add(currLine);

                allNodes.add(nRandNode);
                nPrevRand = nRandNode;
            }

            // Generate more edges
            for (int i = 0; i < nNumberOfLines - MIN_LINES + 1; i++)
            {
                nRandNode = ThreadLocalRandom.current().nextInt(1, MIN_LINES + 1);
                nRandSecNode = ThreadLocalRandom.current().nextInt(1, MIN_LINES + 1);

                // Check if we got the same node
                while (nRandSecNode == nRandNode) {
                    nRandSecNode = ThreadLocalRandom.current().nextInt(1, MIN_LINES + 1);
                }

                nRandAmount = ThreadLocalRandom.current().nextInt(1, 10 + 1);
                nRandCost = ThreadLocalRandom.current().nextInt(1, 20 + 1);

                if (i != nNumberOfLines - MIN_LINES) {
                    currLine = "[" + nRandNode + "," + nRandSecNode + "," + nRandAmount + "," + nRandCost + "],";
                }
                else {
                    currLine = "[" + nRandNode + "," + nRandSecNode + "," + nRandAmount + "," + nRandCost + "]";
                }

                lines.add(currLine);
            }

            lines.add("]");

            Files.write(Paths.get(System.getProperty("user.dir") + "\\FinalProj\\files\\Graphs\\" + graphFileName + ".txt"), lines, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void generateCustomerFile(String custFileName)
    {
        try {
            String currLine;
            int nRandNode, nRandSecNode;
            List<IntPair> lstAllPairs = new ArrayList<>();

            List<String> lines = new ArrayList<>();
            lines.add("[");

            for (int i = 1; i <= NUM_OF_CUSTOMERS; i ++)
            {
                nRandNode = ThreadLocalRandom.current().nextInt(1, MIN_LINES + 1);
                nRandSecNode = ThreadLocalRandom.current().nextInt(1, MIN_LINES + 1);

                // Check if we already have that customer
                while (Utilities.checkPairExist(lstAllPairs, nRandNode, nRandSecNode) || nRandNode == nRandSecNode)
                {
                    nRandNode = ThreadLocalRandom.current().nextInt(1, MIN_LINES + 1);
                    nRandSecNode = ThreadLocalRandom.current().nextInt(1, MIN_LINES + 1);
                }

                lstAllPairs.add(new IntPair(nRandNode, nRandSecNode));

                if (i != NUM_OF_CUSTOMERS)
                {
                    currLine = "[" + nRandNode + "," + nRandSecNode + ",1],";
                }
                else {
                    currLine = "[" + nRandNode + "," + nRandSecNode + ",1]";
                }

                lines.add(currLine);
            }

            lines.add("]");

            Files.write(Paths.get(System.getProperty("user.dir") + "\\FinalProj\\files\\Customers\\" + custFileName + ".txt"), lines, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
