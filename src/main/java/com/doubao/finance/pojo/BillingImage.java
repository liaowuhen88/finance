package com.doubao.finance.pojo;

import com.doubao.finance.util.BaseModel;
import java.sql.Timestamp;

public class BillingImage
        extends BaseModel
{
    private Long id;
    private String path;
    private Long billingId;
    private Timestamp uploadTime;

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPath()
    {
        return this.path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public Long getBillingId()
    {
        return this.billingId;
    }

    public void setBillingId(Long billingId)
    {
        this.billingId = billingId;
    }

    public Timestamp getUploadTime()
    {
        return this.uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime)
    {
        this.uploadTime = uploadTime;
    }
}
