package com.jsure.datacenter.model.model;

import lombok.Data;

import java.util.Date;

@Data
public class TUser extends BaseModel {

    private static final long serialVersionUID = -5153525737318199750L;

    private Integer id;

    private Integer roleId;

    private String userName;

    private String password;

    private Date createDate;

}