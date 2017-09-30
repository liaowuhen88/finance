package com.doubao.finance.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OssService
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DouBaoOssClient douBaoOssClient;

    public String uploadDJFile(String genName, InputStream in, String contextType, boolean sync)
            throws IOException
    {
        return this.douBaoOssClient.uploadPrivateFile(genName, in, contextType, sync);
    }

    public String uploadDJToPublicFile(String genName, InputStream in, String contextType, boolean sync)
            throws IOException
    {
        return this.douBaoOssClient.uploadPublicFile(genName, in, contextType, sync);
    }

    public String genUrl(String key)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(13, 180);
        return this.douBaoOssClient.genUrl(key, calendar.getTime());
    }
}
