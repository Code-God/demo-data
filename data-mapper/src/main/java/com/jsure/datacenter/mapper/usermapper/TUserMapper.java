package com.jsure.datacenter.mapper.usermapper;

import com.jsure.datacenter.model.model.TUser;

import java.util.List;


public interface TUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    TUser findByUserName(String username);

    TUser selectByUser(TUser record);

    List<TUser> selectUserList(TUser record);
}