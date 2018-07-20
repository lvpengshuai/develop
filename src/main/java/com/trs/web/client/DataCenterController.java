package com.trs.web.client;

import com.alibaba.fastjson.JSON;
import com.trs.client.TRSException;
import com.trs.core.util.Config;
import com.trs.core.util.IPUtil;
import com.trs.core.util.RequestUtils;
import com.trs.core.util.TreeNodeUtil;
import com.trs.model.*;
import com.trs.service.NodeDataService;
import com.trs.service.NodeUrlService;
import com.trs.service.OrganizeOnlineService;
import com.trs.service.PermissionService;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/24.
 */
@Controller
public class DataCenterController {

    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");
    @Resource
    private NodeUrlService nodeUrlService;
    @Resource
    private NodeDataService nodeDataService;

   /* @Resource
    private UserCenterService userCenterService;*/

   /* @RequestMapping(value = "/dataCenter")
    public ModelAndView dataCenter(HttpServletRequest request, ModelMap modelMap){
        //获取数据中心的树
        List<UserCenterTree> dataList=userCenterService.getTree();
        modelMap.put("dataList", dataList);
        return new ModelAndView("client/data/datecenter",modelMap);
    }*/

    /**
     * 查询所有节点，return：集合
     *
     * @param request
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/show")
    @ResponseBody
    public ModelAndView show(HttpServletRequest request, ModelMap modelMap) {
        RequestUtils requestUtils = new RequestUtils(request);
        int fid = requestUtils.getParameterAsInt("fid", 0);
        List<NodeData> nodeDataList = nodeDataService.getAllNode();
        List<NodeMenu> nodeMenus = TreeNodeUtil.toListNode(nodeDataList);
        List<NodeMenu> treeNodes = TreeNodeUtil.tree(nodeMenus, fid);
        treeNodes.remove(0);
        List<NodeUrl> nodeDataList1 = nodeUrlService.getAllNode();
        List<NodeMenu> nodeMenus1 = TreeNodeUtil.toListNode1(nodeDataList1);
        List<NodeMenu> treeNodes1 = TreeNodeUtil.tree1(nodeMenus1, fid);
        treeNodes.addAll(treeNodes1);

        modelMap.put("json", JSON.toJSONString(treeNodes));
        return new ModelAndView("client/data/show", modelMap);
    }

    /**
     *图表跳转
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/dataCenter")
    public ModelAndView node(ModelMap modelMap) {
        return new ModelAndView("client/data/datecenter", modelMap);
    }

    /**
     * 请求swf需要的数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getSwf", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public NodeUrl getSwf(String id) {
        NodeUrl nodeUrl = nodeDataService.getSwf(id);
        if (nodeUrl.getExcel_url() == null) {
            nodeUrl.setExcel_url(nodeUrl.getNode_Url());
        }
        return nodeUrl;
    }

    @Resource
    private OrganizeOnlineService organizeOnlineService;
    @Resource
    private PermissionService permissionService;

    /**
     * 请求pdf.js需要的数据
     *
     * @return
     */
    @RequestMapping(value = "/getIfream", produces = "application/json;charset=UTF-8")
    public String getIfream(HttpSession session,HttpServletRequest request) {
        Object username = session.getAttribute("userName");
        if (username == null || username == "") {
            List<Organize> ip = organizeOnlineService.selectAllIP();
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
                            List<Organize> org = organizeOnlineService.selectById(Integer.parseInt(id));
                            if (org.size() != 0) {
                                //通过角色ip查找对应权限id
                                List<RolePermission> organizePermission = permissionService.listPermission(org.get(0).getRoleId());
                                //遍历所有的权限id
                                for (RolePermission rolePermission : organizePermission) {
                                    System.out.println(rolePermission.getPermissionId());
                                    //根据权限id查找所有对应的权限
                                    List<Permission> permission = permissionService.selectPermissionById(rolePermission.getPermissionId());
                                    for (Permission per : permission) {
                                        if (per.getName().equals("在线阅读") || per.getName().equals("原文阅读")) {
                                            return "client/data/viewer";
                                        }
                                    }
                                }

                            }
                        }
                    } else {
                        return "client/data/pdferror";
                    }

                }
            }
        }
        else
            {
            return "client/data/viewer";
            }
        return "client/data/pdferror";

    }

    /**
     *请求pdf的数据（图表）
     * @return
     */
    @RequestMapping(value = "/getIfreams", produces = "application/json;charset=UTF-8")
    public String getIfreams() {
        return "client/data/viewer2";
    }

    /**
     * 数据中心搜索
     *
     * @param search
     * @return
     */
    @RequestMapping(value = "/getDataSearch", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<NodeUrl> getDataSearch(String search) {
        return nodeDataService.getDataSearch(search);
    }

    @RequestMapping(value = "/downloadExcel")
    public ResponseEntity<byte[]> downloadExcel(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") String id) {
        Map excelOnline = organizeOnlineService.getUserOrganzeRole(session, "excel下载", (Math.random()) * 10 + "", "", request, ONLINESTATUS);
        System.out.println(excelOnline.get("status"));
        System.out.println(id);
        int status = Integer.parseInt(excelOnline.get("status").toString());
        if (status != 2) {
            NodeUrl nodeUrl = nodeDataService.getSwf(id);
            //得到下载文件的路径
            String key = Config.getKey("book.source");
            int i = key.lastIndexOf("/cssp_resource");
            String resource = key.substring(0, i);
            String url = null;
            if (nodeUrl.getExcel_url() == null || nodeUrl.getExcel_url() == "") {
                url = nodeUrl.getNode_Url();
            } else {
                url = nodeUrl.getExcel_url();
            }
            String pdfPath = resource + url;


            ResponseEntity<byte[]> entity = null;
            try {
                // 获取文件名
                File file = new File(pdfPath);
                String fileName = file.getName();
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
        } else {
            return null;
        }

    }
}
