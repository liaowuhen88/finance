package com.doubao.finance.api;

import com.doubao.finance.model.PageResponse;
import com.doubao.finance.model.request.BillingQueryCondition;
import com.doubao.finance.model.request.RealCollectionQueryCondition;
import com.doubao.finance.pojo.Billing;
import com.doubao.finance.pojo.RealCollection;
import com.doubao.finance.service.BillingService;
import com.doubao.finance.service.RealCollectionService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/realbilling"})
public class ValidateAPI
{
    public static final Logger LOGGER = LoggerFactory.getLogger(ValidateAPI.class);
    @Autowired
    private RealCollectionService realCollectionService;
    @Autowired
    private BillingService billingService;

    @RequestMapping(value={"/chargeEntity"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public void queryChargeEntities(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="chargeEntityId", defaultValue="") Long chargeEntityId, @RequestParam(value="callback", required=true) String callback)
    {
        RealCollectionQueryCondition condition = new RealCollectionQueryCondition();
        condition.setChargeEntityId(chargeEntityId);
        Map<String, Boolean> result = new HashMap();
        result.put("result", Boolean.valueOf(false));

        PageResponse<RealCollection> collectionPageResponse = this.realCollectionService.queryByCondition(condition);
        if ((collectionPageResponse != null) && (collectionPageResponse.getData() != null) && (!collectionPageResponse.getData().isEmpty()))
        {
            result.put("result", Boolean.valueOf(true));
        }
        else
        {
            BillingQueryCondition condition1 = new BillingQueryCondition();
            condition1.setChargeEntityId(chargeEntityId);
            PageResponse<Billing> billingPageResponse = this.billingService.queryByCondition(condition1);
            if ((billingPageResponse != null) && (billingPageResponse.getData() != null) && (!billingPageResponse.getData().isEmpty())) {
                result.put("result", Boolean.valueOf(true));
            }
        }
        String totalResult = callback + "({\"result\":" + result.get("result") + "})";
        try
        {
            response.getWriter().write(totalResult);
        }
        catch (IOException e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
