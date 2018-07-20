package com.trs.service;

import com.trs.model.Author;

import java.util.List;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
public interface BookAuthorService {

    /**
     *
     * @param zid
     * @return
     */
    public List<Author> getBookAuthorByFid(String zid,String bookcode);
}
