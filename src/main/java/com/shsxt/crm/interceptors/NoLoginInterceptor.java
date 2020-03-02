package com.shsxt.crm.interceptors;

import com.shsxt.crm.exceptions.NoLoginException;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 获取cookie  解析用户id
         *    如果用户id存在 并且 数据库存在对应用户记录  放行  否则进行拦截 重定向到登录
         */
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        /*if (userId==0 || null==userService.selectByPrimaryKey(userId)){
            response.sendRedirect(request.getContextPath()+"/index");
            return false;
        }*/
        /**
         * 判断userId是否存在,如果存在并且可以在数据库查询到相关记录那么则证明该用户登录过,因为cookie存在
         * cookie存在的前提是用户登录成功,cookie里的信息是登录时向后台发送ajax请求,请求成功后返回的data作为
         * 回调函数success的参数,通过data.result拿到UserModel里面的用户信息
         * 当访问被拦截页面 若存在userId那么说明之前登陆过就直接放行
         */
        if(userId==0 || null==userService.selectByPrimaryKey(userId)){
            throw  new NoLoginException();
        }

        return true;
    }
}
