package com.trs.mapper;

import com.trs.model.NodeUrl;

import java.util.List;

/**
 * Created by xuxuecheng on 2017/9/18.
 */
public interface NodeUrlMapper {
    /**
     * 通过NodeId查询节点下的url资源
     *
     * @param nodeId
     * @return
     */
    public List<NodeUrl> getListByNodeId(int nodeId);

    /**
     * 增加节点路径
     *
     * @param nodeUrl
     * @return
     */
    public int addNodeUrl(NodeUrl nodeUrl);

    public void delNodeUrl(Integer id);

    List<NodeUrl> getAllNode();

    public void delNodeUrlById(String id);

    public NodeUrl getNodeUrl(String id);
}
