package com.doubao.finance.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class HttpUtils
{
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String get(String uri)
            throws IOException
    {
        CloseableHttpClient httpclient = getClient(uri);
        HttpGet httpMethod = new HttpGet(uri);
        return praseResponse(httpclient, httpMethod);
    }

    public static String get(String uri, Map<String, String> params)
            throws IOException
    {
        if ((params != null) && (!params.isEmpty()))
        {
            StringBuffer buffer = null;
            if (uri.contains("?"))
            {
                if (!uri.endsWith("&")) {
                    buffer = new StringBuffer("&");
                } else {
                    buffer = new StringBuffer();
                }
            }
            else {
                buffer = new StringBuffer("?");
            }
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry<String, String> entrySet = (Map.Entry)iterator.next();
                buffer.append((String)entrySet.getKey()).append("=").append(URLEncoder.encode((String)entrySet.getValue(), "UTF-8")).append("&");
            }
            uri = uri + buffer.substring(0, buffer.length() - 1);
        }
        logger.debug(uri);
        String result = get(uri);
        logger.debug(result);
        return result;
    }

    public static String post(String uri, Map<String, String> params)
            throws IOException
    {
        CloseableHttpClient httpclient = getClient(uri);
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec("").build();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);
        if ((params != null) && (params.size() > 0))
        {
            List<NameValuePair> nvps = new ArrayList();
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                String key = (String)entry.getKey();
                Object value = entry.getValue();
                if ((value instanceof String)) {
                    nvps.add(new BasicNameValuePair(key, (String)value));
                } else if ((value instanceof List)) {
                    for (Object v : (List)value) {
                        if ((v instanceof String)) {
                            nvps.add(new BasicNameValuePair(key, (String)value));
                        } else {
                            logger.error("error value :" + v + " which key is " + key);
                        }
                    }
                } else {
                    logger.warn("unsupport type");
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        }
        return praseResponse(httpclient, httpPost);
    }

    public static String post(String uri, String entry)
            throws IOException
    {
        Header[] headers = null;
        return post(uri, entry, headers);
    }

    public static String post(String uri, HttpEntity entry)
            throws IOException
    {
        Header[] headers = null;
        return post(uri, entry, headers);
    }

    public static String post(String uri, String entry, Header header)
            throws IOException
    {
        Header[] headers = null;
        if (header != null) {
            headers = new Header[] { header };
        }
        return post(uri, entry, headers);
    }

    private static CloseableHttpClient getClient(String uri)
    {
        CloseableHttpClient httpclient = null;
        if (uri.toLowerCase().startsWith("https"))
        {
            SSLConnectionSocketFactory socketFactory = createSSLConnSocketFactory();

            Registry registry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
            httpclient = HttpClients.custom().setConnectionManager(new PoolingHttpClientConnectionManager(registry)).build();
        }
        else
        {
            httpclient = HttpClients.createDefault();
        }
        return httpclient;
    }

    public static String post(String uri, String entry, Header[] headers)
            throws IOException
    {
        String result = null;
        if (StringUtils.isNotBlank(uri))
        {
            CloseableHttpClient httpclient = getClient(uri);

            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec("").build();
            HttpPost httpPost = new HttpPost(uri);

            httpPost.setConfig(requestConfig);
            if (headers != null) {
                httpPost.setHeaders(headers);
            }
            if (StringUtils.isNotBlank(entry))
            {
                StringEntity reqEntity = new StringEntity(entry, Consts.UTF_8);
                httpPost.setEntity(reqEntity);
            }
            result = praseResponse(httpclient, httpPost);
        }
        return result;
    }

    public static String post(String uri, HttpEntity entry, Header[] headers)
            throws IOException
    {
        String result = null;
        if (StringUtils.isNotBlank(uri))
        {
            CloseableHttpClient httpclient = getClient(uri);

            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec("").build();
            HttpPost httpPost = new HttpPost(uri);

            httpPost.setConfig(requestConfig);
            if (headers != null) {
                httpPost.setHeaders(headers);
            }
            httpPost.setEntity(entry);

            result = praseResponse(httpclient, httpPost);
        }
        return result;
    }

    public static String post(String uri, String entry, SSLConnectionSocketFactory sslsf)
            throws IOException
    {
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec("").build();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);
        if (StringUtils.isNotBlank(entry))
        {
            StringEntity reqEntity = new StringEntity(entry, Consts.UTF_8);
            httpPost.setEntity(reqEntity);
        }
        return praseResponse(httpclient, httpPost);
    }

    private static String praseResponse(CloseableHttpClient httpClient, HttpUriRequest httpMethod)
    {
        String result = null;
        try
        {
            CloseableHttpResponse response = httpClient.execute(httpMethod);
            if (response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, Consts.UTF_8);
                }
                EntityUtils.consume(entity);
                return result;
            }
            response.close();
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory()
    {
        SSLConnectionSocketFactory socketFactory = null;
        try
        {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

        }
        catch (GeneralSecurityException e)
        {
            e.printStackTrace();
        }
        return socketFactory;
    }
}
