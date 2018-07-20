package com.trs.service;

import com.trs.model.Book;

import java.util.List;
import java.util.Map;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
public interface EpubService {


    /**
     * 抽取Epub目录
     *
     * @param targetPath
     * @return
     */
    public String getEpubCatalog(String targetPath,Book book);



}
