package com.trs.web.client;

import com.alibaba.fastjson.JSON;
import com.trs.client.TRSException;
import com.trs.core.util.Config;
import com.trs.core.util.RequestUtils;
import com.trs.service.SearchService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by xubo on 2017/3/20.
 */
@Controller
@RequestMapping(value = "/analy")
public class AnalysisController {
    @Resource
    private SearchService searchService;


    /**
     * 跳转到搜索结果分析
     * @param request
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/searchanalysis")
    public ModelAndView searchanalysis(HttpServletRequest request, ModelMap modelMap) throws TRSException {
        return new ModelAndView("client/view/searchanalysis", modelMap);
    }

    /**
     * 搜索结果分析页查询
     * @param request
     * @param modelMap
     * @param response
     * @return
     * @throws TRSException
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @RequestMapping(value = "/statis", produces = "text/html;charset=UTF-8")
    public String statis(HttpServletRequest request, ModelMap modelMap, HttpServletResponse response) throws TRSException, UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        RequestUtils requestUtils = new RequestUtils(request);
        String skw = requestUtils.getParameterAsString("skw", "");
        System.out.println(skw);
        Map frequency = null;
        Map<Integer, Object> maps = new HashMap();

        //名字最终值
        Map valuemap = new HashMap();


        String frequencyJson = "";
        Map<Integer, Integer> yearmap = null;
        String[] kw = skw.split(",");
        for (int i = 0; i < kw.length; i++) {
            frequency = searchService.getFrequency(Config.getKey("trs.server.search"), "TextContent='" + kw[i] + "'");
            //yearmapsmap.put(frequency.get("name"),frequency.get("yearmaps"));
            //遍历年份数据 {年份=值}
            yearmap = (Map<Integer, Integer>) frequency.get("yearmaps");
            valuemap.put(kw[i],yearmap);
        }

        HashSet<Object> objectSet = new HashSet<>();
        HashMap hashMap = new HashMap();


        for (Object key1 : valuemap.keySet()) {
            Map valMap = (Map) valuemap.get(key1);
            for (Object key2 : valMap.keySet()) {
                objectSet.add(key2);
            }
        }

        for (Object key1 : valuemap.keySet()) {
            Map valMap = (Map) valuemap.get(key1);
            for (Object key2 : valMap.keySet()) {
                objectSet.add(key2);
            }
        }

        for (Object str : objectSet) {
            HashMap hashMap1 = new HashMap();
            HashMap hashMap2 = new HashMap();
            HashSet hashSet = new HashSet();
            for (Object key1 : valuemap.keySet()) {


                Map valMap = (Map) valuemap.get(key1);
                int num =0;
                for (Object key2 : valMap.keySet()) {
                    if(str.equals(key2)){
                        int o = (int) valMap.get(key2);
                        num=o;
                        break;
                    }

                }
                hashMap2.put(key1,num);
                hashSet.add(hashMap2);
            }
            hashMap1.put(str,hashMap2);
            hashMap.putAll(hashMap1);

        }
        //System.out.println(JSONObject.fromObject(hashMap));
        frequencyJson = JSON.toJSONString(hashMap, true);
        System.out.println(frequencyJson);
        return frequencyJson;
    }
}