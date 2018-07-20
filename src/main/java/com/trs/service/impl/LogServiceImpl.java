package com.trs.service.impl;

import com.trs.mapper.LogMapper;
import com.trs.model.Log;
import com.trs.model.User;
import com.trs.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-3-9.
 */
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;


    @Override
    public void addLog(User user, String description, String opertype, String targetType, String ip, String isWarn) {
        Log log = new Log();

        String operId = "";
        String operUsername = "";

        if (user != null) {
            operId = String.valueOf(user.getId());
            operUsername = user.getUsername();
        }
        log.setOperId(operId);
        log.setOperUsername(operUsername);
        log.setDescription(description);
        log.setGmtCreate(new Date());
        log.setOperType(opertype);
        log.setTargetType(targetType);
        log.setIp(ip);
        log.setIsWarn(isWarn);
        logMapper.addLog(log);
    }

    @com.trs.core.annotations.Log(operationType = com.trs.model.Log.LOG_OPERTYPE_SELECT, targetType = com.trs.model.Log.LOG_TARGETTYPE_LogMANAGER, description = "查看log日志")
    @Override
    public Map listLogs(Map map) {
        Map resultMap = new HashMap();

        List<Log> logs = logMapper.findAll(map);
        int total = logMapper.total(map);

        resultMap.put("rows", logs);
        resultMap.put("total", total);
        resultMap.put("pageSize", map.get("pageSize"));
        resultMap.put("currPage", map.get("currPage"));
        resultMap.put("search", map.get("search"));
        resultMap.put("sort", map.get("sort"));
        resultMap.put("order", map.get("order"));
        resultMap.put("targetType", map.get("targetType"));
        resultMap.put("operType", map.get("operType"));
        resultMap.put("start", map.get("start"));
        resultMap.put("end", map.get("end"));

        return resultMap;
    }

    @com.trs.core.annotations.Log(operationType = com.trs.model.Log.LOG_OPERTYPE_DELETE, targetType = com.trs.model.Log.LOG_TARGETTYPE_LogMANAGER, description = "删除log日志")
    @Override
    public Map deleteLog(List ids) {
        Map resultMap = new HashMap();

        logMapper.deleteLog(ids);

        resultMap.put("msg", "");
        resultMap.put("code", "0");
        resultMap.put("total", ids.size());

        return resultMap;
    }

    @Override
    public int getCountByIpAndType(Map map) {

        return logMapper.getCountByIpAndType(map);
    }
}
