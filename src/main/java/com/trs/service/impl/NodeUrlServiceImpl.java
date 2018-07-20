package com.trs.service.impl;

import com.trs.mapper.NodeUrlMapper;
import com.trs.model.NodeUrl;
import com.trs.service.NodeUrlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xuxuecheng on 2017/9/18.
 */
@Service
public class NodeUrlServiceImpl implements NodeUrlService {

    @Resource
    private NodeUrlMapper nodeUrlMapper;

    @Override
    public List<NodeUrl> getListByNodeId(int nodeId) {
        return nodeUrlMapper.getListByNodeId(nodeId);
    }

    @Override
    public int addNodeUrl(NodeUrl nodeUrl) {
        return nodeUrlMapper.addNodeUrl(nodeUrl);
    }

    @Override
    public void delNodeUrl(Integer id) {
        nodeUrlMapper.delNodeUrl(id);
    }

    @Override
    public List<NodeUrl> getAllNode() {
        return nodeUrlMapper.getAllNode();
    }

    @Override
    public void delNodeUrlById(String id) {
        nodeUrlMapper.delNodeUrlById(id);
    }

    @Override
    public NodeUrl getNodeUrl(String id) {
        return nodeUrlMapper.getNodeUrl(id);
    }
}
