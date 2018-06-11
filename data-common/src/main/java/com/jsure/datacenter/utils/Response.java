package com.jsure.datacenter.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/4/9
 * @Time: 15:25
 * I am a Code Man -_-!
 */
@Data
@ApiModel(value="Response返回值")
public class Response<T> {

    @ApiModelProperty("响应状态码")
    private String resCode;

    @ApiModelProperty("响应结果描述")
    private String resMsg;

    @ApiModelProperty("响应结果")
    private T result;

}
