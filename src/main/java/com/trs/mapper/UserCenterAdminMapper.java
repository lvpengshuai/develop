package com.trs.mapper;

import com.trs.model.Concern;

import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/5/26.
 */
public interface UserCenterAdminMapper {
    /**
     * 根据用户名查询我得关注
     *
     * @param username
     * @return
     */
    public List<Concern> selectConcern(String username);

    /**
     * 根据用户名查询我得一周关注
     *
     * @param username
     * @return
     */
    public List<Concern> selectConcernWeek(String username);

    /**
     * 用户中心根据id删除关注词
     *
     * @param id
     * @return
     */
    int deleteConcern(int id);

    /**
     * 根据用户名，tid判断是否已经拼接
     * @param map
     * @return
     */
    public List<Concern>  selectConcernByUserNameAndConcern(Map map);

    /**
     *  更新我得拼接
     * @param concern
     * @return
     */
    int  upConcern(Concern concern);

    /**
     * 添加拼接
     * @param concern
     * @return
     */
    int insertConcern(Concern concern);

    /**
     * 关注词总数
     * @return
     */
    int allConcern(Map map);


}
