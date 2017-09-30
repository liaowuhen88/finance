//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.doubao.finance.model;

import com.doubao.finance.util.BaseModel;
import com.doubao.finance.util.excel.annotation.ExcelColumnMapping;
import com.doubao.finance.util.excel.annotation.ExcelEntity;
import com.doubao.finance.util.excel.validator.RealCollectionRuleValidator;
import java.math.BigDecimal;
import java.util.Date;

@ExcelEntity(
        maxNumOfError = 10,
        validator = RealCollectionRuleValidator.class
)
public class RealCollectionForImport extends BaseModel {
    private Long id;
    @ExcelColumnMapping(
            column = "凭证号",
            required = false
    )
    private String serialNumber;
    @ExcelColumnMapping(
            column = "日期",
            required = true
    )
    private String receiveDate;
    @ExcelColumnMapping(
            column = "贷方发生额",
            defaultValue = "0"
    )
    private BigDecimal money;
    private Long chargeEntityId;
    @ExcelColumnMapping(
            column = "结算主体",
            required = true
    )
    private String chargeEntityName;
    @ExcelColumnMapping(
            column = "摘要"
    )
    private String summary;
    @ExcelColumnMapping(
            column = "用途"
    )
    private String usage;
    @ExcelColumnMapping(
            column = "备注"
    )
    private String remark;
    @ExcelColumnMapping(
            column = "对方户名",
            required = true
    )
    private String payerAccountName;
    @ExcelColumnMapping(
            column = "对方账号",
            required = true
    )
    private String payerAccountNumber;
    @ExcelColumnMapping(
            column = "对方开户银行"
    )
    private String payerSourceAccount;
    @ExcelColumnMapping(
            column = "我方户名",
            required = true
    )
    private String payeeAccountName;
    @ExcelColumnMapping(
            column = "我方账号",
            required = true
    )
    private String payeeAccountNumber;
    @ExcelColumnMapping(
            column = "我方开户银行",
            required = true
    )
    private String payeeSourceAccount;
    private Date createTime;
    private Date lastUpdateTime;
    private String operator;
    private int isDel;

    public RealCollectionForImport() {
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
