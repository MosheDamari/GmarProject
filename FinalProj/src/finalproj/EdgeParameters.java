/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

/**
 * class for the parameters of the edge from the file: 
 * n1 n2 edgeCost numOfSlots
 * @author Liron Levi
 */
public class EdgeParameters
{
    private int n1;
    private int n2;
    private int edgeCost;
    private int numOfSlots;

    public EdgeParameters(int n1, int n2, int edgeCost, int numOfSlots)
    {
        this.n1 = n1;
        this.n2 = n2;
        this.edgeCost = edgeCost;
        this.numOfSlots = numOfSlots;
    }
    
    public EdgeParameters(Edge edge)
    {
        this.n1 = edge.getNode1().getId();
        this.n2 = edge.getNode2().getId();
        this.edgeCost = edge.getEdgeCost();
        this.numOfSlots = edge.getTotalSlots();
    }

    public int getN1()
    {
        return n1;
    }

    public void setN1(int n1)
    {
        this.n1 = n1;
    }

    public int getN2()
    {
        return n2;
    }

    public void setN2(int n2)
    {
        this.n2 = n2;
    }

    public int getEdgeCost()
    {
        return edgeCost;
    }

    public void setEdgeCost(int edgeCost)
    {
        this.edgeCost = edgeCost;
    }

    public int getNumOfSlots()
    {
        return numOfSlots;
    }

    public void setNumOfSlots(int numOfSlots)
    {
        this.numOfSlots = numOfSlots;
    }
    
    
}


