package com.trs.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/5/26.
 */
public interface AccessLogMapper {
    /**
     * 查询访问记录数据
     * @param map
     * @return
     */
    public int pageList(Map map);

    /**
     * 添加访问记录
     * @param mapd
     */
    public int insert(Map map);


    /**
     * 获取下载记录数据
     * @param map
     * @return
     */
    int getDownloadData(Map map);

    /**
     * 添加下载记录
     * @param map
     */
    void addDownloadData(Map map);

    /**
     * 分页查询访问记录
     * @param map
     * @return
     */
    List selectAccessByPage(Map map);
    int getAccessByPageTotal(Map map);
    /**
     * 分页查询下载记录
     * @param map
     * @return
     */
    List selectDownloadByPage(Map map);
    int getDownloadByPageTotal(Map map);

    /**
     * 分页获取热门资源数据
     * @param map
     * @return
     */
    List getHotResource(Map map);
    int getHotResourceTotal(Map map);

    /**
     * 分页获取下载数据
    * @param map
     * @return
     */
    List getDownladResource(Map map);
    int getDownladResourceTotal(Map map);


    /**
     * 分页获取用户记录
     * @param map
     * @return
     */
    List getUserlog(Map map);
    int getUserlogTotal(Map map);

}
