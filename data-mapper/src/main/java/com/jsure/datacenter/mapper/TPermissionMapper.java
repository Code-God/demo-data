package com.jsure.datacenter.mapper;

import com.jsure.datacenter.model.model.TPermission;
import org.springframework.stereotype.Component;

@Component
public interface TPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TPermission record);

    int insertSelective(TPermission record);

    TPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TPermission record);

    int updateByPrimaryKey(TPermission record);
}