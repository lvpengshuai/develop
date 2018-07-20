package com.trs.mapper;

import com.trs.model.AuthorResource;

import java.util.Map;

/**
 * Created by 李春雨 on 2017/3/28.
 */
public interface AuthorResourceMapper {

    public void insert(AuthorResource authorResource);

    /**
     * 根据资源id和type删除作者资源关系表中数据
     * @param map
     */
    public void deleteByResourceAndType(Map map);
}
