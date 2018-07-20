package com.trs.service.impl;

import com.trs.core.annotations.Log;
import com.trs.core.util.Util;
import com.trs.mapper.RoleMapper;
import com.trs.mapper.UserMapper;
import com.trs.model.Role;
import com.trs.model.User;
import com.trs.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zly on 2017-3-20.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_INSERT, targetType = com.trs.model.Log.LOG_TARGETTYPE_USERMANAGER, description = "添加用户")
    @Override
    public Map addUser(User user) {
        Map resultMap = new HashMap();

        User userByName = userMapper.findUserByName(user.getUsername());
        if (userByName != null) {
            resultMap.put("state", "1");
            resultMap.put("msg", "该账户已经被使用，请重新填写。");

            return resultMap;
        }

        String password = Util.toMD5(user.getPwd());

        user.setPwd(password);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        Integer roleId = user.getRoleId();
        if (roleId != 0) {
            Role role = roleMapper.selectByPrimaryKey(roleId);
            role.setUsed(0);
            roleMapper.updateByPrimaryKey(role);
        }
        userMapper.insertSelective(user);

        resultMap.put("state", "0");
        resultMap.put("msg", "添加成功");

        return resultMap;
    }

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_SELECT, targetType = com.trs.model.Log.LOG_TARGETTYPE_USERMANAGER, description = "查看用户")
    @Override
    public Map findAllUser(Map map) {
        Map resultMap = new HashMap();

        List<User> userList = userMapper.listAllUsers(map);
        int total = userMapper.total(map);

        resultMap.put("total", total);
        resultMap.put("rows", userList);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));

        return resultMap;
    }

    @Override
    public User findUserById(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public Map updateUser(User user) {
        Map resultMap = new HashMap();

//        User userByName = userMapper.findUserByName(user.getUsername());
//        if (userByName != null) {
//            resultMap.put("state", "1");
//            resultMap.put("msg", "该账户已经被使用，请重新填写。");
//
//            return resultMap;
//        }


        user.setGmtModified(new Date());

        // 修改用户信息之前查询原数据信息
        Integer id = user.getId();
        User user1 = userMapper.selectByPrimaryKey(id);

        // 获取原数据的角色ID
        Integer roleId = user1.getRoleId();

        // 获取新数据角色ID
        Integer roleId1 = user.getRoleId();
        User user2 = new User();
        user2.setRoleId(roleId);
        user2.setId(id);
        // 根据ID查询本表数据
        List<User> userByRoleId = userMapper.findUserByRoleId(user2);

        // 判断是否存在数据，如果存在则不进行更新操作，如果不存在则将角色的使用情况改为未使用
        if (userByRoleId.size() == 0) {
            Role role = roleMapper.selectByPrimaryKey(roleId);
            role.setUsed(1);
            roleMapper.updateByPrimaryKey(role);
        }
        Role role = roleMapper.selectByPrimaryKey(roleId1);
        if (role != null) {
            role.setUsed(0);
            roleMapper.updateByPrimaryKey(role);
        }
        userMapper.updateByPrimaryKeySelective(user);

        resultMap.put("state", "0");
        resultMap.put("msg", "修改成功");

        return resultMap;
    }

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_DELETE, targetType = com.trs.model.Log.LOG_TARGETTYPE_USERMANAGER, description = "删除用户")
    @Override
    public Map deleteUserById(List usersId) {
        Map resultMap = new HashMap();

        List list = new ArrayList();

        for (int i = 0; i < usersId.size(); i++) {
            int id = Integer.parseInt(String.valueOf(usersId.get(i)));
            User user = userMapper.selectByPrimaryKey(id);
            Util.log(user.getUsername(), "user-delete" , 0);
            if (!"admin".equalsIgnoreCase(user.getUsername())) {
                if (!(user.getStatus() == 0)) {
                    Util.log("删除操作：---", "user-delete" , 0);
                    Util.log(user.getStatus(), "user-delete" , 0);
                    userMapper.deleteByPrimaryKey(id);
                    list.add(String.valueOf(id));
                } else {
                    resultMap.put("used", 1);
                }
            } else {
                resultMap.put("admin", 1);
            }
        }
        resultMap.put("total", usersId.size());
        resultMap.put("deltotal", list.size());
        resultMap.put("code", "0");

        return resultMap;
    }

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_ENABLE, targetType = com.trs.model.Log.LOG_TARGETTYPE_USERMANAGER, description = "启用或禁用用户")
    @Override
    public Map changeStatus(Map map) {
        Map resultMap = new HashMap();
        List list = new ArrayList();

        List ids = (List) map.get("ids");
        String status = (String) map.get("status");

        for (int i = 0; i < ids.size(); i++) {
            int id = Integer.parseInt(String.valueOf(ids.get(i)));
            User user = userMapper.selectByPrimaryKey(id);
            if (!"admin".equalsIgnoreCase(user.getUsername())) {
                Map paramMap = new HashMap();
                paramMap.put("id", id);
                paramMap.put("status", status);
                paramMap.put("gmtModified", new Date());
                list.add(id);
                userMapper.changeStatus(paramMap);
            } else {
                resultMap.put("admin", 1);
            }
        }

        resultMap.put("status", status);
        resultMap.put("total", ids.size());
        resultMap.put("chgtotal", list.size());

        return resultMap;
    }

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_UPDATA, targetType = com.trs.model.Log.LOG_TARGETTYPE_USERMANAGER, description = "用户修改密码")
    @Override
    public Map resetPwd(Map map) {
        Map resultMap = new HashMap();

        String id = (String) map.get("id");
        String oldpassword = (String) map.get("oldpassword");
        String newpassword = (String) map.get("newpassword");

        User userById = userMapper.selectByPrimaryKey(Integer.parseInt(id));
        if (userById != null) {
            if (!Util.toMD5(oldpassword).equalsIgnoreCase(userById.getPwd())) {
                resultMap.put("code", "-1");
                return resultMap;
            } else {
                User user = new User();
                user.setId(Integer.parseInt(id));
                user.setGmtModified(new Date());
                user.setPwd(Util.toMD5(newpassword));
                userMapper.updateByPrimaryKeySelective(user);

                resultMap.put("code", "0");
            }
        }
        return resultMap;
    }

    @Override
    public User findUserByNameAndPwd(Map<String, String> map) throws Exception {
        return userMapper.findUserByNameAndPwd(map);
    }

    @Override
    public List<User> findUserByRoleId(User user) {
        List<User> userByRoleId = userMapper.findUserByRoleId(user);
        return userByRoleId;
    }

    @Override
    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }


    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_UPDATA, targetType = com.trs.model.Log.LOG_TARGETTYPE_USERMANAGER, description = "用户修改个人信息")
    @Override
    public int updateByUserId(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
