package com.trs.service;

import java.util.Map;

/**
 * Created by zly on 2017-6-6.
 */
public interface AccessLogService {

    public Map getAllAccess(Map map);

    public void addAccess(Map map);

    void addDownLoadData(Map map);

    Map getTodayAndHistoryData(Map map);

    Map getAccessByPage(Map map);

    Map getHotResource(Map map);

    Map getUserLog(Map map);
}

