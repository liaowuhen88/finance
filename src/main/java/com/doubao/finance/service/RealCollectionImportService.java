package com.doubao.finance.service;

import com.doubao.finance.util.ajax.JsonResponse;
import java.io.InputStream;
import org.springframework.stereotype.Service;

@Service
public abstract interface RealCollectionImportService
{
    public abstract JsonResponse importRealCollectionFromExcelNew(InputStream paramInputStream, String paramString)
            throws Exception;
}
