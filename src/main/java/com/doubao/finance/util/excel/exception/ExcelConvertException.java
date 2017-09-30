package com.doubao.finance.util.excel.exception;

public class ExcelConvertException
        extends Exception
{
    public ExcelConvertException(String message)
    {
        super(message);
    }

    public ExcelConvertException(String message, Exception e)
    {
        super(message, e);
    }
}
