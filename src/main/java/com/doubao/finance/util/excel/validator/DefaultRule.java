package com.doubao.finance.util.excel.validator;

import org.apache.commons.collections4.map.UnmodifiableMap;

public class DefaultRule
        implements ExcelRuleValidator
{
    public void setData(Object data) {}

    public void check(UnmodifiableMap<String, String> rowData, int row, String columnName, String columnValue) {}
}
