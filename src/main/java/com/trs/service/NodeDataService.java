package com.trs.service;

import com.trs.model.NodeData;
import com.trs.model.NodeUrl;

import java.util.List;

/**
 * Created by xuxuecheng on 2017/9/7.
 */
public interface NodeDataService {
    /**
     * 根据pid查询节点集合
     *
     * @param fid
     * @return
     */
    List<NodeData> getNodeDataList(int fid);

    /**
     * 增加节点
     *
     * @param nodeData
     * @return
     */
    int addNodeData(NodeData nodeData);

    /**
     * 根据id删除节点
     *
     * @param id
     * @return
     */
    int delNodeData(int id);

    /**
     * 查询所有节点
     *
     * @return
     */
    List<NodeData> getAllNode();

    /**
     * 根据pid删除子节点
     *
     * @param fid
     * @return
     */
    int delNodeByid(int id);

    /**
     * 修改节点信息
     *
     * @param nodeData
     * @return
     */
    int updateNode(NodeData nodeData);

    /**
     * @param id
     * @return
     */
    NodeData getNodeById(int id);

    /**
     * 获取swf
     *
     * @param id
     * @return
     */
    NodeUrl getSwf(String id);

    List<NodeUrl> getDataSearch(String search);
}
