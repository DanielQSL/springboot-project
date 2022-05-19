package com.company.project.utils;

import com.qsl.project.base.enums.EnvTypeEnum;

import java.net.InetAddress;

/**
 * 环境工具类
 *
 * @author DanielQSL
 */
public class EnvUtil {

    private static String env;

    static {
        env = initEnv();
    }

    /**
     * 获取环境信息
     *
     * @return 当前环境
     */
    public static String getEnv() {
        return env;
    }

    /**
     * 初始化环境信息
     *
     * @return 环境
     */
    protected static String initEnv() {
        // 1. Try to get environment from JVM system property
        env = System.getProperty("env");
        if (env != null && env.length() != 0) {
            return env;
        }

        // 2. Try to get environment from OS environment variable
        env = System.getenv("ENV");
        if (env != null && env.length() != 0) {
            return env;
        }

        // 3. Try to get environment from hostname
        String hostname = "";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (hostname != null) {
            if (hostname.contains("-dev-")) {
                return EnvTypeEnum.DEV.getDesc();
            }
            if (hostname.contains("-qa-")) {
                return EnvTypeEnum.QA.getDesc();
            }
            if (hostname.contains("-pl-")) {
                return EnvTypeEnum.PL.getDesc();
            }
            if (hostname.contains("-online-")) {
                return EnvTypeEnum.ONLINE.getDesc();
            }
        }
        return EnvTypeEnum.DEV.getDesc();
    }

}
