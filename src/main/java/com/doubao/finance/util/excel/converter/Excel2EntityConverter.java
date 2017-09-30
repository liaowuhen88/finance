package com.doubao.finance.util.excel.converter;

import com.doubao.finance.util.excel.exception.ExcelConvertException;
import java.util.List;
import java.util.Map;

public abstract interface Excel2EntityConverter<T>
{
    public abstract List<T> convert(List<Map<String, String>> paramList)
            throws ExcelConvertException;
}
