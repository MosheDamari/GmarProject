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
        List<EdgeParameters> lstEP = new ArrayList<EdgeParameters>();
        int nDiscoverCost = 1;

        try {
            // Try to read from the topology file
            Files.lines(Paths.get(System.getProperty("user.dir") + "\\files\\topology.txt")).forEach((line) -> {
                if (line.matches(".*\\d.*")) {
            		String[] split = line.substring(1, line.indexOf("]")).split(",");
            		EdgeParameters currEP = new EdgeParameters(Integer.parseInt(split[0]),
									            				Integer.parseInt(split[1]),
									                            Integer.parseInt(split[3]),
									                            Integer.parseInt(split[2]));
            		lstEP.add(currEP);
            		
            	}   
            });
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Return new graph
        return new Graph(nDiscoverCost, lstEP);
    }


    public static List<Customer> readCustomers(){
        List<Customer> lstC = new ArrayList<Customer>();

        try {
            // Try to read from the customers file
            Files.lines(Paths.get(System.getProperty("user.dir") + "\\files\\customers.txt")).forEach((line) -> {
                            	if (line.matches(".*\\d.*")) {
	                String[] split = line.substring(1, line.indexOf("]")).split(",");
	                Customer currC = new Customer(Integer.parseInt(split[0]),
	                                              Integer.parseInt(split[1]),
	                                              Integer.parseInt(split[2]),
	                                              Integer.parseInt(split[3]),
	                                              Integer.parseInt(split[4]));
	                lstC.add(currC);
            	}
            });
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return lstC;
    }
    
    public static void writeGraph(Graph g)
            throws IOException {
        FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "\\files\\NewGraph.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(int i = 0; i < g.getNumberOfEdges() - 1; i++)
        {
            String str = "[" + g.getEdgeParametersList().get(i).getNode1().getId() + ", " + g.getEdgeParametersList().get(i).getNode2().getId() +  ", " + g.getEdgeParametersList().get(i).getTotalSlots() + ", " + g.getEdgeParametersList().get(i).getEdgeCost() + "],\n" ;
            printWriter.print(str);
        }
        
        String str = "[" + g.getEdgeParametersList().get(g.getNumberOfEdges() - 1).getNode1().getId() + ", " + g.getEdgeParametersList().get(g.getNumberOfEdges() - 1).getNode2().getId() +  ", " + g.getEdgeParametersList().get(g.getNumberOfEdges() - 1).getTotalSlots() + ", " + g.getEdgeParametersList().get(g.getNumberOfEdges() - 1).getEdgeCost() + "]" ;
        printWriter.print(str);
        printWriter.close();
    }
}
