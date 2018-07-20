package com.trs.web.client;

import com.alibaba.fastjson.JSON;
import com.trs.client.TRSException;
import com.trs.core.util.Config;
import com.trs.core.util.GroupSort;
import com.trs.core.util.RequestUtils;
import com.trs.core.util.Util;
import com.trs.mapper.BookDetailsMapper;
import com.trs.model.Author;
import com.trs.model.Book;
import com.trs.model.BookDetails;
import com.trs.model.BookFile;
import com.trs.service.BookAuthorService;
import com.trs.service.BookDetailsService;
import com.trs.service.BookFileServices;
import com.trs.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.trs.core.util.DateUtil.getFisrtDayOfMonth;
import static com.trs.core.util.DateUtil.getLastDayOfMonth;

/**
 * Created by Administrator on 2017/8/24.
 */
@Controller
@RequestMapping(value = "book")
public class BookController {

    //在线状态
    private static final String ONLINESTATUS = Config.getKey("online");
    @Resource
    private BookDetailsService bookDetailsService;

    @Resource
    private BookFileServices bookFileServices;
    @Resource
    private BookAuthorService authorService;

    @Resource
    private BookService bookService;

    @Resource
    private BookDetailsMapper bookDetailsMapper;


    /**
     * 根据id查询单本年鉴详情页
     *
     * @param request
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/book")
    public ModelAndView book(HttpServletRequest request, ModelMap modelMap) throws TRSException {
        //查询单本年鉴详情
        RequestUtils requestUtils = new RequestUtils(request);
        String id = requestUtils.getParameterAsString("id", "");
        String bookcode = requestUtils.getParameterAsString("bookcode", "");
        String bookabbreviate = requestUtils.getParameterAsString("bookabbreviate", "");

        Book book = null;
        if (id != null && id.length() > 0) {
            book = bookService.getBookById(Integer.parseInt(id));
        }else if(bookcode!=null&&bookcode.length()>0) {
            book = bookService.getBookByCode(bookcode);
        }else if (bookabbreviate!=null&&bookabbreviate.length()>0){
            book = bookService.selectBookByBookabbreviate(bookabbreviate);
        }

        modelMap.put("book", book);
        /*研究热点*/
        BookDetails hotResearchParam = new BookDetails();
        hotResearchParam.setBookcode(book.getBookcode());
        List<BookDetails> hotResearchList = bookDetailsService.getBookDetailsList(hotResearchParam);
        List<String> keyWords = new ArrayList<>();
        for (int i = 0; i < hotResearchList.size(); i++) {
            BookDetails bookDetails = hotResearchList.get(i);
            if (bookDetails.getKeyword() != null) {
                String[] arr = bookDetails.getKeyword().split(";");
                for (String str : arr) {
                    keyWords.add(str);
                }
            }
        }
        Map<String, Integer> hotResearchMap = GroupSort.getCountSort(keyWords);
        StringBuilder hotResearchData = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, Integer> entry : hotResearchMap.entrySet()) {
            if (count == 15) {
                break;
            }
            hotResearchData.append(entry.getKey() + ";");
            count++;
        }
        modelMap.put("hotResearchData", hotResearchData.toString());
        /////////
        /*相关人物和相关机构*/
        modelMap.put("relatedPeopleData", book.getPeople());
        modelMap.put("relatedInstitutionData", book.getOrgan());
        /*同类型年鉴查询*/
        Book book1 = new Book();
        book1.setId(book.getId());//此条件用来排除当前年鉴
        book1.setBookAbbreviate(book.getBookAbbreviate());
        List<Book> bookList = bookService.getBookList(book1);
        modelMap.put("bookList", bookList);
        //按条件查询的一级目录集合
        BookDetails bookDetails = new BookDetails();
        bookDetails.setBookcode(book.getBookcode());
        bookDetails.setFid("0");
        bookDetails.setClassify("ml");
        List<BookDetails> bookDetailsList = bookDetailsService.getBookDetailsList(bookDetails);
        //一次性封装给前台的数据集合
        List<Object[]> bookDetailsListfinal = new ArrayList<Object[]>();
        for (int i = 0; i < bookDetailsList.size(); i++) {
            Object[] list1 = new Object[2];
            BookDetails bookDetails1 = bookDetailsList.get(i);
            if (!bookDetails1.getDepth().equals("0")) {
                list1[0] = bookDetails1;
                bookDetails.setBookcode(book.getBookcode());
                bookDetails.setFid(bookDetails1.getZid());
                bookDetails.setClassify("ml");
                List<BookDetails> list2 = bookDetailsService.getBookDetailsList(bookDetails);
                list1[1] = list2;
                bookDetailsListfinal.add(list1);
            }
        }
        modelMap.put("bookDetailsListfinal", bookDetailsListfinal);
        int aaa=0;
        if(bookDetailsListfinal.size()>0){
            aaa=1;
        }
        //一次性封装给前台的数据集合，封装完毕

        /*查询本书的热门文章*/
        List<BookDetails> hotArticles = bookDetailsService.getHotArticles(book.getBookcode());
        List<String> hotArticlesAuthors = new ArrayList<String>();
        List<String> hotArticlesFirstCatalogs = new ArrayList<String>();
        for (BookDetails bookDetail : hotArticles) {
            List<Author> authors = authorService.getBookAuthorByFid(bookDetail.getZid(),bookcode);
            if (authors != null && authors.size() != 0) {
                hotArticlesAuthors.add(authors.get(0).getPersonname());
            } else {
                hotArticlesAuthors.add("");
            }
            BookDetails bookDetails1 = null;
            try {
                bookDetails1 = bookDetailsService.getFirstCatalogByZid(bookDetail.getFid(), bookDetail.getBookcode());
            } catch (Exception e) {
                e.printStackTrace();
                bookDetails1 = null;
            }
            if (bookDetails1 != null) {
                hotArticlesFirstCatalogs.add(bookDetails1.getTitle());
            } else {
                hotArticlesFirstCatalogs.add(null);
            }
        }
        modelMap.put("hotArticles", hotArticles);
        if(hotArticles.size()>0){
            aaa=aaa+1;
        }
        modelMap.put("hotArticlesAuthors", hotArticlesAuthors);
        modelMap.put("hotArticlesFirstCatalogs", hotArticlesFirstCatalogs);
        //大事记
        List<BookDetails> htmlContent = bookDetailsService.getHtmlContent(book.getBookcode(), null);
        // 获取单本年鉴的所有大事记词条数据
        List<Map<String, Object>> listDsj = bookDetailsService.getDsjCitiao(htmlContent);
        modelMap.put("listDsj", listDsj);
        modelMap.put("htmlContent", htmlContent);
        Integer htmlContentNum = bookDetailsService.getHtmlContentNum(book.getBookcode());
        modelMap.put("htmlContentNum", htmlContentNum);
        if(htmlContentNum>0){
            aaa=aaa+1;
        }
        if (htmlContentNum>0){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
            String format = sdf.format(htmlContent.get(0).getExdate());
            modelMap.put("nowDate", format);
        }
        //综述\概况
        Integer bookDetailsAuthorNum = bookDetailsService.getBookDetailsAuthorNum(book.getBookcode());
        modelMap.put("bookDetailsAuthorNum", bookDetailsAuthorNum);
        if(bookDetailsAuthorNum>0){
            aaa=aaa+1;
        }

        //图表荟萃
        Integer bookFileNum = bookFileServices.getBookFileNum(book.getBookcode(), null);
        modelMap.put("bookFileNum", bookFileNum);
        if(bookFileNum>0){
            aaa=aaa+1;
        }

        //图书词条
        Integer entrysCount = bookDetailsService.getEntrysCount(book.getBookcode(), "图书词条");
        modelMap.put("entrysCount", entrysCount);
        if(entrysCount>0){
            aaa=aaa+1;
        }

        //人物词条
        Integer entrysCount1 = bookDetailsService.getEntrysCount(book.getBookcode(), "人物词条");
        modelMap.put("entrysCount1", entrysCount1);
        if(entrysCount1>0){
            aaa=aaa+1;
        }

        //最佳论文
        Integer bookDetailPaperCount = bookDetailsService.getBookDetailPaperCount(book.getBookcode());
        modelMap.put("bookDetailPaperCount", bookDetailPaperCount);
        if(bookDetailPaperCount>0){
            aaa=aaa+1;
        }

        //课题
        Integer bookDetailTopicNum = bookDetailsService.getBookDetailTopicNum(book.getBookcode(), null);
        modelMap.put("bookDetailTopicNum", bookDetailTopicNum);
        if(bookDetailTopicNum>0){
            aaa=aaa+1;
        }

        //机构
        Integer bookDetailsMechanismNum = bookDetailsService.getBookDetailsMechanismNum(book.getBookcode());
        modelMap.put("bookDetailsMechanismNum", bookDetailsMechanismNum);
        if(bookDetailsMechanismNum>0){
            aaa=aaa+1;
        }

        //会议词条
//        Integer bookDetailMapNum = bookDetailsService.getBookDetailMapNum(book.getBookcode());
        int year = Integer.parseInt(book.getBookcode().substring(book.getBookcode().length()-4))-1;
        int month = 0;
        String type = "会议词条";
        String startTime = getFisrtDayOfMonth(year, month);
        String endTime = getLastDayOfMonth(year, month);
        List<BookDetails> bookDetailList = bookDetailsMapper.getBookDetailMap(startTime, endTime, type, "", bookcode);
        modelMap.put("bookDetailMapNum", bookDetailList.size());
        if(bookDetailList.size()>0){
            aaa=aaa+1;
        }


        //获取会议词条标题列表
        List<BookDetails> bookDetailsMechanism = bookDetailsService.getBookDetailsMechanismTitle(book.getBookcode());
        modelMap.put("bookDetailsMechanism", bookDetailsMechanism);
        modelMap.put("bbtns", aaa);
        //  ONLINESTATUS
        modelMap.put("ONLINESTATUS", ONLINESTATUS);
        return new ModelAndView("client/book/book", modelMap);
    }

    /**
     * 同类年鉴分析
     *
     * @param request
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/analysis")
    public ModelAndView analysis(HttpServletRequest request, ModelMap modelMap) throws TRSException {
        //获取年鉴类型
        RequestUtils requestUtils = new RequestUtils(request);
        String id = requestUtils.getParameterAsString("id", "");
        String bookcode = requestUtils.getParameterAsString("bookcode", "");
        String bookAbbreviate = requestUtils.getParameterAsString("bookAbbreviate", "");
        Book book = null;
        if (id != null && id.length() > 0) {
            book = bookService.getBookById(Integer.parseInt(id));
        } else if (bookAbbreviate != null && bookAbbreviate.length() > 0) {
        } else {
            book = bookService.getBookByCode(bookcode);
        }
        /*同类型年鉴查询*/
        Book book1 = new Book();
        if (bookAbbreviate != null && bookAbbreviate.length() > 0) {
            book1.setBookAbbreviate(bookAbbreviate);
        } else {
            book1.setBookAbbreviate(book.getBookAbbreviate());
        }
        List<Book> bookList = bookService.getBookList(book1);
        modelMap.put("bookList", bookList);
        if (bookList != null && bookList.size() > 0) {
            Book _book = bookList.get(0);
            if (bookAbbreviate != null && bookAbbreviate.length() > 0) {
                book = bookList.get(0);
            }
            modelMap.put("book", book);
            modelMap.put("bookType", _book.getBookType());
            modelMap.put("title_en", _book.getTitle_en());
        }
        modelMap.put("bookAbbreviate", book.getBookAbbreviate());
        modelMap.put("bookcode", book.getBookcode());
        //一次性封装给前台的数据集合
        List list = new ArrayList();
        for (int i = 0; i < bookList.size(); i++) {
            List items1 = new ArrayList();
            list.add(items1);
            Book _book = bookList.get(i);
            items1.add(_book.getBookYear());
            List list1 = new ArrayList();
            items1.add(list1);
            //按条件查询的一级目录集合
            BookDetails paramForFirstCatalog = new BookDetails();
            paramForFirstCatalog.setBookcode(_book.getBookcode());
            paramForFirstCatalog.setFid("0");
            paramForFirstCatalog.setClassify("ml");
            List<BookDetails> firstCatalogs = bookDetailsService.getBookDetailsList(paramForFirstCatalog);
            for (int j = 0; j < firstCatalogs.size(); j++) {
                BookDetails firstCatalog = firstCatalogs.get(j);
                if (!firstCatalog.getBookpage().equals("0")) {
                    List items2 = new ArrayList();
                    list1.add(items2);
                    items2.add(firstCatalog);
                    /*查询二级目录*/
                    BookDetails paramForSecondCatalog = new BookDetails();
                    paramForSecondCatalog.setBookcode(firstCatalog.getBookcode());
                    paramForSecondCatalog.setFid(firstCatalog.getZid());
                    paramForSecondCatalog.setClassify("ml");
                    List<BookDetails> secondCatalogs = bookDetailsService.getBookDetailsList(paramForSecondCatalog);
                    items2.add(secondCatalogs);
                }
            }
        }
        modelMap.put("list", list);
        modelMap.put("listSize", list.size());
        modelMap.put("maxbookcode", bookList.get(0).getBookcode());
        //一次性封装给前台的数据集合，封装完毕
        return new ModelAndView("client/book/analysis", modelMap);
    }


    /**
     * 同类年鉴分析热词
     *
     * @param request
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/analysisHotword")
    @ResponseBody
    public Object analysisHotword(HttpServletRequest request, ModelMap modelMap) throws TRSException {
        //获取年鉴类型
        RequestUtils requestUtils = new RequestUtils(request);
        String bookAbbreviate = requestUtils.getParameterAsString("bookAbbreviate", "");
        String bookcode = requestUtils.getParameterAsString("bookcode", "");
        String type = requestUtils.getParameterAsString("type", "");

        /*同类型年鉴查询*/
        Book bookmsg = bookService.getBookByCode(bookcode);
        Map<String, Object> map = new HashMap<>();
        map = Util.jsonToMap(bookmsg.getMainmsg());

        String keywords = Util.toStr(map.get(type));
        String[] keyword1 = keywords.split(";");
        List<Map<String, Object>> list = new ArrayList<>();
        int count = 1000;
        for (String keyword1_1 : keyword1) {
            if (keyword1_1.indexOf("/") > -1) {
                Map<String, Object> mapData = new HashMap<>();
                mapData.put("name", keyword1_1.split("/")[0]);
                mapData.put("value", count);
                count = count - 50 > 400 ? count - 50 : 400;
                mapData.put("count", keyword1_1.split("/")[1]);
                list.add(mapData);
            }
        }
        String json = JSON.toJSONString(list);
        Map<String, Object> mapmsg = new HashMap<>();
        mapmsg.put("msg", json);
        return mapmsg;
    }

    /**
     * 按照类别请求书
     *
     * @param request
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/booksList")
    public ModelAndView booksList(HttpServletRequest request, ModelMap modelMap) throws TRSException {
        //得到年鉴类型
        String nj = request.getParameter("nj");
        //得到当前页数
        String page = request.getParameter("page");
        //是否热度排序
        String order = request.getParameter("order");
        //页面需要的list
        Integer pageContet = null;
        List<Book> bookList = null;
        List<Book> bookLists = null;
        List<Book> bookListCategory = null;
        if ("xklnj".equals(nj)) {
            modelMap.put("navigation", "学科类年鉴");
            modelMap.put("nj", "xklnj");
            bookList = bookService.getBooks("学科类年鉴", page, order);
            pageContet = bookService.getBooksNum("学科类年鉴");
            bookListCategory = bookService.getBookCategory("学科类年鉴");
        } else if ("xklnj".equals(nj)) {
            modelMap.put("navigation", "行业类年鉴");
            modelMap.put("nj", "hylnj");
            bookList = bookService.getBooks("行业类年鉴", page, order);
            pageContet = bookService.getBooksNum("行业类年鉴");
            bookListCategory = bookService.getBookCategory("行业类年鉴");
        } else if ("xklnj".equals(nj)) {
            modelMap.put("navigation", "统计类年鉴");
            modelMap.put("nj", "tjlnj");
            bookList = bookService.getBooks("统计类年鉴", page, order);
            pageContet = bookService.getBooksNum("统计类年鉴");
            bookListCategory = bookService.getBookCategory("统计类年鉴");
        } else if ("xklnj".equals(nj)) {
            modelMap.put("navigation", "学术史");
            modelMap.put("nj", "xslnj");
            bookList = bookService.getBooks("学术史", page, order);
            pageContet = bookService.getBooksNum("学术史");
            bookListCategory = bookService.getBookCategory("学术史");
        }else {
            bookList = bookService.getBooks(nj, page, order);
            pageContet = bookService.getBooksNum(nj);
            bookListCategory = bookService.getBookCategory(nj);
            modelMap.put("nj", "null");
        }


        //热门年鉴
        List<Book> booksByHot = bookService.getBooksByHot();
        //所有书籍
        List<Book> books = bookService.selectBooks();
        modelMap.put("booksByHot", booksByHot);
        modelMap.put("bookList", bookList);
        modelMap.put("PageIndex", (page == null) ? 1 : page);
        modelMap.put("PageCount", pageContet);
        modelMap.put("order", order);
        modelMap.put("bookListCategorys", bookListCategory);
        modelMap.put("books",books);
        return new ModelAndView("client/book/bookslist", modelMap);
    }

    /**
     * 大事记数据
     *
     * @return
     */
    @RequestMapping(value = "/memorabilia", method = RequestMethod.POST)
    @ResponseBody
    public List<BookDetails> memorabilia(String bookcode, String date) {
        List<BookDetails> htmlContent = bookDetailsService.getHtmlContent(bookcode, date);
        return htmlContent;
    }

    /**
     * 图表荟萃
     *
     * @return
     */
    @RequestMapping("/getBookFile18")
    @ResponseBody
    public List getBookFile18(String bookcode, String PageIndex, String currPage, String likeString) {
        //图表荟萃
        List<BookFile> bookFileByCodeAndTitle18 = bookFileServices.getBookFileByCodeAndTitle18(bookcode, (Integer.parseInt(PageIndex) - 1) * 22, likeString);
        getBookFileUtile2(bookFileByCodeAndTitle18);
        getBookFileUtil(bookFileByCodeAndTitle18, likeString);
        Integer bookFileNum = bookFileServices.getBookFileNum(bookcode, likeString);
        List<Object> list = new ArrayList<>();
        list.add(bookFileByCodeAndTitle18);
        list.add(bookFileNum % 22 > 1 ? bookFileNum / 22 + 1 : bookFileNum / 22);
        list.add(PageIndex);
        list.add(bookFileNum);
        return list;
    }


    /**
     * 图标荟萃
     * @param bookcode
     * @param PageIndex
     * @param currPage
     * @param likeString
     * @return
     */
    @RequestMapping("/getBookFile6")
    @ResponseBody
    public List getBookFile6(String bookcode, String PageIndex, String currPage, String likeString) {
        List<BookFile> bookFileByCodeAndTitle6 = bookFileServices.getBookFileByCodeAndTitle6(bookcode, (Integer.parseInt(PageIndex) - 1) * 6, likeString);
        getBookFileUtile2(bookFileByCodeAndTitle6);
        getBookFileUtil(bookFileByCodeAndTitle6, likeString);
        Integer bookFileNum = bookFileServices.getBookFileNum(bookcode, likeString);
        List<Object> list = new ArrayList<>();
        list.add(bookFileByCodeAndTitle6);
        list.add(bookFileNum % 6 > 1 ? bookFileNum / 6 + 1 : bookFileNum / 6);
        list.add(PageIndex);
        return list;
    }

    /**
     * 图表荟萃查询图表
     * @param bookFileList
     */
    private void getBookFileUtile2(List<BookFile> bookFileList) {
        for (BookFile b : bookFileList) {
            if (b.getTitle().startsWith("表") || b.getTitle().startsWith("图")) {
                int i = b.getTitle().lastIndexOf(" ");
                b.setTitle(b.getTitle().substring(i + 1));
            }
        }
    }

    /**
     *
     * @param bookFileList
     * @param likeString
     */
    private void getBookFileUtil(List<BookFile> bookFileList, String likeString) {
        if (likeString != "") {
            for (BookFile bookfile : bookFileList) {
                String bookfileTitle = bookfile.getTitle();
                int i = bookfileTitle.indexOf(likeString);
                String substring = bookfileTitle.substring(0, i);
                String substring1 = bookfileTitle.substring(i + likeString.length(), bookfileTitle.length());
                String sub = substring + "<font style='color:red'>" + likeString + "</font>" + substring1;
                bookfile.setTitle(sub);
            }
        }
    }

    /**
     * 图表荟萃
     *
     * @return
     */
    @RequestMapping("/getBookFileImage18")
    public String getBookFileImage18(String bookcode, Map map, String pageIndex, String likeString, String indexNum) {

        //图表荟萃
        List<BookFile> bookFileByCodeAndTitle18 = bookFileServices.getBookFileByCodeAndTitle18(bookcode, (Integer.parseInt(pageIndex) - 1) * 18, likeString);
        map.put("bookFileByCodeAndTitle18", bookFileByCodeAndTitle18);
        map.put("indexNum", Util.toInt(indexNum,1));
        return "client/book/slide01";
    }

    /**
     * 图表荟萃
     * @param bookcode
     * @param map
     * @param pageIndex
     * @param indexNum
     * @param likeString
     * @return
     */
    @RequestMapping("/getBookFileImage6")
    public String getBookFileImage6(String bookcode, Map map, String pageIndex, String indexNum, String likeString) {
        List<BookFile> bookFileByCodeAndTitle6 = bookFileServices.getBookFileByCodeAndTitle6(bookcode, (Integer.parseInt(pageIndex) - 1) * 6, likeString);
        map.put("bookFileByCodeAndTitle6", bookFileByCodeAndTitle6);
        map.put("indexNum", Util.toInt(indexNum,1));
        return "client/book/slide02";
    }

//    /**
//     * 中国地图
//     *
//     * @param request
//     * @param modelMap
//     * @return
//     * @throws TRSException
//     */
//    @RequestMapping(value = "/chinamap")
//    public ModelAndView chinamap(HttpServletRequest request, ModelMap modelMap) throws TRSException {
//        RequestUtils requestUtils = new RequestUtils(request);
//        int year = requestUtils.getParameterAsInt("year", 0);
//        int month = requestUtils.getParameterAsInt("month", 0);
//        String type = requestUtils.getParameterAsString("type", "");
//        String address = requestUtils.getParameterAsString("address", "");
//        String startTime = getFisrtDayOfMonth(year, month);
//        String endTime = getLastDayOfMonth(year, month);
//        List<BookDetails> bookDetailMap = bookDetailsService.getBookDetailMap(startTime, endTime, type, address);
//
//        ArrayList arrayList = new ArrayList();
//        ArrayList showList = new ArrayList();
//        for (int i = 0; i < bookDetailMap.size(); i++) {
//            HashMap valueMap = new HashMap();
//            HashMap showMap = new HashMap();
//            valueMap.put("name", bookDetailMap.get(i).getExarea());
//            valueMap.put("value", Math.round(Math.random() * 1000));
//            String[] split = bookDetailMap.get(i).getTextContent().split("。");
//            showMap.put(bookDetailMap.get(i).getExarea(), bookDetailMap.get(i).getTextContent().split("。")[0]);
//            arrayList.add(valueMap);
//            showList.add(showMap);
//        }
//        String s = JSONArray.toJSONString(arrayList);
//        System.out.println(s);
//        modelMap.put("valdata", s);
//        modelMap.put("showdata", JSONArray.toJSONString(showList));
//
//        return new ModelAndView("client/yearbooks/chinamap", modelMap);
//    }

    /**
     * 综述\概况
     *
     * @param bookcode
     * @return
     */
    @RequestMapping("/getDetailsAuthor")
    @ResponseBody
    public List getDetailsAuthor(String bookcode, String PageIndex, String currPage) {
        List<BookDetails> bookDetailsAuthor = bookDetailsService.getBookDetailsAuthor(bookcode, (Integer.parseInt(PageIndex) - 1) * 9);
        Integer bookDetailsAuthorNum = bookDetailsService.getBookDetailsAuthorNum(bookcode);
        List<Object> list = new ArrayList<>();
        list.add(bookDetailsAuthor);
        list.add(bookDetailsAuthorNum % 9 > 1 ? bookDetailsAuthorNum / 9 + 1 : bookDetailsAuthorNum / 9);
        list.add(PageIndex);
        return list;
    }

    /**
     * 图书词条
     *
     * @param bookcode
     * @param pageIndex
     * @return
     */
    @RequestMapping("/bookEntry")
    @ResponseBody
    public List getBookEntrys(String bookcode, Integer pageIndex) {
        List data = new ArrayList();
        data.add(bookDetailsService.getEntrys(bookcode, "图书词条", (pageIndex - 1) * 9, 9));
        data.add((bookDetailsService.getEntrysCount(bookcode, "图书词条") - 1) / 9 + 1);
        data.add(pageIndex);
        return data;
    }

    /**
     * 人物词条
     *
     * @param bookcode
     * @param pageIndex
     * @return
     */
    @RequestMapping("/peopleEntry")
    @ResponseBody
    public List getPeopleEntrys(String bookcode, Integer pageIndex) {
        List data = new ArrayList();
        data.add(bookDetailsService.getEntrys(bookcode, "人物词条", (pageIndex - 1) * 12, 12));
        data.add((bookDetailsService.getEntrysCount(bookcode, "人物词条") - 1) / 12 + 1);
        data.add(pageIndex);
        return data;
    }

    /**
     * 论文词条
     *
     * @param bookcode
     * @return
     */
    @RequestMapping("/getBookDetailPaper")
    @ResponseBody
    public List getBookDetailPaper(String bookcode, String PageIndex, String currPage) {
        List<BookDetails> bookDetailPaper = bookDetailsService.getBookDetailPaper(bookcode, PageIndex);
        Integer bookDetailPaperCount = bookDetailsService.getBookDetailPaperCount(bookcode);
        List<Object> list = new ArrayList<>();
        list.add(bookDetailPaper);
        list.add(bookDetailPaperCount % 9 > 1 ? bookDetailPaperCount / 9 + 1 : bookDetailPaperCount / 9);
        list.add(PageIndex);
        return list;
    }

    /**
     * 课题词条
     *
     * @param bookcode
     * @return
     */
    @RequestMapping("/getBookDetailTopic")
    @ResponseBody
    public List getBookDetailTopic(String bookcode, String PageIndex, String currPage, String source) {
        List<BookDetails> bookDetailTopic = bookDetailsService.getBookDetailTopic(bookcode, source, PageIndex, currPage);
        List<List> exdatas = new ArrayList<>();
        String type="";
        if ("立项".equals(source)) {
            List<String> list = new ArrayList<String>() {{
                add("编号");
                add("负责人");
                add("职称");
                add("机构");
                add("资助金额");
                add("立项资助");
            }};
            exdatas.addAll(getExdatasUtil(list, bookDetailTopic));
            type="立项课题";
        } else if ("结项".equals(source)) {
            List<String> list = new ArrayList<String>() {{
                add("编号");
                add("负责人");
                add("职称");
                add("单位");
                add("成果形式");
                add("成果名称");
                add("评级");
                add("立项时间");
                add("立项资助");
                add("备注");
            }};
            exdatas.addAll(getExdatasUtil(list, bookDetailTopic));
            type="结项课题";
        }
        Integer bookDetailTopicNum = bookDetailsService.getBookDetailTopicNum(bookcode, source);
        List<Object> list = new ArrayList<>();
        list.add(bookDetailTopic);
        list.add(bookDetailTopicNum % 4 > 1 ? bookDetailTopicNum / 4 + 1 : bookDetailTopicNum / 4);
        list.add(PageIndex);
        list.add(exdatas);
        list.add(type);
        return list;
    }

    /**
     *课题数据不存在替换为空
     * @param list
     * @param bookDetailsList
     * @return
     */
    public List getExdatasUtil(List<String> list, List<BookDetails> bookDetailsList) {
        List<List> exdatas = new ArrayList<>();
        for (int i = 0; i < bookDetailsList.size(); i++) {
            BookDetails bookDetails = bookDetailsList.get(i);
            String exdata = bookDetails.getExdata();
            Map<String, Object> stringObjectMap = Util.jsonToMap(exdata);
            List<String> listNew = new ArrayList<String>();
            for (int j = 0; j < list.size(); j++) {
                String s1 = list.get(j);
                String s2 = stringObjectMap.get(s1).toString();
                if ("".equals(s2)) {
                    listNew.add(s1 + "：（空）");
                } else {
                    listNew.add(s1 + "：" + s2);
                }
            }
            exdatas.add(listNew);
        }
        return exdatas;
    }

    /**
     * 机构词条
     *
     * @param bookcode
     * @return
     */
    @RequestMapping("/getBookDetailsMechanism")
    @ResponseBody
    public List getBookDetailsMechanism(String bookcode, String PageIndex, String currPage, String source, String sourceType,String titleType) {
        return bookDetailsService.getBookDetailsMechanism(bookcode, source,sourceType,titleType,PageIndex,currPage);
    }


    /**
     * 会议查询展示
     *
     * @param request
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/meeting")
    public ModelAndView meeting(HttpServletRequest request, ModelMap modelMap){
        RequestUtils requestUtils = new RequestUtils(request);
        int year = requestUtils.getParameterAsInt("year", 0) - 1;
        int month = requestUtils.getParameterAsInt("month", 0);
        String type = requestUtils.getParameterAsString("type", "");
        String address = requestUtils.getParameterAsString("address", "");
        String bookcode = requestUtils.getParameterAsString("bookcode", "");
        String startTime = getFisrtDayOfMonth(year, month);
        String endTime = getLastDayOfMonth(year, month);
        Map bookDetailMap = bookDetailsService.getBookDetailMap(startTime, endTime, type, address,bookcode);
        modelMap.put("bookList", bookDetailMap.get("resultList"));
        modelMap.put("bookListCount", ((List)bookDetailMap.get("resultList")).size());

        return new ModelAndView("client/book/meeting", modelMap);
    }
    /**
     * 热点事件
     *
     * @param request
     * @param modelMap
     * @return
     * @throws TRSException
     */
    @RequestMapping(value = "/hotevents")
    public ModelAndView hotevents(HttpServletRequest request, ModelMap modelMap){
        RequestUtils requestUtils = new RequestUtils(request);
        int year = requestUtils.getParameterAsInt("year", 0);
        int searchYear  = year-1;
        int month = requestUtils.getParameterAsInt("month", 0);
        String type = requestUtils.getParameterAsString("type", "会议词条");
        String address = requestUtils.getParameterAsString("address", "");
        String bookAbbreviate = requestUtils.getParameterAsString("bookAbbreviate", "");
        int pageIndex = requestUtils.getParameterAsInt("pageIndex", 1);
        int pageSize = requestUtils.getParameterAsInt("pageSize", 10);
        String startTime = getFisrtDayOfMonth(searchYear, month);
        String endTime = getLastDayOfMonth(searchYear, month);
        Map bookDetailMap = bookDetailsService.getBookDetailMap(startTime, endTime, type, address, pageSize, pageIndex,bookAbbreviate);
        modelMap.put("bookList", bookDetailMap.get("resultList"));
        modelMap.put("PageCount", bookDetailMap.get("pageCount"));
        modelMap.put("PageIndex", pageIndex);
        modelMap.put("PageSize", pageSize);
        modelMap.put("year", year);
        modelMap.put("bookAbbreviate",bookAbbreviate);
        return new ModelAndView("client/book/hotevents", modelMap);
    }
}


