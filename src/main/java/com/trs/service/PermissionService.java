package com.trs.service;

import com.trs.model.Permission;
import com.trs.model.RolePermission;

import java.util.List;

/**
 * Created by 李春雨 on 2017/3/22.
 */
public interface PermissionService {
    /**
     * 查找所有父功能列表(过滤重复记录)
     * @return
     */
    List<String> findAllParentName(Permission permission);

    /**
     * 通过父名称查找所有子功能
     * @return
     */
    List<Permission> findAllName(String parentName);

    /**
     * 添加角色与功能ID
     * @param rolePermission
     */
    public void add(RolePermission rolePermission);

    /**
     * 通过ID查询相应的功能ID
     * @param id
     * @return
     */
    public List<RolePermission> listPermission(Integer id);

    /**
     * 通过角色ID删除权限信息
     * @param id
     */
    public void del(Integer id);

    public List<Permission> selectPermissionById(Integer id);
}
