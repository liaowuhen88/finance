package com.doubao.finance.model.request;

import com.doubao.finance.model.PageParamModel;

public class RealCollectionQueryCondition
        extends PageParamModel
{
    private String fromReceiveDate;
    private String toReceiveDate;
    private Long chargeEntityId;
    private String chargeEntityName;

    public String getFromReceiveDate()
    {
        return this.fromReceiveDate;
    }

    public void setFromReceiveDate(String fromReceiveDate)
    {
        this.fromReceiveDate = fromReceiveDate;
    }

    public String getToReceiveDate()
    {
        return this.toReceiveDate;
    }

    public void setToReceiveDate(String toReceiveDate)
    {
        this.toReceiveDate = toReceiveDate;
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
}

