/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.util.ArrayList;
import java.util.List;

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
        EdgeParameters ep1 = new EdgeParameters(1, 2, 3, 4);
        EdgeParameters ep2 = new EdgeParameters(2, 3, 4, 5);
        EdgeParameters ep3 = new EdgeParameters(2, 1, 3, 6);
        EdgeParameters ep4 = new EdgeParameters(1, 3, 4, 4);
        EdgeParameters ep5 = new EdgeParameters(1, 3, 3, 4);
        List<EdgeParameters> lst = new ArrayList<EdgeParameters>();
        lst.add(ep1);
        lst.add(ep2);
        lst.add(ep3);
        lst.add(ep4);
        lst.add(ep5);
        
        Graph g = new Graph(1, lst);
        g.printGraph();
    }
    
}
