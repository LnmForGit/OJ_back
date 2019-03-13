package com.oj.service;

import com.oj.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author lixu
 * @Time 2019年3月9日 15点21分
 * @Description 创建Service接口，统一方法类型和方法名
 */
public interface UserService {

    //返回Map类型的List
    public List<Map> queryUserMapList(User user);

    //删除用户
    public void deleteUser(User user);

    //添加或修改用户
    public void demoSaveOrUpdateUser(User user);


}
