package com.company.project.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * velocity工具类
 */
public class VelocityUtil {

    public static final String WHITE_LIST_ADD_LIST_PATH = "templates/WhiteListAddList.vm";

    /**
     * 简易模板填充
     *
     * @param template 字符串模板, 带有动参
     * @param paramMap 参数map, key与动参名呼应
     * @return 解析后的字符串
     */
    public static String generateSimpleTemplate(String template, Map<String, Object> paramMap) {
        if (StringUtils.isBlank(template)) {
            return StringUtils.EMPTY;
        }
        if (CollectionUtil.isEmpty(paramMap)) {
            return template;
        }

        StringWriter writer = new StringWriter();
        Context context = new VelocityContext();
        for (String key : paramMap.keySet()) {
            context.put(key, paramMap.get(key));
        }

        Velocity.evaluate(context, writer, VelocityUtil.class.getSimpleName(), new StringReader(template));
        return writer.toString();
    }

    public static String generateGovObjectWhiteListAddListTemplate(Dict values) {
        return generateTemplate(WHITE_LIST_ADD_LIST_PATH, values);
    }

    /**
     * 文件模板填充
     *
     * @param resourcePath 资源文件路径
     * @param values       参数map, key与动参名呼应
     * @return 填充后的内容
     */
    public static String generateTemplate(String resourcePath, Dict values) {
        VelocityEngine ve = new VelocityEngine();
        Properties p = new Properties();
        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.init(p);
        Template template = ve.getTemplate(resourcePath);
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        return sw.toString();
    }

    public static void main(String[] args) {
        // 模板名称, 动参以${}包裹
        String template = "http://test.com/leo/offline?devGroupId=${devGroupId}&jobId=${hanoiJobId}";

        // 对应的值, 以key-value map的形式
        Map<String, Object> paramMap = new HashMap<>();
        // key需要与模板中动参的名字一致才能成功映射
        paramMap.put("devGroupId", 297);
        paramMap.put("hanoiJobId", 44771L);
        // 额外放一些模板没用到的字段也没关系
        paramMap.put("xxx", "yyy");

        String result = generateSimpleTemplate(template, paramMap);
        System.out.println(result);
    }

}
