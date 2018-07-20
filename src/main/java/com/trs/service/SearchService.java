package com.trs.service;

import com.trs.client.TRSException;
import com.trs.core.util.SearchResult;

import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2017/3/21.
 */
public interface SearchService {
    /**
     * 检索列表信息
     * @param tableName 表名
     * @param searchword trssql
     * @param orderBy  排序方式
     * @param pageIndex 页数
     * @param pageSize  每页显示条数
     * @return
     */
    SearchResult getResultData(String tableName, String searchword, String orderBy, int pageIndex, int pageSize, boolean reconnect, String licenceCode, String type) throws TRSException;

    /**
     * 获取知识元
     * @param tableName
     * @param searchword
     * @return
     * @throws TRSException
     */
    Map getBookClass(String tableName, String searchword, String isIndex) throws TRSException;

    /**
     * 获取相关检索词
     * @param trsList
     * @return
     * @throws TRSException
     */
    List getSearchWord(List trsList, String word) throws TRSException;

    /**
     * 获取相关作者
     * @param trsList
     * @return
     * @throws TRSException
     */
    List getAuthor(List trsList) throws TRSException;

    /**
     * 相应年份的数量
     * @param tableName
     * @param searchword
     * @return
     * @throws TRSException
     */
    List getYearCount(String tableName, String searchword) throws TRSException;

    /**
     * 相关资源检索检索
     * @param tableName
     * @param searchword
     * @return
     * @throws TRSException
     */
    SearchResult getRelatedtData(String tableName, String searchword) throws TRSException;

    /**
     *
     * @param keywords 检索关键词，多个关键词用“;”分隔
     * @param type 为空代表全部
     * @return
     */
    Map getResource(String keywords, String type);


    /**
     * 根据词匹配最相关的词
     * @param word
     * @return
     */
    String getWord(String word);

    SearchResult bookAnalyse(String tableName, String searchword, String orderBy, int pageIndex, int pageSize) throws TRSException;


    /**
     *
     * @param keyWord 关键词
     * @param type  类型
     * @param organ 机构
     * @param time  时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param bookName 书名
     * @param people 人物
     * @return
     */
    String getSql(String keyWord, String type, String organ, String time, String startTime, String endTime, String bookName, String people, String entry, String source);


    Map getFrequency(String tableName, String searchWord);

    Map<String, Object> resultDataExcel(String type, String[] bookCode, String[] zid);
}
