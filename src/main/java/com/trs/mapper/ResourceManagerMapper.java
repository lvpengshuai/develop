package com.trs.mapper;

import com.trs.model.Book;
import com.trs.model.BookDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author LiangYong
 * @create 2017/11/21-15:59
 */
public interface ResourceManagerMapper {
    public List<Book> selectAllBook(Map<String, Object> map);

    public Book selectBookById(@Param("id") String id);

    public Boolean updateBook(Book book);

    public Boolean deleteBook(@Param("bookCodes") String[] bookCodes);

    public Boolean deleteBookDetails(@Param("ids") String[] ids, @Param("bookcodes") String[] bookcodes);

    public Boolean deleteBookAuthor(@Param("bookCodes") String[] bookCodes);

    public Boolean deleteBookContent(@Param("bookCodes") String[] bookCodes);

    public Boolean deleteBookFile(@Param("bookCodes") String[] bookCodes);

    public List<BookDetails> selectAllBookDetails(Map<String, Object> map);

    public Integer selectBookDetailsCount(Map<String, Object> map);

    public BookDetails selectBookDetails(@Param("id") String id, @Param("bookcode") String bookcode, @Param("entry") String entry);

    public Boolean updateBookDetails(BookDetails bookDetails);

}
