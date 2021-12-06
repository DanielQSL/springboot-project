package com.company.project.model;

/**
 * 通用错误接口
 *
 * @author DanielQSL
 */
public interface BaseCommonError {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    int getErrorCode();

    /**
     * 获取错误描述
     *
     * @return 错误描述
     */
    String getErrorMsg();

}
