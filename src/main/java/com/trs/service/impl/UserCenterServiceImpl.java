package com.trs.service.impl;

import com.trs.mapper.UserCenterTreeMapper;
import com.trs.model.UserCenterTree;
import com.trs.service.UserCenterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihuan on 2017/9/19.
 */
@Service
public class UserCenterServiceImpl implements UserCenterService {
    @Resource
    private UserCenterTreeMapper userCenterTreeMapper;

    @Override
    public List<UserCenterTree> getTree() {
        //准备好list
        List<UserCenterTree> list=new ArrayList<>();
        //获取所有一级节点
        List<UserCenterTree> firstNodes=userCenterTreeMapper.findChildsByFid(0);
        //遍历一级节点，封装对应的子节点
        for(int i=0; i<firstNodes.size(); i++){
            //每封装完成一个节点，就添加进list
            list.add(recursiveTree(firstNodes.get(i).getId()));
        }
        return list;
    }

    private UserCenterTree recursiveTree(int id) {
        UserCenterTree node=userCenterTreeMapper.findNodeById(id);
        List<UserCenterTree> childs=userCenterTreeMapper.findChildsByFid(id);
        for(UserCenterTree child: childs){
            UserCenterTree n=recursiveTree(child.getId());
            node.getChilds().add(n);
        }
        return node;
    }
}
