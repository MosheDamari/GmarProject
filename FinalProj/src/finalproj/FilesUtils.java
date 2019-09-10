package finalproj;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class FilesUtils {

    public static Graph readGraph(){
        // Initialize variables
        int nDiscoverCost = 1;
        List<Node> lstN = new ArrayList<Node>();
        List<Edge> lstE = new ArrayList<Edge>();

        try {
            // Try to read from the topology file
            Files.lines(Paths.get(System.getProperty("user.dir") + "\\FinalProj\\files\\newTopology.txt")).forEach((line) -> {
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
    
    public static Graph readGraph2(){
        // Initialize variables
        int nDiscoverCost = 1;
        List<Node> lstN = new ArrayList<Node>();
        List<Edge> lstE = new ArrayList<Edge>();

        try {
            // Try to read from the topology file
            Files.lines(Paths.get("D:\\limudim\\GmarProject\\FinalProj\\files\\DecisionTreeCheck.txt")).forEach((line) -> {
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

    public static List<Customer> readCustomers(){
        List<Customer> lstC = new ArrayList<Customer>();

        try {
            // Try to read from the customers file
            Files.lines(Paths.get(System.getProperty("user.dir") + "\\FinalProj\\files\\newCust.txt")).forEach((line) -> {
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
}
