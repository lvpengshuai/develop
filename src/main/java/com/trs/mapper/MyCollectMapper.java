package com.trs.mapper;

import com.trs.model.MyCollect;

import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/5/26.
 */
public interface MyCollectMapper {
    /**
     * 分页查询数据
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
     */
    public int insert(Map map);

    /**
     * 取消收藏
     * @param map
     * @return
     */
    public int detelete(Map map);

    /**
     * 查询收藏
     * @param map
     * @return
     */
    public MyCollect findCollectByUserNameAndTypeAndNameId(Map map);
}
