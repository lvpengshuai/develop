package com.trs.mapper;
import org.apache.ibatis.annotations.Param;
import com.trs.model.Organize;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface OrganizeMapperOnline {
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


    /**
     * 查询所有的IP
     * @return
     */
    List<Organize> selectAllIP();

    int total(Map map);

    Organize selectByName(String name);

    List<Organize> selectById(Integer orgID);

    String selectCodeByID(Integer id);
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

    String selectBid(Integer id);

    Organize getOrganizeByBid(Integer bid);

    List<Organize> getOrganizeByBidOnline(Integer bid);

    Organize getOragnizeByip(@Param("start") String start,@Param("end") String end);

    /**
     * 查询所有的organize
     * @return
     */
    List<Organize> getOrganizesList();
}
