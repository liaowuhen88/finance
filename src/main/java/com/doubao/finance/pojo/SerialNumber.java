package com.doubao.finance.pojo;

public class SerialNumber
{
    private Long id;
    private String businessName;
    private String day;
    private long curVal;

    public SerialNumber() {}

    public SerialNumber(String businessName, String day, long curVal)
    {
        this.businessName = businessName;
        this.day = day;
        this.curVal = curVal;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getBusinessName()
    {
        return this.businessName;
    }

    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }

    public String getDay()
    {
        return this.day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public long getCurVal()
    {
        return this.curVal;
    }

    public void setCurVal(long curVal)
    {
        this.curVal = curVal;
    }
}
