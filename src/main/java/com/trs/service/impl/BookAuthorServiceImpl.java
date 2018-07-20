package com.trs.service.impl;

import com.trs.mapper.AuthorMapper;
import com.trs.model.Author;
import com.trs.service.BookAuthorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
@Service
public class BookAuthorServiceImpl implements BookAuthorService {

    @Resource
    private AuthorMapper AuthorMapper;

    @Override
    public List<Author> getBookAuthorByFid(String zid,String bookcode) {
        return AuthorMapper.getBookAuthorByZid(zid,bookcode);
    }
}
