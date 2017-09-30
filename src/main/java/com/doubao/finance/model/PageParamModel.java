package com.doubao.finance.model;

import com.doubao.finance.util.BaseModel;

public class PageParamModel
        extends BaseModel
{
    private int draw;
    private int start;
    private int pageSize = 10;

    public int getDraw()
    {
        return this.draw;
    }

    public void setDraw(int draw)
    {
        this.draw = draw;
    }

    public int getStart()
    {
        return this.start;
    }

    public void setStart(int start)
    {
        this.start = start;
    }

    public int getPageSize()
    {
        return this.pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
}

