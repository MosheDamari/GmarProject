/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pandora;

import finalproj.Edge;
import finalproj.Node;

import java.util.HashMap;

/**
 *
 *
 */
public class PandoraEdge extends Edge
{
    private HashMap<String, HashMap<Integer,Float>> nEdgeCostToPrecent;

    public PandoraEdge(int nSlot, int nEdgeCost, Node node1, Node node2) {
        super(nSlot, nEdgeCost, node1, node2);
    }

    public HashMap<String, HashMap<Integer, Float>> getnEdgeCostToPrecent() {
        return nEdgeCostToPrecent;
    }

    public void setnEdgeCostToPrecent(HashMap<String, HashMap<Integer, Float>> nEdgeCostToPrecent) {
        this.nEdgeCostToPrecent = nEdgeCostToPrecent;
    }
}
