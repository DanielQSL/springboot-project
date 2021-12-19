package com.company.project.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.company.project.model.PageResult;
import com.company.project.pojo.StudentDO;
import com.company.project.pojo.StudentDTO;
import com.company.project.pojo.StudentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 黑白名单转化类
 *
 * @author qianshuailong
 * @date 2020-10-19
 */
@Mapper
public interface StudentConverter {

    StudentConverter INSTANCE = Mappers.getMapper(StudentConverter.class);

    @Mappings({})
    StudentDTO convert(StudentDO entity);

    @Mappings({})
    List<StudentDTO> convert(List<StudentDO> entities);

    StudentDO convert(StudentDTO dto);

    /**
     * 转换为统一分页对象
     *
     * @param entityPage 分页对象
     * @return 统一分页对象
     */
    @Mappings({
            @Mapping(source = "records", target = "list"),
            @Mapping(source = "pages", target = "totalPage"),
            @Mapping(source = "current", target = "pageNum"),
            @Mapping(source = "size", target = "pageSize")
    })
    PageResult<StudentVO> convertPage(IPage<StudentDO> entityPage);

}
