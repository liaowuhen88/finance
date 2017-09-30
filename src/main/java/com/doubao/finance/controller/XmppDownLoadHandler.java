package com.doubao.finance.controller;

import com.doubao.finance.service.DouBaoOssClient;
import java.io.InputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"xmppDownLoad"})
public class XmppDownLoadHandler
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DouBaoOssClient douBaoOssClient;

    @RequestMapping(value={"downLoad"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
    public void uploadDj(String type, String key, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        request.setCharacterEncoding("UTF-8");
        if ((StringUtils.isEmpty(type)) || (type.equals("image")))
        {
            response.setContentType("image/jpeg");
            response.setHeader("content-disposition", "attachment;filename=" + key.hashCode() + ".jpg");
        }
        else if (type.equals("voice"))
        {
            response.setContentType("application/ogg");
        }
        ServletOutputStream stream = response.getOutputStream();
        InputStream in = this.douBaoOssClient.downloadDJFile(key);
        if (in != null)
        {
            byte[] b = new byte['?'];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                stream.write(b, 0, len);
            }
        }
        stream.flush();
        stream.close();
    }
}
