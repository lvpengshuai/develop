package com.trs.service;

import com.trs.model.Collect;
import com.trs.model.Concern;
import com.trs.model.Splice;

import java.util.List;
import java.util.Map;

/**
 * Created by epro on 2017/8/24.
 */
public interface UserCenterAdminService {

    /**
     * 用户中心查询关注词
     *
     * @param username
     * @return
     */
    public List<Concern> selectConcern(String username);
    /**
     * 用户中心查询我得一周藏
     *
     * @param username
     * @return
     */
    public List<Concern> selectConcernWeek(String username);

    /**
     * 用户中心根据id删除关注词
     * @param id
     * @return
     */
    public Map deleteConcern(int id);

    /**
     * 用户中心查询我得收藏
     * @param  map
     * @return
     */
    public List<Collect> collectShow(Map map);

    /**
     * 用户中心查询我得一周收藏
     * @param username
     * @return
     */
    public List<Collect> collectShowWeek(String username);

    /**
     * 用户中心查询我得一周拼接
     * @param username
     * @return
     */
    public List<Splice> collectSpliceWeek(String username);



    /**
     * 用户中心根据id删除我的收藏
     *
     * @param id
     * @return
     */
    public Map collectDelete(int id);

    /**
     * 我的收藏文件夹查找
     *
     * @return
     */
    public List<Collect> collectFolder(String username);

    /**
     * 用户中心根据收藏夹名字删除
     * @param map
     * @return
     */
    public Map collectFolderDelete(Map map);

    /**
     * 用户中心根据收藏夹名字重命名
     * @param map
     * @return
     */
    public Map collectFolderReName(Map map);

    /**
     * 用户中心 添加收藏夹
     * @param collect
     * @return
     */
    public Map insertFolder(Collect collect);

    /**
     * 添加收藏
     * @param collect
     * @return
     */
    public Map addCollect(Collect collect);

    /**
     * 根据用户名，tid查找收藏
     * @param map
     * @return
     */
    public Map selectByUserNameAndTID(Map map);


    /**
     * 获取收藏总数
     * @param map
     * @return
     */
    public Integer collectShowCount(Map map);

    /**
     * 根据用户名，tid判断是否已经拼接
     * @param map
     * @return
     */
    public Map selectSpliceByUserNameAndTID(Map map);


    /**
     * 更新拼接
     * @param splice
     * @return
     */
    public Map upSplice(Splice splice);

    /**
     * 添加拼接
     * @param splice
     * @return
     */
    public Map mySpliceAdd(Splice splice);

    /**
     * 我的拼接查找
     *
     * @return
     */
    public List<Splice> spliceShow(Map map);

    /**
     * 用户中心删除我得拼接
     * @param id
     * @return
     */
    public Map deleteSplice(int id);

    /**
     * 我得拼接总数
     * @param map
     * @return
     */
    public Integer allSpliceShowCount(Map map);


    /**
     * 根据用户名，tid判断是否已经关注
     * @param map
     * @return
     */
    public Map selectConcernByUserNameAndConcern(Map map);

    /**
     * 更新关注
     * @param concern
     * @return
     */
    public Map upConcern(Concern concern);

    /**
     * 添加关注
     * @param concern
     * @return
     */
    public Map insertConcern(Concern concern);

    /**
     * 关注词总个数
     * @return
     */
    public int allConcern(Map map);

}
