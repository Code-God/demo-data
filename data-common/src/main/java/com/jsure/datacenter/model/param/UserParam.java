package com.jsure.datacenter.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/30
 * @Time: 12:11
 * I am a Code Man -_-!
 */
@Data
public class UserParam extends BaseParam {

    private static final long serialVersionUID = 114507752827655634L;

    @ApiModelProperty("用户id数组")
    private Integer id;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

}
