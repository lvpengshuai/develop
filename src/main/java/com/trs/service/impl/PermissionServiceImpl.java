package com.trs.service.impl;

import com.trs.mapper.PermissionMapper;
import com.trs.model.Permission;
import com.trs.model.RolePermission;
import com.trs.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 李春雨 on 2017/3/22.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    @Override
    public List<String> findAllParentName(Permission permission) {
        return permissionMapper.findAllParentName(permission);
    }

    @Override
    public List<Permission> findAllName(String parentName) {
        return permissionMapper.findAllName(parentName);
    }

    @Override
    public void add(RolePermission rolePermission) {
        permissionMapper.add(rolePermission);
    }

    @Override
    public List<RolePermission> listPermission(Integer id) {
        return permissionMapper.listPermission(id);
    }

    @Override
    public void del(Integer id) {
        permissionMapper.deletePermissionById(id);
    }

    @Override
    public List<Permission> selectPermissionById(Integer id) {
        return permissionMapper.selectPermissionById(id);
    }
}
