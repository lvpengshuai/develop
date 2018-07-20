package com.trs.service;

import com.trs.model.SearchHistory;

import java.util.List;
import java.util.Map;

/**
 * Created by pz on 2017/5/10.
 */
public interface SearchHistoryService {
    /**
     * 分页查询数据列表
     *
     * @return
     */
    public Map findAll(Map map);

    /**
     *移除历史记录
     * @param id
     * @return
     */
    public int remove(int id,Boolean isAll,String userName);

    /**
     * 插入檢索歷史
     * @param searchHistory
     * @return
     */
    public int insertSearchHistroy(SearchHistory searchHistory);

    /**
     * 获取热门搜索词
     * @return
     */
    List getHotSearchWord(Map map);
}
