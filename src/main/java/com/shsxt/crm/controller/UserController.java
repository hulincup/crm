package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.CookieUtil;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lynn
 */
@Controller
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @GetMapping("user/queryUserByUserId")
    @ResponseBody
    public User queryUserByUserId(Integer userId){
        return userService.selectByPrimaryKey(userId);
    }

    @RequestMapping("user/login")
    @ResponseBody
    public ResultInfo login(String userName,String userPwd){
        UserModel userModel = userService.login(userName, userPwd);
        return success("用户登录成功",userModel);
    }

    @RequestMapping("user/updateUserPassword")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword){
        userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request),oldPassword,newPassword,confirmPassword);
        return success("密码更新成功");
    }

    @RequestMapping("user/index")
    public String index(){
        return "user";
    }

    @RequestMapping("user/list")
    @ResponseBody
    public Map<String,Object> queryUsersByParams(UserQuery userQuery){
        return userService.queryByParamsForDataGrid(userQuery);
    }

    @RequestMapping("user/save")
    @ResponseBody
    public ResultInfo saveUser(User user){
        user.getRoleIds().forEach(roleId->{
            System.out.println(roleId);
        });
        userService.saveUser(user);
        return success("用户添加成功");
    }

    @RequestMapping("user/update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("用户更新成功");
    }

    @RequestMapping("user/delete")
    @ResponseBody
    /**
     * 设置别名前台传过来的参数别名为id
     */
    public ResultInfo deleteUser(@RequestParam("id") Integer userId){
        userService.deleteUser(userId);
        return success("用户删除成功");
    }

}
