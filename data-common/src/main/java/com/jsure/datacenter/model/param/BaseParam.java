package com.jsure.datacenter.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wuxiaobiao
 * @Description: Base入参
 * @Date: Created in 2018/4/11
 * @Time: 14:56
 * I am a Code Man -_-!
 */
@Data
public class BaseParam implements Serializable {

    @ApiModelProperty("分页当前页数（默认从1开始）")
    protected Integer pageCurrent;

    @ApiModelProperty("分页每页个数（默认10条）")
    protected Integer pageSize;

    @ApiModelProperty("时间范围查询参数--开始时间")
    protected Date beginTime;

    @ApiModelProperty("时间范围查询参数--结束时间")
    protected Date endTime;

    @ApiModelProperty("排序字段")
    protected String orderField;

    @ApiModelProperty("排序規則")
    protected String orderRule;

}
