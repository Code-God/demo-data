package com.jsure.datacenter.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统级静态变量
 * 创建者 科帮网 
 * 创建时间	2017年11月20日
 */
public class CustomConstant {
    /**
	 * token
	 */
	public static final int RESCODE_REFTOKEN_MSG = 1006;		//刷新TOKEN(有返回数据)
	public static final int RESCODE_REFTOKEN = 1007;			//刷新TOKEN
	
	public static final int JWT_ERRCODE_NULL = 4000;			//Token不存在
	public static final int JWT_ERRCODE_EXPIRE = 4001;			//Token过期
	public static final int JWT_ERRCODE_FAIL = 4002;			//验证不通过


	/**
	 * 脱敏替换字符
	 */
	public static final String INSENSITIVE_STRING = "******";

	/**
	 * 敏感属性列表
	 */
	public static List<String> INSENSITIVE_LIST = new ArrayList<>();

	static {
		INSENSITIVE_LIST.add("passwd");
		INSENSITIVE_LIST.add("password");
		INSENSITIVE_LIST.add("initPasswd");
	}


	/**
	 * JWT
	 */
	public static final String JWT_SECERT = "8677df7fc3a34e26a61c034d5ec8245d";			//密匙
	public static final long JWT_TTL = 7 * 24 * 60 * 60 * 1000;								//token有效时间


	public static final String SYS_LOGIN_SUCCESS = "登录成功";
	public static final String USER_INFO_SUCCESS = "获取用户信息成功";
	public static final String CREATE_USER_SUCCESS = "创建用户成功";
	public static final String UPDATE_USER_SUCCESS = "更新用户信息成功";
	public static final String DELETE_USER_SUCCESS = "删除用户成功";
	public static final String EXPORT_EXCEL_SUCCESS = "导出excel成功";
}
