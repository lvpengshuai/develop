package com.trs.model;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * Created by lihuan on 2017-8-28.
 */
public class Epub {

    private int id;
    private String bookId;
    private String bookName;
    private String bookCode;
    private Short platform;

    private String coverUrl;

    private String bookUrl;

    private String bookSize;

    private Date updatetime;

    private String checkId;

    private Date createtime;

    private String pageSize;

    private String catalogue;

    private String verId;

    private Boolean owner;

    private Boolean commentUser;

    private String  title;

    private String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVerId() {
        return verId;
    }

    public void setVerId(String verId) {
        this.verId = verId;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public Boolean getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(Boolean commentUser) {
        this.commentUser = commentUser;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(String catalogue) {
        this.catalogue = catalogue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public Short getPlatform() {
        return platform;
    }

    public void setPlatform(Short platform) {
        this.platform = platform;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getBookSize() {
        return bookSize;
    }

    public void setBookSize(String bookSize) {
        this.bookSize = bookSize;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }


    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}

