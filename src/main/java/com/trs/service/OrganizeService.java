package com.trs.service;

import com.trs.model.Organize;

import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-5-11.
 */
public interface OrganizeService {

    List<Organize> selectAllIP();

    /**
     * 添加organize
     * @param map
     */
    Map addOrganizeUser(Map map);

    /**
     * 根据id查询organize
     * @param id
     * @return
     */
    Map getOrganizeById(int id);

    /**
     * 查询所有的organize
     * @param map
     * @return
     */
    Map getAllOrganizes(Map map);

    /**
     * 修改organize
     * @param map
     */
    Map updateOrganize(Map map);

    /**
     * 修改状态
     * @param map
     */
    Map changeStatus(Map map);

    /**
     * 删除organize
     * @param ids
     */
    Map deleteOrganizeById(List<Integer> ids);

    List getUsedIp();

    Map checkMemberIsAdmin(Map map);

    /**
     * 查找机构用户
     *
     */
    Map getOrganizeIp(String ip);
}
