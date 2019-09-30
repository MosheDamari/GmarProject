package Pandora;

import finalproj.Customer;
import finalproj.FilesUtils;
import finalproj.Graph;

import java.io.IOException;
import java.util.List;

public class MainMoshe {
    public static void main(String[] args) throws IOException {
        // Read the topology and build the graph
        Graph g = FilesUtils.readGraph();

        // Get the customers data
        List<Customer> lstC = FilesUtils.readCustomers();

        // Read cap slots
        List<Integer> lstSC = FilesUtils.readSlotCaps();
        // Go over all percentages and calc all algorithms
        PandoraGraph pGraph = new PandoraGraph(g);
        Pandora.run(pGraph,lstC);
    }
}
