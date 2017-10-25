package com.doubao.finance.pojo;

import com.doubao.finance.enums.BillingType;
import com.doubao.finance.enums.ProjectType;
import com.doubao.finance.util.BaseModel;
import com.doubao.finance.util.excel.annotation.ExportEntity;
import com.doubao.finance.util.excel.annotation.ExportField;

import java.math.BigDecimal;
import java.util.Date;

@ExportEntity(sheetName = "开票信息")
public class Billing extends BaseModel {
    private Long id;
    @ExportField(title = "流水号")
    private String billingSerialNumber;
    @ExportField(title = "发票号")
    private String billingNumber;
    private Long chargeEntityId;
    @ExportField(title = "结算主体")
    private String chargeEntityName;
    @ExportField(title = "开票金额")
    private BigDecimal billingMoney;
    @ExportField(title = "发票类型", refer = BillingType.class)
    private int billType;
    @ExportField(title = "发票项目", refer = ProjectType.class)
    private int billProjectType;
    @ExportField(title = "开票日期")
    private String billingDate;
    @ExportField(title = "申请人")
    private String applicant;
    @ExportField(title = "影像张数")
    private int billingImgCount;
    @ExportField(title = "备注")
    private String remark;
    private int isDel;
    @ExportField(title = "最后修改人")
    private String lastUpdater;
    private Date createTime;
    @ExportField(title = "最后修改时间")
    private Date lastUpdateTime;

    private String billTypeName;

    public Billing() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isImg() {
        return this.billingImgCount > 0;
    }

    public String getBillingSerialNumber() {
        return this.billingSerialNumber;
    }

    public void setBillingSerialNumber(String billingSerialNumber) {
        this.billingSerialNumber = billingSerialNumber;
    }

    public Long getChargeEntityId() {
        return this.chargeEntityId;
    }

    public void setChargeEntityId(Long chargeEntityId) {
        this.chargeEntityId = chargeEntityId;
    }

    public String getChargeEntityName() {
        return this.chargeEntityName;
    }

    public void setChargeEntityName(String chargeEntityName) {
        this.chargeEntityName = chargeEntityName;
    }

    public BigDecimal getBillingMoney() {
        return this.billingMoney;
    }

    public void setBillingMoney(BigDecimal billingMoney) {
        this.billingMoney = billingMoney;
    }

    public String getBillingDate() {
        return this.billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public String getApplicant() {
        return this.applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public int getBillingImgCount() {
        return this.billingImgCount;
    }

    public void setBillingImgCount(int billingImgCount) {
        this.billingImgCount = billingImgCount;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsDel() {
        return this.isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getLastUpdater() {
        return this.lastUpdater;
    }

    public void setLastUpdater(String lastUpdater) {
        this.lastUpdater = lastUpdater;
    }

    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getBillType() {
        return this.billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
        this.billTypeName = BillingType.valueOf(this.getBillType());
    }

    public int getBillProjectType() {
        return this.billProjectType;
    }

    public void setBillProjectType(int billProjectType) {
        this.billProjectType = billProjectType;
    }

    public String getBillingNumber() {
        return billingNumber;
    }

    public void setBillingNumber(String billingNumber) {
        this.billingNumber = billingNumber;
    }

    public String getBillTypeName() {
        return billTypeName;
    }

    public void setBillTypeName(String billTypeName) {
        this.billTypeName = billTypeName;
    }
}
