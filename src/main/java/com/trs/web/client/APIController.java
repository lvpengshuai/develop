package com.trs.web.client;

import com.trs.client.TRSException;
import com.trs.core.util.Config;
import com.trs.core.util.RequestUtils;
import com.trs.core.util.SearchResult;
import com.trs.core.util.Util;
import com.trs.model.Book;
import com.trs.model.HotWord;
import com.trs.service.BookService;
import com.trs.service.HotWordService;
import com.trs.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SH on 2018/4/23.
 */

@Controller
@RequestMapping(value="/api")
public class APIController {

    @Autowired
    private HotWordService hotWordService;

    @Resource
    private SearchService searchService;
    @Resource
    private BookService bookService;

    private String searchTable = Config.getKey("trs.server.search");


    //获取热词接口
    @RequestMapping(value = "/hotwords")
    @ResponseBody
    public Map resultHotWords(){
        Map map = new HashMap<>();
        List<HotWord> hotWords = hotWordService.getIndexHotWords();
        List<String> hotWordsList = new ArrayList<String>();
        if (hotWords.size() != 0) {
            String[] split1 = hotWords.get(0).getWord().split("/");
            for (String str : split1) {
                    hotWordsList.add(str);
            }
        }
        map.put("status","-1");
        if(hotWordsList.isEmpty()){
            map.put("msg","无热门词汇");
        }else{
            map.put("msg","获取热词成功");
            map.put("data",hotWordsList);
            map.put("status","0");
        }
        return map;
    }

    //提供集成检索
    @RequestMapping(value = "/get_integrate_search")
    public ModelAndView getIntegrateSearch(HttpServletRequest request, ModelMap modelMap)throws Exception{
            RequestUtils requestUtils = new RequestUtils(request);
            String keyWord = requestUtils.getParameterAsString("keyword","");
            String searchSql = requestUtils.getParameterAsString2("ssq","");
            searchSql = RequestUtils.decodeBase64(searchSql).replace("+", "####");
            searchSql= URLDecoder.decode(searchSql,"utf-8").replace("####","+");

            String typeSearch = requestUtils.getParameterAsString("tp","wz");
            String cttype = requestUtils.getParameterAsString("cttype","人物");
            String clickRe = requestUtils.getParameterAsString("clickRe","1");
            //包含全部检索词
            int pageIndex = requestUtils.getParameterAsInt("pi", 1);
            int pageSize = requestUtils.getParameterAsInt("ps", 10);
            //发表时间排序
            String pubdate = requestUtils.getParameterAsString("pubd","");
            //人物
            String author = requestUtils.getParameterAsString("ath","");
            //作者
            String source = requestUtils.getParameterAsString("sre","");
            //机构
            String organ = requestUtils.getParameterAsString("org","");
            //年鉴名称
            String bookname = requestUtils.getParameterAsString("bn","");
            //标题
            String title = requestUtils.getParameterAsString("title","");
            //时间
            String startTime = requestUtils.getParameterAsString("startTime","");
            String endTime = requestUtils.getParameterAsString("endTime","");
            //范围
            String range = requestUtils.getParameterAsString("range","");
            //排序
            String sort;
            //相关性
            String relevant = requestUtils.getParameterAsString("re","");

            //新加参数可以搜索空
            String nu = requestUtils.getParameterAsString("nu","StringNull");
            nu = keyWord.equals("")?"sy":nu;

            String swd = requestUtils.getParameterAsString("swd","");
            swd=RequestUtils.trimInnerSpaceStr(RequestUtils.getReal(swd));
            String entry = requestUtils.getParameterAsString("entry","人物");
            if(entry.equals("")){
                entry="人物";
            }
            String type="";
            if("wz".equals(typeSearch)){
                type="wz";
            }else if("nj".equals(typeSearch) || "1".equals(typeSearch)){
                type="nj";
                typeSearch ="nj";
            }else {
                type="ct";
            }
            String cttp = "人物";
            if("lw".equals(typeSearch)){
                cttp="论文";
                type="ct";
                sort ="RELEVANCE";
            }else if("kt".equals(typeSearch)){
                cttp="课题";
                type="ct";
                sort ="RELEVANCE";
            }else if("hy".equals(typeSearch)){
                cttp="会议";
                type="ct";
                sort ="RELEVANCE";
            }else if("ts".equals(typeSearch)){
                cttp="图书";
                type="ct";
                sort ="RELEVANCE";
            }else if("dsj".equals(typeSearch)){
                cttp="大事记";
                type="ct";
                sort ="RELEVANCE";
            }

            if(cttp.equals("人物")){
                pageSize = 16;
            }

            //发表时间排序
            String hot = requestUtils.getParameterAsString("hot","");
            StringBuffer sortBu = new StringBuffer();

            if(relevant.equals("-1")){
                sortBu.append("RELEVANCE");
                sortBu.append(",-bookyear");
            }else if(relevant.equals("1")){
                sortBu.append("RELEVANCE");
            }
            if(hot.equals("-1")){
                sortBu.append(",-readCount");
                sortBu.append(",RELEVANCE");
            }else if(hot.equals("1")){
                sortBu.append("RELEVANCE");
                sortBu.append(",readCount");
            }
            if(pubdate.equals("-1")){
                sortBu.append(",-bookyear");
                sortBu.append(",RELEVANCE");
            }else if(pubdate.equals("1")){
                sortBu.append("RELEVANCE");
                sortBu.append(",bookyear");
            }else if(!"StringNull".equals(nu) || keyWord.equals("")){
                sortBu.append(",-bookyear");
                sortBu.append(",RELEVANCE");
            }
            if(sortBu.length()==0){
                sortBu.append("RELEVANCE");
            }

            sort=sortBu.toString();
            //二次检索licenceCode
            String licenceCode = requestUtils.getParameterAsString("licenceCode","");
            boolean second = false;
        /*-------------------高级检索参数接收-----------------*/
            //是否为高级检索
            String ifadv = requestUtils.getParameterAsString("ifadv","");
            StringBuffer sqlbuff = new StringBuffer();
            StringBuffer wdbuff = new StringBuffer();
            if(searchSql.equals("")&&pageIndex==1){
                if(ifadv.equals("true")){
                    sqlbuff.append("type='"+type+"'");
                    //检索词
                    String[] wds = requestUtils.getParameterValues("advwd");
                    //检索位置
                    String[] advposition = requestUtils.getParameterValues("advposition");
                    //精确或模糊
                    String[] advaccurate = requestUtils.getParameterValues("advaccurate");
                    //关系
                    String[] adcimpact = requestUtils.getParameterValues("adcimpact");

                /*年鉴高级检索参数*/
                    //主编
                    String[] advath = requestUtils.getParameterValues("advath");
                    //主编检索性质（模糊/精确）
                    String[] athacc = requestUtils.getParameterValues("athacc");
                    //年鉴名称
                    String[] njname = requestUtils.getParameterValues("njname");

                    String[] titleacc = requestUtils.getParameterValues("titleacc");
                    String[] st = requestUtils.getParameterValues("st");
                    String[] nt = requestUtils.getParameterValues("nt");

                    int num=0;
                    if(type.equals("nj")){
                        num=0;
                    }else if(type.equals("wz")){
                        num=1;
                    }

                    if(type.equals("wz")){
                        sqlbuff .append(" and ");
                        for (int i = 0; wds!=null&&i<wds.length;i++){

                            if(!wds[i].equals("")){
                                sqlbuff.append(advposition[i]);
                                wdbuff.append(wds[i]);
                                if(advaccurate[i].equals("vague")){
                                    sqlbuff.append("= %"+wds[i]+"%");
                                }else {
                                    sqlbuff.append("='"+wds[i]+"'");
                                }
                                if(i!=wds.length-1 && !wds[i+1].equals("")){
                                    sqlbuff.append(" "+adcimpact[i]+" ");
                                }
                            }
                        }
                    }

                    if(!advath[num].equals("")){
                        wdbuff.append(Util.setAgnattacks(advath[num]));
                        if(!sqlbuff.toString().equals("")){
                            // sqlbuff.append(" and ");
                        }
                        if(athacc[num].equals("vague")){
                            sqlbuff.append("people ='%"+Util.setAgnattacks(advath[num])+"%'");
                        }else {
                            sqlbuff.append("people ='"+Util.setAgnattacks(advath[num])+"'");
                        }
                    }
                    if(!njname[num].equals("")){
                        wdbuff.append(Util.setAgnattacks(njname[num]));
                        if(!sqlbuff.toString().equals("")){
                            sqlbuff.append(" and ");
                        }
                        if(titleacc[num].equals("vague")){
                            sqlbuff.append(" (title ='%"+Util.setAgnattacks(njname[num])+"%' or isbn ='%"+Util.setAgnattacks(njname[num])+"%')");
                        }else {
                            sqlbuff.append(" (title ='"+Util.setAgnattacks(njname[num])+"' or isbn ='"+Util.setAgnattacks(njname[num])+"')");
                        }
                    }

                    if(!sqlbuff.toString().equals("")){
                        if(!st[num].equals("")){
                            sqlbuff.append(" and bookyear>='"+Util.setAgnattacks(st[num])+"'");
                        }
                        if(!nt[num].equals("")){
                            sqlbuff.append(" and bookyear<='"+Util.setAgnattacks(nt[num])+"'");
                        }
                    }

                    System.out.println(sqlbuff.toString());
                    keyWord=wdbuff.toString();
                }

        /*------------------高级检索拼装sql完成--------------------*/
                if(ifadv!=""){
                    searchSql=sqlbuff.toString();
                }else {
                    if("ct".equals(type)){
                        searchSql = searchService.getSql(keyWord, type, "", "", startTime, endTime, bookname, "", cttp, "");
                    }else {
                        entry="";
                        searchSql = searchService.getSql(keyWord, type, organ, pubdate, startTime, endTime, bookname, author, "",source);
                    }
                }
            }
            if(!"StringNull".equals(nu) && keyWord.equals("")){
                if("wz".equals(type)){
                    searchSql ="type='"+type+"'";
                }else if("nj".equals(type)){
                    searchSql ="type='"+type+"'";
                }else{
                    searchSql = "entry='%"+cttp+"%' and type='"+type+"'";
                }
            }
         /*筛选文章检索开始*/
            String wzType = requestUtils.getParameterAsString("wzType","");
            if (wzType.equals("title")){
                if (keyWord != null){
                    //keyword=like('马克思') and type='wz'
                    searchSql = "title='%"+keyWord+"%' and type='"+type+"'";
                }
            }
            if (wzType.equals("author")){
                if (keyWord != null){
                    searchSql = "source=like('"+keyWord+"') and type='"+type+"'";
                }
            }
            if (wzType.equals("Keyword")){
                if (keyWord != null){
                    searchSql = "Keyword=like('"+keyWord+"') and type='"+type+"'";
                }
            }

            System.out.println(searchSql);







            try {
            /*检索开始*/
                SearchResult resultData = searchService.getResultData(searchTable, searchSql, sort, pageIndex, pageSize,second,licenceCode,type);
                int TRSCount=0;
                TRSCount = resultData.getTRSCount();
            /*-----------end-----------*/
                int PageCount = (TRSCount % pageSize) == 0 ? TRSCount / pageSize : TRSCount / pageSize + 1;
                String serverSearch = Config.getKey("trs.server.search");
                Map<String, Object> bookClass = new HashMap<>();
                bookClass = searchService.getBookClass(serverSearch, searchSql, nu);

                modelMap.put("organmaps",bookClass.get("organmaps"));
                modelMap.put("bnamemaps",bookClass.get("bnamemaps"));
                modelMap.put("yearmaps",bookClass.get("yearmaps"));
                modelMap.put("personmaps",bookClass.get("personmaps"));
                modelMap.put("authorss",bookClass.get("authorss"));
                //相关资源
                modelMap.put("hotResearchData",bookClass.get("wordShow"));
                modelMap.put("relatedPeopleData",bookClass.get("peoShow"));
                modelMap.put("relatedInstitutionData",bookClass.get("organShow"));

                modelMap.put("type",typeSearch);
                modelMap.put("PageCount",PageCount);
                modelMap.put("PageIndex", pageIndex);
                modelMap.put("PageSize", pageSize);
                List asd=resultData.getTrsList();
                List<Map> list = resultData.getTrsList();
                for (Map b : list){
                    String otitle = (String) b.get("title");
                    String ntitle = Util.rmNumIndex(otitle);
                    b.put("title",ntitle.replace(keyWord, "<font color='#ca0000'>" + keyWord + "</font>"));
                }
                modelMap.put("searchresult",list);
                modelMap.put("trscount",TRSCount);
                modelMap.put("licenceCode",resultData.getLicenceCode());
                modelMap.put("authors",author);
                modelMap.put("range", range);
                modelMap.put("startTime", startTime);
                modelMap.put("endTime", endTime);
                modelMap.put("relevant", relevant);
                modelMap.put("pubdate", pubdate);
                modelMap.put("title", title);
                modelMap.put("keyWord", keyWord);
                modelMap.put("re", relevant);
                modelMap.put("hot", hot);
                modelMap.put("bookname", bookname);
                modelMap.put("author", author);
                System.out.println(source);
                modelMap.put("pubd", pubdate);
                modelMap.put("organ",organ);
                modelMap.put("source",source);
                modelMap.put("ifadv",ifadv);
                modelMap.put("searchSql",searchSql);
                modelMap.put("cttype",cttype);
                modelMap.put("cttp",cttp);
                modelMap.put("firstSearch","1");
                modelMap.put("entry",cttp);
                modelMap.put("ctType",type);
                modelMap.put("clickRe",clickRe);

            } catch (TRSException e) {
                e.printStackTrace();

                System.out.println(e);
            }
            //热门年鉴
            if (keyWord == null || keyWord == "") {
                List<Book> booksByHot = bookService.getBooksByHot();
                modelMap.put("booksByHot", booksByHot);
            }

        return new ModelAndView("/api/index",modelMap);
    }
}
