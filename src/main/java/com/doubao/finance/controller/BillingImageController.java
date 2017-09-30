package com.doubao.finance.controller;

import com.doubao.finance.pojo.BillingImage;
import com.doubao.finance.service.BillingImageService;
import com.doubao.finance.util.ajax.JsonResponse;
import com.doubao.finance.util.ajax.JsonResponseBuilder;
import com.doubao.finance.util.ajax.ResponseCode;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/billingImage"})
public class BillingImageController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(BillingImageController.class);
    @Autowired
    private BillingImageService billingImageService;

    @ResponseBody
    @RequestMapping(value={"/query"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public JsonResponse<BillingImage> list(HttpServletRequest request, @RequestParam("billingId") Long billingId)
    {
        try
        {
            List<BillingImage> list = this.billingImageService.queryByBillingId(billingId);

            return JsonResponseBuilder.buildSuccessJsonResponse(list);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
    }

    @RequestMapping(value={"/query/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public JsonResponse<BillingImage> toEdit(HttpServletRequest request, @PathVariable("id") Long id)
    {
        BillingImage billing = this.billingImageService.queryById(id);
        return JsonResponseBuilder.buildSuccessJsonResponse(billing);
    }

    @ResponseBody
    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public JsonResponse<BillingImage> add(HttpServletRequest request, BillingImage billingImage)
    {
        try
        {
            BillingImage newBilling = this.billingImageService.save(billingImage);
            return JsonResponseBuilder.buildSuccessJsonResponse(newBilling);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
    }

    @ResponseBody
    @RequestMapping(value={"/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public JsonResponse<BillingImage> del(HttpServletRequest request, @RequestParam(value="key", required=true) Long id)
    {
        if ((id == null) || (id.longValue() <= 0L)) {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_PARAMETER);
        }
        try
        {
            BillingImage billingImage = this.billingImageService.queryById(id);
            if (billingImage == null) {
                return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_ENTITY_NOT_EXISTS);
            }
            this.billingImageService.delete(billingImage);

            return JsonResponseBuilder.buildSuccessJsonResponseWithoutData();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
    }
}
