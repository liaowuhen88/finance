package com.doubao.finance.service;

import com.doubao.finance.model.PageResponse;
import com.doubao.finance.model.request.BillingQueryCondition;
import com.doubao.finance.pojo.Billing;
import java.util.List;

public abstract interface BillingService
{
    public abstract Billing save(Billing paramBilling);

    public abstract PageResponse<Billing> queryByCondition(BillingQueryCondition paramBillingQueryCondition);

    public abstract Billing queryById(Long paramLong);

    public abstract void update(Billing paramBilling);

    public abstract List<Billing> exportByCondition(BillingQueryCondition paramBillingQueryCondition);
}
