package com.doubao.finance.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DouBaoOssClient
{
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
    private static final Logger logger = Logger.getLogger(DouBaoOssClient.class);
    @Value("${bdy.env}")
    private String env;
    @Value("${bj.bdy.private}")
    private String privateBucket;
    @Value("${bj.bdy.public}")
    private String publicBucket;
    @Autowired
    private OSSClient ossClient;

    public String uploadPrivateFile(String key, InputStream fin, String contentType, boolean async)
            throws OSSException, ClientException, IOException
    {
        return uploadFile(key, fin, contentType, this.privateBucket, async);
    }

    public String uploadPublicFile(String key, InputStream fin, String contentType, boolean async)
            throws OSSException, ClientException, IOException
    {
        return uploadFile(key, fin, contentType, this.publicBucket, async);
    }

    private String uploadFile(String key, final InputStream fin, String contentType, final String bucketName, boolean async)
            throws OSSException, ClientException, IOException
    {
        final ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(fin.available());
        if (StringUtils.isNotBlank(key))
        {
            String fileName = key.toLowerCase();
            if (fileName.endsWith("png")) {
                contentType = "image/png";
            } else if (fileName.endsWith("jpg")) {
                contentType = "image/jpeg";
            } else if (fileName.endsWith("pdf")) {
                contentType = "application/pdf";
            }
        }
        if (StringUtils.isBlank(contentType)) {
            contentType = "application/octet-stream";
        }
        objectMeta.setContentType(contentType);

        final String path = getKey(key);

        if (async) {
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    ossClient.putObject(bucketName, path, fin, objectMeta);
                }
            });
        } else {
            ossClient.putObject(bucketName, path, fin, objectMeta);
        }
        return path;
    }

    private static String hotfix = "/upload/down";

    public String getKey(String key)
    {
        StringBuffer path = new StringBuffer();
        if (key.startsWith(this.env))
        {
            path.append(key);
        }
        else if (key.startsWith(hotfix))
        {
            int s = hotfix.length();
            path.append(this.env).append(key.substring(s, key.length()));
        }
        else if (key.startsWith(File.separator))
        {
            path.append(this.env).append(key);
        }
        else
        {
            path.append(this.env).append(File.separator).append(key);
        }
        return path.toString();
    }

    @Deprecated
    public InputStream downloadDJFile(String key)
            throws OSSException, ClientException
    {
        OSSObject ossObject = this.ossClient.getObject(new GetObjectRequest(this.privateBucket, getKey(key)));
        long start = System.currentTimeMillis();
        InputStream inputStream = ossObject.getObjectContent();
        logger.debug(">>>>downloadFile:" + (System.currentTimeMillis() - start));
        return inputStream;
    }

    public InputStream getPrivateInputStream(String key)
    {
        InputStream result = null;

        OSSObject ossObject = this.ossClient.getObject(new GetObjectRequest(this.privateBucket, getKey(key)));
        InputStream inputStream = ossObject.getObjectContent();
        if (inputStream != null) {
            try
            {
                byte[] bytes = IOUtils.readStreamAsByteArray(inputStream);
                if ((bytes != null) && (bytes.length > 0)) {
                    result = new ByteArrayInputStream(bytes);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                IOUtils.safeClose(inputStream);
            }
        }
        return result;
    }

    public String genUrl(String key, Date expiration)
    {
        URL url = this.ossClient.generatePresignedUrl(this.privateBucket, getKey(key), expiration);
        return url.toString();
    }
}
