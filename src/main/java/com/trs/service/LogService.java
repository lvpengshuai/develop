package com.trs.service;

import com.trs.model.Log;
import com.trs.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-3-9.
 */
public interface LogService {

    /**
     * 添加日志
     * @param description
     * @param opertype
     * @param targetType
     */
    public void addLog(User user, String description, String opertype, String targetType, String ip, String isWarn);

    /**
     * 查询所有日志
     * @param map
     * @return
     */
    public Map listLogs(Map map);

    /**
     * 删除日志
     * @param ids
     */
    public Map deleteLog(List ids);

    int getCountByIpAndType(Map map);
}
