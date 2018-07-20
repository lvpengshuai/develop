package com.trs.service.impl;

import com.trs.core.util.Config;
import com.trs.core.util.FileUtil;
import com.trs.core.util.IPUtil;
import com.trs.core.util.Util;
import com.trs.mapper.MemberMapperOnline;
import com.trs.mapper.OrganizeMapperOnline;
import com.trs.mapper.PermissionMapper;
import com.trs.mapper.RoleMapper;
import com.trs.model.*;
import com.trs.service.MemberOnlineService;
import com.trs.service.MemberService;
import com.trs.service.OrganizeOnlineService;
import com.trs.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * Created by zly on 2017-5-11.
 */
@Service
public class OrganizeOnlineImplService implements OrganizeOnlineService {

    @Resource
    private OrganizeMapperOnline organizeMapperOnline;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private MemberMapperOnline memberMapperOnline;

    @Resource
    private PermissionService permissionService;

    @Resource
    private MemberOnlineService memberOnlineService;

    @Resource
    private MemberService memberService;

    private static final String STATIC_RESOURCE = Config.getKey("app.static.folder");

    @Override
    public Map addOrganizeUser(Map map) {
        Map resultMap = new HashMap();

        Organize organize = (Organize) map.get("organize");
        String[] permission = (String[]) map.get("permission");
        String memberId = (String) map.get("memberId");
        String filePath = (String) map.get("filePath");

        try {
            Organize o = organizeMapperOnline.selectByName(organize.getName());
            if (o != null) {
                resultMap.put("state", 2);
                resultMap.put("msg", "该机构已经被注册！");
                return resultMap;
            }


            /*创建机构角色*/
            Role role = new Role();
            role.setName(organize.getName());
            role.setStatus(0);
            role.setAttribute(3);
            role.setRemark(organize.getName() + "的相关权限");
            role.setUsed(0);
            roleMapper.insertSelective(role);

            int roleId = role.getId();

            if (permission != null) {
                for (String s : permission) {
                    if (!"".equals(s)) {
                        int r = Integer.parseInt(s);
                        RolePermission rp = new RolePermission();
                        rp.setRoleId(roleId);
                        rp.setPermissionId(r);
                        permissionMapper.add(rp);
                    }
                }
            }

            organize.setRoleId(roleId);
            organize.setGmtCreate(new Date());
            organize.setGmtModified(new Date());

            organizeMapperOnline.addOrganizeUser(organize);

            /*添加机构ip*/
            String ipStart = organize.getIpStart();
            String ipEnd = organize.getIpEnd();

            Map ipMap = new HashMap();
            Integer organizeId = organize.getId();

//
//            ipMap.put("start", ipStart);
//            ipMap.put("end", ipEnd);

            if (ipStart.contains(",") && ipEnd.contains(",")) {
                String[] ips = ipStart.split(",");
                String[] ipe = ipEnd.split(",");

                for (int i = 0; i < ips.length; i++) {
                    ipMap.put("id", organizeId);
                    ipMap.put("start", ips[i]);
                    ipMap.put("end", ipe[i]);
                    organizeMapperOnline.addIp(ipMap);
                }
            } else {
                ipMap.put("id", organizeId);
                ipMap.put("start", ipStart);
                ipMap.put("end", ipEnd);
                organizeMapperOnline.addIp(ipMap);
            }

            //organizeMapperOnline.addIp(ipMap);

            /*添加机构所属用户*/
            if (memberId != null && !"".equals(memberId)) {
                if (memberId.contains(",")) {
                    Set<String> set = new HashSet();
                    for (String ss : memberId.split(",")) {
                        set.add(ss);
                    }
                    for (String s : set) {
                        if (!"".equals(s)) {
                            int i = Integer.parseInt(s);
//                            Map memberMap = new HashMap();
//                            memberMap.put("organizeId", organizeId);
//                            memberMap.put("memberId", i);
//                            memberMap.put("isAdmin", 0);
//                            organizeMapper.addOrganizeAdmin(memberMap);
////                            Member memberById = memberService.findMemberById(i);
////                            memberById.setOrganizaId(organizeId);
////                            memberService.updateInfo(memberById);
                            Map orMap = new HashMap();
                            orMap.put("organizaId", organizeId);
                            orMap.put("id", i);
                            orMap.put("organization", organize.getName());
                            memberMapperOnline.updataOrganize(orMap);
                        }
                    }
                } else {
//                    int i = Integer.parseInt(memberId);
//                    Map memberMap = new HashMap();
//                    memberMap.put("organizeId", organizeId);
//                    memberMap.put("memberId", i);
//                    memberMap.put("isAdmin", 0);
//                    organizeMapper.addOrganizeAdmin(memberMap);
////                    Member memberById = memberService.findMemberById(i);
////                    memberById.setOrganizaId(organizeId);
////                    memberService.updateInfo(memberById);
                    Map orMap = new HashMap();
                    orMap.put("organizaId", organizeId);
                    orMap.put("id", memberId);
                    orMap.put("organization", organize.getName());
                    memberMapperOnline.updataOrganize(orMap);
                }


            }
//            /*处理上传的文件*/
//            if (filePath != null && !"".equals(filePath)) {
//                String substring = filePath.substring(filePath.lastIndexOf(FileUtil.getPathWithSystem("/")));
//                String targetPath = FileUtil.getPathWithSystem(STATIC_RESOURCE + "/organize/" + organizeId);
//                File staticFile = new File(targetPath);
//                if (!staticFile.exists()) {
//                    staticFile.mkdirs();
//                }
//                FileUtil.copy(FileUtil.getPathWithSystem(filePath), FileUtil.getPathWithSystem(targetPath + substring));
//                Organize org = organizeMapper.selectOrganizeById(organizeId);
//                org.setFile(FileUtil.getPathWithSystem("/organize/" + organizeId + substring));
//                organizeMapper.updateOrganize(org);
//            }
            resultMap.put("state", "0");
        } catch (Exception e) {
            resultMap.put("state", "1");

            e.printStackTrace();
        }
        return resultMap;
    }

    @Override
    public Map getOrganizeById(int id) {
        Map resultMap = new HashMap();

        Organize organize = organizeMapperOnline.selectOrganizeById(id);
        List ipList = organizeMapperOnline.selectIp(id);
        List adminList = organizeMapperOnline.selectOrganizeAdmin(id);

        resultMap.put("organize", organize);
        resultMap.put("organizeIp", ipList);
        resultMap.put("organizeAdmin", adminList);

        return resultMap;
    }

    @Override
    public Map getAllOrganizes(Map map) {
        Map resultMap = new HashMap();

        List<Organize> organizes = organizeMapperOnline.selectAllOrganizes(map);
        for (Organize organize : organizes) {
            Integer id = organize.getId();
            List list = organizeMapperOnline.selectIp(id);
            if (list.size() != 0) {
                String ipStart = "";
                String ipEnd = "";
                for (int i = 0; i < list.size(); i++) {
                    Map m = (Map) list.get(i);
                    ipStart += m.get("ipStart") + ";";
                    ipEnd += m.get("ipEnd") + ";";
                }
                organize.setIpStart(ipStart);
                organize.setIpEnd(ipEnd);
            }
        }

        int total = organizeMapperOnline.total(map);

        resultMap.put("total", total);
        resultMap.put("rows", organizes);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));

        return resultMap;
    }

    @Override
    public Map updateOrganize(Map map) {
        Map resultMap = new HashMap();

        Organize organize = (Organize) map.get("organize");
        String[] permission = (String[]) map.get("permission");
        String memberId = (String) map.get("memberId");
        String filePath = (String) map.get("filePath");

        try {
            int organizeId = organize.getId();

            /*处理上传的文件*/
            if (filePath != null && !"".equals(filePath)) {
                String substring = filePath.substring(filePath.lastIndexOf(FileUtil.getPathWithSystem("/")));
                String targetPath = FileUtil.getPathWithSystem(STATIC_RESOURCE + "/organize/" + organizeId);
                File staticFile = new File(targetPath);
                if (!staticFile.exists()) {
                    staticFile.mkdirs();
                }
                deleteFile(STATIC_RESOURCE + organize.getFile());

                FileUtil.copy(FileUtil.getPathWithSystem(filePath), FileUtil.getPathWithSystem(targetPath + substring));
                organize.setFile(FileUtil.getPathWithSystem("/organize/" + organizeId + substring));
            }

            organize.setGmtModified(new Date());

            String ipStart = organize.getIpStart();
            String ipEnd = organize.getIpEnd();

            organizeMapperOnline.updateOrganize(organize);
            organizeMapperOnline.deleteIp(organize.getId());

            Map ipMap = new HashMap();
            ipMap.put("id", organize.getId());

            if (ipStart.contains(",") && ipEnd.contains(",")) {
                String[] ips = ipStart.split(",");
                String[] ipe = ipEnd.split(",");

                for (int i = 0; i < ips.length; i++) {
                    ipMap.put("start", ips[i]);
                    ipMap.put("end", ipe[i]);
                    organizeMapperOnline.addIp(ipMap);
                }
            } else {
                ipMap.put("start", ipStart);
                ipMap.put("end", ipEnd);
                organizeMapperOnline.addIp(ipMap);
            }

            int roleId = organize.getRoleId();
            permissionMapper.deletePermissionById(roleId);
            if (permission != null) {
                for (String s : permission) {
                    if (!"".equals(s)) {
                        int r = Integer.parseInt(s);
                        RolePermission rp = new RolePermission();
                        rp.setRoleId(roleId);
                        rp.setPermissionId(r);
                        permissionMapper.add(rp);
                    }
                }
            }


            organizeMapperOnline.deleteOrganizeAdmin(organizeId);
            if (memberId != null && !"".equals(memberId)) {
                if (memberId.contains(",")) {
                    Set<String> set = new HashSet();
                    for (String ss : memberId.split(",")) {
                        set.add(ss);
                    }
                    for (String s : set) {
                        if (!"".equals(s)) {
                            int i = Integer.parseInt(s);
//                            Map memberMap = new HashMap();
//                            memberMap.put("organizeId", organizeId);
//                            memberMap.put("memberId", i);
//                            memberMap.put("isAdmin", 0);
//                            organizeMapper.addOrganizeAdmin(memberMap);
////                            Member memberById = memberService.findMemberById(i);
////                            memberById.setOrganizaId(organizeId);
////                            memberService.updateInfo(memberById);
                            Map orMap = new HashMap();
                            orMap.put("organizaId", organizeId);
                            orMap.put("id", i);
                            orMap.put("organization", organize.getName());
                            memberMapperOnline.updataOrganize(orMap);
                        }
                    }
                } else {
//                    int i = Integer.parseInt(memberId);
//                    Map memberMap = new HashMap();
//                    memberMap.put("organizeId", organizeId);
//                    memberMap.put("memberId", i);
//                    memberMap.put("isAdmin", 0);
//                    organizeMapper.addOrganizeAdmin(memberMap);
////                    Member memberById = memberService.findMemberById(i);
////                    memberById.setOrganizaId(organizeId);
////                    memberService.updateInfo(memberById);
                    Map orMap = new HashMap();
                    orMap.put("organizaId", organizeId);
                    orMap.put("id", memberId);
                    orMap.put("organization", organize.getName());
                    memberMapperOnline.updataOrganize(orMap);
                }

            }


            resultMap.put("state", "0");
        } catch (Exception e) {
            resultMap.put("state", "1");
            resultMap.put("msg", "1");
            e.printStackTrace();
        }


        return resultMap;
    }

    @Override
    public Map changeStatus(Map map) {
        Map resultMap = new HashMap();

        int tmp = 0;
        List<Integer> ids = (List) map.get("ids");
        String status = (String) map.get("status");

        for (Integer id : ids) {
            Map paramMap = new HashMap();
            paramMap.put("id", id);
            paramMap.put("status", status);
            paramMap.put("gmtModified", new Date());
            organizeMapperOnline.changeStatus(paramMap);
            tmp++;
        }
        resultMap.put("status", status);
        resultMap.put("total", tmp);

        return resultMap;
    }

    @Override
    public Map deleteOrganizeById(List<Integer> ids) {
        Map resultMap = new HashMap();

        int tmp = 0;
        for (Integer id : ids) {
            Organize organize = organizeMapperOnline.selectOrganizeById(id);
            if (organize.getStatus() == 1) {
                deleteFile(STATIC_RESOURCE + organize.getFile());
                organizeMapperOnline.deleteOrganizeById(id);
                organizeMapperOnline.deleteIp(id);
                organizeMapperOnline.deleteOrganizeAdmin(id);
                int roleId = organize.getRoleId();
                roleMapper.deleteByPrimaryKey(roleId);
                permissionMapper.deletePermissionById(roleId);
                tmp++;
            }
        }

        resultMap.put("total", tmp);
        resultMap.put("code", "0");

        return resultMap;
    }

    @Override
    public Map deleteOrganizeById1(List<Integer> ids) {
        Map resultMap = new HashMap();

        int tmp = 0;
        for (Integer id : ids) {
            Organize organize = organizeMapperOnline.selectOrganizeById(id);
            deleteFile(STATIC_RESOURCE + organize.getFile());
            organizeMapperOnline.deleteOrganizeById(id);
            organizeMapperOnline.deleteIp(id);
            organizeMapperOnline.deleteOrganizeAdmin(id);
            int roleId = organize.getRoleId();
            roleMapper.deleteByPrimaryKey(roleId);
            permissionMapper.deletePermissionById(roleId);
            tmp++;
        }

        resultMap.put("total", tmp);
        resultMap.put("code", "0");

        return resultMap;
    }


    @Override
    public List getUsedIp() {
        return null;
    }

    @Override
    public Map checkMemberIsAdmin(Map map) {
        Map resultMap = new HashMap();

        Integer id = Integer.parseInt(String.valueOf(map.get("id")));
        String username = String.valueOf(map.get("username"));
        Integer orgId = -1;

        if (null != map.get("orgId") && !"".equals(map.get("orgId"))) {
            orgId = Integer.parseInt(String.valueOf(map.get("orgId")));
        }

//        List<Map> list = organizeMapper.selectOrganizeAdminByMember(id);
//        if (list.size() > 0) {
//            for (Map m : list) {
//                if (orgId != Integer.parseInt(String.valueOf(m.get("organizeId")))) {
//                    resultMap.put("code", 1);
//                    resultMap.put("msg", "账户“" + username + "”已经是其他机构的管理员，无法添加。");
//                    resultMap.put("memberId", id);
//                    resultMap.put("memberName", username);
//                } else if (orgId == -1) {
//                    resultMap.put("code", 0);
//                } else {
//                    resultMap.put("code", 0);
//                }
//            }
//        }
        Member userByName=memberMapperOnline.findMemberByUserName(username);
        Organize organize = organizeMapperOnline.getOrganizeByBid(userByName.getOrganizaId());
        if (organize == null){
            userByName.setOrganization("");
            userByName.setOrganizaId(null);
        }
        if(userByName.getOrganization()!=null && userByName.getOrganization()!="" && userByName.getOrganizaId() != orgId){
            resultMap.put("code", 1);
            resultMap.put("msg", "账户“" + username + "”已经是其他机构的会员，无法添加。");
            resultMap.put("memberId", id);
            resultMap.put("memberName", username);
        }else {
            resultMap.put("code", 0);
        }
        return resultMap;
    }

    @Override
    public String selectCodeByID(Integer id) {
        return organizeMapperOnline.selectCodeByID(id);
    }

    @Override
    public String selectBid(Integer id) {
        return organizeMapperOnline.selectBid(id);
    }

    @Override
    public Map addOnlineUserOrganize(Map map) {
        memberMapperOnline.updataOrganize(map);
        return null;
    }

    @Override
    public Organize selectOrganById(Integer id) {
        return organizeMapperOnline.selectOrganizeById(id);
    }

    private void deleteFile(String path) {
        path = FileUtil.getPathWithSystem(path);
        if (FileUtil.fileisExist(path)) {
            FileUtil.delete(new File(path));
        }
    }

    @Override
    public List<Organize> selectAllIP() {
        return organizeMapperOnline.selectAllIP();
    }

    @Override
    public List<Organize> selectById(Integer orgID) {
        return organizeMapperOnline.selectById(orgID);
    }

    @Override
    public Organize getOrganizeByBid(Integer bid) {
        return organizeMapperOnline.getOrganizeByBid(bid);
    }

    @Override
    public List<Organize> getOrganizeByBidOnline(Integer bid) {
        List<Organize> or=organizeMapperOnline.getOrganizeByBidOnline(bid);
        return or;
    }


    @Override
    public Organize getOragnizeByip(String startIp, String endIp) {
        return organizeMapperOnline.getOragnizeByip(startIp,endIp);
    }
    @Override
    public List<Organize> getOrganizesList() {
        Map resultMap = new HashMap();
        List<Organize> organizes=organizeMapperOnline.getOrganizesList();
        return organizes;
    }

    @Override
    public Map getUserOrganzeRole(HttpSession session, String name, String dateTime, String iiiPP, HttpServletRequest request, String ONLINESTATUS) {
        System.out.println(name);
        Util.log("是否进入权限判断-------------权限名称", "authorization", 0);
        Util.log(name + "随机数：" + dateTime, "authorization", 0);
        Map resultMap = new HashMap();
        Object username = session.getAttribute("userName");
        if (ONLINESTATUS.equals("ON")) {
            if (resultMap.size() == 0) {
                Map organizeMap = new HashMap();
                Map map = new HashMap();
                if (username == null || username == "") {
                    if (name.equals("收藏") || name.equals("关注") || name.equals("拼接")) {
                        resultMap.put("status", 3);
                        return resultMap;
                    }
                    List<Organize> ip = selectAllIP();
                    Util.log("未登录权限判断开始--------------", "login-ip", 0);
                    Util.log(IPUtil.getIpAddr(request), "login-ip", 0);
                    if (ip.size() != 0) {
                        for (int ii = 0; ii < ip.size(); ii++) {
                            //获取起始ip
                            String startIp = ip.get(ii).getIpStart();
                            //获取结束ip
                            String endIp = ip.get(ii).getIpEnd();

                            String id = ip.get(ii).getOrgName();
                            if (startIp != null && endIp != null && startIp != "" && endIp != "") {
                                //判断当前用户的ip是否在机构ip下
                                if (IPUtil.getIp2long(startIp) <= IPUtil.getIp2long(IPUtil.getIpAddr(request)) && IPUtil.getIp2long(IPUtil.getIpAddr(request)) <= IPUtil.getIp2long(endIp)) {
                                    List<Organize> org = selectById(Integer.valueOf(id));
                                    if (org.size() != 0) {
                                        //通过角色ip查找对应权限id
                                        List<RolePermission> organizePermission = permissionService.listPermission(org.get(0).getRoleId());
                                        //遍历所有的权限id
                                        for (RolePermission rolePermission : organizePermission) {
                                            System.out.println(rolePermission.getPermissionId());
                                            //根据权限id查找所有对应的权限
                                            List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                                            for (Permission per : permission) {
                                                if (per.getName().equals(name)) {
                                                    resultMap.put("status", 0);
                                                    return resultMap;
                                                }
                                                Util.log(per.getName(), "authorization", 0);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    resultMap.put("status", 2);
                    return resultMap;
                } else {
                    //通过用户名获取用户信息
                    Member member = memberOnlineService.findMemberByUserName(String.valueOf(username));
                    //验证用户是否存在机构
                    if (member.getOrganizaId() != null) {
                        //获取机构ip
                        Map organizeIp = getOrganizeById(member.getOrganizaId());
                        List<Map> organize = (List) organizeIp.get("organizeIp");
                        //遍历
                        System.out.println(organize);
                        for (Map organizes : organize) {
                            //获取起始ip
                            String startIp = (String) organizes.get("ipStart");
                            //获取结束ip
                            String endIp = (String) organizes.get("ipEnd");
                            if (startIp != null && endIp != null && startIp != "" && endIp != "") {
                                //判断当前用户的ip是否在机构ip下
                                if (IPUtil.getIp2long(startIp) <= IPUtil.getIp2long(IPUtil.getIpAddr(request)) && IPUtil.getIp2long(IPUtil.getIpAddr(request)) <= IPUtil.getIp2long(endIp)) {
                                    /*如果存在则查找机构角色
                                     *
                                     * 并且查找自身角色
                                     * */
                                    //获取机构信息
                                    Organize getorganize = (Organize) organizeIp.get("organize");
                                    //通过角色ip查找对应权限id
                                    List<RolePermission> organizePermission = permissionService.listPermission(getorganize.getRoleId());
                                    //遍历所有的权限id
                                    for (RolePermission rolePermission : organizePermission) {
                                        System.out.println(rolePermission.getPermissionId());
                                        //根据权限id查找所有对应的权限
                                        List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                                        for (Permission per : permission) {
                                            if (per.getName().equals(name)) {
                                                resultMap.put("status", 0);
                                                return resultMap;
                                            }
                                            System.out.println(per.getName());
                                        }
                                    }
                                }
                                //如果上面的方法没有获取到对应权限则查找自身角色
                                //通过角色ip查找对应权限id
                                List<RolePermission> userPermission = permissionService.listPermission(member.getRoleId());
                                //遍历所有的权限id
                                for (RolePermission rolePermission : userPermission) {
                                    System.out.println(rolePermission.getPermissionId());
                                    //根据权限id查找所有对应的权限
                                    List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                                    for (Permission per : permission) {
                                        if (per.getName().equals(name)) {
                                            resultMap.put("status", 0);
                                            return resultMap;
                                        }
                                        System.out.println(per.getName());
                                    }
                                }
                            }

                            System.out.println(IPUtil.getServerIp() + "====" + IPUtil.getIp2long(startIp) + "====" + IPUtil.getIp2long(endIp));
                        }
                    } else {
                        //不存在则查找自身角色
                        //通过角色ip查找对应权限id
                        List<RolePermission> userPermission = permissionService.listPermission(member.getRoleId());
                        //遍历所有的权限id
                        for (RolePermission rolePermission : userPermission) {
                            System.out.println(rolePermission.getPermissionId());
                            //根据权限id查找所有对应的权限
                            List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                            for (Permission per : permission) {
                                if (per.getName().equals(name)) {
                                    resultMap.put("status", 0);
                                    return resultMap;
                                }
                                System.out.println(per.getName());
                            }
                        }
                    }
                    resultMap.put("status", 2);
                    return resultMap;
                }
            }


        } else {
            //通过用户名获取用户信息
            Member member = memberService.findMemberByUserName(String.valueOf(username));

            if (member == null) {
                resultMap.put("status", 1);
                return resultMap;
            }

            //验证用户是否存在机构
            if (member.getOrganizaId() != null) {
                //获取机构ip
                Map organizeIp = getOrganizeById(member.getOrganizaId());
                List<Map> organize = (List) organizeIp.get("organizeIp");
                //遍历
                System.out.println(organize);
                for (Map organizes : organize) {
                    //获取起始ip
                    String startIp = (String) organizes.get("ipStart");
                    //获取结束ip
                    String endIp = (String) organizes.get("ipEnd");
                    if (startIp != null && endIp != null && startIp != "" && endIp != "") {
                        //判断当前用户的ip是否在机构ip下
                        if (IPUtil.getIp2long(startIp) <= IPUtil.getIp2long(IPUtil.getIpAddr(request)) && IPUtil.getIp2long(IPUtil.getIpAddr(request)) <= IPUtil.getIp2long(endIp)) {
                            /*如果存在则查找机构角色
                             *
                             * 并且查找自身角色
                             * */
                            //获取机构信息
                            Organize getorganize = (Organize) organizeIp.get("organize");
                            //通过角色ip查找对应权限id
                            List<RolePermission> organizePermission = permissionService.listPermission(getorganize.getRoleId());
                            //遍历所有的权限id
                            for (RolePermission rolePermission : organizePermission) {
                                System.out.println(rolePermission.getPermissionId());
                                //根据权限id查找所有对应的权限
                                List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                                for (Permission per : permission) {
                                    if (per.getName().equals(name)) {
                                        resultMap.put("status", 0);
                                        return resultMap;
                                    }
                                    System.out.println(per.getName());
                                }
                            }
                        }
                    }


                    System.out.println(IPUtil.getServerIp() + "====" + IPUtil.getIp2long(startIp) + "====" + IPUtil.getIp2long(endIp));
                }
                //如果上面的方法没有获取到对应权限则查找自身角色
                //通过角色ip查找对应权限id
                List<RolePermission> userPermission = permissionService.listPermission(member.getRoleId());
                //遍历所有的权限id
                for (RolePermission rolePermission : userPermission) {
                    System.out.println(rolePermission.getPermissionId());
                    //根据权限id查找所有对应的权限
                    List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                    for (Permission per : permission) {
                        if (per.getName().equals(name)) {
                            resultMap.put("status", 0);
                            return resultMap;
                        }
                        System.out.println(per.getName());
                    }
                }
            } else {
                //不存在则查找自身角色
                //通过角色ip查找对应权限id
                List<RolePermission> userPermission = permissionService.listPermission(member.getRoleId());
                //遍历所有的权限id
                for (RolePermission rolePermission : userPermission) {
                    System.out.println(rolePermission.getPermissionId());
                    //根据权限id查找所有对应的权限
                    List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                    for (Permission per : permission) {
                        if (per.getName().equals(name)) {
                            resultMap.put("status", 0);
                            return resultMap;
                        }
                        System.out.println(per.getName());
                    }
                }
            }

        }
        resultMap.put("status", 2);
        return resultMap;
    }

}
