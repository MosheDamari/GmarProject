package Pandora;

import finalproj.Customer;
import finalproj.Edge;
import finalproj.Node;

import java.util.*;

public class Path {
    private Customer customer;
    private LinkedList<Node> path;
    private LinkedList<Edge> edgesPath;
    private int pathCost;
    private double pathChance;

    public Path(Customer customer, LinkedList<Node> path, LinkedList<Edge> edgesPath){
        this.customer = customer;
        this.path = path;
        this.edgesPath = edgesPath;
        this.pathCost = 0;
        this.pathChance = 0;
    }
    public Path(){}
    public Set<Path> getPaths(Customer customer , HashMap<String, LinkedList<Node>> customerPaths){
        Set<Path> returnPaths = new HashSet<>();
        for(Map.Entry<String, LinkedList<Node>> paths: customerPaths.entrySet()) {
            LinkedList<Node> path = paths.getValue();
            Node startNode = path.poll();
            Node lastNode = path.getLast();
            LinkedList<Edge> Edges = new LinkedList();
            /*
            Set<Edge> sEdge = startNode.getEdgesToTarget(path.peek());

            for (Edge edge : sEdge) {
                Edges.add(edge);
                loopEdges(customer, edge, lastNode, returnPaths, path, Edges);
            }
             */

        }
        return  returnPaths;
    }
    private static void loopEdges(Customer customer, Edge edge, Node lastNode, Set<Path> returnPaths, LinkedList<Node> path, LinkedList<Edge> Edges){
        Node currentNode = edge.getNode2();
        if(currentNode.getId() == lastNode.getId()){
            returnPaths.add(new Path(customer, path, Edges));
            return;
        }
        for(Edge targetEdge : currentNode.getEdges()){
            if(currentNode.getId() != edge.getNode1().getId()){
                if(path.peek().getId() == targetEdge.getNode2().getId()){
                    Edges.add(targetEdge);
                    Node tempNode = path.poll();
                    loopEdges(customer, targetEdge, lastNode, returnPaths, path, Edges);
                    path.add(tempNode);
                }
            }
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LinkedList<Node> getPath() {
        return path;
    }

    public void setPath(LinkedList<Node> path) {
        this.path = path;
    }

    public LinkedList<Edge> getEdgesPath() {
        return edgesPath;
    }

    public void setEdgesPath(LinkedList<Edge> edgesPath) {
        this.edgesPath = edgesPath;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public double getPathChance() {
        return pathChance;
    }

    public void setPathChance(double pathChance) {
        this.pathChance = pathChance;
    }

}
