package com.trs.model;

import java.util.Date;

/**
 * Created by lcy on 2017/5/26.
 */
public class MyCollect {
    private int id;
    private String name;
    private String type;
    private Date gmtCreate;
    private String username;
    private int nameId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }


    @Override
    public String toString() {
        return "MyCollect{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", username='" + username + '\'' +
                ", nameId=" + nameId +
                '}';
    }
}
