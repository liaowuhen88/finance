package com.doubao.finance.util.excel.parser;

import com.doubao.finance.util.excel.exception.ExcelParseException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public abstract interface ExcelParser
{
    public abstract void parseExcel(String paramString, int paramInt)
            throws ExcelParseException;

    public abstract void parseExcel(InputStream paramInputStream, int paramInt)
            throws ExcelParseException;

    public abstract List<Map<String, String>> getData();

    public abstract List<String> getTitle();

    public abstract int getTotalRowNumber();

    public abstract int getTotalColumnNumber();
}
