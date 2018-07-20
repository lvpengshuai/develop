package com.trs.service.impl;


import com.alibaba.fastjson.JSON;
import com.trs.client.TRSConnection;
import com.trs.client.TRSConstant;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;
import com.trs.core.db.TRSConnect;
import com.trs.core.util.*;
import com.trs.mapper.SearchHistoryMapper;
import com.trs.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static com.trs.core.util.GroupSort.getCountSort;

/**
 * Created by xubo on 2017/3/21.
 */
@Service
public class SearchServiceImpl implements  SearchService {

    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    public SearchResult getResultData(String tableName, String searchword, String orderBy, int pageIndex, int pageSize, boolean reconnect, String licenceCode, String type) {
        SearchResult searchResult = new SearchResult();
        TRSUtil conn = new TRSUtil();
        TRSConnection connect = null;
        TRSResultSet rs = null;
        List trsList = new ArrayList();
        // TRS数据库读出条数
        int TRSCount = 0;
        try {
            connect = conn.getTrsconnect();
            if (reconnect == true) {
                connect = conn.getReTrsConnect(connect, licenceCode);
            }
            String licenceCodes = connect.getLicenceCode();
            searchResult.setLicenceCode(licenceCodes);
//            connect.setAutoExtend("CHEMICAL_THESAURUS_UTF8", "", "", TRSConstant.TCM_KAXALLBT | TRSConstant.TCM_KAXALLNT | TRSConstant.TCM_KAXST | TRSConstant.TCM_KAXPT | TRSConstant.TCM_KAXRT);
//            connect.SetExtendOption("RELEVANTMODE", "32768");
            long startTime = System.currentTimeMillis();

            rs = connect.executeSelect(tableName, searchword, orderBy, "", "", 0, TRSConstant.TCE_OFFSET, reconnect);
            long endTime = System.currentTimeMillis();
            System.out.println("调用API花费时间" + (endTime - startTime) +"ms");
            //System.out.println(rs.getTotalHitPoint());

            TRSCount = new Long(rs.getRecordCount()).intValue();
            int trsSelectInsex = pageIndex - 1;

            for (int i = trsSelectInsex * pageSize; i < trsSelectInsex * pageSize + pageSize && i < rs.getRecordCount(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                rs.moveTo(0, i);
                map.put("id", rs.getString("id"));
                map.put("title", rs.getString("title", "#ca0000"));
                map.put("content", rs.getStringWithCutsize("TextContent", 300, "#ca0000"));
                String soucetitle=rs.getString("zid_title");
                String neirong=rs.getString("abs", "#ca0000");
                String neirongjiequ=subTextString(neirong,neirong.length());
                map.put("abs", getNjAbs(rs.getString("abs", "#ca0000"),261));
                map.put("hc", rs.getString("HtmlContent"));
                // 前台关键词处理
                String  kewor=rs.getString("keyword");
                List keywordList=new ArrayList();
                String[] kw = kewor.split(";");
                for (int k = 0; k < kw.length; k++) {
                    keywordList.add(kw[k]);
                }
                map.put("keyword", keywordList);
                map.put("zid", rs.getString("zid"));
                map.put("source", rs.getString("source"));
                map.put("people", rs.getString("people"));
                map.put("author", rs.getString("author"));
                map.put("exdate", rs.getString("exdate"));
                map.put("bookcode", rs.getString("bookcode"));
                map.put("cover", rs.getString("cover"));
                map.put("exarea", rs.getString("exarea"));
                map.put("publishername", rs.getString("publishername"));
                String exdata = rs.getString("exdata");
                if(exdata!=null && !exdata.equals("") ){
                    Map<String, Object> exmap = JSON.parseObject(exdata);
                    for(Map.Entry<String, Object> entry : exmap.entrySet()){
                        if(Util.toStr(entry.getValue()).equals("")){
                            exmap.put(entry.getKey(),"（空）");
                        }
                    }

                    map.put("exmap",exmap);
                }
                if(!type.equals("ct")) {
                    if (soucetitle != null && !soucetitle.equals("")) {
                        String[] split = soucetitle.split("<.,>");
                        List search = new ArrayList<>();
                        for (String s : split) {
                            Map<String, Object> mapSource = new HashMap();
                            String[] splits = s.split("<,.>");
                            String id = splits[0];
                            String titleSource = splits[1];
                            mapSource.put("id", id);
                            mapSource.put("titleSource", titleSource);
                            search.add(mapSource);
                        }
                        map.put("listZT", search);
                    }
                }

                trsList.add(map);
            }
        } catch (TRSException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (connect != null) {
                connect.clean();
            }
        }
        searchResult.setTrsList(trsList);
        searchResult.setTRSCount(TRSCount);

        return searchResult;
    }

    @Override
    public Map<String, Object> getBookClass(String tableName, String searchword, String isIndex) {
        long st = System.currentTimeMillis();

        List<Map<String, Object>> selectList = new ArrayList<>();
        if(isIndex.equals("StringNull")){
            selectList = TRSConnect.select(tableName, searchword);
        }

        System.out.println("get the DB data: " + (System.currentTimeMillis() - st));
        Map<String, Object> allMap = new HashMap<String, Object>();
        long str7 = System.currentTimeMillis();
        try {
            long str1 = System.currentTimeMillis();


            String [] array= null;
            ArrayList<String> organList = new ArrayList<String>();
            ArrayList bnameList = new ArrayList();
            ArrayList yearList = new ArrayList();
             ArrayList personList = new ArrayList(10);
             ArrayList authorList = new ArrayList(10);
            ArrayList wordList = new ArrayList();
            long stfor = System.currentTimeMillis();



            for (int i = 1;  i < selectList.size(); i++) {
                String author = (String) selectList.get(i).get("source");

                if (author != null && !author.equals("")){
                    String[] auList = author.split(";");
                    for (String a : auList){
                        if (!a.equals("")) {
                            authorList.add(a);
                        }
                    }
                }

                String people = (String) selectList.get(i).get("people");
                if(people!=null&&!people.equals("")){
                    String[] psList = people.split(";");
                    for(String p : psList){
                        personList.add(p);
                    }
                }
                String organ = (String) selectList.get(i).get("organ");
                if(!organ.equals("")){
                    String[] onList = organ.split(";");
                    for(String o : onList){
                        organList.add(o);
                    }

                }
                String year = (String) selectList.get(i).get("bookyear");
                if(year!=null&&!year.equals("")){
                    yearList.add(year);
                }
                String bookname = (String) selectList.get(i).get("bookname");
                if(bookname!=null && !bookname.equals("")){
                    bnameList.add(bookname);
                }
                String keyword = (String) selectList.get(i).get("keyword");
                if(!keyword.equals("")){
                    String[] words = keyword.split(";");
                    for(String o : words){
                        wordList.add(o);
                    }

                }
            }
            long stresult = System.currentTimeMillis();
            StringBuffer organShow = new StringBuffer();
            StringBuffer peoShow = new StringBuffer();
            StringBuffer wordShow = new StringBuffer();
            StringBuffer authShow = new StringBuffer();

            long str2 = System.currentTimeMillis();
            Map organmaps = getCountSort(organList);
            long str3 = System.currentTimeMillis();
            Map bnamemaps = getCountSort(bnameList);
            long str4 = System.currentTimeMillis();
            Map personmaps= getCountSort(personList);

            Map authormaps = getCountSort(authorList);

            long str5 = System.currentTimeMillis();
            Map yearmaps = getCountSort(yearList);
            long str6 = System.currentTimeMillis();
            Map wordmaps = getCountSort(wordList);

            for(Object key : organmaps.keySet()){
                organShow.append(key+";");
            }
            for(Object key : personmaps.keySet()){
                peoShow.append(key+";");
            }
            for (Object key : authormaps.keySet()){
                authShow.append(key + ";");
            }

            for(Object key : wordmaps.keySet()){
                wordShow.append(key+";");
            }
            long entresult = System.currentTimeMillis();

            allMap.put("authorss", authormaps);
                allMap.put("organmaps", organmaps);
                allMap.put("bnamemaps", bnamemaps);
                allMap.put("yearmaps", yearmaps);
                allMap.put("personmaps", personmaps);
            if(organShow.length()>0){
                organShow.deleteCharAt(organShow.length()-1);
            }
            allMap.put("organShow",organShow);
            if(peoShow.length()>0){
                peoShow.deleteCharAt(peoShow.length()-1);
            }
            allMap.put("peoShow",peoShow);
            allMap.put("wordShow",wordShow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long str8 = System.currentTimeMillis();
        return allMap;
    }

    public Map eliminateNo(String[] strings, Map maps) {
        Map map = new HashMap();
        for (int i = 0; i < strings.length; i++) {
            Object o = maps.get(strings[i]);
            if (o != null) {
                map.put(strings[i], o);
            }
        }
        return map;
    }
    @Override
    public List getSearchWord(List trsList, String word) {
        ArrayList wordList = new ArrayList(20);
        /*使用正则去除不需要字符*/
        String regEx = "[ ]";
        Pattern pattern = Pattern.compile(regEx);
        for (int i = 0; i < trsList.size(); i++) {
            HashMap map = (HashMap) trsList.get(i);
            String keywords = (String) map.get("keyword");
            String[] aut = keywords.split(";");
            for (int j = 0; j < aut.length; j++) {
                String w = pattern.matcher(aut[j]).replaceAll("");
                if (word.equals(w)) {
                    continue;
                }
                wordList.add(w);
            }
        }
        HashSet h = new HashSet(wordList);
        wordList.clear();
        wordList.addAll(h);
        return wordList;
    }

    @Override
    public List getAuthor(List trsList) {
        ArrayList authorList = new ArrayList(20);
        for (int i = 0; i < trsList.size(); i++) {
            HashMap map = (HashMap) trsList.get(i);
            String authors = (String) map.get("author");
            String[] aut = authors.split(";");
            for (int j = 0; j < aut.length; j++)
                authorList.add(aut[j]);
        }
        HashSet h = new HashSet(authorList);
        authorList.clear();
        authorList.addAll(h);
        return authorList;
    }

    @Override
    public List getYearCount(String tableName, String searchword) {
        TRSUtil conn = new TRSUtil();
        TRSConnection connect = null;
        TRSResultSet rs = null;
        // TRS数据库读出条数
        int TRSCount = 0;
        ArrayList yearList = new ArrayList();
        try {
            long startTime = System.currentTimeMillis();


            connect = conn.getTrsconnect();
            //"可读取的记录数":rs.getRecordCount()
//            connect.setAutoExtend("CHEMICAL_THESAURUS_UTF8", "", "", TRSConstant.TCM_KAXALLBT | TRSConstant.TCM_KAXALLNT | TRSConstant.TCM_KAXST | TRSConstant.TCM_KAXPT | TRSConstant.TCM_KAXRT);
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            for (int i = 0; i < 6; i++) {
                HashMap yearcont = new HashMap();
                Date d = new Date();
                String dateNowStr = format.format(d);
                String year = Integer.parseInt(dateNowStr) - i - 1 + "";

                String s = searchword + " and pub_date>='" + year + "'";
//
                if (i == 5) {
                    s = searchword + " and pub_date<='" + year + "'";
                }
                rs = connect.executeSelect(tableName, s, "", false);
                TRSCount = new Long(rs.getRecordCount()).intValue();
                yearcont.put("year", year);
                yearcont.put("count", TRSCount);

                yearList.add(yearcont);
            }
            long endTime = System.currentTimeMillis();
        } catch (TRSException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (connect != null) {
                connect.clean();
            }
        }

        return yearList;
    }

    @Override
    public SearchResult getRelatedtData(String tableName, String searchword) {
        SearchResult searchResult = new SearchResult();
        TRSUtil conn = new TRSUtil();
        TRSConnection connect = null;
        TRSResultSet rs = null;
        List trsList = new ArrayList();
        // TRS数据库读出条数
        int TRSCount = 0;
        try {
            connect = conn.getTrsconnect();
            String licenceCodes = connect.getLicenceCode();
            searchResult.setLicenceCode(licenceCodes);

            rs = connect.executeSelect(tableName, searchword, "RELEVANCE", false);
            //"可读取的记录数":rs.getRecordCount()
            TRSCount = new Long(rs.getRecordCount()).intValue();
            for (int i = 0; i < 5 && i < rs.getRecordCount(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                rs.moveTo(0, i);
                map.put("id", rs.getString("id"));
                map.put("title", rs.getString("title", "#e79332"));
                map.put("content", rs.getStringWithCutsize("TextContent", 300, "#ca0000"));
                String soucetitle=rs.getString("zid_title");
                map.put("abs", rs.getString("abs","red"));
                map.put("hc", rs.getString("HTMLContent"));
                map.put("keyword", rs.getString("keyword"));
                map.put("zid", rs.getString("zid"));
                map.put("bookcode", rs.getString("bookcode"));
                if(soucetitle!=null && !soucetitle.equals("")){
                    String[] split = soucetitle.split("<.,>");
                    List search = new ArrayList<>();
                    for (String  s : split) {
                        Map<String,Object> mapSource = new HashMap();
                        String[] splits = s.split("<,.>");
                        String id=splits[0];
                        String titleSource=splits[1];
                        mapSource.put("id",id);
                        mapSource.put("titleSource",titleSource);
                        search.add(mapSource);
                    }
                    map.put("listZT", search);
                    trsList.add(map);
                }

            }
        } catch (TRSException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (connect != null) {
                connect.clean();
            }
        }
        searchResult.setTrsList(trsList);
        searchResult.setTRSCount(TRSCount);

        return searchResult;
    }

    @Override
    public Map getResource(String keywords, String type) {
        return null;
    }


    @Override
    public String getWord(String word) {
        String wd = StringUtil.cn2Spell(word);
        String[] pyList = CKMUtil.getPyList(wd, "chemical", 1);
        String realWord = "";
        if (pyList.length > 0) {
            realWord = pyList[1];
        }
        if (realWord.length() == word.length()) {
            return realWord;
        } else {
            return word;
        }
    }

    @Override
    public SearchResult bookAnalyse(String tableName, String searchword, String orderBy, int pageIndex, int pageSize) {
        SearchResult searchResult = new SearchResult();
        TRSUtil conn = new TRSUtil();
        TRSConnection connect = null;
        TRSResultSet rs = null;
        List trsList = new ArrayList();
        // TRS数据库读出条数
        int TRSCount = 0;
        try {
            connect = conn.getTrsconnect();
            long startTime = System.currentTimeMillis();
            rs = connect.executeSelect(tableName, searchword, orderBy,false);
            long endTime = System.currentTimeMillis();
            //"可读取的记录数":rs.getRecordCount()
            TRSCount = new Long(rs.getRecordCount()).intValue();
            int trsSelectInsex = pageIndex - 1;
            ArrayList categoryList = new ArrayList();
            for (int i = trsSelectInsex * pageSize; i < trsSelectInsex * pageSize + pageSize && i < rs.getRecordCount(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                rs.moveTo(0, i);
                map.put("id", rs.getString("id"));
                map.put("title", rs.getString("name", "red"));
                map.put("content", rs.getStringWithCutsize("content", 200, "red"));
                map.put("keyword", StringUtil.toNoHtml(rs.getString("keyword")));
                map.put("page", rs.getString("pdfpage"));
                trsList.add(map);
            }
        } catch (TRSException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (connect != null) {
                connect.clean();
            }
        }
        searchResult.setTrsList(trsList);
        searchResult.setTRSCount(TRSCount);

        return searchResult;
    }

    @Override
    public String getSql(String keyWord, String type, String organ,String time, String startTime, String endTime, String bookName, String people,String entry,String source) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("(title/50,TextContent/30,keyword/10,abs/10)+=like('"+keyWord+"')");
        if (!source.equals("")){
            sqlBuffer.append(" and source='%"+source+"%'");
        }

        if(!organ.equals("")){
            sqlBuffer.append(" and organ='%"+organ+";%'");
        }
        if(!time.equals("")&&!time.equals("-1")&&!time.equals("1")){
            sqlBuffer.append(" and bookyear='"+time+"'");
        }
        if(!startTime.equals("")){
            sqlBuffer.append(" and bookyear>="+startTime);
        }
        if(!endTime.equals("")){
            sqlBuffer.append(" and endTime<="+endTime);
        }
        if(!bookName.equals("")){
            sqlBuffer.append(" and bookname='%"+bookName+"%'");
        }
        if(!people.equals("")){
            sqlBuffer.append(" and people='%"+people+"%'");
        }
        if(!entry.equals("")){
            sqlBuffer.append(" and entry='%"+entry+"%'");
        }
        if(!type.equals("")){
            sqlBuffer.append(" and type='"+type+"'");
        }


        System.out.println(sqlBuffer.toString());
        return sqlBuffer.toString();
        //return "(title/50,TextContent/30,keyword/10,abs/10)+=like('经济') and type='wz'";
        //return "((title/50,TextContent/30,keyword/10,abs/10)+=like('经济') and type='wz') and bookname='%中国经济学年鉴%'";
        //return "(((title/50,TextContent/30,keyword/10,abs/10)+=like('经济') and type='wz') and bookname='%中国经济学年鉴%') and bookyear='2008'";
    }

    @Override
    public Map getFrequency(String tablename, String searchword) {

        List<Map<String, Object>> selectList = TRSConnect.select(tablename, searchword);
        ArrayList yearList = new ArrayList();
        ArrayList personList = new ArrayList();
        for (int i = 1; i < selectList.size(); i++) {
            String year = (String) selectList.get(i).get("bookyear");
            if (!year.equals("")) {
                yearList.add(year);
            }
        }
        Map yearmaps = getCountSort(yearList);
        HashMap valMap = new HashMap();
        valMap.put("name",searchword);
        valMap.put("yearmaps",yearmaps);
        return valMap;
    }

    @Override
    public Map<String,Object> resultDataExcel(String type, String[] bookCode, String[] zid) {
        HashMap<String, Object> map = new HashMap<>();
        if ("nj".equals(type)){
            List<Map<String,Object>> books = searchHistoryMapper.resultDataExcelBook(bookCode);
            List<Map<String,Object>> newBooks=new ArrayList<>();
            for (int j = 0; j <bookCode.length ; j++) {
                String s = bookCode[j];
                for (int k = 0; k <books.size() ; k++) {
                    Map<String, Object> stringObjectMap = books.get(k);
                    if (s.equals(stringObjectMap.get("bookcode"))){
                        newBooks.add(stringObjectMap);
                    }
                }
            }
            map.put("data",newBooks);
            map.put("fileName","年鉴搜索结果.xls");
            map.put("firstRow",new ArrayList<String>(){{
                add("年鉴名称");
                add("出版单位");
                add("作者");
                add("简介");
            }});
            map.put("rowValue",new ArrayList<String>(){{
                add("title");
                add("publishername");
                add("author");
                add("abs");
            }});
            return map;
        }else {
            List<Map<String,Object>> bookDetails = searchHistoryMapper.resultDataExcelBookDetails(type, bookCode, zid);
            List<Map<String,Object>> newBookDetails=new ArrayList<>();
            for (int j = 0; j <bookCode.length ; j++) {
                String b = bookCode[j];
                String z = zid[j];
                for (int i = 0; i <bookDetails.size() ; i++) {
                    Map<String, Object> stringObjectMap = bookDetails.get(i);
                    if (b.equals(stringObjectMap.get("bookcode")) && z.equals(stringObjectMap.get("zid"))){
                        String zid_title = stringObjectMap.get("zid_title").toString();
                        if (zid_title.contains("<.,>")){
                            String[] split = zid_title.split("<.,>");
                            String[] split1 = split[0].split("<,.>");
                            String[] split2 = split[1].split("<,.>");
                            String strings=split1[1]+"\\"+split2[1];
                            stringObjectMap.put("zid_title",strings);
                        }
                        newBookDetails.add(stringObjectMap);
                    }
                }
            }
            map.put("data",newBookDetails);
            switch (type){
                case "wz":
                    map.put("fileName","ArticleSearchResult.xls");
                    map.put("firstRow",new ArrayList<String>(){{
                        add("标题");
                        add("文章");
                        add("来源");
                        add("关键词");
                    }});
                    map.put("rowValue",new ArrayList<String>(){{
                        add("title");
                        add("TextContent");
                        add("zid_title");
                        add("keyword");
                    }});
                    break;
                case "rw":
                    map.put("fileName","人物搜索结果.xls");
                    map.put("firstRow",new ArrayList<String>(){{
                        add("人名");
                        add("来自");
                        add("相关内容");
                    }});
                    map.put("rowValue",new ArrayList<String>(){{
                        add("title");
                        add("source");
                        add("HtmlContent");
                    }});
                    break;
                case "lw":
                    map.put("fileName","论文搜索结果.xls");
                    map.put("firstRow",new ArrayList<String>(){{
                    add("标题");
                    add("作者");
                    add("相关记载");
                    }});
                    map.put("rowValue",new ArrayList<String>(){{
                        add("title");
                        add("people");
                        add("source");
                    }});
                    break;
                case "kt":
                    map.put("fileName","课题搜索结果.xls");
                    map.put("firstRow",new ArrayList<String>(){{
                        add("标题");
                        add("内容");
                        add("相关记载");
                    }});
                    map.put("rowValue",new ArrayList<String>(){{
                        add("title");
                        add("exdata");
                        add("source");
                    }});
                    break;
                case "hy":
                    map.put("fileName","会议搜索结果.xls");
                    map.put("firstRow",new ArrayList<String>(){{
                        add("标题");
                        add("内容");
                        add("日期");
                        add("地点");
                    }});
                    map.put("rowValue",new ArrayList<String>(){{
                        add("title");
                        add("HtmlContent");
                        add("exdate");
                        add("exarea");
                    }});
                    break;
                case "ts":
                    map.put("fileName","图书搜索结果.xls");
                    map.put("firstRow",new ArrayList<String>(){{
                        add("标题");
                        add("作者");
                        add("相关记载");
                    }});
                    map.put("rowValue",new ArrayList<String>(){{
                        add("title");
                        add("people");
                        add("source");
                    }});
                    break;
                case "dsj":
                    map.put("fileName","大事记搜索结果.xls");
                    map.put("firstRow",new ArrayList<String>(){{
                        add("标题");
                        add("内容");
                        add("日期");
                    }});
                    map.put("rowValue",new ArrayList<String>(){{
                        add("title");
                        add("HtmlContent");
                        add("exdate");
                    }});
                    break;
                    default:
            }
            return map;
        }
    }

    //截取字符串长度(中文2个字节，半个中文显示一个)
    public static String subTextString(String str,int len){
        if(str.length()<len/2)return str;
        int count = 0;
        StringBuffer sb = new StringBuffer();
        String[] ss = str.split("");
        for(int i=1;i<ss.length;i++){
            count+=ss[i].getBytes().length>1?2:1;
            sb.append(ss[i]);
            if(count>=len)break;
        }
        //不需要显示...的可以直接return sb.toString();
        return (sb.toString().length()<str.length())?sb.append("...").toString():str;
    }

    private String getNjAbs(String info, int length){
        String s = info.replace("<font color=#ca0000>", "<f>").replace("</font>", "</>");

        String rtn = "";
        if (Util.toStr(s).equals("")) {
            return "";
        }
        byte[] bytes;
        try {
            String strrsl = "";
            bytes = s.getBytes("Unicode");
            int n = 0;
            int i = 2;
            for (; i < bytes.length && n < length; i++) {
                if (i % 2 == 1) {
                    n++;
                } else {
                    if (bytes[i] != 0) {
                        n++;
                    }
                }
            }
            if (i % 2 == 1) {
                if (bytes[i - 1] != 0) {
                    i = i - 1;
                } else {
                    i = i + 1;
                }
            }
            strrsl = new String(bytes, 0, i, "Unicode");

            if (strrsl.indexOf("<f>") > -1 && strrsl.lastIndexOf("<f>") > strrsl.lastIndexOf("</>")) {
                strrsl = strrsl.substring(0, strrsl.lastIndexOf("<f>"));
            }
            strrsl = strrsl.replace("<f>", "<font color=#ca0000>").replace("</>", "</font>");

            rtn = strrsl+ "...";
        } catch (Exception e) {
            rtn = info;
        }
        return rtn;
    }
}
