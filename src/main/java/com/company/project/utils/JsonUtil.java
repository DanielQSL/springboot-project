package com.company.project.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Jackson JSON工具类
 * <p>
 * Jackson 配置官方文档: https://github.com/FasterXML/jackson-databind/wiki/JacksonFeatures
 *
 * @author DanielQSL
 */
public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 日期格式化
     */
    private static final String DATETIME_STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        // 模组（Long转String）
        OBJECT_MAPPER.registerModule(longToStringModule());
        // 所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DATETIME_STANDARD_FORMAT));
        // 对象的所有字段全部列入。NON_NULL：不返回 null 值字段
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 允许序列化空的POJO类(否则会抛出异常)
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 取消java.util.Date, Calendar默认转换timestamps形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 在遇到未知属性的时候不抛出异常。忽略在json字符串中存在，但是在java对象中不存在对应属性的情况。
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 强制 JSON 空字符串("")转换为 null 对象值:
        OBJECT_MAPPER.disable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        // 在 JSON 中允许 C/C++ 样式的注释(非标准，默认禁用)
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_COMMENTS);
        // 允许单引号(非标准)
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        // 反序列化可以解析JSON串里包含了数字类型的属性值为NaN
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
        // 强制转义非 ASCII 字符
        OBJECT_MAPPER.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
    }

    /**
     * 将Long类型转换为String类型
     * 说明：由于JavaScript的最大的安全整数是2^53 - 1，而Java的Long.MAX_VALUE是2^63 - 1
     * 数字会越界，但字符串不会
     *
     * @return 模组
     */
    private static SimpleModule longToStringModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        return simpleModule;
    }

    private JsonUtil() {
    }

    /**
     * 对象转为Json格式字符串
     *
     * @param obj 对象
     * @return Json格式字符串
     */
    public static <T> String toJsonString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("Parse Object to String error", e);
            return null;
        }
    }

    /**
     * 对象转为Json格式字符串（格式化的Json字符串）
     *
     * @param obj 对象
     * @return 美化的Json格式字符串
     */
    public static <T> String toJsonStringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("Parse Object to Pretty String error", e);
            return null;
        }
    }

    /**
     * Json字符串转换为自定义对象
     *
     * @param str   要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseObject(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : OBJECT_MAPPER.readValue(str, clazz);
        } catch (Exception e) {
            LOGGER.error("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * 将Json字符串转化为ArrayList对象
     *
     * @param str            Json字符串
     * @param elementClasses 元素类型
     * @return ArrayList对象
     */
    public static <T> T parseObjectList(String str, Class<?>... elementClasses) {
        return parseObjectCollection(str, ArrayList.class, elementClasses);
    }

    /**
     * 将Json字符串转化为集合对象
     *
     * @param str             Json字符串
     * @param collectionClass Collection类型
     * @param elementClasses  元素类型
     * @return 集合对象
     */
    public static <T> T parseObjectCollection(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        // 获取泛型的 Collection Type
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (Exception e) {
            LOGGER.error("Parse String to Collection error", e);
            return null;
        }
    }

    /**
     * 将Json字符串转化为集合对象
     *
     * @param str           Json字符串
     * @param typeReference 集合类型
     * @return 集合对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseObjectCollection(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : OBJECT_MAPPER.readValue(str, typeReference));
        } catch (Exception e) {
            LOGGER.error("Parse String to Collection error", e);
            return null;
        }
    }

    /**
     * 压缩Json字符串
     * e.g.:
     * {"name":"Tom","age":18}
     *
     * @param str 未压缩的Json字符串
     * @return 压缩后的Json字符串
     */
    public static String compress(String str) {
        return str.replace(" ", "").replace("\r", "").replace("\n", "").replace("\t", "");
    }

}
