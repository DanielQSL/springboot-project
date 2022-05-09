package com.company.project.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

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
     * 默认时区
     */
    public static final String DEFAULT_TIME_ZONE = "Asia/Shanghai";
    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    static {
        // 指定时区
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        // 设置日期格式化
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT));
        // Java8 日期处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        OBJECT_MAPPER.registerModule(javaTimeModule);
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        OBJECT_MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 对象的所有字段全部列入。NON_NULL：不返回 null 值字段
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 在遇到未知属性的时候不抛出异常。忽略在json字符串中存在，但是在java对象中不存在对应属性的情况。
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许序列化空的POJO类(否则会抛出异常)
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 取消java.util.Date, Calendar默认转换timestamps形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 模组（Long转String）
        OBJECT_MAPPER.registerModule(longToStringModule());
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
        // 解析以"0"为开头的数字
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS);
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

    /**
     * 是否为有效Json字符串
     *
     * @param jsonStr Json字符串
     * @return 是否有效
     */
    public static boolean isValidJsonStr(String jsonStr) {
        try {
            OBJECT_MAPPER.readTree(jsonStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        // json解析list嵌套
        String jsonStr = "[[\"zhangsan\",\"lisi\"],[\"wangwu\"]]";
        List<List<String>> list = JsonUtil.parseObjectCollection(jsonStr, new TypeReference<List<List<String>>>() {});
        // 打平嵌套list
        Set<String> set = list.stream().flatMap(List::stream).collect(Collectors.toSet());
        System.out.println(set);
    }

}
