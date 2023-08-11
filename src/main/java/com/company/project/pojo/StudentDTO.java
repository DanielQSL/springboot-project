package com.company.project.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 学生 DTO对象
 *
 * @author DanielQSL
 */
@Data
public class StudentDTO {

    private String name;

    private Integer age;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

}
