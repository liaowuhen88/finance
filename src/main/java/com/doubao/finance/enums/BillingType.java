package com.doubao.finance.enums;

public enum BillingType
{
    VALUE_ADDED_TAX_SPECIAL_INVOICE(1, "新增税普通发票"),  VALUE_ADDED_TAX_INVOICE(2, "新增税专用发票"),  UNKNOWN(-1, "未知发票类型");

    private int code;
    private String text;

    private BillingType(int code, String text)
    {
        this.code = code;
        this.text = text;
    }

    public String getText()
    {
        return this.text;
    }

    public int getCode()
    {
        return this.code;
    }

    public static String valueOf(int code)
    {
        for (BillingType billingType : BillingType.values()) {
            if (billingType.getCode() == code) {
                return billingType.getText();
            }
        }
        return UNKNOWN.getText();
    }
}

