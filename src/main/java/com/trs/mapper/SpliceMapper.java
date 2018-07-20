package com.trs.mapper;

import com.trs.model.Collect;
import com.trs.model.Splice;

import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/5/26.
 */
public interface SpliceMapper {


    /**
     * 根据用户名，tid判断是否已经拼接
     * @param map
     * @return
     */
    public List<Splice>  selectSpliceByUserNameAndTID(Map map);

    /**
     *  更新我得拼接
     * @param splice
     * @return
     */
    int  upSplice(Splice splice);

    /**
     * 添加拼接
     * @param splice
     * @return
     */
    int mySpliceAdd(Splice splice);

    /**
     * 用户中心查询我得拼接
     * @return
     */
    public List<Splice> spliceShow(Map map);


    /**
     * 用户中心删除我得拼接
     * @param id
     * @return
     */
    int  deleteSplice(int id);

    /**
     * 我得拼接总数
     * @param map
     * @return
     */
    public Integer allSpliceShowCount(Map map);
}
