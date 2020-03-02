package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.dto.TreeDto;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.query.ModuleQuery;
import com.shsxt.crm.service.ModuleService;
import com.shsxt.crm.vo.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("module")
/**
 * 要继承baseController baseController里面有
 * 一个先执行的方法就是存ctx到request域对象中
 */
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeDto> queryAllModules(Integer roleId){
        return moduleService.queryAllModules02(roleId);
    }

    /**
     * 菜单页面转发控制
     * grade：转发到 第几级菜单页面
     * mid:如果为二级 三级菜单 此时mid 为上级菜单的id 此处作为中转
     */
    @RequestMapping("/index/{grade}")
    public String  index(@PathVariable Integer grade, Integer mid, Model model){
        model.addAttribute("mid",mid);
        if (grade==1){
            return "module_1";
        }else if (grade==2){
            return "module_2";
        }else if (grade==3){
            return "module_3";
        }else{
            return "";
        }
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryModulesByParams(ModuleQuery moduleQuery){
        return moduleService.queryByParamsForDataGrid(moduleQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveModule(Module module){
        moduleService.saveModule(module);
        return success("菜单添加成功");
    }


    @RequestMapping("queryAllModulesByGrade")
    @ResponseBody
    public List<Map<String,Object>> queryAllModulesByGrade(Integer grade){
        return moduleService.queryAllModulesByGrade(grade);
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateModule(Module module){
        moduleService.updateModule(module);
        return success("菜单更新成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteModule(Integer id){
        moduleService.deleteModuleById(id);
        return success("菜单删除成功");
    }
}
