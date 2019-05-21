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
public class AlgoResult
{
    private Costumer costumer;
    private int routeCost;
    private int totalCost;
    private List<EdgeParameters> route;

    public AlgoResult(Costumer costumer)
    {
        this.costumer = new Costumer(costumer);
        this.routeCost = 0;
        this.totalCost = 0;
        this.route = new ArrayList<EdgeParameters>();
    }

    public Costumer getCostumer()
    {
        return costumer;
    }

    public void setCostumer(Costumer costumer)
    {
        this.costumer = costumer;
    }

    public int getRouteCost()
    {
        return routeCost;
    }

    public void setRouteCost(int routeCost)
    {
        this.routeCost = routeCost;
    }

    public int getTotalCost()
    {
        return totalCost;
    }

    public void setTotalCost(int totalCost)
    {
        this.totalCost = totalCost;
    }
    
    public void addEdgeParameter(EdgeParameters ep)
    {
        this.route.add(ep);
    }
    
    public void resetRoute()
    {
        this.route = null;
    }
    
    public void addToCost(int cost)
    {
        this.routeCost += cost;
    }
}
