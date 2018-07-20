package com.trs.core.util;

import com.trs.ckm.soap.*;
import com.trs.core.filter.CKMFilter;
import com.trs.model.SysContext;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class CKMUtil {

    /**
     * 获取CKM连接
     *
     * @return
     */
    private static TrsCkmSoapClient client;
    private static String url;
    private static String user;
    private static String pass;

    static {
        url = Config.getKey("trs.ckm.host");
        user = Config.getKey("trs.ckm.username");
        pass = Config.getKey("trs.ckm.password");
        client = new TrsCkmSoapClient();
        client.SetServer(url, user, pass);
        //client.SetTimeOut(0);
    }

    /**
     * 获取关键词
     *
     * @param concent 传入文章内容
     * @param count
     * @return
     */
    public static String getKeyword(String concent, int count) {
        String result = "";
        if(concent == null || concent.trim().equals("")){
            return result;
        }
        ABSResult _aABSResult = null;
        int cnt = 0;
        try {
            AbsTheme[] keywords = client.GetAbsThemeList(concent, count * 3);
            if (keywords != null && keywords.length > 0) {
                for (int i = 0; i < keywords.length; i++) {
                    String kw = keywords[i].getWord();
                    boolean b = true;
                    for(String str : CKMFilter.notKeyword){
                        if (str.equals(kw)){
                            b= false;
                            break;
                        }
                    }
                    if(b){
                        result +=( keywords[i].getWord() + ";");
                        if(++cnt > count){
                            break;
                        }
                    }
                }
            }
            if(result.endsWith(";")){
                result = result.substring(0, result.length()-1);
            }
        } catch (CkmSoapException e) {
            e.printStackTrace();
            result = "";
        }
        if(result.length() > 164){
            result = "";
        }
        return result;
    }

    /**
     * CKM获取地区分类
     * @param content
     * @return
     */
    public static String getZone(String content){
        String result = "";

        if(content == null || content.trim().equals("")){
            return result;
        }


        try {
            RuleCATField[] _RuleFields = new RuleCATField[1];
            _RuleFields[0] = new RuleCATField(content, "正文");
            String sResult;
            sResult = client.RuleCATClassifyText("demo", _RuleFields);
            if (sResult != null) {
                result = sResult;
            }
        } catch (CkmSoapException e) {
            e.printStackTrace();
            result = "";
        }

        return result;
    }

    /**
     * CKM获取新闻分类
     * @param content
     * @return
     */
    public String getClassify(String content){
        String result = "";

        if(content == null || content.trim().equals("")){
            return result;
        }

        try {
            CATRevDetail[] _CatResult = client.CATClassifyText("demo", content);
            if (_CatResult != null) {
                for (int i = 0; i < _CatResult.length; i++) {
                    if (i == 0) {
                        result = _CatResult[i].getCATName() + "\\" + _CatResult[i].getv();
                    } else {
                        result += ";" + _CatResult[i].getCATName() + "\\" + _CatResult[i].getv();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "";
        }

        return result;
    }

    /**
     * 获取文章摘要
     *
     * @param concent 内容
     * @param count
     * @return
     */
    public static String getAbs(String concent, int count) {
        if (concent == null || concent.equals("")) {
            return "";
        }
        String result = "";
        ABSResult _aABSResult = null;
        try {
            _aABSResult = client.ABSText(concent, null, null, new ABSHold(10, 30));
        } catch (CkmSoapException e) {
            e.printStackTrace();
        }
        if (_aABSResult != null) {
            result = _aABSResult.getabs();
        }
        return result;
    }

    /**
     * 获取人名等基本信息
     * @param content
     * @param name
     * @return
     */
    public static String getWordMsg(String content, String name, int cnt) {
        if (content == null || content.trim().equals("")) {
            return "";
        }
        String result = "";
        try {
            PLOResult[] _aPLOResult = client.PLOText(content, 0, 0);
            if (_aPLOResult != null) {
                int count = 0;
                for (int i = 0; i < _aPLOResult.length; i++) {

                    if (_aPLOResult[i].gettype() == getNameCode(name)) {
                        boolean b = true;
                        if(name.equals("机构")){
                            for(String str: CKMFilter.notOrg){
                                if(str.equals(_aPLOResult[i].getword())){
                                    b  = false;
                                    break;
                                }
                            }
                        }
                        if(name.equals("人名")){
                            for(String str: CKMFilter.notPeople){
                                if(str.equals(_aPLOResult[i].getword()) || _aPLOResult[i].getword().startsWith(str)){
                                    b  = false;
                                    break;
                                }
                            }
                        }
                        if(b){
                            result += _aPLOResult[i].getword() + ";";
                            if (++count > (cnt-1)) {
                                break;
                            }
                        }
                    }
                }
            }
        } catch (CkmSoapException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 拼音匹配查询词
     *
     * @param word      匹配的词
     * @param tableName 表名
     * @param num       匹配数量
     * @return
     * @throws CkmSoapException
     */
    public static String[] getPyList(String word, String tableName, int num) {
        String[] result = new String[0];

        try {
            result = client.GetWordTips(word, tableName, num);
            //        String s = result[0];
        } catch (CkmSoapException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 插入拼音词条
     *
     * @param word
     * @param tableName
     * @param option
     * @return
     */
    public static int insertPyWord(String[] word, String tableName, int option) {
        int m = 0;
        try {

            for (int i = 0; i < word.length; i++) {
                try {
                    NumberFormat nf = NumberFormat.getPercentInstance();
                    nf.setMaximumFractionDigits(0);
                    HttpSession session = SysContext.getSession();
                    session.setAttribute("ckm_progress", nf.format((double) i / word.length));
                } catch (Exception e) {
                }
                try {
                    int ret = client.PyInsertWord(tableName, word[i].trim(), 0);
                    if (ret == 0) {
                        m++;
                    } else {
                    }
                } catch (CkmSoapException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    /**
     * 删除所有拼音词库词
     *
     * @throws CkmSoapException
     */
    public static int deletePy(String tableName) throws CkmSoapException {
        int m = 0;
        for (int j = 0; j < 1000; j++) {
            PyListWordResult _wordList = client.PyListWord(tableName, "", 1000, 0, 0);
            if (_wordList != null) {
                for (int i = 0; i < _wordList.getDGList().length; i++) {
                    int num = client.PyDeleteWord("chemical", _wordList.getDGList()[i], 0);
                    m++;
                }
            }
        }
        return m;
    }

    /**
     * 添加短语词条
     *
     * @param tableName
     * @param words
     * @return
     */
    public static int dyInsert(String tableName, String[] words) {
        int m = 0;
        for (int i = 0; i < words.length; i++) {
            try {
                int ret = client.DyInsertWord(tableName, words[i], 0);
                if (ret == 0) {
                    m++;
                } else {
                }
            } catch (CkmSoapException e) {
                e.printStackTrace();
            }
        }
        return m;
    }

    public static int getNameCode(String name){
        if (name.equals("人名")) { return 1001; } else if (name.equals("地名")) { return 1002; } else if (name.equals("机构")) {
            return 1003; } else if (name.equals("时间")) { return 2001; } else if (name.equals("案件")) { return 3000; } else{ return 9999; }
    }

    public static String getBookMsg(String info){

        String msg = "";
        String keyword = "";
        String personname = "";
        String catname = "";
        Map<String, Object> map = new HashMap<>();

        if(info == null || info.trim().equals("")){
            return "";
        }

        try {
            AbsTheme[] keywords = client.GetAbsThemeList(info, 80);
            if (keywords != null && keywords.length > 0) {
                for (int i = 0; i < keywords.length; i++) {
                    boolean b = true;
                    for(String str : CKMFilter.notKeyword){
                        if(str.equals(keywords[i].getWord())){
                            b= false;
                            break;
                        }
                    }
                    if(b){
                        if (i == 0) {
                            keyword = keywords[i].getWord() + "/" + keywords[i].getPfq();
                        } else {
                            keyword += ";" + keywords[i].getWord() + "/" + keywords[i].getPfq();
                        }
                    }
                }
            }
        } catch (CkmSoapException e) {
            e.printStackTrace();
            keyword = "";
        }
        try {
            PLOResult[] _aPLOResult = client.PLOText(info,0,0);
            if (_aPLOResult != null) {
                int count = 0;
                for (int i = 0;i < _aPLOResult.length; i++) {
                    if(_aPLOResult[i].gettype() == getNameCode("人名")){
                        boolean b = true;
                        for(String str : CKMFilter.notPeople){
                            if(_aPLOResult[i].getword().equals(str) || _aPLOResult[i].getword().startsWith(str)){
                                b = false;
                                break;
                            }
                        }
                        if(b){
                            personname += _aPLOResult[i].getword() + "/" + _aPLOResult[i].getFQ() + ";";
                            if(++count > 80){
                                break;
                            }
                        }
                    }
                }
            }
        } catch (CkmSoapException e) {
            e.printStackTrace();
        }
        if(personname.endsWith(";")){
            personname = personname.substring(0, personname.length() -1);
        }
        map.put("keyword", keyword);
        map.put("personname", personname);
        return Util.mapToJson(map);

    }
}
