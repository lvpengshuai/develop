package com.trs.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by xuxuecheng on 2017/8/25.
 */
@Entity
@Table(name = "book_author")
public class Author {

    /*Id*/
    private Integer id;
    /*作者ID*/
    private String fid;
    /*作者姓名*/
    private String personname;
    /*作者简介*/
    private String personblurb;
    /*作者作品*/
    private String bookcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getPersonblurb() {
        return personblurb;
    }

    public void setPersonblurb(String personblurb) {
        this.personblurb = personblurb;
    }

    public String getBookcode() {
        return bookcode;
    }

    public void setBookcode(String bookcode) {
        this.bookcode = bookcode;
    }

    @Override
    public String toString() {
        return "BookAuthor{" +
                "id=" + id +
                ", fid='" + fid + '\'' +
                ", personname='" + personname + '\'' +
                ", personblurb='" + personblurb + '\'' +
                ", bookcode='" + bookcode + '\'' +
                '}';
    }
}
