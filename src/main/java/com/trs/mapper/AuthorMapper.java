package com.trs.mapper;

import com.trs.model.Author;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
public interface AuthorMapper {
    /**
     *
     * @param zid
     * @return
     */
    public List<Author> getBookAuthorByZid(@Param("zid") String zid,@Param("bookcode") String bookcode);
}
