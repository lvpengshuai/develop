package com.trs.core.breakxml;

import com.trs.core.util.CKMUtil;
import com.trs.core.util.ImageUtil;
import com.trs.core.util.JDBConnect;
import com.trs.core.util.Util;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author a
 */
public class XmlDataCut {
    public static int tsjs = 0;
    public static int rwjs = 0;
    public static int lwjs = 0;
    public static int ktjs = 0;
    public static int jgjs = 0;
    public static int hyjs = 0;
    public static int dsjjs = 0;
    public static String filepath = "";
    public static int depth = 1;
    public static int id = 0;
    public static String fid = "0";
    public static String f1 = "<,.>";
    public static String f2 = "<.,>";
    public static String f3 = "";
    public static StringBuffer wholeContent = new StringBuffer("");
    public static long startTime, tempTime;
    public static String classify, classify_b, mainPath, bookcode, year, bookname, booknameFull, bookAbbreviate, bookClass = "";
    public static Map<String, Integer> mapLog = new HashMap<>();
    public static List list = new ArrayList();

    public static void main(String[] args) {
        //String xmlpath = args[0];
        String xmlpath = "D:\\cssp_resource\\世界经济年鉴2014卷\\Main.xml";
        //1 代表原先路径传来的解析  2 代表是  点击解析来的路径解析
        putXmlData(xmlpath, "2");
        System.out.println("OVER");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void putXmlData(String xmlpath, String Identification) {
        String OS = System.getProperty("os.name").toLowerCase();
        startTime = System.currentTimeMillis();
        tempTime = startTime;
        list.clear();
        if (Util.toStr(xmlpath).equals("")) {
            return;
        } else {
            filepath = xmlpath.replace("\\", "/");
        }
        // 资源结构初期检查
        if (getBookClass(Identification) < 0) {
            Util.log("资源信息取得失败.", bookcode, 0);
            return;
        } else {
            deleteData();
        }
        jishi("删除既存数据完成");
        // 创建命名
        Map map = new HashMap();
        map.put("tag", "http://docbook.org/ns/docbook");
        SAXReader saxReader = new SAXReader();
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);

        try {
            Document document = saxReader.read(new File(filepath));
            String wholeStr = Util.rmHTMLTag(document.asXML().replace("\n", "").replace("\t", "").replace("续表", ""));
            try {
                String t = Util.toStr(wholeStr.length());
                Util.log("登录全书,约" + t.substring(0, t.length() - 3) + "万字", bookcode, 0);
            } catch (Exception e) {
                System.out.println(e);
                return;
            }
            if (wholeStr.length() > 17000000) {
                wholeStr = wholeStr.substring(0, 16000000);
            }
            wholeContent.append(wholeStr);
            jishi("将整本书读入内存完成");
            // -----------获取词条内容----------------
            getCitiao(document);

//            System.out.println("图书词条"+tsjs);
//            System.out.println("论文词条"+lwjs);
//            System.out.println("人物词条"+rwjs);
//            System.out.println("课题词条"+ktjs);
//            System.out.println("机构词条"+jgjs);
//            System.out.println("会议词条"+hyjs);
//            System.out.println("大事记词条"+dsjjs);
            //临时文件log
            Util.logXml("- - - - - - - - - - 录入词条信息- - - - - - - - - - ", "examination", 0, 1);
            Util.logXml("图书词条：\t" + tsjs, "examination", 0, 1);
            Util.logXml("论文词条：\t" + lwjs, "examination", 0, 1);
            Util.logXml("人物词条：\t" + rwjs, "examination", 0, 1);
            Util.logXml("课题词条：\t" + ktjs, "examination", 0, 1);
            Util.logXml("机构词条：\t" + jgjs, "examination", 0, 1);
            Util.logXml("会议词条：\t" + hyjs, "examination", 0, 1);
            Util.logXml("大事记词条：\t" + dsjjs, "examination", 0, 1);
//            //永久文件log
            Util.logXml("- - - - - - - - - - 录入词条信息- - - - - - - - - - ", "permanentRecords", 0, 2);
            Util.logXml("图书词条：\t" + tsjs, "permanentRecords", 0, 2);
            Util.logXml("论文词条：\t" + lwjs, "permanentRecords", 0, 2);
            Util.logXml("人物词条：\t" + rwjs, "permanentRecords", 0, 2);
            Util.logXml("课题词条：\t" + ktjs, "permanentRecords", 0, 2);
            Util.logXml("机构词条：\t" + jgjs, "permanentRecords", 0, 2);
            Util.logXml("会议词条：\t" + hyjs, "permanentRecords", 0, 2);
            Util.logXml("大事记词条：\t" + dsjjs, "permanentRecords", 0, 2);

            // ------------获取整本书内容--------------
            getWholeBookContent();

            // ----------- 获取书基本信息--------------
            getBase(document);

            // ------------获取书籍目录----------------
            getMenu(document);

            // -----------获取文章内容----------------
            getWenxian(document);

            getChapterCiTiao(document);

        } catch (Exception e) {
            Util.log("-------------" + classify + "---------------", "err", 0);
            Util.log(e.getMessage(), "err", 0);
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            Util.log(sw.toString(), "err", 0);
            e.printStackTrace();
            return;
        }

        // 更新书籍种类卷次数
        updateAbb();
        // 最终处理
        theLast();

    }

    public static int getBookClass(String Identification) {
        try {
            classify_b = filepath.substring(filepath.substring(0, filepath.lastIndexOf("/")).lastIndexOf("/") + 1, filepath.substring(0, filepath.lastIndexOf("/")).length());
            System.out.println(classify_b);
            if (classify_b.endsWith("年")) {
                classify = classify_b.substring(0, classify_b.length() - 1) + "卷";
            } else {
                classify = classify_b;
            }
            year = classify.substring(classify.length() - 5, classify.length() - 1);
            booknameFull = classify.substring(0, classify.length() - 5);
            bookname = booknameFull.replaceAll("[0-9]+-$", "");
            if (Identification.equals("1")) {
                mainPath = filepath.substring(filepath.indexOf("cssp_resource") - 1).replace("Main.xml", "");
            } else {
                mainPath = filepath.substring(filepath.indexOf("cssp_source_file") - 1).replace("Main.xml", "");
            }
            bookClass = "学科类年鉴";
            bookAbbreviate = Util.getPinYinHeadChar(bookname);
            bookcode = bookAbbreviate + year;
        } catch (Exception e) {
            return -1;
        }
        if (!year.startsWith("1") && !year.startsWith("2")) {
            return -1;
        } else if (classify.equals("") || year.equals("") || bookname.equals("") || mainPath.equals("") || bookClass.equals("") || bookAbbreviate.equals("") || bookcode.equals("")) {
            return -1;
        }
        Util.log("classify:\t" + classify + "\nbookyear:\t" + year + "\nbookname:\t" + bookname + "\nbookcode:\t" + bookcode + "\nbookClass:\t" + bookClass + "\nbookAbbreviate:\t" + bookAbbreviate, bookcode, 0);
        //临时解析内容
        Util.logXml("- - - - - - - - - - " + Util.getNowTime() + "- - - - - - - - - - ", "examination", 0, 1);
        Util.logXml("正在处理" + classify + "请稍后...", "examination", 0, 1);
        Util.logXml("年鉴名称：\t" + classify, "examination", 0, 1);
        Util.logXml("年鉴年份：\t" + year, "examination", 0, 1);
        Util.logXml("图书名称：\t" + bookname, "examination", 0, 1);
        Util.logXml("图书编码：\t" + bookcode, "examination", 0, 1);
        Util.logXml("图书类别：\t" + bookClass, "examination", 0, 1);
        Util.logXml("名称缩写：\t" + bookAbbreviate, "examination", 0, 1);
        // 全部解析文件内容
        Util.logXml("- - - - - - - - - - " + Util.getNowTime() + "- - - - - - - - - - ", "permanentRecords", 0, 2);
        Util.logXml("年鉴名称：\t" + classify, "permanentRecords", 0, 2);
        Util.logXml("年鉴年份：\t" + year, "permanentRecords", 0, 2);
        Util.logXml("图书名称：\t" + bookname, "permanentRecords", 0, 2);
        Util.logXml("图书编码：\t" + bookcode, "permanentRecords", 0, 2);
        Util.logXml("图书类别：\t" + bookClass, "permanentRecords", 0, 2);
        Util.logXml("名称缩写：\t" + bookAbbreviate, "permanentRecords", 0, 2);

        return 0;
    }

    public static void deleteData() {
        JDBConnect db = new JDBConnect();
        db.delete("delete from book where bookcode='" + bookcode + "';");
        db.delete("delete from book_author where bookcode='" + bookcode + "';");
        db.delete("delete from book_content where bookcode='" + bookcode + "';");
        db.delete("delete from book_details where bookcode='" + bookcode + "';");
        db.delete("delete from book_file where bookcode='" + bookcode + "';");
    }

    @SuppressWarnings("unchecked")
    public static String getSectContent(Element e, String fid) {
        List<Element> le1 = e.elements();
        String content = "";
        for (Element e1 : le1) {
            if (e1.getName().equals("para")) {
                content += setp(e1, fid, null);
            }

            if (e1.getName().equals("sect1")) {
                List<Element> le2 = e1.elements();
                for (Element e2 : le2) {
                    if (e2.getName().equals("title")) {
                        content += joinPara(e1, e2.getText());
                    }
                    if (e2.getName().equals("para")) {
                        content += setp(e2, fid, e1);
                    }

                    if (e2.getName().equals("sect2")) {
                        List<Element> le3 = e2.elements();
                        for (Element e3 : le3) {
                            if (e3.getName().equals("title")) {
                                content += joinPara(e2, e3.getText());
                            }
                            if (e3.getName().equals("para")) {
                                content += setp(e3, fid, e2);
                            }
                            if (e3.getName().equals("sect3")) {
                                List<Element> le4 = e3.elements();
                                for (Element e4 : le4) {
                                    if (e4.getName().equals("title")) {
                                        content += joinPara(e3, e4.getText());
                                    }
                                    if (e4.getName().equals("para")) {
                                        content += setp(e4, fid, e3);
                                    }
                                    if (e4.getName().equals("sect4")) {
                                        List<Element> le5 = e4.elements();
                                        for (Element e5 : le5) {
                                            if (e5.getName().equals("title")) {
                                                content += joinPara(e4, e5.getText());
                                            }
                                            if (e5.getName().equals("para")) {
                                                content += setp(e5, fid, e4);
                                            }
                                            if (e5.getName().equals("sect5")) {
                                                List<Element> le6 = e5.elements();
                                                for (Element e6 : le6) {
                                                    if (e6.getName().equals("title")) {
                                                        content += joinPara(e5, e6.getText());
                                                    }
                                                    if (e6.getName().equals("para")) {
                                                        content += setp(e6, fid, e5);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return content;
    }

    @SuppressWarnings("unchecked")
    public static void getPic(Element e, String Zid, String Fid, String bookcode) {
        Map<String, Object> DBMap = new HashMap<>();
        Element e0 = e.element("mediaobject").element("imageobject").element("imagedata");
        Element e1 = e0.element("info");
        String picTitle = "";
        String picPara = "";
        String fileurl = "";
        if (e1 != null && e1.element("releaseinfo") != null && !e1.element("releaseinfo").getStringValue().equals("")) {
            if (mainPath.indexOf("cssp_source_file") != -1) {
                String mainPathl = mainPath.replace("cssp_source_file", "cssp_resource");
                fileurl = mainPathl + e1.element("releaseinfo").getStringValue();
                DBMap.put("filetype", "excel");
            }else {
                fileurl = mainPath + e1.element("releaseinfo").getStringValue();
                DBMap.put("filetype", "excel");
            }


        } else {
            if (mainPath.indexOf("cssp_source_file") != -1) {
                String mainPathl = mainPath.replace("cssp_source_file", "cssp_resource");
                fileurl = mainPathl + e0.attribute("fileref").getValue();
                DBMap.put("filetype", "image");
            }else {
                fileurl = mainPath + e0.attribute("fileref").getValue();
                DBMap.put("filetype", "image");
            }

        }
        String bookpage = "";
        String pdfpage = "";
        if (e1 != null && e1.element("title") != null) {
            picTitle = e1.element("title").getStringValue();
        } else {
        }

        if (e1 != null && e1.element("pagenums") != null) {

            List<Element> le2 = e1.elements();
            int ia = 0;
            for (Element e2 : le2) {
                if (e2.getName().equals("pagenums")) {
                    if (ia == 0) {
                        bookpage = e2.getTextTrim();
                        ia = 1;
                    } else {
                        pdfpage = e2.getTextTrim();
                    }
                }
            }
        }
        if (e1 != null && e1.element("annotation") != null && e1.element("annotation").element("para") != null) {
            picPara = e1.element("annotation").element("para").getStringValue().replace("'", "&apos;");
        }
        // 插入数据库  图片
        DBMap.put("title", picTitle);
        DBMap.put("content", picPara);
        DBMap.put("fid", Fid);
        DBMap.put("bookpage", bookpage);
        DBMap.put("pdfpage", pdfpage);
        DBMap.put("fileurl", fileurl);
        DBMap.put("zid", Zid);
        DBMap.put("bookcode", bookcode);
        putdata("book_file", DBMap);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getInfoAuthor(Element e, String fid, String entry) {
        Map<String, Object> mapRtn = new HashMap<>();
        String authorStr = "";
        List<Element> le1 = e.elements();
        for (Element e1 : le1) {
            if (e1.getName().equals("authorgroup")) {
                List<Element> le2 = e1.elements();
                for (Element e2 : le2) {
                    try {
                        Map<String, Object> map = new HashMap<>();
                        map.put("fid", fid);

                        // 作者简介
                        String blurb = "";
                        List<Element> le3 = e2.elements();
                        for (Element e3 : le3) {
                            if (e3.getName().equals("personblurb")) {
                                blurb = e2.element("personblurb").element("para").getText();
                            } else {
                                blurb = entry;
                            }
                        }

                        map.put("personblurb", blurb);
                        String personname = e2.element("personname").getStringValue();
                        authorStr += "-" + personname;
                        map.put("personname", personname);
                        map.put("bookcode", bookcode);

                        // 插入数据库  作者表
                        putdata("book_author", map);
                    } catch (Exception err) {
                        Util.log("----------------------------", bookcode + "-" + "war", 1);
                        StringWriter sw = new StringWriter();
                        err.printStackTrace(new PrintWriter(sw, true));
                        Util.log(sw.toString(), bookcode + "-" + "war", 1);
                        Util.log(e2.asXML(), bookcode + "-" + "war", 1);
                        break;
                    }
                }
            }
            if (e1.getName().equals("pagenums")) {
                if (e1.attribute("role").getValue().equals("pdfpage")) {
                    mapRtn.put("pdfpage", e1.getStringValue());
                }
                if (e1.attribute("role").getValue().equals("bookpage")) {
                    mapRtn.put("bookpage", e1.getStringValue());
                }
            }
        }
        mapRtn.put("authorStr", authorStr);

        return mapRtn;
    }

    /**
     * 判断是否 Element下是否含有 名称为info的子节点
     *
     * @param e
     * @param info
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String isExist(Element e, String info) {
        List<Element> le1 = e.elements();
        String b = "Y";
        for (Element e1 : le1) {
            if (e1.getName().equals(info) && e1.getTextTrim() != null) {
                b = "N";
            }
        }
        return b;
    }

    public static void getBase(Document document) {
        Map<String, Object> DBMap = new HashMap<>();
        //获取年鉴基本信息book表
        String abs = CKMUtil.getAbs(wholeContent.toString(), 10);
        jishi("书籍扫描简介完成");
        DBMap.put("bookyear", year);
        DBMap.put("bookabbreviate", bookAbbreviate);
        DBMap.put("bookcode", bookcode);
        DBMap.put("bookname", bookname);
        DBMap.put("title", getXpathValue(document, "/tag:book/tag:info/tag:title[@xml:lang='ZH']", ""));
        DBMap.put("title_en", Util.toStr(getXpathValue(document, "/tag:book/tag:info/tag:title[@xml:lang='en']", ""), "　"));
        DBMap.put("issn", getXpathValue(document, "/tag:book/tag:info/tag:biblioid[@class='issn']", ""));
        DBMap.put("isbn", getXpathValue(document, "/tag:book/tag:info/tag:biblioid[@class='isbn']", ""));
        DBMap.put("author", getXpathValue(document, "/tag:book/tag:info/tag:authorgroup/tag:author[@role='编']/tag:personname", ""));
        DBMap.put("publishername", getXpathValue(document, "/tag:book/tag:info/tag:publisher/tag:publishername", ""));
        DBMap.put("booktype", getXpathValue(document, "/tag:book/tag:info/tag:biblioid[@otherclass='booktype']", ""));
        DBMap.put("charcount", getXpathValue(document, "/tag:book/tag:info/tag:releaseinfo[@role='wordnum']", ""));
        if (((mainPath + getXpathValue(document, "/tag:book/tag:info/tag:cover/tag:mediaobject/tag:imageobject/tag:imagedata", "fileref")).toString()).indexOf("cssp_source_file") == -1) {
            DBMap.put("cover", mainPath + getXpathValue(document, "/tag:book/tag:info/tag:cover/tag:mediaobject/tag:imageobject/tag:imagedata", "fileref"));
        } else {
            String mainPathl =  mainPath.replace("cssp_source_file", "cssp_resource");
            DBMap.put("cover", mainPathl + getXpathValue(document, "/tag:book/tag:info/tag:cover/tag:mediaobject/tag:imageobject/tag:imagedata", "fileref"));

        }
        DBMap.put("abs", abs.length() > 350 ? abs.substring(0, 350) : abs);
        DBMap.put("pubdate", getXpathValue(document, "/tag:book/tag:info/tag:pubdate", ""));
        DBMap.put("people", CKMUtil.getWordMsg(wholeContent.toString(), "人名", 10));
        jishi("书籍扫描人名完成");
        DBMap.put("organ", CKMUtil.getWordMsg(wholeContent.toString(), "机构", 10));
        jishi("书籍扫描机构完成");
        DBMap.put("mainmsg", CKMUtil.getBookMsg(wholeContent.toString()));
        jishi("书籍扫描搜索信息完成");
        DBMap.put("bookClass", bookClass);
        DBMap.put("keyword", CKMUtil.getKeyword(wholeContent.toString(), 10));
        jishi("书籍扫描关键字完成");

        if (mainPath + getXpathValue(document, "/tag:book/tag:info/tag:releaseinfo[@role='singlelowerpdf']", "href") == "") {
            if (((mainPath + getXpathValue(document, "/tag:book/tag:info/tag:releaseinfo[@role='singlelowerpdf']", "href")).toString()).indexOf("cssp_source_file") != -1) {
                String mainPathl =  mainPath.replace("cssp_source_file", "cssp_resource");
                DBMap.put("epub", mainPathl + getXpathValue(document, "/tag:book/tag:info/tag:releaseinfo[@role='singlelowerpdf']", "href").replace("PDF", "pdf"));
            } else {
                DBMap.put("epub", mainPath + getXpathValue(document, "/tag:book/tag:info/tag:releaseinfo[@role='singlelowerpdf']", "href").replace("PDF", "pdf"));
            }
        } else {
            if (((mainPath + getXpathValue(document, "/tag:book/tag:info/tag:releaseinfo[@role='doublehighpdf']", "href")).toString()).indexOf("cssp_source_file") != -1) {
                String mainPathl =  mainPath.replace("cssp_source_file", "cssp_resource");
                DBMap.put("epub", mainPathl + getXpathValue(document, "/tag:book/tag:info/tag:releaseinfo[@role='doublehighpdf']", "href").replace("PDF", "pdf"));
            } else {
                DBMap.put("epub", mainPath + getXpathValue(document, "/tag:book/tag:info/tag:releaseinfo[@role='doublehighpdf']", "href").replace("PDF", "pdf"));
            }
        }

        putdata("book", DBMap);
        String coverImg = filepath.replace("Main.xml", "") + getXpathValue(document, "/tag:book/tag:info/tag:cover/tag:mediaobject/tag:imageobject/tag:imagedata", "fileref");
        ImageUtil.reduceImg(coverImg, coverImg, 400, 568, null);
    }

    @SuppressWarnings("unchecked")
    public static void getWenxian(Document document) {
        Map<String, Object> DBMap = new HashMap<>();
        DBMap.put("bookcode", bookcode);
        DBMap.put("bookname", bookname);
        DBMap.put("bookyear", year);
        DBMap.put("classify", "wz");
        // ID和标题组合
        String id_title_base = bookcode + f1 + booknameFull + year;
        List<Element> tag = document.selectNodes("/tag:book/tag:part");
        for (Element element : tag) {
            String fid = element.attribute("id").getValue();
            String zid = "";
            DBMap.put("zid", fid);
            DBMap.put("fid", "0");
            String id_title = "";
            id_title = id_title_base + f2 + fid + f1 + getXpathValue(element, "./tag:title");
            DBMap.put("zid_title", id_title);
            List<Element> le1 = element.elements();

            // 循环book/part中的子标签
            for (Element e1 : le1) {
                String authorStr = "";
                // 循环book/part/chapter
                if (e1.getName().equals("chapter")) {
                    zid = e1.attribute("id").getValue();
                    DBMap.put("zid", zid);
                    DBMap.put("fid", fid);
                    DBMap.put("content_pdf", mainPath + e1.attribute("href").getValue());
                    Map<String, Object> mapAuthor = getInfoAuthor(e1.element("info"), zid, "");
                    DBMap.put("pdfpage", Util.toStr(mapAuthor.get("pdfpage")));
                    DBMap.put("bookpage", Util.toStr(mapAuthor.get("bookpage")));
                    authorStr = Util.toStr(mapAuthor.get("authorStr"));

                    List<Element> le2 = e1.elements();
                    for (Element e2 : le2) {
                        if (e2.getName().equals("title")) {
                            DBMap.put("title", e2.getText());
                            if (e2.getText().endsWith("综述") || getXpathValue(e1, "./@role").equals("综述词条")) {
                                DBMap.put("entry", "综述词条");
                            } else {
                                DBMap.put("entry", "普通文章");
//                                DBMap.put("entry", getXpathValue(e1, "./@role"));
                            }
                            break;
                        }
                    }
                    String c = getSectContent(e1, zid);
                    DBMap.put("HtmlContent", c);
                    DBMap.put("TextContent", Util.rmHTMLTag(c).replace("续表", "").replace("点击下载附件↑", "") + authorStr);
                    DBMap.put("keyword", CKMUtil.getKeyword(Util.rmHTMLTag(c).replace("表", "").replace("点击下载附件↑", ""), 10));
                    DBMap.put("people", CKMUtil.getWordMsg(Util.rmHTMLTag(c), "人名", 2));
                    DBMap.put("organ", CKMUtil.getWordMsg(Util.rmHTMLTag(c), "机构", 2));
                    DBMap.put("abs", Util.rmHTMLTag(c).length() > 1000 ? CKMUtil.getAbs(Util.rmHTMLTag(c), 10) : "");
                    DBMap.put("source", authorStr.replace("-", ";") + ";");
                    DBMap.put("title", Util.rmNumIndex(Util.toStr(DBMap.get("title"))));
                    putdata("book_details", DBMap);
                    setMapLog("book_details.article");
                }
            }
        }
        jishi("获取文章内容完成");
    }

    public static void getchapterlw(Document document){

        List<Element> tag = document.selectNodes("/tag:book/tag:part");
        for (Element element : tag) {

            List<Element> le1 = element.elements();

            // 循环book/part中的子标签
            for (Element e1 : le1) {
                String authorStr = "";
                // 循环book/part/chapter
                if (e1.getName().equals("chapter")) {
                    String lwct = e1.attribute("role").getValue();
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void getCitiao(Document document) {
        Map<String, Object> DBMap = new HashMap<>();
        DBMap.put("bookcode", bookcode);
        DBMap.put("bookname", bookname);
        DBMap.put("bookyear", year);
        DBMap.put("classify", "ct");
        List<Element> tag = document.selectNodes("/tag:book/tag:part");
        for (Element e1 : tag) {
            f3 = getXpathValue(e1, "./tag:title") + "/" + getXpathValue(e1, "./tag:chapter/tag:title");
            List<Element> le2 = e1.elements();
            for (Element e2 : le2) {
                DBMap.put("fid", e1.attribute("id").getValue());
                List<Element> lsect1 = e2.elements();
                for (Element sect1 : lsect1) {
                    if (sect1.getName().equals("sect1")) {
                        if (sect1.attribute("role") != null) {
                            getSect(sect1, DBMap);
                        }
                        List<Element> lsect2 = sect1.elements();
                        for (Element sect2 : lsect2) {

                            if (sect2.getName().equals("sect2")) {
                                if (sect2.attribute("role") != null) {
                                    getSect(sect2, DBMap);
                                }
                            }


                            List<Element> lsect3 = sect2.elements();
                            for (Element sect3 : lsect3) {

                                if (sect3.getName().equals("sect3")) {
                                    if (sect3.attribute("role") != null) {
                                        getSect(sect3, DBMap);
                                    }
                                }

                                List<Element> lsect4 = sect3.elements();
                                for (Element sect4 : lsect4) {

                                    if (sect4.attribute("role") != null) {
                                        getSect(sect4, DBMap);
                                    }

                                    List<Element> lsect5 = sect4.elements();

                                    for (Element sect5 : lsect5) {


                                        if (sect5.attribute("role") != null) {
                                            getSect(sect5, DBMap);
                                        }

//                                        List<Element> lsect6 = sect5.elements();
//                                        for (Element sect6 : lsect6) {
//                                            if (sect6.attribute("role") != null) {
//                                                getSect(sect6, DBMap);
//                                            }
//                                        }

                                    }


                                }


                            }


                        }
                    }
                }
            }
        }
        jishi("获取词条内容完成");
    }

    public static String getNodeValse(Node node) {
        try {
            if (node != null) {
                return node.getStringValue();
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String setp(Element e, String exstr, Element ex) {
        String content = "";
        if (exstr.equals("getPContentOnly")) {
            if (isHavePic(e)) {
                return content;
            }
            content = "<p>" + Util.rmHTMLTag(e.asXML()) + "</p>";
        } else if (isHavePic(e)) {
            // 处理独立图片
            getPic(e, getUUID(), exstr, bookcode);
            String picMain = "";
            if (mainPath.indexOf("cssp_source_file") != -1) {
                String mainPathl = mainPath.replace("cssp_source_file", "cssp_resource");
                picMain = "<img class='f-single-img' src=\"" + mainPathl + e.element("mediaobject").element("imageobject").element("imagedata").attribute("fileref").getValue() + "\">";
            }else {
               picMain = "<img class='f-single-img' src=\"" + mainPath + e.element("mediaobject").element("imageobject").element("imagedata").attribute("fileref").getValue() + "\">";
            }


            String picRole = e.element("mediaobject").element("imageobject").element("imagedata").attribute("role").getValue();

            String picTmp = "";
            if (picRole.equals("figure")) {
                picTmp = getXpathValue(e, "./tag:mediaobject/tag:imageobject/tag:imagedata/tag:info/tag:title");
                content += picMain;
                if (!picTmp.equals("")) {
                    content += "<p><div class=\"f-pic-figure-title\">" + picTmp + "</div></p>";
                }
                picTmp = getXpathValue(e, "./tag:mediaobject/tag:imageobject/tag:imagedata/tag:info/tag:annotation[@role='show']/tag:para");
                if (!picTmp.equals("")) {
                    content += "<p><div class=\"f-pic-figure-show\">" + picTmp + "</div></p>";
                }
            } else if (picRole.equals("table")) {
                picTmp = getXpathValue(e, "./tag:mediaobject/tag:imageobject/tag:imagedata/tag:info/tag:title");
                if (!picTmp.equals("")) {
                    content += "<p><div class=\"f-pic-table-title\">" + picTmp + "</div></p>";
                }
                content += picMain;
                picTmp = getXpathValue(e, "./tag:mediaobject/tag:imageobject/tag:imagedata/tag:info/tag:annotation[@role='show']/tag:para");
                if (!picTmp.equals("")) {
                    content += "<p><div class=\"f-pic-table-show\">" + picTmp + "</div></p>";
                }
            } else {
                content += picMain;
            }

            String excel = getXpathValue(e, "./tag:mediaobject/tag:imageobject/tag:imagedata/tag:info/tag:releaseinfo[@role='excelattach']");
            if (!excel.equals("")) {

                if (mainPath.indexOf("cssp_source_file") != -1) {
                    String mainPathl = mainPath.replace("cssp_source_file", "cssp_resource");
                    content += "<div class=\"f-a-file-down\"><a class=\"f-id-link\" href=\"" + mainPathl + excel + "\" download=\"附件\">" + "点击下载附件↑</a></div>";
                } else {
                    content += "<div class=\"f-a-file-down\"><a class=\"f-id-link\" href=\"" + mainPath + excel + "\" download=\"附件\">" + "点击下载附件↑</a></div>";
                }

            }

        } else {
            // 处理上下角标
            String paraderole = getXpathValue(e, "./@role");
            String s0 = e.asXML().replace("<para xmlns=\"http://docbook.org/ns/docbook\"/>", "");
            if (s0.equals("")) {
                return "";
            }
            String s1 = s0.replace("<subscript>", "<sub>").replace("</subscript>", "</sub>").replace("<superscript>", "sup").replace("</superscript>", "</sup>");
            // 处理内置图片1
            String s2 = "";
            if (mainPath.indexOf("cssp_source_file") != -1) {
                String mainPathl = mainPath.replace("cssp_source_file", "cssp_resource");
                s2 = s1.replace("\n", "").replace("\t", "").replace("<inlinemediaobject><imageobject><imagedata fileref=\"", "<img class=\"f-in-line-img\" src=\"" + mainPathl);
            }else {
                s2 = s1.replace("\n", "").replace("\t", "").replace("<inlinemediaobject><imageobject><imagedata fileref=\"", "<img class=\"f-in-line-img\" src=\"" + mainPath);
            }
             // 处理内置图片2
            String s3 = s2.replaceAll("width.*</info>.*</inlinemediaobject>", ">");
            // 处理柱脚
            String s4 = s3.replace("<footnote><para>", "<span class='f-in-line-boot' title=\"").replace("</para></footnote>", "\"><span class='f-in-wordshow'>[※注]</span></span>");
            // 处理段落格式
            String s5 = s4.replaceAll("<para[^>]+>", "<p>").replace("<para>", "<p>").replace("</para>", "</p>");
            if (!paraderole.equals("")) {
                s5 = s5.replace("<p>", "<p class=\"f-para-role-" + paraderole + "\">");
            }
            String s6 = s5.replace("<link xml:base=\"", "<a class=\"f-id-link\"  href=\"javascript:void(0)\" onclick=\"fwdIdUrl(this)\" id=\"").replace("</link>", "</a>");
            String s7 = s6.replaceAll("<emphasis role=\\\"[a-z]*\\\">", "").replace("</emphasis>", "");
            content += s7;
        }
        content = content.replace("\t", "").replace("<p>　</p>", "");
        return content;
    }

    @SuppressWarnings("unchecked")
    public static String joinPara(Element e, String info) {
        String rtn = "<p class='ftitle'>" + info;
        String ctname = getXpathValue(e, "./@role");
        if (ctname.equals("图书词条")) {
            rtn += "<span class=\"f-nor-ct\">" + getXpathValue(e, "./tag:info/tag:authorgroup/tag:author/tag:personname") + "</span>";
            rtn += "<span class=\"f-nor-ct\">" + getXpathValue(e, "./tag:info/tag:releaseinfo[@role='来源']") + "</span>";
        } else if (ctname.equals("论文词条")) {
            List<Element> ctlw = e.selectNodes("./tag:info/tag:authorgroup/tag:author/tag:personname");
            String ctlwname = "";
            for (Element elw : ctlw) {
                ctlwname += elw.getStringValue() + "、";
            }
            if (ctlwname.endsWith("、")) {
                ctlwname = ctlwname.substring(0, ctlwname.length() - 1);
            }
            rtn += "<span class=\"f-nor-ct\">" + ctlwname + "</span>";
            rtn += "<span class=\"f-nor-ct\">" + getXpathValue(e, "./tag:info/tag:releaseinfo[@role='来源']") + "</span>";
            //lwjs++;
        } else if (ctname.equals("课题词条")) {
            List<Element> listltmp = e.selectNodes("./tag:info/tag:releaseinfo");
            for (Element listtmp : listltmp) {
                rtn += "<span class=\"f-nor-ct\">" + listtmp.attribute("role").getValue() + "：" + listtmp.getStringValue().replace("\"", "\\\"") + "</span>" + "&emsp;";
            }
        } else if (ctname.equals("大事记词条")) {
            rtn += "<span class=\"f-nor-ct\">" + getXpathValue(e, "./tag:info/tag:releaseinfo[@role='时间']") + "</span>";
            List<Element> listltmp = e.selectNodes("./tag:info/tag:releaseinfo[@role='简介记事']");
            for (Element listtmp : listltmp) {
                rtn += "<span class=\"f-nor-ct\">" + listtmp.getStringValue() + "</span>";
            }
        }
        rtn += "</p>\n";

        return rtn;
    }

    @SuppressWarnings("unchecked")
    public static boolean isHavePic(Element e) {
        List<Element> le1 = e.elements();
        for (Element e1 : le1) {
            if (e1.getName().equals("mediaobject")) {
                return true;
            } else {
            }
        }
        return false;
    }

    public static void putdata(String table, Map<String, Object> map) {
        JDBConnect db = new JDBConnect();
        String sql = "insert into " + table + "(";
        String sql1 = "";
        String sql2 = "";
        for (Entry<String, Object> entry : map.entrySet()) {
            sql1 += entry.getKey() + ",";
            sql2 += "'" + Util.toStr(entry.getValue()).replace("'", "''") + "',";
        }
        sql1 = sql1.substring(0, sql1.length() - 1);
        sql2 = sql2.substring(0, sql2.length() - 1);
        sql = sql + sql1 + " ) values ( " + sql2 + ");";
        db.insert(sql);
        setMapLog(table);
        if (!Util.toStr(map.get("title")).equals("")) {
            //System.out.println("取出片段: [" +  map.get("zid") + "]" + map.get("title"));
        }
    }

//    public static void trans(String sql){
//        dsql.append(sql);
//        //JDBConnect db = new JDBConnect();
//        if (dsql != null && dsql.length() > 10000) {
//            System.out.println(dsql.length());
//            //System.out.println(dsql.toString());
//            //db.insertTran(dsql.toString());
//            dsql.delete(0,dsql.length());
//        }
//    }

    public static void getWholeBookContent() {
        Map<String, Object> DBMap = new HashMap<>();
        DBMap.put("bookcode", bookcode);
        DBMap.put("TextContent", classify/*wholeContent.toString().replace("'", "''")*/);
        putdata("book_content", DBMap);
        jishi("书籍标记完成");
    }

    /**
     * Document忽略异常获取值
     *
     * @param document
     * @param xpath
     * @param type
     * @return
     */
    public static String getXpathValue(Document document, String xpath, String type) {
        String str = "";
        try {
            if (type.equals("")) {
                str = document.selectSingleNode(xpath).getText();
            } else {
                str = ((Element) document.selectSingleNode(xpath)).attribute(type).getValue();
            }
        } catch (Exception err) {
            Util.log("----------------------------", bookcode + "-" + "war", 1);
            StringWriter sw = new StringWriter();
            err.printStackTrace(new PrintWriter(sw, true));
            Util.log(sw.toString(), bookcode + "-" + "war", 1);
            Util.log(xpath.replace("tag:", ""), bookcode + "-" + "war", 1);
            return "";
        }
        return str;
    }

    /**
     * 获取xml节点值
     *
     * @param element
     * @param path
     * @return
     */
    public static String getXpathValue(Element element, String path) {
        String rtn = "";
        try {
            Node node = element.selectSingleNode(path);
            rtn = node.getText();
        } catch (Exception err) {
            if (!path.equals("./@role")) {
                Util.log("----------------------------", bookcode + "-" + "war", 1);
                StringWriter sw = new StringWriter();
                err.printStackTrace(new PrintWriter(sw, true));
                Util.log(sw.toString(), bookcode + "-" + "war", 1);
                Util.log(path.replace("tag:", ""), bookcode + "-" + "war", 1);
            }
            return "";
        }
        return rtn;
    }

    @SuppressWarnings("unchecked")
    public static void getMenu(Document document) {
        Map<String, Object> DBMap = new HashMap<>();
        DBMap.put("bookcode", bookcode);
        DBMap.put("bookname", bookname);
        DBMap.put("bookyear", year);
        DBMap.put("classify", "ml");

        List<Element> tag = document.selectNodes("/tag:book/tag:toc[@role='zh-Hans']/tag:tocdiv");
        for (Element e1 : tag) {
            DBMap.put("depth", "1");
            DBMap.put("fid", "0");
            DBMap.put("zid", e1.element("tocentry").attribute("href").getValue());
            DBMap.put("entry", "目录");
            DBMap.put("title", e1.element("title").getStringValue());
            DBMap.put("keyword", CKMUtil.getKeyword(e1.element("title").getStringValue(), 5));
            DBMap.put("bookpage", e1.element("tocentry").attribute("pagenum").getValue());
            // 标记书的前言后记标记
            String idindex = document.selectSingleNode("//tag:*[@xml:id='" + DBMap.get("zid") + "']").getName();
            if (idindex.equals("dedication") || idindex.equals("index")) {
                DBMap.put("depth", "0");
            }
            putdata("book_details", DBMap);
            setMapLog("book_details.menu");
            List<Element> le2 = e1.elements();
            for (Element e2 : le2) {
                if (e2.getName().equals("tocdiv")) {
                    DBMap.put("depth", "2");
                    DBMap.put("fid", e1.element("tocentry").attribute("href").getValue());
                    DBMap.put("zid", e2.element("tocentry").attribute("href").getValue());
                    DBMap.put("title", e2.element("title").getStringValue());
                    DBMap.put("keyword", CKMUtil.getKeyword(e2.element("title").getStringValue(), 5));
                    DBMap.put("bookpage", e2.element("tocentry").attribute("pagenum").getValue());
                    putdata("book_details", DBMap);
                    setMapLog("book_details.menu");
                }
            }
        }
        jishi("获取书籍目录完成");
    }

    @SuppressWarnings("unchecked")
    public static void getSect(Element e, Map<String, Object> DBMap) {
        DBMap.put("zid", getNodeValse(e.attribute("id")).equals("") ? getUUID() : getNodeValse(e.attribute("id")));
        DBMap.put("title", getNodeValse(e.element("title")));
        DBMap.put("content_pdf", "");
        DBMap.put("pdfpage", getXpathValue(e, "./tag:info/tag:pagenums[@role='pdfpage']"));
        DBMap.put("bookpage", getXpathValue(e, "./tag:info/tag:pagenums[@role='bookpage']"));
        DBMap.put("entry", e.attribute("role").getValue());
        DBMap.put("people", "");
        DBMap.put("source", "");
        DBMap.put("exarea", "");
        DBMap.put("zid_title", f3);
        DBMap.remove("exdate");
        String TextContent = "";
        String HtmlContent = "";
        // 是否拼接para内容
        boolean ispara = true;
        Map<String, Object> mapJson = new HashMap<>();
        String ctname = e.attribute("role").getValue();
        if (ctname.equals("图书词条")) {
            List<Element> listltmp1 = e.selectNodes("./tag:info/tag:authorgroup/tag:author");
            String lwnames = "";
            for (Element listtmp : listltmp1) {
                lwnames += listtmp.element("personname").getStringValue() + ";";
            }
            // DBMap.put("people", getXpathValue(e, "./tag:info/tag:authorgroup/tag:author/tag:personname").replace("、", ";"));
            DBMap.put("people", lwnames);
            DBMap.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='来源']"));
            mapJson.put("personname", getXpathValue(e, "./tag:info/tag:authorgroup/tag:author/tag:personname").replace("、", ";"));
            mapJson.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='来源']"));
            TextContent += "|" + DBMap.get("people") + "|" + DBMap.get("source");
            tsjs++;
        } else if (ctname.equals("人物词条")) {
            DBMap.put("people", DBMap.get("title"));
            DBMap.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='人物单位']"));
            DBMap.put("depth", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='名次']"));
            mapJson.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='人物单位']"));
            TextContent += "|" + DBMap.get("people") + "|" + DBMap.get("source");
            List<Element> sectList = e.selectNodes("./tag:para");
            for (Element sectPara : sectList) {
                HtmlContent += setp(sectPara, "getPContentOnly", null);
            }
            ispara = false;
            rwjs++;
        } else if (ctname.equals("论文词条")) {

            List<Element> listltmp = e.selectNodes("./tag:info/tag:authorgroup/tag:author");
            String lwname = "";
            String lwblurb = "";
            for (Element listtmp : listltmp) {
                lwname += listtmp.element("personname").getStringValue() + ";";
                lwblurb += getXpathValue(listtmp, "./tag:personblurb/tag:para");
            }
            if (lwname.endsWith(";")) {
                lwname = lwname.substring(0, lwname.length() - 1);
            }
            DBMap.put("people", lwname);
            DBMap.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='来源']"));
            mapJson.put("personname", lwname);
            mapJson.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='来源']"));

            String canz = "";
            if (e.attribute("href") != null && !e.attribute("href").getValue().equals("")) {
                canz = e.attribute("href").getValue();
            }
            if (!canz.equals("")) {
                List<Element> tag = e.selectNodes("//tag:*[@xml:id='" + canz + "']/tag:info/tag:authorgroup/tag:author/tag:personblurb/tag:para");
                tag = e.selectNodes("//tag:*[@xml:id='" + canz + "']/tag:para");
                for (Element para : tag) {
                    HtmlContent += setp(para, Util.toStr(DBMap.get("fid")), null);
                }
            }
            TextContent += "|" + DBMap.get("people") + "|" + DBMap.get("source");
            ispara = false;
            lwjs++;
        } else if (ctname.equals("课题词条")) {
            List<Element> listltmp = e.selectNodes("./tag:info/tag:releaseinfo");
            for (Element listtmp : listltmp) {
                mapJson.put(listtmp.attribute("role").getValue(), listtmp.getStringValue().replace("\"", "\\\""));
                TextContent += "|" + listtmp.getStringValue().replace("\"", "\\\"");
            }
            if (mapJson.containsKey("成果形式")) {
                DBMap.put("source", "结项");
                mapJson.put("项目区分", "结项");
            } else {
                DBMap.put("source", "立项");
                mapJson.put("项目区分", "立项");
            }
            ktjs++;
        } else if (ctname.equals("机构词条")) {
            if (e.attribute("type").getValue().equals("1")) {
                DBMap.put("title", Util.rmNumIndex(e.attribute("xreflabel").getValue()));
                DBMap.put("abs", getXpathValue(e, "./tag:para[@role='机构简介']"));
                HtmlContent += "<p>" + Util.rmNumIndex(Util.rmHTMLTag(getNodeValse(e.element("title")))) + "</p>";
                TextContent += "|" + Util.rmHTMLTag(HtmlContent);
            } else if (e.attribute("type").getValue().equals("2")) {
                DBMap.put("title", "其他");
                DBMap.put("abs", getXpathValue(e, "./tag:para[@role='机构简介']"));
                HtmlContent += "<p>" + Util.rmNumIndex(Util.rmHTMLTag(getNodeValse(e.element("title")))) + "</p>";
                TextContent += "|" + Util.rmHTMLTag(HtmlContent);
            } else if (e.attribute("type").getValue().equals("3")) {
                List<Element> listltmp = e.selectNodes("./tag:para[@role='机构名称']");
                for (Element listtmp : listltmp) {
                    HtmlContent += "<p>" + Util.rmNumIndex(listtmp.getStringValue()) + "</p>";
                    TextContent += "|" + Util.rmHTMLTag(HtmlContent);
                }
            }
            DBMap.put("source", e.attribute("type").getValue());
            ispara = false;
            jgjs++;
        } else if (ctname.equals("会议词条")) {
            DBMap.put("exdate", e.selectSingleNode("./tag:info/tag:releaseinfo[@role='时间']").getText());
            List<Element> sectList = e.selectNodes("./tag:para");
            for (Element sectPara : sectList) {
                HtmlContent += setp(sectPara, "getPContentOnly", null);
            }
            String zone = e.selectSingleNode("./tag:info/tag:releaseinfo[@role='地点']").getText();
            TextContent += "|" + DBMap.get("exdate") + "|" + Util.rmHTMLTag(HtmlContent);
            String rtZone = Util.toStr(Util.getProv(zone), Util.getProv(Util.toStr(DBMap.get("title"))));
            rtZone = Util.toStr(Util.getProv(rtZone), Util.toStr(CKMUtil.getZone(zone + zone), CKMUtil.getZone(Util.toStr(DBMap.get("title")) + Util.toStr(DBMap.get("title")))));
            rtZone = Util.toStr(Util.getProv(rtZone), CKMUtil.getZone(TextContent));
            rtZone = Util.getProv(rtZone);
//            if(rtZone.equals("")){
//                System.out.println("1-" + zone);
//                System.out.println("2-" + DBMap.get("title"));
//                System.out.println("3-" + TextContent);
//            }
            DBMap.put("exarea", rtZone);
            ispara = false;
            hyjs++;
        } else if (ctname.equals("大事记词条")) {
            if (Util.toStr(DBMap.get("title")).equals("") || Util.toStr(DBMap.get("title")).equals("　")) {
                DBMap.put("title", "大事记");
            }
            DBMap.put("exdate", e.selectSingleNode("./tag:info/tag:releaseinfo[@role='时间']").getText());
            List<Element> listltmp = e.selectNodes("./tag:info/tag:releaseinfo[@role='简介记事']");
            for (Element listtmp : listltmp) {
                HtmlContent += "<p>" + listtmp.getStringValue() + "</p>";
            }
            TextContent += "|" + DBMap.get("exdate") + "|" + Util.rmHTMLTag(HtmlContent);
            ispara = false;
            dsjjs++;
        }

        DBMap.put("exdata", Util.mapToJson(mapJson));

        if (ispara) {
            List<Element> sectList = e.selectNodes("./tag:para");
            for (Element sectPara : sectList) {
                HtmlContent += setp(sectPara, fid, null);
                TextContent += "|" + Util.rmHTMLTag(HtmlContent);
            }
        }
        DBMap.put("HtmlContent", HtmlContent);
        DBMap.put("TextContent", TextContent);
        putdata("book_details", DBMap);
        setMapLog("book_details.entry");
    }

    public static void setMapLog(String key) {
        int count = Util.toInt(mapLog.get(key), 0) + 1;
        mapLog.put(key, new Integer(count));
    }

    public static void updateAbb() {
        JDBConnect db = new JDBConnect();
        String selSql = "select count(*) as counts from book where bookabbreviate='" + bookAbbreviate + "'";
        List<Map<String, Object>> list = db.select(selSql);
        int counts = Util.toInt(list.get(0).get("counts"), 4);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time = formatter.format(new Date());
        String updSql = "update book_classify set counts='" + counts + "', mendtime='" + time
                + "' where bookabbreviate='" + bookAbbreviate + "'";
        try {
            db.update(updSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void theLast() {
        for (Entry<String, Integer> entry : mapLog.entrySet()) {
            Util.log("登录数据[" + entry.getKey() + "]" + entry.getValue() + "条", bookcode, 0);
        }
        Util.log("完成全书解析用时:" + Util.taste(startTime, tempTime), bookcode, 0);
    }

    public static void jishi(String info) {
        long temp = System.currentTimeMillis();
        String in = Util.taste(tempTime, temp);
        Util.log(info + "用时:" + in, bookcode, 0);
        tempTime = temp;
    }

    public static void getChapterCiTiao(Document document) {
        Map<String, Object> DBMap = new HashMap<>();
        DBMap.put("bookcode", bookcode);
        DBMap.put("bookname", bookname);
        DBMap.put("bookyear", year);
        DBMap.put("classify", "wz");
        // ID和标题组合
        String id_title_base = bookcode + f1 + booknameFull + year;
        List<Element> tag = document.selectNodes("/tag:book/tag:part");
        for (Element element : tag) {
            String fid = element.attribute("id").getValue();
            String zid = "";
            DBMap.put("zid", fid);
            DBMap.put("fid", "0");
            String id_title = "";
            id_title = id_title_base + f2 + fid + f1 + getXpathValue(element, "./tag:title");
            DBMap.put("zid_title", id_title);
            List<Element> le1 = element.elements();

            // 循环book/part中的子标签
            for (Element e1 : le1) {
                String authorStr = "";
                // 循环book/part/chapter
                if (e1.getName().equals("chapter")) {
                    //String lwct = e1.attribute("role").getValue();
                    zid = e1.attribute("id").getValue();
                    List<Element> le2 = e1.elements();
                    for (Element e2 : le2) {

                        if (e2.getName().equals("title")) {

                            // if (e2.getText().endsWith("综述") || getXpathValue(e1, "./@role").equals("综述词条") || getXpathValue(e1, "./@role").equals("") || getXpathValue(e1, "./@role") == null) {
                            if (getXpathValue(e1, "./@role").equals("综述词条") || getXpathValue(e1, "./@role").equals("") || getXpathValue(e1, "./@role") == null) {
                                break;
                            }
                            if (list.indexOf(zid) == -1) {
                                getChapterDetails(e1, DBMap);
                            }
                            list.add(zid);

                        }
                    }

                }
            }
        }
        jishi("获取文章词条内容完成");
    }

    public static void getChapterDetails(Element e, Map<String, Object> DBMap) {
        DBMap.put("zid", getNodeValse(e.attribute("id")).equals("") ? getUUID() : getNodeValse(e.attribute("id")));
        DBMap.put("title", getNodeValse(e.element("title")));
        DBMap.put("content_pdf", "");
        DBMap.put("pdfpage", getXpathValue(e, "./tag:info/tag:pagenums[@role='pdfpage']"));
        DBMap.put("bookpage", getXpathValue(e, "./tag:info/tag:pagenums[@role='bookpage']"));
        DBMap.put("entry", getXpathValue(e, "./@role"));
        DBMap.put("people", "");
        DBMap.put("source", "");
        DBMap.put("exarea", "");
        DBMap.put("zid_title", f3);
        DBMap.remove("exdate");
        String TextContent = "";
        String HtmlContent = "";
        // 是否拼接para内容
        boolean ispara = true;
        Map<String, Object> mapJson = new HashMap<>();
        String ctname = getXpathValue(e, "./@role");
        if (ctname.equals("图书词条")) {
            DBMap.put("people", getXpathValue(e, "./tag:info/tag:authorgroup/tag:author/tag:personname").replace("、", ";"));

            DBMap.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='来源']"));

            mapJson.put("personname", getXpathValue(e, "./tag:info/tag:authorgroup/tag:author/tag:personname").replace("、", ";"));
            mapJson.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='来源']"));
            TextContent += "|" + DBMap.get("people") + "|" + DBMap.get("source");
            tsjs++;
        } else if (ctname.equals("人物词条")) {
            DBMap.put("people", DBMap.get("title"));
            DBMap.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='人物单位']"));
            DBMap.put("depth", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='名次']"));
            mapJson.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='人物单位']"));
            TextContent += "|" + DBMap.get("people") + "|" + DBMap.get("source");
            List<Element> sectList = e.selectNodes("./tag:para");
            for (Element sectPara : sectList) {
                HtmlContent += setp(sectPara, "getPContentOnly", null);
            }
            ispara = false;
            rwjs++;
        } else if (ctname.equals("论文词条")) {


            // if (list.indexOf(e.attribute("id").getValue()) == -1){

            List<Element> listltmp = e.selectNodes("./tag:info/tag:authorgroup/tag:author");
            String lwname = "";
            String lwblurb = "";
            for (Element listtmp : listltmp) {
                lwname += listtmp.element("personname").getStringValue() + ";";
                lwblurb += getXpathValue(listtmp, "./tag:personblurb/tag:para");
            }
            if (lwname.endsWith(";")) {
                lwname = lwname.substring(0, lwname.length() - 1);
            }
            //System.out.println("======>"+e.attribute("id").getValue());

            DBMap.put("people", lwname);
            DBMap.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='来源']"));
            mapJson.put("personname", lwname);
            mapJson.put("source", getXpathValue(e, "./tag:info/tag:releaseinfo[@role='来源']"));
            String canz = "";
            if (e.attribute("href") != null && !e.attribute("href").getValue().equals("")) {
                canz = e.attribute("href").getValue();
            }
            if (!canz.equals("")) {
                List<Element> tag = e.selectNodes("//tag:*[@xml:id='" + canz + "']/tag:info/tag:authorgroup/tag:author/tag:personblurb/tag:para");
                tag = e.selectNodes("//tag:*[@xml:id='" + canz + "']/tag:para");
                for (Element para : tag) {
                    HtmlContent += setp(para, Util.toStr(DBMap.get("fid")), null);
                }
            }
            TextContent += "|" + DBMap.get("people") + "|" + DBMap.get("source");
            ispara = false;
            lwjs++;

        } else if (ctname.equals("课题词条")) {
            List<Element> listltmp = e.selectNodes("./tag:info/tag:releaseinfo");
            for (Element listtmp : listltmp) {
                mapJson.put(listtmp.attribute("role").getValue(), listtmp.getStringValue().replace("\"", "\\\""));
                TextContent += "|" + listtmp.getStringValue().replace("\"", "\\\"");
            }
            if (mapJson.containsKey("成果形式")) {
                DBMap.put("source", "结项");
                mapJson.put("项目区分", "结项");
            } else {
                DBMap.put("source", "立项");
                mapJson.put("项目区分", "立项");
            }
            ktjs++;
        } else if (ctname.equals("机构词条")) {
            if (e.attribute("type").getValue().equals("1")) {
                DBMap.put("title", Util.rmNumIndex(e.attribute("xreflabel").getValue()));
                DBMap.put("abs", getXpathValue(e, "./tag:para[@role='机构简介']"));
                HtmlContent += "<p>" + Util.rmNumIndex(Util.rmHTMLTag(getNodeValse(e.element("title")))) + "</p>";
                TextContent += "|" + Util.rmHTMLTag(HtmlContent);
            } else if (e.attribute("type").getValue().equals("2")) {
                DBMap.put("title", "其他");
                DBMap.put("abs", getXpathValue(e, "./tag:para[@role='机构简介']"));
                HtmlContent += "<p>" + Util.rmNumIndex(Util.rmHTMLTag(getNodeValse(e.element("title")))) + "</p>";
                TextContent += "|" + Util.rmHTMLTag(HtmlContent);
            } else if (e.attribute("type").getValue().equals("3")) {
                List<Element> listltmp = e.selectNodes("./tag:para[@role='机构名称']");
                for (Element listtmp : listltmp) {
                    HtmlContent += "<p>" + Util.rmNumIndex(listtmp.getStringValue()) + "</p>";
                    TextContent += "|" + Util.rmHTMLTag(HtmlContent);
                }
            }
            DBMap.put("source", e.attribute("type").getValue());
            ispara = false;
            jgjs++;
        } else if (ctname.equals("会议词条")) {
            DBMap.put("exdate", e.selectSingleNode("./tag:info/tag:releaseinfo[@role='时间']").getText());
            List<Element> sectList = e.selectNodes("./tag:para");
            for (Element sectPara : sectList) {
                HtmlContent += setp(sectPara, "getPContentOnly", null);
            } 
            String zone = e.selectSingleNode("./tag:info/tag:releaseinfo[@role='地点']").getText();
            TextContent += "|" + DBMap.get("exdate") + "|" + Util.rmHTMLTag(HtmlContent);
            String rtZone = Util.toStr(Util.getProv(zone), Util.getProv(Util.toStr(DBMap.get("title"))));
            rtZone = Util.toStr(Util.getProv(rtZone), Util.toStr(CKMUtil.getZone(zone + zone), CKMUtil.getZone(Util.toStr(DBMap.get("title")) + Util.toStr(DBMap.get("title")))));
            rtZone = Util.toStr(Util.getProv(rtZone), CKMUtil.getZone(TextContent));
            rtZone = Util.getProv(rtZone);
            DBMap.put("exarea", rtZone);
            ispara = false;
            hyjs++;
        } else if (ctname.equals("大事记词条")) {
            if (Util.toStr(DBMap.get("title")).equals("") || Util.toStr(DBMap.get("title")).equals("　")) {
                DBMap.put("title", "大事记");
            }
            DBMap.put("exdate", e.selectSingleNode("./tag:info/tag:releaseinfo[@role='时间']").getText());
            List<Element> listltmp = e.selectNodes("./tag:info/tag:releaseinfo[@role='简介记事']");
            for (Element listtmp : listltmp) {
                HtmlContent += "<p>" + listtmp.getStringValue() + "</p>";
            }
            TextContent += "|" + DBMap.get("exdate") + "|" + Util.rmHTMLTag(HtmlContent);
            ispara = false;
            //dsjjs++;
        }

        DBMap.put("exdata", Util.mapToJson(mapJson));

        if (ispara) {
            List<Element> sectList = e.selectNodes("./tag:para");
            for (Element sectPara : sectList) {
                HtmlContent += setp(sectPara, fid, null);
                TextContent += "|" + Util.rmHTMLTag(HtmlContent);
            }
        }
        DBMap.put("HtmlContent", HtmlContent);
        DBMap.put("TextContent", TextContent);
        putdata("book_details", DBMap);
        setMapLog("book_details.entry");
    }

    public static Integer putXmlDataBig(String xmlpath, String Identification) {
        tsjs = 0;
        rwjs = 0;
        lwjs = 0;
        ktjs = 0;
        jgjs = 0;
        hyjs = 0;
        dsjjs = 0;
        String OS = System.getProperty("os.name").toLowerCase();
        startTime = System.currentTimeMillis();
        tempTime = startTime;
        list.clear();
        if (Util.toStr(xmlpath).equals("")) {
            return 1;
        } else {
            filepath = xmlpath.replace("\\", "/");
        }
        // 资源结构初期检查
        if (getBookClass(Identification) < 0) {
            Util.log("资源信息取得失败.", bookcode, 0);
            return 1;
        } else {
            deleteData();
        }
        jishi("删除既存数据完成");
        // 创建命名
        Map map = new HashMap();
        map.put("tag", "http://docbook.org/ns/docbook");
        SAXReader saxReader = new SAXReader();
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);

        try {
            Document document = saxReader.read(new File(filepath));
            String wholeStr = Util.rmHTMLTag(document.asXML().replace("\n", "").replace("\t", "").replace("续表", ""));
            try {
                String t = Util.toStr(wholeStr.length());
                Util.log("登录全书,约" + t.substring(0, t.length() - 3) + "万字", bookcode, 0);
            } catch (Exception e) {
                System.out.println(e);
                return 1;
            }
            if (wholeStr.length() > 17000000) {
                wholeStr = wholeStr.substring(0, 16000000);
            }
            wholeContent.append(wholeStr);
            jishi("将整本书读入内存完成");
            // -----------获取词条内容----------------
            getCitiao(document);

//            System.out.println("图书词条"+tsjs);
//            System.out.println("论文词条"+lwjs);
//            System.out.println("人物词条"+rwjs);
//            System.out.println("课题词条"+ktjs);
//            System.out.println("机构词条"+jgjs);
//            System.out.println("会议词条"+hyjs);
//            System.out.println("大事记词条"+dsjjs);
            // ------------获取整本书内容--------------
            getWholeBookContent();

            // ----------- 获取书基本信息--------------
            getBase(document);

            // ------------获取书籍目录----------------
            getMenu(document);

            // -----------获取文章内容----------------
            getWenxian(document);

            getChapterCiTiao(document);

            //临时文件log
            Util.logXml("- - - - - - - - - - 录入词条信息- - - - - - - - - - ", "examination", 0, 1);
            Util.logXml("图书词条：\t" + tsjs, "examination", 0, 1);
            Util.logXml("论文词条：\t" + lwjs, "examination", 0, 1);
            Util.logXml("人物词条：\t" + rwjs, "examination", 0, 1);
            Util.logXml("课题词条：\t" + ktjs, "examination", 0, 1);
            Util.logXml("机构词条：\t" + jgjs, "examination", 0, 1);
            Util.logXml("会议词条：\t" + hyjs, "examination", 0, 1);
            Util.logXml("大事记词条：\t" + dsjjs, "examination", 0, 1);
//            //永久文件log
            Util.logXml("- - - - - - - - - - 录入词条信息- - - - - - - - - - ", "permanentRecords", 0, 2);
            Util.logXml("图书词条：\t" + tsjs, "permanentRecords", 0, 2);
            Util.logXml("论文词条：\t" + lwjs, "permanentRecords", 0, 2);
            Util.logXml("人物词条：\t" + rwjs, "permanentRecords", 0, 2);
            Util.logXml("课题词条：\t" + ktjs, "permanentRecords", 0, 2);
            Util.logXml("机构词条：\t" + jgjs, "permanentRecords", 0, 2);
            Util.logXml("会议词条：\t" + hyjs, "permanentRecords", 0, 2);
            Util.logXml("大事记词条：\t" + dsjjs, "permanentRecords", 0, 2);

        } catch (Exception e) {
            Util.log("-------------" + classify + "---------------", "err", 0);
            Util.log(e.getMessage(), "err", 0);
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            Util.log(sw.toString(), "err", 0);
            e.printStackTrace();
            return 1;
        }

        // 更新书籍种类卷次数
        updateAbb();
        // 最终处理
        theLast();
        return 2;
    }
}
