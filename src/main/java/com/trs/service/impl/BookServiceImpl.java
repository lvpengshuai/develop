package com.trs.service.impl;

import com.trs.mapper.BookClassifyMapper;
import com.trs.mapper.BookMapper;
import com.trs.model.Book;
import com.trs.model.BookClassify;
import com.trs.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
@Service
public class BookServiceImpl implements BookService{

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BookClassifyMapper bookClassifyMapper;

    /**
     *
     * @param bookcode
     * @return
     */
    @Override
    public Book getBookByCode(String bookcode) {
        return bookMapper.getBookByCode(bookcode);
    }

    @Override
    public Book getBookById(int id) {
        return bookMapper.getBookById(id);
    }

    /**
     * 根据类别去查书
     *
     * @param bookClass
     * @return
     */
    @Override
    public List<Book> getBooksIndex(String bookClass) {
        return bookMapper.getBooksIndex(bookClass);
    }

    /**
     * 根据书的大分类去查询书
     * @param bookClass
     * @param page
     * @param order
     * @return
     */
    @Override
    public List<Book> getBooks(String bookClass,String page,String order) {
        Integer pageInt =(page==null)?0:Integer.parseInt(page);
        if (pageInt<=0){
            pageInt=0;
        }else {
            pageInt=(pageInt-1)*10;
        }
        return bookMapper.getBooks(bookClass,pageInt,order);
    }
    /**
     * 获取书的长度返回分页长度
     * @param bookClass
     * @return
     */
    @Override
    public Integer getBooksNum(String bookClass) {
        Integer booksNum = bookMapper.getBooksNum(bookClass);
        Integer i = booksNum / 10;
        Double y = (double) booksNum / 10;
        if (y>i){
            i++;
        }
        return i;
    }


    @Override
    public List<Book> getBookList(Book book) {
        return bookMapper.getBookList(book);
    }
    /**
     * 按热度查询书
     * @return
     */
    @Override
    public List<Book> getBooksByHot() {
        return bookMapper.getBooksByHot();
    }
    /**
     * 首页中间轮播
     * 按hottype查询
     * @return
     */
    @Override
    public List<Book> getBookByHotType() {
        return bookMapper.getBookByHotType();
    }

    @Override
    public List<Book> getBookCategory(String bookClass) {
        return bookMapper.getBookCategory(bookClass);
    }


    /**
     * 更新拼接
     * @return
     */
    public Map upBookEpub(Book book){
        Map resultMap = new HashMap();
        int  splices=bookMapper.upBookEpub(book);
        System.out.println("是什么=="+book);
        if(splices>0){
            resultMap.put("code", "0");
        }else{
            resultMap.put("code", "1");
        }
        return resultMap;
    }

    /**
     * 根据类别去查书
     *
     * @return
     */
    @Override
    public List<Book> selectBookEpub(String title) {
        return bookMapper.selectBookEpub(title);
    }

    @Override
    public List<Book> selectBooks() {
        return bookMapper.selectBooks();
    }

    @Override
    public List<BookClassify> selectBookClassify() {
        return bookClassifyMapper.getBookClassify();
    }

    @Override
    public Book selectBookByBookabbreviate(String bookabbreviate) {
        return bookMapper.selectBookByBookabbreviate(bookabbreviate);
    }

}
