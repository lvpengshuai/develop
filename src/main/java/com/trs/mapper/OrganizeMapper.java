package com.trs.mapper;

import com.trs.model.Organize;
import com.trs.model.User;

import java.util.List;
import java.util.Map;

public interface OrganizeMapper {



    /**
     * 查询所有的IP
     * @return
     */
    List<Organize> selectAllIP();

    /**
     * 添加organize
     * @param organize
     */
    void addOrganizeUser(Organize organize);

    /**
     * 根据id查询organize
     * @param id
     * @return
     */
    Organize selectOrganizeById(int id);

    /**
     * 查询所有的organize
     * @param map
     * @return
     */
    List<Organize> selectAllOrganizes(Map map);

    int total(Map map);

    Organize selectByName(String name);
    /**
     * 修改organize
     * @param organize
     */
    void updateOrganize(Organize organize);

    /**
     * 修改状态
     * @param map
     */
    void changeStatus(Map map);

    /**
     * 删除organize
     * @param id
     */
    void deleteOrganizeById(int id);

    List selectIp(int id);

    void addIp(Map map);

    void updateIp(Map map);

    void deleteIp(int id);

    List selectOrganizeAdmin(int id);
    List selectOrganizeAdminByMember(int id);

    void addOrganizeAdmin(Map map);

    void deleteOrganizeAdmin(int id);

    List getUsedIp(String date);

}