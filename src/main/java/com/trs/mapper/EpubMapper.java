package com.trs.mapper;

import com.trs.model.Book;
import com.trs.model.Epub;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
public interface EpubMapper {

    int  insertEpub(Epub epub);

    /**
     * 根据id查询
     *
     * @return
     */
    public Epub getBookById(String bookId);

    public  List<Epub> selectEpub(String  title);

    int  updateEpub(Epub epub);
}
