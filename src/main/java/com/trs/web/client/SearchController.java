package com.trs.web.client;

import com.alibaba.fastjson.JSON;
import com.trs.client.TRSException;
import com.trs.core.annotations.SearchHistory;
import com.trs.core.util.*;
import com.trs.model.Book;
import com.trs.service.BookService;
import com.trs.service.SearchService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2017/3/20.
 */
@Controller
@RequestMapping
public class SearchController {

    @Resource
    private SearchService searchService;
    @Resource
    private BookService bookService;

    private String searchTable = Config.getKey("trs.server.search");

    /**
     * 搜索结果页，高级搜索
     * @param request
     * @param modelMap
     * @return
     * @throws Exception
     */
    @SearchHistory(url = "/search")
    @RequestMapping(value = "/search")
    public ModelAndView searchResult(HttpServletRequest request, ModelMap modelMap) throws Exception {
        RequestUtils requestUtils = new RequestUtils(request);
        String keyWord = requestUtils.getParameterAsString("kw","");
        String searchSql = requestUtils.getParameterAsString2("ssq","");
        searchSql = RequestUtils.decodeBase64(searchSql).replace("+", "####");
        searchSql=URLDecoder.decode(searchSql,"utf-8").replace("####","+");

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
        return new ModelAndView("client/search/searchresult",modelMap);
    }

    /**
     * Excel文件导出
     * @param type
     * @param bookCode
     * @param zid
     * @param map
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws IOException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping("/resultDataExcel/{type}/{bookCode}/{zid}")
    public void resultDataExcel(@PathVariable String type, @PathVariable String[] bookCode, @PathVariable String[] zid , Map<String,Object> map,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        Map<String, Object> stringObjectMap = searchService.resultDataExcel(type, bookCode, zid);
        map.putAll(stringObjectMap);

        //设置excel响应头
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + new String(map.get("fileName").toString().getBytes(), "iso8859-1"));

        //创建文件输出
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        Workbook workbook = ExcelUtils.downloadExcel(map);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 词条检索
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/searchentry")
    public ModelAndView searchEntry(HttpServletRequest request, ModelMap modelMap){
        RequestUtils requestUtils = new RequestUtils(request);
        String swd = requestUtils.getParameterAsString("swd","");
        swd=RequestUtils.trimInnerSpaceStr(RequestUtils.getReal(swd));
        String typeSearch = requestUtils.getParameterAsString("tp","ct");
        String entry = requestUtils.getParameterAsString("entry","人物");
        if(entry.equals("")){
            entry="人物";
        }
        String type="ct";
        if("ct".equals(typeSearch)){
            type=typeSearch;
        }
        //包含全部检索词
        int pageIndex = requestUtils.getParameterAsInt("pi", 1);
        int pageSize = requestUtils.getParameterAsInt("ps", 15);
        //发表时间排序
        String pubdate = requestUtils.getParameterAsString("pubd","");
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
        String sort ="RELEVANCE";
        //相关性
        StringBuffer sortBu = new StringBuffer();
        StringBuffer sqlbuff = new StringBuffer();

        System.out.println(sqlbuff.toString());
        sort=sortBu.toString();
        //二次检索licenceCode
        String licenceCode = requestUtils.getParameterAsString("licenceCode","");
        boolean second = false;
        String searchSql="";
        searchSql = searchService.getSql(swd, type, "","", startTime, endTime, bookname, "","",entry);

        try {
            /*检索开始*/
            SearchResult resultData = searchService.getResultData(searchTable, searchSql, sort, pageIndex, pageSize,second,licenceCode,type);
            int TRSCount=0;
            TRSCount = resultData.getTRSCount();
            /*-----------end-----------*/
            int PageCount = (TRSCount % pageSize) == 0 ? TRSCount / pageSize : TRSCount / pageSize + 1;
            modelMap.put("type",typeSearch);
            modelMap.put("entry",entry);
            modelMap.put("PageCount",PageCount);
            modelMap.put("PageIndex", pageIndex);
            modelMap.put("PageSize", pageSize);
            modelMap.put("searchresult",resultData.getTrsList());
            modelMap.put("trscount",TRSCount);
            modelMap.put("licenceCode",resultData.getLicenceCode());
            modelMap.put("range", range);
            modelMap.put("startTime", startTime);
            modelMap.put("endTime", endTime);
            modelMap.put("pubdate", pubdate);
            modelMap.put("title", title);
            modelMap.put("bookname", bookname);
            modelMap.put("pubd", pubdate);
        } catch (TRSException e) {
            e.printStackTrace();
        }
        return new ModelAndView("client/search/searchshow",modelMap);
    }

    /**
     * 获取trs数据库关键词
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/words")
    public List words(HttpServletRequest request) {
        String keyWord = request.getParameter("keyWord");
        if(keyWord==null){
            return null;
        }
        ArrayList result = new ArrayList();
        try {
            String[] pyList;
            if(!keyWord.equals("")){
                pyList = CKMUtil.getPyList(keyWord, Config.getKey("trs.ckm.promtp"),6);

                for (int i=0;i<pyList.length;i++) {
                    result.add(pyList[i]);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 高级检索页面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/advanced", method = RequestMethod.GET)
    public ModelAndView advanced(HttpServletRequest request, ModelMap modelMap){
        return new ModelAndView("client/search/advanced",modelMap);
    }

    /**
     * 相关资源检索
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    public Map resource(HttpServletRequest request) throws TRSException {
        RequestUtils requestUtils = new RequestUtils(request);
        String keyword = requestUtils.getParameterAsString("keyword", "");
        keyword = "经济,贡献";
        SearchResult relatedtData = searchService.getRelatedtData("cssp_bookdetail", keyword);

        return null;
    }

    /**
     * 查询年份数据，加快检索速度
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/yearcount")
    public ModelAndView yearCount(HttpServletRequest request, ModelMap modelMap) throws TRSException {
        RequestUtils requestUtils = new RequestUtils(request);
        String yearCountSql = requestUtils.getParameterAsString2("yearCountSql", "");
        yearCountSql=RequestUtils.getReal(yearCountSql);
        List yearCount = searchService.getYearCount(Config.getKey("trs.server.allsearch"), yearCountSql);
        modelMap.put("yearCount",yearCount);

        int a = 1;
        int i = a + "".length();

        return new ModelAndView("client/search/yearcount",modelMap);
    }


    /**
     * 获取trs数据库数据
     * @param request
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/analyse")
    public ModelAndView analyse(HttpServletRequest request, ModelMap modelMap) throws TRSException {
        RequestUtils requestUtils = new RequestUtils(request);
        int pageIndex = requestUtils.getParameterAsInt("pageIndex", 1);
        int pageSize = requestUtils.getParameterAsInt("pageSize", 3);
        String bookId = requestUtils.getParameterAsString2("bookId", "");
        String keyword = requestUtils.getParameterAsString2("keyWord","");
        keyword=keyword.replace("'","\\'");
        String trsSql = "(name/79,keyword/20,content/1)+=like('"+keyword+"') and book_id ="+bookId+"";
        SearchResult searchResult = searchService.bookAnalyse(Config.getKey("trs.server.analyse"), trsSql, "", pageIndex, pageSize);
        List trsList = searchResult.getTrsList();
        ArrayList resultList = new ArrayList();

        for(int i = 0;i<trsList.size();i++){
            HashMap<String, Object> valMap = new HashMap<>();
            Map map = (Map) trsList.get(i);
            String name = (String) map.get("title");
            name.replaceAll("　"," ");
            String[] allAnalyse = name.split(";");
            String falName = allAnalyse[allAnalyse.length-1];
            System.out.println();
            String firstAnalyse = "";
            String secondAnalyse = "";
            if(allAnalyse.length>1){
                try {
                    if(allAnalyse[0]!=null){
                        String[] splitFirst = allAnalyse[0].split(" ");
                        if(splitFirst[0]!=null){
                            firstAnalyse=splitFirst[0];
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if(allAnalyse[1]!=null && allAnalyse.length-1 !=1){
                        String[] secondFirst = allAnalyse[1].split(" ");
                        if(secondFirst[0]!=null){
                            secondAnalyse=secondFirst[0];
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String page="";
            String  pages = (String) map.get("page");
            if(pages!=null && !pages.equals("")){
                page = pages.split("-")[0];
            }
            System.out.println(firstAnalyse+"   >>  "+secondAnalyse+"   >>  "+falName);
            valMap.put("firstAnalyse",firstAnalyse);
            valMap.put("secondAnalyse",secondAnalyse);
            valMap.put("falName",falName);
            valMap.put("content",map.get("content"));
            valMap.put("page",page);
            resultList.add(valMap);
        }
        modelMap.put("resultList",resultList);
        int TRSCount = searchResult.getTRSCount();
        int PageCount = (TRSCount % pageSize) == 0 ? TRSCount / pageSize : TRSCount / pageSize + 1;
        modelMap.put("PageCount",PageCount);
        modelMap.put("PageIndex", pageIndex);
        modelMap.put("PageSize", pageSize);
        modelMap.put("bookId",bookId);
        return new ModelAndView("client/search/analyse",modelMap);
    }


    /**
     * 根据类型获取数据
     * @param request
     * @param modelMap
     * @param type
     * @param kw
     * @param trscount
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/bookclass/{type}/{kw}/{trscount}")
    public ModelAndView bookClass(HttpServletRequest request,
                                  ModelMap modelMap,
                                  @PathVariable(value = "type")String type,
                                  @PathVariable(value = "kw")String kw,
                                  @PathVariable(value = "trscount")String trscount) throws TRSException {
//       RequestUtils requestUtils = new RequestUtils(request);
//        String kw = requestUtils.getParameterAsString("kw", "");
//        String tp = requestUtils.getParameterAsString("tp", "1");
        Map bookClass = searchService.getBookClass(searchTable, kw, "StringNull");
        modelMap.put("organmaps",bookClass.get("organmaps"));
        //System.out.println(bookClass.get("organmaps"));
        modelMap.put("personmaps",bookClass.get("personmaps"));
        //System.out.println(bookClass.get("personmaps"));
        //年鉴分析
        String bnamejson = JSON.toJSONString(bookClass.get("bnamemaps"),true);
        //时间分布
        String yearjson = JSON.toJSONString(bookClass.get("yearmaps"),true);

        request.setAttribute("bnamemaps",bnamejson);
        modelMap.put("yearmaps",yearjson);
        modelMap.put("trscount",Util.setAgnattacks(trscount));
        System.out.println("-----------------");
        return new ModelAndView("client/view/searchanalysis",modelMap);
    }

    /**
     * 跳转高级搜索
     * @param request
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/booktest")
    public ModelAndView bookClass(HttpServletRequest request) {
        return new ModelAndView("client/include/advancesearch");
    }
}
