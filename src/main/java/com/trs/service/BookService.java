package com.trs.service;

import com.trs.model.Book;
import com.trs.model.BookClassify;
import com.trs.model.BookDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
public interface BookService {

    /**
     *
     * @param bookcode
     * @return
     */
    public Book getBookByCode(String bookcode);

    /**
     *
     * @param id
     * @return
     */
    public Book getBookById(int id);

    /**
     * 根据类别去查书 首页用
     * @param bookClass
     * @return
     */
    public List<Book> getBooksIndex(String bookClass);

    /**
     * 根据书的大分类去查询书 年鉴列表
     * @param bookClass
     * @return
     */
    public List<Book> getBooks(String bookClass,String page,String order);


    /**
     * 获取书的长度
     * @param bookClass
     * @return
     */
    public Integer getBooksNum(String bookClass);
    /**
     *
     * @param book
     * @return
     */
    public List<Book> getBookList(Book book);
    /**
     * 按热度查询书
     * @return
     */
    public List<Book> getBooksByHot();

    /**
     * 首页中间轮播
     * 按hottype查询
     * @return
     */
    public List<Book> getBookByHotType();

    /**
     * 获取大类别的小类别
     * @return
     */
    public List<Book> getBookCategory(String bookClass);

    /**
     * 插入book表epub
     * @return
     */
    public Map upBookEpub(Book Book);


    /**
     * 查询bookcode
     * @return
     */
    public List<Book> selectBookEpub(String  title);

    /**
     * 查询所有书籍
     * @return
     */
    public List<Book> selectBooks();

    /**
     * 查询所有BookClassify数据
     * @return
     */
    public List<BookClassify> selectBookClassify();

    /**
     * 根据Bookabbreviate查询book
     * @return
     */
    public Book selectBookByBookabbreviate(String bookabbreviate);



}
