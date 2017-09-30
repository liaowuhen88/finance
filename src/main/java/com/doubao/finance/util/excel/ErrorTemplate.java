package com.doubao.finance.util.excel;

import java.io.Serializable;

public class ErrorTemplate
        implements Serializable
{
    private int row;
    private String column;
    private String errorMessage;

    public ErrorTemplate(int row, String column, String errorMessage)
    {
        this.row = row;
        this.column = column;
        this.errorMessage = errorMessage;
    }

    public int getRow()
    {
        return this.row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public String getColumn()
    {
        return this.column;
    }

    public void setColumn(String column)
    {
        this.column = column;
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String toString()
    {
        return String.format("行:%d 列 %s，提示 {%s}", new Object[] { Integer.valueOf(this.row), this.column, this.errorMessage });
    }
}
