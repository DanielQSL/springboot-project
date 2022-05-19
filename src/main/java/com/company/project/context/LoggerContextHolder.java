package com.company.project.context;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 日志上下文
 *
 * @author qianshuailong
 * @date 2022/5/19
 */
public class LoggerContextHolder {

    private static final ThreadLocal<String> context = new ThreadLocal<String>();

    /**
     * 获取日志打印的内容
     */
    public static String get() {
        return context.get();
    }

    /**
     * 设置日志打印内容
     */
    public static void set(String loggerContext) {
        context.set(loggerContext);
    }

    /**
     * 添加日志内容
     */
    public static void add(String... loggerContext) {
        if (ArrayUtils.isEmpty(loggerContext)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String loggerStr : loggerContext) {
            sb.append(',').append(loggerStr);
        }

        if (StringUtils.isNotBlank(context.get())) {
            context.set(context.get().concat(sb.toString()));
        } else {
            context.set(sb.toString());
        }
    }

    /**
     * 清除日志内容
     */
    public static void clear() {
        context.set(null);
    }

}
