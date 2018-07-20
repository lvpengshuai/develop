package com.trs.service;

import com.trs.model.Author;

import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/4/6.
 */
public interface AuthorService {
    /**
     * 查找作者
     * @param name
     * @return
     */
    public Author findDataByAuthorName(String name);

    /**
     * 添加作者
     * @param author
     */
    public void addAuthor(Author author) throws Exception;

    /**
     * 添加作者与资源关系
     * @param map
     */
    public void addResourceAndAuthor(Map map) throws Exception;

    /**
     * 获取资源数目
     * @param map
     * @return
     */
    public List<Integer> getResourceTotal(Map map);

    /**
     * 根据作者名分页查询资源的id
     * @param map
     * @return
     */
    public List<Integer> findItemsIdByAuthor(Map map);

}
