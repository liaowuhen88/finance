package com.doubao.finance.util.excel.validator;

import com.doubao.finance.util.excel.exception.ExcelConvertException;
import org.apache.commons.collections4.map.UnmodifiableMap;

public abstract interface ExcelRuleValidator
{
    public abstract void setData(Object paramObject);

    public abstract void check(UnmodifiableMap<String, String> paramUnmodifiableMap, int paramInt, String paramString1, String paramString2)
            throws ExcelConvertException;
}
