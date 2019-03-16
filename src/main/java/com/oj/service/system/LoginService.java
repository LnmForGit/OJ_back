package com.oj.service.system;

import java.util.List;
import java.util.Map;
public interface LoginService {
    //通过登陆名和密码查询用户
    public List<Map<String, String>> getUserByLoginInfo(Map<String, String> loginInfo);

    //通过用户ID更新密码
    public void resetPassword(String user_id, String newPassword);
}
