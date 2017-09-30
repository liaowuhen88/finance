package com.doubao.finance.dao;

import com.doubao.finance.pojo.SerialNumberRule;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface SerialNumberRuleDao
{
    public abstract void insertSerialNumberRule(SerialNumberRule paramSerialNumberRule);

    public abstract SerialNumberRule queryById(Long paramLong);

    public abstract SerialNumberRule queryByBusinessName(String paramString);
}

