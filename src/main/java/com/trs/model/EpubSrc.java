package com.trs.model;

import java.util.Date;

/**
 * Created by lihuan on 2017-8-28.
 */
public class EpubSrc {

    private int id;
    private String htmlSRC;
    private String sign;
    private Date gmtCreate;
    private String bookCode;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHtmlSRC() {
        return htmlSRC;
    }

    public void setHtmlSRC(String htmlSRC) {
        this.htmlSRC = htmlSRC;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}

