package com.doubao.finance.service.impl;

import com.doubao.finance.dao.BillingDao;
import com.doubao.finance.enums.DataStatus;
import com.doubao.finance.model.PageResponse;
import com.doubao.finance.model.request.BillingQueryCondition;
import com.doubao.finance.pojo.Billing;
import com.doubao.finance.service.BillingService;
import com.doubao.finance.service.SerialNumberService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class BillingServiceImpl
        implements BillingService
{
    @Autowired
    private BillingDao billingDao;
    @Autowired
    private SerialNumberService serialNumberService;

    public Billing save(Billing billing)
    {
        billing.setIsDel(DataStatus.NORMAL.getCode());
        billing.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        billing.setBillingSerialNumber(this.serialNumberService.generateSerialNumber("finance_system"));
        this.billingDao.insertBilling(billing);
        return billing;
    }

    public PageResponse<Billing> queryByCondition(BillingQueryCondition condition)
    {
        Page<Object> page = PageHelper.startPage(condition.getStart() / condition.getPageSize() + 1, condition.getPageSize());
        List<Billing> realCollections = this.billingDao.queryByCondition(condition);
        if (realCollections == null) {
            return new PageResponse(condition.getDraw());
        }
        PageResponse<Billing> pageResponse = new PageResponse(condition.getDraw(), page.getTotal(), realCollections);
        return pageResponse;
    }

    public Billing queryById(Long id)
    {
        return this.billingDao.queryById(id);
    }

    public void update(Billing billing)
    {
        billing.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        this.billingDao.update(billing);
    }

    public List<Billing> exportByCondition(BillingQueryCondition condition)
    {
        List<Billing> realCollections = this.billingDao.queryByCondition(condition);
        return realCollections;
    }
}
