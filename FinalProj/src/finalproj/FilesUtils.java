package finalproj;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    public static void writeGraph(Graph g){
//            throws IOException {
//        FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "\\files\\NewGraph.txt");
//        PrintWriter printWriter = new PrintWriter(fileWriter);
//        for(int i = 0; i < g.getNumberOfEdges() - 1; i++)
//        {
//            String str = "[" + g.getEdgeParametersList().get(i).getNode1().getId() + ", " + g.getEdgeParametersList().get(i).getNode2().getId() +  ", " + g.getEdgeParametersList().get(i).getTotalSlots() + ", " + g.getEdgeParametersList().get(i).getEdgeCost() + "],\n" ;
//            printWriter.print(str);
//        }
//
//        String str = "[" + g.getEdgeParametersList().get(g.getNumberOfEdges() - 1).getNode1().getId() + ", " + g.getEdgeParametersList().get(g.getNumberOfEdges() - 1).getNode2().getId() +  ", " + g.getEdgeParametersList().get(g.getNumberOfEdges() - 1).getTotalSlots() + ", " + g.getEdgeParametersList().get(g.getNumberOfEdges() - 1).getEdgeCost() + "]" ;
//        printWriter.print(str);
//        printWriter.close();
    }
}
