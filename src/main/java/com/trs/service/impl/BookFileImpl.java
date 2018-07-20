package com.trs.service.impl;

import com.trs.mapper.BookFileMapper;
import com.trs.model.BookFile;
import com.trs.service.BookFileServices;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class BookFileImpl implements BookFileServices {
    @Resource
    private BookFileMapper bookFileMapper;
    @Override
    public List<BookFile> getBookFileByCodeAndTitle18(String bookCode,Integer index ,String likeString) {
        if (likeString!=null){
            likeString="%"+likeString+"%";
        }
        return bookFileMapper.getBookFileByCodeAndTitle18(bookCode,index,22,likeString);
    }



    @Override
    public List<BookFile> getBookFileByCodeAndTitle6(String bookCode, Integer index,String likeString) {
        if (likeString!=null){
            likeString="%"+likeString+"%";
        }
        List<BookFile> bookFileByCodeAndTitle6 = bookFileMapper.getBookFileByCodeAndTitle18(bookCode, index, 6, likeString);
        return bookFileByCodeAndTitle6;
    }

    @Override
    public Integer getBookFileNum(String bookCode,String likeString) {
        if (likeString!=null){
            likeString="%"+likeString+"%";
        }
        return bookFileMapper.getBookFileNum(bookCode,likeString);
    }
}
