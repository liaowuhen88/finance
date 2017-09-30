package com.doubao.finance.model;

import com.doubao.finance.util.BaseModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageResponse<T>
        extends BaseModel
{
    private long draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<T> data = new ArrayList();

    public PageResponse(long draw)
    {
        this.draw = draw;
    }

    public PageResponse(long draw, long recordsTotal)
    {
        this(draw, recordsTotal, Collections.EMPTY_LIST);
    }

    public PageResponse(long draw, long recordsTotal, List<T> data)
    {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
        this.data = data;
    }

    public long getDraw()
    {
        return this.draw;
    }

    public void setDraw(long draw)
    {
        this.draw = draw;
    }

    public long getRecordsTotal()
    {
        return this.recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal)
    {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered()
    {
        return this.recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered)
    {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData()
    {
        return this.data;
    }

    public void setData(List<T> data)
    {
        this.data = data;
    }
}

