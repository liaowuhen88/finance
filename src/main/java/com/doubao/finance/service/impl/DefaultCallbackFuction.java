package com.doubao.finance.service.impl;


import com.wzg.xls.tools.tools.ExcelFileHelper;
import com.wzg.xls.tools.tools.ICallbackFunction;

import java.util.List;

public class DefaultCallbackFuction
        implements ICallbackFunction{

    @Override
    public void onFailure(ExcelFileHelper excelFileHelper, Exception e) throws Exception {

    }

    @Override
    public void onSuccess(ExcelFileHelper excelFileHelper, List list) throws Exception {

    }

    @Override
    public void before(ExcelFileHelper excelFileHelper) throws Exception {

    }

    @Override
    public void after(ExcelFileHelper excelFileHelper, List list) throws Exception {

    }
}
