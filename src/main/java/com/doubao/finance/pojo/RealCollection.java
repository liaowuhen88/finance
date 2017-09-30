//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.doubao.finance.pojo;

import com.doubao.finance.util.BaseModel;
import com.doubao.finance.util.excel.annotation.ExportEntity;
import com.doubao.finance.util.excel.annotation.ExportField;
import java.math.BigDecimal;
import java.util.Date;

@ExportEntity(
        sheetName = "实收款信息导出"
)
public class RealCollection extends BaseModel {
    private Long id;
    @ExportField(
            title = "流水号"
    )
    private String serialNumber;
    @ExportField(
            title = "回款日期"
    )
    private String receiveDate;
    @ExportField(
            title = "金额"
    )
    private BigDecimal money;
    private Long chargeEntityId;
    @ExportField(
            title = "结算实体"
    )
    private String chargeEntityName;
    @ExportField(
            title = "摘要"
    )
    private String summary;
    @ExportField(
            title = "用途"
    )
    private String usage;
    @ExportField(
            title = "备注"
    )
    private String remark;
    @ExportField(
            title = "付款人户名"
    )
    private String payerAccountName;
    @ExportField(
            title = "付款人账号"
    )
    private String payerAccountNumber;
    @ExportField(
            title = "付款人开户行"
    )
    private String payerSourceAccount;
    @ExportField(
            title = "收款人户名"
    )
    private String payeeAccountName;
    @ExportField(
            title = "收款人账号"
    )
    private String payeeAccountNumber;
    private String payeeSourceAccount;
    private Date createTime;
    @ExportField(
            title = "最后更新时间"
    )
    private Date lastUpdateTime;
    @ExportField(
            title = "操作人"
    )
    private String operator;
    private int isDel;

    public RealCollection() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getReceiveDate() {
        return this.receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public BigDecimal getMoney() {
        return this.money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getChargeEntityId() {
        return this.chargeEntityId;
    }

    public void setChargeEntityId(Long chargeEntityId) {
        this.chargeEntityId = chargeEntityId;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUsage() {
        return this.usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayerAccountName() {
        return this.payerAccountName;
    }

    public void setPayerAccountName(String payerAccountName) {
        this.payerAccountName = payerAccountName;
    }

    public String getPayerAccountNumber() {
        return this.payerAccountNumber;
    }

    public void setPayerAccountNumber(String payerAccountNumber) {
        this.payerAccountNumber = payerAccountNumber;
    }

    public String getPayerSourceAccount() {
        return this.payerSourceAccount;
    }

    public void setPayerSourceAccount(String payerSourceAccount) {
        this.payerSourceAccount = payerSourceAccount;
    }

    public String getPayeeAccountName() {
        return this.payeeAccountName;
    }

    public void setPayeeAccountName(String payeeAccountName) {
        this.payeeAccountName = payeeAccountName;
    }

    public String getPayeeAccountNumber() {
        return this.payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public String getPayeeSourceAccount() {
        return this.payeeSourceAccount;
    }

    public void setPayeeSourceAccount(String payeeSourceAccount) {
        this.payeeSourceAccount = payeeSourceAccount;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getChargeEntityName() {
        return this.chargeEntityName;
    }

    public void setChargeEntityName(String chargeEntityName) {
        this.chargeEntityName = chargeEntityName;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getIsDel() {
        return this.isDel;
    }

    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
