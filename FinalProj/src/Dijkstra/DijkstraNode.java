/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dijkstra;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liron Levi
 */
public class DijkstraNode implements Comparable<DijkstraNode>
{
    private String name;
    private List<DijkstraEdge> adjacenciesList;
    private boolean visited;
    private DijkstraNode predecessor;
    private double distance = Double.MAX_VALUE;
    private static int idCounter = 0;
    
    private int id;
    //private List<DijkstraEdge> lstEdges;

    public DijkstraNode(String name) {
            this.name = name;
            this.adjacenciesList = new ArrayList<>();
            this.id = idCounter++;
    }

    public void addNeighbour(DijkstraEdge edge) {
            this.adjacenciesList.add(edge);
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public List<DijkstraEdge> getAdjacenciesList() {
            return adjacenciesList;
    }

    public void setAdjacenciesList(List<DijkstraEdge> adjacenciesList) {
            this.adjacenciesList = adjacenciesList;
    }
    
    public void addEdge(DijkstraEdge newEdge) 
    {
        this.adjacenciesList.add(newEdge);
    }

    public boolean isVisited() {
            return visited;
    }

    public void setVisited(boolean visited) {
            this.visited = visited;
    }

    public DijkstraNode getPredecessor() {
            return predecessor;
    }

    public void setPredecessor(DijkstraNode predecessor) {
            this.predecessor = predecessor;
    }

    public double getDistance() {
            return distance;
    }

    public void setDistance(double distance) {
            this.distance = distance;
    }
    
    public int getId()
    {
        return this.id;
    }

    @Override
    public String toString() {
            return this.name;
    }

    @Override
    public int compareTo(DijkstraNode otherDijkstraNode) {
            return Double.compare(this.distance, otherDijkstraNode.getDistance());
    }
}
