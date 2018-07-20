package com.trs.service.impl;

import com.trs.core.util.StringUtil;
import com.trs.core.util.Util;
import com.trs.mapper.MemberMapperOnline;
import com.trs.mapper.RoleMapper;
import com.trs.model.Member;
import com.trs.model.Role;
import com.trs.service.MemberOnlineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MemberOnlineServiceImpl implements MemberOnlineService {

    @Resource
    private MemberMapperOnline memberMapperOnline;

    @Resource
    private RoleMapper roleMapper;


    @Override
    public Map add(Member member) {
        Map resultMap = new HashMap();
        memberMapperOnline.insert(member);
        resultMap.put("state", "0");
        resultMap.put("msg", "添加成功");
        return resultMap;
    }

    @Override
    public Map updatePassword(Member member) {
        Map resultMap = new HashMap();
        memberMapperOnline.updateByPrimaryKeySelective(member);
        resultMap.put("state", "0");
        resultMap.put("msg", "修改成功");
        return resultMap;
    }

    @Override
    public Member findMemberByUserName(String userName) {
        return memberMapperOnline.findMemberByUserName(userName);
    }

    @Override
    public Map findAllMember(Map map) {
        Map resultMap = new HashMap();
        List<Member> members = memberMapperOnline.listAllMember(map);
        int total = memberMapperOnline.total(map);
        resultMap.put("total", total);
        resultMap.put("rows", members);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));
        return resultMap;
    }

    @Override
    public Map findAllOrgainzeMember(Map map) {
        Map resultMap = new HashMap();
        List<Member> members = memberMapperOnline.listAllOrganizeMember(map);
        int total = memberMapperOnline.OrganizeTotal(map);
        resultMap.put("total", total);
        resultMap.put("rows", members);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));
        return resultMap;
    }



    @Override
    public Member findMemberById(Integer id) {
        return memberMapperOnline.selectByPrimaryKey(id);
    }

    @Override
    public Map changeStatus(Map map) {
        Map resultMap = new HashMap();
        List list = new ArrayList();

        List ids = (List) map.get("ids");
        String status = (String) map.get("status");

        for (int i = 0; i < ids.size(); i++) {
            int id = Integer.parseInt(String.valueOf(ids.get(i)));
            // Member member = memberMapper.selectByPrimaryKey(id);
            Map paramMap = new HashMap();
            paramMap.put("id", id);
            paramMap.put("status", status);
            paramMap.put("gmtModified", new Date());
            list.add(id);
            memberMapperOnline.changeStatus(paramMap);
        }
        resultMap.put("status", status);
        resultMap.put("total", ids.size());
        resultMap.put("chgtotal", list.size());
        return resultMap;
    }

    @Override
    public Map deleteMemberByid(List<Integer> ids) {
        Map resultMap = new HashMap();

        List list = new ArrayList();

        int id = 0;

        for (int i = 0; i < ids.size(); i++) {
            id = ids.get(i);
            Member member = memberMapperOnline.selectByPrimaryKey(id);

            if (0 == member.getStatus()) {
                resultMap.put("used", 1);
                continue;
            }
            memberMapperOnline.deleteByPrimaryKey(id);
            list.add(id);
        }
        resultMap.put("total", ids.size());
        resultMap.put("deltotal", list.size());
        resultMap.put("code", "0");
        return resultMap;
    }

    @Override
    public Map<String, String> changRoleId(Map map) {
        Map<String, String> resultMap = new HashMap<>();
        List<Integer> list = (List) map.get("ids");
        String roleId = (String) map.get("roleId");
        for (int i = 0; i < list.size(); i++) {
            Map paraMap = new HashMap();
            paraMap.put("id", list.get(i));
            paraMap.put("roleId", roleId);

            // 获取角色ID
            Member member = memberMapperOnline.selectByPrimaryKey(list.get(i));
            Integer roleId1 = member.getRoleId();
            // 通过角色ID查询会员表
            List<Member> memberByRoleId = memberMapperOnline.findMemberByRoleId(member);

            // 检查角色是否被使用
            if (memberByRoleId.size() == 0) {
                Role role = roleMapper.selectByPrimaryKey(roleId1);
                if (!StringUtil.isEmpty(role)) {
                    role.setUsed(1);
                    roleMapper.updateByPrimaryKey(role);
                }
            }
            Role role = roleMapper.selectByPrimaryKey(Integer.parseInt(roleId));
            if (!StringUtil.isEmpty(role)) {
                role.setUsed(0);
                roleMapper.updateByPrimaryKey(role);
            }
            memberMapperOnline.changRoleId(paraMap);
        }
        resultMap.put("code", "1");
        return resultMap;
    }

    @Override
    public Member findMemberById(int id) {
        return memberMapperOnline.selectByPrimaryKey(id);
    }

    @Override
    public Map updateInfo(Member member) {
        Map resultMap = new HashMap();
        Date date = new Date();
        member.setGmtModified(date);
        // 修改用户信息之前查询原数据信息
        Integer id = member.getId();
        Member user1 = memberMapperOnline.selectByPrimaryKey(id);
        //修改信息  密码不可为空
        member.setPwd(user1.getPwd());
        memberMapperOnline.updateByPrimaryKeySelective(member);
        resultMap.put("state", "0");
        resultMap.put("msg", "修改成功");

        return resultMap;
    }

    @Override
    public Map resetPwd(Map map) {
        Map resultMap = new HashMap();

        String id = (String) map.get("id");
        //String oldpassword = (String) map.get("oldpassword");
        String newpassword = (String) map.get("newpassword");

        Member member = memberMapperOnline.selectByPrimaryKey(Integer.parseInt(id));

        if (null == member) {
            resultMap.put("code", 1);
            resultMap.put("msg", "您要修改的用户不存在");
            return resultMap;
        }
        member.setGmtModified(new Date());
        member.setPwd(Util.toMD5(newpassword));

        memberMapperOnline.updateByPrimaryKey(member);
        resultMap.put("code", 0);
        return resultMap;
    }

    @Override
    public Map updataOrganize(Map map) {
        Map resultMap = new HashMap();
        memberMapperOnline.updataOrganize(map);
        resultMap.put("state", "0");
        resultMap.put("msg", "修改成功");
        return resultMap;
    }
    @Override
    public Map updataOrganizeByUserName(Map map) {
        Map resultMap = new HashMap();
        memberMapperOnline.updataOrganizeByUserName(map);
        resultMap.put("state", "0");
        resultMap.put("msg", "修改成功");
        return resultMap;
    }



    @Override
    public Map findMemberByOrgId(Map map) {
        Map resultMap = new HashMap();

        List<Member> members = memberMapperOnline.findMemberByOrgId(map);
        int total = memberMapperOnline.total(map);

        resultMap.put("total", total);
        resultMap.put("rows", members);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));

        return resultMap;
    }
}
