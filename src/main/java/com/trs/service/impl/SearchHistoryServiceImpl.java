package com.trs.service.impl;

import com.trs.mapper.SearchHistoryMapper;
import com.trs.model.SearchHistory;
import com.trs.service.SearchHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pz on 2017/5/10.
 */
@Service("searchHistoryService")
public class SearchHistoryServiceImpl implements SearchHistoryService {

    @Resource
    SearchHistoryMapper searchHistoryMapper;

    /**
     * 分页查询数据列表
     *
     * @return
     */
    @Override
    public Map findAll(Map map) {

        List<SearchHistory> SearchHistoryList = searchHistoryMapper.findAll(map);
        int total = searchHistoryMapper.total(map);
        Map resultMap = new HashMap();

        resultMap.put("total", total);
        resultMap.put("rows", SearchHistoryList);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));

        return resultMap;

    }

    /**
     * 移除历史记录
     *
     * @param id
     * @return
     */
    @Override
    public int remove(int id, Boolean isAll, String userName) {

        if (isAll) {
            try {
                SearchHistory searchHistory = new SearchHistory();
                searchHistory.setStatus(0);
                searchHistory.setUserid(userName);
                searchHistoryMapper.updateStatusByUserid(searchHistory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            SearchHistory searchHistory = searchHistoryMapper.selectByPrimaryKey(id);
            searchHistory.setStatus(0);
            searchHistoryMapper.updateByPrimaryKey(searchHistory);
        }
        return 1;
    }

    @Override
    public int insertSearchHistroy(SearchHistory searchHistory) {
        return searchHistoryMapper.insert(searchHistory);
    }

    @Override
    public List getHotSearchWord(Map map) {
        List resultList = new ArrayList();

        List<Map> time = (List) map.get("time");
        String appPath = String.valueOf(map.get("appPath"));
        Map paramMap = new HashMap();
        for (Map m : time) {
            paramMap.put("begin", String.valueOf(m.get("begin")));
            paramMap.put("end", String.valueOf(m.get("end")));
        }
        paramMap.put("count", 30);
        paramMap.put("ipStart", String.valueOf(map.get("ipStart")));
        paramMap.put("ipEnd", String.valueOf(map.get("ipEnd")));
        List<Map> tmpList = searchHistoryMapper.getHotSearchWord(paramMap);

        for (Map m : tmpList) {
            Map wordMap = new HashMap();
            try {

                String searchname = String.valueOf(m.get("searchname"));
                int weight = Integer.parseInt(String.valueOf(m.get("weight")));

                wordMap.put("text", searchname);
                wordMap.put("weight", ((weight * 0.01) + 2) * 10);
                wordMap.put("link", appPath + "/search?keyWord=" + searchname);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resultList.add(wordMap);
        }
        return resultList;
    }

}
