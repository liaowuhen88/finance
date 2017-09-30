package com.doubao.finance.util.excel.exception;

public class ExcelParseException
        extends Exception
{
    public ExcelParseException(String message)
    {
        super(message);
    }

    public ExcelParseException(String message, Exception e)
    {
        super(message, e);
    }
}
