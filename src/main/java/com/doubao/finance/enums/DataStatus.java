package com.doubao.finance.enums;

public enum DataStatus
{
    NORMAL(1, "����"),  DELETED(0, "������");

    private int code;
    private String message;

    private DataStatus(int code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public int getCode()
    {
        return this.code;
    }

    public String getMessage()
    {
        return this.message;
    }
}