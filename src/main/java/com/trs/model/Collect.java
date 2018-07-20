package com.trs.model;

import java.util.Date;

/**
 * Created by epro on 2017/8/26.
 */
public class Collect {

    private int id;
//    收藏的文章id
    private String tid;
//    收藏得文章标题
    private String title;
//    收藏文章的副标题
    private String  subtitle;
//    文章的来源
    private String  source;
//    文章的摘要
    private String  abs;
//    收藏的用户名
    private String username;
//    收藏的文件夹名称
    private String  foldername;
//    收藏的时间
    private Date gmtCreate;
    //   bookcode
    private String bookcode;

    public String getBookcode() {
        return bookcode;
    }

    public void setBookcode(String bookcode) {
        this.bookcode = bookcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFoldername() {
        return foldername;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "Collent{" +
                "id=" + id +
                ", tid='" + tid + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", source='" + source + '\'' +
                ", abs='" + abs + '\'' +
                ", username='" + username + '\'' +
                ", foldername='" + foldername + '\'' +
                ", gmtCreate=" + gmtCreate +
                '}';
    }
}
