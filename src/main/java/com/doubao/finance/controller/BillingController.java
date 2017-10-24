package com.doubao.finance.controller;

import com.doubao.finance.enums.DataStatus;
import com.doubao.finance.model.PageResponse;
import com.doubao.finance.model.request.BillingQueryCondition;
import com.doubao.finance.pojo.Billing;
import com.doubao.finance.pojo.BillingImage;
import com.doubao.finance.service.BillingImageService;
import com.doubao.finance.service.BillingService;
import com.doubao.finance.util.ajax.JsonResponse;
import com.doubao.finance.util.ajax.JsonResponseBuilder;
import com.doubao.finance.util.ajax.ResponseCode;
import com.doubao.finance.view.BillingExportView;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/billing"})
public class BillingController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(BillingController.class);
    @Autowired
    private BillingService billingService;
    @Autowired
    private BillingImageService billingImageService;

    @ResponseBody
    @RequestMapping(value={"/query"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public PageResponse list(HttpServletRequest request, BillingQueryCondition queryCondition)
    {
        try
        {
            return this.billingService.queryByCondition(queryCondition);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return new PageResponse(queryCondition.getDraw());
    }

    @RequestMapping(value={"/edit/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toEdit(HttpServletRequest request, @PathVariable("id") Long id)
    {
        ModelAndView mv = new ModelAndView("billing/editBilling");
        Billing billing = this.billingService.queryById(id);
        mv.addObject("billing", billing);
        return mv;
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public JsonResponse<Billing> edit(HttpServletRequest request, Billing billing)
    {
        if (billing == null) {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_PARAMETER);
        }
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        if (principal == null) {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_USER_NOT_LOGIN);
        }
        billing.setLastUpdater(principal.getName());
        try
        {
            this.billingService.update(billing);
            return JsonResponseBuilder.buildSuccessJsonResponse(billing);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public JsonResponse<Billing> add(HttpServletRequest request, Billing billing)
    {
        try
        {
            /*AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
            if (principal == null) {
                return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_USER_NOT_LOGIN);
            }
            billing.setLastUpdater(principal.getName());*/
            billing.setLastUpdater("principal");
            Billing newBilling = this.billingService.save(billing);
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
    public JsonResponse<Billing> del(HttpServletRequest request, @RequestParam(value="id", required=true) Long id)
    {
        if ((id == null) || (id.longValue() <= 0L)) {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_PARAMETER);
        }
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        if (principal == null) {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_USER_NOT_LOGIN);
        }
        try
        {
            Billing billing = this.billingService.queryById(id);
            if (billing == null) {
                return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_ENTITY_NOT_EXISTS);
            }
            billing.setIsDel(DataStatus.DELETED.getCode());
            billing.setLastUpdater(principal.getName());
            this.billingService.update(billing);

            return JsonResponseBuilder.buildSuccessJsonResponseWithoutData();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
    }

    @ResponseBody
    @RequestMapping({"/image/{billingId}"})
    public JsonResponse<BillingImage> loadBillingImages(@PathVariable("billingId") Long billingId)
    {
        List<BillingImage> billingImages = this.billingImageService.queryByBillingId(billingId);
        if (billingImages == null) {
            billingImages = new ArrayList();
        }
        return JsonResponseBuilder.buildSuccessJsonResponse(billingImages);
    }

    @RequestMapping({"/export"})
    public ModelAndView export2Excel(HttpServletRequest request, BillingQueryCondition queryCondition, Map modelMap)
    {
        List billingList = this.billingService.exportByCondition(queryCondition);
        if (billingList != null && !billingList.isEmpty()) {
            String fileName = "开票信息导出.xlsx";
            if (StringUtils.isNotBlank(queryCondition.getFromBillingDate()) && StringUtils.isNotBlank(queryCondition.getToBillingDate())) {
                fileName = "开票信息导出(%s至%s).xlsx";
                fileName = String.format(fileName, new Object[]{queryCondition.getFromBillingDate(), queryCondition.getToBillingDate()});
            }

            modelMap.put("title", fileName);
            modelMap.put("data", billingList);
            return new ModelAndView(new BillingExportView(Billing.class), modelMap);
        } else {
            modelMap.put("message", "无数据");
            return new ModelAndView("error", modelMap);
        }
    }
}
