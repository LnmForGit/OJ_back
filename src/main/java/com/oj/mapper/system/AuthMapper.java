package com.oj.mapper.system;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AuthMapper {

    //获取当前用户对应权限的方法
    @Select("SELECT b.* FROM teach_back_role_auth a LEFT JOIN teach_back_auth b ON a.authId  = b.id WHERE a.roleId = #{roleId}")
    public List<Map<String, String>> getAuthListByRole(String roleId);
}
