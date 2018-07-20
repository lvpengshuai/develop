package com.trs.service;

import com.trs.model.BookFile;

import java.util.List;

public interface BookFileServices {
    /**
     * 通过Code和Title获得BookFile
     *
     * @return
     */
    public List<BookFile> getBookFileByCodeAndTitle18(String bookCode,Integer index,String likeString);
    public List<BookFile> getBookFileByCodeAndTitle6(String bookCode,Integer index,String likeString);

    /**
     * 得到bookFile的数量
     * @param bookCode
     * @return
     */
    public Integer getBookFileNum(String bookCode,String likeString);
}
