package com.trs.service.impl;

import com.trs.core.util.Util;
import com.trs.mapper.ResourceManagerMapper;
import com.trs.model.Book;
import com.trs.model.BookDetails;
import com.trs.service.ResourceManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LiangYong
 * @create 2017/11/21-16:07
 */
@Service
public class ResourceManagerServiceImpl implements ResourceManagerService {
    @Resource
    private ResourceManagerMapper resourceManagerMapper;

    @Override
    public Map<String, Object> getAllBooks(Map<String, Object> map) {
        map.put("search", map.get("search") == null ? null : "%" + map.get("search").toString() + "%");
        List<Book> bookList = resourceManagerMapper.selectAllBook(map);
        List<Book> collect = bookList.stream().skip(Integer.valueOf(map.get("currPage").toString())).limit(Integer.valueOf(map.get("pageSize").toString())).collect(Collectors.toList());
        map.put("total", bookList.size());
        map.put("rows", collect);
        return map;
    }

    @Override
    public Book getBookById(String id) {
        Book book = resourceManagerMapper.selectBookById(id);
        return book;
    }

    @Override
    public Boolean setBookByBook(Book book, Map<String, Object> map) {
        Book selectBookById = resourceManagerMapper.selectBookById(Integer.toString(book.getId()));
        selectBookById.setTitle(book.getTitle());
        selectBookById.setTitle_en(book.getTitle_en());
        selectBookById.setAbs(book.getAbs());
        selectBookById.setAuthor(book.getAuthor());
        selectBookById.setCharCount(book.getCharCount());
        selectBookById.setIsbn(book.getIsbn());
        selectBookById.setPubdate(book.getPubdate());
        selectBookById.setPublisherName(book.getPublisherName());
        Boolean aBoolean = resourceManagerMapper.updateBook(selectBookById);
        return aBoolean;
    }

    @Override
    public Boolean deleteBooks(String[] bookCodes) {
        Boolean deleteBook = resourceManagerMapper.deleteBook(bookCodes);
        Boolean deleteBookAuthor = resourceManagerMapper.deleteBookAuthor(bookCodes);
        Boolean deleteBookContent = resourceManagerMapper.deleteBookContent(bookCodes);
        Boolean deleteBookDetails = resourceManagerMapper.deleteBookDetails(null, bookCodes);
        Boolean deleteBookFile = resourceManagerMapper.deleteBookFile(bookCodes);
        return deleteBook && deleteBookAuthor && deleteBookContent && deleteBookDetails && deleteBookFile;
    }

    @Override
    public Map<String, Object> getAllPersonEntry(Map<String, Object> map) {
        map.put("pageSize", Integer.valueOf(map.get("pageSize").toString()));
        map.put("currPage", Integer.valueOf(map.get("currPage").toString()));
        map.put("order", map.get("order"));
        map.put("search", map.get("search"));
        map.put("sort", map.get("sort"));
        map.put("entry", "人物词条");
        List<BookDetails> bookDetailsList = resourceManagerMapper.selectAllBookDetails(map);
        map.put("rows", bookDetailsList);
        map.put("total", resourceManagerMapper.selectBookDetailsCount(map));
        return map;
    }

    @Override
    public BookDetails getPersonEntry(String id, String bookcode) {
        BookDetails bookDetails = resourceManagerMapper.selectBookDetails(id, bookcode, "人物词条");
        String htmlContent = bookDetails.getHtmlContent();
        List<String> list = Arrays.asList(htmlContent.split("<.*?>"));
        List<String> collect = list.stream().parallel().filter(x -> !"".equals(x)).collect(Collectors.toList());
        String reduce = collect.stream().reduce("", (x, y) -> x + y + "\r\n");
        bookDetails.setHtmlContent(reduce);
        return bookDetails;
    }

    @Override
    public Boolean setPersonEntry(BookDetails bookDetails) {
        bookDetails.setEntry("人物词条");
        String htmlContent = bookDetails.getHtmlContent();
        String[] split = htmlContent.split("\r\n");
        String reduce = Arrays.stream(split).reduce("", (x, y) -> x + "<p>" + y + "</p>");
        bookDetails.setHtmlContent(reduce);
        Boolean aBoolean = resourceManagerMapper.updateBookDetails(bookDetails);
        return aBoolean;
    }

    @Override
    public Boolean delPersonEntry(String[] ids, String[] bookcodes) {
        Boolean aBoolean = resourceManagerMapper.deleteBookDetails(ids, bookcodes);
        return aBoolean;
    }

    @Override
    public Map<String, Object> getAllPaperEntry(Map<String, Object> map) {
        map.put("pageSize", Integer.valueOf(map.get("pageSize").toString()));
        map.put("currPage", Integer.valueOf(map.get("currPage").toString()));
        map.put("entry", "论文词条");
        List<BookDetails> bookDetailsList = resourceManagerMapper.selectAllBookDetails(map);
        map.put("rows", bookDetailsList);
        map.put("total", resourceManagerMapper.selectBookDetailsCount(map));
        return map;
    }

    @Override
    public BookDetails getPaperEntry(String id, String bookcode) {
        BookDetails bookDetails = resourceManagerMapper.selectBookDetails(id, bookcode, "论文词条");
        String people = bookDetails.getPeople().replaceAll(";", "/");
        bookDetails.setPeople(people);
        return bookDetails;
    }

    @Override
    public Boolean setPaperEntry(BookDetails bookDetails) {
        String people = bookDetails.getPeople().replaceAll("/", ";");
        bookDetails.setPeople(people);
        Boolean aBoolean = resourceManagerMapper.updateBookDetails(bookDetails);
        return aBoolean;
    }

    @Override
    public Boolean delPaperEntry(String[] ids, String[] bookcodes) {
        Boolean aBoolean = resourceManagerMapper.deleteBookDetails(ids, bookcodes);
        return aBoolean;
    }

    @Override
    public Map<String, Object> getAllTopicEntry(Map<String, Object> map) {
        map.put("pageSize", Integer.valueOf(map.get("pageSize").toString()));
        map.put("currPage", Integer.valueOf(map.get("currPage").toString()));
        map.put("entry", "课题词条");
        List<BookDetails> bookDetailsList = resourceManagerMapper.selectAllBookDetails(map);
        map.put("rows", bookDetailsList);
        map.put("total", resourceManagerMapper.selectBookDetailsCount(map));
        return map;
    }

    @Override
    public Map<String, Object> getTopicEntry(String id, String bookcode) {
        Map<String, Object> map = new HashMap<>();
        BookDetails bookDetails = resourceManagerMapper.selectBookDetails(id, bookcode, "课题词条");
        String exdata = bookDetails.getExdata();
        Map<String, Object> exdataMap = Util.jsonToMap(exdata);
        map.put("bookDetails", bookDetails);
        map.put("exdataMap", exdataMap);
        return map;
    }

    @Override
    public Boolean setTopicEntry(Map<String, Object> map) {
        BookDetails bookDetails = new BookDetails();
        bookDetails.setId(Integer.valueOf(map.get("id").toString().trim()));
        bookDetails.setBookcode(map.get("bookcode").toString());
        bookDetails.setTitle(map.get("title").toString());
        map.remove("id");
        map.remove("bookcode");
        map.remove("title");
        String mapToJson = Util.mapToJson(map);
        bookDetails.setExdata(mapToJson);
        StringBuffer stringBuffer = new StringBuffer("");
        for (Object o : map.values()) {
            stringBuffer.append("|" + o);
        }
        bookDetails.setTextContent(new String(stringBuffer));
        Boolean aBoolean = resourceManagerMapper.updateBookDetails(bookDetails);
        return aBoolean;
    }

    @Override
    public Map<String, Object> getAllBookEntry(Map<String, Object> map) {
        map.put("pageSize", Integer.valueOf(map.get("pageSize").toString()));
        map.put("currPage", Integer.valueOf(map.get("currPage").toString()));
        map.put("entry", "图书词条");
        List<BookDetails> bookDetailsList = resourceManagerMapper.selectAllBookDetails(map);
        map.put("rows", bookDetailsList);
        map.put("total", resourceManagerMapper.selectBookDetailsCount(map));
        return map;
    }

    @Override
    public BookDetails getBookEntry(String id, String bookcode) {
        BookDetails bookDetails = resourceManagerMapper.selectBookDetails(id, bookcode, "图书词条");
        return bookDetails;
    }

    @Override
    public Boolean setBookEntry(BookDetails bookDetails) {
        Boolean aBoolean = resourceManagerMapper.updateBookDetails(bookDetails);
        return aBoolean;
    }

    @Override
    public Boolean delBookEntry(String[] ids, String[] bookcodes) {
        Boolean aBoolean = resourceManagerMapper.deleteBookDetails(ids, bookcodes);
        return aBoolean;
    }

    @Override
    public Map<String, Object> getAllMeetingBookEntry(Map<String, Object> map) {
        map.put("pageSize", Integer.valueOf(map.get("pageSize").toString()));
        map.put("currPage", Integer.valueOf(map.get("currPage").toString()));
        map.put("entry", "会议词条");
        List<BookDetails> bookDetailsList = resourceManagerMapper.selectAllBookDetails(map);
        map.put("rows", bookDetailsList);
        map.put("total", resourceManagerMapper.selectBookDetailsCount(map));
        return map;
    }

    @Override
    public BookDetails getMeetingEntry(String id, String bookcode) {
        BookDetails bookDetails = resourceManagerMapper.selectBookDetails(id, bookcode, "会议词条");
        String htmlContent = bookDetails.getHtmlContent();
        List<String> list = Arrays.asList(htmlContent.split("<.*?>"));
        List<String> collect = list.stream().parallel().filter(x -> !"".equals(x)).collect(Collectors.toList());
        String reduce = collect.stream().reduce("", (x, y) -> x + y + "\r\n");
        bookDetails.setHtmlContent(reduce);
        return bookDetails;
    }

    @Override
    public Boolean setMeetingEntry(BookDetails bookDetails) {
        bookDetails.setEntry("会议词条");
        String htmlContent = bookDetails.getHtmlContent();
        String[] split = htmlContent.split("\r\n");
        String reduce = Arrays.stream(split).reduce("", (x, y) -> x + "<p>" + y + "</p>");
        bookDetails.setHtmlContent(reduce);
        Boolean aBoolean = resourceManagerMapper.updateBookDetails(bookDetails);
        return aBoolean;
    }

    @Override
    public Boolean delMeetingEntry(String[] ids, String[] bookcodes) {
        Boolean aBoolean = resourceManagerMapper.deleteBookDetails(ids, bookcodes);
        return aBoolean;
    }

    @Override
    public Map<String, Object> getAllMechanismEntry(Map<String, Object> map) {
        map.put("pageSize", Integer.valueOf(map.get("pageSize").toString()));
        map.put("currPage", Integer.valueOf(map.get("currPage").toString()));
        map.put("entry", "机构词条");
        List<BookDetails> bookDetailsList = resourceManagerMapper.selectAllBookDetails(map);
        if ("3".equals(map.get("state"))) {
            List<BookDetails> collect2 = bookDetailsList.stream().map((x) -> {
                x.setTextContent("");
                String htmlContent = x.getHtmlContent();
                List<String> list = Arrays.asList(htmlContent.split("<.*?>"));
                List<String> collect = list.stream().parallel().filter(y -> !"".equals(y)).collect(Collectors.toList());
                List<String> collect1 = collect.stream().limit(3).map((z) -> "<p>" + z + "</p>").collect(Collectors.toList());
                StringBuilder stringBuilder = new StringBuilder();
                for (Integer integer = 0; integer < collect1.size(); integer++) {
                    stringBuilder.append(collect1.get(integer));
                }
                x.setHtmlContent(stringBuilder + "(" + collect.size() + ")");
                return x;
            }).collect(Collectors.toList());
            map.put("rows", collect2);
            map.put("total", collect2.size());
        } else {
            map.put("rows", bookDetailsList);
            map.put("total", resourceManagerMapper.selectBookDetailsCount(map));
        }
        return map;
    }

    @Override
    public BookDetails getMechanismEntry(String id, String bookcode) {
        BookDetails bookDetails = resourceManagerMapper.selectBookDetails(id, bookcode, "机构词条");
        String htmlContent = bookDetails.getHtmlContent();
        List<String> list = Arrays.asList(htmlContent.split("<.*?>"));
        List<String> collect = list.stream().parallel().filter(x -> !"".equals(x)).collect(Collectors.toList());
        String reduce = collect.stream().reduce("", (x, y) -> x + y + "\r\n");
        bookDetails.setHtmlContent(reduce);
        return bookDetails;
    }

    @Override
    public Boolean setMechanismEntry(BookDetails bookDetails) {
        bookDetails.setEntry("机构词条");
        String htmlContent = bookDetails.getHtmlContent();
        String[] split = htmlContent.split("\r\n");
        String reduce = Arrays.stream(split).reduce("", (x, y) -> x + "<p>" + y + "</p>");
        bookDetails.setHtmlContent(reduce);
        Boolean aBoolean = resourceManagerMapper.updateBookDetails(bookDetails);
        return aBoolean;
    }

    @Override
    public Boolean delMechanismEntry(String[] ids, String[] bookcodes) {
        Boolean aBoolean = resourceManagerMapper.deleteBookDetails(ids, bookcodes);
        return aBoolean;
    }

    @Override
    public Map<String, Object> getAllMemorabiliayEntry(Map<String, Object> map) {
        map.put("pageSize", Integer.valueOf(map.get("pageSize").toString()));
        map.put("currPage", Integer.valueOf(map.get("currPage").toString()));
        map.put("order", map.get("order"));
        map.put("search", map.get("search"));
        map.put("sort", map.get("sort"));
        map.put("entry", "大事记词条");
        List<BookDetails> bookDetailsList = resourceManagerMapper.selectAllBookDetails(map);
        map.put("rows", bookDetailsList);
        map.put("total", resourceManagerMapper.selectBookDetailsCount(map));
        return map;
    }

    @Override
    public BookDetails getMemorabiliayEntry(String id, String bookcode) {
        BookDetails bookDetails = resourceManagerMapper.selectBookDetails(id, bookcode, "大事记词条");
        String htmlContent = bookDetails.getHtmlContent();
        List<String> list = Arrays.asList(htmlContent.split("<.*?>"));
        List<String> collect = list.stream().parallel().filter(x -> !"".equals(x)).collect(Collectors.toList());
        String reduce = collect.stream().reduce("", (x, y) -> x + y + "\r\n");
        bookDetails.setHtmlContent(reduce);
        return bookDetails;
    }

    @Override
    public Boolean setMemorabiliayEntry(BookDetails bookDetails) {
        String htmlContent = bookDetails.getHtmlContent();
        String[] split = htmlContent.split("\r\n");
        String reduce = Arrays.stream(split).reduce("", (x, y) -> x + "<p>" + y + "</p>");
        bookDetails.setHtmlContent(reduce);
        Boolean aBoolean = resourceManagerMapper.updateBookDetails(bookDetails);
        return aBoolean;
    }

    @Override
    public Boolean delMemorabiliayEntry(String[] ids, String[] bookcodes) {
        Boolean aBoolean = resourceManagerMapper.deleteBookDetails(ids, bookcodes);
        return aBoolean;
    }

}
