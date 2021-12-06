package com.company.project.adaptor;

import com.company.project.model.PageResult;

/**
 * page对象转换器
 * <p>
 * 转换为 mybatis 或者 mybatis-plus对应的分页信息
 *
 * @author DanielQSL
 */
public interface PageInfoAdaptor<T> {

    /**
     * 转换为自己系统的 page对象
     *
     * @param commonPage 通用分页结果
     * @return 目标分页结果
     */
    T to(PageResult commonPage);

    /**
     * 转回公用的 page对象
     *
     * @param selfPage 目标分页结果
     * @return 通用分页结果
     */
    PageResult from(T selfPage);

}