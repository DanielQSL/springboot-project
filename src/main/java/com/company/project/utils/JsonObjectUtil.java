package com.company.project.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Json转换工具类
 *
 * @author qianshuailong
 * @date 2021/4/29
 */
public class JsonObjectUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parseObject(String str, Class<T> clz) {
        try {
            return objectMapper.readValue(str, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseObject(String str, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(str, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> parseList(String str, TypeReference<List<T>> typeReference) {
        try {
            return objectMapper.readValue(str, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> parseList(String str) {
        try {
            return objectMapper.readValue(str, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
