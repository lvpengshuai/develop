package com.trs.web.client;

import com.trs.core.util.Config;
import com.trs.core.util.IPUtil;
import com.trs.core.util.Util;
import com.trs.model.Book;
import com.trs.model.BookClassify;
import com.trs.model.HotWord;
import com.trs.model.Organize;
import com.trs.service.BookDetailsService;
import com.trs.service.BookService;
import com.trs.service.HotWordService;
import com.trs.service.OrganizeOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by lcy on 2017/3/30.
 */
@Controller
public class IndexController {
    @Autowired
    private BookService bookService;
    @Autowired
    private HotWordService hotWordService;
    @Autowired
    private BookDetailsService bookDetailsService;
    @Resource
    private OrganizeOnlineService organizeOnlineService;

    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");

    /**
     * 登录页面
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session, HttpServletResponse response, Map<String, Object> map,HttpServletRequest request) {
        //首页中间轮播
        List<Book> booksByHot = bookService.getBookByHotType();
        //拆分轮播数据
        List<Book> booksByHotBig = new ArrayList<>();
        List<Book> booksByHotSmall = new ArrayList<>();
        for (int i = 0; i <= booksByHot.size() - 1 && i < 16; i++) {
            if (i % 4 == 0) {
                booksByHotBig.add(booksByHot.get(i));
            } else {
                booksByHotSmall.add(booksByHot.get(i));
            }
        }
        List<HotWord> hotWords = hotWordService.getIndexHotWords();
        Map<String, String> split = new LinkedHashMap<>();
        if (hotWords.size() != 0) {
            String[] split1 = hotWords.get(0).getWord().split("/");
            for (String str : split1) {
                try{
                    split.put(URLEncoder.encode(str,"utf-8"), str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }


        //查询所有数据的个数
        Map<String, Integer> countByEntry = bookDetailsService.getCountByEntry();
        if(!Util.isNull(countByEntry)){
            for(Map.Entry<String, Integer> entry:countByEntry.entrySet()){
                if(Util.toInt(entry.getValue(), 500) > 100){
                    countByEntry.put(entry.getKey(), Util.toInt(entry.getValue(), 500)/100*100);
                }
            }
        }
        //获取所有bookclassify
        List<BookClassify> bookClassifies = bookService.selectBookClassify();
        // 登陆判断展示
        Object username = session.getAttribute("userName");
        if (ONLINESTATUS.equals("ON")) {
            if (username == null || username == "") {
                List<Organize> ip = organizeOnlineService.selectAllIP();
                Map organizeMap = new HashMap();
                if (ip.size() != 0) {
                    for (int ii = 0; ii < ip.size(); ii++) {
                        //获取起始ip
                        String startIp = ip.get(ii).getIpStart();
                        //获取结束ip
                        String endIp = ip.get(ii).getIpEnd();

                        String id = ip.get(ii).getOrgName();
                        if (startIp != null && endIp != null && startIp != "" && endIp != "") {
                            //判断当前用户的ip是否在机构ip下
                            //System.out.println("当前登陆IP========="+IPUtil.getIpAddr(request));
                            if (IPUtil.getIp2long(startIp) <= IPUtil.getIp2long(IPUtil.getIpAddr(request)) && IPUtil.getIp2long(IPUtil.getIpAddr(request)) <= IPUtil.getIp2long(endIp)) {
                                Organize or=organizeOnlineService.selectOrganById(Integer.parseInt(id));
                                map.put("loginSign", 1);
                                map.put("org",or.getName());
                                organizeMap.put("loginSign",1);
                            }

                        }
                    }
                    if(organizeMap.size()==0){
                        map.put("loginSign", 2);
                    }
                }
            }else{
                List<Organize> ip = organizeOnlineService.selectAllIP();
                Map organizeMap = new HashMap();
                if (ip.size() != 0) {
                    for (int ii = 0; ii < ip.size(); ii++) {
                        //获取起始ip
                        String startIp = ip.get(ii).getIpStart();
                        //获取结束ip
                        String endIp = ip.get(ii).getIpEnd();

                        String id = ip.get(ii).getOrgName();
                        if (startIp != null && endIp != null && startIp != "" && endIp != "") {
                            //判断当前用户的ip是否在机构ip下
                            //System.out.println("当前登陆IP========="+IPUtil.getIpAddr(request));
                            if (IPUtil.getIp2long(startIp) <= IPUtil.getIp2long(IPUtil.getIpAddr(request)) && IPUtil.getIp2long(IPUtil.getIpAddr(request)) <= IPUtil.getIp2long(endIp)) {
                                Organize or=organizeOnlineService.selectOrganById(Integer.parseInt(id));
                                map.put("loginSign", 3);
                                map.put("org",or.getName());
                                organizeMap.put("loginSign",3);
                            }

                        }
                    }
                    if(organizeMap.size()==0){
                        map.put("loginSign", 4);
                    }
                }
            }

        }

        map.put("ONLINESTATUS", ONLINESTATUS);
        map.put("booksByHotBigs", booksByHotBig);
        map.put("booksByHotSmalls", booksByHotSmall);
        map.put("split", split);
        map.put("bookClassifies",bookClassifies);
        map.put("wzCount", Util.toInt(countByEntry.get("wz"),500));
        map.put("ktCount",Util.toInt(countByEntry.get("kt"),500));
        map.put("dsjCount",Util.toInt(countByEntry.get("dsj"),500));
        map.put("tsCount",Util.toInt(countByEntry.get("ts"),500));
        map.put("rwCount",Util.toInt(countByEntry.get("rw"),500));
        map.put("lwCount",Util.toInt(countByEntry.get("lw"),500));
        map.put("jgCount",Util.toInt(countByEntry.get("jg"),500));
        map.put("hyCount", Util.toInt(countByEntry.get("hy"), 500));

        ModelAndView modelAndView = new ModelAndView("client/index");
        return modelAndView;
    }

    /**
     * 首页，购买页面
     * @return
     */
    @RequestMapping(value = "/index_buy", method = RequestMethod.GET)
    public ModelAndView indexBuy() {
        ModelAndView modelAndView = new ModelAndView("client/usercenter/buy");
        return modelAndView;
    }
}
