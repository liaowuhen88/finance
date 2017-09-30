package com.doubao.finance.dao;

import com.doubao.finance.model.request.RealCollectionQueryCondition;
import com.doubao.finance.pojo.RealCollection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface RealCollectionDao
{
    public abstract void insertRealCollection(RealCollection paramRealCollection);

    public abstract List<RealCollection> queryByCondition(RealCollectionQueryCondition paramRealCollectionQueryCondition);

    public abstract RealCollection queryById(Long paramLong);

    public abstract void update(RealCollection paramRealCollection);

    public abstract void batchInsertRealCollection(List<RealCollection> paramList);
}
