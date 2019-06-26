/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

/**
 *
 *
 */
public class Edge
{
    private int nSlot; // 0 or 1
    private int nEdgeCost;
    private Node node1;
    private Node node2;

    public Edge(int nSlot, int nEdgeCost, Node node1, Node node2)
    {
        this.nSlot = nSlot;
        this.nEdgeCost = nEdgeCost;
        this.node1 = node1;
        this.node2 = node2;
    }

    public int getSlot()
    {
        return this.nSlot;
    }

    public void setSlot(int nSlot) { this.nSlot = nSlot; }

    public int getEdgeCost()
    {
        return this.nEdgeCost;
    }

    public void setEdgeCost(int nEdgeCost) { this.nEdgeCost = nEdgeCost; }

    public Node getNode1() { return this.node1; }

    public Node getNode2() { return this.node2; }

    public Node getOtherNode(int nodeId)
    {
        Node returnValue = null;

        if (this.node1.getId() == nodeId){
            returnValue = this.node2;
        }
        else if (this.node2.getId() == nodeId) {
            returnValue = this.node1;
        }

        return returnValue;
    }
}
