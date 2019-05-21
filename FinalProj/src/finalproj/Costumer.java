/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproj;

/**
 *
 * @author Liron Levi
 */
public class Costumer
{
    private int nSerialNumber;
    private int nSourceId;
    private int nTargerId;
    private int nBandWidth;
    private int nTime;
    
    public Costumer(Costumer costumer)
    {
        this.nSerialNumber = costumer.getSerialNumber();
        this.nSourceId = costumer.getSourceId();
        this.nTargerId = costumer.getTargerId();
        this.nBandWidth = costumer.getBandWidth();
        this.nTime = costumer.getTime();
    }

    public Costumer(int nSerialNumber, int nSourceId, int nTargerId, int nBandWidth, int nTime)
    {
        this.nSerialNumber = nSerialNumber;
        this.nSourceId = nSourceId;
        this.nTargerId = nTargerId;
        this.nBandWidth = nBandWidth;
        this.nTime = nTime;
    }

    public int getSerialNumber()
    {
        return nSerialNumber;
    }

    public void setSerialNumber(int nSerialNumber)
    {
        this.nSerialNumber = nSerialNumber;
    }

    public int getSourceId()
    {
        return nSourceId;
    }

    public void setSourceId(int nSourceId)
    {
        this.nSourceId = nSourceId;
    }

    public int getTargerId()
    {
        return nTargerId;
    }

    public void setTargerId(int nTargerId)
    {
        this.nTargerId = nTargerId;
    }

    public int getBandWidth()
    {
        return nBandWidth;
    }

    public void setBandWidth(int nBandWidth)
    {
        this.nBandWidth = nBandWidth;
    }

    public int getTime()
    {
        return nTime;
    }

    public void setTime(int nTime)
    {
        this.nTime = nTime;
    }   
}
