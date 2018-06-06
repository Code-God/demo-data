package com.jsure.datacenter.model.result;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wuxiaobiao
 * @Description:
 * @Date: Created in 2018/5/21
 * @Time: 15:07
 * I am a Code Man -_-!
 */
@Data
public class TUserResult implements Serializable {

    private static final long serialVersionUID = -5595969440795460228L;

    private Integer id;

    private String userName;

    private String password;

    private Date createDate;

}
