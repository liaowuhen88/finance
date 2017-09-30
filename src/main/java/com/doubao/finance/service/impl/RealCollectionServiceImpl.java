package com.doubao.finance.service.impl;

import com.doubao.finance.dao.RealCollectionDao;
import com.doubao.finance.enums.DataStatus;
import com.doubao.finance.model.PageResponse;
import com.doubao.finance.model.request.RealCollectionQueryCondition;
import com.doubao.finance.pojo.RealCollection;
import com.doubao.finance.service.RealCollectionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor={Exception.class})
public class RealCollectionServiceImpl
        implements RealCollectionService
{
    @Autowired
    private RealCollectionDao realCollectionDao;

    public RealCollection save(RealCollection realCollection)
    {
        realCollection.setCreateTime(new Timestamp(System.currentTimeMillis()));
        realCollection.setIsDel(DataStatus.NORMAL.getCode());
        this.realCollectionDao.insertRealCollection(realCollection);
        return realCollection;
    }

    public PageResponse<RealCollection> queryByCondition(RealCollectionQueryCondition condition)
    {
        Page<Object> page = PageHelper.startPage(condition.getStart() / condition.getPageSize() + 1, condition.getPageSize());
        List<RealCollection> realCollections = this.realCollectionDao.queryByCondition(condition);
        if (realCollections == null) {
            return new PageResponse(condition.getDraw());
        }
        PageResponse<RealCollection> pageResponse = new PageResponse(condition.getDraw(), page.getTotal(), realCollections);
        return pageResponse;
    }

    public RealCollection queryById(Long id)
    {
        return this.realCollectionDao.queryById(id);
    }

    public void update(RealCollection realCollection)
    {
        this.realCollectionDao.update(realCollection);
    }

    public void batchSave(List<RealCollection> list)
    {
        this.realCollectionDao.batchInsertRealCollection(list);
    }

    public List<RealCollection> exportByCondition(RealCollectionQueryCondition condition)
    {
        return this.realCollectionDao.queryByCondition(condition);
    }
}
