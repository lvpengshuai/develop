package com.trs.service.impl;

import com.trs.mapper.BookMapper;
import com.trs.mapper.EpubMapper;
import com.trs.model.Book;
import com.trs.model.Epub;
import com.trs.service.BookEpubService;
import com.trs.service.BookService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
@Service
public class BookEpubServiceImpl implements BookEpubService{

    @Resource
    private EpubMapper epubMapper;


    /**
     * 添加epub
     * @return
     */
    @Override
    public Map insertEpub(Epub epub) {
        Map addMap = new HashMap();
        int addSplice=epubMapper.insertEpub(epub);
        if(addSplice==1){
            //成功
            addMap.put("state", "0");
        }else {
            addMap.put("state", "1");
        }
        return addMap;
    }

    /**
     * 查询根据id
     * @return
     */
    public Epub getBookById(String  bookId){

        Epub list=epubMapper.getBookById(bookId);

        return list;
    }



    /**
     * 添加epub
     * @return
     */
    @Override
    public List<Epub> selectEpub(String title) {
        Map addMap = new HashMap();
        List<Epub> addSplice=epubMapper.selectEpub(title);
        return addSplice;
    }

    /**
     * 添加epub
     * @return
     */
    @Override
    public Map updateEpub(Epub epub) {
        Map addMap = new HashMap();
        int addSplice=epubMapper.updateEpub(epub);
        if(addSplice==1){
            //成功
            addMap.put("state", "0");
        }else {
            addMap.put("state", "1");
        }
        return addMap;
    }

    public void deleteAll(File file) {

        if(!file.exists()){
            return;
        }else if (file.isFile() || file.list().length == 0) {
            file.delete();
        } else {
            for (File f : file.listFiles()) {
                deleteAll(f); // 递归删除每一个文件

            }
            file.delete(); // 删除文件夹
        }
    }


    public  byte[] ioPDf(String  path) {
        File file=new File(path);
        byte[] data=null;
        try{
            FileInputStream input=new FileInputStream(file);
            data = new byte[input.available()];
            input.read(data);
            input.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
    /**
     * 对给定的字符串进行base64加密操作
     */
    public  String encodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.encodeBase64(inputData.getBytes("utf-8")), "utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println(inputData+e);
        }

        return null;
    }

    /**
     * 对给定的字符串进行base64解码操作
     */
    public  String decodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.decodeBase64(inputData.getBytes("utf-8")), "utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println(inputData+e);
        }

        return null;
    }
}
