package com.trs.mapper;

import com.trs.model.BookFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookFileMapper {
    public List<BookFile> getBookFileByCodeAndTitle18(@Param("bookCode") String bookCode, @Param("start") Integer start, @Param("end") Integer end, @Param("likeString") String likeString);
    public Integer getBookFileNum(@Param("bookCode") String bookCode, @Param("likeString") String likeString);
}
