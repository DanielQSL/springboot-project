package com.company.project.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.model.PageResult;
import com.github.pagehelper.PageInfo;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 分页工具类
 *
 * @author qianshuailong
 * @date 2020/10/7
 */
public class PageUtil {

    private static final int DEFAULT_PAGE_SIZE = 20;

    private PageUtil() {
    }

    /**
     * 转为统一分页结果返回
     *
     * @param currentPage 当前页码
     * @param pageSize    每页大小
     * @param data        分页数据
     * @param total       总记录数
     * @return 统一分页结果
     */
    public static <T> PageResult<T> toPage(Integer currentPage, Integer pageSize, List<T> data, Long total) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum(currentPage);
        pageResult.setPageSize(pageSize);
        pageResult.setTotal(total);
        pageResult.setList(data);
        return pageResult;
    }

    /**
     * 处理 Mybatis plus 分页查询结果包装成统一分页结果返回
     *
     * @param page Mybatis plus分页查询结果
     * @return 统一分页结果
     */
    public static <T> PageResult<T> toPage(IPage<T> page) {
        if (page == null) {
            return null;
        }
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum((int) page.getCurrent());
        pageResult.setPageSize((int) page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setList(page.getRecords());
        return pageResult;
    }

    /**
     * 处理 com.github.pagehelper 分页查询结果包装成统一分页结果返回
     *
     * @param page com.github.pagehelper分页查询结果
     * @return 统一分页结果
     */
    public static <T> PageResult<T> toPage(PageInfo<T> page) {
        if (page == null) {
            return null;
        }
        PageResult<T> pageVo = new PageResult<>();
        pageVo.setPageNum(page.getPageNum());
        pageVo.setPageSize(page.getPageSize());
        pageVo.setPages(page.getPages());
        pageVo.setList(page.getList());
        return pageVo;
    }

    /**
     * 根据总数计算总页数
     *
     * @param totalCount 总数
     * @param pageSize   每页数
     * @return 总页数
     */
    public static int totalPage(int totalCount, int pageSize) {
        if (pageSize == 0) {
            return 0;
        }
        return totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize + 1);
    }

    /**
     * 根据分页要求切分集合
     *
     * @param list     集合
     * @param pageNum  当前页
     * @param pageSize 每页大小
     * @return 切分后的集合
     */
    public static <T> List<T> subListByPage(List<T> list, Integer pageNum, Integer pageSize) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        // 记录总数
        int count = list.size();
        // 页数
        int pageCount;
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        // 开始索引
        int fromIndex;
        // 结束索引
        int toIndex;
        if (pageNum != pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
        return list.subList(fromIndex, toIndex);
    }

}
