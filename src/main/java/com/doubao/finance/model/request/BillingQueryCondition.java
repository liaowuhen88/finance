package com.doubao.finance.model.request;

import com.doubao.finance.model.PageParamModel;

public class BillingQueryCondition
        extends PageParamModel
{
    private String fromBillingDate;
    private String toBillingDate;
    private Long chargeEntityId;
    private String chargeEntityName;
    private String remark;
    private Integer imgCount;

    public String getFromBillingDate()
    {
        return this.fromBillingDate;
    }

    public void setFromBillingDate(String fromBillingDate)
    {
        this.fromBillingDate = fromBillingDate;
    }

    public String getToBillingDate()
    {
        return this.toBillingDate;
    }

    public void setToBillingDate(String toBillingDate)
    {
        this.toBillingDate = toBillingDate;
    }

    public Long getChargeEntityId()
    {
        return this.chargeEntityId;
    }

    public void setChargeEntityId(Long chargeEntityId)
    {
        this.chargeEntityId = chargeEntityId;
    }

    public String getChargeEntityName()
    {
        return this.chargeEntityName;
    }

    public void setChargeEntityName(String chargeEntityName)
    {
        this.chargeEntityName = chargeEntityName;
    }

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Integer getImgCount()
    {
        return this.imgCount;
    }

    public void setImgCount(Integer imgCount)
    {
        this.imgCount = imgCount;
    }
}
