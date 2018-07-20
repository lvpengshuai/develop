package com.trs.service.impl;

import com.trs.core.util.DateUtil;
import com.trs.mapper.AccessLogMapper;
import com.trs.mapper.SearchHistoryMapper;
import com.trs.model.SearchHistory;
import com.trs.service.AccessLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-6-6.
 */
@Service
public class AccessLogServiceImpl implements AccessLogService {

    @Resource
    private AccessLogMapper accessLogMapper;
    @Resource
    private SearchHistoryMapper searchHistoryMapper;


    @Override
    public Map getAllAccess(Map map) {
        Map resultMap = new HashMap();
        String type = (String) map.get("type");
        List<Map> time = (List) map.get("time");
        if ("0".equals(type)) {
            List bookTotal = new ArrayList();
            List journalTotal = new ArrayList();
            List standardTotal = new ArrayList();
            for (Map m : time) {
                Map paramMap = new HashMap();
                paramMap.put("type", 1);
                paramMap.put("begin", String.valueOf(m.get("begin")));
                paramMap.put("end", String.valueOf(m.get("end")));
                paramMap.put("ipStart", String.valueOf(map.get("ipStart")));
                paramMap.put("ipEnd", String.valueOf(map.get("ipEnd")));
                int book = accessLogMapper.pageList(paramMap);
                bookTotal.add(book);

                Map paramMap1 = new HashMap();
                paramMap1.put("type", 2);
                paramMap1.put("begin", String.valueOf(m.get("begin")));
                paramMap1.put("end", String.valueOf(m.get("end")));
                paramMap1.put("ipStart", String.valueOf(map.get("ipStart")));
                paramMap1.put("ipEnd", String.valueOf(map.get("ipEnd")));
                int jounal = accessLogMapper.pageList(paramMap1);
                journalTotal.add(jounal);

                Map paramMap2 = new HashMap();
                paramMap2.put("type", 3);
                paramMap2.put("begin", String.valueOf(m.get("begin")));
                paramMap2.put("end", String.valueOf(m.get("end")));
                paramMap2.put("ipStart", String.valueOf(map.get("ipStart")));
                paramMap2.put("ipEnd", String.valueOf(map.get("ipEnd")));
                int standard = accessLogMapper.pageList(paramMap2);
                standardTotal.add(standard);
            }

            resultMap.put("book", bookTotal);
            resultMap.put("journal", journalTotal);
            resultMap.put("standard", standardTotal);
        } else if ("1".equals(type)) {
            List total = new ArrayList();
            for (Map m : time) {
                Map paramMap = new HashMap();
                paramMap.put("begin", String.valueOf(m.get("begin")));
                paramMap.put("end", String.valueOf(m.get("end")));
                paramMap.put("ipStart", String.valueOf(map.get("ipStart")));
                paramMap.put("ipEnd", String.valueOf(map.get("ipEnd")));
                total.add(searchHistoryMapper.pageList(paramMap));
            }

            resultMap.put("total", total);
        } else if ("2".equals(type)) {
            List journalTotal = new ArrayList();
            List standardTotal = new ArrayList();
            for (Map m : time) {

                Map paramMap1 = new HashMap();
                paramMap1.put("type", 2);
                paramMap1.put("begin", String.valueOf(m.get("begin")));
                paramMap1.put("end", String.valueOf(m.get("end")));
                paramMap1.put("ipStart", String.valueOf(map.get("ipStart")));
                paramMap1.put("ipEnd", String.valueOf(map.get("ipEnd")));
                int jounal = accessLogMapper.getDownloadData(paramMap1);
                journalTotal.add(jounal);

                Map paramMap2 = new HashMap();
                paramMap2.put("type", 3);
                paramMap2.put("begin", String.valueOf(m.get("begin")));
                paramMap2.put("end", String.valueOf(m.get("end")));
                paramMap2.put("ipStart", String.valueOf(map.get("ipStart")));
                paramMap2.put("ipEnd", String.valueOf(map.get("ipEnd")));
                int standard = accessLogMapper.getDownloadData(paramMap2);
                standardTotal.add(standard);
            }

            resultMap.put("journal", journalTotal);
            resultMap.put("standard", standardTotal);
        }


        return resultMap;
    }

    @Override
    public Map getTodayAndHistoryData(Map map) {
        Map resultMap = new HashMap();

        Map paramMap = new HashMap();
        paramMap.put("ipStart", String.valueOf(map.get("ipStart")));
        paramMap.put("ipEnd", String.valueOf(map.get("ipEnd")));

        int todayAccessTotal = accessLogMapper.pageList(map);
        int todayDownloadTotal = accessLogMapper.getDownloadData(map);
        int todaySearchTotal = searchHistoryMapper.pageList(map);

        int hisAccessTotal = accessLogMapper.pageList(paramMap);
        int hisDownloadTotal = accessLogMapper.getDownloadData(paramMap);
        int hisSearchTotal = searchHistoryMapper.pageList(paramMap);

        resultMap.put("todayAccess", todayAccessTotal);
        resultMap.put("hisAccess", hisAccessTotal);

        resultMap.put("todayDownload", todayDownloadTotal);
        resultMap.put("hisDownload", hisDownloadTotal);

        resultMap.put("todaySearch", todaySearchTotal);
        resultMap.put("hisSearch", hisSearchTotal);


        return resultMap;
    }

    @Override
    public Map getAccessByPage(Map map) {
        Map resultMap = new HashMap();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String type = (String) map.get("type");
        if ("0".equals(type)) {
            map.put("begin", sdf.format(DateUtil.getDayBegin()));
            map.put("end", sdf.format(DateUtil.getDayEnd()));

            resultMap.put("item", accessLogMapper.selectAccessByPage(map));
            resultMap.put("itemCount", accessLogMapper.getAccessByPageTotal(map));
        } else if ("1".equals(type)) {
            resultMap.put("item", accessLogMapper.selectAccessByPage(map));
            resultMap.put("itemCount", accessLogMapper.getAccessByPageTotal(map));
        } else if ("2".equals(type)) {
            map.put("begin", sdf.format(DateUtil.getDayBegin()));
            map.put("end", sdf.format(DateUtil.getDayEnd()));

            List<SearchHistory> all = searchHistoryMapper.findAll(map);
            List items = new ArrayList();
            for (SearchHistory searchHistory : all) {
                Map m = new HashMap();
                m.put("ip", searchHistory.getIp());
                try {
                    m.put("gmt_create", sdf.parse(searchHistory.getCreatetime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                items.add(m);
            }
            resultMap.put("item", items);
            resultMap.put("itemCount", searchHistoryMapper.total(map));
        } else if ("3".equals(type)) {
            List<SearchHistory> all = searchHistoryMapper.findAll(map);
            List items = new ArrayList();
            for (SearchHistory searchHistory : all) {
                Map m = new HashMap();
                m.put("ip", searchHistory.getIp());
                try {
                    m.put("gmt_create", sdf.parse(searchHistory.getCreatetime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                items.add(m);
            }
            resultMap.put("item", items);
            resultMap.put("itemCount", searchHistoryMapper.total(map));
        } else if ("4".equals(type)) {
            map.put("begin", sdf.format(DateUtil.getDayBegin()));
            map.put("end", sdf.format(DateUtil.getDayEnd()));

            resultMap.put("item", accessLogMapper.selectDownloadByPage(map));
            resultMap.put("itemCount", accessLogMapper.getDownloadByPageTotal(map));
        } else if ("5".equals(type)) {
            resultMap.put("item", accessLogMapper.selectDownloadByPage(map));
            resultMap.put("itemCount", accessLogMapper.getDownloadByPageTotal(map));
        }

        return resultMap;
    }

    @Override
    public Map getHotResource(Map map) {
        Map resultMap = new HashMap();

        String tab = String.valueOf(map.get("tab"));

        List<Map> resouceList = new ArrayList<>();
        List<Map> resource = new ArrayList<>();
        int total = 0;

        if ("0".equals(tab)) {
            resource = accessLogMapper.getHotResource(map);
            total = accessLogMapper.getHotResourceTotal(map);
        } else if ("1".equals(tab)) {
            resource = accessLogMapper.getDownladResource(map);
            total = accessLogMapper.getDownladResourceTotal(map);
        }
        for (Map m : resource) {
            m.get("name");
            m.get("count");
            m.get("resource_id");
            resouceList.add(m);
        }

        resultMap.put("resourceList", resouceList);
        resultMap.put("total", total);

        return resultMap;
    }

    @Override
    public Map getUserLog(Map map) {
        Map resultMap = new HashMap();

        List userlog = accessLogMapper.getUserlog(map);
        int userlogTotal = accessLogMapper.getUserlogTotal(map);

        resultMap.put("userlog", userlog);
        resultMap.put("total", userlogTotal);

        return resultMap;
    }

    @Override
    public void addAccess(Map map) {
        accessLogMapper.insert(map);
    }

    @Override
    public void addDownLoadData(Map map) {
        accessLogMapper.addDownloadData(map);
    }
}


