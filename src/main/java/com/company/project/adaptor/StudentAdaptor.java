package com.company.project.adaptor;

import com.company.project.pojo.StudentDO;
import com.company.project.pojo.StudentDTO;
import com.company.project.utils.BeanCopyUtil;

/**
 * 学生适配器
 *
 * @author DanielQSL
 */
public class StudentAdaptor {

    public static StudentDTO poToHistoryPo(StudentDO studentDO) {
        if (studentDO == null) {
            return null;
        }
        StudentDTO studentDTO = BeanCopyUtil.copyProperties(studentDO, StudentDTO.class);
        // 这些由数据库自动生成
        studentDTO.setDeleted(null);
        studentDTO.setCreateTime(null);
        studentDTO.setUpdateTime(null);
        return studentDTO;
    }

}
