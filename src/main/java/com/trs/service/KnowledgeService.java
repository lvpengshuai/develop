package com.trs.service;

import com.trs.model.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by pz on 2017/3/10.
 */

public interface KnowledgeService {

    /**
     * 查询知识源
     *
     * @param id
     * @return
     */
    public Knowledge selectByPrimaryKey(Integer id);

    /**
     * 通过基本属性名称，插入数据，更新数据
     *
     * @return
     */
    public int insertBaseKnowledgeElement(Knowledge knowledge);

    /**
     * 写入关联的图书片段
     *
     * @param
     * @return
     */
    public void insertBook(List<KnowledgeSegmentBookWithBLOBs> books);

    /**
     * 写入关联的图书片段
     *
     * @param knowledgeSegmentBook
     * @return
     */
    public int insertBooks(KnowledgeSegmentBookWithBLOBs knowledgeSegmentBook);

    /**
     * 通过id同步数据
     *
     * @return
     */
    public int updateByBaseKnowledgeElementId(Knowledge knowledge);


    /**
     * 分页查询数据列表
     *
     * @return
     */
    public Map findAll(Map map);

    /**
     * 通过inc查询
     */
    public List findByINCProerty(KnowledgeSegmentBook record);

    public List findBlobsByINCProerty(KnowledgeSegmentBook record);

    /**
     * 统计每个属性关联的图书数
     */
    public int countPropertyBook(Map map);

    /**
     * 通过名称查询知识元
     *
     * @param name
     * @return
     */
    public Knowledge findByName(String name);
    public Map knowledgeResult(String name);

    /**
     * 查询所有数据
     * @return
     */
    public List<Knowledge> selectData();

    /**
     * 查询知识扩展
     * @param name
     * @return
     */
    public Map findBookType(String name);

    public void deleteKnowledgeAll();

    /**
     * 查询数据库基础属性和专业属性知识元总数
     */
    public Set getBaseKnowledgeWords();
    public Set getProKnowledgeWords();

    public List getAboutWords(String word);

    /**
     * 纠错
     */
    public int errorCorrection(KnowledgeReviewWithBLOBs knowledgeReview);

    /**
     * 删除知识元关联书
     * @param knowledgeid
     * @return
     */
    public int deleteBooks(String knowledgeid);

    /**
     * 纠错列表
     * @param map
     * @return
     */
    public Map findAllReview(Map map);

    /**
     * 纠错审核
     * @param checkBoxKnowledgeId
     */
    public void examine(String[] checkBoxKnowledgeId,String state,String userName);

    /**
     * 获取纠错信息
     */
    public KnowledgeReviewWithBLOBs getKnowledgeReviewInfoById(int id);

    /**
     * 查询书名
     * @param
     * @return
     */
    public List<String> findBookName(String knowledgeId,String propertyName);




}
