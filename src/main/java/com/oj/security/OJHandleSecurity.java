package com.oj.security;

import com.oj.frameUtil.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lixu
 * @Time 2019年3月12日 13点47分
 * @Description 自定义拦截器OJHandleSecurity
 */
public class OJHandleSecurity implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    //实现preHandle方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(LogUtil.requestLogger(request));
        return true;
    }
}
