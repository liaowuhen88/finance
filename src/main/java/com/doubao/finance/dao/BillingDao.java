package com.doubao.finance.dao;

import com.doubao.finance.model.request.BillingQueryCondition;
import com.doubao.finance.pojo.Billing;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingDao
{
    public abstract void insertBilling(Billing paramBilling);

    public abstract List<Billing> queryByCondition(BillingQueryCondition paramBillingQueryCondition);

    public abstract Billing queryById(Long paramLong);

    public abstract void update(Billing paramBilling);

    public abstract long echo();
}

