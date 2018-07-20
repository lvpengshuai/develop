package com.trs.service;

import com.trs.model.Book;
import com.trs.model.BookDetails;

import java.util.Map;

/**
 * @author LiangYong
 * @create 2017/11/21-16:03
 */
public interface ResourceManagerService {
    public Map<String, Object> getAllBooks(Map<String, Object> map);

    public Book getBookById(String id);

    public Boolean setBookByBook(Book book, Map<String, Object> map);

    public Boolean deleteBooks(String[] bookCodes);

    public Map<String, Object> getAllPersonEntry(Map<String, Object> map);

    public BookDetails getPersonEntry(String id, String bookcode);

    public Boolean setPersonEntry(BookDetails bookDetails);

    public Boolean delPersonEntry(String[] ids, String[] bookcodes);

    public Map<String, Object> getAllPaperEntry(Map<String, Object> map);

    public BookDetails getPaperEntry(String id, String bookcode);

    public Boolean setPaperEntry(BookDetails bookDetails);

    public Boolean delPaperEntry(String[] ids, String[] bookcodes);

    public Map<String, Object> getAllTopicEntry(Map<String, Object> map);

    public Map<String, Object> getTopicEntry(String id, String bookcode);

    public Boolean setTopicEntry(Map<String, Object> map);

    public Map<String, Object> getAllBookEntry(Map<String, Object> map);

    public BookDetails getBookEntry(String id, String bookcode);

    public Boolean setBookEntry(BookDetails bookDetails);

    public Boolean delBookEntry(String[] ids, String[] bookcodes);

    public Map<String, Object> getAllMeetingBookEntry(Map<String, Object> map);

    public BookDetails getMeetingEntry(String id, String bookcode);

    public Boolean setMeetingEntry(BookDetails bookDetails);

    public Boolean delMeetingEntry(String[] ids, String[] bookcodes);

    public Map<String, Object> getAllMechanismEntry(Map<String, Object> map);

    public BookDetails getMechanismEntry(String id, String bookcode);

    public Boolean setMechanismEntry(BookDetails bookDetails);

    public Boolean delMechanismEntry(String[] ids, String[] bookcodes);

    public Map<String, Object> getAllMemorabiliayEntry(Map<String, Object> map);

    public BookDetails getMemorabiliayEntry(String id, String bookcode);

    public Boolean setMemorabiliayEntry(BookDetails bookDetails);

    public Boolean delMemorabiliayEntry(String[] ids, String[] bookcodes);
}
