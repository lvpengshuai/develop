package com.trs.service.impl;

import com.trs.core.annotations.Log;
import com.trs.core.util.Config;
import com.trs.core.util.FileUtil;
import com.trs.mapper.MemberMapper;
import com.trs.mapper.OrganizeMapper;
import com.trs.mapper.PermissionMapper;
import com.trs.mapper.RoleMapper;
import com.trs.model.Member;
import com.trs.model.Organize;
import com.trs.model.Role;
import com.trs.model.RolePermission;
import com.trs.service.MemberService;
import com.trs.service.OrganizeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zly on 2017-5-11.
 */
@Service
public class OrganizeServiceImpl implements OrganizeService {

    @Resource
    private OrganizeMapper organizeMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private MemberService memberService;
    @Resource
    private MemberMapper memberMapper;

    private static final String STATIC_RESOURCE = Config.getKey("app.static.folder");


    @Override
    public List<Organize> selectAllIP() {
        return null;
    }

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_INSERT, targetType = com.trs.model.Log.LOG_TARGETTYPE_ORGANIZEMANAGER, description = "添加机构用户")
    @Override
    public Map addOrganizeUser(Map map) {
        Map resultMap = new HashMap();

        Organize organize = (Organize) map.get("organize");
        String[] permission = (String[]) map.get("permission");
        String memberId = (String) map.get("memberId");
        String filePath = (String) map.get("filePath");

        try {
            Organize o = organizeMapper.selectByName(organize.getName());
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

            organizeMapper.addOrganizeUser(organize);

            /*添加机构ip*/
            String ipStart = organize.getIpStart();
            String ipEnd = organize.getIpEnd();

            Map ipMap = new HashMap();
            Integer organizeId = organize.getId();

            ipMap.put("id", organizeId);
            ipMap.put("start", ipStart);
            ipMap.put("end", ipEnd);

            organizeMapper.addIp(ipMap);

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
                            memberService.updataOrganize(orMap);
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
                    memberService.updataOrganize(orMap);
                }


            }
            /*处理上传的文件*/
            if (filePath != null && !"".equals(filePath)) {
                String substring = filePath.substring(filePath.lastIndexOf(FileUtil.getPathWithSystem("/")));
                String targetPath = FileUtil.getPathWithSystem(STATIC_RESOURCE + "/organize/" + organizeId);
                File staticFile = new File(targetPath);
                if (!staticFile.exists()) {
                    staticFile.mkdirs();
                }
                FileUtil.copy(FileUtil.getPathWithSystem(filePath), FileUtil.getPathWithSystem(targetPath + substring));
                Organize org = organizeMapper.selectOrganizeById(organizeId);
                org.setFile(FileUtil.getPathWithSystem("/organize/" + organizeId + substring));
                organizeMapper.updateOrganize(org);
            }


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

        Organize organize = organizeMapper.selectOrganizeById(id);
        List ipList = organizeMapper.selectIp(id);
        List adminList = organizeMapper.selectOrganizeAdmin(id);

        resultMap.put("organize", organize);
        resultMap.put("organizeIp", ipList);
        resultMap.put("organizeAdmin", adminList);

        return resultMap;
    }

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_SELECT, targetType = com.trs.model.Log.LOG_TARGETTYPE_ORGANIZEMANAGER, description = "查看机构用户")
    @Override
    public Map getAllOrganizes(Map map) {
        Map resultMap = new HashMap();

        List<Organize> organizes = organizeMapper.selectAllOrganizes(map);
        for (Organize organize : organizes) {
            Integer id = organize.getId();
            List list = organizeMapper.selectIp(id);
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

        int total = organizeMapper.total(map);

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
    public List getUsedIp() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return organizeMapper.getUsedIp(dateString);
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

            organizeMapper.updateOrganize(organize);
            organizeMapper.deleteIp(organize.getId());

            Map ipMap = new HashMap();
            ipMap.put("id", organize.getId());

            if (ipStart.contains(",") && ipEnd.contains(",")) {
                String[] ips = ipStart.split(",");
                String[] ipe = ipEnd.split(",");

                for (int i = 0; i < ips.length; i++) {
                    ipMap.put("start", ips[i]);
                    ipMap.put("end", ipe[i]);
                    organizeMapper.addIp(ipMap);
                }
            } else {
                ipMap.put("start", ipStart);
                ipMap.put("end", ipEnd);
                organizeMapper.addIp(ipMap);
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


            organizeMapper.deleteOrganizeAdmin(organizeId);
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
                            memberService.updataOrganize(orMap);
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
                    memberService.updataOrganize(orMap);
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

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_ENABLE, targetType = com.trs.model.Log.LOG_TARGETTYPE_ORGANIZEMANAGER, description = "启用或禁用机构用户")
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
            organizeMapper.changeStatus(paramMap);
            tmp++;
        }


        resultMap.put("status", status);
        resultMap.put("total", tmp);

        return resultMap;
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
        Member userByName=memberMapper.findMemberByUserName(username);
        if(userByName.getOrganization()!=null){

            if(orgId == -1){
                resultMap.put("code", 1);
                resultMap.put("msg", "账户“" + username + "”已经是其他机构的会员，无法添加。");
                resultMap.put("memberId", id);
                resultMap.put("memberName", username);
            }else{
                if(userByName.getOrganizaId()!=null&&userByName.getOrganization()!=map.get("orgId")){
                    resultMap.put("code", 1);
                    resultMap.put("msg", "账户“" + username + "”已经是其他机构的会员，无法添加。");
                    resultMap.put("memberId", id);
                    resultMap.put("memberName", username);
                }
            }
        }else {
            resultMap.put("code", 0);
        }
        return resultMap;
    }

    @Override
    public Map getOrganizeIp(String ip) {
        return null;
    }

    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_DELETE, targetType = com.trs.model.Log.LOG_TARGETTYPE_ORGANIZEMANAGER, description = "删除机构用户")
    @Override
    public Map deleteOrganizeById(List<Integer> ids) {

        Map resultMap = new HashMap();

        int tmp = 0;
        for (Integer id : ids) {
            Organize organize = organizeMapper.selectOrganizeById(id);
            if (organize.getStatus() == 1) {
                deleteFile(STATIC_RESOURCE + organize.getFile());
                organizeMapper.deleteOrganizeById(id);
                organizeMapper.deleteIp(id);
                organizeMapper.deleteOrganizeAdmin(id);
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

    private void deleteFile(String path) {
        path = FileUtil.getPathWithSystem(path);
        if (FileUtil.fileisExist(path)) {
            FileUtil.delete(new File(path));
        }
    }
    /*查找机构用户*/


}
