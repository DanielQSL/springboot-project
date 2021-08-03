package com.company.project.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页参数
 *
 * @author DanielQSL
 */
@ApiModel("分页参数")
@Getter
@Setter
public class PageParam implements Serializable {

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    @ApiModelProperty(value = "页码", required = true, example = "1")
    private Integer pageNum;

    @NotNull(message = "每页条数不能为空")
    @Range(min = 1, max = 100, message = "条数范围为 [1, 100]")
    @ApiModelProperty(value = "每页条数", required = true, example = "20")
    private Integer pageSize;

}
