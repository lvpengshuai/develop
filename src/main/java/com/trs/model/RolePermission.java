package com.trs.model;

/**
 * Created by root on 17-3-19.
 */
public class RolePermission {
    private int roleId;
    private int permissionId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RolePermission{" +
                "roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}
