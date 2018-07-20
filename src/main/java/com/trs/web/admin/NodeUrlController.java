package com.trs.web.admin;

import com.trs.client.TRSException;
import com.trs.core.util.Config;
import com.trs.core.util.ConvertSwf;
import com.trs.core.util.OS;
import com.trs.model.NodeData;
import com.trs.model.NodeUrl;
import com.trs.service.NodeDataService;
import com.trs.service.NodeUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by xuxuecheng on 2017/9/18.
 */
@Controller
@RequestMapping(value = "/node")
public class NodeUrlController {

    @Resource
    private NodeUrlService nodeUrlService;
    @Resource
    private NodeDataService nodeDataService;

    /**
     *根据nodeId获取资源路径
     * @param nodeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/urlList")
    public List<NodeUrl> urlList(String nodeId) {
        List<NodeUrl> list = null;
        if (!"".equals(nodeId)) {
            list = nodeUrlService.getListByNodeId(Integer.parseInt(nodeId));
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * 添加node资源
     * @param file
     * @param nodeId
     * @param tid
     * @param modelMap
     * @param redirectAttributesModelMap
     * @return
     * @throws TRSException
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView upload(@RequestParam(value = "input43[]") MultipartFile[] file, @RequestParam(value = "nodeId") int nodeId, @RequestParam(value = "tid") String tid, ModelMap modelMap, RedirectAttributesModelMap redirectAttributesModelMap) throws IOException {
        if (file.length > 0) {
            for (int i = 0; i < file.length; i++) {
                if (!file[i].isEmpty()) {
                    NodeUrl nodeUrl = new NodeUrl();
                    nodeUrl.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    nodeUrl.setNode_Id(nodeId);
                    nodeUrl.setTid(tid);
                    String name = file[i].getOriginalFilename();
                    String[] names = name.split("\\.");
                    nodeUrl.setName(names[0]);
                    List<String> list = new ArrayList<String>();
                    StringBuffer str = new StringBuffer();
                    NodeData nodeData = nodeDataService.getNodeById(nodeId);
                    NodeData nodeData1 = null;
                    list.add(nodeData.getName());
                    if (nodeData.getFid() != 0) {
                        nodeData1 = nodeDataService.getNodeById(nodeData.getFid());
                        list.add(nodeData1.getName());
                        if (nodeData1.getFid() != 0) {
                            NodeData nodeData2 = nodeDataService.getNodeById(nodeData1.getFid());
                            list.add(nodeData2.getName());
                            if (nodeData2.getFid() != 0) {
                                NodeData nodeData3 = nodeDataService.getNodeById(nodeData2.getFid());
                                list.add(nodeData3.getName());
                                if (nodeData3.getFid() != 0) {
                                    NodeData nodeData4 = nodeDataService.getNodeById(nodeData3.getFid());
                                    list.add(nodeData4.getName());

                                }
                            }
                        }
                    }
                    str.append(Config.getKey("swf.disk"));
                    for (int j = list.size() - 1; j >= 0; j--) {
                        str.append("/" + list.get(j));
                    }
                    File file2 = new File(str.toString());
                    if (!file2.exists() && !file2.isDirectory()) {
                        file2.mkdirs();
                        file[i].transferTo(new File(file2.getAbsolutePath() + "/" + file[i].getOriginalFilename()));
                    } else {
                        file[i].transferTo(new File(file2.getAbsolutePath() + "/" + file[i].getOriginalFilename()));
                    }
                    String s = ConvertSwf.beginConvert(file2.getAbsolutePath(), file[i].getOriginalFilename());
                    //String s="D:/epro/cssp/cssp_resource/datacenter/基点/世界经济年鉴/2016/世界经济年鉴2015_253_1.pdf";
                    if (!OS.isLinux){
                        int i1 = s.indexOf("\\cssp_resource");
                        nodeUrl.setNode_Url(s.substring(i1).replaceAll("\\\\", "/"));
                        File file1 = new File(s.substring(0, s.lastIndexOf("\\")) +"\\"+ file[i].getOriginalFilename());
                        int i2 = file1.getAbsolutePath().indexOf("\\cssp_resource");
                        nodeUrl.setExcel_url(file1.getAbsolutePath().substring(i2).replaceAll("\\\\", "/"));
                    }else {
                        int i1 = s.indexOf("/cssp_resource");
                        nodeUrl.setNode_Url(s.substring(i1));
                        File file1 = new File(s.substring(0,s.lastIndexOf("/")) +"/"+  file[i].getOriginalFilename());
                        int i2 = file1.getAbsolutePath().indexOf("/cssp_resource");
                        nodeUrl.setExcel_url(file1.getAbsolutePath().substring(i2));
                    }
                    nodeUrlService.addNodeUrl(nodeUrl);

                }
            }
        }
        redirectAttributesModelMap.put("tid", tid);
        redirectAttributesModelMap.put("nodeId", nodeId);
        return new ModelAndView("redirect:/admin/node", modelMap);
    }

    /**
     * 删除node资源
     * @param id
     * @return
     */
    @RequestMapping(value = "delNodeUrl")
    @ResponseBody
    public List<String> delNodeUrl(String id){
        NodeUrl nodeUrl = nodeUrlService.getNodeUrl(id);
        nodeUrlService.delNodeUrlById(id);
        List<String> list=new ArrayList<>();
        list.add(nodeUrl.getTid());
        list.add(nodeUrl.getNode_Id().toString());
        return list;
    }

}
