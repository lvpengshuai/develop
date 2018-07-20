package com.trs.service.impl;

import com.trs.mapper.MyCollectMapper;
import com.trs.model.MyCollect;
import com.trs.service.MyCollectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/5/26.
 */
@Service
public class MyCollectServiceImpl implements MyCollectService {
    @Resource
    private MyCollectMapper myCollectMapper;

    @Override
    public List<MyCollect> pageList(Map map) {
        return myCollectMapper.pageList(map);
    }

    @Override
    public int total(Map map) {
        return myCollectMapper.total(map);
    }

    @Override
    public boolean add(Map map) {
        int insert = myCollectMapper.insert(map);
        if (insert > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Map map) {
        myCollectMapper.detelete(map);
        return true;
    }

    @Override
    public boolean findCollectByUserNameAndTypeAndNameId(Map map) {
        MyCollect myCollect = myCollectMapper.findCollectByUserNameAndTypeAndNameId(map);
        if (null == myCollect) {
            return false;
        }
        return true;
    }
}
