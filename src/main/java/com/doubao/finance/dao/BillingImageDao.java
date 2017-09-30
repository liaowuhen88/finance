package com.doubao.finance.dao;

import com.doubao.finance.pojo.BillingImage;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface BillingImageDao
{
    public abstract void insertBillingImage(BillingImage paramBillingImage);

    public abstract BillingImage queryById(Long paramLong);

    public abstract List<BillingImage> queryByBillingId(Long paramLong);

    public abstract void delete(Long paramLong);
}
