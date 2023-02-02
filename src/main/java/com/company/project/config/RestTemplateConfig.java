package com.company.project.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * RestTemplate配置类
 *
 * @author qianshuailong
 * @date 2021/1/16
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 基于Apache httpclient配置RestTemplate
     *
     * @return RestTemplate
     */
    @Primary
    @Bean
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
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

        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingConnectionManager)
                // 支持https
                .setSSLSocketFactory(getSslConnectionSocketFactory())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        // 添加内容转换器
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter());
        return restTemplate;
    }

    /**
     * 支持SSL
     *
     * @return SSLConnectionSocketFactory
     */
    private static SSLConnectionSocketFactory getSslConnectionSocketFactory() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        return new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
    }

    /**
     * 基于OkHttp3配置RestTemplate
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate4OkHttp3() {
        // 设置连接池参数，最大空闲连接数200，空闲连接存活时间10s
        ConnectionPool connectionPool = new ConnectionPool(200, 10, TimeUnit.SECONDS);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectionPool(connectionPool)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false).build();

        ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory(okHttpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

}
