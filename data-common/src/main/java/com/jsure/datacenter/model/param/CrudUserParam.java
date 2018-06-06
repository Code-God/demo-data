package com.jsure.datacenter.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/6/6
 * @Time: 11:38
 * I am a Code Man -_-!
 */
@Data
public class CrudUserParam implements Serializable{

    private static final long serialVersionUID = -8695287777184365957L;

    @ApiModelProperty("用户id")
    private Integer[] id;

    @ApiModelProperty("删除标识符")
    private String deleteFlag;

    @ApiModelProperty("审核标识符")
    private String review;
}
