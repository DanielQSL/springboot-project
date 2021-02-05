package com.company.project.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
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

    /**
     * 将MybatisPlus分页后的list转为分页信息
     */
    public static <T> PageResult<T> convertPageResult(Page<T> page) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum((int) page.getCurrent());
        pageResult.setPageSize((int) page.getSize());
        pageResult.setTotalPage((int) page.getPages());
        pageResult.setTotal(page.getTotal());
        pageResult.setList(page.getRecords());
        return pageResult;
    }

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> PageResult<T> convertPageResult(List<T> list) {
        PageResult<T> result = new PageResult<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotalPage(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }
}
