package com.trs.service;


import com.trs.model.BookDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by xuxuecheng on 2017/08/24.
 */
public interface BookDetailsService {
    /**
     * 获取对应图书的词条
     *
     * @param bookcode
     * @param entry
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<BookDetails> getEntrys(String bookcode, String entry, Integer pageIndex, Integer pageSize);

    /**
     * 获取对应图书的词条
     *
     * @param bookcode
     * @param entry
     * @return
     */
    public Integer getEntrysCount(String bookcode, String entry);

    /**
     * 通过bookcode获取年鉴所有详细信息
     *
     * @param bookDetails
     * @return
     */
    public List<BookDetails> getBookDetailsList(BookDetails bookDetails);

    /**
     * 通过zid获取年鉴所有详细信息
     *
     * @param zid
     * @return
     */
    public BookDetails getBookDetailsByZid(String zid,String bookCode);

    /**
     * 通过zid获取文章wz信息
     *
     * @param zid
     * @return
     */
    public int getBookListByZid(String zid,String bookcode);

    /**
     * 更新阅读量
     *
     * @param bookDetails
     * @return
     */
    public int setReadCount(BookDetails bookDetails);

    public List<BookDetails> getHotArticles(String bookCode);

    /**
     * 根据zid查找一级目录
     *
     * @param zid
     * @return
     */
    public BookDetails getFirstCatalogByZid(String zid, String bookcode);

    /**
     * 大事记数据
     *
     * @return
     */
    public List<BookDetails> getHtmlContent(String bookcode, String date);

    /**
     * 大事记词条展示加工
     * @param list
     * @return
     */
    public List<Map<String, Object>> getDsjCitiao(List<BookDetails> list);

    /**
     * 大事记数据个数
     *
     * @param bookcode
     * @return
     */
    public Integer getHtmlContentNum(String bookcode);

    /**
     * 综述\概况
     *
     * @param bookcode
     * @param pageIndex
     * @return
     */
    public List<BookDetails> getBookDetailsAuthor(String bookcode, Integer pageIndex);

    /**
     * 综述\概况个数
     *
     * @param bookcode
     * @return
     */
    public Integer getBookDetailsAuthorNum(String bookcode);

    /**
     * 按时间和地址获取会议添加分页
     *
     * @param startTime
     * @param endTime
     * @param type
     * @param ads
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public Map getBookDetailMap(String startTime, String endTime, String type, String ads, int pageSize, int pageIndex, String bookcode);

    /**
     * 按时间和地址获取会议
     *
     * @param startTime
     * @param endTime
     * @param type
     * @param ads
     * @return
     */
    public Map getBookDetailMap(String startTime, String endTime, String type, String ads, String bookcode);

    public Integer getBookDetailMapNum(String bookcode);

    /**
     * 论文词条
     *
     * @param bookcode
     * @param pageIndex
     * @return
     */
    public List<BookDetails> getBookDetailPaper(String bookcode, String pageIndex);

    /**
     * 论文词条个数
     *
     * @param bookcode
     * @return
     */
    public Integer getBookDetailPaperCount(String bookcode);

    /**
     * 课题词条
     *
     * @param bookcode
     * @return
     */
    public List<BookDetails> getBookDetailTopic(String bookcode, String source, String pageIndex, String currPage);

    public Integer getBookDetailTopicNum(String bookcode, String source);

    /**
     * 机构词条
     *
     * @param bookcode
     * @return
     */
    public List getBookDetailsMechanism(String bookcode, String title,String sourceType,String titleType, String PageIndex, String currPage);

    public Integer getBookDetailsMechanismNum(String bookcode);

    /**
     * 机构词条分类
     */
    public List<BookDetails> getBookDetailsMechanismTitle(String bookcode);

    public List<BookDetails> getDirectoryByFid(String fid, String bookcode);

    /**
     * 首页各词条数量
     * @return
     */
    public Map<String, Integer> getCountByEntry();

}

