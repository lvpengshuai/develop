package com.trs.mapper;

import com.trs.model.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
public interface BookMapper {

    /**
     * @param bookcode
     * @return
     */
    public Book getBookByCode(String bookcode);

    /**
     * @param id
     * @return
     */
    public Book getBookById(int id);

    /**
     * 根据类别去查书
     *
     * @param bookClass
     * @return
     */
    public List<Book> getBooksIndex(String bookClass);

    /**
     * 根据书的大分类去查询书
     *
     * @param bookClass
     * @return
     */
    public List<Book> getBooks(@Param("bookClass") String bookClass, @Param("page") Integer page, @Param("order") String order);

    /**
     * 获取书的长度
     *
     * @param bookClass
     * @return
     */
    public Integer getBooksNum(String bookClass);

    /**
     * @param book
     * @return
     */
    public List<Book> getBookList(Book book);

    /**
     * 按热度查询书
     *
     * @return
     */
    public List<Book> getBooksByHot();

    /**
     * 首页中间轮播
     * 按hottype查询
     *
     * @return
     */
    public List<Book> getBookByHotType();

    public List<Book> getBookCategory(String bookClass);


    /**
     *  更新book表得epub
     * @return
     */
    int  upBookEpub(Book book);


    /**
     *查询bookcode
     * @return
     */
    public List<Book> selectBookEpub(String title);

    /**
     * 查询所有书籍
     * @return
     */
    public List<Book> selectBooks();

    /**
     * 根据Bookabbreviate查询book
     * @return
     */
    public Book selectBookByBookabbreviate(String bookabbreviate);

}
