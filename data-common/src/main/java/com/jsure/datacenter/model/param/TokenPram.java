package com.jsure.datacenter.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/30
 * @Time: 10:37
 * I am a Code Man -_-!
 */
@Data
public class TokenPram implements Serializable {

    private static final long serialVersionUID = -2158597745910748193L;

    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

}
