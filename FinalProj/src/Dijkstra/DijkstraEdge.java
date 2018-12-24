/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dijkstra;

import finalproj.Node;

/**
 *
 * @author Liron Levi
 */
public class DijkstraEdge
{   
    private double weight;
    private DijkstraNode startDijkstraNode;
    private DijkstraNode targetDijkstraNode;
    
    private int id;
    private int nTotalSlots;
    private int nSlotCurrentUsage;
    private int nEdgeCost;
    private DijkstraNode node1;
    private DijkstraNode node2;

    public DijkstraEdge(double weight, DijkstraNode startDijkstraNode, DijkstraNode targetDijkstraNode,
                        int nTotalSlots, int nSlotUseCost, DijkstraNode node1, DijkstraNode node2) {
        this.weight = weight;
        this.startDijkstraNode = startDijkstraNode;
        this.targetDijkstraNode = targetDijkstraNode;
            
        this.nTotalSlots = nTotalSlots;
        this.nEdgeCost = nSlotUseCost;
        this.nSlotCurrentUsage = 0;
        this.node1 = node1;
        this.node2 = node2;
        this.node1.addEdge(this);
        this.node2.addEdge(this);
    }

    public double getWeight() {
            return weight;
    }

    public void setWeight(double weight) {
            this.weight = weight;
    }

    public DijkstraNode getStartDijkstraNode() {
            return startDijkstraNode;
    }

    public void setStartDijkstraNode(DijkstraNode startDijkstraNode) {
            this.startDijkstraNode = startDijkstraNode;
    }

    public DijkstraNode getTargetDijkstraNode() {
            return targetDijkstraNode;
    }

    public void setTargetDijkstraNode(DijkstraNode targetDijkstraNode) {
            this.targetDijkstraNode = targetDijkstraNode;
    }
}
