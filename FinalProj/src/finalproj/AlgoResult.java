/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    
}
