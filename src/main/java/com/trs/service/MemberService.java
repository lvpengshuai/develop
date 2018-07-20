package com.trs.service;

import com.trs.model.Member;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/3/31.
 */
public interface MemberService {

    /**
     * 查询所有会员
     * @param map
     * @return
     */
    public Map findAllMember(Map map);

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

    /**
     * 重置用户密码
     * @param map
     * @return
     */
    public Map resetPwd(Map map);

    /**
     * 更改权限
     * @param map
     */
    Map<String,String> changRoleId(Map map);

    /**
     * 查找角色是否被占用
     * @param member
     * @return
     */
    public List<Member> findMemberByRoleId(Member member);

    /**
     * 查询用户
     * @param userName
     * @return
     */
    public Member findMemberByUserName(String userName);

    /**
     * 添加会员
     * @param member
     */
    public Map add(Member member);

    /**
     * 同步添加会员
     * @param member
     */
    public void synAdd(Member member);

    /**
     * 更新会员
     * @param member
     */
    public Map updateInfo(Member member);

    public Map updatePassword(Member member);

    public Member findMemberById(int id);

    /**
     * IDS注册
     * @param member
     * @return
     */
    public Map<String,Object> idsRegister(Member member);

    /**
     * IDS修改资料
     * @param member
     * @return
     */
    public Map<String,Object> idsUpdateInfo(Member member);

    /**
     * IDS修改密码
     * @param userName
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public Map<String,Object> idsUpdatePwd(String userName, String oldPwd, String newPwd, String pwdAgain);

    /**
     * ids找回密码
     * @param userName
     * @return
     */
    public Map<String, Object> idsFindPwd(String userName,String password,String password_again);

    /**
     * ids删除用户
     * @param userName
     * @return
     */
    public Map<String, Object> idsDelete(String userName);

    /**
     * ids检测接受方是否做过绑定
     * @param uid
     * @param authSite
     * @param accessToken
     * @return
     */
    public Map<String, Object> idsCheckAccountMapping(String uid, String authSite, String accessToken);

    /**
     * ids绑定第三方账户
     * @param uid
     * @param authSite
     * @param accessToken
     * @param userName
     * @param password
     * @return
     */
    public Map<String, Object> idsBindUser(String uid, String authSite, String accessToken, String userName, String password);

    /**
     * ids第三方登录
     * @param uid
     * @param authSite
     * @param accessToken
     * @param coSessionId
     * @return
     */
    public Map<String, Object> idsOauthLogin(String uid, String authSite, String accessToken, String coSessionId);

    /**
     * ids登录接口
     * @param userName
     * @param sessionID
     * @return
     */
    public Map<String, Object> idsLogin(String userName, String sessionID);



    /**
     * 检测原密码是否正确
     * @param map
     * @return
     */
    public boolean findMemberByUserNameAndPwd(Map map, HttpSession session);

    /**
     * 查询会员
     * @param email
     * @return
     */
    List<Member> findMemberByEmail(String email);

    /**
     * 查询会员
     * @param id
     * @return
     */
    Member findMemberById(Integer id);

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
     * 修改用户信息
     * @param map
     * @return
     */
    public Map findMemberByOrgId(Map map);

}
