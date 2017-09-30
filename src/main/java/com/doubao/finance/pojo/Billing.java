package com.doubao.finance.pojo;

import com.doubao.finance.enums.BillingType;
import com.doubao.finance.enums.ProjectType;
import com.doubao.finance.util.BaseModel;
import com.doubao.finance.util.excel.annotation.ExportEntity;
import com.doubao.finance.util.excel.annotation.ExportField;
import java.math.BigDecimal;
import java.util.Date;

@ExportEntity(sheetName="��������")
public class Billing
        extends BaseModel
{
    private Long id;
    @ExportField(title="������")
    private String billingSerialNumber;
    private Long chargeEntityId;
    @ExportField(title="��������")
    private String chargeEntityName;
    @ExportField(title="��������")
    private BigDecimal billingMoney;
    @ExportField(title="��������", refer=BillingType.class)
    private int billType;
    @ExportField(title="��������", refer=ProjectType.class)
    private int billProjectType;
    @ExportField(title="��������")
    private String billingDate;
    @ExportField(title="������")
    private String applicant;
    @ExportField(title="��������")
    private int billingImgCount;
    @ExportField(title="����")
    private String remark;
    private int isDel;
    @ExportField(title="����������")
    private String lastUpdater;
    private Date createTime;
    @ExportField(title="������������")
    private Date lastUpdateTime;

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public boolean isImg()
    {
        return this.billingImgCount > 0;
    }

    public String getBillingSerialNumber()
    {
        return this.billingSerialNumber;
    }

    public void setBillingSerialNumber(String billingSerialNumber)
    {
        this.billingSerialNumber = billingSerialNumber;
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

    public BigDecimal getBillingMoney()
    {
        return this.billingMoney;
    }

    public void setBillingMoney(BigDecimal billingMoney)
    {
        this.billingMoney = billingMoney;
    }

    public String getBillingDate()
    {
        return this.billingDate;
    }

    public void setBillingDate(String billingDate)
    {
        this.billingDate = billingDate;
    }

    public String getApplicant()
    {
        return this.applicant;
    }

    public void setApplicant(String applicant)
    {
        this.applicant = applicant;
    }

    public int getBillingImgCount()
    {
        return this.billingImgCount;
    }

    public void setBillingImgCount(int billingImgCount)
    {
        this.billingImgCount = billingImgCount;
    }

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public int getIsDel()
    {
        return this.isDel;
    }

    public void setIsDel(int isDel)
    {
        this.isDel = isDel;
    }

    public String getLastUpdater()
    {
        return this.lastUpdater;
    }

    public void setLastUpdater(String lastUpdater)
    {
        this.lastUpdater = lastUpdater;
    }

    public Date getLastUpdateTime()
    {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public int getBillType()
    {
        return this.billType;
    }

    public void setBillType(int billType)
    {
        this.billType = billType;
    }

    public int getBillProjectType()
    {
        return this.billProjectType;
    }

    public void setBillProjectType(int billProjectType)
    {
        this.billProjectType = billProjectType;
    }
}

