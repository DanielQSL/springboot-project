package com.company.project.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果集
 *
 * @author DanielQSL
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页的数量
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 结果集
     */
    private List<T> list;

}
