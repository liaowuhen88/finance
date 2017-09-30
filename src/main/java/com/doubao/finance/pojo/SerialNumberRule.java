package com.doubao.finance.pojo;

import java.sql.Timestamp;

public class SerialNumberRule
{
    public static final String DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";
    private Long id;
    private String businessName;
    private String prefix = "";
    private String dateFormat = "yyyyMMddHHmmss";
    private byte width = 1;
    private long start = 1L;
    private long max = Long.MAX_VALUE;
    private int step = 1;
    private Timestamp createTime;

    public Timestamp getCreateTime()
    {
        return this.createTime;
    }

    public static String getDefaultDateFormat()
    {
        return "yyyyMMddHHmmss";
    }

    public String getBusinessName()
    {
        return this.businessName;
    }

    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }

    public String getPrefix()
    {
        return this.prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public String getDateFormat()
    {
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    public byte getWidth()
    {
        return this.width;
    }

    public void setWidth(byte width)
    {
        this.width = width;
    }

    public long getStart()
    {
        return this.start;
    }

    public void setStart(long start)
    {
        this.start = start;
    }

    public long getMax()
    {
        return this.max;
    }

    public void setMax(long max)
    {
        this.max = max;
    }

    public int getStep()
    {
        return this.step;
    }

    public void setStep(int step)
    {
        this.step = step;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getTotalLength()
    {
        return getPrefix().length() + getDateFormat().length() + getWidth();
    }
}
