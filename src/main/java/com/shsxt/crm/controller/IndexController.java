package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.service.ModuleService;
import com.shsxt.crm.service.PermissionService;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lynn
 */
@Controller
public class IndexController extends BaseController {
    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private ModuleService moduleService;

    /**
     * 登录页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("main")
    public String main(HttpServletRequest request){
        /**
         * 是为了在前台main页面显示用户名 欢迎XXX
         */
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        request.setAttribute("user",userService.selectByPrimaryKey(userId));
        /**
         * 在页面跳转前 先将查到的用户权限码存到session中 前台遍历
         */
        List<String> permissions = permissionService.queryUserHasRolesHasPermissions(userId);
        request.getSession().setAttribute("permissions",permissions);
        List<ModuleDto> moduleDtos = moduleService.queryUserHasRoleHasModuleDtos(userId);
        request.getSession().setAttribute("modules",moduleDtos);
        return "main_2.0";
    }
}
