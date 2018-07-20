package com.trs.service;

import com.trs.model.EpubSrc;
import com.trs.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by zly on 2017-3-20.
 */
public interface EpubSrcService {


    /**
     *
     *插入到epubsrc表
     * @param epubSrc
     * @return
     */
    public Map insertEpubSrc(EpubSrc epubSrc);

    /**
     *
     *插入到epubsrc表
     * @return
     */
    public List<EpubSrc> selectByTitle(String bookCode, String title);

    /**
     *
     *插入到epubsrc表
     * @param bookCode
     * @return
     */
    public Map deleteSrc(String bookCode);

}
