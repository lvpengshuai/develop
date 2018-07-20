package com.trs.mapper;

import com.trs.model.Knowledge;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface KnowledgeMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteAll();
    int deleteByName(String knowledgeName);
    int insert(Knowledge record);

    int insertSelective(Knowledge record);

    Knowledge selectByPrimaryKey(Integer id);

    List<Knowledge> selectByKnowledgeName(String name);

    Knowledge findByName(String name);

    int updateByPrimaryKeySelective(Knowledge record);

    int updateByPrimaryKeyWithBLOBs(Knowledge record);

    int updateByPrimaryKey(Knowledge record);

    List<Knowledge> findBaseKnowledgeElements();

    /**
     * 分页查询
     *
     * @return
     */
    public List<Knowledge> findAll(Map map);

    /**
     * 获取知识元总记录数
     *
     * @return
     */
    public int total(Map map);

    /**
     * 查询所有数据
     * @return
     */
    public List<Knowledge> selectData();

    /**
     * 查询相关词
     */

    public List selectAboutWords(String name);
    public Set<String> selectAllWords();
}