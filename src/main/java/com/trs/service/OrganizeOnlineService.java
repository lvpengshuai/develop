package com.trs.service;

import com.trs.model.Organize;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 在线机构
 */
public interface OrganizeOnlineService {

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

    String selectCodeByID(Integer id);

    String selectBid(Integer id);

    Map addOnlineUserOrganize(Map map);

    Organize selectOrganById(Integer id);

    List<Organize> selectAllIP();

    List<Organize> selectById(Integer orgID);

    Organize getOrganizeByBid(Integer bid);

    List<Organize> getOrganizeByBidOnline(Integer bid);

    Organize getOragnizeByip(String startIp,String endIp);

    Map deleteOrganizeById1(List<Integer> ids);

    /**
     * 查询所有的organize 不分页
     * @return
     */
    List<Organize> getOrganizesList();

    Map getUserOrganzeRole(HttpSession session, String name, String dateTime, String iiiPP, HttpServletRequest request, String ONLINESTATUS);
}
