package com.company.project.utils;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;

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

    public static String getProperty(String namespace, String key, String defaultValue) {
        Config config = ConfigService.getAppConfig();
        String val = config.getProperty(key, defaultValue);
        if (!val.equals(defaultValue)) {
            return val;
        }
        config = ConfigService.getConfig(namespace);
        return config.getProperty(key, defaultValue);
    }

}
