package com.doubao.finance.service.impl;

import com.doubao.finance.enums.DataStatus;
import com.doubao.finance.model.RealCollectionForImport;
import com.doubao.finance.pojo.RealCollection;
import com.doubao.finance.util.ajax.JsonResponse;
import com.doubao.finance.util.ajax.JsonResponseBuilder;
import com.doubao.finance.util.ajax.ResponseCode;
import com.doubao.finance.util.excel.CallbackForConverter;
import com.doubao.finance.util.excel.ErrorTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by liaowuhen on 2017/9/30.
 */
public class CallbackForConverterImpl implements CallbackForConverter<RealCollectionForImport> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallbackForConverterImpl.class);

    private RealCollectionImportServiceImpl realCollectionImportService;
    private JsonResponse[] jrs;
    private Map map;
    private String name;

    CallbackForConverterImpl(RealCollectionImportServiceImpl realCollectionImportService,JsonResponse[] jrs, Map map, String name) {
        this.realCollectionImportService = realCollectionImportService;
        this.jrs = jrs;
        this.map = map;
        this.name = name;
    }

    @Override
    public boolean accecp(Map rowData, int paramInt) {
        if (rowData.containsKey("贷方发生额") && rowData.containsKey("借方发生额")) {
            String 贷方发生额 = (String) rowData.get("贷方发生额");
            String 借方发生额 = (String) rowData.get("借方发生额");
            if ((new BigDecimal(借方发生额)).compareTo(BigDecimal.ZERO) > 0 && (new BigDecimal(贷方发生额)).compareTo(BigDecimal.ZERO) <= 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void beforeEachRow(Map rowData, int row) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("processing row: {} ,data:{}", Integer.valueOf(row), rowData);
        }

        String 贷方发生额;
        if (rowData.containsKey("借方发生额")) {
            贷方发生额 = (String) rowData.get("借方发生额");
            if (StringUtils.isBlank(贷方发生额)) {
                rowData.put("借方发生额", "0");
            }
        }

        if (rowData.containsKey("贷方发生额")) {
            贷方发生额 = (String) rowData.get("贷方发生额");
            if (StringUtils.isBlank(贷方发生额)) {
                rowData.put("贷方发生额", "0");
            }
        }
    }

    @Override
    public void onFinish(List<RealCollectionForImport> list, List<ErrorTemplate> errorTemplates, List<Map<String, String>> source) {
        if (!errorTemplates.isEmpty()) {
            Iterator realCollections1 = errorTemplates.iterator();

            while (realCollections1.hasNext()) {
                ErrorTemplate template1 = (ErrorTemplate) realCollections1.next();
                LOGGER.warn(template1.toString());
            }

            this.jrs[0] = JsonResponseBuilder.buildErrorJsonResponse(ResponseCode.ERROR_PARAMETER, errorTemplates.toString());
        } else {
            ArrayList realCollections = new ArrayList(list.size());
            Iterator template = list.iterator();

            while (template.hasNext()) {
                RealCollectionForImport realCollectionForImport = (RealCollectionForImport) template.next();
                String serialNumber = realCollectionForImport.getSerialNumber();
                if (StringUtils.isBlank(serialNumber) || "0".equals(serialNumber)) {
//                    RealCollectionImportServiceImpl.access$100(this.rms).generateSerialNumber("finance_system_manual");
                    realCollectionForImport.setSerialNumber(realCollectionImportService.getSerialNumberService().generateSerialNumber("finance_system_manual"));
                }

                if (!this.map.containsKey(realCollectionForImport.getChargeEntityName())) {
                    String realCollection1 = "数据库中不存在结算主体： " + realCollectionForImport.getChargeEntityName();
                    this.jrs[0] = JsonResponseBuilder.buildErrorJsonResponse(ResponseCode.ERROR_PARAMETER, realCollection1);
                    return;
                }

                realCollectionForImport.setChargeEntityId((Long) this.map.get(realCollectionForImport.getChargeEntityName()));
                RealCollection realCollection = realCollectionImportService.transferTo( realCollectionForImport);
                if (realCollection == null) {
                    this.jrs[0] = JsonResponseBuilder.buildErrorJsonResponse(ResponseCode.ERROR_PARAMETER, ResponseCode.ERROR_PARAMETER.getMessage());
                    return;
                }

                realCollection.setIsDel(DataStatus.NORMAL.getCode());
                realCollection.setOperator(this.name);
                realCollections.add(realCollection);
            }

            realCollectionImportService.getRealCollectionService().batchSave(realCollections);
        }
    }
}
