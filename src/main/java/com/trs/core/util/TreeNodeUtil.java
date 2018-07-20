package com.trs.core.util;

/**
 * Created by xuxuecheng on 2017/9/12.
 */

import com.trs.model.NodeData;
import com.trs.model.NodeMenu;
import com.trs.model.NodeUrl;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeUtil {
        public static List<NodeMenu> tree(List<NodeMenu> nodes, Integer id) {
            //递归转化为树形
            List<NodeMenu> treeNodes=new ArrayList<NodeMenu>();
            for(NodeMenu nodeMenu : nodes) {
                NodeMenu node=new NodeMenu();
                node.setId(nodeMenu.getId());
                node.setName(nodeMenu.getName());
                node.setpId(nodeMenu.getpId());
                treeNodes.add(node);
               /* if(id==nodeMenu.getpID()){
                    node.setChildren(tree(nodes, node.getId()));
                    treeNodes.add(node);
                }*/

            }
            return treeNodes;
        }
        //转化为TreeNode节点
        public static NodeMenu toNode(NodeData nodeData){
            NodeMenu node=new NodeMenu();
            node.setId(String.valueOf(nodeData.getId()));
            node.setpId(nodeData.getFid());
            node.setName(nodeData.getName());
            return node;
        }
        public static List<NodeMenu> toListNode(List<NodeData> nodeData){
            List<NodeMenu>nodes=new ArrayList<NodeMenu>();
            for(NodeData nodeData1:nodeData){
                nodes.add(toNode(nodeData1));
            }
            return nodes;
        }

    public static List<NodeMenu> tree1(List<NodeMenu> nodes, Integer id) {
        //递归转化为树形
        List<NodeMenu> treeNodes=new ArrayList<NodeMenu>();
        for(NodeMenu nodeMenu : nodes) {
            NodeMenu node=new NodeMenu();
            node.setId(nodeMenu.getId());
            node.setName(nodeMenu.getName());
            node.setpId(nodeMenu.getpId());
            node.setMyAttr("file");
            treeNodes.add(node);
               /* if(id==nodeMenu.getpID()){
                    node.setChildren(tree(nodes, node.getId()));
                    treeNodes.add(node);
                }*/

        }
        return treeNodes;
    }
    //转化为TreeNode节点
    public static NodeMenu toNode1(NodeUrl nodeData){
        NodeMenu node=new NodeMenu();
        node.setId(nodeData.getId());
        node.setpId(nodeData.getNode_Id());
        node.setName(nodeData.getName());
        return node;
    }
    public static List<NodeMenu> toListNode1(List<NodeUrl> nodeData){
        List<NodeMenu> nodes=new ArrayList<NodeMenu>();
        for(NodeUrl nodeData1:nodeData){
            nodes.add(toNode1(nodeData1));
        }
        return nodes;
    }

}
