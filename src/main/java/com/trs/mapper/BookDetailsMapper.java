package com.trs.mapper;

/**
 * Created by xuxuecheng on 2017/8/25.
 */

import com.trs.model.BookDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BookDetailsMapper {
    /**
     * 获取对应图书的图书词条
     *
     * @param bookcode
     * @param entry
     * @param pageIndex
     * @return
     */
    public List<BookDetails> getEntrys(@Param("bookcode") String bookcode,
                                       @Param("entry") String entry,
                                       @Param("pageIndex") Integer pageIndex,
                                       @Param("pageSize") Integer pageSize);

    /**
     * 获取对应图书的图书词条总数
     *
     * @param bookcode
     * @param entry
     * @return
     */
    public Integer getEntrysCount(@Param("bookcode") String bookcode, @Param("entry") String entry);

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
    public BookDetails getBookDetailsByZid(@Param("zid")String zid,@Param("bookcode")String bookCode);

    /**
     * 通过zid获取文章wz信息
     *
     * @param zid
     * @return
     */
    public BookDetails getBookListByZid(@Param("zid") String zid, @Param("bookcode") String bookcode);

    /**
     * 更新阅读量
     *
     * @param bookDetails
     * @return
     */
    public int setReadCount(BookDetails bookDetails);

    /**
     * 查询热门文章
     *
     * @return
     */
    public List<BookDetails> getHotArticles(String bookCode);

    /**
     * 根据zid查询一级目录
     *
     * @param zid
     * @return
     */
    public BookDetails getFirstCatalogByZid(@Param("zid") String zid, @Param("bookcode") String bookcode);

    public List<BookDetails> getHtmlContent(@Param("bookcode") String bookcode);

    public Integer getHtmlContentNum(@Param("bookcode") String bookcode);

    public List<BookDetails> getBookDetailsAuthor(@Param("bookcode") String bookcode, @Param("pageIndex") Integer pageIndex);

    public Integer getBookDetailsAuthorNum(@Param("bookcode") String bookcode);

    public List<BookDetails> getBookDetailTopic(@Param("bookcode") String bookcode, @Param("source") String source, @Param("pageIndex") Integer pageIndex, @Param("currPage") Integer currPage);

    public Integer getBookDetailTopicNum(@Param("bookcode") String bookcode, @Param("source") String source);

    public List<BookDetails> getBookDetailMap(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("entry") String entry, @Param("exarea") String exarea, @Param("bookcode") String bookcode);

    public List<BookDetails> getHotBookDetailMap(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("entry") String entry, @Param("exarea") String exarea, @Param("bookcode") String bookcode);

    public Integer getBookDetailMapNum(@Param("bookcode") String bookcode, @Param("type") String type);

    public String getBookDetailsMechanism(@Param("bookcode") String bookcode, @Param("zid") String zid,@Param("sourceType") String sourceType);

    public List<BookDetails> getBookDetailsMechanismType(@Param("bookcode") String bookcode, @Param("titleType") String titleType,@Param("sourceType") String sourceType,@Param("pageBegin") Integer pageBegin,@Param("currPage") Integer currPage);


    public Integer getBookDetailsMechanismTypeCount(@Param("bookcode") String bookcode, @Param("titleType") String titleType,@Param("sourceType") String sourceType);


    public List<BookDetails> getBookDetailsMechanismTitle(String bookcode);

    public Integer getBookDetailsMechanismNum(@Param("bookcode") String bookcode);

    public List<BookDetails> getDirectoryByZid(@Param("zid") String zid, @Param("bookcode") String bookcode);

    public Map<String, Integer> getCountByEntry();
}
