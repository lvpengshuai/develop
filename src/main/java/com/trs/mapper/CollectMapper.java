package com.trs.mapper;

import com.trs.model.Collect;
import com.trs.model.Splice;

import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/5/26.
 */
public interface CollectMapper {

    /**
     * 用户中心查询我得收藏
     * @param map
     * @return
     */
    public List<Collect> collectShow(Map map);

    public List<Collect> collectShow(String  username);
    /**
     * 用户中心查询我得一周收藏
     * @param username
     * @return
     */
    public List<Collect> collectShowWeek(String  username);
    /**
     * 用户中心查询我得一周拼接
     * @param username
     * @return
     */
    public List<Splice> collectSpliceWeek(String username);
    /**
     * 用户中心查询我得收藏  文件夹查询
     * @return
     */
    public List<Collect> collectFolder(String username);

    /**
     * 删除我得收藏
     * @param id
     * @return
     */
    int  collectDelete(int id);


    /**
     * 用户中心根删除收藏夹
     * @param map
     * @return
     */
    int  collectFolderDelete(Map map);


    /**
     * 用户中心 文件夹重命名
     * @param map
     * @return
     */
    int  collectFolderReName(Map map);

    /**
     * 用户中心 添加收藏夹
     * @param collect
     * @return
     */
    int insertFolder(Collect collect);

    /**
     * 查找收藏夹名字
     * @param map
     * @return
     */
    public List<Collect> selectFolderName(Map map);


    /**
     * 添加收藏
     * @param collect
     * @return
     */
    int addCollect(Collect collect);

    /**
     * 根据用户名，tid查找收藏
     * @param map
     * @return
     */
    public List<Collect>  selectByUserNameAndTID(Map map);

    /**
     * 获取收藏总数
     * @param map
     * @return
     */
    public Integer collectShowCount(Map map);
}
