package com.trs.model;

import java.util.Date;

/**
 * Created by epro on 2017/9/4.
 */
public class Splice {

    private int id;
    //    标题得zid
    private String zid;
    //    标题名称
    private String title;
    //    用户名
    private String  username;
    //    时间
    private Date gmtCreate;
    //bookcode
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

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
