package finalproj;

import java.io.IOException;
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
            Files.lines(Paths.get("./FinalProj/files/topology.txt")).forEach((line) -> {
                // Split every line by space char and add new EP
                String[] split = line.split(" ");
                EdgeParameters currEP = new EdgeParameters(Integer.parseInt(split[0]),
                                                           Integer.parseInt(split[1]),
                                                           Integer.parseInt(split[3]),
                                                           Integer.parseInt(split[2]));
                lstEP.add(currEP);
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
            Files.lines(Paths.get("./FinalProj/files/customers.txt")).forEach((line) -> {
                // Split every line by space char and add new customer
                String[] split = line.split(" ");
                Customer currC = new Customer(Integer.parseInt(split[0]),
                                              Integer.parseInt(split[1]),
                                              Integer.parseInt(split[2]),
                                              Integer.parseInt(split[3]),
                                              Integer.parseInt(split[4]));
                lstC.add(currC);
            });
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return lstC;
    }
}
