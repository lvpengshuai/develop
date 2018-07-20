package com.trs.web.admin;

import com.trs.core.annotations.Permission;
import com.trs.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-3-9.
 */
@Controller
public class LogController extends AbstractAdminController {

    @Resource
    private LogService logService;

    /*跳转日志页面*/
    @Permission(url = "/operate-record")
    @RequestMapping(value = "/operate-record", method = RequestMethod.GET)
    public ModelAndView toLog() {
        ModelAndView modelAndView = new ModelAndView("admin/log/log");
        modelAndView.addObject("title", "操作日志");

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/operate-records", method = RequestMethod.GET)
    public Map getLogs(HttpServletRequest request, HttpServletResponse response) {

        String pageSize = request.getParameter("pageSize");
        String currPage = request.getParameter("currPage");
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String targetType = request.getParameter("targetType");
        String operType = request.getParameter("operType");
        String start = request.getParameter("start");
        String end = request.getParameter("end");

        Map map = new HashMap();
        map.put("pageSize", Integer.parseInt(pageSize));
        map.put("currPage", Integer.parseInt(currPage));
        map.put("search", search);
        map.put("sort", sort);
        map.put("order", order);
        map.put("targetType", targetType);
        map.put("operType", operType);
        map.put("start", start);
        map.put("end", end);
        map.put("isWarn", "1");


        Map all = logService.listLogs(map);

        return all;
    }

    @ResponseBody
    @RequestMapping(value = "/operate-record/{ids}", method = RequestMethod.DELETE)
    public Map deleteLog(@PathVariable("ids") List ids, HttpServletRequest request, HttpServletResponse response) {

        Map map = logService.deleteLog(ids);

        return map;
    }
}
