package com.trs.mapper;

import com.trs.model.NodeData;
import com.trs.model.NodeUrl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xuxuecheng on 2017/9/7.
 */
public interface NodeDataMapper {
    /**
     * 根据pid查询字节点集合
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
     * 根据pid删除所有子节点
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
     * 根据节点id查询节点信息
     *
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
    NodeUrl getSwf(@Param("id") String id);

    List<NodeUrl> getDataSearch(@Param("search") String search);
}
