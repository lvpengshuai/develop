package com.trs.service;

import com.trs.model.Member;

import java.util.List;
import java.util.Map;

public interface MemberOnlineService {
    /**
     * 添加会员
     * @param member
     */
    public Map add(Member member);

    /**
     * 修改密码
     */
    public Map updatePassword(Member member);

    /**
     * 查询用户
     * @param userName
     * @return
     */
    public Member findMemberByUserName(String userName);

    /**
     * 查询所有会员
     * @param map
     * @return
     */
    public Map findAllMember(Map map);

    public Map  findAllOrgainzeMember(Map map);


    /**
     * 查询会员
     * @param id
     * @return
     */
    Member findMemberById(Integer id);


    /**
     * 禁用，启用用户
     * @param map
     * @return
     */
    public Map changeStatus(Map map);

    /**
     * 删除会员
     * @param ids
     * @return
     */
    public Map deleteMemberByid(List<Integer> ids);


    public Map<String, String> changRoleId(Map map);

    public Member findMemberById(int id);

    /**
     * 更新会员
     * @param member
     */
    public Map updateInfo(Member member);

    /**
     * 重置密码
     * @param map
     * @return
     */
    public Map resetPwd(Map map);

    /**
     * 修改用户信息
     * @param map
     * @return
     */
    public Map updataOrganize(Map map);


    /**
     * 修改用户信息
     * @param map
     * @return
     */
    public Map updataOrganizeByUserName(Map map);

    /**
     * 查找用户信息根据机构ID
     * @param map
     * @return
     */
    public Map findMemberByOrgId(Map map);
}
