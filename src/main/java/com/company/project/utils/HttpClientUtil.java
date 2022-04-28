package com.company.project.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Apache Http客户端工具类
 *
 * @author DanielQSL
 */
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final CloseableHttpClient HTTP_CLIENT;

    private static final String JSON_CONTENT_TYPE = "application/json";

    private static final int SUCCESS_STATUS = 200;

    static {
        RequestConfig requestConfig = RequestConfig.custom()
                // 设置客户端和服务端建立连接的超时时间
                .setConnectTimeout(5_000)
                // 设置客户端从服务端读取数据的超时时间
                .setSocketTimeout(5_000)
                // 设置从连接池获取连接的超时时间，不宜过长
                .setConnectionRequestTimeout(200)
                .build();

        // Httpclient连接池，长连接保持30秒
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        // 连接池最大连接数
        poolingConnectionManager.setMaxTotal(1_000);
        // 设置同路由的并发数
        poolingConnectionManager.setDefaultMaxPerRoute(100);
        // 可用空闲连接过期时间
        poolingConnectionManager.setValidateAfterInactivity(10_000);

        HTTP_CLIENT = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingConnectionManager)
                .build();
    }

    private HttpClientUtil() {
    }

    /**
     * 通过连接池获取HttpClient
     *
     * @return HttpClient
     */
    public static CloseableHttpClient getHttpClient() {
        return HTTP_CLIENT;
    }

    public static String get(String url) throws Exception {
        return get(url, Collections.emptyMap(), Collections.emptyMap());
    }

    public static String get(String url, Map<String, Object> params) throws Exception {
        return get(url, Collections.emptyMap(), params);
    }

    public static String get(String url, Map<String, Object> headers, Map<String, Object> params)
            throws Exception {
        URIBuilder ub = new URIBuilder(url);
        if (MapUtils.isNotEmpty(params)) {
            ArrayList<NameValuePair> pairs = buildParameters(params);
            ub.setParameters(pairs);
        }
        HttpGet httpGet = new HttpGet(ub.build());
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }
        return getResponse(httpGet);
    }

    public static String post(String url) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        return getResponse(httpPost);
    }

    public static String post(String url, String jsonStr) throws Exception {
        return post(url, Collections.emptyMap(), jsonStr);
    }

    public static String post(String url, Map<String, Object> headers, String jsonStr) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }
        StringEntity entity = new StringEntity(jsonStr, StandardCharsets.UTF_8);
        entity.setContentEncoding(StandardCharsets.UTF_8.toString());
        entity.setContentType(JSON_CONTENT_TYPE);
        httpPost.setEntity(entity);
        return getResponse(httpPost);
    }

    public static String post(String url, Map<String, Object> params) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = buildParameters(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
        return getResponse(httpPost);
    }

    public static String post(String url, Map<String, Object> headers, Map<String, Object> params)
            throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }

        ArrayList<NameValuePair> pairs = buildParameters(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
        return getResponse(httpPost);
    }

    private static ArrayList<NameValuePair> buildParameters(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }
        return pairs;
    }

    /**
     * 处理http请求
     *
     * @param request 请求参数
     * @return 响应结果
     * @throws Exception 异常
     */
    private static String getResponse(HttpRequestBase request) throws Exception {
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(request)) {
            // 判断返回状态是否返回为200
            if (response != null && response.getStatusLine().getStatusCode() == SUCCESS_STATUS) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(StandardCharsets.UTF_8.toString());
        Map<String, Object> params = new HashMap<>();
        params.put("city", "上海");
        final String result;
        try {
            result = HttpClientUtil.get("http://wthrcdn.etouch.cn/weather_mini", params);
            System.out.println(result);
        } catch (Exception e) {
            LOGGER.error("error. {}", e.getMessage(), e);
        }
    }

}
