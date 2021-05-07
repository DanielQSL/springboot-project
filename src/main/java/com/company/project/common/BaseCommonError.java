package com.company.project.common;

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

    /**
     * 设置自定义错误描述
     *
     * @param errorMsg 错误描述
     * @return 通用错误接口
     */
    BaseCommonError setErrorMsg(String errorMsg);

}
