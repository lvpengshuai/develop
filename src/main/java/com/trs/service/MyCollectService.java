package com.trs.service;

import com.trs.model.MyCollect;

import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/5/26.
 */
public interface MyCollectService {
    /**
     * 数据分页
     * @param map
     * @return
     */
    public List<MyCollect> pageList(Map map);

    /**
     * 数据总数
     * @param map
     * @return
     */
    public int total(Map map);

    /**
     * 收藏
     * @param map
     * @return
     */
    public boolean add(Map map);

    /**
     * 取消收藏
     * @param map
     * @return
     */
    public boolean delete(Map map);

    /**
     * 查询收藏
     * @param map
     * @return
     */
    public boolean findCollectByUserNameAndTypeAndNameId(Map map);
}
