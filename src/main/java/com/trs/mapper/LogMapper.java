package com.trs.mapper;

import com.trs.model.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-3-9.
 */
public interface LogMapper {
    /**
     * 查询所有日志
     * @param map
     * @return
     */
    public List<Log> findAll(Map map);

    /**
     * 查询日志总数
     * @param map
     * @return
     */
    public int total(Map map);

    /**
     * 添加日志
     * @param log
     */
    public void addLog(Log log);

    /**
     * 删除日志
     * @param ids
     */
    public void deleteLog(List ids);

    int getCountByIpAndType(Map map);
}
