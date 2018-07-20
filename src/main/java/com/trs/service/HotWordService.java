package com.trs.service;

import com.trs.model.Book;
import com.trs.model.BookClassify;
import com.trs.model.HotWord;

import java.util.List;
import java.util.Map;

public interface HotWordService {
    /**
     * 添加热词
     *
     * @param hot
     */
    public void addHotWord(String hot);

    /**
     * 查询热词
     *
     * @return
     */
    public List<HotWord> getHotWords();

    public List<HotWord> getIndexHotWords();

    /**
     * 删除热词
     *
     * @param
     */
    public void deleteHotWord(String id);

    /**
     * 修改热词状态
     *
     * @param
     */
    public void updateHotWord(String id, String status);

    /**
     * 修改热词内容
     *
     * @param
     */
    public void updateHotWordHot(String id, String hot);

    /**
     * 得到全部的书
     *
     * @return
     */
    public List<Book> getBookAll(String pageSize, String currPage, String search);

    /**
     * 得到全部书的数量
     *
     * @return
     */
    public Integer getBookAllNum();

    /**
     * 修改书的轮播是否显示
     *
     * @param id
     * @param state
     */
    public void updateBookHotType(List<String> id, String state);

    public void updateBookClassofyOrder(List<Map<String, Object>> list);

    public List<BookClassify> getBookClassofyOrder();
}
