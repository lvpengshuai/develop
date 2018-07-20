package com.trs.service;

import com.trs.model.Permission;
import com.trs.model.Role;

import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-3-20.
 */
public interface RoleService {

    public void addRole(Map map);

    public List<Role> findAllRole(int attribute);

    public Role findRoleById(Map map);

    public void updateRole(Map map);

    public void deleteRoleById(Map map);

    public Map findAll(Map<String, Object> map);

    public Map change(Map map);

    public Map add(Role role, String[] chkfunctions, String id);

    public Role findRoleById(Integer id);

    public boolean findRoleByName(String name);

    public Map<String, Object> deleteById(Integer id);

    public List<String> findPermissionByRoleId(int rolId);

    /**
     * 通过角色id查询Permission
     * @param rolId
     * @return
     */
    public List<Permission> findPermissionListByRoleId(int rolId);
    public List<Permission> findClientPermissionByRoleId(int rolId);

}
