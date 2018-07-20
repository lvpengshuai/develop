package com.trs.service;


import javax.xml.transform.TransformerException;

/**
 * Created by pz on 2017/4/20.
 */
public interface EpubHandlerService {

    /**
     * epubj解析，传入文件夹路径
     * @param targetPath
     */
    public void handleEpubOpf(String targetPath);

    public void handleEpubHtml(String targetPath) throws TransformerException;

}
