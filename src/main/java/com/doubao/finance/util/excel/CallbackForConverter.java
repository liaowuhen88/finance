package com.doubao.finance.util.excel;

import java.util.List;
import java.util.Map;

public abstract interface CallbackForConverter<T>
{
    public abstract boolean accecp(Map<String, String> paramMap, int paramInt);

    public abstract void beforeEachRow(Map<String, String> paramMap, int paramInt);

    public abstract void onFinish(List<T> paramList, List<ErrorTemplate> paramList1, List<Map<String, String>> paramList2);
}
