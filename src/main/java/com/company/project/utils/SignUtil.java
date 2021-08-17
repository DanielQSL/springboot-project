package com.company.project.utils;

import com.alibaba.fastjson.JSON;
import com.company.project.common.CommonResponse;
import com.company.project.enums.ResponseCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * 签名工具类
 *
 * @author DanielQSL
 */
public class SignUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUtil.class);


    /**
     * 应用id
     */
    private static final String APP_ID = "app_id";

    /**
     * 时间戳
     */
    private static final String TIMESTAMP = "timestamp";

    /**
     * 签名
     */
    private static final String SIGN = "sign";

    /**
     * 临时流水号，防止重复提交
     */
    private static final String NONCE = "nonce";

    /**
     * 时间差限制 单位：毫秒（10分钟）
     */
    private static final long TIME_DIFF_MAX = 1000 * 60 * 10;

    /**
     * 请求的URL
     */
    private static final String URL = "url";

    private SignUtil() {

    }

    /**
     * 验证请求头参数
     *
     * @param request 请求
     * @return 是否验证通过
     */
    public static boolean verifyHeaderParams(HttpServletRequest request) {
        String appId = request.getHeader(APP_ID);
        if (StringUtils.isBlank(appId)) {
            return false;
        }
        String timestamp = request.getHeader(TIMESTAMP);
        if (StringUtils.isBlank(timestamp)) {
            return false;
        }
        String sign = request.getHeader(SIGN);
        if (StringUtils.isBlank(sign)) {
            return false;
        }
        String nonce = request.getHeader(NONCE);
        if (StringUtils.isBlank(nonce)) {
            return false;
        }

        // 判断时间差（毫秒）
        long diff = System.currentTimeMillis() - Long.parseLong(timestamp);
        if (Math.abs(diff) > TIME_DIFF_MAX) {
            return false;
        }
        return true;
    }

    /**
     * 生成签名（大写）
     * 拼接为字符串：app_id=xxxx&nonce=xxxx&param=xxx&app_secret=xxx
     *
     * @param paramMap  排序后的请求参数
     * @param secretKey 秘钥
     * @return 签名（大写）
     */
    public static String generateSign(SortedMap<String, String> paramMap, String secretKey) {
        // 先移除请求头中的sign
        paramMap.remove(SIGN);
        // 进行key value的拼接
        StringBuilder paramStr = new StringBuilder();
        for (Map.Entry<String, String> param : paramMap.entrySet()) {
            paramStr.append(param.getKey()).append("=").append(param.getValue()).append("&");
        }
        // 将secretKey拼接到最后
        paramStr.append("SecretKey=").append(secretKey);
        return DigestUtils.md5DigestAsHex(paramStr.toString().getBytes(StandardCharsets.UTF_8)).toUpperCase();
    }

    /**
     * 获取所有请求参数，并将所有参与传参的参数按照ASCII排序（升序）
     * 参数：header请求头+URL请求路径+request参数+body请求体
     *
     * @param request 请求
     * @return 排序后的请求参数
     */
    public static SortedMap<String, String> getAllParams(HttpServletRequest request) throws IOException {
        SortedMap<String, String> paramMap = new TreeMap<>();
        // 获取请求头上的参数
        String appId = request.getHeader(APP_ID);
        String timestamp = request.getHeader(TIMESTAMP);
        String nonce = request.getHeader(NONCE);
        String sign = request.getHeader(SIGN);
        paramMap.put(APP_ID, appId);
        paramMap.put(TIMESTAMP, timestamp);
        paramMap.put(NONCE, nonce);
        paramMap.put(SIGN, sign);
        // 请求路径 如：http://localhost:8080/sign-poject/user/info
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getServletPath();
        paramMap.put(URL, url);
        // 获取request参数（对应的@RequestParam）
        if (!CollectionUtils.isEmpty(request.getParameterMap())) {
            for (Map.Entry<String, String[]> param : request.getParameterMap().entrySet()) {
                paramMap.put(param.getKey(), param.getValue()[0]);
            }
        }
        // todo 获取请求体中请求参数
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder requestBody = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            requestBody.append(inputStr);
        }
        Map<String, String> params = JSON.parseObject(requestBody.toString(), Map.class);

        return paramMap;
    }

    /**
     * 验证签名
     *
     * @param paramMap  排序后的请求参数
     * @param appSecret 秘钥
     * @return 是否验证成功
     */
    public static boolean verifySign(SortedMap<String, String> paramMap, String appSecret) {
        String requestSign = paramMap.get(SIGN);
        if (StringUtils.isBlank(requestSign)) {
            return false;
        }
        String sign = generateSign(paramMap, appSecret);
        return StringUtils.isNotBlank(sign) && sign.equals(requestSign);
    }

    // ========== 接口请求校验 ==========

    private static final String IGNORE_URI = "/login";

    public boolean verifyHttp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 把唯一请求id加到当前线程的contextMap中
        String nonce = request.getHeader(NONCE);
        MDC.put(NONCE, nonce);
        // 无需校验的路径
        if (IGNORE_URI.equals(request.getRequestURI())) {
            return true;
        }
        // 获取所有请求参数
        SortedMap<String, String> paramMap = SignUtil.getAllParams(request);
        // 验证请求头参数
        boolean isHeaderValid = SignUtil.verifyHeaderParams(request);
        if (isHeaderValid) {
            // 校验是否重复使用请求
            if (isRepeat(paramMap.get(APP_ID), Long.parseLong(paramMap.get(TIMESTAMP)), paramMap.get(nonce), paramMap.get(SIGN))) {
                LOGGER.error("重复提交请求");
                generateResponse(response, CommonResponse.fail(ResponseCodeEnum.UNAUTHORIZED));
                return false;
            }
            // 获取appId对应的秘钥  getSecretKey(appId);
            String secretKey = "";
            // 验签
            boolean isSignValid = SignUtil.verifySign(paramMap, secretKey);
            if (isSignValid) {
                // 验签通过
                return true;
            }
        }
        LOGGER.error("签名校验未通过 params: {}", paramMap);
        generateResponse(response, CommonResponse.fail(ResponseCodeEnum.UNAUTHORIZED));
        return false;
    }

    /**
     * 生成响应结果
     *
     * @param response HttpServletResponse
     * @param result   响应结果
     * @throws IOException IO异常
     */
    public static void generateResponse(HttpServletResponse response, CommonResponse result)
            throws IOException {
        try (OutputStream out = response.getOutputStream()) {
            response.setContentType("application/json;charset=UTF-8");
            out.write(JsonUtil.toJsonString(result).getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

    @Resource
    private StringRedisTemplate redisTemplate;

    /**
     * 判断是否重复提交
     *
     * @param appId     应用ID
     * @param timestamp 时间戳
     * @param nonce     临时流水号
     * @param signature 请求中的签名
     * @return 是否重复
     */
    public boolean isRepeat(String appId, long timestamp, String nonce, String signature) {
        String key = "repeat_attack" + appId + "_" + timestamp + "_" + nonce;
        String signCache = redisTemplate.opsForValue().get(key);
        if (signCache != null && signCache.equals(signature)) {
            return true;
        }
        redisTemplate.opsForValue().set(key, signature, TIME_DIFF_MAX, TimeUnit.MILLISECONDS);
        return false;
    }

}
