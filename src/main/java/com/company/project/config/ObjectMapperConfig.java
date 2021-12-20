package com.company.project.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * ObjectMapper配置类
 *
 * @author DanielQSL
 */
@Configuration
public class ObjectMapperConfig {

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

    /**
     * 定义ObjectMapper配置
     * 因为new ObjectMapper()初始化很耗时，影响性能，避免重复创建，所以将他注入到bean中
     *
     * @return ObjectMapper
     */
    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        // 日期类型字符串处理
        om.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT));
        // 指定时区
        om.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // java8日期处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        om.registerModule(javaTimeModule);
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 在遇到未知属性的时候不抛出异常
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 允许序列化空的POJO类(否则会抛出异常)
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // NULL不参与序列化
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 取消java.util.Date, Calendar默认转换timestamps形式
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        /// 强制 JSON 空字符串("")转换为 null 对象值:
        om.disable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        // 在 JSON 中允许 C/C++ 样式的注释(非标准，默认禁用)
        om.enable(JsonParser.Feature.ALLOW_COMMENTS);
        // 允许单引号(非标准)
        om.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        // 反序列化可以解析JSON串里包含了数字类型的属性值为NaN
        om.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
        // 强制转义非 ASCII 字符
        om.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        // 解析以"0"为开头的数字
        om.enable(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS);
        return om;
    }

}
