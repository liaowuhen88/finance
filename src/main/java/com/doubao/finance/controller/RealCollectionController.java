package com.doubao.finance.controller;

import com.doubao.finance.enums.DataStatus;
import com.doubao.finance.model.PageResponse;
import com.doubao.finance.model.request.RealCollectionQueryCondition;
import com.doubao.finance.pojo.RealCollection;
import com.doubao.finance.service.RealCollectionImportService;
import com.doubao.finance.service.RealCollectionService;
import com.doubao.finance.util.ajax.JsonResponse;
import com.doubao.finance.util.ajax.JsonResponseBuilder;
import com.doubao.finance.util.ajax.ResponseCode;
import com.doubao.finance.view.BillingExportView;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/realCollection"})
public class RealCollectionController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RealCollectionController.class);
    @Value("${api.chargeentity.url}")
    private String chargeEntityApiUrl;
    @Autowired
    private RealCollectionService realCollectionService;
    @Autowired
    private RealCollectionImportService realCollectionImportService;

    @ModelAttribute
    private void setContextAttributes()
    {
        ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        if ((servletContext != null) &&
                (servletContext.getAttribute("chargeEntityApiUrl") == null)) {
            servletContext.setAttribute("chargeEntityApiUrl", this.chargeEntityApiUrl);
        }
    }

    @RequestMapping(value={"/list"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView toListPage(HttpServletRequest request)
    {
        return new ModelAndView("realCollection/realCollectionList");
    }

    @ResponseBody
    @RequestMapping(value={"/query"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public PageResponse list(HttpServletRequest request, RealCollectionQueryCondition queryCondition)
    {
        try
        {
            return this.realCollectionService.queryByCondition(queryCondition);
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
        ModelAndView mv = new ModelAndView("realCollection/editRealCollection");
        RealCollection realCollection = this.realCollectionService.queryById(id);
        mv.addObject("realCollection", realCollection);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public JsonResponse<RealCollection> edit(HttpServletRequest request, RealCollection realCollection)
    {
        try
        {
            AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
            if (null == principal) {
                return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_USER_NOT_LOGIN);
            }
            realCollection.setOperator(principal.getName());
            this.realCollectionService.update(realCollection);
            return JsonResponseBuilder.buildSuccessJsonResponse(realCollection);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
    }

    @ResponseBody
    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public JsonResponse<RealCollection> add(HttpServletRequest request, RealCollection realCollection)
    {
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        if (null == principal) {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_USER_NOT_LOGIN);
        }
        realCollection.setOperator(principal.getName());
        try
        {
            RealCollection collection = this.realCollectionService.save(realCollection);
            return JsonResponseBuilder.buildSuccessJsonResponse(collection);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
    }

    @ResponseBody
    @RequestMapping(value={"/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public JsonResponse<RealCollection> del(HttpServletRequest request, @RequestParam(value="id", required=true) Long id)
    {
        if ((id == null) || (id.longValue() <= 0L)) {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_PARAMETER);
        }
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        if (null == principal) {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_USER_NOT_LOGIN);
        }
        try
        {
            RealCollection realCollection = this.realCollectionService.queryById(id);
            if (realCollection == null) {
                return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_ENTITY_NOT_EXISTS);
            }
            realCollection.setOperator(principal.getName());
            realCollection.setIsDel(DataStatus.DELETED.getCode());
            this.realCollectionService.update(realCollection);

            return JsonResponseBuilder.buildSuccessJsonResponseWithoutData();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
    }

    @ResponseBody
    @RequestMapping(value={"/import"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public JsonResponse importRealCollection(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request)
    {
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        if (principal == null) {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_USER_NOT_LOGIN);
        }
        try
        {
            InputStream inputStream = file.getInputStream();
            if (inputStream == null) {
                return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_PARAMETER);
            }
            return this.realCollectionImportService.importRealCollectionFromExcelNew(inputStream, principal.getName());
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
    }

    @RequestMapping(value={"/template/download"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public void downLoadTemplate(HttpServletRequest request, HttpServletResponse response)
    {
        String realPath = request.getServletContext().getRealPath("WEB-INF/����������������.xls");
        File file = new File(realPath);
        if ((file == null) || (!file.exists()))
        {
            LOGGER.error("file:{} not exist!", file.getPath());
            return;
        }
        String fileName = null;
        try
        {
            fileName = new String("����������������.xls".getBytes("gb2312"), "ISO8859-1");
        }
        catch (UnsupportedEncodingException e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

        byte[] buffer = new byte['?'];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try
        {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1)
            {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            return;
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        finally
        {
            if (bis != null) {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (fis != null) {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    @RequestMapping({"/export"})
    public ModelAndView export2Excel(HttpServletRequest request, RealCollectionQueryCondition queryCondition, Map modelMap)
    {
        List<RealCollection> billingList = this.realCollectionService.exportByCondition(queryCondition);
        if ((billingList == null) || (billingList.isEmpty()))
        {
            modelMap.put("message", "������");
            return new ModelAndView("error", modelMap);
        }
        String fileName = "������������.xlsx";
        if ((StringUtils.isNotBlank(queryCondition.getFromReceiveDate())) && (StringUtils.isNotBlank(queryCondition.getToReceiveDate())))
        {
            fileName = "������������(%s��%s).xlsx";
            fileName = String.format(fileName, new Object[] { queryCondition.getFromReceiveDate(), queryCondition.getToReceiveDate() });
        }
        modelMap.put("title", fileName);
        modelMap.put("data", billingList);
        return new ModelAndView(new BillingExportView(RealCollection.class), modelMap);
    }
}
