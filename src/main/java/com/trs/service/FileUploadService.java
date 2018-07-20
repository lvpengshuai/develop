package com.trs.service;


import com.trs.model.Book;
import com.trs.model.BookDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by pz on 2017/4/20.
 */
public interface FileUploadService {

    public String getFilePathForZip(Book book, Short platform);

    public String getEpubCompleteURL(Book book, Short platform);

    public String getFilePathForZip2(Book book, Short platform);

    /**
     * 复制文件夹下所有内容到另一个文件夹下
     * @param oldPath
     * @param newPath
     */
    public void  copyDir(String oldPath, String newPath);

    /**
     * 获取文件夹名称
     * @param oldPath
     * @return
     */
    public List FileName(String oldPath);

    /**
     * 删除文件夹
     * @param folderPath
     */
    public  void delFolder(String folderPath);

    /**
     * 读取日志
     */
    public List<String> readLog(String filePath);
    /**
     * 获取文件名称
     */
    public List FilefoloderName(String oldPath);

    /**
     * 删除所有文件
     * @param path
     * @return
     */
    public  boolean  delAllFile(String path);

    /**
     * s删除文件夹下的文件
     * @param path
     * @return
     */
    public  boolean deleteDir(String path);

    public  void delFolderAll(File file);

    /**
     * pdf生成txt文件
     * @param pdfpath
     * @param newPdfPath
     * @return
     */
    public Integer  readPdfjiami(String  pdfpath,String newPdfPath);
}
