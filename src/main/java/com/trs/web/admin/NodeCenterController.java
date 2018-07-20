package com.trs.web.admin;

import com.alibaba.fastjson.JSON;
import com.trs.client.TRSException;
import com.trs.core.util.Config;
import com.trs.core.util.RequestUtils;
import com.trs.core.util.TreeNodeUtil;
import com.trs.model.NodeData;
import com.trs.model.NodeMenu;
import com.trs.service.NodeDataService;
import com.trs.service.NodeUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by xuxuecheng on 2017/9/7.
 */
@Controller
@RequestMapping(value = "/admin")
public class NodeCenterController extends AbstractAdminController {
    @Resource
    private NodeUrlService nodeUrlService;
    @Resource
    private NodeDataService nodeDataService;
    private List<NodeData> list = new ArrayList<NodeData>();


    /*@RequestMapping(value = "/node1")
        @ResponseBody
        public Map nodeList(HttpServletRequest request) throws TRSException {
            Map valuemap = new HashMap();
            RequestUtils requestUtils = new RequestUtils(request);
            int pid = requestUtils.getParameterAsInt("pid", 0);
            List<NodeData> nodeDataList = nodeDataService.getAllNode();
            List<NodeMenu> nodeMenus = TreeNodeUtil.toListNode(nodeDataList);
            List<NodeMenu> treeNodes = TreeNodeUtil.tree(nodeMenus, pid);
            listToString(treeNodes,',');
            valuemap.put("json", JSON.toJSONString(treeNodes));
            System.out.println(JSON.toJSONString(treeNodes).toString());
            return valuemap;
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
    public ModelAndView nodeList(HttpServletRequest request, ModelMap modelMap) throws TRSException {
        RequestUtils requestUtils = new RequestUtils(request);
        int fid = requestUtils.getParameterAsInt("fid", 0);
        String tid = request.getParameter("tid");
        String nodeId = request.getParameter("nodeId");
        List<NodeData> nodeDataList = nodeDataService.getAllNode();
        List<NodeMenu> nodeMenus = TreeNodeUtil.toListNode(nodeDataList);
        List<NodeMenu> treeNodes = TreeNodeUtil.tree(nodeMenus, fid);
        modelMap.put("json", JSON.toJSONString(treeNodes));
        modelMap.put("tid", tid);
        modelMap.put("nodeId", nodeId);
        return new ModelAndView("admin/datacenter/show", modelMap);
    }

    /**
     * 跳转到节点菜单栏
     *
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/node")
    public ModelAndView node(ModelMap modelMap, String tid, String nodeId) throws TRSException {
        modelMap.put("tid", tid);
        modelMap.put("nodeId", nodeId);
        return new ModelAndView("admin/datacenter/nodelist", modelMap);
    }

    /**
     * 增加节点
     *
     * @param fid
     * @param name
     * @return
     * @throws TRSException
     */
    @ResponseBody
    @RequestMapping(value = "/addNode")
    public Map addNode(@RequestParam(value = "fid", required = true) int fid, @RequestParam(value = "name", required = true) String name) throws TRSException {
        Map<String, Object> result = new HashMap<>();
        NodeData nodeData = nodeDataService.getNodeById(fid);
        NodeData nodeData1 = new NodeData();
        nodeData1.setFid(fid);
        nodeData1.setName(name);
        nodeData1.setState(nodeData.getState() + 1);
        String key = Config.getKey("swf.disk") + "/";
        String path = nodeData.getNodeurl() + "/" + nodeData1.getName();
        File file = new File(key + path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        nodeData1.setNodeurl(path);
        nodeDataService.addNodeData(nodeData1);
        int id = nodeData1.getId();
        result.put("code", 1);
        result.put("id", id);
        return result;
    }

    /**
     * 通过id删除节点(包括节点下的子节点)
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delNode")
    public Map delNode(@RequestParam(value = "id", required = true) int id) {
        Map<String, Object> result = new HashMap<>();
        NodeData nodeById = nodeDataService.getNodeById(id);
        nodeDataService.delNodeData(id);

        List<Integer> integerList=new ArrayList<>();
        integerList.add(id);
        for (int i=0;i<integerList.size();i++){
            List<NodeData> nodeDataList = nodeDataService.getNodeDataList(integerList.get(i));
            integerList.addAll(nodeDataList.stream().map(NodeData::getId).collect(Collectors.toList()));
            nodeDataService.delNodeByid(integerList.get(i));
            nodeUrlService.delNodeUrl(integerList.get(i));
        }

        String key = Config.getKey("swf.disk");
        File file = new File(key + nodeById.getNodeurl());
        deleteDir(file);
        result.put("code", 1);
        return result;
    }

    /**
     * 删除目录
     * @param dir
     * @return
     */
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 通过传入的父ID，查询节点下是否存在子节点
     *
     * @param fid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryNode")
    public Map queryNode(@RequestParam(value = "fid") int fid) {
        Map<String, Object> result = new HashMap<>();
        List<NodeData> list = nodeDataService.getNodeDataList(fid);
        if (list != null && list.size() > 0) {
            result.put("code", 1);
        } else {
            result.put("code", 0);
        }
        return result;
    }

    /**
     * 更新node信息
     * @param name
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateNode")
    public Map updateNode(@RequestParam(value = "name") String name, @RequestParam(value = "id") int id) {
        Map<String, Object> result = new HashMap<>();
        NodeData nodeById = nodeDataService.getNodeById(id);

        NodeData nodeData = new NodeData();
        nodeData.setId(id);
        nodeData.setName(name);

        String key = Config.getKey("swf.disk");
        File file=new File(key + nodeById.getNodeurl());

        String substring = nodeById.getNodeurl().substring(0,nodeById.getNodeurl().lastIndexOf("/")+1)+name;
        File fileNew=new File(key + substring);
        file.renameTo(fileNew);

        nodeData.setNodeurl(substring);
        int flag = nodeDataService.updateNode(nodeData);

        if (flag == 1) {
            result.put("code", 1);
        } else {
            result.put("code", 0);
        }
        return result;
    }

    /**
     * 通过id获取node信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getNodeById")
    public Map getNodeById(@RequestParam(value = "id") int id) {
        Map<String, Object> result = new HashMap<>();
        NodeData nodeData = nodeDataService.getNodeById(id);
        if (nodeData.getState() == 4) {
            result.put("code", 1);
        } else {
            result.put("code", 0);
        }
        return result;
    }
}
