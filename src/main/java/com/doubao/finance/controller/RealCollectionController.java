//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/realCollection"})
public class RealCollectionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RealCollectionController.class);
    @Value("${api.chargeentity.url}")
    private String chargeEntityApiUrl;
    @Autowired
    private RealCollectionService realCollectionService;
    @Autowired
    private RealCollectionImportService realCollectionImportService;

    public RealCollectionController() {
    }

    @ModelAttribute
    private void setContextAttributes() {
        ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        if(servletContext != null && servletContext.getAttribute("chargeEntityApiUrl") == null) {
            servletContext.setAttribute("chargeEntityApiUrl", this.chargeEntityApiUrl);
        }

    }

    @RequestMapping(
            value = {"/list"},
            method = {RequestMethod.GET}
    )
    public ModelAndView toListPage(HttpServletRequest request) {
        return new ModelAndView("realCollection/realCollectionList");
    }

    @ResponseBody
    @RequestMapping(
            value = {"/query"},
            method = {RequestMethod.GET}
    )
    public PageResponse list(HttpServletRequest request, RealCollectionQueryCondition queryCondition) {
        try {
            PageResponse e = this.realCollectionService.queryByCondition(queryCondition);
            return e;
        } catch (Exception var4) {
            LOGGER.error(var4.getMessage(), var4);
            return new PageResponse((long)queryCondition.getDraw());
        }
    }

    @RequestMapping(
            value = {"/edit/{id}"},
            method = {RequestMethod.GET}
    )
    public ModelAndView toEdit(HttpServletRequest request, @PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("realCollection/editRealCollection");
        RealCollection realCollection = this.realCollectionService.queryById(id);
        mv.addObject("realCollection", realCollection);
        return mv;
    }

    @ResponseBody
    @RequestMapping(
            value = {"/edit"},
            method = {RequestMethod.POST}
    )
    public JsonResponse<RealCollection> edit(HttpServletRequest request, RealCollection realCollection) {
        try {
            AttributePrincipal e = (AttributePrincipal)request.getUserPrincipal();
            if(null == e) {
                return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_USER_NOT_LOGIN);
            } else {
                realCollection.setOperator(e.getName());
                this.realCollectionService.update(realCollection);
                JsonResponse jsonResponse = JsonResponseBuilder.buildSuccessJsonResponse(realCollection);
                return jsonResponse;
            }
        } catch (Exception var5) {
            LOGGER.error(var5.getMessage(), var5);
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(
            value = {"/add"},
            method = {RequestMethod.POST}
    )
    public JsonResponse<RealCollection> add(HttpServletRequest request, RealCollection realCollection) {
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        if(null == principal) {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_USER_NOT_LOGIN);
        } else {
            realCollection.setOperator(principal.getName());

            try {
                RealCollection e = this.realCollectionService.save(realCollection);
                return JsonResponseBuilder.buildSuccessJsonResponse(e);
            } catch (Exception var5) {
                LOGGER.error(var5.getMessage(), var5);
                return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
            }
        }
    }

    @ResponseBody
    @RequestMapping(
            value = {"/del"},
            method = {RequestMethod.POST}
    )
    public JsonResponse<RealCollection> del(HttpServletRequest request, @RequestParam(
            value = "id",
            required = true
    ) Long id) {
        if(id != null && id.longValue() > 0L) {
            AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
            if(null == principal) {
                return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_USER_NOT_LOGIN);
            } else {
                try {
                    RealCollection e = this.realCollectionService.queryById(id);
                    if(e == null) {
                        return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_ENTITY_NOT_EXISTS);
                    } else {
                        e.setOperator(principal.getName());
                        e.setIsDel(DataStatus.DELETED.getCode());
                        this.realCollectionService.update(e);
                        return JsonResponseBuilder.buildSuccessJsonResponseWithoutData();
                    }
                } catch (Exception var5) {
                    LOGGER.error(var5.getMessage(), var5);
                    return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
                }
            }
        } else {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_PARAMETER);
        }
    }

    @ResponseBody
    @RequestMapping(
            value = {"/import"},
            method = {RequestMethod.POST}
    )
    public JsonResponse importRealCollection(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        if(principal == null) {
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_USER_NOT_LOGIN);
        } else {
            try {
                InputStream e = file.getInputStream();
                if(e == null) {
                    return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_PARAMETER);
                } else {
                    JsonResponse response = this.realCollectionImportService.importRealCollectionFromExcelNew(e, principal.getName());
                    return response;
                }
            } catch (Exception var6) {
                LOGGER.error(var6.getMessage(), var6);
                return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
            }
        }
    }

    @RequestMapping(
            value = {"/template/download"},
            method = {RequestMethod.GET}
    )
    public void downLoadTemplate(HttpServletRequest request, HttpServletResponse response) {
        String realPath = request.getServletContext().getRealPath("WEB-INF/实收付款导入模板.xls");
        File file = new File(realPath);
        if(file != null && file.exists()) {
            String fileName = null;

            try {
                fileName = new String("实收付款导入模板.xls".getBytes("gb2312"), "ISO8859-1");
            } catch (UnsupportedEncodingException var24) {
                LOGGER.error(var24.getMessage(), var24);
            }

            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;

            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                ServletOutputStream e = response.getOutputStream();

                for(int i = bis.read(buffer); i != -1; i = bis.read(buffer)) {
                    e.write(buffer, 0, i);
                }
            } catch (Exception var25) {
                LOGGER.error(var25.getMessage(), var25);
            } finally {
                if(bis != null) {
                    try {
                        bis.close();
                    } catch (IOException var23) {
                        LOGGER.error(var23.getMessage(), var23);
                    }
                }

                if(fis != null) {
                    try {
                        fis.close();
                    } catch (IOException var22) {
                        LOGGER.error(var22.getMessage(), var22);
                    }
                }

            }

        } else {
            LOGGER.error("file:{} not exist!", file.getPath());
        }
    }

    @RequestMapping({"/export"})
    public ModelAndView export2Excel(HttpServletRequest request, RealCollectionQueryCondition queryCondition, Map modelMap) {
        List billingList = this.realCollectionService.exportByCondition(queryCondition);
        if(billingList != null && !billingList.isEmpty()) {
            String fileName = "实收信息导出.xlsx";
            if(StringUtils.isNotBlank(queryCondition.getFromReceiveDate()) && StringUtils.isNotBlank(queryCondition.getToReceiveDate())) {
                fileName = "实收信息导出(%s至%s).xlsx";
                fileName = String.format(fileName, new Object[]{queryCondition.getFromReceiveDate(), queryCondition.getToReceiveDate()});
            }

            modelMap.put("title", fileName);
            modelMap.put("data", billingList);
            return new ModelAndView(new BillingExportView(RealCollection.class), modelMap);
        } else {
            modelMap.put("message", "无数据");
            return new ModelAndView("error", modelMap);
        }
    }
}
