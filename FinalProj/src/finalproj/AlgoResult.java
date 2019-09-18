/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.util.*;

/**
 *
 *
 */
public class AlgoResult
{
    private Customer customer;
    private int nRouteCost;
    private int nTotalCost;
    private List<Integer> route;

    public AlgoResult(Customer customer)
    {
        this.customer = new Customer(customer);
        this.nRouteCost = 0;
        this.nTotalCost = 0;
        this.route = new ArrayList<>();
    }

    public Customer getCustomer() { return this.customer; }

    public int getRouteCost() { return this.nRouteCost; }

    public int getTotalCost() { return this.nTotalCost; }

    public void setRouteCost(int routeCost) { this.nRouteCost = routeCost; }

    public void setTotalCost(int totalCost) { this.nTotalCost = totalCost; }

    public List<Integer> getRoute(){ return this.route; }

    public void setRoute(List<Integer> lstRoute){ this.route = lstRoute; }

    public void addNode(int n)
    {
        this.route.add(n);
    }

    public void print()
    {
        System.out.println("Route Cost: " + this.nRouteCost);
        System.out.println("Total Cost: " + this.nTotalCost);
        System.out.print("Route: ");
        System.out.print(this.route.get(0));
        for (int i = 1; i < this.route.size(); i++) {
            System.out.print(" -> " + this.route.get(i));
        }
    }
    
    public boolean isExist(Integer node) 
    {
        for (int i=0; i < this.route.size(); i++)
        {
            if(this.route.get(i) == node) 
            {
                return true;
            }
        }
        return false;
    
    }

    public HashMap<Integer, Integer> getRouteCosts(Graph g)
    {
        Comparator<Edge> edgeComparator = Comparator.comparing(Edge::getEdgeCost);
        int routeCost = 0, totalCost = 0;
        HashMap<Integer, Integer> costs = new HashMap<>();

        for (int i = 0; i < this.getRoute().size() - 1; i++)
        {
            List<Edge> lstEdges = g.getEdgesBetweenNodes(g.getNodeById(this.getRoute().get(i)), g.getNodeById(this.getRoute().get(i+1)));
            lstEdges.sort(edgeComparator);

            for (Edge edge : lstEdges)
            {
                totalCost++;

                if (edge.getSlot() != 1)
                {
                    routeCost += edge.getEdgeCost();
                    break;
                }
            }
        }

        costs.put(routeCost, totalCost);
        return costs;
    }
}
