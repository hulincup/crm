package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {
    /**
     * 查询所有角色的方法,下拉菜单
     * @return
     */
    public List<Map<String, Object>> queryAllRoles();

    public Role queryRoleByRoleName(String roleName);

}