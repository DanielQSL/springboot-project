package com.company.project.utils;

import com.company.project.enums.EnvType;

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

        //此处为新增逻辑
        // 4. Try to get environment from hostname
        String hostname = "";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (hostname != null) {
            if (hostname.contains("-dev-")) {
                return EnvType.DEV.getValue();
            }
            if (hostname.contains("-qa-")) {
                return EnvType.QA.getValue();
            }
            if (hostname.contains("-pl-")) {
                return EnvType.PL.getValue();
            }
            if (hostname.contains("-online-")) {
                return EnvType.ONLINE.getValue();
            }
        }
        return EnvType.DEV.getValue();
    }

}
