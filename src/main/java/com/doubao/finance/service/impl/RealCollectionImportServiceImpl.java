package com.doubao.finance.service.impl;

import com.doubao.finance.model.RealCollectionForImport;
import com.doubao.finance.pojo.RealCollection;
import com.doubao.finance.service.RealCollectionImportService;
import com.doubao.finance.service.RealCollectionService;
import com.doubao.finance.service.SerialNumberService;
import com.doubao.finance.util.ExcelUtils;
import com.doubao.finance.util.HttpUtils;
import com.doubao.finance.util.JSONUtil;
import com.doubao.finance.util.ajax.JsonResponse;
import com.doubao.finance.util.ajax.JsonResponseBuilder;
import com.doubao.finance.util.ajax.ResponseCode;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RealCollectionImportServiceImpl
        implements RealCollectionImportService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RealCollectionImportServiceImpl.class);
    @Autowired
    private RealCollectionService realCollectionService;
    @Value("${api.chargeentity.query.url}")
    private String chargeEntityApiUrl;
    @Autowired
    private SerialNumberService serialNumberService;

    public JsonResponse importRealCollectionFromExcelNew(InputStream stream, String name)
            throws Exception
    {
        Map<String, Long> chargeEntitis = loadChargeEntitis();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.error("chargeEntity cache is {}", chargeEntitis);
        }
        if (chargeEntitis.isEmpty()) {
            return JsonResponseBuilder.buildErrorJsonResponse(ResponseCode.ERROR_SERVER_ERROR, "远程api无法访问");
        }
        JsonResponse[] response = { JsonResponseBuilder.buildSuccessJsonResponseWithoutData() };
        //ExcelUtils.xls2Entity(stream, RealCollectionForImport.class, new DefaultCallbackFuction.1(this, response, chargeEntitis, name), chargeEntitis);
        ExcelUtils.xls2Entity(stream, RealCollectionForImport.class, new CallbackForConverterImpl(this, response, chargeEntitis, name), chargeEntitis);

        return response[0];
    }

    public RealCollection transferTo(RealCollectionForImport realCollectionForImport)
    {
        RealCollection realCollection = new RealCollection();
        try
        {
            PropertyUtils.copyProperties(realCollection, realCollectionForImport);
            return realCollection;
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private Map<String, Long> loadChargeEntitis()
    {
        Map<String, Long> entityCache = new HashMap();
        try
        {
            String response = HttpUtils.get(this.chargeEntityApiUrl);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("response is :{}", response);
            }
            Map map = (Map)JSONUtil.fromJson(response, Map.class);
            List<Map> list = (List)map.get("aaData");
            for (Map item : list) {
                entityCache.put(item.get("name").toString(), Long.valueOf(Double.valueOf(item.get("id").toString()).longValue()));
            }
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return entityCache;
    }


    public RealCollectionService getRealCollectionService() {
        return realCollectionService;
    }

    public void setRealCollectionService(RealCollectionService realCollectionService) {
        this.realCollectionService = realCollectionService;
    }

    public String getChargeEntityApiUrl() {
        return chargeEntityApiUrl;
    }

    public void setChargeEntityApiUrl(String chargeEntityApiUrl) {
        this.chargeEntityApiUrl = chargeEntityApiUrl;
    }

    public SerialNumberService getSerialNumberService() {
        return serialNumberService;
    }

    public void setSerialNumberService(SerialNumberService serialNumberService) {
        this.serialNumberService = serialNumberService;
    }
}
