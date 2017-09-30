package com.doubao.finance.service.impl;

import com.doubao.finance.dao.BillingImageDao;
import com.doubao.finance.pojo.BillingImage;
import com.doubao.finance.service.BillingImageService;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillingImageServiceImpl
        implements BillingImageService
{
    @Autowired
    private BillingImageDao billingImageDao;

    public BillingImage save(BillingImage billingImage)
    {
        billingImage.setUploadTime(new Timestamp(System.currentTimeMillis()));
        this.billingImageDao.insertBillingImage(billingImage);
        return billingImage;
    }

    public BillingImage queryById(Long id)
    {
        return this.billingImageDao.queryById(id);
    }

    public List<BillingImage> queryByBillingId(Long billingId)
    {
        return this.billingImageDao.queryByBillingId(billingId);
    }

    public void delete(BillingImage realCollection)
    {
        this.billingImageDao.delete(realCollection.getId());
    }
}
