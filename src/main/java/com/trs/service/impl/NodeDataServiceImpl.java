package com.trs.service.impl;

import com.trs.mapper.NodeDataMapper;
import com.trs.model.NodeData;
import com.trs.model.NodeUrl;
import com.trs.service.NodeDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xuxuecheng on 2017/9/7.
 */
@Service
public class NodeDataServiceImpl implements NodeDataService {

    @Resource
    private NodeDataMapper nodeDataMapper;

    @Override
    public List<NodeData> getNodeDataList(int fid) {
        return nodeDataMapper.getNodeDataList(fid);
    }

    @Override
    public int addNodeData(NodeData nodeData) {
        return nodeDataMapper.addNodeData(nodeData);
    }

    @Override
    public int delNodeData(int id) {
        return nodeDataMapper.delNodeData(id);
    }

    @Override
    public List<NodeData> getAllNode() {
        return nodeDataMapper.getAllNode();
    }

    @Override
    public int delNodeByid(int id) {
        return nodeDataMapper.delNodeByid(id);
    }

    @Override
    public int updateNode(NodeData nodeData) {
        return nodeDataMapper.updateNode(nodeData);
    }

    @Override
    public NodeData getNodeById(int id) {
        return nodeDataMapper.getNodeById(id);
    }

    @Override
    public NodeUrl getSwf(String id) {
        return nodeDataMapper.getSwf(id);
    }

    @Override
    public List<NodeUrl> getDataSearch(String search) {
        return nodeDataMapper.getDataSearch(search);
    }
}
