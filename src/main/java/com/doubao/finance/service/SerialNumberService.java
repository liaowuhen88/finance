package com.doubao.finance.service;

import com.doubao.finance.pojo.SerialNumberRule;

public abstract interface SerialNumberService
{
    public abstract String generateSerialNumber(String paramString);

    public abstract SerialNumberRule saveSerialNumberRule(SerialNumberRule paramSerialNumberRule);
}
