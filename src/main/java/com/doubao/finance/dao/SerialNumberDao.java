package com.doubao.finance.dao;

import com.doubao.finance.pojo.SerialNumber;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface SerialNumberDao
{
    public abstract void insertSerialNumber(SerialNumber paramSerialNumber);

    public abstract SerialNumber queryById(Long paramLong);

    public abstract SerialNumber queryByBusinessNameAndDay(@Param("businessName") String paramString1, @Param("day") String paramString2);

    public abstract void updateSerialNumber(SerialNumber paramSerialNumber);

    public abstract void clearAll();
}

