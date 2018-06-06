package com.jsure.datacenter.model.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/6/1
 * @Time: 10:46
 * I am a Code Man -_-!
 */
@Data
public class BaseModel implements Serializable{

    private static final long serialVersionUID = 572696470293592852L;

    /**
     * 是否在查询时没有查到记录时抛异常，默认为true（查询选填）
     */
    protected Boolean isNullError = true;
    /**
     * 分页当前页数（从1开始）
     */
    protected Integer pageCurrent;
    /**
     * 分页每页个数 (默认10条)
     */
    protected Integer pageSize;

    /**
     * 时间范围查询参数--开始时间
     */
    protected Date beginTime;

    /**
     * 时间范围查询参数--结束时间
     */
    protected Date endTime;

    /**
     * 排序属性
     */
    protected String orderField;

    /**
     * 排序规则
     */
    protected String orderRule;

    /**
     * 是否下载
     */
    protected Boolean isDownload = false;

}
