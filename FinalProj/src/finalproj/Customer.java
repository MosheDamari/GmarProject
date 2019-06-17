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
public class Customer
{
    private int nSourceId;
    private int nTargetId;
    private int nBandWidth;

    public Customer(Customer customer)
    {
        this.nSourceId = customer.getSourceId();
        this.nTargetId = customer.getTargetId();
        this.nBandWidth = customer.getBandWidth();
    }

    public Customer(int nSourceId, int nTargetId, int nBandWidth)
    {
        this.nSourceId = nSourceId;
        this.nTargetId = nTargetId;
        this.nBandWidth = nBandWidth;
    }

    public int getSourceId()
    {
        return nSourceId;
    }

    public int getTargetId()
    {
        return nTargetId;
    }

    public int getBandWidth()
    {
        return nBandWidth;
    }

    public void setSourceId(int nSourceId)
    {
        this.nSourceId = nSourceId;
    }

    public void setTargetId(int nTargetId)
    {
        this.nTargetId = nTargetId;
    }
}
