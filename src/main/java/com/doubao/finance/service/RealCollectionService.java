package com.doubao.finance.service;

import com.doubao.finance.model.PageResponse;
import com.doubao.finance.model.request.RealCollectionQueryCondition;
import com.doubao.finance.pojo.RealCollection;
import java.util.List;

public abstract interface RealCollectionService
{
    public abstract RealCollection save(RealCollection paramRealCollection);

    public abstract PageResponse<RealCollection> queryByCondition(RealCollectionQueryCondition paramRealCollectionQueryCondition);

    public abstract RealCollection queryById(Long paramLong);

    public abstract void update(RealCollection paramRealCollection);

    public abstract void batchSave(List<RealCollection> paramList);

    public abstract List<RealCollection> exportByCondition(RealCollectionQueryCondition paramRealCollectionQueryCondition);
}
