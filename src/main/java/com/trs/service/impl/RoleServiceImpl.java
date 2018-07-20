package com.trs.service.impl;

import com.trs.mapper.MemberMapper;
import com.trs.mapper.PermissionMapper;
import com.trs.mapper.RoleMapper;
import com.trs.mapper.UserMapper;
import com.trs.model.*;
import com.trs.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-3-20.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private MemberMapper memberMapper;


    @Override
    public void addRole(Map map) {

    }

    @Override
    public List<Role> findAllRole(int attribute) {
        return roleMapper.findAllRole(attribute);
    }

    @Override
    public Role findRoleById(Map map) {
        return null;
    }

    @Override
    public void updateRole(Map map) {

    }

    @Override
    public void deleteRoleById(Map map) {

    }

    @com.trs.core.annotations.Log(operationType = com.trs.model.Log.LOG_OPERTYPE_SELECT, targetType = com.trs.model.Log.LOG_TARGETTYPE_SYSTEMMANAGER, description = "查看角色")
    @Override
    public Map findAll(Map<String, Object> map) {
        List<Role> all = roleMapper.findAll(map);
        int total = roleMapper.total(map);
        Map resultMap = new HashMap();
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("search", map.get("search"));
        resultMap.put("rows", all);
        resultMap.put("total", total);
        return resultMap;
    }

    @com.trs.core.annotations.Log(operationType = com.trs.model.Log.LOG_OPERTYPE_ENABLE, targetType = com.trs.model.Log.LOG_TARGETTYPE_SYSTEMMANAGER, description = "启用或禁用角色")
    @Override
    public Map change(Map map) {
        List<Integer> ids = (List<Integer>) map.get("ids");
        String flag = (String) map.get("state");
        HttpServletRequest request = (HttpServletRequest) map.get("request");

         /*定义操作说明 和 操作类型*/
        String description = "";
        String operType = "";

        // 创建map对象，用于保存返回值
        Map<String, Object> result = new HashMap<String, Object>();

        // 定义id
        int id = 0;

        for (int i = 0; i < ids.size(); i++) {
            // 赋值id
            id = ids.get(i);

            // 检测资源是否存在
            Role role = roleMapper.selectByPrimaryKey(id);
            if (null == role) {
                continue;
            }
            // 检测资源的状态信息
            Integer status = role.getStatus();

            // 执行发布的方法
            if ("0".equals(flag)) {
                if (0 == status) {
                    continue;
                }
                role.setStatus(0);
                roleMapper.updateByPrimaryKey(role);
            } else if ("1".equals(flag)) {
                if (1 == status) {
                    continue;
                }
                role.setStatus(1);
                roleMapper.updateByPrimaryKey(role);
            }
        }
        result.put("code", 1);
        result.put("msg", "操作成功");
        return result;
    }

    @com.trs.core.annotations.Log(operationType = com.trs.model.Log.LOG_OPERTYPE_INSERT, targetType = com.trs.model.Log.LOG_TARGETTYPE_SYSTEMMANAGER, description = "添加角色")
    @Override
    public Map add(Role role, String[] chkfunctions, String state) {
        // 定义Map存放返回值
        Map result = new HashMap();

        // 检测是否分配权限
        if (null == chkfunctions || chkfunctions.length == 0) {
            result.put("code", -2);
            return result;
        }

        // 获取状态值
        Integer status = role.getStatus();

        // 获取状态信息
        role.setStatus(status);

        // 通过名称查询角色
        Role roleByName = roleMapper.findRoleByName(role.getName());

        if ("0".equals(state)) {
            if (null != roleByName) {
                result.put("code", 0);
                return result;
            }

            // 将数据添加到数据表中
            role.setUsed(1);
            roleMapper.insertSelective(role);

            // 将角色ID对应的方法权限表中
            Integer id = role.getId();
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(id);
            for (int i = 0; i < chkfunctions.length; i++) {
                rolePermission.setPermissionId(Integer.parseInt(chkfunctions[i]));
                permissionMapper.add(rolePermission);
            }
        } else {
            if (null != roleByName) {
                if (Integer.parseInt(state) != roleByName.getId()) {
                    result.put("code", 0);
                    return result;
                }
            }
            roleMapper.updateByPrimaryKey(role);

            // 获取角色ID
            Integer id = role.getId();

            // 通过角色ID删除权限信息
            permissionMapper.deletePermissionById(id);

            // 将角色ID对应的方法权限表中
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(id);

            for (int i = 0; i < chkfunctions.length; i++) {
                rolePermission.setPermissionId(Integer.parseInt(chkfunctions[i]));
                permissionMapper.add(rolePermission);
            }
        }


        result.put("code", 1);
        return result;
    }

    @Override
    public Role findRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean findRoleByName(String name) {
        if (null == roleMapper.findRoleByName(name)) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Object> deleteById(Integer id) {
        // 创建map对象，用于保存返回信息
        Map<String, Object> result = new HashMap<String, Object>();

        // 通过id查询数据
        Role roleById = roleMapper.selectByPrimaryKey(id);

        // 判断资源是否存在
        if (null == roleById) {
            result.put("code", 0);
            return result;
        }

        // 检测资源的状态
        Integer status = roleById.getStatus();
        if (0 == status) {
            // 返回数据
            result.put("code", -1);
            return result;
        }

        /*
            * ======检测角色属性======
            *  1、如果角色属性后台，更新user表中的角色ID
            *  2、如果角色属性前台，更新member表以及机构表的角色ID
         */
        Integer attribute = roleById.getAttribute();
        if (1 == attribute) {
            User user = new User();
            user.setId(-1);
            user.setRoleId(roleById.getId());
            List<User> userByRoleId = userMapper.findUserByRoleId(user);
            if (userByRoleId.size() != 0) {
                for (User user1 : userByRoleId) {
                    user1.setRoleId(0);
                    userMapper.updateByPrimaryKey(user1);
                }
            }
        } else if (2 == attribute) {
            Member member = new Member();
            member.setId(-1);
            member.setRoleId(roleById.getId());
            List<Member> memberByRoleId = memberMapper.findMemberByRoleId(member);
            if (memberByRoleId.size() != 0) {
                for (Member member1 : memberByRoleId) {
                    member1.setRoleId(0);
                    memberMapper.updateByPrimaryKey(member1);
                }
            }
        }

        // 删除角色信息
        roleMapper.deleteByPrimaryKey(id);

        // 通过ID删除对应的权限信息
        permissionMapper.deletePermissionById(id);

        result.put("code", 1);
        return result;
    }

    @Override
    public List<String> findPermissionByRoleId(int rolId) {
        List<String> result = new ArrayList<>();
        List<RolePermission> list = permissionMapper.listPermission(rolId);
        for (int i = 0; i < list.size(); i++) {
            try {
                Permission permission = permissionMapper.selectPermissionById(list.get(i).getPermissionId());
                result.add(permission.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public List<Permission> findPermissionListByRoleId(int rolId) {
        List<Permission> result = new ArrayList<>();
        List<RolePermission> list = permissionMapper.listPermission(rolId);
        for (int i = 0; i < list.size(); i++) {
            try {
                Permission permission = permissionMapper.selectPermissionById(list.get(i).getPermissionId());
                if (permission.getAttribute() != 1) {
                    continue;
                }
                result.add(permission);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public List<Permission> findClientPermissionByRoleId(int rolId) {
        List<Permission> result = new ArrayList<>();
        List<RolePermission> list = permissionMapper.listPermission(rolId);
        for (int i = 0; i < list.size(); i++) {
            try {
                Permission permission = permissionMapper.selectPermissionById(list.get(i).getPermissionId());
                if (permission.getAttribute() == 2) {
                    result.add(permission);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
