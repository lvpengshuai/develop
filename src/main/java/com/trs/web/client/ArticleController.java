package com.trs.web.client;

import com.trs.client.TRSException;
import com.trs.core.util.Config;
import com.trs.core.util.RequestUtils;
import com.trs.core.util.Util;
import com.trs.model.Author;
import com.trs.model.Book;
import com.trs.model.BookDetails;
import com.trs.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuxuecheng on 2017/9/6.
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController {

    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");
    @Resource
    private BookDetailsService bookDetailsService;

    @Resource
    private BookAuthorService authorService;

    @Resource
    private BookService bookService;

    @Resource
    private SearchService searchService;

    @Resource
    private EpubSrcService epubSrcService;

    /**
     * 收藏年鉴标题
     * @param request
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/articleInfo")
    public ModelAndView bookCollect(HttpServletRequest request, ModelMap modelMap) throws TRSException{
          /*判断是否显示目录标识*/
        int muluFlag = 1;
        RequestUtils requestUtils = new RequestUtils(request);
        BookDetails bookDetails = null;
        String zid = requestUtils.getParameterAsString("zid", "");
        String bookcode = requestUtils.getParameterAsString("bookcode", "");
//        /* 增加阅读量 */
//        if(!zid.equals("")) {
//            bookDetailsService.getBookListByZid(zid,bookcode);
//        }

        String titleIndex = requestUtils.getParameterAsString("titleIndex", "0");
        String[] zidGroup = zid.split(";");
        String[] bookcodeGroup = bookcode.split(";");
        muluFlag=zidGroup.length;
        modelMap.put("muluFlag",muluFlag);
        List<BookDetails> titleList = new ArrayList<BookDetails>();
        if (!zidGroup.equals("") && zidGroup.length > 0) {
            for (int i = 0; i < zidGroup.length; i++) {
                bookDetails = bookDetailsService.getBookDetailsByZid(zidGroup[i],bookcodeGroup[i]);
                BookDetails bookd = new BookDetails();
                bookd.setZid(zid);
                bookd.setBookcode(bookcode);
                bookd.setTitle(bookDetails.getTitle());
                bookd.setFid(bookDetails.getZid());
                titleList.add(bookd);
                    /*标题集合*/
                modelMap.put("titleList", titleList);
            }
            modelMap.put("titleIndex", titleIndex);
            bookDetails = bookDetailsService.getBookDetailsByZid(zidGroup[Integer.parseInt(titleIndex)],bookcodeGroup[Integer.parseInt(titleIndex)]);
              /* 增加阅读量 */
            if(!zid.equals("")) {
                bookDetailsService.getBookListByZid(zidGroup[Integer.parseInt(titleIndex)],bookcodeGroup[Integer.parseInt(titleIndex)]);
            }
            /*原书阅读*/
            if (bookDetails.getClassify().equals("wz")){
                String pdfpage = bookDetails.getPdfpage();
                boolean matches = pdfpage.contains("-");
                if(!pdfpage.equals("")&&matches){
                    String startPage = pdfpage.substring(0,pdfpage.lastIndexOf("-"));
                    String endPage = pdfpage.substring(pdfpage.lastIndexOf("-"));
                    modelMap.put("startPage",startPage);
                    modelMap.put("endPage",endPage);
                }else if (!pdfpage.equals("")){
                    modelMap.put("startPage",pdfpage);
                }

            }
                /*来源信息*/
            if (bookDetails.getZidTitle() != null && bookDetails.getZidTitle() != "") {
                String[] zipTitle = bookDetails.getZidTitle().split("<.,>");
                String[] zipTitle1 = zipTitle[0].split("<,.>");
                String[] writings = zipTitle[1].split("<,.>");
                modelMap.put("zipTitle1", zipTitle1[1]);
                modelMap.put("writings", writings[1]);
                modelMap.put("bookcode", zipTitle1[0]);
                modelMap.put("partcode", writings[0]);
            }
            //通过fid查询书籍作者信息
            // 作者简介
            String personblurb = "";
            if (bookDetails != null) {
                List<Author> bookAuthorList = authorService.getBookAuthorByFid(bookDetails.getZid(),bookcode);
                /*作者信息*/
                modelMap.put("bookAuthorList", bookAuthorList);
                for(Author author:bookAuthorList){
                    if(!Util.toStr(author.getPersonblurb()).equals("")){
                        personblurb += author.getPersonblurb() + "　";
                    }
                }
            }
            modelMap.put("personblurb", personblurb);
            /*显示在左边栏标题信息
            正文标题信息*/
            String cnTitle = Util.rmNumIndex(bookDetails.getTitle());
            modelMap.put("title", cnTitle);
            modelMap.put("epubZid", bookDetails.getZid());
            String  zid111=bookDetails.getZid();
//                //根据标题和bookcode查到原文阅读需要得src
//            String bookCode=bookDetails.getBookcode();
//            String asdadasdasd=bookDetails.getTitle();
//            List<EpubSrc> epSr=epubSrcService.selectByTitle(bookCode,bookDetails.getTitle());
//            String  src="";
//            if(epSr.size()!=0){
//                src=epSr.get(0).getHtmlSRC();
//            }
//            modelMap.put("src", src);
                /*文章信息*/
            if (bookDetails.getTextContent() != null && bookDetails.getTextContent() != "") {
                modelMap.put("TextContent", bookDetails.getTextContent());
            }
                /*正文信息*/
            if (bookDetails.getHtmlContent() != null && bookDetails.getHtmlContent() != "") {
                Map<String, String> bookInfoMap = new HashMap<String, String>();
                String flag;
                if (bookDetails.getHtmlContent().length() >= 1000) {
                    flag = "true";
                    String[] p = bookDetails.getHtmlContent().split("</p>");
                    StringBuffer stringBuffer = new StringBuffer();
                    StringBuffer stringBuffer1 = new StringBuffer();
                    for (int k = 0; k < p.length; k++) {
                        if ((stringBuffer.toString().toCharArray().length) < 1000) {
                            stringBuffer.append(p[k] + "</p>");

                        } else {
                            stringBuffer1.append(p[k] + "</p>");
                        }
                    }
                    String showContextInfo = stringBuffer.toString();
                    bookInfoMap.put("HtmlContent", showContextInfo);
                    String endContextInfo = stringBuffer1.toString();
                    bookInfoMap.put("endContextInfo", endContextInfo);
                } else {
                    flag = "false";
                    bookInfoMap.put("HtmlContent", bookDetails.getHtmlContent());
                }
                bookInfoMap.put("flag", flag);
                modelMap.put("ContextInfo", bookInfoMap);
            }
                /*查询图书信息*/
            Book book = bookService.getBookByCode(bookDetails.getBookcode());
            modelMap.put("book", book);

                /*关键词信息*/
            ArrayList wordList = new ArrayList();
            if (bookDetails.getKeyword() != null && bookDetails.getKeyword() != "") {
                String[] keyword = bookDetails.getKeyword().split(";");
                int kflag = 0;
                if (keyword.length > 5) {
                    kflag = 5;
                } else {
                    kflag = keyword.length;
                }
                for (int j = 0; j < kflag; j++) {
                    wordList.add(keyword[j]);
                }
                /*相似文献查询*/
                String searchword="";
                for(int i=0; i<keyword.length && i<5; i++){
                    searchword+=keyword[i]+",";
                }
                List<Map<String, Object>> relatedData=searchService.getRelatedtData(Config.getKey("trs.server.search"),searchword).getTrsList();
                if(relatedData.size()!=0){
                    for(int j = 0 ; j < relatedData.size(); j++){
                        if(Util.toStr(relatedData.get(j).get("zid")).equals(zid) && Util.toStr(relatedData.get(j).get("bookcode")).equals(bookcode)){
                            relatedData.remove(j);
                        }
                    }
                    modelMap.put("relatedArticles",relatedData);
                }

            }
            modelMap.put("wordList", wordList);
        }
        modelMap.addAttribute(bookDetails);
        /*摘要信息*/
        modelMap.put("abs", bookDetails.getAbs());
        /*浏览次数*/
        modelMap.put("readCount", bookDetails.getReadCount());
        //引用文献
        List<String> footImport = Util.getFootImport(bookDetails.getHtmlContent());
        modelMap.put("footImport", footImport);
        modelMap.put("ONLINESTATUS", ONLINESTATUS);
        return new ModelAndView("client/book/bookdetail", modelMap);
    }
    @RequestMapping(value = "getDirectory")
    @ResponseBody
    public List<BookDetails> getDirectory(String fid, String bookcode){
        return bookDetailsService.getDirectoryByFid(fid,bookcode);
    }
}
