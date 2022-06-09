package com.company.project.utils;

import cn.hutool.core.util.StrUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.ConfigConsts;

/**
 * Apollo工具类
 *
 * @author DanielQSL
 */
public class ApolloUtil {

    private ApolloUtil() {
    }

    public static String getProperty(String key, String defaultValue) {
        Config config = ConfigService.getAppConfig();
        return config.getProperty(key, defaultValue);
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    public static String getProperty(String namespace, String key, String defaultValue) {
        Config config = ConfigService.getConfig(StrUtil.blankToDefault(namespace, ConfigConsts.NAMESPACE_APPLICATION));
        return config.getProperty(key, defaultValue);
    }

}
