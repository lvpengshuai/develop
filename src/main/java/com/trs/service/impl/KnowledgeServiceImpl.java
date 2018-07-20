package com.trs.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.trs.core.annotations.Log;
import com.trs.core.util.StringUtil;
import com.trs.mapper.KnowledgeMapper;
import com.trs.mapper.KnowledgeReviewMapper;
import com.trs.mapper.KnowledgeSegmentBookMapper;
import com.trs.model.*;
import com.trs.service.KnowledgeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * Created by pz on 2017/3/10.
 */
@Service("knowledgeService")
public class KnowledgeServiceImpl implements KnowledgeService {

    @Resource
    KnowledgeReviewMapper knowledgeReviewMapper;
    @Resource
    private KnowledgeMapper knowledgeMapper;
    @Resource
    private KnowledgeSegmentBookMapper knowledgeSegmentBookMapper;

    /**
     * 分页查询所有知识元
     *
     * @param map
     * @return
     */
//    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_SELECT, targetType = com.trs.model.Log.LOG_TARGETTYPE_ONTOLOGYINFOMANAGER, description = "查看知识元")
    @Override
    public Map findAll(Map map) {

        List<Knowledge> knowledgeElementsList = knowledgeMapper.findAll(map);
        int total = knowledgeMapper.total(map);
        Map resultMap = new HashMap();

        resultMap.put("total", total);
        resultMap.put("rows", knowledgeElementsList);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));

        return resultMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteKnowledgeAll() {
        //knowledgeMapper.deleteAll();
        knowledgeSegmentBookMapper.deleteAll();
    }

    @Override
    public Knowledge selectByPrimaryKey(Integer id) {

        return knowledgeMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int insertBaseKnowledgeElement(Knowledge knowledge) {
        // knowledgeMapper.deleteByName(knowledge.getKnowledgeName());
        try {
            knowledgeMapper.insert(knowledge);
            return knowledge.getId();
        } catch (Exception e) {
            try {
                Map insertMap;
                Map propertyMapOld;
                Map newPropertyMap;
                Knowledge oldKnowledge = knowledgeMapper.findByName(knowledge.getKnowledgeName());
                String oldPropertyString = oldKnowledge.getProperty();
                String newPropertyString = knowledge.getProperty();
                if (!StringUtil.isEmpty(oldPropertyString) && !StringUtil.isEmpty(newPropertyString)) {
                    JSON json = JSON.parseObject(newPropertyString);
                    newPropertyMap = JSON.toJavaObject(json, Map.class);
                    JSON jsonOld = JSON.parseObject(oldPropertyString);
                    propertyMapOld = JSON.toJavaObject(jsonOld, Map.class);
                    propertyMapOld.putAll(newPropertyMap);
                    insertMap = propertyMapOld;
                } else if (!StringUtil.isEmpty(oldPropertyString)) {
                    JSON jsonOld = JSON.parseObject(oldPropertyString);
                    insertMap = JSON.toJavaObject(jsonOld, Map.class);
                } else if (!StringUtil.isEmpty(newPropertyString)) {
                    JSON jsonOld = JSON.parseObject(newPropertyString);
                    insertMap = JSON.toJavaObject(jsonOld, Map.class);
                } else {
                    insertMap = null;
                }
                String propertyString = JSON.toJSONString(insertMap);
                oldKnowledge.setProperty(propertyString);
                knowledgeMapper.updateByPrimaryKeyWithBLOBs(oldKnowledge);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return 0;
        }
    }


    @Override
    public int updateByBaseKnowledgeElementId(Knowledge knowledge) {
        /**
         * 通过id发起同步命令
         * 1、获取id
         * 2、获取名称调用第三方知识元接口
         */
        return knowledgeMapper.updateByPrimaryKeySelective(knowledge);
    }

    /**
     * ************************************专业属性*************************************************************
     */

    /**
     * 批量写入关联书
     *
     * @param
     * @return
     */
    @Override
    @Transactional
    public void insertBook(List<KnowledgeSegmentBookWithBLOBs> books) {
        try {
            for (int i = 0; i < books.size(); i++) {
                knowledgeSegmentBookMapper.insert(books.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量写入关联书
     *
     * @param knowledgeSegmentBook
     * @return
     */
    @Override
    @Transactional
    public int insertBooks(KnowledgeSegmentBookWithBLOBs knowledgeSegmentBook) {
        knowledgeSegmentBookMapper.deleteByKnowledgeId(knowledgeSegmentBook.getKnowledgeid());
        return knowledgeSegmentBookMapper.insert(knowledgeSegmentBook);

    }

    @Override
    public int deleteBooks(String knowledgeid) {
        return knowledgeSegmentBookMapper.deleteByKnowledgeId(knowledgeid);
    }

    /**
     * 可以通过知识元名称{knowledgeid}，知识元属性名称{propertyname,}书名查询{bookname},查询
     * page,为当前页，pageSize为返回数大小
     *
     * @param record
     * @return
     */
    public List findByINCProerty(KnowledgeSegmentBook record) {
        return knowledgeSegmentBookMapper.findByINCProerty(record);
    }

    public List findBlobsByINCProerty(KnowledgeSegmentBook record) {
        return knowledgeSegmentBookMapper.findBlobsByINCProerty(record);
    }

    /**
     * 统计此属性下关联书，或者关联某本书的片段的数量
     */
    @Override
    public int countPropertyBook(Map map) {
        return knowledgeSegmentBookMapper.countPropertyBook(map);
    }

    /**
     * 通过名称查询知识元
     *
     * @param name
     * @return
     */
    @Override
    public Knowledge findByName(String name) {
        Knowledge knowledge = knowledgeMapper.findByName(name);
        return knowledge;
    }

    /**
     * 知识元详情页数据
     *
     * @param name
     * @return
     */
    @Override
    public Map knowledgeResult(String name) {
        Map result = new HashMap();
        List aboutWordsResult = new ArrayList();
        Map longProperty = new HashMap();
        try {
            Map map_property = new HashMap();
            Knowledge knowledge = knowledgeMapper.findByName(name);
            Map<String, String> map = JSON.parseObject(knowledge.getProperty(), new TypeReference<Map<String, String>>() {
            });
            Iterator<String> i = map.keySet().iterator();
            while (i.hasNext()) {
                String key = i.next();

                if (key.contains("结构式")) {
                    String src = map.get(key).toString();
                    src = src.substring(src.indexOf("Image/"), src.indexOf("</image"));
                    result.put("structural", src);
                } else if (map.get(key).toString().length() < 25) {
                    map_property.put(key, map.get(key));
                } else if (map.get(key).toString().length() < 79) {
                    //长属性
                    longProperty.put(key, map.get(key));
                } else {
                    result.put("range", map.get(key).substring(0, map.get(key).length() / 3));
                    result.put("longRange", map.get(key));
                }

            }

            result.put("id", knowledge.getId());
            result.put("name", name);
            result.put("englishName", knowledge.getEnglishName());
            result.put("property", map_property);
            result.put("longProperty", longProperty);
            aboutWordsResult = knowledgeMapper.selectAboutWords(knowledge.getParentClass());
            if (aboutWordsResult.isEmpty()) {
                aboutWordsResult.add("化学工业");
            }
            result.put("aboutWords", aboutWordsResult);

            return result;
        } catch (Exception e) {
            result.put("name", name);
            aboutWordsResult.add("化学工业");
            result.put("aboutWords", aboutWordsResult);
            return result;
        }

    }

    @Override
    public List getAboutWords(String word) {
        return knowledgeMapper.selectAboutWords(word);
    }

    /**
     * 查找知识元关联书的属性名称+key的列表，使用需要迭代map
     *
     * @param name
     * @return
     */
    @Override
    public Map findBookType(String name) {
        Map map = new HashMap();
        Map map_property = new HashMap();
        try {
            List<String> properties = knowledgeSegmentBookMapper.selectPropertyByKnowledgeName(name);
            if (properties == null || properties.isEmpty()) {
                return null;
            } else {
                Iterator<String> i = properties.iterator();
                while (i.hasNext()) {
                    String propertyname = i.next();
                    map.put("knowledgeid", name);
                    map.put("propertyname", propertyname);
                    int cout = knowledgeSegmentBookMapper.countPropertyBook(map);
                    if (cout < 1) {
                        continue;
                    }
                    map_property.put(propertyname, cout);


                }
                return map_property;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Knowledge> selectData() {
        return knowledgeMapper.selectData();
    }

    /**
     * 获取所有基础属性的知识元词
     *
     * @return
     */
    @Override
    public Set getBaseKnowledgeWords() {
        return knowledgeMapper.selectAllWords();
    }

    /**
     * 获取所有专业属性的知识元词
     *
     * @return
     */
    @Override
    public Set getProKnowledgeWords() {
        return knowledgeSegmentBookMapper.selectAllWords();
    }

    /**
     * 知识元纠错记录
     */
    @Override
    public int errorCorrection(KnowledgeReviewWithBLOBs knowledgeReview) {
        return knowledgeReviewMapper.insert(knowledgeReview);
    }

    /**
     * 分页查询所有知识元纠错信息
     *
     * @param map
     * @return
     */

//    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_SELECT, targetType = com.trs.model.Log.LOG_TARGETTYPE_ONTOLOGYINFOMANAGER, description = "查看纠错信息")
    @Override
    public Map findAllReview(Map map) {

        List<KnowledgeReview> reviews = knowledgeReviewMapper.findAll(map);
        int total = knowledgeReviewMapper.total(map);
        Map resultMap = new HashMap();

        resultMap.put("total", total);
        resultMap.put("rows", reviews);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));

        return resultMap;
    }

//    @Log(operationType = com.trs.model.Log.LOG_OPERTYPE_VERIFY, targetType = com.trs.model.Log.LOG_TARGETTYPE_ONTOLOGYINFOMANAGER, description = "审核知识元")
    @Override
    public void examine(String[] checkBoxKnowledgeId, String state, String userName) {
        for (int i = 0; i < checkBoxKnowledgeId.length; i++) {
            KnowledgeReview knowledgeReview = knowledgeReviewMapper.selectByPrimaryKey(Integer.valueOf(checkBoxKnowledgeId[i]));
            knowledgeReview.setStatus(Integer.valueOf(state));
            knowledgeReview.setExamineDate(new Date());
            knowledgeReview.setManager(userName);
            knowledgeReviewMapper.updateByPrimaryKey(knowledgeReview);
        }
    }

    /**
     * 通过id获取信息
     *
     * @param id
     * @return
     */
    @Override
    public KnowledgeReviewWithBLOBs getKnowledgeReviewInfoById(int id) {
        return knowledgeReviewMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询书名
     */
    public List<String> findBookName(String knowledgeId, String propertyName) {
        Map map = new HashMap();
        map.put("knowledgeId", knowledgeId);
        map.put("propertyName", propertyName);
        return knowledgeSegmentBookMapper.findBookName(map);
    }
}