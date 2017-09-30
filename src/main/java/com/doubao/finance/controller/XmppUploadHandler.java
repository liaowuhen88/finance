package com.doubao.finance.controller;

import com.doubao.finance.model.ImgResponse;
import com.doubao.finance.service.OssService;
import com.doubao.finance.util.FileUtil;
import com.doubao.finance.util.ajax.JsonResponse;
import com.doubao.finance.util.ajax.JsonResponseBuilder;
import com.doubao.finance.util.ajax.ResponseCode;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"xmppUpload"})
public class XmppUploadHandler
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OssService ossService;

    @ResponseBody
    @RequestMapping(value={"up/{userType}/{fileType}/{id}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
    public JsonResponse uploadDj(HttpServletRequest request, HttpServletResponse hResponse, @PathVariable("userType") String userType, @PathVariable("fileType") String fileType, @PathVariable("id") String id, @RequestParam("file") MultipartFile multipartFile)
            throws Exception
    {
        String originalFilename = multipartFile.getOriginalFilename();
        if (!FileUtil.checkFileName(originalFilename))
        {
            this.logger.error("file name:{} incorrect!", originalFilename);
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_UPLOAD_FILE_FORMAT);
        }
        if (FileUtil.checkFileSize(multipartFile.getInputStream()))
        {
            this.logger.error("file:{},file size too large!", originalFilename);
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_UPLOAD_FILE_SIZE_TOO_LARGE);
        }
        ImgResponse imgResponse = new ImgResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        String upFileTime = sdf.format(new Date());

        String childFileType = "";
        if (id.contains("@"))
        {
            childFileType = id.substring(0, id.indexOf("@"));
            imgResponse.setChildFileType(childFileType);
            childFileType = childFileType + File.separator;
        }
        id = id.substring(id.indexOf("@") + 1, id.length()) + "_" + UUID.randomUUID() + FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String key = userType + File.separator + fileType + File.separator + childFileType + upFileTime + File.separator + id;
        try
        {
            this.ossService.uploadDJFile(key, multipartFile.getInputStream(), "application/octet-stream", false);
        }
        catch (Exception e)
        {
            this.logger.error(e.getMessage(), e);
            return JsonResponseBuilder.buildErrorJsonResponseWithoutData(ResponseCode.ERROR_SERVER_ERROR);
        }
        if (this.logger.isInfoEnabled()) {
            this.logger.info("file : {},upload success,key:{}", originalFilename, key);
        }
        imgResponse.setFileType(fileType);
        imgResponse.setId(id);
        imgResponse.setDate(upFileTime);
        imgResponse.setUserType(userType);
        imgResponse.setSrc(key);
        return JsonResponseBuilder.buildSuccessJsonResponse(imgResponse);
    }
}
