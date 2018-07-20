package com.trs.service;

import com.trs.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-3-20.
 */
public interface UserService {

    /**
     * 添加用户
     *
     * @param user
     */
    public Map addUser(User user);

    /**
     * 查询所有用户
     *
     * @param map
     * @return
     */
    public Map findAllUser(Map map);

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    public User findUserById(int id);

    /**
     * 更新用户信息
     *
     * @param user
     */
    public Map updateUser(User user);

    /**
     * 根据id删除用户
     *
     * @param usersId
     * @return
     */
    public Map deleteUserById(List usersId);

    /**
     * 更改用户状态
     *
     * @param map
     * @return
     */
    public Map changeStatus(Map map);

    /**
     * 重置用户密码
     *
     * @param map
     * @return
     */
    public Map resetPwd(Map map);

    /**
     * 登录用户
     *
     * @return
     */
    public User findUserByNameAndPwd(Map<String, String> map) throws Exception;

    public List<User> findUserByRoleId(User user);

    public User findUserByName(String name);

    public int updateByUserId(User user);
}
