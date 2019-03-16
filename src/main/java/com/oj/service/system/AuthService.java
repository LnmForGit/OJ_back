package com.oj.service.system;

import java.util.List;
import java.util.Map;

public interface AuthService {
    //通过roleID获取当前用户的权限
    public String getAuthListByRole(String roleId);
}
