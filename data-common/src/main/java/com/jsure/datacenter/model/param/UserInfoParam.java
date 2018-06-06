package com.jsure.datacenter.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/30
 * @Time: 11:54
 * I am a Code Man -_-!
 */
@Data
public class UserInfoParam implements Serializable {

    private static final long serialVersionUID = 7735609058476849444L;

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

}
