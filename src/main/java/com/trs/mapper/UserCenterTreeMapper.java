package com.trs.mapper;

import com.trs.model.UserCenterTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by epro on 2017/9/19.
 */
public interface UserCenterTreeMapper {
    /**
     * 根据id获取节点对象
     * @param id
     * @return
     */
    public UserCenterTree findNodeById(@Param("id") int id);

    /**
     * 查询fid下的所有子节点
     * @param fid
     * @return
     */
    public List<UserCenterTree> findChildsByFid(@Param("fid") int fid);
}
