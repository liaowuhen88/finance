package com.doubao.finance.dao;

import com.doubao.finance.model.request.BillingQueryCondition;
import com.doubao.finance.pojo.Billing;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BillingDao
{
    public abstract void insertBilling(Billing paramBilling);

    public abstract List<Billing> queryByCondition(BillingQueryCondition paramBillingQueryCondition);

    public abstract Billing queryById(Long paramLong);

    public abstract void update(Billing paramBilling);

    public abstract long echo();
}

