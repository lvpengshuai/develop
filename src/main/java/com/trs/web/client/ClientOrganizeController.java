package com.trs.web.client;

import com.trs.core.util.Config;
import com.trs.core.util.IPUtil;
import com.trs.core.util.Util;
import com.trs.mapper.OrganizeMapperOnline;
import com.trs.model.Organize;
import com.trs.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-6-5.
 */
@Controller
@RequestMapping(value = "/organize")
public class ClientOrganizeController {

    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");

    //请求url
    private static final String USERURL = Config.getKey("zas.userUrl");

    @Resource
    private OrganizeService organizeService;
    @Resource
    private OrganizeOnlineService organizeOnlineService;
    @Resource
    private OrganizeMapperOnline organizeMapperOnline;
    @Resource
    private PermissionService permissionService;
    @Resource
    private AccessLogService accessLogService;
    @Resource
    private RoleService roleService;
    @Resource
    private SearchHistoryService searchHistoryService;
    @Resource
    private MemberService memberService;
    @Resource
    private MemberOnlineService memberOnlineService;

//    @RequestMapping(value = "/index")
//    public ModelAndView index(HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        Object orgFlag = request.getSession().getAttribute("orgFlag");
//        if (orgFlag != null) {
//            int organizeId = Integer.parseInt(String.valueOf(orgFlag));
//
//            Map organizeById = organizeService.getOrganizeById(organizeId);
//            Organize organize = (Organize) organizeById.get("organize");
//            List<Map> organizeIp = (List) organizeById.get("organizeIp");
//
//            String ipStart = "";
//            String ipEnd = "";
//            for (Map m : organizeIp) {
//                ipStart = String.valueOf(m.get("ipStart"));
//
//                ipEnd = String.valueOf(m.get("ipEnd"));
//            }
//
//
//            modelAndView.setViewName("client/organize/organize");
//            modelAndView.addObject("organize", organize);
//            modelAndView.addObject("ipStart", IPUtil.ip2Long(ipStart));
//            modelAndView.addObject("ipEnd", IPUtil.ip2Long(ipEnd));
//        } else {
//            modelAndView.setViewName("client/error/404");
//        }
//        return modelAndView;
//    }
//
//    /**
//     * 使用情况折线图
//     *
//     * @param tab
//     * @param time
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/syqk", method = RequestMethod.GET)
//    public List syqk(String tab, String time, String ipStart, String ipEnd, String orgId) {
//        List resultList = new ArrayList();
//
//        Map organizeById = organizeService.getOrganizeById(Integer.parseInt(orgId));
//        List<Map> organizeIp = (List) organizeById.get("organizeIp");
//
//        List<Map> list = new ArrayList<>();
//        for (Map m : organizeIp) {
//            Map paramMap = new HashMap();
//            paramMap.put("type", tab);
//            paramMap.put("ipStart", IPUtil.ip2Long(String.valueOf(m.get("ipStart"))));
//            paramMap.put("ipEnd", IPUtil.ip2Long(String.valueOf(m.get("ipEnd"))));
//
//            if ("0".equals(time)) {
//                List tmpList = new ArrayList();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                List<Date> week = DateUtil.dateToWeek(new Date());
//                for (Date date : week) {
//                    Map tmp = new HashMap();
//                    tmp.put("begin", sdf.format(DateUtil.getDayStartTime(date)));
//                    tmp.put("end", sdf.format(DateUtil.getDayEndTime(date)));
//                    tmpList.add(tmp);
//                }
//                paramMap.put("time", tmpList);
//            } else if ("1".equals(time)) {
//                List tmpList = new ArrayList();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                List<Date> month = DateUtil.getAllTheDateOftheMonth(new Date());
//                for (Date date : month) {
//                    Map tmp = new HashMap();
//                    tmp.put("begin", sdf.format(DateUtil.getDayStartTime(date)));
//                    tmp.put("end", sdf.format(DateUtil.getDayEndTime(date)));
//                    tmpList.add(tmp);
//                }
//                paramMap.put("time", tmpList);
//            } else if ("2".equals(time)) {
//                List tmpList = new ArrayList();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                for (int i = 1; i <= 12; i++) {
//                    Map tmp = new HashMap();
//                    tmp.put("begin", sdf.format(DateUtil.getMonthStartTime(i)));
//                    tmp.put("end", sdf.format(DateUtil.getMonthEndTime(i)));
//                    tmpList.add(tmp);
//                }
//                paramMap.put("time", tmpList);
//            }
//
//            list.add(accessLogService.getAllAccess(paramMap));
//
//        }
//
//
//        if ("0".equals(tab)) {
//            List tmpBook = new ArrayList();
//            List tmpJournal = new ArrayList();
//            List tmpstandard = new ArrayList();
//            for (Map map : list) {
//                List<Integer> book = (List) map.get("book");
//                List<Integer> journal = (List) map.get("journal");
//                List<Integer> standard = (List) map.get("standard");
//
//                for (int i : book) {
//                    int b = 0;
//                    b += i;
//                    tmpBook.add(b);
//                }
//                for (int i : journal) {
//                    int j = 0;
//                    j += i;
//                    tmpJournal.add(j);
//                }
//                for (int i : standard) {
//                    int s = 0;
//                    s += i;
//                    tmpstandard.add(s);
//                }
//
//            }
//            Map tmpMap = new HashMap();
//            tmpMap.put("name", "电子书");
//            tmpMap.put("type", "line");
//            tmpMap.put("smooth", true);
//            tmpMap.put("data", tmpBook);
//
//            Map tmpMap1 = new HashMap();
//            tmpMap1.put("name", "期刊");
//            tmpMap1.put("type", "line");
//            tmpMap1.put("smooth", true);
//            tmpMap1.put("data", tmpJournal);
//
//            Map tmpMap2 = new HashMap();
//            tmpMap2.put("name", "标准");
//            tmpMap2.put("type", "line");
//            tmpMap2.put("smooth", true);
//            tmpMap2.put("data", tmpstandard);
//
//            resultList.add(tmpMap);
//            resultList.add(tmpMap1);
//            resultList.add(tmpMap2);
//
//        } else if ("1".equals(tab)) {
//            List tmpTotal = new ArrayList();
//            for (Map map : list) {
//                List<Integer> total = (List) map.get("total");
//                for (int i : total) {
//                    int t = 0;
//                    t += i;
//                    tmpTotal.add(t);
//                }
//            }
//
//            Map tmpMap = new HashMap();
//            tmpMap.put("name", "检索量");
//            tmpMap.put("type", "line");
//            tmpMap.put("smooth", true);
//            tmpMap.put("data", tmpTotal);
//
//            resultList.add(tmpMap);
//        } else if ("2".equals(tab)) {
//            List tmpJournal = new ArrayList();
//            List tmpstandard = new ArrayList();
//            for (Map map : list) {
//                List<Integer> journal = (List) map.get("journal");
//                List<Integer> standard = (List) map.get("standard");
//                for (int i : journal) {
//                    int j = 0;
//                    j += i;
//                    tmpJournal.add(j);
//                }
//                for (int i : standard) {
//                    int s = 0;
//                    s += i;
//                    tmpstandard.add(s);
//                }
//            }
//
//            Map tmpMap1 = new HashMap();
//            tmpMap1.put("name", "期刊");
//            tmpMap1.put("type", "line");
//            tmpMap1.put("smooth", true);
//            tmpMap1.put("data", tmpJournal);
//
//            Map tmpMap2 = new HashMap();
//            tmpMap2.put("name", "标准");
//            tmpMap2.put("type", "line");
//            tmpMap2.put("smooth", true);
//            tmpMap2.put("data", tmpstandard);
//
//            resultList.add(tmpMap1);
//            resultList.add(tmpMap2);
//        }
////
////
////        Map paramMap = new HashMap();
////        paramMap.put("type", tab);
////        paramMap.put("ipStart", ipStart);
////        paramMap.put("ipEnd", ipEnd);
//
//
//        return resultList;
//    }
//
//    /**
//     * 访问检索下载统计
//     */
//    @ResponseBody
//    @RequestMapping(value = "/syqkall", method = RequestMethod.GET)
//    public Map syqkAll(String ipStart, String ipEnd, String orgId) {
//        Map resultMap = new HashMap();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Map organizeById = organizeService.getOrganizeById(Integer.parseInt(orgId));
//        List<Map> organizeIp = (List) organizeById.get("organizeIp");
//
//        List<Map> tmpList = new ArrayList();
//        for (Map m : organizeIp) {
//            Map tmp = new HashMap();
//            tmp.put("begin", sdf.format(DateUtil.getDayBegin()));
//            tmp.put("end", sdf.format(DateUtil.getDayEnd()));
//            tmp.put("ipStart", IPUtil.ip2Long(String.valueOf(m.get("ipStart"))));
//            tmp.put("ipEnd", IPUtil.ip2Long(String.valueOf(m.get("ipEnd"))));
//
//            tmpList.add(accessLogService.getTodayAndHistoryData(tmp));
//        }
//
//        int todayAccess = 0;
//        int hisAccess = 0;
//        int todaySearch = 0;
//        int hisSearch = 0;
//        int todayDownload = 0;
//        int hisDownload = 0;
//        for (Map map : tmpList) {
//            todayAccess += (int) map.get("todayAccess");
//            hisAccess += (int) map.get("hisAccess");
//            todaySearch += (int) map.get("todaySearch");
//            hisSearch += (int) map.get("hisSearch");
//            todayDownload += (int) map.get("todayDownload");
//            hisDownload += (int) map.get("hisDownload");
//        }
//        resultMap.put("todayAccess", todayAccess);
//        resultMap.put("hisAccess", hisAccess);
//        resultMap.put("todaySearch", todaySearch);
//        resultMap.put("hisSearch", hisSearch);
//        resultMap.put("todayDownload", todayDownload);
//        resultMap.put("hisDownload", hisDownload);
//
//
////        Map tmp = new HashMap();
////        tmp.put("begin", sdf.format(DateUtil.getDayBegin()));
////        tmp.put("end", sdf.format(DateUtil.getDayEnd()));
////        tmp.put("ipStart", ipStart);
////        tmp.put("ipEnd", ipEnd);
////
////        resultMap = accessLogService.getTodayAndHistoryData(tmp);
//
//        return resultMap;
//    }
//
//    /**
//     * 访问检索下载统计
//     *
//     * @param type
//     * @param PageIndex
//     * @param PageSize
//     * @return
//     */
//    @RequestMapping(value = "/menu")
//    public ModelAndView menu(String type, String PageIndex, String PageSize, String ipStart, String ipEnd, String orgId) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        int pageIndex = Util.rmNull(PageIndex).equals("") ? 1 : Integer.parseInt(PageIndex);
//        int pageSize = Util.rmNull(PageSize).equals("") ? 5 : Integer.parseInt(PageSize);
//        int PageCount = 0;
//        Map itemsMap = new HashMap();
//
//
//        Map organizeById = organizeService.getOrganizeById(Integer.parseInt(orgId));
//        List<Map> organizeIp = (List) organizeById.get("organizeIp");
//
//        List<Map> tmpList = new ArrayList();
//        for (Map m : organizeIp) {
//            Map paramMap = new HashMap();
//            paramMap.put("type", type);
//            paramMap.put("currPage", (pageIndex - 1) * pageSize);
//            paramMap.put("pageSize", pageSize);
//            paramMap.put("ipStart", IPUtil.ip2Long(String.valueOf(m.get("ipStart"))));
//            paramMap.put("ipEnd", IPUtil.ip2Long(String.valueOf(m.get("ipEnd"))));
//
//            tmpList.add(accessLogService.getAccessByPage(paramMap));
//        }
//
//        List tmpItemList = new ArrayList();
//        int itemCount = 0;
//        for (Map map : tmpList) {
//            List item = (List) map.get("item");
//            if (item.size() > 0) {
//                for (int i = 0; i < item.size(); i++) {
//                    tmpItemList.add(item.get(i));
//                }
//            }
//            itemsMap.put("items", tmpItemList);
//            itemCount += (int) map.get("itemCount");
//        }
//
//
////        Map paramMap = new HashMap();
////        paramMap.put("type", type);
////        paramMap.put("currPage", (pageIndex - 1) * pageSize);
////        paramMap.put("pageSize", pageSize);
////        paramMap.put("ipStart", ipStart);
////        paramMap.put("ipEnd", ipEnd);
////
////        Map accessByPage = accessLogService.getAccessByPage(paramMap);
//
////        itemsMap.put("items",accessByPage.get("item"));
////        int itemCount = (int) accessByPage.get("itemCount");
//        PageCount = (itemCount % pageSize) == 0 ? itemCount / pageSize : itemCount / pageSize + 1;
//
//        modelAndView.setViewName("client/organize/menu");
//        modelAndView.addObject("items", itemsMap);
//        modelAndView.addObject("type", type);
//        modelAndView.addObject("PageIndex", pageIndex);
//        modelAndView.addObject("PageSize", pageSize);
//        modelAndView.addObject("PageCount", PageCount);
//        return modelAndView;
//    }
//
//    /**
//     * 热门搜索词标签
//     *
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/tagcloud")
//    public List tagCloud(String time, HttpServletRequest request, String ipStart, String ipEnd, String orgId) {
//        List resultList = new ArrayList();
//
//        Map organizeById = organizeService.getOrganizeById(Integer.parseInt(orgId));
//        List<Map> organizeIp = (List) organizeById.get("organizeIp");
//
//        List list = new ArrayList();
//        if ("0".equals(time)) {
//            for (Map map : organizeIp) {
//                Map paramMap = new HashMap();
//                paramMap.put("appPath", request.getContextPath());
//                paramMap.put("ipStart", IPUtil.ip2Long(String.valueOf(map.get("ipStart"))));
//                paramMap.put("ipEnd", IPUtil.ip2Long(String.valueOf(map.get("ipEnd"))));
//
//                List tmpList = new ArrayList();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Map tmp = new HashMap();
//                tmp.put("begin", sdf.format(DateUtil.getBeginDayOfWeek()));
//                tmp.put("end", sdf.format(DateUtil.getEndDayOfWeek()));
//                tmpList.add(tmp);
//                paramMap.put("time", tmpList);
//
//                list.add(searchHistoryService.getHotSearchWord(paramMap));
//            }
//
//        } else if ("1".equals(time)) {
//            for (Map map : organizeIp) {
//                Map paramMap = new HashMap();
//                paramMap.put("appPath", request.getContextPath());
//                paramMap.put("ipStart", IPUtil.ip2Long(String.valueOf(map.get("ipStart"))));
//                paramMap.put("ipEnd", IPUtil.ip2Long(String.valueOf(map.get("ipEnd"))));
//
//                List tmpList = new ArrayList();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Map tmp = new HashMap();
//                tmp.put("begin", sdf.format(DateUtil.getBeginDayOfMonth()));
//                tmp.put("end", sdf.format(DateUtil.getEndDayOfMonth()));
//                tmpList.add(tmp);
//                paramMap.put("time", tmpList);
//
//                list.add(searchHistoryService.getHotSearchWord(paramMap));
//
//            }
//        } else if ("2".equals(time)) {
//            for (Map map : organizeIp) {
//                Map paramMap = new HashMap();
//                paramMap.put("appPath", request.getContextPath());
//                paramMap.put("ipStart", IPUtil.ip2Long(String.valueOf(map.get("ipStart"))));
//                paramMap.put("ipEnd", IPUtil.ip2Long(String.valueOf(map.get("ipEnd"))));
//
//                List tmpList = new ArrayList();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Map tmp = new HashMap();
//                tmp.put("begin", sdf.format(DateUtil.getBeginDayOfYear()));
//                tmp.put("end", sdf.format(DateUtil.getEndDayOfYear()));
//                tmpList.add(tmp);
//                paramMap.put("time", tmpList);
//
//                list.add(searchHistoryService.getHotSearchWord(paramMap));
//            }
//
//        }
//        if (list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                List<Map> o = (List<Map>) list.get(i);
//                if (o.size() > 0) {
//                    for (int j = 0; j < o.size(); j++) {
//                        resultList.add(o.get(j));
//                    }
//                }
//            }
//        }
//
//        return resultList;
//    }
//
//    /**
//     * 热门资源
//     *
//     * @param flag
//     * @param tab
//     * @param type
//     * @param PageIndex
//     * @param PageSize
//     * @return
//     */
//    @RequestMapping(value = "/hotresource")
//    public ModelAndView hotresource(String flag, String tab, String type, String PageIndex, String PageSize, String ipStart, String ipEnd, String orgId) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        int pageIndex = Util.rmNull(PageIndex).equals("") ? 1 : Integer.parseInt(PageIndex);
//        int pageSize = Util.rmNull(PageSize).equals("") ? 6 : Integer.parseInt(PageSize);
//
//        Map organizeById = organizeService.getOrganizeById(Integer.parseInt(orgId));
//        List<Map> organizeIp = (List) organizeById.get("organizeIp");
//
//        if ("true".equals(flag)) {
//            int PageCount = 0;
//
//            List<Map> tmpList = new ArrayList<>();
//            for (Map m : organizeIp) {
//                Map paramMap = new HashMap();
//                paramMap.put("tab", tab);
//                paramMap.put("type", type);
//                paramMap.put("currPage", (pageIndex - 1) * pageSize);
//                paramMap.put("pageSize", pageSize);
//                paramMap.put("ipStart", IPUtil.ip2Long(String.valueOf(m.get("ipStart"))));
//                paramMap.put("ipEnd", IPUtil.ip2Long(String.valueOf(m.get("ipEnd"))));
//
//                tmpList.add(accessLogService.getHotResource(paramMap));
//            }
//            int itemCount = 0;
//            List resourceList = new ArrayList();
//            for (Map map : tmpList) {
//                itemCount += (int) map.get("total");
//                List list = (List) map.get("resourceList");
//                if (list.size() > 0) {
//                    for (int i = 0; i < list.size(); i++) {
//                        resourceList.add(list.get(i));
//                    }
//                }
//            }
//            PageCount = (itemCount % pageSize) == 0 ? itemCount / pageSize : itemCount / pageSize + 1;
//
//            modelAndView.setViewName("client/organize/morehotres");
//            modelAndView.addObject("items", resourceList);
//            modelAndView.addObject("hotPageIndex", pageIndex);
//            modelAndView.addObject("hotPageSize", pageSize);
//            modelAndView.addObject("hotPageCount", PageCount);
//        } else {
//            List<Map> tmpList = new ArrayList<>();
//            for (Map m : organizeIp) {
//                Map paramMap = new HashMap();
//                paramMap.put("tab", tab);
//                paramMap.put("type", type);
//                paramMap.put("currPage", (pageIndex - 1) * pageSize);
//                paramMap.put("pageSize", pageSize);
//                paramMap.put("ipStart", IPUtil.ip2Long(String.valueOf(m.get("ipStart"))));
//                paramMap.put("ipEnd", IPUtil.ip2Long(String.valueOf(m.get("ipEnd"))));
//
//                tmpList.add(accessLogService.getHotResource(paramMap));
//            }
//            List resourceList = new ArrayList();
//
//            for (Map map : tmpList) {
//                List list = (List) map.get("resourceList");
//                if (list.size() > 0) {
//                    for (int i = 0; i < list.size(); i++) {
//                        resourceList.add(list.get(i));
//                    }
//                }
//            }
//
//            modelAndView.addObject("items", resourceList);
//            modelAndView.setViewName("client/organize/hotresource");
//        }
//
//        modelAndView.addObject("hottab", tab);
//        modelAndView.addObject("hotopt", type);
//        return modelAndView;
//    }

    /**
     * 用户记录
     *
     * @return
     */
    @RequestMapping(value = "/userlog")
    public ModelAndView userlog(String PageIndex, String PageSize, String ipStart, String ipEnd, String orgId) {
        ModelAndView modelAndView = new ModelAndView();

        int pageIndex = Util.rmNull(PageIndex).equals("") ? 1 : Integer.parseInt(PageIndex);
        int pageSize = Util.rmNull(PageSize).equals("") ? 5 : Integer.parseInt(PageSize);
        int PageCount = 0;

        Map organizeById = organizeService.getOrganizeById(Integer.parseInt(orgId));
        List<Map> organizeIp = (List) organizeById.get("organizeIp");

        List<Map> list = new ArrayList<>();
        for (Map map : organizeIp) {

            Map paramMap = new HashMap();
            paramMap.put("currPage", (pageIndex - 1) * pageSize);
            paramMap.put("pageSize", pageSize);
            paramMap.put("ipStart", IPUtil.ip2Long(String.valueOf(map.get("ipStart"))));
            paramMap.put("ipEnd", IPUtil.ip2Long(String.valueOf(map.get("ipEnd"))));
            list.add(accessLogService.getUserLog(paramMap));
        }

        int itemCount = 0;
        List items = new ArrayList();
        for (Map map : list) {
            itemCount += (int) map.get("total");
            List userlog = (List) map.get("userlog");
            if (userlog.size() > 0) {
                for (int i = 0; i < userlog.size(); i++) {
                    items.add(userlog.get(i));
                }
            }
        }


        PageCount = (itemCount % pageSize) == 0 ? itemCount / pageSize : itemCount / pageSize + 1;


        modelAndView.setViewName("client/organize/userlog");
        modelAndView.addObject("items", items);
        modelAndView.addObject("userPageIndex", pageIndex);
        modelAndView.addObject("userPageSize", pageSize);
        modelAndView.addObject("userPageCount", PageCount);
        return modelAndView;
    }


    /**
     * 用户操作权限判断
     * @param session
     * @param name
     * @param dateTime
     * @param iiiPP
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/authorization")
    public Map getUserOrganzeRole(HttpSession session, @RequestParam(value = "name") String name, @RequestParam(value = "dateTime") String dateTime, @RequestParam(value = "iiiPP") String iiiPP, HttpServletRequest request) {
        Map userOrganzeRole = organizeOnlineService.getUserOrganzeRole(session, name, dateTime, iiiPP, request, ONLINESTATUS);
        return userOrganzeRole;
    }

    /**
     * 访问网站判断ip是否外网，是否登陆，显示欢迎登陆提示
     * @param session
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loginSign")
    public Map getUserOrganzeRole(HttpSession session, HttpServletRequest request) {
        // 登陆判断展示
        Map map = new HashMap();
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
                            //System.out.println("当前登陆IP=========" + IPUtil.getIpAddr(request));
                            if (IPUtil.getIp2long(startIp) <= IPUtil.getIp2long(IPUtil.getIpAddr(request)) && IPUtil.getIp2long(IPUtil.getIpAddr(request)) <= IPUtil.getIp2long(endIp)) {
                                Organize or = organizeOnlineService.selectOrganById(Integer.parseInt(id));
                                map.put("loginSign", 1);
                                map.put("org", or.getName());
                                organizeMap.put("loginSign", 1);
                            }

                        }
                    }
                    if (organizeMap.size() == 0) {
                        map.put("loginSign", 2);
                    }
                }
            } else {
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
                            //System.out.println("当前登陆IP=========" + IPUtil.getIpAddr(request));
                            if (IPUtil.getIp2long(startIp) <= IPUtil.getIp2long(IPUtil.getIpAddr(request)) && IPUtil.getIp2long(IPUtil.getIpAddr(request)) <= IPUtil.getIp2long(endIp)) {
                                Organize or = organizeOnlineService.selectOrganById(Integer.parseInt(id));
                                map.put("loginSign", 3);
                                map.put("org", or.getName());
                                organizeMap.put("loginSign", 3);
                            }

                        }
                    }
                    if (organizeMap.size() == 0) {
                        map.put("loginSign", 4);
                    }
                }
            }

        }
        map.put("ONLINESTATUS", "ON");
        return map;
    }


}

