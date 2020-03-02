package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.ModuleMapper;
import com.shsxt.crm.dao.PermissionMapper;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.dto.TreeDto;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.Module;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("all")
public class ModuleService extends BaseService<Module,Integer> {
    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;
    public List<TreeDto> queryAllModules(){
        return moduleMapper.queryAllModules();
    }

    public List<TreeDto> queryAllModules02(Integer roleId){
        List<TreeDto> treeDtos=moduleMapper.queryAllModules();
        // 根据角色id 查询角色拥有的菜单id  List<Integer>
        List<Integer> roleHasMids=permissionMapper.queryRoleHasAllModuleIdsByRoleId(roleId);
        if (roleHasMids!=null && roleHasMids.size()>0){
            treeDtos.forEach(treeDto -> {
                if (roleHasMids.contains(treeDto.getId())){
                    treeDto.setChecked(true);
                }
            });
        }
        return treeDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveModule(Module module){
        /**
         * 1.参数校验
         *     模块名-module_name
         *         非空  同一层级下模块名唯一
         *     url
         *         二级菜单  非空  不可重复
         *     上级菜单-parent_id
         *         一级菜单   null
         *         二级|三级菜单 parent_id 非空 必须存在
         *      层级-grade
         *          非空  0|1|2
         *       权限码 optValue
         *          非空  不可重复
         * 2.参数默认值设置
         *     is_valid  create_date update_date
         * 3.执行添加 判断结果
         */
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"请输入菜单名");
        Integer grade = module.getGrade();
        //AssertUtil.isTrue(grade==null||grade!=0||grade!=1||grade!=2,"菜单层级不合法");
        AssertUtil.isTrue(grade==null||!(grade==0||grade==1||grade==2),"菜单层级不合法");
        AssertUtil.isTrue(moduleMapper.queryModuleByGradeAndModuleName(module.getGrade(),module.getModuleName())!=null,"该层级下菜单重复");
        if (grade==1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"请指定二级菜单的url");
            AssertUtil.isTrue(moduleMapper.queryModuleByGradeAndUrl(module.getGrade(),module.getUrl())!=null,"二级菜单下url不可重复");
        }

        if (grade!=0){
            Integer parentId = module.getParentId();
            AssertUtil.isTrue(parentId==null||selectByPrimaryKey(parentId)==null,"请指定上级菜单");

        }
        AssertUtil.isTrue(module.getOptValue()==null,"请输入权限码");
        AssertUtil.isTrue(moduleMapper.queryModuleByOptValue(module.getOptValue())!=null,"权限码不可重复");
        module.setIsValid((byte)1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(module)<1,"菜单添加失败");
    }

    /**
     * 通过grade查询所有的资源 放在下拉菜单
     * @param grade
     * @return
     */
    public List<Map<String,Object>> queryAllModulesByGrade(Integer grade){
        return moduleMapper.queryAllModulesByGrade(grade);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateModule(Module module){
        /**
         * 1.参数校验
         *     id 非空 记录存在
         *     模块名-module_name
         *         非空  同一层级下模块名唯一
         *     url
         *         二级菜单  非空  不可重复
         *     上级菜单-parent_id
         *         二级|三级菜单 parent_id 非空 必须存在
         *      层级-grade
         *          非空  0|1|2
         *       权限码 optValue
         *          非空  不可重复
         * 2.参数默认值设置
         *      update_date
         * 3.执行更新 判断结果
         */
        AssertUtil.isTrue(module.getId()==null,"请选择待更新的用户记录");
        AssertUtil.isTrue(selectByPrimaryKey(module.getId())==null,"待更新的用户不存在");
        Integer grade = module.getGrade();
        AssertUtil.isTrue(grade==null||!(grade==0||grade==1||grade==2),"菜单层级不合法");
        Module temp=moduleMapper.queryModuleByGradeAndModuleName(grade,module.getModuleName());
        if (temp!=null){
            AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"该层级下菜单已存在");
        }
        if (grade==1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"该菜单下Url不能为空");
            temp=moduleMapper.queryModuleByGradeAndUrl(grade,module.getUrl());
            AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"该菜单下url已存在");
        }
        if (grade!=0){
            Integer parentId = module.getParentId();
            AssertUtil.isTrue(parentId==null || selectByPrimaryKey(parentId)==null,"请指定上级菜单");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"请输入权限码");
        temp = moduleMapper.queryModuleByOptValue(module.getOptValue());
        if (temp!=null){
            AssertUtil.isTrue(!(temp.getId().equals(module.getId())),"权限码已存在");
        }
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(module)<1,"菜单更新失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteModuleById(Integer mid){
        Module temp = selectByPrimaryKey(mid);
        AssertUtil.isTrue(mid==null||temp==null,"待删除记录不存在");
        /**
         * 如果存在子菜单 不允许删除
         */
        int count=moduleMapper.countSubModuleByParentId(mid);
        AssertUtil.isTrue(count>0,"存在子菜单,不支持删除操作!");
        //权限表
        count=permissionMapper.countPermissionsByModuleId(mid);
        if (count>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionsByModuleId(mid)<count,"菜单删除失败");
        }
        temp.setIsValid((byte)0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"菜单删除失败");

    }

    public List<ModuleDto> queryUserHasRoleHasModuleDtos(Integer userId){
        /**
         * 1.查询用户角色分配的一级菜单
         * 2.根据一级菜单查询用户角色分配的二级菜单
         */
        List<ModuleDto> moduleDtos= moduleMapper.queryUserHasRoleHasModuleDtos(userId, 0, null);
        if (moduleDtos!=null&&moduleDtos.size()>0){
            moduleDtos.forEach(moduleDto -> {
                moduleDto.setSubModules(moduleMapper.queryUserHasRoleHasModuleDtos(userId,1,moduleDto.getId()));
            });
        }
        return moduleDtos;
    }
}
