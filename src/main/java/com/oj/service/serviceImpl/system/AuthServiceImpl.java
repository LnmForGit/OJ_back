package com.oj.service.serviceImpl.system;

import com.oj.mapper.system.AuthMapper;
import com.oj.service.system.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired(required = false)
    private AuthMapper mapper;

    /**
     * 通过roleID获取当前用户的权限的功能接口实现
     * @param roleId
     * @return 当前权限下的前端功能HTML语句
     */
    @Override
    public String getAuthListByRole(String roleId) {
        //调用查询条件的Mapper接口方法
        List<Map<String, String>> authList = mapper.getAuthListByRole(roleId);
        return drawLeftPage(authList);
    }

    /**
     * 绘制前端权限功能的HTML语句
     * @param authList
     * @return
     */
    private String drawLeftPage(List<Map<String, String>> authList){
        StringBuffer leftHtml = new StringBuffer();
        for (Map<String, String> auth: authList){
            //若权限url为‘#’代表为父权限
            if ("#".equals(auth.get("auth_url"))){
                leftHtml.append("<li class=''><a href='#'><i class='"+auth.get("auth_ico")+"'></i>");
                leftHtml.append("<span class='nav-label'>"+auth.get("auth_name")+"</span><span class='fa arrow'></span></a>");
                leftHtml.append("<ul class='nav nav-second-level collapse' aria-expanded='false' style='height: 0px;'>");
                for (Map<String, String> authForChild : authList) {
                    //若权限数据中有auth_parent键值，且键值与父权限对应，为子权限
                    if(null != authForChild.get("auth_parent")){
                        if (auth.get("id") == authForChild.get("auth_parent")){
                            leftHtml.append("<li><a class='J_menuItem' href='"+authForChild.get("auth_url")+"'><i class='"+authForChild.get("auth_ico")+"'></i>"+authForChild.get("auth_name")+"</a></li>");
                        }
                    }
                }
                leftHtml.append("</ul></li>");
            }
        }
        return leftHtml.toString();
    }
}
