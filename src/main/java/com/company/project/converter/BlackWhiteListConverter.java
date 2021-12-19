package com.company.project.converter;

import com.company.project.pojo.StudentDTO;
import com.company.project.pojo.StudentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 黑白名单转化类
 *
 * @author qianshuailong
 * @date 2020-10-19
 */
@Mapper
public interface BlackWhiteListConverter extends BaseConverter<StudentDTO, StudentVO> {

    BlackWhiteListConverter INSTANCE = Mappers.getMapper(BlackWhiteListConverter.class);

}
