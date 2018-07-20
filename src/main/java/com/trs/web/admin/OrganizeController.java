package com.trs.web.admin;

import com.trs.core.util.Config;
import com.trs.core.util.DateUtil;
import com.trs.core.util.FileUtil;
import com.trs.core.util.IPUtil;
import com.trs.model.Member;
import com.trs.model.Organize;
import com.trs.model.Permission;
import com.trs.model.RolePermission;
import com.trs.service.*;
import com.trs.service.impl.OrganizeOnlineImplService;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by zly on 2017-5-11.
 */
@Controller
public class OrganizeController extends AbstractAdminController {

    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");

    //请求url
    private static final String USERURL = Config.getKey("zas.userUrl");

    @Resource
    private OrganizeService organizeService;

    @Resource
    private OrganizeOnlineService organizeOnlineService;

    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private MemberService memberService;

    @Resource
    private MemberOnlineService memberOnlineService;


    /**
     * 跳转到机构用户页面
     *
     * @return
     */
    @com.trs.core.annotations.Permission(url = "/organize")
    @RequestMapping(value = "/organize", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/admin/organize/organize");
        modelAndView.addObject("title", "机构用户");
        modelAndView.addObject("online", ONLINESTATUS);
        return modelAndView;
    }

    /**
     * 查询所有机构用户
     */
    @ResponseBody
    @RequestMapping(value = "/organizes", method = RequestMethod.GET)
    public Map organizes(HttpServletRequest request) {

        if (ONLINESTATUS.equals("ON")) {
            String pageSize = request.getParameter("pageSize");
            String currPage = request.getParameter("currPage");
            String search = request.getParameter("search");
            String sort = request.getParameter("sort");
            String order = request.getParameter("order");
            String state = request.getParameter("state");

            Map map = new HashMap();
            map.put("pageSize", Integer.parseInt(pageSize));
            map.put("currPage", Integer.parseInt(currPage));
            map.put("search", search);
            map.put("sort", sort);
            map.put("order", order);
            map.put("state", state);

            Map resultMap = organizeOnlineService.getAllOrganizes(map);
            return resultMap;
        } else {
            String pageSize = request.getParameter("pageSize");
            String currPage = request.getParameter("currPage");
            String search = request.getParameter("search");
            String sort = request.getParameter("sort");
            String order = request.getParameter("order");
            String state = request.getParameter("state");

            Map map = new HashMap();
            map.put("pageSize", Integer.parseInt(pageSize));
            map.put("currPage", Integer.parseInt(currPage));
            map.put("search", search);
            map.put("sort", sort);
            map.put("order", order);
            map.put("state", state);

            Map resultMap = organizeService.getAllOrganizes(map);
            return resultMap;
        }
    }

    /**
     * 跳转到添加/修改页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/organize/{id}", method = RequestMethod.GET)
    public ModelAndView toAdd(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView();

        StringBuffer sb = new StringBuffer();
        StringBuffer append = new StringBuffer();
        /*
        * 在线模式
        * */
        if (ONLINESTATUS.equals("ON")) {
            if ("add".equals(id)) {
                modelAndView.addObject("title", "机构用户");
                modelAndView.addObject("stitle", "添加机构");
            } else {
                modelAndView.addObject("title", "机构用户");
                modelAndView.addObject("stitle", "修改机构");

                Map organizeById = organizeOnlineService.getOrganizeById(Integer.parseInt(id));

                Organize organize = (Organize) organizeById.get("organize");
                String s = DateUtil.date2String(organize.getExpiration(), "yyyy-MM-dd");

                List organizeIp = (List) organizeById.get("organizeIp");
                List organizeAdmin = (List) organizeById.get("organizeAdmin");
                List organizeAdmins = new ArrayList();

                for (int i = 0; i < organizeAdmin.size(); i++) {
                    Map map = (Map) organizeAdmin.get(i);
                    int memberId = (int) map.get("memberId");
                    Member member = memberOnlineService.findMemberById(memberId);
                    if (member != null) {
                        organizeAdmins.add(member);
                    }
                }

                List<RolePermission> rolePermissions = permissionService.listPermission(organize.getRoleId());
                if (rolePermissions.size() > 0) {
                    for (int i = 0; i < rolePermissions.size(); i++) {
                        int permissionId = rolePermissions.get(i).getPermissionId();
                        append = sb.append(rolePermissions.get(i).getPermissionId()).append(";");
                    }
                    modelAndView.addObject("functions", append.substring(0, append.toString().lastIndexOf(";")));
                }
                StringBuilder organizeIpStringBuilder = new StringBuilder();
                for (int i = 0; i < organizeIp.size(); i++) {
                    Map<String, String> map = (Map<String, String>) organizeIp.get(i);
                    String ipStart = map.get("ipStart");
                    String ipEnd = map.get("ipEnd");
                    if (ipStart.equals(ipEnd)) {
                        organizeIpStringBuilder.append(ipStart + "\r\n");
                    } else {
                        organizeIpStringBuilder.append(ipStart + "-" + ipEnd + "\r\n");
                    }
                }
                modelAndView.addObject("expiration", s);
                modelAndView.addObject("organize", organize);
                modelAndView.addObject("organizeIp", organizeIpStringBuilder);
                modelAndView.addObject("organizeAdmins", organizeAdmins);
            }
            Map<String, List<Permission>> result = adminMenu(2);
            modelAndView.addObject("functionMap", result);
            modelAndView.addObject("online", ONLINESTATUS);
            modelAndView.setViewName("/admin/organize/addorganize");
            return modelAndView;
        } else {
            if ("add".equals(id)) {
                modelAndView.addObject("title", "机构用户");
                modelAndView.addObject("stitle", "添加机构");
            } else {
                modelAndView.addObject("title", "机构用户");
                modelAndView.addObject("stitle", "修改机构");

                Map organizeById = organizeService.getOrganizeById(Integer.parseInt(id));

                Organize organize = (Organize) organizeById.get("organize");
                String s = DateUtil.date2String(organize.getExpiration(), "yyyy-MM-dd");

                List organizeIp = (List) organizeById.get("organizeIp");
                List organizeAdmin = (List) organizeById.get("organizeAdmin");
                List organizeAdmins = new ArrayList();

                for (int i = 0; i < organizeAdmin.size(); i++) {
                    Map map = (Map) organizeAdmin.get(i);
                    int memberId = (int) map.get("memberId");
                    Member member = memberService.findMemberById(memberId);
                    if (member != null) {
                        organizeAdmins.add(member);
                    }
                }

                List<RolePermission> rolePermissions = permissionService.listPermission(organize.getRoleId());
                if (rolePermissions.size() > 0) {
                    for (int i = 0; i < rolePermissions.size(); i++) {
                        int permissionId = rolePermissions.get(i).getPermissionId();
                        append = sb.append(rolePermissions.get(i).getPermissionId()).append(";");
                    }
                    modelAndView.addObject("functions", append.substring(0, append.toString().lastIndexOf(";")));
                }

                modelAndView.addObject("expiration", s);
                modelAndView.addObject("organize", organize);
                modelAndView.addObject("organizeIp", organizeIp);
                modelAndView.addObject("organizeAdmins", organizeAdmins);
            }

            Map<String, List<Permission>> result = adminMenu(2);
            modelAndView.addObject("functionMap", result);
            modelAndView.addObject("online", ONLINESTATUS);
            modelAndView.setViewName("/admin/organize/addorganize");
            return modelAndView;
        }
    }

    /**
     * 保存机构用户信息
     *
     * @param organize
     */
    @ResponseBody
    @RequestMapping(value = "/organize", method = RequestMethod.POST)
    public Map save(Organize organize, HttpServletRequest request) {
        String ipStartOne = organize.getIpStartOne();
        String[] split = ipStartOne.split("\r\n");
        String startOneIp = "";
        String endOneIp = "";
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("-")) {
                String[] split1 = split[i].split("-");
                startOneIp += split1[0] + ",";
                endOneIp += split1[1] + ",";
            } else {
                startOneIp += split[i] + ",";
                endOneIp += split[i] + ",";
            }
        }
        organize.setIpStart(startOneIp);
        organize.setIpEnd(endOneIp);
        Map resultMap = new HashMap();
        Map paramMap = new HashMap();

        String[] permission = request.getParameterValues("permission");
        String memberId = request.getParameter("memberId");
        //if (ONLINESTATUS.equals("OFF")) {
        paramMap.put("organize", organize);
        // }
        paramMap.put("permission", permission);
        paramMap.put("memberId", memberId);

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iters = multipartHttpServletRequest.getFileNames();
            while (iters.hasNext()) {
                paramMap.put("filePath", "");
                MultipartFile file = multipartHttpServletRequest.getFile(iters.next());

                if (file != null) {
                    String filename = file.getOriginalFilename();

                    if (!"".equals(filename)) {
                        String newFilename = UUID.randomUUID().toString().replaceAll("-", "") + FileUtil.getExtend(filename);
                        String uploadPath = FileUtil.getPathWithSystem(System.getProperty("java.io.tmpdir") + "/" + newFilename);
                        File localFile = new File(uploadPath);

                        if (!localFile.exists()) {
                            localFile.mkdirs();
                        }

                        try {
                            file.transferTo(localFile);
                            paramMap.put("filePath", uploadPath);
                        } catch (IOException e) {
                            resultMap.put("state", "1");
                            resultMap.put("msg", "导入失败");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        List<Organize> ips = organizeOnlineService.selectAllIP();
        //切割ip段
        String[] startIp = organize.getIpStart().split(",");
        String[] endIp = organize.getIpEnd().split(",");

        if (ONLINESTATUS.equals("ON")) {
            if (organize.getId() != null && !"".equals(organize.getId())) {
                //获取自身的ip段
                Map orgainizeMap = organizeOnlineService.getOrganizeById(organize.getId());
                List<Map> orgainize1 = (List) orgainizeMap.get("organizeIp");
                //遍历切割ip

                for (int i = 0; i < startIp.length; i++) {

                    //重组ip段
                    String newIp = startIp[i] + "-" + endIp[i];
                    //切割起始ip
                    String s = startIp[i];
                    //切割结束ip
                    String e = endIp[i];
                    //  System.out.println(s + "====>" + IPUtil.getIp2long(s) + "======>>>>>" + e + ">>>>>" + IPUtil.getIp2long(e));
                    //判断写入ip段范围
                    //if (IPUtil.getIp2long(s) >= IPUtil.getIp2long(e)) {
                    //    resultMap.put("state", 3);
                    //    resultMap.put("msg", "起始ip不可以大于结束ip");
                    //    return resultMap;
                    //}
                    int oo = -1;
                    //遍历查询到的所有ip
                    //for (Organize ip : ips) {
                    //    oo++;
                    //    for(int j = 0;j< orgainize1.size();j++){
                    //        //比对所有ip 但需要抛出自身ip
                    //        if (!((ip.getIpStart() + "-" + ip.getIpEnd()).equals((orgainize1.get(j).get("ipStart")) + "-" + (orgainize1.get(j).get("ipEnd"))))) {
                    //            if ((ip.getIpStart() + "-" + ip.getIpEnd()).equals(newIp)) {
                    //                resultMap.put("state", 3);
                    //                resultMap.put("msg", "机构ip" + newIp + "已重复");
                    //                return resultMap;
                    //            }
                    //
                    //            if (IPUtil.getIp2long(ip.getIpStart()) <= IPUtil.getIp2long(s) && IPUtil.getIp2long(s) <= IPUtil.getIp2long(ip.getIpEnd())) {
                    //               resultMap.put("state", 3);
                    //                resultMap.put("msg", "机构域" + s + "-" + e + "与" + ip.getIpStart() + "-" + ip.getIpEnd() + "重复");
                    //                return resultMap;
                    //            }
                    //
                    //
                    //            if (IPUtil.getIp2long(s) <= IPUtil.getIp2long(ip.getIpStart()) && IPUtil.getIp2long(ip.getIpStart()) <= IPUtil.getIp2long(e)) {
                    //                resultMap.put("state", 3);
                    //                resultMap.put("msg", "机构域" + s + "-" + e + "与" + ip.getIpStart() + "-" + ip.getIpEnd() + "重复");
                    //                return resultMap;
                    //            }
                    //
                    //
                    //        }
                    //    }
                    //
                    //
                    //}
                }
                resultMap = organizeOnlineService.updateOrganize(paramMap);
            } else {
                for (int i = 0; i < startIp.length; i++) {
                    //重组ip段
                    String newIp = startIp[i] + "-" + endIp[i];
                    String s = startIp[i];
                    String e = endIp[i];
                    //if (IPUtil.getIp2long(s) >= IPUtil.getIp2long(e)) {
                    //    resultMap.put("state", 3);
                    //    resultMap.put("msg", "起始ip不可以大于结束ip");
                    //    return resultMap;
                    //}
                    for (Organize ip : ips) {
                        if ((ip.getIpStart() + "-" + ip.getIpEnd()).equals(newIp)) {
                            Map organs = organizeOnlineService.getOrganizeById(Integer.valueOf(ip.getOrgName()));
                            Organize or = (Organize) organs.get("organize");
                            resultMap.put("state", 3);
                            resultMap.put("msg", "机构ip" + newIp + "与" + or.getName() + "重复");
                            return resultMap;
                        }
                        if (IPUtil.getIp2long(ip.getIpStart()) <= IPUtil.getIp2long(s) && IPUtil.getIp2long(s) <= IPUtil.getIp2long(ip.getIpEnd())) {
                            Map organs = organizeOnlineService.getOrganizeById(Integer.valueOf(ip.getOrgName()));
                            Organize or = (Organize) organs.get("organize");
                            resultMap.put("state", 3);
                            resultMap.put("msg", "机构域与" + ip.getIpStart() + "-" + ip.getIpEnd() + "与"+ or.getName() +"重复");
                            return resultMap;
                        }
                        if (IPUtil.getIp2long(s) <= IPUtil.getIp2long(ip.getIpStart()) && IPUtil.getIp2long(ip.getIpStart()) <= IPUtil.getIp2long(e)) {
                            Map organs = organizeOnlineService.getOrganizeById(Integer.valueOf(ip.getOrgName()));
                            Organize or = (Organize) organs.get("organize");
                            resultMap.put("state", 3);
                            resultMap.put("msg", "机构域与" + ip.getIpStart() + "-" + ip.getIpEnd() + "与"+ or.getName() + "重复");
                            return resultMap;
                        }

                    }
                }
                resultMap = organizeOnlineService.addOrganizeUser(paramMap);
            }
            return resultMap;
        } else {

        /*保存修改的用户*/
            if (organize.getId() != null && !"".equals(organize.getId())) {
                resultMap = organizeService.updateOrganize(paramMap);
            } else {
                resultMap = organizeService.addOrganizeUser(paramMap);
            }

            return resultMap;
        }
    }

    /**
     * 启用禁用机构
     *
     * @param status
     * @param usersId
     */
    @ResponseBody
    @RequestMapping(value = "/organize/{usersId}/{status}", method = RequestMethod.POST)
    public Map changeUserStatus(@PathVariable String status, @PathVariable List<Integer> usersId) {
        if (ONLINESTATUS.equals("ON")) {
            Map map = new HashMap();
            map.put("status", status);
            map.put("ids", usersId);
            Map resultMap = organizeOnlineService.changeStatus(map);
            return resultMap;
        } else {
            Map map = new HashMap();
            map.put("status", status);
            map.put("ids", usersId);
            Map resultMap = organizeService.changeStatus(map);
            return resultMap;
        }
    }

    /**
     * 删除机构
     *
     * @param ids
     */
    @ResponseBody
    @RequestMapping(value = "/organizeDel/{ids}", method = RequestMethod.GET)
    public Map deleteUser(@PathVariable("ids") List<Integer> ids) {

        if (ONLINESTATUS.equals("ON")) {
            Map resultMap = organizeOnlineService.deleteOrganizeById(ids);

            return resultMap;
        } else {
            Map resultMap = organizeService.deleteOrganizeById(ids);

            return resultMap;
        }

    }


    private Map<String, List<Permission>> adminMenu(int value) {
        Map<String, List<Permission>> result = new LinkedHashMap<>();
        Permission permission = new Permission();
        permission.setAttribute(value);
        List<String> pList = permissionService.findAllParentName(permission);
        for (Iterator iterator = pList.iterator(); iterator.hasNext(); ) {
            String pName = (String) iterator.next();
            List<Permission> fList = permissionService.findAllName(pName);
            result.put(pName, fList);
        }
        return result;
    }

    @RequestMapping(value = "/organize/download/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downLoad(@PathVariable int id) {
        //获取文件存放的目录
        Map organizeById = organizeService.getOrganizeById(id);

        Organize organize = (Organize) organizeById.get("organize");
        String filePath = FileUtil.getPathWithSystem(Config.getKey("app.static.folder") + organize.getFile());
        ResponseEntity<byte[]> entity = null;
        try {
            // 获取文件名
            File file = new File(filePath);
            String fileName = file.getName();
            //通过FileUtils工具把指定文件转换成字节数组
//            fileName = new String(fileName.getBytes("utf-8"), "utf-8");
//            File tmp = new File(Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("%20", " "), fileName);
            byte[] body = FileUtils.readFileToByteArray(file);

            //设置Http协议响应头信息
            HttpHeaders headers = new HttpHeaders();
            //设置下戟内容为字节流
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            fileName = URLEncoder.encode(fileName, "utf-8");
            //设置下载方式为附件另存为方式
            headers.setContentDispositionFormData("attachment", fileName);
            //设置响应代码为正常
            HttpStatus statusCode = HttpStatus.OK;
            entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * 检查账户是否是其他 机构的管理员
     */
    @ResponseBody
    @RequestMapping(value = "/organize/checkmember", method = RequestMethod.GET)
    public Map checkMember(String username, String
            id, @RequestParam(value = "orgId", required = false, defaultValue = "") String orgId) {
        Map resultMap = new HashMap();

        if (ONLINESTATUS.equals("ON")) {
            Map zasApi = new HashMap();
            //检查是否为其他机构成员
            Map parMap = new HashMap();
            parMap.put("username", username);
            parMap.put("id", Integer.parseInt(id));
            parMap.put("orgId", orgId);
            resultMap = organizeOnlineService.checkMemberIsAdmin(parMap);
            if (resultMap.get("code").equals(0)) {
                Map setUserBid = new HashMap();
                setUserBid.put("LoginID", username);
                String bid = organizeOnlineService.selectBid(Integer.valueOf(orgId));
                setUserBid.put("BID", bid);
                JSONObject userMap = JSONObject.fromObject(setUserBid);
                zasApi.put("method", "ZAS.ModifyUser");
                zasApi.put("username", "syncuser");
                zasApi.put("password", "syncuser");
                zasApi.put("params", userMap.toString());
                //Greeting contnet = HttpClientUtil.post(USERURL, zasApi);
//                if (contnet.getStatus() != 1) {
//                    resultMap.put("code", 1);
//                    resultMap.put("msg", "网络异常请联系管理员");
//                    return resultMap;
//                }
//                String contentInfo = (String) contnet.getContent();
//                JSONObject cotentMap = JSONObject.fromObject(contentInfo);
//                if (cotentMap.get("Success").equals(true)) {
                Map inserUserandOrganize = new HashMap();
                inserUserandOrganize.put("organizaId", orgId);
                inserUserandOrganize.put("id", id);
                Organize organize = organizeOnlineService.selectOrganById(Integer.valueOf(orgId));
                inserUserandOrganize.put("organization", organize.getName());
                memberOnlineService.updataOrganize(inserUserandOrganize);
                return resultMap;
//                }
//                else {
//                    resultMap.put("msg", "添加失败请联系管理员");
//                    return resultMap;
//                }
            }
        } else {
            Map parMap = new HashMap();
            parMap.put("username", username);
            parMap.put("id", Integer.parseInt(id));
            parMap.put("orgId", orgId);
            resultMap = organizeService.checkMemberIsAdmin(parMap);
        }
        return resultMap;

    }


    /**
     * 机构下的用户删除
     *
     * @param id
     */
    @ResponseBody
    @RequestMapping(value = "/organize/deleteOrg", method = RequestMethod.GET)
    public Map deleteOrg(String id) {

        Map map = new HashMap();
        map.put("id", id);
        map.put("organizaId", null);
        map.put("organization", null);
        if (ONLINESTATUS.equals("ON")) {
            Map resultMap = memberOnlineService.updataOrganize(map);

            return resultMap;
        } else {
            Map resultMap = memberService.updataOrganize(map);

            return resultMap;
        }

    }
}
