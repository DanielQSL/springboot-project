package com.company.project.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.SimpleDateFormat;

/**
 * ObjectMapper配置类
 *
 * @author DanielQSL
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * 日期格式化
     */
    private static final String DATETIME_STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

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
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        om.setDateFormat(new SimpleDateFormat(DATETIME_STANDARD_FORMAT));
        // 在遇到未知属性的时候不抛出异常
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 允许序列化空的POJO类(否则会抛出异常)
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 取消java.util.Date, Calendar默认转换timestamps形式
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 在 JSON 中允许 C/C++ 样式的注释(非标准，默认禁用)
        om.enable(JsonParser.Feature.ALLOW_COMMENTS);
        // 解析以"0"为开头的数字
        om.enable(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS);
        // 允许单引号(非标准)
        om.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        // 反序列化可以解析JSON串里包含了数字类型的属性值为NaN
        om.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
        return om;
    }

}
