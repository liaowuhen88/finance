package com.doubao.finance.service;

import com.doubao.finance.pojo.BillingImage;
import java.util.List;

public abstract interface BillingImageService
{
    public abstract BillingImage save(BillingImage paramBillingImage);

    public abstract BillingImage queryById(Long paramLong);

    public abstract List<BillingImage> queryByBillingId(Long paramLong);

    public abstract void delete(BillingImage paramBillingImage);
}
