package com.company.project.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * RestTemplate使用
 *
 * @author DanielQSL
 */
@Slf4j
@Service
public class RestTemplateUsageService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String URL = "www.baidu.com";

    /**
     * 第三方调用示例
     */
    public ThirdPartyResponse invokeThirdParty() {
        ThirdPartyResponse response = null;
        // 设置头字段
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic 123");
        // 设置请求体
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("fname", "filename");
        params.put("base_cont", "1234");
        params.put("mode", 1);
        try {
            HttpEntity httpEntity = new HttpEntity(params, headers);
            response = restTemplate.postForObject(URL, httpEntity, ThirdPartyResponse.class);
            log.info("invoke third party. request: {} response: {}", "request", JSON.toJSONString(response));
        } catch (Exception e) {
            log.info("invoke third party error. request: {}", "request", e);
        }
        return response;
    }

    /**
     * get请求获取字节流示例
     */
    public void getForByte() {
        // 请求字节流
        byte[] bytes = restTemplate.getForObject(URL, byte[].class);
        // 将请求内容base64编码
        String dataByBase64 = new String(Base64.encodeBase64(bytes), Consts.UTF_8);
    }

    @Data
    public static class ThirdPartyResponse {
        private Integer code;
        private String msg;
        private Object data;
    }

}
