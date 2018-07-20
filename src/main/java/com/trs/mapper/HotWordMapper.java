package com.trs.mapper;

import com.trs.model.Book;
import com.trs.model.BookClassify;
import com.trs.model.HotWord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HotWordMapper {

    public void addHotWord(HotWord hotWord);

    public List<HotWord> getHotWords();

    public List<HotWord> getIndexHotWords();

    public void deleteHotWord(Integer id);

    public void updateHotWord(@Param("id") String id, @Param("status") String status);

    public void updateHotWordHot(@Param("id") String id, @Param("hot") String hot);

    public List<Book> getBookAll(@Param("pageSize") Integer pageSize, @Param("currPage") Integer currPage, @Param("search") String search);

    public void updateBookHotType(@Param("id") String id, @Param("hottype") String hottype);

    public Integer getBookAllNum();

    public List<BookClassify> getBookClassifyList();
}
