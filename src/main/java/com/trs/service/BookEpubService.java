package com.trs.service;

import com.trs.model.Book;
import com.trs.model.Epub;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
public interface BookEpubService {


    public Map insertEpub(Epub epub);


    /**
     * 用户中心查询关注词
     *
     * @return
     */
    public Epub getBookById(String bookId);

    public List<Epub> selectEpub(String  title);

    public Map updateEpub(Epub epub);

    public void deleteAll(File file);

    public  byte[] ioPDf(String  path);

    public  String encodeData(String inputData);

    public  String decodeData(String inputData);
}
