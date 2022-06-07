package com.company.project.constants;

/**
 * 项目常量
 *
 * @author DanielQSL
 */
public final class CommonConstants {
    /**
     * 生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）
     */
    public static final String BASE_PACKAGE = "com.company.project";

    /**
     * 是否
     */
    public interface YesOrNo {
        Integer NO = 0;
        Integer YES = 1;
    }

    /**
     * 可用状态（Y可用 、N不可用）
     */
    public interface EnableStatus {
        String ENABLE = "Y";
        String DISABLE = "N";
    }

}
